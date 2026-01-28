package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityCharmChain extends EntityProjectileFireball2{
    private EntityLivingBase owner;
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(EntityChained.class, DataSerializers.VARINT);

    public EntityCharmChain(World worldIn) {
        super(worldIn);
        this.setSize(0.98F, 0.3F);
    }

    public EntityCharmChain(World worldIn, EntityLivingBase owner) {
        super(worldIn);
        this.setSize(0.98F, 0.3F);

        this.owner = owner;

    }
    public EntityCharmChain(World worldIn, EntityLivingBase owner, float accX, float accY, float accZ) {
        super(worldIn,owner, accX, accY, accZ);
        this.setSize(0.98F, 0.3F);
        this.owner = owner;
        this.setBuffedEntity(this.owner.getEntityId());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(OWNER, 0);
    }

    public void onUpdate()
    {
        super.onUpdate();

        if(this.ticksExisted > 100){
            this.setDead();
        }

    }

    public void onImpact(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null &&  !(rayTraceResult.entityHit instanceof EntityProjectileGeneric) && !(rayTraceResult.entityHit == this.owner))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
            if(rayTraceResult.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase b = (EntityLivingBase) rayTraceResult.entityHit;
                if(!b.isPotionActive(ModPotions.CHARMED)){
                b.addPotionEffect(new PotionEffect(ModPotions.CHARMED, 400, 0));

                } else {
                    if(b.isPotionActive(ModPotions.CHARMED)) {
                        int duration = b.getActivePotionEffect(ModPotions.CHARMED).getDuration();
                        b.removePotionEffect(ModPotions.CHARMED);
                        b.addPotionEffect(new PotionEffect(ModPotions.CHARMED, duration + 200, 1));
                    }
                }
            }

        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric))
        {
            this.setDead();
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

    @Nullable
    public EntityLivingBase getBuffedEntity()
    {
        if (!this.hasBuffedEntity())
        {
            return null;
        }
        else if(this.world.isRemote){
            Entity entity = this.world.getEntityByID(this.dataManager.get(OWNER));

            if (entity instanceof EntityLivingBase)
            {
                this.owner = (EntityLivingBase) entity;
                return this.owner;
            }
            else
            {
                return null;
            }
        }
        else {
            return owner;
        }
    }

    public boolean hasBuffedEntity()
    {
        return this.dataManager.get(OWNER) != 0;
    }

    private void setBuffedEntity(int entityId)
    {
        this.dataManager.set(OWNER, entityId);
        Entity mob = this.world.getEntityByID(entityId);
        if(mob instanceof EntityLivingBase) {
            this.owner = (EntityLivingBase) mob;
        }
    }
}
