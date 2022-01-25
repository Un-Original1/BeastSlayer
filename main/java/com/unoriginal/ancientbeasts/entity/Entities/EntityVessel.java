package com.unoriginal.ancientbeasts.entity.Entities;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.ai.AIMoveControl;
import com.unoriginal.ancientbeasts.entity.Entities.ai.AIMoveRandom;
import com.unoriginal.ancientbeasts.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityVessel extends EntitySpellcasterIllager {
    public static final ResourceLocation LOOT = new ResourceLocation(AncientBeasts.MODID, "entities/Vessel");
    private static final DataParameter<Integer> ENTITY = EntityDataManager.createKey(EntityVessel.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> INACTIVE = EntityDataManager.createKey(EntityVessel.class, DataSerializers.BOOLEAN);
    private float previousYaw = -1F;
    private float previousPitch = -1F;
    private EntityVesselHead targetedEntity;

    public EntityVessel(World world) {
        super(world);
        this.setSize(0.8F, 2.5F);
        this.moveHelper = new AIMoveControl(this);
        this.experienceValue = 30;
    }
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new AIShootHead());
        this.tasks.addTask(4, new AIBallin());
        this.tasks.addTask(5, new AIDuplicate());
        this.tasks.addTask(6, new AISummonGhosts());
        this.tasks.addTask(8, new AIMoveRandom(this));
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D + AncientBeastsConfig.VesselHealthBonus);
    }
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ENTITY, 0);
        this.dataManager.register(INACTIVE, false);
    }
    private void setTargetedEntity(int entityId)
    {
        this.dataManager.set(ENTITY, entityId);
    }

    public boolean hasTargetedEntity()
    {
        return this.dataManager.get(ENTITY) != 0;
    }

    @Nullable
    public EntityVesselHead getTargetedEntity()
    {
        if (!this.hasTargetedEntity())
        {
            return null;
        }
        else if (this.world.isRemote)
        {
            if (this.targetedEntity != null)
            {
                return this.targetedEntity;
            }
            else
            {
                Entity entity = this.world.getEntityByID(this.dataManager.get(ENTITY));

                if (entity instanceof EntityVesselHead)
                {
                    this.targetedEntity = (EntityVesselHead) entity;
                    return this.targetedEntity;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            return this.targetedEntity;
        }
    }

    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
        if(this.getTargetedEntity() != null && this.getTargetedEntity().ticksExisted >= 60 && world.isRemote){
            this.setTargetedEntity(0);
        }

        if(this.isInactive()){
            this.getNavigator().setPath(null, 0);
            this.setNoAI(true);
            this.setSilent(true);
            if(this.previousYaw == -1F) {
                this.previousYaw = this.rotationYaw;
                this.previousPitch = this.rotationPitch;
            }
            this.setRotation(this.previousYaw, this.previousPitch);
        }
        else {
            this.setNoAI(false);
            this.setSilent(false);
            this.previousYaw = -1F;
            this.previousPitch = -1F;
        }
    }

    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(this.world.isDaytime() && !this.world.isRemote){
            this.setInactive(true);
        } else {
            if(!this.world.isRemote) {
                this.setInactive(false);
            }
        }
    }

    @Override
    protected SoundEvent getSpellSound() {
        return null;
    }

    protected boolean canDespawn()
    {
        return false;
    }

    protected int decreaseAirSupply(int air)
    {
        return air;
    }
    public void fall(float distance, float damageMultiplier)
    {
    }
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || this.isInactive())
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }
    class AISummonGhosts extends EntitySpellcasterIllager.AIUseSpell{

        private AISummonGhosts(){

        }
        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            if(world.isDaytime()){
                return false;
            }
            else
            {
                int i = EntityVessel.this.world.getEntitiesWithinAABB(EntityGhost.class, EntityVessel.this.getEntityBoundingBox().grow(16.0D)).size();
                return EntityVessel.this.rand.nextInt(7) + 1 > i;
            }
        }
        @Override
        protected void castSpell() {
            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos = (new BlockPos(EntityVessel.this)).add(-2 + EntityVessel.this.rand.nextInt(5), 1, -2 + EntityVessel.this.rand.nextInt(5));
                EntityGhost ghost = new EntityGhost(EntityVessel.this.world);
                ghost.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                ghost.onInitialSpawn(EntityVessel.this.world.getDifficultyForLocation(blockpos), null);
                EntityVessel.this.world.spawnEntity(ghost);
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 800;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }

        @Override
        protected SpellType getSpellType() {
            return SpellType.SUMMON_VEX;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityVessel.this.setSpellType(SpellType.NONE);
        }
    }
    class AIDuplicate extends EntitySpellcasterIllager.AIUseSpell{

        private AIDuplicate(){

        }
        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            if(EntityVessel.this.getHealth() > (EntityVessel.this.getMaxHealth() / 2F)){
                return false;
            }
            else
            {
                int i = EntityVessel.this.world.getEntitiesWithinAABB(EntityFakeDuplicate.class, EntityVessel.this.getEntityBoundingBox().grow(16.0D)).size();
                return EntityVessel.this.rand.nextInt(12) + 1 > i;
            }
        }
        @Override
        protected void castSpell() {
            for (int i = 0; i < 8; ++i) {
                BlockPos blockpos = (new BlockPos(EntityVessel.this)).add(-2 + EntityVessel.this.rand.nextInt(5), 1, -2 + EntityVessel.this.rand.nextInt(5));
                EntityFakeDuplicate illusion = new EntityFakeDuplicate(EntityVessel.this.world);
                illusion.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                illusion.setCreator(EntityVessel.this);
                illusion.setLimitedLife(400);
                illusion.onInitialSpawn(EntityVessel.this.world.getDifficultyForLocation(blockpos), null);
                EntityVessel.this.world.spawnEntity(illusion);
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 800;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
        }

        @Override
        protected SpellType getSpellType() {
            return SpellType.FANGS;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityVessel.this.setSpellType(SpellType.NONE);
        }
    }

    class AIBallin extends EntitySpellcasterIllager.AIUseSpell{

        private AIBallin(){

        }
        @Override
        protected void castSpell() {
            double d0 = 10D - EntityVessel.this.posX;
            double d1 = EntityVessel.this.posY + EntityVessel.this.height;
            double d2 = 10D - EntityVessel.this.posZ;
            float f = (float) MathHelper.atan2( 10.0F - EntityVessel.this.posZ, 10.0F - EntityVessel.this.posX);
            for (int i = 0; i < 5; ++i) {
                float f2 = f + (float)i * (float)Math.PI * 0.6F + ((float)Math.PI * 0.6F);
                EntityBall ball = new EntityBall(EntityVessel.this.world, EntityVessel.this);
                ball.shoot(d0 + (double)MathHelper.cos(f2) * (i + 1 * 10000D), d1, d2 + (double)MathHelper.sin(f2) * (i + 1 * 10000D), 0.8F, 0.0F);
                ball.setVariant(EntityVessel.this.world.rand.nextInt(4));
                if(!world.isRemote) {
                    EntityVessel.this.world.spawnEntity(ball);
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
            EntityVessel.this.setSpellType(SpellType.NONE);
        }
    }
    class AIShootHead extends EntitySpellcasterIllager.AIUseSpell{
        private AIShootHead(){
        }
        @Override
        protected void castSpell() {
            EntityLivingBase entitylivingbase = EntityVessel.this.getAttackTarget();
            if(entitylivingbase != null) {
                double d1 = entitylivingbase.posX - EntityVessel.this.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F) - (EntityVessel.this.posY + (double) (EntityVessel.this.height / 2.0F));
                double d3 = entitylivingbase.posZ - EntityVessel.this.posZ;
                EntityVesselHead vesselHead = new EntityVesselHead(world, EntityVessel.this, d1, d2, d3);
                vesselHead.setPosition(EntityVessel.this.posX, EntityVessel.this.posY + EntityVessel.this.getEyeHeight(), EntityVessel.this.posZ);
                EntityVessel.this.setTargetedEntity(vesselHead.getEntityId());
                EntityVessel.this.world.spawnEntity(vesselHead);
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 200;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }

        @Override
        protected SpellType getSpellType() {
            return SpellType.SUMMON_VEX;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityVessel.this.setSpellType(SpellType.NONE);
        }
    }
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);

        if (ENTITY.equals(key))
        {
            this.targetedEntity = null;
        }
    }
    public float getEyeHeight()
    {
        return 0.9F;
    }
    public void setInactive(boolean i){
        this.dataManager.set(INACTIVE, i);
    }
    public boolean isInactive(){
        return this.dataManager.get(INACTIVE);
    }
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Inactive", this.isInactive());
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setInactive(compound.getBoolean("Inactive"));
    }
    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.VESSEL_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.VESSEL_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.VESSEL_DEATH; }

    protected float getSoundVolume()
    {
        return 0.5F;
    }
}
