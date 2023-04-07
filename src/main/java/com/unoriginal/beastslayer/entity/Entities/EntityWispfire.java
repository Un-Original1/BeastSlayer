package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.init.ModParticles;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWispfire extends EntityThrowable {

    public EntityWispfire(World worldIn) {
        super(worldIn);
    }
    public EntityWispfire(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityWispfire(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(world.isRemote){
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(ModParticles.WISPFLAME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
       // this.motionY *=0.05;
        if(this.ticksExisted > 200 && !world.isRemote){
            this.setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setMagicDamage(), 2F);
            if (!world.isRemote) {
                if(result.entityHit instanceof EntityLivingBase){
                    EntityLivingBase b = (EntityLivingBase) result.entityHit;
                    b.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 0));
                }
            }


        }
        if (!this.world.isRemote)
        {
            EntityAreaEffectCloud effectCloud = new EntityAreaEffectCloud(world, this.posX, this.posY, this.posZ);
            effectCloud.setParticle(ModParticles.WISPFLAME);
            effectCloud.setRadius(0.6F);
            effectCloud.setDuration(80);
            effectCloud.addEffect(new PotionEffect(MobEffects.POISON, 60, 0));
            this.world.spawnEntity(effectCloud);
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                this.world.spawnParticle(ModParticles.WISPFLAME, this.posX, this.posY, this.posZ, (rand.nextDouble() - 0.5D) * 0.5D, rand.nextDouble() * 0.25D, (rand.nextDouble() - 0.5D) * 0.5D);
            }
        }
    }
    public float getBrightness()
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }
}
