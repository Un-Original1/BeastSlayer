package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIAttackRangedStrafe;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityBonepile extends EntityMob implements IRangedAttackMob {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Bonepile");
    private static final DataParameter<Boolean> SPOOK = EntityDataManager.createKey(EntityBonepile.class, DataSerializers.BOOLEAN);
    private int chainTicks;


    public EntityBonepile(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.1F);
    }
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(3, new EntityAIAttackRangedStrafe<>(this, 1.0D, this.world.getDifficulty()== EnumDifficulty.HARD ? 30 : 50, 20.0F));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 6.0F, 1.0D, 1.0D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityIronGolem.class, 8.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    public void onLivingUpdate()
    {
        if (this.world.isDaytime() && !this.world.isRemote)
        {
            float f = this.getBrightness();
            BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(blockpos))
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
        if(this.chainTicks > 0){
            --this.chainTicks;
        }

        super.onLivingUpdate();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalDamageMultiplier);
    }
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SPOOK, false);
    }

    public void setSpook(boolean spook)
    {
        this.getDataManager().set(SPOOK, spook);
    }

    @SideOnly(Side.CLIENT)
    public boolean isSpook()
    {
        return this.getDataManager().get(SPOOK);
    }

    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        this.setSpook(entitylivingbaseIn != null);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (!this.world.isRemote) {
            this.chainTicks = 20;
            this.world.setEntityState(this, (byte)6);
            EntityChained entityarrow = new EntityChained(world, this);
            entityarrow.setMob(true);
            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - entityarrow.posY;
            double d2 = target.posZ - this.posZ;
            double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

            entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.2F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
            this.playSound(ModSounds.KUNAI, 0.8F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(entityarrow);

        }

    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 6)
        {
            this.chainTicks = 20;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    @SideOnly(Side.CLIENT)
    public int getChainTicks(){
        return this.chainTicks;
    }
    @Override
    public void setSwingingArms(boolean swingingArms) {}

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }
    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }


    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected float getSoundPitch()
    {
        return 0.85F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.dimension == 0;
    }
    public double getYOffset() {
        return -0.5D;
    }
}
