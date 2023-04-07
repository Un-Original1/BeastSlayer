package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.init.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityRiftedPearl extends EntityThrowable {
    private EntityLivingBase perlThrower;

    public EntityRiftedPearl(World worldIn) {
        super(worldIn);
    }

    public EntityRiftedPearl(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.perlThrower = throwerIn;
    }

    @SideOnly(Side.CLIENT)
    public EntityRiftedPearl(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    protected void onImpact(RayTraceResult result) {
        EntityLivingBase entitylivingbase = this.getThrower();

        List<EntityItem> itemlist = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(64D));
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(64D));

        if (result.entityHit != null) {
            if (result.entityHit == this.perlThrower) {
                return;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 0.0F);
        }

        for (int i = 0; i < 32; ++i) {
            this.world.spawnParticle(ModParticles.RIFT, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        if (!this.world.isRemote) {

            if(!itemlist.isEmpty() && rand.nextInt(2) == 1 ){
               for (int i = 0; i < itemlist.size() / 2; i++) {
                   EntityItem item = itemlist.get(this.world.rand.nextInt(itemlist.size()));
                   item.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                   item.fallDistance = 0.0F;
               }
                this.setDead();
            }
            else {
                if(!list.isEmpty()) {
                    EntityLivingBase teleported = list.get(this.world.rand.nextInt(list.size()));
                    if (teleported instanceof EntityPlayerMP) {
                        EntityPlayerMP entityplayermp = (EntityPlayerMP) teleported;

                        if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping()) {
                            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
                            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                                if (teleported.isRiding()) {
                                    teleported.dismountRidingEntity();
                                }

                                teleported.setPositionAndUpdate(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                                teleported.fallDistance = 0.0F;
                                teleported.attackEntityFrom(DamageSource.FALL, 1.0F);
                            }
                        }
                    } else if (teleported != null) {
                        teleported.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                        teleported.fallDistance = 0.0F;
                    }

                    this.setDead();
                }
            }
        }
    }

    public void onUpdate() {
        EntityLivingBase entitylivingbase = this.getThrower();

        if (entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) {
            this.setDead();
        } else {
            super.onUpdate();
        }
    }

    @Nullable
    public Entity changeDimension(int dimensionIn, net.minecraftforge.common.util.ITeleporter teleporter) {
        if (this.thrower.dimension != dimensionIn) {
            this.thrower = null;
        }

        return super.changeDimension(dimensionIn, teleporter);
    }
}
