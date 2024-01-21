package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMeleeConditional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntitySpiritWolf extends EntityAnimal {
    private EntityLivingBase caster;
    private UUID casterUuid;
    private int stalkTicks;

    public EntitySpiritWolf(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.6F);
    }
    public EntitySpiritWolf(World worldIn, EntityLivingBase caster) {
        super(worldIn);
        this.setSize(1.5F, 1.6F);
        this.setCaster(caster);
    }
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIMeleeConditional(this, 1.3D, false, Predicate ->  this.stalkTicks  <= 0));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, Predicate -> this.stalkTicks > 0,2F, 0.75D, 0.75D));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(26.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
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

    public void setCaster(@Nullable EntityLivingBase caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUniqueID();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.stalkTicks > 0){
            --this.stalkTicks;
        }

        if(this.getAttackTarget() != null && this.canEntityBeSeen(this.getAttackTarget())){
            this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), this.getHorizontalFaceSpeed(), this.getVerticalFaceSpeed());
        }

        if(this.ticksExisted > 600){
            this.setHealth(0.0F);
        }

    }

    @Nullable
    public EntityLivingBase getCaster() {
        if (this.caster == null && this.casterUuid != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.casterUuid);
            if (entity instanceof EntityLivingBase) {
                this.caster = (EntityLivingBase)entity;
            }
        }

        return this.caster;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        this.stalkTicks = 200;
        this.world.setEntityState(this,(byte)8);
        super.setAttackTarget(entitylivingbaseIn);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasUniqueId("owner")){
            this.casterUuid = compound.getUniqueId("owner");
        }
        if(compound.hasKey("Stalk")){
            this.stalkTicks = compound.getInteger("Stalk");
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag =entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag && entityIn instanceof EntityLivingBase)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100 * (int) f, 1));

            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 80, 0));

        }

        return flag;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 8) {
            this.stalkTicks = 200;
        }

        else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getStalkTicks(){
        return this.stalkTicks;
    }

    public void setStalkTicks(int ticks){
        this.stalkTicks = ticks;
        world.setEntityState(this, (byte)8);

    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        if(this.casterUuid != null){
            compound.setUniqueId("owner", this.casterUuid);
        }
        compound.setInteger("Stalk", this.stalkTicks);
    }
}
