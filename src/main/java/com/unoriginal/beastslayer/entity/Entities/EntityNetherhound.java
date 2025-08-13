package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIRamAtTarget;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EntityNetherhound extends EntityTameable {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Netherhound");
    private int flameTicks;
    private boolean isInLava;
    private boolean isShaking;
    private float timeIsShaking;
    private float prevTimeIsShaking;
    private Set<Block> VALID_BLOCKS = Sets.newHashSet(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.MAGMA, Blocks.GRAVEL);

    public EntityNetherhound(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 1.4F);
        this.flameTicks = 200 + rand.nextInt(400);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.isImmuneToFire = true;
        this.setTamed(false);
    }

    protected void initEntityAI()
    {
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(4, new EntityAIRamAtTarget(this));
        this.tasks.addTask(6, new AINetherhoundFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityFrostWalker.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityFrostashFox.class, false));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, true, (Predicate<Entity>) p_apply_1_ -> p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof EntityIronGolem));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(28.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            Entity entity = source.getTrueSource();

            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this).setFireDamage(), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
            entityIn.setFire(4);
            if(this.isSprinting()){
                entityIn.motionX += (entityIn.posX - this.posX) * 0.8D;
                entityIn.motionY += 0.2D;
                entityIn.motionZ += (entityIn.posZ - this.posZ) * 0.8D;
            }
        }

        return flag;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if((this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 3D && this.rand.nextInt(7) == 0) || --this.flameTicks <= 0){
            Predicate<EntityLivingBase> selector = entityliving -> !(entityliving instanceof EntityNetherhound);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(2.5D), selector);
            if(!list.isEmpty() || --this.flameTicks <= 0){
                for(EntityLivingBase l : list){
                    if(!this.isOnSameTeam(l) && !this.world.isRemote){
                        if(!l.isImmuneToFire()) {
                            l.setFire(4);
                            l.attackEntityFrom(DamageSource.causeMobDamage(this), 3F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
                            this.world.setEntityState(this, (byte) 10);
                            this.playSound(ModSounds.NETHERHOUND_CHARGE, 1.0F, 1.0F);
                        }
                    }
                    if(this.world.isRemote){
                        for (int i = 0; i < 30; ++i)
                        {
                            this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
                        }
                    }
                }
                this.flameTicks = 200 + rand.nextInt(400);
            }
        }
        if(this.isSprinting()){
            if(this.world.isRemote) {
                for (int i = 0; i < 2; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height + 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
                }
            }
        }
        if(this.isWet() && !world.isRemote){
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        if (!this.world.isRemote && this.isInLava && !this.isShaking && !this.hasPath() && this.onGround)
        {
            this.isShaking = true;
            this.timeIsShaking = 0.0F;
            this.prevTimeIsShaking = 0.0F;
            this.world.setEntityState(this, (byte)8);
        }

        if (!world.isRemote && this.ticksExisted % 150 == 0 && this.isTamed()) {
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(12D));
            if(!entities.isEmpty()) {
                for (Entity entity : entities)
                    if (entity instanceof EntityFrostashFox) {
                        this.setAttackTarget((EntityFrostashFox)entity);
                    }
            }
        }
        if(this.world.getDifficulty() == EnumDifficulty.PEACEFUL && !world.isRemote){
            this.setDead();
        }
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(this.isSprinting() && !this.world.isRemote){
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(1.2D));
            if (!list.isEmpty()){
                for (EntityLivingBase livingBase : list){
                    if (livingBase != this) {
                        this.attackEntityAsMob(livingBase);
                        this.motionX *= 0F;
                        this.motionY *= 0F;
                        this.setSprinting(false);
                    }
                }
            }
        }
        if (this.isInLava())
        {
            this.isInLava = true;
            this.isShaking = false;
            this.timeIsShaking = 0.0F;
            this.prevTimeIsShaking = 0.0F;
        }
        else if ((this.isInLava || this.isShaking) && this.isShaking)
        {
            if (this.timeIsShaking == 0.0F)
            {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeIsShaking = this.timeIsShaking;
            this.timeIsShaking += 0.05F;

            if (this.prevTimeIsShaking >= 2.0F)
            {
                this.isInLava = false;
                this.isShaking = false;
                this.prevTimeIsShaking = 0.0F;
                this.timeIsShaking = 0.0F;
            }

            if (this.timeIsShaking > 0.4F)
            {
                float f = (float)this.getEntityBoundingBox().minY;
                int i = (int)(MathHelper.sin((this.timeIsShaking - 0.4F) * (float)Math.PI) * 2.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + (double)f1, f + 0.8F, this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float p_70923_1_, float p_70923_2_)
    {
        float f = (this.prevTimeIsShaking + (this.timeIsShaking - this.prevTimeIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }
        else if (f > 1.0F)
        {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float)Math.PI) * MathHelper.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }


    public void onDeath(DamageSource cause)
    {
        if(!this.world.isRemote){
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(3D));
            if(!list.isEmpty()){
                for (EntityLivingBase e : list){
                    e.setFire(4);
                }
            }
            this.world.createExplosion(this, this.posX, this.posY + (this.height / 2), this.posZ, 1.0F, false);
        }
        if (this.world.isRemote)
        {
            for (int i = 0; i < 30; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
            }
        }
        super.onDeath(cause);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed())
        {
            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.getHealth() < this.getMaxHealth())
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }

                        this.heal((float)itemfood.getHealAmount(itemstack));
                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack))
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        }
        else if (itemstack.getItem() == Items.ROTTEN_FLESH && this.getHealth() < this.getMaxHealth() / 2)
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote)
            {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setHealth(this.getMaxHealth());
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.ROTTEN_FLESH;
    }

    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(otherAnimal instanceof EntityNetherhound))
        {
            return false;
        }
        else
        {
            EntityNetherhound netherhound = (EntityNetherhound)otherAnimal;

            if (!netherhound.isTamed())
            {
                return false;
            }
            else if (netherhound.isSitting())
            {
                return false;
            }
            else
            {
                return this.isInLove() && netherhound.isInLove();
            }
        }
    }

    @Nullable
    @Override
    public EntityNetherhound createChild(EntityAgeable ageable)
    {
        EntityNetherhound netherhound = new EntityNetherhound(this.world);
        UUID uuid = this.getOwnerId();

        if (uuid != null)
        {
            netherhound.setOwnerId(uuid);
            netherhound.setTamed(true);
        }

        return netherhound;
    }


    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 8)
        {
            this.isShaking = true;
            this.timeIsShaking = 0.0F;
            this.prevTimeIsShaking = 0.0F;
        }
        else if (id == 10){
            this.playSound(ModSounds.NETHERHOUND_CHARGE, 1.0F, 1.0F);
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("flameTime")){
            this.flameTicks = compound.getInteger("flameTime");
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("flameTime", this.flameTicks);
    }

    @Override
    public boolean canDespawn(){return !this.isTamed();}

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL &&  isValidBiome(this.posX, this.posY, this.posZ) && isValidBlock(this.posX, this.posY, this.posZ);
    }

    public boolean isValidBiome(double x, double y, double z){
        return world.getBiome(new BlockPos(x, y, z)) != ForgeRegistries.BIOMES.getValue(new ResourceLocation("nb:warped_forest"));

    }

    public boolean isValidBlock(double x, double y, double z){
        return VALID_BLOCKS.contains(this.world.getBlockState(new BlockPos(x, y-1, z)).getBlock()) || rand.nextInt(2) == 0;

    }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.NETHERHOUND_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.NETHERHOUND_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.NETHERHOUND_DEATH; }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    class AINetherhoundFollowOwner extends EntityAIFollowOwner{
        World world;
        private final EntityTameable tameable;
        public AINetherhoundFollowOwner(EntityTameable tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
            super(tameableIn, followSpeedIn, minDistIn, maxDistIn);
            this.world = tameableIn.world;
            this.tameable = tameableIn;
        }
        @Override
        protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
        {
            BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            boolean canSeeSkyRain = this.world.canSeeSky(blockpos) && this.world.isRaining();
            return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.tameable) && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2)) && !canSeeSkyRain;
        }
    }

}
