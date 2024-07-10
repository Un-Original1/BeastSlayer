package com.unoriginal.beastslayer.entity.Entities;


import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.items.ItemMask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityTank extends AbstractTribesmen {
    private static final DataParameter<Boolean> TORCH = EntityDataManager.createKey(EntityTank.class, DataSerializers.BOOLEAN);
    private ResourceLocation TRADE = new ResourceLocation(BeastSlayer.MODID, "trades/Tank");
    private ResourceLocation TREASURE = new ResourceLocation(BeastSlayer.MODID, "trades/treasure_t");
    private int blockTicks;
    private int attackTick;

    public EntityTank(World worldIn) {
        super(worldIn);
        this.setSize(1.3F, 2.0F);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.12D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new AIScorcherAttack(this));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, AbstractTribesmen.class, 10, true, false, p_apply_1_ -> p_apply_1_.isFiery() && !this.isFiery()));
    }
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.TORCH));

    }

    @Override
    public float getEyeHeight() {
        return 1.2F;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TORCH, false);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.blockTicks >= 0){
            --this.blockTicks;
        }
        if(this.attackTick >= 0){
            --this.attackTick;
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return livingdata;
    }

    @SideOnly(Side.CLIENT)
    public boolean isTorchInHand()
    {
        return this.dataManager.get(TORCH);
    }

    public void setTorchInHand(boolean torchInHand){
        this.dataManager.set(TORCH, torchInHand);
    }

    public boolean isBlocking(){
        return this.blockTicks > 0;
    }

    static class AIScorcherAttack extends EntityAIBase
    {
        private final EntityTank tank;
        private int attackStep;
        private int attackTime;

        public AIScorcherAttack(EntityTank tank)
        {
            this.tank = tank;
            this.setMutexBits(3);
        }

        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.tank.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive() && !tank.isBlocking() && !tank.isAttacking();
        }

        public void startExecuting()
        {
            this.attackStep = 0;

        }

        public void resetTask()
        {
            this.tank.setTorchInHand(false);
           super.resetTask();
        }

        public void updateTask() {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.tank.getAttackTarget();
            double d0 = this.tank.getDistanceSq(entitylivingbase);

            if (d0 < 4.0D) {
                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    this.tank.attackEntityAsMob(entitylivingbase);
                }

                this.tank.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
                double d1 = entitylivingbase.posX - this.tank.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F) - (this.tank.posY + (double) (this.tank.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.tank.posZ;

                if (this.attackTime <= 0) {
                    ++this.attackStep;

                    if (this.attackStep == 1) {
                        this.attackTime = 60;
                    } else if (this.attackStep <= 4) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 120;
                        this.attackStep = 0;
                        this.tank.setTorchInHand(false);
                    }

                    if (this.attackStep > 1) {
                        this.tank.setTorchInHand(true);
                        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                        this.tank.world.playEvent(null, 1018, new BlockPos((int) this.tank.posX, (int) this.tank.posY, (int) this.tank.posZ), 0);
                        if (this.tank.isFiery()) {
                            for (int i = 0; i < 1; ++i) {
                                EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.tank.world, this.tank, d1 + this.tank.getRNG().nextGaussian() * (double) f, d2, d3 + this.tank.getRNG().nextGaussian() * (double) f);
                                entitysmallfireball.posY = this.tank.posY + (double) (this.tank.height / 2.0F) + 0.5D;
                                this.tank.world.spawnEntity(entitysmallfireball);
                            }
                        } else {
                            for (int i = 0; i < 1; ++i) {
                                float f1 = MathHelper.sin(this.tank.renderYawOffset * 0.017453292F + (float) Math.PI);
                                float f2 = MathHelper.cos(this.tank.renderYawOffset * 0.017453292F);
                                EntityWispfire entityWispfire = new EntityWispfire(this.tank.world, this.tank.posX + (f1 * 1.8D), this.tank.posY + this.tank.getEyeHeight(), this.tank.posZ + (f2 * 1.8D));
                                entityWispfire.shoot(d1 + this.tank.getRNG().nextGaussian() * (double) f, d2, d3 + this.tank.getRNG().nextGaussian() * (double) f, 1.0F, 1.0F);
                                this.tank.world.spawnEntity(entityWispfire);
                            }

                        }
                    }
                }

                this.tank.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            } else {
                this.tank.getNavigator().clearPath();
                this.tank.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }

        private double getFollowDistance()
        {
            IAttributeInstance iattributeinstance = this.tank.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
        }

    }
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(source.getTrueSource() instanceof EntityPlayer && source.getTrueSource().onGround && rand.nextInt(3)==0 && this.blockTicks <= 0){
            this.blockTicks = 20;
            this.world.setEntityState(this, (byte) 7);
            return false;
        }
        if(this.blockTicks > 0 && source.getTrueSource() != null){
            if(rand.nextInt(3) == 0 && this.getDistanceSq(source.getTrueSource()) < 3.5F) {

                this.attackEntityAsMob(source.getTrueSource());
            }
            return false;

        }
        else {
            return super.attackEntityFrom(source, amount);
        }
    }
    public boolean attackEntityAsMob(Entity entityIn)
    {
        this.attackTick = 10;
        this.world.setEntityState(this, (byte)8);
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            entityIn.motionX += (entityIn.posX - this.posX) * 0.3;
            entityIn.motionY += 0.3D;
            entityIn.motionZ += (entityIn.posZ - this.posZ) * 0.3;
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 7) {
            this.blockTicks = 20;
        }
        if (id == 8){
            this.attackTick = 10;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTick(){
        return this.attackTick;
    }

    public boolean isAttacking(){
        return this.attackTick > 0;
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Block", this.blockTicks);
        compound.setInteger("Attack", this.attackTick);
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.blockTicks = compound.getInteger("Block");
        this.attackTick = compound.getInteger("Attack");
    }

    public boolean shouldTradeWithplayer(EntityPlayer player){
        Item stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        boolean b = stack instanceof ItemMask && ((ItemMask) stack).getTier() >=1;
        return super.shouldTradeWithplayer(player) && !this.isBlocking() && !this.isAttacking() && b;
    }
    @Nullable
    @Override
    protected ResourceLocation getBarteringTable()
    {
        return TRADE;
    }

    @Override
    public int getTreasureTier(){
        return 2;
    }

    @Nullable
    @Override
    protected ResourceLocation getTreasureTable() {
        return TREASURE;
    }
}
