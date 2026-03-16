package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Optional;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EntityGhostHolder extends EntityCreature {
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityGhostHolder.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private EntityPlayer owner;


    public EntityGhostHolder(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.9F);
    }


    public EntityGhostHolder(World worldIn, int exp)
    {
        super(worldIn);
        this.setSize(0.6F, 1.9F);
        this.experienceValue = exp;
    }


    @Override
    public void onUpdate() {

        super.onUpdate();

        if(this.ticksExisted > 1200 && !this.world.isRemote) {
            this.setDead();
        }
        if(this.getOwner() != null) {
            this.getLookHelper().setLookPositionWithEntity(this.getOwner(), 30.0F, 30.0F);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 9F, 1d, 2.0d));
    }


    @Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }


    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }


    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if (this.getOwnerId() == null)
        {
            compound.setString("OwnerUUID", "");
        }
        else
        {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("OwnerUUID", 8))
        {
            this.setOwnerId(UUID.fromString(compound.getString("OwnerUUID")));

        }
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public float getBrightness()
    {
        return 1.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.GHOST_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.GHOST_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.GHOST_DEATH; }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(source.getTrueSource() == this.getOwner()) {
            return super.attackEntityFrom(source, amount);
        }
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
