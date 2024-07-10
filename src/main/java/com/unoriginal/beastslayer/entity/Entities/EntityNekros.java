package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveRandom;
import com.unoriginal.beastslayer.entity.Entities.magic.CastingMagic;
import com.unoriginal.beastslayer.entity.Entities.magic.MagicType;
import com.unoriginal.beastslayer.entity.Entities.magic.UseMagic;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityNekros extends EntityMob implements IMagicUser {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Nekros");
    private static final DataParameter<Byte> MAGIC = EntityDataManager.createKey(EntityNekros.class, DataSerializers.BYTE);
    private MagicType activeMagic = MagicType.NONE;
    private int magicUseTicks;

    public EntityNekros(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.5F);
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
        this.tasks.addTask(1, new CastingMagic<>(this));
        this.tasks.addTask(5, new EntityNekros.UseDarkMagic(this));
        this.tasks.addTask(6, new EntityNekros.LifeStealing(this));
        this.tasks.addTask(6, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 6.0F, 0.6D, 1.0D));
        this.tasks.addTask(6, new EntityAIAvoidEntity<>(this, EntityIronGolem.class, 4.0F, 0.6D, 0.8D));
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
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(MAGIC, (byte)0);
    }
    protected void updateAITasks()
    {
        super.updateAITasks();
        if (this.magicUseTicks > 0) {
            --this.magicUseTicks;
        }
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
        if(this.isUsingMagic()) {
            IMagicUser.spawnMagicParticles(this);
        }
        else {
            this.dataManager.set(MAGIC, (byte)0);
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
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }

    class UseDarkMagic extends UseMagic<EntityNekros>
    {

        protected UseDarkMagic(EntityNekros magicUserMob) {
            super(magicUserMob);
        }


        @Override
        public boolean shouldExecute() {
            int i = EntityNekros.this.world.getEntitiesWithinAABB(EntityMob.class, EntityNekros.this.getEntityBoundingBox().grow(16.0D)).size();
            return EntityNekros.this.rand.nextInt(4) + 1 > i && super.shouldExecute();
        }

        @Override
        protected void useMagic()
        {
            for (int i = 0; i < 5; ++i)
            {
                BlockPos blockpos = (new BlockPos(EntityNekros.this)).add(-4 + EntityNekros.this.rand.nextInt(9), 0, -4 + EntityNekros.this.rand.nextInt(9));

                boolean flag = false;

                while (true)
                {
                    if (!EntityNekros.this.world.isBlockNormalCube(blockpos, true) && EntityNekros.this.world.isBlockNormalCube(blockpos.down(), true))
                    {
                        if (!EntityNekros.this.world.isAirBlock(blockpos))
                        {
                            IBlockState iblockstate = EntityNekros.this.world.getBlockState(blockpos);
                            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(EntityNekros.this.world, blockpos);

                            if (axisalignedbb != null)
                            {
                            }
                        }

                        flag = true;
                        break;
                    }

                    blockpos = blockpos.down();

                    if (EntityNekros.this.getAttackTarget() != null && blockpos.getY() < MathHelper.floor(Math.min((EntityNekros.this.getAttackTarget()).posY, EntityNekros.this.posY)) - 1)
                    {
                        break;
                    }
                }

                if(flag) {
                    EntityZombie zombie = new EntityZombie(world);
                    zombie.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    zombie.onInitialSpawn(EntityNekros.this.world.getDifficultyForLocation(blockpos), null);

                    EntitySkeleton spuki = new EntitySkeleton(world);
                    spuki.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    spuki.onInitialSpawn(EntityNekros.this.world.getDifficultyForLocation(blockpos), null);

                    if (EntityNekros.this.world.isRemote) {
                        int m = MathHelper.floor(blockpos.getX());
                        int j = MathHelper.floor(blockpos.getY() - 0.20000000298023224D);
                        int k = MathHelper.floor(blockpos.getZ());
                        IBlockState iblockstate = EntityNekros.this.world.getBlockState(new BlockPos(m, j, k));

                        if (iblockstate.getMaterial() != Material.AIR) {
                            EntityNekros.this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, blockpos.getX() + ((double) EntityNekros.this.rand.nextFloat() - 0.5D) * (double) EntityNekros.this.width, blockpos.getY() + 0.1D, blockpos.getZ() + ((double) EntityNekros.this.rand.nextFloat() - 0.5D) * (double) EntityNekros.this.width, 4.0D * ((double) EntityNekros.this.rand.nextFloat() - 0.5D), 0.5D, ((double) EntityNekros.this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(iblockstate));
                        }
                    }
                    if (EntityNekros.this.rand.nextInt(2) == 0) {
                        EntityNekros.this.world.spawnEntity(zombie);
                        zombie.motionY += 0.4D;
                    } else {
                        EntityNekros.this.world.spawnEntity(spuki);
                        spuki.motionY += 0.4D;
                    }
                }
            }
        }


        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 400;
        }

        @Override
        protected SoundEvent getMagicPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }

        @Override
        protected MagicType getMagicType() {
            return MagicType.NECROMANCY;
        }
    }

    class LifeStealing extends UseMagic<EntityNekros>
    {
        protected LifeStealing(EntityNekros magicUserMob) {
            super(magicUserMob);
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && EntityNekros.this.getHealth() < EntityNekros.this.getMaxHealth() && EntityNekros.this.getAttackTarget() != null &&EntityNekros.this.getDistance(EntityNekros.this.getAttackTarget()) < 8F ;
        }

        @Override
        protected void useMagic()
        {
            EntityLivingBase livingBase = EntityNekros.this.getAttackTarget();
            if(livingBase != null && !EntityNekros.this.world.isRemote && EntityNekros.this.canEntityBeSeen(livingBase)) {
                livingBase.attackEntityFrom(DamageSource.causeMobDamage(EntityNekros.this), 5F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
                livingBase.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200));
                EntityNekros.this.heal(5.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
            }
        }


        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 600;
        }

        @Override
        protected SoundEvent getMagicPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }

        @Override
        protected MagicType getMagicType() {
            return MagicType.SOUL;
        }
    }

    @Override
    public boolean isUsingMagic() {
        if (this.world.isRemote) {
            return this.dataManager.get(MAGIC) > 0;
        } else {
            return this.magicUseTicks > 0;
        }
    }

    @Override
    public int getMagicUseTicks() {
        return this.magicUseTicks;
    }

    @Override
    public void setMagicUseTicks(int magicUseTicks) {
        this.magicUseTicks = magicUseTicks;
    }

    @Override
    public MagicType getMagicType() {
        return !this.world.isRemote ? this.activeMagic : MagicType.getFromId(this.dataManager.get(MAGIC));
    }

    @Override
    public void setMagicType(MagicType magicType) {
        this.activeMagic = magicType;
        this.dataManager.set(MAGIC, (byte)magicType.getId());
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("MagicUseTicks", this.magicUseTicks);
    }
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.magicUseTicks = compound.getInteger("MagicUseTicks");
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.GHOST_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.GHOST_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.GHOST_DEATH; }

    protected float getSoundPitch()
    {
        return 0.7F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.dimension == 0;
    }
}
