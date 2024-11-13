package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModPotions;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityZealot extends EntitySpellcasterIllager {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Zealot");

    public EntityZealot(World world) {
        super(world);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 10;
    }
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(1, new AICastingSpell());
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 0.8D));
        this.tasks.addTask(4, new AIBuffSpell());
        this.tasks.addTask(6, new AIWeakeningSpell());
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 0.6D, 1.0D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMob.class));
        this.targetTasks.addTask(2, (new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true)).setUnseenMemoryTicks(100));
        this.targetTasks.addTask(3, (new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false)).setUnseenMemoryTicks(100));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, false));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0 + BeastSlayerConfig.GlobalArmor);
    }
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected SoundEvent getSpellSound() {
        return null;
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    public void onLivingUpdate()
    {
        if (this.world.isDaytime() && !this.world.isRemote)
        {
            float f = this.getBrightness();
            BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(blockpos))
            {
                boolean flag = true;
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.setFire(8);
                }
            }
        }
        if(this.getHealth() <= 0)
        {
            List<EntityMob> mobs = EntityZealot.this.world.getEntitiesWithinAABB(EntityMob.class, EntityZealot.this.getEntityBoundingBox().grow(15D));
            if(!world.isRemote){
                for(EntityMob mob : mobs)
                {
                    mob.removeActivePotionEffect(ModPotions.SHIELDED);
                }
            }
        }
        super.onLivingUpdate();
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    class AICastingSpell extends AICastingApell
    {
        private AICastingSpell()
        {
        }
        public void updateTask()
        {
            if (EntityZealot.this.getAttackTarget() != null)
            {
                EntityZealot.this.getLookHelper().setLookPositionWithEntity(EntityZealot.this.getAttackTarget(), (float)EntityZealot.this.getHorizontalFaceSpeed(), (float)EntityZealot.this.getVerticalFaceSpeed());
            }
        }
    }
    class AIBuffSpell extends AIUseSpell
    {
        final Predicate<EntityMob> selector = entityMob -> !(entityMob instanceof EntityZealot) && entityMob.isPotionActive(ModPotions.SHIELDED);
        final Predicate<EntityMob> selector2 = entityMob -> !(entityMob instanceof EntityZealot) && !entityMob.isPotionActive(ModPotions.SHIELDED);

        @Override
        public boolean shouldExecute()
        {
            if (EntityZealot.this.getAttackTarget() == null)
            {
                return false;
            }
            else if (EntityZealot.this.isSpellcasting())
            {
                return false;
            }
            else if (EntityZealot.this.ticksExisted < this.spellCooldown)
            {
                return false;
            }
            else
            {
                List<EntityMob> mobs = EntityZealot.this.world.getEntitiesWithinAABB(EntityMob.class, EntityZealot.this.getEntityBoundingBox().grow(6D), this.selector2);
                if (mobs.isEmpty())
                {
                    return false;

                }
                else
                {
                    List<EntityMob> mobsbuffed = EntityZealot.this.world.getEntitiesWithinAABB(EntityMob.class, EntityZealot.this.getEntityBoundingBox().grow(6D), this.selector);
                    return mobsbuffed.size() < 5;

                }
            }
        }



        @Override
        protected void castSpell() {
            List<EntityMob> mobs = EntityZealot.this.world.getEntitiesWithinAABB(EntityMob.class, EntityZealot.this.getEntityBoundingBox().grow(6D), this.selector2);
            if(!mobs.isEmpty()){
                for(EntityMob mob: mobs)
                {
                    if(mob != null && mob.isEntityAlive() && !world.isRemote)
                    {
                        EntityBeam beam = new EntityBeam(world, EntityZealot.this, mob);
                        mob.addPotionEffect(new PotionEffect(ModPotions.SHIELDED, 200, 0, false, false));
                        EntityZealot.this.getNavigator().tryMoveToEntityLiving(mob, 0.6F);
                        world.spawnEntity(beam);
                    }
                }
            }
        }
        @Override
        protected int getCastingTime() {
            return 40;
        }
        @Override
        protected int getCastingInterval() {
            return 200;
        }
        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }
        @Override
        protected SpellType getSpellType() {
            return SpellType.FANGS;
        }
        @Override
        public void resetTask() {
            super.resetTask();
            EntityZealot.this.setSpellType(SpellType.NONE);
        }
    }
    class AIWeakeningSpell extends AIUseSpell {
        @Override
        public boolean shouldExecute()
        {
            if (EntityZealot.this.getAttackTarget() == null)
            {
                return false;
            }
            else if (EntityZealot.this.isSpellcasting())
            {
                return false;
            }
            else if (EntityZealot.this.ticksExisted < this.spellCooldown)
            {
                return false;
            }
            else
            {

                if (!EntityZealot.this.canEntityBeSeen(EntityZealot.this.getAttackTarget()))
                {
                    return false;

                }
                else
                {
                    return EntityZealot.this.getDistanceSq(EntityZealot.this.getAttackTarget()) < 25F;

                }
            }
        }
        private AIWeakeningSpell()
        {
        }
        @Override
        protected void castSpell() {
            EntityLivingBase entity = EntityZealot.this.getAttackTarget();
            if(entity != null && !world.isRemote){
                entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 1));
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
            }
        }
        @Override
        protected int getCastingTime() {
            return 40;
        }
        @Override
        protected int getCastingInterval() {
            return 300;
        }
        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
        }
        @Override
        protected SpellType getSpellType() {
            return SpellType.FANGS;
        }
        @Override
        public void resetTask() {
            super.resetTask();
            EntityZealot.this.setSpellType(SpellType.NONE);
        }
    }
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.ZEALOT_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.ZEALOT_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.ZEALOT_DEATH; }

    public boolean isOnSameTeam(Entity entityIn)
    {
        if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }
    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.dimension == 0;
    }
}