package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityRiftedEnderman extends EntityMob {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier ATTACKING_SPEED_BOOST = (new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
    private static final DataParameter<Boolean> SCREAMING = EntityDataManager.createKey(EntityRiftedEnderman.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMORED = EntityDataManager.createKey(EntityRiftedEnderman.class, DataSerializers.VARINT);
    private int lastCreepySound;
    private int armoredTicks;
    private int armoredCooldown;
    private int armorWeakTicks;
    private int shockwaveTicks;
    private int shockwaveCD;
    private static final DataParameter<Boolean> SHOCKWAVE = EntityDataManager.createKey(EntityRiftedEnderman.class, DataSerializers.BOOLEAN);

    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Rifted_Enderman");


    public EntityRiftedEnderman(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.setPathPriority(PathNodeType.WATER, -1.0F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new AIFindPlayer(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityEnderman.class, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(28.0D);
    }

    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
    {
        super.setAttackTarget(entitylivingbaseIn);
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (entitylivingbaseIn == null)
        {
            this.dataManager.set(SCREAMING, Boolean.FALSE);
            iattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
        }
        else
        {
            this.dataManager.set(SCREAMING, Boolean.TRUE);

            if (!iattributeinstance.hasModifier(ATTACKING_SPEED_BOOST))
            {
                iattributeinstance.applyModifier(ATTACKING_SPEED_BOOST);
            }
        }
        if(this.armoredCooldown <= 0 && !this.isArmored()){
            this.setArmored(2);
            this.armoredTicks = 4000;
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SCREAMING, false);
        this.dataManager.register(ARMORED, 0);
        this.dataManager.register(SHOCKWAVE, false);
    }

    public void playEndermanSound()
    {
        if (this.ticksExisted >= this.lastCreepySound + 400)
        {
            this.lastCreepySound = this.ticksExisted;

            if (!this.isSilent())
            {
                this.world.playSound(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, this.getSoundCategory(), 2.5F, 1.0F, false);
            }
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (SCREAMING.equals(key) && this.isScreaming() && this.world.isRemote)
        {
            this.playEndermanSound();
        }

        super.notifyDataManagerChange(key);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);


        if (compound.hasKey("WeakTicks"))
        {
            this.armorWeakTicks = compound.getInteger("WeakTicks");
        }
        if (compound.hasKey("ArmorTicks"))
        {
            this.armoredTicks = compound.getInteger("ArmorTicks");
        }
        if (compound.hasKey("ArmorCooldown"))
        {
            this.armoredCooldown = compound.getInteger("ArmorCooldown");
        }
        if (compound.hasKey("LaserCooldown"))
        {
            this.shockwaveCD = compound.getInteger("LaserCooldown");
        }
        if (compound.hasKey("LaserTicks"))
        {
            this.shockwaveTicks = compound.getInteger("LaserTicks");
        }
        this.setShockwaving(compound.getBoolean("Shockwaving"));
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        compound.setInteger("WeakTicks", this.armorWeakTicks);
        compound.setInteger("ArmorTicks", this.armoredTicks);
        compound.setInteger("ArmorCooldown", this.armoredCooldown);
        compound.setInteger("LaserCooldown", this.shockwaveCD);
        compound.setInteger("LaserTicks", this.shockwaveTicks);
        compound.setBoolean("Shockwaving", this.isShockwaving());
    }

    private boolean shouldAttackPlayer(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.armorInventory.get(3);

        return itemstack.getItem() != Item.getItemFromBlock(Blocks.PUMPKIN) && this.canEntityBeSeen(player) && player.canEntityBeSeen(this);
    }

    public float getEyeHeight()
    {
        return 2.55F;
    }

    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
            this.world.spawnParticle(ModParticles.RIFT, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        if(this.isVulnerable()){
            this.world.setEntityState(this, (byte) 6);
        }

        if(this.isArmored() && !this.world.isRemote){
            if(--this.armoredTicks <= 0 ){
                this.setArmored(0);
                this.armoredCooldown = 3000 + rand.nextInt(3000);
            }
            if(this.ticksExisted % (300 + rand.nextInt(100)) == 0 && !this.isVulnerable()){
                this.armorWeakTicks = 60;
                this.world.setEntityState(this, (byte)11);
                if(this.getAttackTarget() != null && this.canEntityBeSeen(this.getAttackTarget()) && this.getAttackTarget().canEntityBeSeen(this)) {
                    this.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 60, 0, false, false));
                }
            }
            if(this.shockwaveCD < 0){
                Predicate<EntityLiving> selector = entityLiving -> !(entityLiving instanceof EntityRiftedEnderman);
                List<EntityLiving> list = this.world.getEntitiesWithinAABB(EntityLiving.class, this.getEntityBoundingBox().grow(3D, 1D, 3D), selector);
                if(!list.isEmpty()) {
                    this.shockwaveTicks = 20;
                    this.playSound(ModSounds.SHOCKWAVE, 1.5F, 1.0F);
                    this.world.setEntityState(this, (byte) 10);
                    for (EntityLiving living : list){
                        if(!(living instanceof EntityRiftedEnderman)) {
                            living.attackEntityFrom(DamageSource.MAGIC, 5F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
                            living.setFire(5);
                            living.motionX += (living.posX - this.posX) * 0.5;
                            living.motionY += 0.3D;
                            living.motionZ += (living.posZ - this.posZ) * 0.5;
                            this.shockwaveCD = 200;
                        }
                    }
                }
            }
            this.setShockwaving(this.shockwaveTicks > 0);
        }
        if(!this.isArmored() && !this.world.isRemote && this.armoredTicks > 0){
            this.armoredCooldown = 3000 + rand.nextInt(3000);
            this.armoredTicks = 0;
        }
        if(this.armoredCooldown > 0){
            --this.armoredCooldown;
        }

        if(this.armorWeakTicks > 0){
            --this.armorWeakTicks;
        }

        if (this.shockwaveCD >= 0){
            --this.shockwaveCD;
        }
        if(this.shockwaveTicks >= 0){
            --this.shockwaveTicks;
        }

        this.isJumping = false;


        super.onLivingUpdate();

    }

    protected void updateAITasks()
    {
        if (this.isWet() && !this.isArmored())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        super.updateAITasks();
    }

    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(d1, d2, d3);
    }

    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }

        return flag;
    }

    protected SoundEvent getAmbientSound()
    {
        return this.isScreaming() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_ENDERMEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_ENDERMEN_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_ENDERMEN_DEATH;
    }


    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        if (source instanceof EntityDamageSourceIndirect)
        {
            if(!this.isArmored()) {
                for (int i = 0; i < 64; ++i) {
                    if (this.teleportRandomly()) {
                        return true;
                    }
                }

                return false;
            }
            else {
                return super.attackEntityFrom(source, amount * 0.1F);
            }
        }
        else
        {
            if(this.isArmored() ) {
                if (this.isVulnerable()) {
                    int damage = dataManager.get(ARMORED);
                    this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    this.setArmored(damage - 1);
                    this.armorWeakTicks = 0;
                    this.world.setEntityState(this, (byte) 6);
                }
                else {
                    amount = amount * 0.1F;
                }
            }

            boolean flag = super.attackEntityFrom(source, amount);

            if (source.isUnblockable() && this.rand.nextInt(10) != 0 && !this.isArmored())
            {
                this.teleportRandomly();
            }

            return flag;
        }
    }

    public boolean isScreaming()
    {
        return this.dataManager.get(SCREAMING);
    }

    public boolean isArmored()
    {
        return this.dataManager.get(ARMORED) > 0;
    }
    public int getArmorValue()
    {
        return this.dataManager.get(ARMORED);
    }

    public void setArmored(int armored){
        this.dataManager.set(ARMORED, armored);
    }

    public boolean isShockwaving()
    {
        return this.dataManager.get(SHOCKWAVE);
    }
    public void setShockwaving(boolean b){
        this.dataManager.set(SHOCKWAVE, b);
    }

    static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        private final EntityRiftedEnderman enderman;
        private EntityPlayer player;
        private int aggroTime;
        private int teleportTime;

        public AIFindPlayer(EntityRiftedEnderman riftedEnderman)
        {
            super(riftedEnderman, EntityPlayer.class, false);
            this.enderman = riftedEnderman;
        }

        public boolean shouldExecute()
        {
            double d0 = this.getTargetDistance();
            this.player = this.enderman.world.getNearestAttackablePlayer(this.enderman.posX, this.enderman.posY, this.enderman.posZ, d0, d0, null, p_apply_1_ -> p_apply_1_ != null && AIFindPlayer.this.enderman.shouldAttackPlayer(p_apply_1_));
            return this.player != null;
        }

        public void startExecuting()
        {
            this.aggroTime = 5;
            this.teleportTime = 0;
        }

        public void resetTask()
        {
            this.player = null;
            super.resetTask();
        }

        public boolean shouldContinueExecuting()
        {
            if (this.player != null)
            {
                if (!this.enderman.shouldAttackPlayer(this.player))
                {
                    return false;
                }
                else
                {
                    this.enderman.faceEntity(this.player, 10.0F, 10.0F);
                    return true;
                }
            }
            else
            {
                return this.targetEntity != null && this.targetEntity.isEntityAlive() || super.shouldContinueExecuting();
            }
        }

        public void updateTask()
        {
            if (this.player != null)
            {
                if (--this.aggroTime <= 0)
                {
                    this.targetEntity = this.player;
                    this.player = null;
                    super.startExecuting();
                }
            }
            else
            {
                if (this.targetEntity != null)
                {
                    if (this.enderman.shouldAttackPlayer(this.targetEntity))
                    {
                        if (this.targetEntity.getDistanceSq(this.enderman) < 16.0D && this.enderman.getRNG().nextInt(5) == 0)
                        {
                            this.enderman.teleportRandomly();
                        }

                        this.teleportTime = 0;
                    }
                    else if (this.targetEntity.getDistanceSq(this.enderman) > 256.0D && this.teleportTime++ >= 30 && this.enderman.teleportToEntity(this.targetEntity))
                    {
                        this.teleportTime = 0;
                    }
                }

                super.updateTask();
            }
        }
    }

    public boolean isVulnerable(){
        return this.armorWeakTicks > 0;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return (this.isVulnerable()) ? 15728880 : super.getBrightnessForRender();
    }

    public float getBrightness()
    {
        return (this.isVulnerable()) ? 1.0F : super.getBrightness();
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 6)
        {
            this.armorWeakTicks = 0;
            this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX + 0.55D - (double)(rand.nextFloat() * 0.1F), this.posY + height / 2 + 0.55D - (double)(rand.nextFloat() * 0.1F) , this.posZ + 0.55D - (double)(rand.nextFloat() * 0.1F) , rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D);
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }
        if(id == 10){
            this.shockwaveTicks = 20;
        }
        if(id == 11){
            this.armorWeakTicks = 60;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean attemptTeleport(double x, double y, double z)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this);
        World world = this.world;
        Random random = this.getRNG();

        if (world.isBlockLoaded(blockpos))
        {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0)
            {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1)
            {
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(this.getEntityBoundingBox()))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            this.setPositionAndUpdate(d0, d1, d2);
            return false;
        }
        else
        {
            for (int j = 0; j < 128; ++j)
            {
                double d6 = (double)j / 127.0D;
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * (double)this.height;
                double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                world.spawnParticle(ModParticles.RIFT, d3, d4, d5, f, f1, f2);
            }

            (this).getNavigator().clearPath();

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getShockwaveTicks(){
        return shockwaveTicks;
    }


    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.dimension == 0;
    }
}
