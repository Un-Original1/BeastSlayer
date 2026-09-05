package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityProjectileBubble extends EntityProjectileGeneric
{
	public EntityProjectileBubble(World world)
	{
        super(world);
        this.setSize(0.8F, 0.8F);
    }

    public EntityProjectileBubble (World world, EntityLivingBase owner){
        super(world);
        this.owner = owner;
        this.setPosition(owner.posX - (double)(owner.width + 1.0F) * 0.5D, owner.posY + (double)owner.getEyeHeight() - 0.10000000149011612D, owner.posZ + (double)(owner.width + 1.0F) * 0.5D);
        this.setSize(0.8F, 0.8F);
    }
    public void onUpdate()
    {
        super.onUpdate();

       /* for (int i = 0; i < 2; ++i)
        {
            double d0 = 0.4D + 0.1D * (double)i;
            world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, motionX * d0, motionY, motionZ * d0);
        }*/
        this.motionY *= 0.01D;
      if(this.ticksExisted > 80){
           this.motionY += 0.1D;
      }
      if(this.ticksExisted > 150 && rand.nextInt(5) == 0){
          this.setDead();
      }
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null &&  !(rayTraceResult.entityHit instanceof EntityProjectileGeneric) && !(rayTraceResult.entityHit == this.owner))
        {
           // rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 0.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
            if(rayTraceResult.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase b = (EntityLivingBase) rayTraceResult.entityHit;
                b.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 80, 0));
                float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                if (f1 > 0.0F)
                {
                    b.addVelocity(this.motionX * 2 * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * 2 * 0.6000000238418579D / (double)f1);
                    b.velocityChanged = true;
                }

            }

        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric))
        {
            this.setDead();
        }
    }
}
