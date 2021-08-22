package com.unoriginal.ancientbeasts.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntitySandShooter extends Entity {
    private EntityLivingBase caster;
    private boolean didShoot;
    private UUID casterUuid;

    public EntitySandShooter(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.8F);
    }
    public EntitySandShooter(World worldIn, EntityLivingBase casterIn) {
        super(worldIn);
        this.setCaster(casterIn);
        this.setSize(1.0F, 1.8F);
    }
    public void onUpdate()
    {
        super.onUpdate();
        if(!this.didShoot && !this.world.isRemote) {
            this.shoot();
            this.spawnExplosionCloud();
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.8F, 1.0F);
        }
        if(this.didShoot && !this.world.isRemote){
            this.setDead();
        }
    }
    public void setCaster(@Nullable EntityLivingBase entityLivingBase)
    {
        this.caster = entityLivingBase;
        this.casterUuid = entityLivingBase == null ? null : entityLivingBase.getUniqueID();
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

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.casterUuid = compound.getUniqueId("OwnerUUID");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        if (this.casterUuid != null)
        {
            compound.setUniqueId("OwnerUUID", this.casterUuid);
        }
    }

    @Override
    protected void entityInit() {

    }
    private void shoot()
    {
        double d0 = 10D - this.posX;
        double d1 = this.posY;
        double d2 = 10D - this.posZ;

        float f = (float) MathHelper.atan2( 10.0F - this.posZ, 10.0F - this.posX);
        for (int k = 0; k < 8; ++k){
            float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
            EntitySandSpit entitySandSpit = new EntitySandSpit(this.world, this);
            entitySandSpit.shoot(d0 + (double)MathHelper.cos(f2) * (k + 1 * 10000D), d1, d2 + (double)MathHelper.sin(f2) * (k + 1 * 10000D), 1.2F, 0.0F);
            this.world.spawnEntity(entitySandSpit);
        }

        this.didShoot = true;
    }
    public void spawnExplosionCloud(){
        EntityAreaEffectCloud areaeffectcloudentity = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
        areaeffectcloudentity.setParticle(EnumParticleTypes.EXPLOSION_LARGE);
        areaeffectcloudentity.setRadius(2.0F);
        areaeffectcloudentity.setDuration(0);
        this.world.spawnEntity(areaeffectcloudentity);
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
}
