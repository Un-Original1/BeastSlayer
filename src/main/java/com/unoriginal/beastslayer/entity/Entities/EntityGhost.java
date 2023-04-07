package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveRandom;
import com.unoriginal.beastslayer.entity.Entities.magic.MagicType;
import com.unoriginal.beastslayer.entity.Entities.magic.UseMagic;
import com.unoriginal.beastslayer.init.ModPotions;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGhost extends EntityMob implements IMagicUser {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Ghost");
    private EntityLivingBase possessedEntity;
    private int followTicks;
    public EntityGhost(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.9F);
        this.moveHelper = new AIMoveControl(this);
    }
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(true);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityGhost.EntityAIPossession(this));
        this.tasks.addTask(8, new AIMoveRandom(this));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 1.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMob.class));
        this.targetTasks.addTask(2, (new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true)).setUnseenMemoryTicks(100));
        this.targetTasks.addTask(3, (new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false)).setUnseenMemoryTicks(100));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalDamageMultiplier);
    }
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    public void onUpdate()
    {
        this.noClip = true;
        super.onUpdate();
        this.noClip = false;
        this.setNoGravity(true);
        if (this.world.isDaytime() && this.dimension == 0 && !this.world.isRemote)
        {
            this.setHealth(0.0F);
        }
        if(this.getPossessedEntity() != null && !this.world.isRemote){
            EntityLivingBase base = this.getPossessedEntity();
            if(--this.followTicks > 0) {
                this.moveHelper.setMoveTo(base.posX, base.posY + base.height, base.posZ, 1.0D);
            }
            if(this.getHealth() <= 0.0F){
                if(base.isPotionActive(ModPotions.POSSESSED) && base instanceof EntityPlayer)
                {
                    EntityPlayer p = (EntityPlayer)base;
                    p.removeActivePotionEffect(ModPotions.POSSESSED);
                } else{
                    base.clearActivePotions();
                }
            }
        }
    }
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    private void setPossessedEntity(@Nullable EntityLivingBase possessedIn)
    {
        this.possessedEntity = possessedIn;
    }

    @Nullable
    private EntityLivingBase getPossessedEntity()
    {
        return this.possessedEntity;
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
    public boolean isUsingMagic() {
        return false;
    }

    @Override
    public int getMagicUseTicks() {
        return 0;
    }

    @Override
    public void setMagicUseTicks(int magicUseTicks) {
    }

    @Override
    public MagicType getMagicType() {
        return null;
    }

    @Override
    public void setMagicType(MagicType spellTypeIn) {

    }

    class EntityAIPossession extends UseMagic<EntityGhost>{
        final Predicate<EntityLivingBase> selector = livingBase -> !(livingBase instanceof EntityGhost) && !livingBase.isPotionActive(ModPotions.POSSESSED) && !(livingBase instanceof EntityAnimal) && !livingBase.isPotionActive(ModPotions.GHOSTLY);

        protected EntityAIPossession(EntityGhost magicUserMob) {
            super(magicUserMob);
        }

        @Override
        public boolean shouldExecute() {
            if (EntityGhost.this.getAttackTarget() == null)
            {
                return false;
            }
            if(EntityGhost.this.ticksExisted < this.magicCooldown)
            {
                return false;
            }
            else if (EntityGhost.this.isUsingMagic())
            {
                return false;
            }
            else
            {
                List<EntityLivingBase> list = EntityGhost.this.world.getEntitiesWithinAABB(EntityLivingBase.class, EntityGhost.this.getEntityBoundingBox().grow(16.0D), this.selector);

                if (list.isEmpty())
                {
                    return false;
                }
                else
                {
                    EntityGhost.this.setPossessedEntity(list.get(EntityGhost.this.rand.nextInt(list.size())));
                    return true;
                }
            }
        }

        @Override
        protected void useMagic() {
            EntityLivingBase livingBase = EntityGhost.this.getPossessedEntity();
            EntityGhost.this.followTicks = 200;
            if(livingBase != null && !world.isRemote && livingBase instanceof EntityLiving && !(livingBase instanceof EntityVillager)){
                EntityLiving possessed = (EntityLiving)livingBase;
                possessed.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 400));
                possessed.addPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 2));
                possessed.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 400, 2));
                possessed.setAttackTarget(EntityGhost.this.getAttackTarget());
            }
            else if(livingBase instanceof EntityPlayer && !((EntityPlayer) livingBase).capabilities.isCreativeMode|| livingBase instanceof EntityVillager && !world.isRemote){
                livingBase.addPotionEffect(new PotionEffect(ModPotions.POSSESSED, 400));
                livingBase.attackEntityFrom(DamageSource.causeMobDamage(EntityGhost.this), 4.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
            }
        }

        public boolean shouldContinueExecuting()
        {
            return EntityGhost.this.getPossessedEntity() != null && this.magicWarmup > 0;
        }

        @Override
        public void resetTask() {
            super.resetTask();
        }

        @Override
        protected int getMagicUseTime() {
            return 400;
        }

        @Override
        protected int getMagicUseInterval() {
            return 400;
        }

        @Nullable
        @Override
        protected SoundEvent getMagicPrepareSound() {
            return ModSounds.GHOST_POSSESS;
        }

        @Override
        protected MagicType getMagicType() {
            return null;
        }
    }
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.GHOST_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.GHOST_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.GHOST_DEATH; }
    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }
}