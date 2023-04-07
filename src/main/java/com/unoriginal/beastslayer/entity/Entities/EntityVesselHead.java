package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVesselHead extends EntityFireball {

    public EntityVesselHead(World worldIn)
    {
        super(worldIn);
        this.setSize(0.75F, 0.75F);
    }

    public EntityVesselHead(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.75F, 0.75F);
    }

    @SideOnly(Side.CLIENT)
    public EntityVesselHead(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.75F, 0.75F);
    }
    protected float getMotionFactor(){return 0.9F;}

    public boolean isBurning()
    {
        return false;
    }

    public void onUpdate(){
        super.onUpdate();
        if(this.ticksExisted >= 60 && !this.world.isRemote){
            this.setDead();
        }
    }

    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                if (this.shootingEntity != null)
                {
                    if (result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F  * (float) BeastSlayerConfig.GlobalDamageMultiplier))
                    {
                        if (result.entityHit.isEntityAlive())
                        {
                            this.applyEnchantments(this.shootingEntity, result.entityHit);
                        }
                        else
                        {
                            this.shootingEntity.heal(5.0F);
                        }
                    }
                }
                else if(!(result.entityHit instanceof EntityVessel)) {
                    result.entityHit.attackEntityFrom(DamageSource.MAGIC, 6.0F);
                    this.setDead();
                }

            }

        }
    }

    public boolean canBeCollidedWith()
    {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    protected boolean isFireballFiery()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

        if (this.world.isBlockLoaded(blockpos$mutableblockpos))
        {
            blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double)this.getEyeHeight()));
            return this.world.getCombinedLight(blockpos$mutableblockpos, 0);
        }
        else
        {
            return 0;
        }
    }

    @Override
    public float getBrightness()
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

        if (this.world.isBlockLoaded(blockpos$mutableblockpos))
        {
            blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double)this.getEyeHeight()));
            return this.world.getLightBrightness(blockpos$mutableblockpos);
        }
        else
        {
            return 0.0F;
        }
    }
}
