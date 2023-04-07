package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveRandom;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityFakeDuplicate extends EntitySpellcasterIllager {
    private int lifeTicks;
    private boolean limitedLifespan;
    private EntityVessel creator;
    private UUID creatorUuid;
    public EntityFakeDuplicate(World world) {
        super(world);
        this.setSize(0.8F, 2.5F);
        this.moveHelper = new AIMoveControl(this);
        this.experienceValue = 0;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new AIBallin());
        this.tasks.addTask(6, new AIMoveRandom(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 1.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMob.class));
        this.targetTasks.addTask(2, (new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true)).setUnseenMemoryTicks(100));
        this.targetTasks.addTask(3, (new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false)).setUnseenMemoryTicks(100));
        this.targetTasks.addTask(2, (new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, false)));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
        if(this.limitedLifespan && --this.lifeTicks <= 0  && !this.world.isRemote){
            this.attackEntityFrom(DamageSource.MAGIC, 1.0F);

        }
        if((this.getCreator() == null && this.creatorUuid == null) || this.world.isDaytime() && !this.world.isRemote){
            this.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }
    }
    public void fall(float distance, float damageMultiplier)
    {
    }
    @Override
    protected SoundEvent getSpellSound() {
        return null;
    }
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        if (this.limitedLifespan) {
            compound.setInteger("LifeTicks", this.lifeTicks);
        }
        if(this.creatorUuid != null){
            compound.setUniqueId("creator", this.creatorUuid);
        }
    }
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("LifeTicks"))
        {
            this.setLimitedLife(compound.getInteger("LifeTicks"));
        }
        if (compound.hasUniqueId("creator")){
            this.creatorUuid = compound.getUniqueId("creator");
        }
    }
    public void setLimitedLife(int limitedLifeTicksIn)
    {
        this.limitedLifespan = true;
        this.lifeTicks = limitedLifeTicksIn;
    }
    class AIBallin extends EntitySpellcasterIllager.AIUseSpell{

        private AIBallin(){

        }
        @Override
        protected void castSpell() {
            double d0 = 10D - EntityFakeDuplicate.this.posX;
            double d1 = EntityFakeDuplicate.this.posY + EntityFakeDuplicate.this.height;
            double d2 = 10D - EntityFakeDuplicate.this.posZ;
            float f = (float) MathHelper.atan2( 10.0F - EntityFakeDuplicate.this.posZ, 10.0F - EntityFakeDuplicate.this.posX);
            for (int i = 0; i < 3; ++i) {
                float f2 = f + (float)i * (float)Math.PI * 0.4F + ((float)Math.PI * 0.4F);
                EntityBall ball = new EntityBall(EntityFakeDuplicate.this.world, EntityFakeDuplicate.this);
                ball.shoot(d0 + (double)MathHelper.cos(f2) * (i + 1 * 10000D), d1, d2 + (double)MathHelper.sin(f2) * (i + 1 * 10000D), 0.8F, 0.0F);
                ball.setVariant(EntityFakeDuplicate.this.world.rand.nextInt(4));
                if(!world.isRemote) {
                    EntityFakeDuplicate.this.world.spawnEntity(ball);
                }
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 400;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
        }

        @Override
        protected SpellType getSpellType() {
            return SpellType.WOLOLO;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityFakeDuplicate.this.setSpellType(SpellType.NONE);
        }
    }
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.VESSEL_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.VESSEL_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.VESSEL_DEATH; }
    public EntityVessel getCreator(){
        if(this.creatorUuid != null && this.creator == null && this.world instanceof WorldServer){
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.creatorUuid);
            if(entity instanceof EntityVessel){
                this.creator = (EntityVessel) entity;
            }
        }
        return this.creator;
    }
    public void setCreator(EntityVessel vessel){
        this.creator = vessel;
        this.creatorUuid = vessel == null ? null : vessel.getUniqueID();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(!this.isEntityInvulnerable(source)) {
            if((this.lifeTicks <= 0 || this.getHealth() == 0) && this.ticksExisted % 30 == 0) {
                for (int i = 0; i < 3; ++i) {
                    double d0 = 10D - EntityFakeDuplicate.this.posX;
                    double d1 = EntityFakeDuplicate.this.posY + EntityFakeDuplicate.this.height;
                    double d2 = 10D - EntityFakeDuplicate.this.posZ;
                    float f = (float) MathHelper.atan2(10.0F - EntityFakeDuplicate.this.posZ, 10.0F - EntityFakeDuplicate.this.posX);
                    float f2 = f + (float) i * (float) Math.PI * 0.4F + ((float) Math.PI * 0.4F);
                    EntityBall ball = new EntityBall(EntityFakeDuplicate.this.world, EntityFakeDuplicate.this);
                    ball.shoot(d0 + (double) MathHelper.cos(f2) * (i + 1 * 10000D), d1, d2 + (double) MathHelper.sin(f2) * (i + 1 * 10000D), 0.8F, 0.0F);
                    ball.setVariant(EntityFakeDuplicate.this.world.rand.nextInt(4));
                    if (!world.isRemote) {
                        EntityFakeDuplicate.this.world.spawnEntity(ball);
                    }
                }
            }
        }
        return super.attackEntityFrom(source, amount);


    }
}
