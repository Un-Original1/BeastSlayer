package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGiant extends EntityMob {
    protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Giant");
    private int attackTimer;
    private int grabTicks;
    private int actionCooldown;
    private int throwTicks;
    private boolean didThrow;
    public EntityGiant(World worldIn) {
        super(worldIn);
        this.setSize(2.8F, 7.2F);
        this.stepHeight = 2.0F;
        this.experienceValue = 50;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIGiantMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityZombie.class));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((180.0D + BeastSlayerConfig.GiantHealthBonus) * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D + BeastSlayerConfig.GlobalArmor);
        this.getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.7D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 2.5F, 0.5F);
    }

    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F + 0.5F;
    }
    protected float getSoundVolume()
    {
        return 2.5F;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if(this.grabTicks > 0)
        {
            --this.grabTicks;
        }
        if(this.actionCooldown > 0)
        {
            --this.actionCooldown;
        }
        if(this.throwTicks > 0)
        {
            --this.throwTicks;
        }
        if(!world.isRemote && !this.isBeingRidden() && this.actionCooldown <= 0)
        {
            if(rand.nextInt(6) == 0){
                List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(6D));
                if(!list.isEmpty()) {
                    EntityLivingBase p = list.get(this.world.rand.nextInt(list.size()));
                    if(p instanceof EntityPlayer || p instanceof EntityVillager || p instanceof EntityIronGolem) {
                        if(p instanceof EntityPlayer) {
                            EntityPlayer entityPlayer = (EntityPlayer)p;
                            if(!entityPlayer.capabilities.isCreativeMode) {
                                p.startRiding(this);
                                this.grabTicks = 200;
                            }
                        }
                        else {
                            p.startRiding(this);
                            this.grabTicks = 200;
                        }
                    }
                }
            }
        }
        if(this.isBeingRidden())
        {
            for(Entity e : this.getPassengers())
            {
                if(e instanceof EntityLivingBase)
                {
                    EntityLivingBase l = (EntityLivingBase)e;
                    l.attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
                }
            }
            if(this.grabTicks <= 0 || this.hurtTime != 0)
            {
                this.removePassengers();
                this.actionCooldown = 800;
            }
        }
        if(this.getAttackTarget() != null && !this.world.isRemote && actionCooldown <= 0 && rand.nextInt(6) == 0 && !this.isBeingRidden()){
            EntityLivingBase target = this.getAttackTarget();
            if(this.getDistanceSq(target) > 16D && !this.didThrow){
                this.world.setEntityState(this, (byte)6);
                this.throwBoulder(target);
                this.throwTicks = 12;
                this.actionCooldown = 400;
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));

            if (iblockstate.getMaterial() != Material.AIR)
            {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(iblockstate));
            }
        }
        if(this.didThrow && this.throwTicks <= 0){
            this.didThrow = false;
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

        if (flag)
        {
            entityIn.motionX += entityIn.posX - this.posX;
            entityIn.motionY += 0.6D;
            entityIn.motionZ += entityIn.posZ - this.posZ;
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount))
        {
            EntityLivingBase entitylivingbase = this.getAttackTarget();

            if (entitylivingbase == null && source.getTrueSource() instanceof EntityLivingBase)
            {
                entitylivingbase = (EntityLivingBase)source.getTrueSource();
            }

            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if ((double)this.rand.nextFloat() < this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue() && this.world.getGameRules().getBoolean("doMobSpawning"))
            {


                for (int l = 0; l < 6; ++l)
                {
                    EntityZombie entityzombie = new EntityZombie(this.world);
                    int i1 = i + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);
                    int j1 = j + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);
                    int k1 = k + MathHelper.getInt(this.rand, 7, 20) * MathHelper.getInt(this.rand, -1, 1);

                    if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP) && this.world.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10)
                    {
                        entityzombie.setPosition(i1, j1, k1);

                        if (/*!this.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) &&*/ this.world.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), entityzombie) && this.world.getCollisionBoxes(entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(entityzombie.getEntityBoundingBox()))
                        {
                            this.world.spawnEntity(entityzombie);
                            if (entitylivingbase != null) entityzombie.setAttackTarget(entitylivingbase);
                            entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), null);
                            entityzombie.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isAttacking(){ return this.attackTimer > 0; }

    @Override
    public void updatePassenger(Entity entity) {
        super.updatePassenger(entity);
        if (entity instanceof EntityLivingBase)
        {
            this.world.setEntityState(this, (byte)5);
            float f1 = MathHelper.sin(this.renderYawOffset * 0.017453292F + (float)Math.PI);
            float f2 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            entity.setPosition(this.posX + (f1 * 4D), this.posY + 5D, this.posZ + (f2 * 4D));

            if (entity.isSneaking())
            {
                entity.setSneaking(false);
            }
        }
    }
    @Override
    public boolean shouldRiderSit() {
        return false;
    }
    @Override
    public boolean canRiderInteract()
    {
        return true;
    }
    @Override
    public boolean canPassengerSteer() {
        return false;
    }

    static class EntityAIGiantMelee extends EntityAIAttackMelee
    {
        public EntityAIGiantMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }
        protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr)
        {
            double d0 = this.getAttackReachSqr(enemy);

            if (distToEnemySqr <= d0 && this.attackTick <= 0)
            {
                this.attackTick = 25;
                this.attacker.attackEntityAsMob(enemy);
            }
            else if (distToEnemySqr <= d0 * 2.0D)
            {
                if (this.attackTick <= 0)
                {
                    this.attackTick = 25;
                }
            }
            else
            {
                this.attackTick = 25;
            }
        }

        public void resetTask()
        {
            super.resetTask();
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (this.attacker.width * 1.9F * this.attacker.width * 1.9F + attackTarget.width);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 4)
        {
            this.attackTimer = 10;
        }
        if (id == 5)
        {
            this.grabTicks = 200;
        }
        if (id == 6)
        {
            this.throwTicks = 12;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }

    private void throwBoulder(EntityLivingBase target){
        EntityBoulder boulder = new EntityBoulder(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - boulder.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        boulder.shoot(d0, d1 + (double)f, d2, 1.3F, 0.0F);
        this.world.spawnEntity(boulder);
        this.didThrow = true;
    }
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }
    @SideOnly(Side.CLIENT)
    public int getThrowTicks()
    {
        return this.throwTicks;
    }
    public boolean isThrowing(){ return this.throwTicks > 0; }
    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }
}
