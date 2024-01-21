package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import net.minecraft.entity.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityHand extends EntityLiving {
    private EntityLivingBase caster;
    private UUID casterUuid;
    private EntityLivingBase target;
    private UUID targetUUID;
    private BlockPos posTarget;

    public EntityHand(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.moveHelper = new AIMoveControl(this);
        this.isImmuneToFire = true;
        this.stepHeight = 2.0F;
    }

    public EntityHand(World worldIn, EntityLivingBase casterIn, EntityLivingBase targetIn)
    {
        this(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.0F, 1.0F);
        this.setCaster(casterIn);
        this.setTarget(targetIn);
        this.isImmuneToFire = true;
        this.stepHeight = 2.0F;
        this.setPosition(targetIn.posX, targetIn.posY, targetIn.posZ);
        this.moveHelper = new AIMoveControl(this);
    }
    protected void initEntityAI()
    {
    }


    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    public void setCaster(@Nullable EntityLivingBase caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getCaster() {
        if (this.caster == null && this.casterUuid != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.casterUuid);
            if (entity instanceof EntityLivingBase) {
                this.caster = (EntityLivingBase)entity;
            }
        }

        return this.caster;
    }

    public void setTarget(@Nullable EntityLivingBase target) {
        this.target = target;
        this.targetUUID = target == null ? null : target.getUniqueID();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);

        if(!world.isRemote && target != null) {
            target.startRiding(this);
        }
        if(this.ticksExisted > 200){
            this.setDead();
        }
        if(this.getTargetPos() != null){

            double d0 = (this.getTargetPos().getX() - this.posX) * 0.05;
            double d2 = (this.getTargetPos().getY() + 1.0 - this.posY) * 0.05;
            double d1 = (this.getTargetPos().getZ() - this.posZ) * 0.05;
            if(this.getTargetPos().add(0.5, 1.0, 0.5) != this.getPosition()) {
                this.addVelocity(d0, d2, d1);
            }
            this.getLookHelper().setLookPosition(this.getTargetPos().getX(), this.getTargetPos().getY(), this.getTargetPos().getZ(), this.getHorizontalFaceSpeed(), this.getVerticalFaceSpeed());
            if(this.getDistanceSq(this.getTargetPos()) < 24F){
                this.setPosition(this.getTargetPos().getX() + 0.5, this.getTargetPos().getY() + 1.0, this.getTargetPos().getZ() + 0.5);
            }
        }
        if(this.getTargetPos() == null || this.getTargetPos().add(0.5, 1.0, 0.5) == this.getPosition()){
            this.motionX *= 0F;
            this.motionY *= 0F;
            this.motionZ *= 0F;
        }
    }


    public void setTargetPos(@Nullable BlockPos pos){
        this.posTarget = pos;
    }

    public BlockPos getTargetPos(){
        return this.posTarget;
    }
    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasUniqueId("owner")){
            this.casterUuid = compound.getUniqueId("owner");
        }
        if(compound.hasUniqueId("target")){
            this.targetUUID = compound.getUniqueId("target");
        }
        if(compound.hasKey("TposX")){
            double x = compound.getDouble("TposX");
            double y = compound.getDouble("TposY");
            double z = compound.getDouble("TposZ");
            this.setTargetPos(new BlockPos(x, y, z));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        if(this.casterUuid != null){
            compound.setUniqueId("owner", this.casterUuid);
        }
        if(this.targetUUID != null)
        {
            compound.setUniqueId("target", this.targetUUID);
        }
        if(this.posTarget != null){
            compound.setDouble("TposX", this.posTarget.getX() );
            compound.setDouble("TposY", this.posTarget.getY() );
            compound.setDouble("TposZ", this.posTarget.getZ() );
        }

    }

    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public boolean canPassengerSteer() {
        return false;
    }
    @Override
    public boolean canRiderInteract()
    {
        return false;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
    @Override
    public void updatePassenger(Entity entity) {
        super.updatePassenger(entity);
        if(entity instanceof EntityLiving && !world.isRemote){
            EntityLiving entityLiving = (EntityLiving) entity;
            entityLiving.getNavigator().clearPath();
        }

    }

    @Override
    public double getMountedYOffset() {
        return this.height / 2 - 0.4F;
    }


    @Nullable
    public Entity getControllingPassenger()
    {
        return null;
    }
    @Override
    public boolean canBeSteered()
    {
        return false;
    }
}
