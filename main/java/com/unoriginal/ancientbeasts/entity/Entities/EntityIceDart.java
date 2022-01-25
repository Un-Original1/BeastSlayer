package com.unoriginal.ancientbeasts.entity.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityIceDart extends EntityProjectileGeneric{
    public boolean isRedIce;
    private static final DataParameter<Boolean> RED = EntityDataManager.createKey(EntityIceDart.class, DataSerializers.BOOLEAN);
    private EntityLivingBase owner;
    public EntityIceDart(World worldIn)
    {
        super(worldIn);
    }

    public EntityIceDart(World worldIn, EntityLivingBase owner, boolean isRedIceIn)
    {
        super(worldIn);
        this.owner = owner;
        this.isRedIce = isRedIceIn;
        this.setPosition(owner.posX - (double)(owner.width + 1.0F) * 0.5D, owner.posY + (double)owner.getEyeHeight() - 0.10000000149011612D, owner.posZ + (double)(owner.width + 1.0F) * 0.5D);
        this.setSize(0.25F, 0.25F);
    }

    public void onUpdate()
    {
        super.onUpdate();

        for (int i = 0; i < 2; ++i)
        {
            double d0 = 0.4D + 0.1D * (double)i;
            world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, motionX * d0, motionY, motionZ * d0);
        }
        if(this.isRedIce){
            this.setRed(true);
        }
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric || rayTraceResult.entityHit == this.owner))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
            if(rayTraceResult.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase b = (EntityLivingBase) rayTraceResult.entityHit;
                if(this.isRedIce){
                    if(!(b instanceof EntityNetherhound)) {
                        b.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 4));
                    }
                    b.addPotionEffect(new PotionEffect(MobEffects.WITHER, 80, 3));
                }
                else {
                    if(!(b instanceof EntityNetherhound)) {
                        b.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 4));
                    }
                }
            }

        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric))
        {
            this.setDead();
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(RED, false);
    }
    public boolean isRed(){
        return dataManager.get(RED);
    }
    public void setRed(boolean red){
        dataManager.set(RED, red);
    }
}
