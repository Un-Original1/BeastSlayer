package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDamcellSpike extends EntityProjectileGeneric {
    public EntityDamcellSpike(World worldIn) {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }
    public EntityDamcellSpike(World worldIn, EntityLivingBase owner)
    {
        super(worldIn);
        this.owner = owner;
        this.setPosition(owner.posX, owner.posY + (double)owner.getEyeHeight() - 0.10000000149011612D, owner.posZ);
        this.setSize(0.25F, 0.25F);
    }

    public void onUpdate()
    {
        super.onUpdate();

    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null &&  !(rayTraceResult.entityHit instanceof EntityProjectileGeneric) && !(rayTraceResult.entityHit == this.owner))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile().setDamageBypassesArmor(), 6.0F  * (float) BeastSlayerConfig.GlobalDamageDamcellMultiplier);
        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric) && !(rayTraceResult.entityHit == this.owner))
        {
            this.setDead();
        }
    }
}
