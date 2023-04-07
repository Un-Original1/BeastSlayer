package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.magic.CastingMagic;
import com.unoriginal.beastslayer.entity.Entities.magic.MagicType;
import com.unoriginal.beastslayer.entity.Entities.magic.UseMagic;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityFrostWalker extends EntityMob implements IMagicUser {
    private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.createKey(EntityFrostWalker.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> MAGIC_CAST = EntityDataManager.createKey(EntityFrostWalker.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityFrostWalker.class, DataSerializers.VARINT);
    private int magicUseTicks;
    public EntityFrostWalker(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.0F);
    }
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AIFrostWalkerFastMelee(this, 2.0F, false));
        this.tasks.addTask(3, new AIFrostWalkerSlowMelee(this, 1.0F, false));
        this.tasks.addTask(3, new EntityAICastIceMagic(this));
        this.tasks.addTask(3, new CastingMagic<>(this));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 1.0D, 1.25D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityIronGolem.class, 8.0F, 1.0D, 1.25D));
        this.tasks.addTask(5, new EntityAIAvoidEntity<>(this, EntityNetherhound.class, 10.0F,  0.8D,1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityFrostWalker.class));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityNetherhound.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0F * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D + BeastSlayerConfig.GlobalArmor);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(ARMS_RAISED, Boolean.FALSE);
        this.getDataManager().register(MAGIC_CAST, Boolean.FALSE);
        this.dataManager.register(VARIANT, 0);
    }
    protected void updateAITasks()
    {
        super.updateAITasks();
        if (this.magicUseTicks > 0) {
            --this.magicUseTicks;
        }
    }

    public void onLivingUpdate(){
        if (this.onGround && !this.world.isRemote)
        {
            BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
            float f = (float)Math.min(16, 4);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(this.posX, this.posY, this.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR)
                    {
                        IBlockState iblockstate1 = this.world.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.WATER && (iblockstate1.getBlock() == net.minecraft.init.Blocks.WATER || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_WATER) && iblockstate1.getValue(BlockLiquid.LEVEL) == 0 && world.mayPlace(Blocks.FROSTED_ICE, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null))
                        {
                            this.world.setBlockState(blockpos$mutableblockpos1, Blocks.FROSTED_ICE.getDefaultState());
                            this.world.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.FROSTED_ICE, MathHelper.getInt(this.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild())
        {
            float f = this.getBrightness();

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
            {
                boolean flag = true;
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.setFire(8);
                }
            }
        }

        super.onLivingUpdate();
    }

    public static int getRandVariant(Random rand)
    {
        int i = rand.nextInt(100);
        if(i < 15){
            return 1;
        }
        else {
            return 0;
        }
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 4);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    public void setArmsRaised(boolean armsRaised)
    {
        this.dataManager.set(ARMS_RAISED, armsRaised);
    }

    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised()
    {
        return this.dataManager.get(ARMS_RAISED);
    }

    public void setMagicCast(boolean magicCast)
    {
        this.dataManager.set(MAGIC_CAST, magicCast);
    }

    @SideOnly(Side.CLIENT)
    public boolean isCastingMagic()
    {
        return this.dataManager.get(MAGIC_CAST);
    }

    @Override
    public boolean isUsingMagic() {
        return this.magicUseTicks > 0;
    }

    @Override
    public int getMagicUseTicks() {
        return this.magicUseTicks;
    }

    @Override
    public void setMagicUseTicks(int magicUseTicks) {
        this.magicUseTicks = magicUseTicks;
    }

    @Override
    public MagicType getMagicType() {
        return null;
    }

    @Override
    public void setMagicType(MagicType spellTypeIn) {
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag && this.getHeldItemMainhand().isEmpty() && entityIn instanceof EntityLivingBase)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if(!(entityIn instanceof EntityNetherhound)) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100 * (int) f, 4));
            }
            if(this.isRed()){
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 80, 3));
            }
        }

        return flag;
    }

    class EntityAICastIceMagic extends UseMagic<EntityFrostWalker>
    {
        protected EntityAICastIceMagic(EntityFrostWalker magicUserMob) {
            super(magicUserMob);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
        }

        public void updateTask() {
            super.updateTask();
            if(this.magicWarmup < 8) {
                EntityFrostWalker.this.setMagicCast(this.magicWarmup > 0);
            }
        }

        @Override
        protected void useMagic()
        {
            EntityLivingBase entitylivingbase = EntityFrostWalker.this.getAttackTarget();
            double d0 = Math.min(entitylivingbase.posY, EntityFrostWalker.this.posY);
            double d1 = Math.max(entitylivingbase.posY, EntityFrostWalker.this.posY) + 1.0D;
            float f = (float) MathHelper.atan2(entitylivingbase.posZ - EntityFrostWalker.this.posZ, entitylivingbase.posX - EntityFrostWalker.this.posX);

            for (int l = 0; l < 10; ++l)
            {
                double d2 = 1.5D * (double)(l + 1);
                this.spawnIce(EntityFrostWalker.this.posX + (double)MathHelper.cos(f) * d2, EntityFrostWalker.this.posZ + (double)MathHelper.sin(f) * d2, d0, d1, f, l);
            }
        }
        private void spawnIce(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_)
        {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            boolean flag = false;
            double d0 = 0.0D;

            while (true)
            {
                if (!EntityFrostWalker.this.world.isBlockNormalCube(blockpos, true) && EntityFrostWalker.this.world.isBlockNormalCube(blockpos.down(), true))
                {
                    if (!EntityFrostWalker.this.world.isAirBlock(blockpos))
                    {
                        IBlockState iblockstate = EntityFrostWalker.this.world.getBlockState(blockpos);
                        AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(EntityFrostWalker.this.world, blockpos);

                        if (axisalignedbb != null)
                        {
                            d0 = axisalignedbb.maxY;
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();

                if (blockpos.getY() < MathHelper.floor(p_190876_5_) - 1)
                {
                    break;
                }
            }

            if (flag)
            {
                EntityIceCrystal iceCrystal = new EntityIceCrystal(EntityFrostWalker.this.world, p_190876_1_, (double)blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, EntityFrostWalker.this, EntityFrostWalker.this.isRed());
                EntityFrostWalker.this.world.spawnEntity(iceCrystal);
            }
        }

        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 300;
        }

        @Override
        protected SoundEvent getMagicPrepareSound() {
            return ModSounds.FROSTWALKER_PAIN;
        }

        @Override
        protected MagicType getMagicType() {
            return MagicType.SUMMON;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityFrostWalker.this.setMagicCast(false);
        }
    }
    static class AIFrostWalkerFastMelee extends AIFrostWalkerSlowMelee{
        public AIFrostWalkerFastMelee(EntityFrostWalker creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }
        @Override
         public boolean shouldExecute() {
             return super.shouldExecute() && this.attacker.getAttackTarget().isPotionActive(MobEffects.SLOWNESS);
         }
    }

    static class AIFrostWalkerSlowMelee extends EntityAIAttackMelee{
        private int raiseArmTicks;
        private final EntityFrostWalker zombie;

        public AIFrostWalkerSlowMelee(EntityFrostWalker creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
            this.zombie = creature;
        }

        public void startExecuting()
        {
            super.startExecuting();
            this.raiseArmTicks = 0;
        }

        public void resetTask()
        {
            super.resetTask();
            this.zombie.setArmsRaised(false);
        }

        public void updateTask()
        {
            super.updateTask();
            ++this.raiseArmTicks;

            this.zombie.setArmsRaised(this.raiseArmTicks >= 5 && this.attackTick < 10);
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("MagicUseTicks", this.magicUseTicks);
        compound.setInteger("Variant", this.getVariant());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.magicUseTicks = compound.getInteger("MagicUseTicks");
        this.setVariant(compound.getInteger("Variant"));
    }
    public boolean isRed(){
        return this.getVariant() > 0;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(getRandVariant(this.world.rand));
        return livingdata;
    }
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if(!world.isRemote) {
            if (cause.getTrueSource() instanceof EntityPlayer) {
                if (rand.nextInt(4) == 0) {
                    if (this.isRed()) {
                        this.entityDropItem(new ItemStack(ModItems.ICE_WAND_RED, 1, 0), 0.0F);
                    } else {
                        this.entityDropItem(new ItemStack(ModItems.ICE_WAND, 1, 0), 0.0F);
                    }
                }
            }
            if (this.isRed()) {
                this.entityDropItem(new ItemStack(ModItems.ICE_DART, this.world.rand.nextInt(3), 1), 0.0F);
            } else {
                this.entityDropItem(new ItemStack(ModItems.ICE_DART, this.world.rand.nextInt(3), 0), 0.0F);
            }
        }
    }
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_ZOMBIE;
    }
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.FROSTWALKER_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.FROSTWALKER_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.FROSTWALKER_DEATH; }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (source.isFireDamage())
        {
            amount = amount * 3.0F;
        }

        return super.attackEntityFrom(source, amount);
    }
    public double getYOffset() {
        return -0.5D;
    }
}
