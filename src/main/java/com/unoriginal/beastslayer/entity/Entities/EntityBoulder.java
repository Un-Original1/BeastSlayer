package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBoulder extends EntityProjectileGeneric {

    public EntityBoulder(World worldIn)
    {
        super(worldIn);
    }

    public EntityBoulder(World worldIn, EntityLivingBase owner)
    {
        super(worldIn);
        this.owner = owner;
        this.setPosition(owner.posX - (double)(owner.width + 1.0F) * 0.5D, owner.posY + owner.height + 4.0D, owner.posZ + (double)(owner.width + 1.0F) * 0.5D);
        this.setSize(2.2F, 2.2F);
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null &&  !(rayTraceResult.entityHit instanceof EntityProjectileGeneric))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 18.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);

        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityBoulder))
        {
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
            this.setDead();
        }
        if(this.world.isRemote){
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0D, 0D, 0D);
        }
    }

    protected void entityInit()
    {
        super.entityInit();
    }
}
