package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIBurrow;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMeleeConditional;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAISandyFollowOwner;
import com.unoriginal.beastslayer.entity.Entities.magic.CastingMagic;
import com.unoriginal.beastslayer.entity.Entities.magic.MagicType;
import com.unoriginal.beastslayer.entity.Entities.magic.UseMagic;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class EntitySandy extends EntityTameable implements IMagicUser, IEntityMultiPart, IMob {
    private int attackTicks;
    private int buriedTicks;
    private static final DataParameter<Byte> MAGIC = EntityDataManager.createKey(EntitySandy.class, DataSerializers.BYTE);
    private MagicType activeMagic = MagicType.NONE;
    private int magicUseTicks;
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Sandmonster");
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.PORKCHOP, Items.BEEF, Items.ROTTEN_FLESH, Items.MUTTON, Items.CHICKEN);
    public MultiPartEntityPart[] sandyPartArray;
    public MultiPartEntityPart sandyPartMain = new MultiPartEntityPart(this, "main", 1.4F, 3.5F);
    public MultiPartEntityPart sandyPartBody = new MultiPartEntityPart(this, "body1", 1.2F, 1.2F);
    public MultiPartEntityPart sandyPartBody2 = new MultiPartEntityPart(this, "body2", 1.0F, 1.1F);
    public MultiPartEntityPart sandyPartBody3 = new MultiPartEntityPart(this, "body3", 0.8F, 0.8F);
    public MultiPartEntityPart sandyPartTail1 = new MultiPartEntityPart(this, "tail1", 0.6F, 0.6F);
    public MultiPartEntityPart sandyPartTail2 = new MultiPartEntityPart(this, "tail2", 0.4F, 0.4F);

    private int dodgeTicks;
    private int avoidTicks;

    private EntityAIBurrow entityAIBurrow;

    public EntitySandy(World worldIn) {

        super(worldIn);
        this.sandyPartArray = new MultiPartEntityPart[] {this.sandyPartMain, this.sandyPartBody, this.sandyPartBody2, this.sandyPartBody3, this.sandyPartTail1, this.sandyPartTail2};
        this.setSize(1.5F, 3.5F);
        this.stepHeight = 1.0F;
        this.experienceValue = 50;
        this.noClip = false;
        this.ignoreFrustumCheck = true;
        this.spawnableBlock = Blocks.SAND;
        this.setTamed(false);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public float getEyeHeight()
    {
        return 2.8F;
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.entityAIBurrow = new EntityAIBurrow(this, 200, true);
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new CastingMagic<>(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(4, new EntitySandy.UseSandMagic(this));
       // this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, this.entityAIBurrow);
        this.tasks.addTask(5, new EntityAIMeleeConditional(this, 1.0D, false, Predicate -> this.avoidTicks <= 0));
        this.tasks.addTask(6, new EntitySandy.SummonSandShooter(this));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 1.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVex.class, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntitySandy.class, false));
        this.tasks.addTask(6, new EntityAISandyFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, (Predicate<Entity>) entity -> entity instanceof EntityVillager || entity instanceof EntityIronGolem));
        this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, (Predicate<Entity>) entity -> entity instanceof EntityPlayer));

        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, Predicate -> this.avoidTicks > 0 ,2F, 1.0D, 1.0D));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        if(this.isTamed()){
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((80.0D + BeastSlayerConfig.SandyTamedHealthBonus) * BeastSlayerConfig.GlobalHealthMultiplier );
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((80.0D + BeastSlayerConfig.SandyHealthBonus)  * BeastSlayerConfig.GlobalHealthMultiplier );
        }

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D + BeastSlayerConfig.GlobalArmor);
    }
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(MAGIC, (byte)0);
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
        this.buriedTicks = this.entityAIBurrow.getBuriedTimer();
        if (this.magicUseTicks > 0) {
            --this.magicUseTicks;
        }
    }
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(this.isUsingMagic()) {
            IMagicUser.spawnMagicParticles(this);
        }
        else {
               this.dataManager.set(MAGIC, (byte)0);
            }
        float f1 = MathHelper.sin(this.renderYawOffset * 0.017453292F + (float)Math.PI);
        float f2 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
        this.sandyPartMain.onUpdate();
        this.sandyPartMain.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
        this.sandyPartBody.onUpdate();
        this.sandyPartBody2.onUpdate();
        this.sandyPartBody3.onUpdate();
        this.sandyPartTail1.onUpdate();
        this.sandyPartTail2.onUpdate();
        if(this.isSitting()){
            this.sandyPartBody.setLocationAndAngles(this.posX - (double) (f1 * 0.8F), this.posY, this.posZ - (double) (f2), 0.0F, 0.0F);
            this.sandyPartBody2.setLocationAndAngles(this.posX - (double) (f1 * 1.4F), this.posY, this.posZ - (double) (f2 * 1.9F), 0.0F, 0.0F);
            this.sandyPartBody3.setLocationAndAngles(this.posX - (double) (f1 * 0.8F), this.posY, this.posZ - (double) (f2 * 2.6F), 0.0F, 0.0F);
            this.sandyPartTail1.setLocationAndAngles(this.posX - (double) (f1 * 0.6F), this.posY, this.posZ - (double) (f2 * 2.9F), 0.0F, 0.0F);
            this.sandyPartTail2.setLocationAndAngles(this.posX - (double) (f1 * 1.2F), this.posY, this.posZ - (double) (f2 * 2.1F), 0.0F, 0.0F);
        }
        else {
            this.sandyPartBody.setLocationAndAngles(this.posX - (double) (f1 * 1.4F), this.posY, this.posZ - (double) (f2 * 1.4F), 0.0F, 0.0F);
            this.sandyPartBody2.setLocationAndAngles(this.posX - (double) (f1 * 2.5F), this.posY, this.posZ - (double) (f2 * 2.5F), 0.0F, 0.0F);
            this.sandyPartBody3.setLocationAndAngles(this.posX - (double) (f1 * 3.5F), this.posY, this.posZ - (double) (f2 * 3.5F), 0.0F, 0.0F);
            this.sandyPartTail1.setLocationAndAngles(this.posX - (double) (f1 * 4.2F), this.posY, this.posZ - (double) (f2 * 4.2F), 0.0F, 0.0F);
            this.sandyPartTail2.setLocationAndAngles(this.posX - (double) (f1 * 5.1F), this.posY, this.posZ - (double) (f2 * 5.1F), 0.0F, 0.0F);
        }

        if(!this.isBuried()) {

                this.collideWithPart(this.world.getEntitiesWithinAABBExcludingEntity(this, this.sandyPartBody.getEntityBoundingBox()));
                this.collideWithPart(this.world.getEntitiesWithinAABBExcludingEntity(this, this.sandyPartBody2.getEntityBoundingBox()));
                this.collideWithPart(this.world.getEntitiesWithinAABBExcludingEntity(this, this.sandyPartBody3.getEntityBoundingBox()));
                this.collideWithPart(this.world.getEntitiesWithinAABBExcludingEntity(this, this.sandyPartTail1.getEntityBoundingBox()));
                this.collideWithPart(this.world.getEntitiesWithinAABBExcludingEntity(this, this.sandyPartTail2.getEntityBoundingBox()));
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry())
        {
            this.setAngry(false);
        }
        if(this.avoidTicks >= 0){
            --this.avoidTicks;
        }
        if (this.attackTicks > 0) {
            --this.attackTicks;
        }
        if (this.dodgeTicks > 0) {
            --this.dodgeTicks;

        }
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
        if (this.isBuried()) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
            if (iblockstate.getMaterial() != Material.AIR) {
                for(int x = 0; x < 4; x++) {
                    this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.02D, Block.getStateId(iblockstate));
                }
                if (!world.isRemote && this.ticksExisted % 150 == 0 ) {
                    this.playSound(iblockstate.getBlock().getSoundType().getBreakSound(), 1.0F, 1.0F); //this is deprecated! ... joe mama is deprecated
                }
            }

        }

        if (this.world.isRemote)
        {
            this.buriedTicks = Math.max(0, this.buriedTicks - 1);
        }
        if (!world.isRemote && this.ticksExisted % 150 == 0 && this.isTamed()) {
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(15D));
            if(!entities.isEmpty()) {
                for (Entity entity : entities)
                    if (entity instanceof EntitySandy)
                        if (((EntitySandy) entity).getOwner() == this.getOwner()) {
                            ((EntitySandy) entity).setAttackTarget(this); //now sandmonster is jealous for real
                        }
            }
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTicks = 10;
        this.world.setEntityState(this, (byte)4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag)
        {
            entityIn.motionY += 0.4000000059604645D;
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public boolean canDespawn(){return !this.isTamed();}

    @Override
    public boolean canBePushed(){return false;}

    @Override
    protected boolean canBeRidden(Entity entityIn)
    {
        return false;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
        if(part == this.sandyPartMain || part == this.sandyPartBody || part == this.sandyPartBody2 || part == this.sandyPartBody3 || part == this.sandyPartTail1 || part == this.sandyPartTail2)
        {
            if(!this.isBuried()) {
                this.attackSandyFrom(source, damage);
            }
        }
        return true;
    }

    private void collideWithPart(List<Entity> list)
    {
        for (Entity entity : list)
        {
            if (entity instanceof EntityLivingBase)
            {
                double d0 = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double) (1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double) (1.0F - this.entityCollisionReduction);
                    entity.addVelocity(d0, 0.0D, d1);
                }
            }
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || this.isBuried() || source == DamageSource.IN_WALL || source == DamageSource.CACTUS)
        {
            return false;
        }
        if(rand.nextInt(3) == 0 && !this.isBuried() && !this.isUsingMagic() && source.getTrueSource() instanceof EntityLivingBase) {
            double d0 = this.posX - source.getImmediateSource().posX;
            double d1 = this.posZ - source.getImmediateSource().posZ;

            this.dodgeTicks = 10;
            this.avoidTicks = 10 + rand.nextInt(20);
            this.world.setEntityState(this, (byte) 11);
            this.addVelocity(d0 * 0.5D, 0.25D, d1 * 0.5D);
            this.navigator.clearPath();

            if (this.aiSit != null)
            {
                aiSit.setSitting(false);
            }

            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                aiSit.setSitting(false);
            }
            this.avoidTicks = 10 + rand.nextInt(20);
            return super.attackEntityFrom(source, amount);
        }
    }
