package com.unoriginal.ancientbeasts.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.entity.Entities.magic.MagicType;
import com.unoriginal.ancientbeasts.entity.Entities.magic.UseMagic;
import com.unoriginal.ancientbeasts.init.ModPotions;
import com.unoriginal.ancientbeasts.init.ModSounds;
import com.unoriginal.ancientbeasts.util.IMagicUser;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGhost extends EntityMob implements IMagicUser {
    public static final ResourceLocation LOOT = new ResourceLocation(AncientBeasts.MODID, "entities/Ghost");
    private EntityLivingBase possessedEntity;
    public EntityGhost(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.9F);
        this.moveHelper = new EntityGhost.AIMoveControl(this);
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
        this.tasks.addTask(8, new EntityGhost.AIMoveRandom());
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(28.0D);
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
        if (this.world.isDaytime() && !this.world.isRemote)
        {
            this.setHealth(0.0F);
        }
        if(this.getPossessedEntity() != null && !this.world.isRemote){
            EntityLivingBase base = this.getPossessedEntity();
            this.moveHelper.setMoveTo(base.posX, base.posY + 0.5, base.posZ, 1.0D);
            if(this.getHealth() <= 0.0F){
                base.clearActivePotions();
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

    @Override
    public SoundEvent getMagicSound() {
        return null;
    }

    class AIMoveControl extends EntityMoveHelper
    {
        public AIMoveControl(EntityGhost ghost)
        {
            super(ghost);
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - EntityGhost.this.posX;
                double d1 = this.posY - EntityGhost.this.posY;
                double d2 = this.posZ - EntityGhost.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = MathHelper.sqrt(d3);

                if (d3 < EntityGhost.this.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityGhost.this.motionX *= 0.5D;
                    EntityGhost.this.motionY *= 0.5D;
                    EntityGhost.this.motionZ *= 0.5D;
                }
                else
                {
                    EntityGhost.this.motionX += d0 / d3 * 0.05D * this.speed;
                    EntityGhost.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityGhost.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityGhost.this.getAttackTarget() == null)
                    {
                        EntityGhost.this.rotationYaw = -((float)MathHelper.atan2(EntityGhost.this.motionX, EntityGhost.this.motionZ)) * (180F / (float)Math.PI);
                    }
                    else
                    {
                        double d4 = EntityGhost.this.getAttackTarget().posX - EntityGhost.this.posX;
                        double d5 = EntityGhost.this.getAttackTarget().posZ - EntityGhost.this.posZ;
                        EntityGhost.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                    }
                    EntityGhost.this.renderYawOffset = EntityGhost.this.rotationYaw;
                }
            }
        }
    }

    class AIMoveRandom extends EntityAIBase
    {
        public AIMoveRandom()
        {
            this.setMutexBits(1);
        }

        public boolean shouldExecute()
        {
            return !EntityGhost.this.getMoveHelper().isUpdating() && EntityGhost.this.rand.nextInt(7) == 0;
        }

        public boolean shouldContinueExecuting()
        {
            return false;
        }

        public void updateTask()
        {
            BlockPos blockpos = new BlockPos(EntityGhost.this);
            for (int i = 0; i < 3; ++i)
            {
                BlockPos blockpos1 = blockpos.add(EntityGhost.this.rand.nextInt(15) - 7, EntityGhost.this.rand.nextInt(9) - 5, EntityGhost.this.rand.nextInt(15) - 7);

                if (EntityGhost.this.world.isAirBlock(blockpos1))
                {
                    EntityGhost.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityGhost.this.getAttackTarget() == null)
                    {
                        EntityGhost.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }

                    break;
                }
            }
        }
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
            if(livingBase != null && !world.isRemote && livingBase instanceof EntityLiving && !(livingBase instanceof EntityVillager)){
                EntityLiving possessed = (EntityLiving)livingBase;
                possessed.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 400));
                possessed.addPotionEffect(new PotionEffect(MobEffects.SPEED, 400));
                possessed.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 400));
                possessed.setAttackTarget(EntityGhost.this.getAttackTarget());
            }
            else if(livingBase instanceof EntityPlayer || livingBase instanceof EntityVillager){
                livingBase.addPotionEffect(new PotionEffect(ModPotions.POSSESSED, 400));
                livingBase.attackEntityFrom(DamageSource.causeMobDamage(EntityGhost.this), 4.0F);
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