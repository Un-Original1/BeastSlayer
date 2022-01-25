package com.unoriginal.ancientbeasts.entity.Entities;

import com.unoriginal.ancientbeasts.init.ModParticles;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySandSpit extends EntityProjectileGeneric {
    private EntitySandy owner;
    public EntitySandSpit(World worldIn)
    {
        super(worldIn);
    }

    public EntitySandSpit(World worldIn, EntitySandy owner)
    {
        super(worldIn);
        this.owner = owner;
        this.setPosition(owner.posX - (double)(owner.width + 1.0F) * 0.5D, owner.posY + (double)owner.getEyeHeight() - 0.10000000149011612D, owner.posZ + (double)(owner.width + 1.0F) * 0.5D);
        this.setSize(0.25F, 0.25F);
    }

    public void onUpdate()
    {
        super.onUpdate();

        for (int i = 0; i < 7; ++i)
        {
            double d0 = 0.4D + 0.1D * (double)i;
            world.spawnParticle(ModParticles.SAND_SPIT, this.posX, this.posY, this.posZ, motionX * d0, motionY, motionZ * d0);
        }
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null && !(rayTraceResult.entityHit instanceof EntitySandSpit || rayTraceResult.entityHit == this.owner))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 6.0F);
        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntitySandSpit))
        {
            this.setDead();
        }
    }

    protected void entityInit()
    {
    }
}
