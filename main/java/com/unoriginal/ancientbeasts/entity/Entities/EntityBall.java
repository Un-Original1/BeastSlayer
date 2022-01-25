package com.unoriginal.ancientbeasts.entity.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBall extends EntityThrowable {
    private double newX;
    private double newY;
    private double newZ;
    private boolean bouncing;
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityBall.class, DataSerializers.VARINT);
    public EntityBall(World worldIn) {
        super(worldIn);
        this.setSize(0.375F, 0.375F);
    }

    public EntityBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setSize(0.375F, 0.375F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.typeOfHit;
        if (raytraceresult$type == RayTraceResult.Type.BLOCK)
        {
            bouncing = true;
            this.newX = this.motionX;
            this.newY = this.motionY;
            this.newZ = this.motionZ;

            switch (result.sideHit.getAxis()) {
                case X:
                    this.newX = -this.newX;
                    break;
                case Y:
                    this.newY = -this.newY;
                    break;
                case Z:
                    this.newZ = -this.newZ;
                    break;
            }
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;

            this.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 1.0F);

            if (this.ticksExisted > 200)
            {
                this.setDead();
            }
        }

        if (result.entityHit != null && result.entityHit != this.thrower && !(result.entityHit instanceof EntityBall) && !(result.entityHit instanceof EntityFakeDuplicate) && !(result.entityHit instanceof EntityVessel))
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.0F);
            this.setDead();
        }
    }
    public void onUpdate()
    {
        if(bouncing){
            bouncing = false;
            this.motionX = this.newX;
            this.motionY = this.newY;
            this.motionZ = this.newZ;
        }
        super.onUpdate();
        if(this.isInWater()){
            this.setDead();
        }
        for (int i = 0; i < 1; ++i)
        {
            double d0 = 0.4D + 0.1D * (double)i;
            world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX, this.posY, this.posZ, motionX * d0, motionY, motionZ * d0);
        }
    }
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 3);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }
}
