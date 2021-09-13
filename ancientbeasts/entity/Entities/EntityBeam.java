package com.unoriginal.ancientbeasts.entity.Entities;

import net.minecraft.entity.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.UUID;

public class EntityBeam extends Entity {
    private static final DataParameter<Integer> BUFFED_ENTITY = EntityDataManager.createKey(EntityBeam.class, DataSerializers.VARINT);
    public EntityZealot buffedEntity;
    private int lifeticks;
    private EntityLivingBase caster;
    private UUID casterUuid;
    private EntityLivingBase target;
    private UUID targetUUID;
    private final double heightAdjustment = (1.0F - this.height) / 2.0F;

    public EntityBeam(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 1.4F);
    }

    public EntityBeam(World worldIn, EntityLivingBase casterIn, EntityLivingBase targetIn)
    {
        this(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(0.8F, 1.4F);
        this.setCaster(casterIn);
        this.setTarget(targetIn);
        this.setPosition(targetIn.posX, targetIn.posY + targetIn.height / 2 + heightAdjustment, targetIn.posZ);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = targetIn.posX;
        this.prevPosY = targetIn.posY + 1.0D + targetIn.height / 2 + heightAdjustment;
        this.prevPosZ = targetIn.posZ;
        this.lifeticks = 200;
    }

    protected void entityInit() {
        this.dataManager.register(BUFFED_ENTITY, 0);
    }

    private void setBuffedEntity(int entityId)
    {
        this.dataManager.set(BUFFED_ENTITY, entityId);
        Entity mob = this.world.getEntityByID(entityId);
        if(mob instanceof EntityZealot) {
            this.buffedEntity = (EntityZealot) mob;
        }
    }

    public boolean hasBuffedEntity()
    {
        return this.dataManager.get(BUFFED_ENTITY) != 0;
    }

    @Nullable
    public EntityZealot getBuffedEntity()
    {
        if (!this.hasBuffedEntity())
        {
            return null;
        }
        else if(this.world.isRemote){
            Entity entity = this.world.getEntityByID(this.dataManager.get(BUFFED_ENTITY));

            if (entity instanceof EntityZealot)
            {
                this.buffedEntity = (EntityZealot) entity;
                return this.buffedEntity;
            }
            else
            {
                return null;
            }
        }
        else {
            return buffedEntity;
        }
    }

    private void tryMoveWithTarget(EntityLivingBase targetIn) {
        this.setPosition(targetIn.posX, targetIn.posY + targetIn.height / 2 + heightAdjustment, targetIn.posZ);
    }

    public void setCaster(@Nullable EntityLivingBase caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUniqueID();
    }

    public void setTarget(@Nullable EntityLivingBase target) {
        this.target = target;
        this.targetUUID = target == null ? null : target.getUniqueID();
    }

    @Override
    public void onUpdate() {
        if(this.target != null && !this.world.isRemote)
        {
            this.tryMoveWithTarget(this.target);
        }
        if(this.caster != null){
            this.setBuffedEntity(caster.getEntityId());
        }
        if(this.target == null || this.caster == null || this.target.getHealth() <= 0F)
        {
            if(!this.world.isRemote)
            this.setDead();
        }
        if(lifeticks > 0)
        {
            --lifeticks;
        } else {
            if(!this.world.isRemote)
            this.setDead();
        }
    }
    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasUniqueId("owner")){
            this.casterUuid = compound.getUniqueId("owner");
        }
        if(compound.hasUniqueId("target")){
            this.targetUUID = compound.getUniqueId("target");
        }
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        if(this.casterUuid != null){
            compound.setUniqueId("owner", this.casterUuid);
        }
        if(this.targetUUID != null)
        {
            compound.setUniqueId("target", this.targetUUID);
        }

    }
    public boolean canRenderOnFire() {
        return false;
    }
}