//fix? set nopath
    protected boolean attackSandyFrom(DamageSource source, float amount)
    {
        return super.attackEntityFrom(source, amount);
    }

    public Entity[] getParts()
    {
        return this.sandyPartArray;
    }

    public boolean canBeLeashedTo(EntityPlayer player) { return false; }

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

                    if (itemstack.getCount() == BeastSlayerConfig.sandmonsterTameStackSize && this.getHealth() < 80.0F &&  TAME_ITEMS.contains(itemstack.getItem()))
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(BeastSlayerConfig.sandmonsterTameStackSize);
                        }

                        this.heal((float)itemfood.getHealAmount(itemstack) * 10.0F);
                        this.playTameEffect(true);
                        return true;
                    }
                }
            }

            if (this.isTamed() && this.isOwner(player) && !this.world.isRemote)
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        }
        else if (itemstack.getItem() instanceof ItemFood && itemstack.getCount() == BeastSlayerConfig.sandmonsterTameStackSize && !this.isTamed() &&  TAME_ITEMS.contains(itemstack.getItem()))
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(BeastSlayerConfig.sandmonsterTameStackSize);
            }

            if (!this.world.isRemote)
            {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setHealth(80.0F);
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

    public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner)
    {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast))
        {
            if (target instanceof EntityWolf)
            {
                EntityWolf entitywolf = (EntityWolf)target;

                if (entitywolf.isTamed() && entitywolf.getOwner() == owner)
                {
                    return false;
                }
            }
            if(target instanceof EntitySandy){

                EntitySandy sandy = (EntitySandy)target;

                if(sandy.isTamed() && sandy.getOwner() != owner){
                    return false;
                }
            }
           if (target instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer)owner).canAttackPlayer((EntityPlayer)target))
            {
                return false;
            }
            else
            {
                return !(target instanceof AbstractHorse) || !((AbstractHorse)target).isTame();
            }
        }
        else {
            return false;
        }

    }

    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
    {
        super.setAttackTarget(entitylivingbaseIn);
        this.setAngry(entitylivingbaseIn != null);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Angry", this.isAngry());
        compound.setBoolean("Buried", this.isBuried());
        compound.setInteger("MagicUseTicks", this.magicUseTicks);
        compound.setInteger("Dodge", this.dodgeTicks);
        compound.setInteger("Avoid", this.avoidTicks);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setAngry(compound.getBoolean("Angry"));
        this.magicUseTicks = compound.getInteger("MagicUseTicks");
        this.dodgeTicks = compound.getInteger("Dodge");
        this.avoidTicks = compound.getInteger("Avoid");
    }

    public boolean isAngry()
    {
        return (this.dataManager.get(TAMED) & 2) != 0;
    }

    public void setAngry(boolean angry)
    {
        byte b0 = this.dataManager.get(TAMED);

        if (angry)
        {
            this.dataManager.set(TAMED, (byte) (b0 | 2));
        }
        else
        {
            this.dataManager.set(TAMED, (byte) (b0 & -3));
        }
    }

    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    class UseSandMagic extends UseMagic<EntitySandy>
    {

        protected UseSandMagic(EntitySandy magicUserMob) {
            super(magicUserMob);
        }


        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !EntitySandy.this.isBuried() && EntitySandy.this.dodgeTicks <= 0 && !(EntitySandy.this.getAttackTarget() instanceof EntitySandy);

        }

        @Override
        protected void useMagic()
        {
            EntityLivingBase entitylivingbase = EntitySandy.this.getAttackTarget();
            double d0 = Math.min(entitylivingbase.posY, EntitySandy.this.posY);
            double d1 = Math.max(entitylivingbase.posY, EntitySandy.this.posY) + 1.0D;
            float f = (float)MathHelper.atan2(entitylivingbase.posZ - EntitySandy.this.posZ, entitylivingbase.posX - EntitySandy.this.posX);

            if (EntitySandy.this.getDistanceSq(entitylivingbase) < 9.0D)
            {
                for (int k = 0; k < 8; ++k)
                {
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    this.spawnTornadoes(EntitySandy.this.posX + (double)MathHelper.cos(f2) * 4.5D, EntitySandy.this.posZ + (double)MathHelper.sin(f2) * 4.5D, d0, d1, f2);
                }
            }
            else if (EntitySandy.this.getDistanceSq(entitylivingbase) > 18D && rand.nextInt(5)==0)
            {
                for (int i = 0; i < 7; ++i)
                {
                    float f2 = f + (float)i * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    this.spawnTornadoes(entitylivingbase.posX + (double)MathHelper.cos(f2) * 4D, entitylivingbase.posZ + (double)MathHelper.sin(f2) * 4D, d0, d1, f2);
                }
            }
            else
            {
                for (int l = 0; l < 4; ++l)
                {
                    double d2 = 5.0D * (double)(l + 1);
                    float f3 = f + 12F;
                    float f4 = f - 12F;
                    this.spawnTornadoes(EntitySandy.this.posX + (double)MathHelper.cos(f) * d2, EntitySandy.this.posZ + (double)MathHelper.sin(f) * d2, d0, d1, f);
                    this.spawnTornadoes(EntitySandy.this.posX + (double)MathHelper.cos(f3) * d2, EntitySandy.this.posZ + (double)MathHelper.sin(f3) * d2, d0, d1, f3);
                    this.spawnTornadoes(EntitySandy.this.posX + (double)MathHelper.cos(f4) * d2, EntitySandy.this.posZ + (double)MathHelper.sin(f4) * d2, d0, d1, f4);
                }
            }

        }
        private void spawnTornadoes(double x, double z, double y, double p_190876_7_, float rotation)
        {
            BlockPos blockpos = new BlockPos(x, p_190876_7_, z);
            boolean flag = false;
            double d0 = 0.0D;

            while (true)
            {
                if (!EntitySandy.this.world.isBlockNormalCube(blockpos, true) && EntitySandy.this.world.isBlockNormalCube(blockpos.down(), true))
                {
                    if (!EntitySandy.this.world.isAirBlock(blockpos))
                    {
                        IBlockState iblockstate = EntitySandy.this.world.getBlockState(blockpos);
                        AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(EntitySandy.this.world, blockpos);

                        if (axisalignedbb != null)
                        {
                            d0 = axisalignedbb.maxY;
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();

                if (blockpos.getY() < MathHelper.floor(y) - 1)
                {
                    break;
                }
            }

            if (flag)
            {
                EntityTornado entityTornado = new EntityTornado(EntitySandy.this.world, x, (double)blockpos.getY() + d0, z, rotation, EntitySandy.this);
                EntitySandy.this.world.spawnEntity(entityTornado);
            }
        }

        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 600;
        }

        @Override
        protected SoundEvent getMagicPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }

        @Override
        protected MagicType getMagicType() {
            return MagicType.SUMMON;
        }
    }
    class SummonSandShooter extends UseMagic<EntitySandy>
    {

        protected SummonSandShooter(EntitySandy magicUserMob) {
            super(magicUserMob);
        }

        @Override
        protected void useMagic() {
            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos = (new BlockPos(EntitySandy.this)).add(-8 + EntitySandy.this.rand.nextInt(20), 1, -8 + EntitySandy.this.rand.nextInt(20));
                double d0 = 10D - blockpos.getX();
                double d1 = blockpos.getY();
                double d2 = 10D - blockpos.getX();
                float f = (float) MathHelper.atan2( 10.0F - blockpos.getZ(), 10.0F - blockpos.getX());
                for (int k = 0; k < 8; ++k){
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    EntitySandSpit entitySandSpit = new EntitySandSpit(EntitySandy.this.world, EntitySandy.this);
                    entitySandSpit.moveToBlockPosAndAngles(blockpos, 0, 0);
                    entitySandSpit.shoot(d0 + (double)MathHelper.cos(f2) * (k + 1 * 10000D), d1, d2 + (double)MathHelper.sin(f2) * (k + 1 * 10000D), 1.2F, 0.0F);
                    EntitySandy.this.world.spawnEntity(entitySandSpit);
                }
            }
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !EntitySandy.this.isBuried() && EntitySandy.this.dodgeTicks <= 0;
        }

        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 400;
        }

        @Override
        protected SoundEvent getMagicPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
        }

        @Override
        protected MagicType getMagicType() {
            return MagicType.SUMMON;
        }
    }

    @Override
    public boolean getCanSpawnHere() { return super.getCanSpawnHere() && this.world.isThundering() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL; }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    public boolean isUsingMagic() {
        if (this.world.isRemote) {
            return this.dataManager.get(MAGIC) > 0;
        } else {
            return this.magicUseTicks > 0;
        }
    }

    @Override
    public int getMagicUseTicks() { return this.magicUseTicks; }

    @Override
    public void setMagicUseTicks(int magicUseTicksIn) { this.magicUseTicks = magicUseTicksIn; }

    @Override
    public MagicType getMagicType() {
        return !this.world.isRemote ? this.activeMagic : MagicType.getFromId(this.dataManager.get(MAGIC));
    }

    @Override
    public void setMagicType(MagicType magicType) {
        this.activeMagic = magicType;
        this.dataManager.set(MAGIC, (byte)magicType.getId());
    }

    public boolean isAttacking(){ return this.attackTicks > 0; }

    public boolean isBuried(){ return this.buriedTicks > 0; }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTicks = 10;
        }
        if (id == 10) {
            this.buriedTicks = 200;
        }
        if(id == 11){
            this.dodgeTicks = 10;
        }
        if (id == 12) {
            this.buriedTicks = 0;
        }
        else {
        super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() { return this.attackTicks;}
    // /kill @e[type=ancientbeasts:sandy]

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.SANDMONSTER_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.SANDMONSTER_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.SANDMONSTER_DEATH; }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
         if(entityIn instanceof EntitySandy){
            return false;
         }
         else {
             return super.isOnSameTeam(entityIn);
         }
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
        if (!this.isRidingSameEntity(entityIn)) {
            if (!entityIn.noClip && !this.noClip) {
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double) (1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double) (1.0F - this.entityCollisionReduction);

                    if (!entityIn.isBeingRidden()) {
                        entityIn.addVelocity(d0, 0.0D, d1);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getDodgeTicks(){
        return this.dodgeTicks;
    }
}