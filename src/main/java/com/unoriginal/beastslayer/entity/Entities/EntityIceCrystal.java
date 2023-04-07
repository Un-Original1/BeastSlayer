package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityIceCrystal extends Entity {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks;
    private boolean isAboutToVanish;
    private EntityLivingBase caster;
    private UUID casterUuid;
    public boolean isRedIce;
    private static final DataParameter<Boolean> RED = EntityDataManager.createKey(EntityIceCrystal.class, DataSerializers.BOOLEAN);

    public EntityIceCrystal(World worldIn)
    {
        super(worldIn);
        this.lifeTicks = 22;
        this.setSize(0.8F, 1.4F);
    }

    public EntityIceCrystal(World worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, EntityLivingBase casterIn, boolean isRedIce)
    {
        this(worldIn);
        this.warmupDelayTicks = p_i47276_9_;
        this.setCaster(casterIn);
        this.rotationYaw = p_i47276_8_ * (180F / (float)Math.PI);
        this.setPosition(x, y, z);
        this.isRedIce = isRedIce;
    }

    protected void entityInit() {
        dataManager.register(RED, false);
    }

    public void setCaster(@Nullable EntityLivingBase p_190549_1_)
    {
        this.caster = p_190549_1_;
        this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getCaster()
    {
        if (this.caster == null && this.casterUuid != null && this.world instanceof WorldServer)
        {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.casterUuid);

            if (entity instanceof EntityLivingBase)
            {
                this.caster = (EntityLivingBase)entity;
            }
        }

        return this.caster;
    }

    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.warmupDelayTicks = compound.getInteger("Warmup");
        this.casterUuid = compound.getUniqueId("OwnerUUID");
    }

    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Warmup", this.warmupDelayTicks);

        if (this.casterUuid != null)
        {
            compound.setUniqueId("OwnerUUID", this.casterUuid);
        }
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            if (this.isAboutToVanish)
            {
                --this.lifeTicks;
            }
        }
        else if (--this.warmupDelayTicks < 0)
        {
            if (this.warmupDelayTicks == -8)
            {
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(0.2D, 0.0D, 0.2D)))
                {
                    this.damage(entitylivingbase);
                }
            }

            if (!this.sentSpikeEvent)
            {
                this.world.setEntityState(this, (byte)4);
                this.sentSpikeEvent = true;
            }

            if (--this.lifeTicks < 0 && !this.world.isRemote)
            {
                this.setDead();
            }
        }
        if(this.isRedIce){
            this.setRed(true);
        }
        if(this.isInWater() && !this.world.isRemote){
            this.setDead();
        }
    }

    private void damage(EntityLivingBase livingBase)
    {
        EntityLivingBase caster = this.getCaster();

        if (livingBase.isEntityAlive() && !livingBase.getIsInvulnerable() && livingBase != caster)
        {
            if (caster == null)
            {
                livingBase.attackEntityFrom(DamageSource.MAGIC, 4.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
            }
            else
            {
                if (caster.isOnSameTeam(livingBase))
                {
                    return;
                }

                livingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), 4.0F);
            }
            if(!(livingBase instanceof EntityNetherhound)) {
                livingBase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 280, 8));
            }
            if(this.isRed())
            {
                livingBase.addPotionEffect(new PotionEffect(MobEffects.WITHER, 80, 2));
            }
        }
    }
    public boolean isRed(){
        return dataManager.get(RED);
    }
    public void setRed(boolean red){
        dataManager.set(RED, red);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        super.handleStatusUpdate(id);

        if (id == 4)
        {
            this.isAboutToVanish = true;

            if (!this.isSilent())
            {
                this.world.playSound(this.posX, this.posY, this.posZ, ModSounds.ICE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.2F + 0.85F, false);
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public float getAnimationProgress(float partialTicks)
    {
        if (!this.isAboutToVanish)
        {
            return 0.0F;
        }
        else
        {
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - partialTicks) / 20.0F;
        }
    }
}
