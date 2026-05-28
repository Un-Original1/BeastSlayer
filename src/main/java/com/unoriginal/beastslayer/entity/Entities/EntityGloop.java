package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.animation.EZAnimation;
import com.unoriginal.beastslayer.animation.EZAnimationHandler;
import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityGloop extends EntityAnimal implements IAnimatedEntity {
    public static final EZAnimation ANIMATION_WALK = EZAnimation.create(20);
    private EZAnimation currAnimation;
    private int animTick;
    private int walkticks;
    private static final DataParameter<Integer> BALLOON = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BUBBLE = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private boolean wasMoving = false;

    public EntityGloop(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
        this.walkticks = -1;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1D));
        this.tasks.addTask(4, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.rotationYawHead = this.rotationYaw;
        EZAnimationHandler.INSTANCE.updateAnimations(this);

        if(this.getBalloon() > 0 ){
            this.setBalloon(this.getBalloon() - 1);
        }

     /*   if (this.motionX * this.motionX + this.motionZ * this.motionZ <= 2.500000277905201E-7D){
            this.setAnimation(NO_ANIMATION);
        }*/
        if(this.isBeingRidden() && this.getBalloon() <= 0 && !this.world.isRemote){
            this.removePassengers();
        }

        double dx = this.posX - this.prevPosX;
        double dz = this.posZ - this.prevPosZ;

        double speedSq = dx * dx + dz * dz;

        boolean moving = speedSq > 1.0E-5;

        if (moving && !this.wasMoving) {
            this.walkticks = this.ticksExisted;
            this.setAnimation(ANIMATION_WALK);
            EZAnimationHandler.INSTANCE.sendAnimationMessage(this, ANIMATION_WALK);
        }

        if(moving && this.walkticks != -1 && (this.ticksExisted - this.walkticks) % 20 == 0) {
            this.setAnimation(ANIMATION_WALK);
            EZAnimationHandler.INSTANCE.sendAnimationMessage(this, ANIMATION_WALK);
        }

        if (!moving && this.wasMoving) {
            this.walkticks = -1;
           this.setAnimation(NO_ANIMATION);
        }

        this.wasMoving = moving;
    }



    @Override
    public void travel(float strafe, float vertical, float forward) {
        super.travel(strafe, vertical, forward);

       /* if (this.ticksExisted % 20 == 0) {
            this.setAnimation(ANIMATION_WALK);
            EZAnimationHandler.INSTANCE.sendAnimationMessage(this, ANIMATION_WALK);
        }*/
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BALLOON, 0);
        this.dataManager.register(BUBBLE, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("balloon", this.getBalloon());
        compound.setInteger("walkticks", this.walkticks);
        compound.setBoolean("wasMoving", this.wasMoving);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setBalloon(compound.getInteger("balloon"));
        this.walkticks = compound.getInteger("walkticks");
        this.wasMoving = compound.getBoolean("wasMoving");
    }

    public void setBalloon(int balloon) {
        this.dataManager.set(BALLOON, balloon);
    }
    public int getBalloon() {
        return this.dataManager.get(BALLOON);
    }
    @SideOnly(Side.CLIENT)
    public int getBalloonClient() {
        return this.dataManager.get(BALLOON);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityGloop(this.world);
    }

    @Override
    public float getEyeHeight() {
        return this.isChild()? 0.15F : 0.3F;
    }

    @Override
    public int getAnimationTick() {
        return this.animTick;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!this.isChild() && !this.isBeingRidden() && !this.world.isRemote) {
            this.setBalloon(800);
            player.startRiding(this);
            player.setActiveHand(hand);
            return true;
        }

        return super.processInteract(player, hand);

    }

    public boolean shouldRiderSit()
    {
        return false;
    }

    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }


    @Override
    protected boolean canTriggerWalking() {
        return this.getBalloon() <= 0 && super.canTriggerWalking();
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animTick = tick;
    }

    @Override
    public EZAnimation getAnimation() {
        return this.currAnimation;
    }

    @Override
    public void setAnimation(EZAnimation animation) {
        this.currAnimation = animation;
    }

    @Override
    public EZAnimation[] getAnimations() {
        return new EZAnimation[]{ANIMATION_WALK};
    }
}
