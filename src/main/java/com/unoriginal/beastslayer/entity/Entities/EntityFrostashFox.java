package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAISleep;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class EntityFrostashFox extends EntityTameable {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityFrostashFox.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EntityFrostashFox.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SECONDGEN = EntityDataManager.createKey(EntityFrostashFox.class, DataSerializers.BOOLEAN);
    public boolean didShoot;
    protected EntityAISleep entityAISleep;
    private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.COOKED_RABBIT);
    public int spikeTimer;
    public int spikeShedTime;
    public int i =  400 + rand.nextInt(200);
    public int lastSleepTimer;
    public int sitTimer;
    private EntityAITempt aiTempt;

    public EntityFrostashFox(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.9F);
        this.spikeShedTime = this.rand.nextInt(6000) + 4000;
        this.setTamed(false);
    }
    protected void initEntityAI()
    {
        this.aiSit = new EntityAISit(this);
        this.entityAISleep = new EntityAISleep(this);
        this.aiTempt = new EntityAITempt(this, 0.6D, true, TAME_ITEMS);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.0D));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.entityAISleep);
        this.tasks.addTask(3, this.aiTempt);
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, randJump()));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPolarBear.class, 8.0F,  0.6D,1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIAvoidEntity<>(this, EntityWolf.class, 8.0F,  0.6D,1.0D));
        this.tasks.addTask(0, new EntityAIAvoidEntity<>(this, EntityNetherhound.class, 16.0F,  0.8D,1.0D));
        this.tasks.addTask(6, new EntityFrostashFox.AIFrostashFollow(this, 1D, 10F, 2F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityChicken.class, false));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityRabbit.class, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }
    public float randJump(){
        return rand.nextInt(10) == 0 ? 0.7F : 0.4F;
    }

    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(!this.world.isRemote && !this.didShoot && this.spikeTimer <= 0 && rand.nextInt(8) < 4 && !this.isTamed())
        {
            List<EntityLivingBase> baseList = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(2D));
            if(!baseList.isEmpty()){
                EntityLivingBase base = baseList.get(this.world.rand.nextInt(baseList.size()));
                if(base instanceof EntityPlayer){
                    EntityPlayer p = (EntityPlayer) base;
                    if(!this.isChild() && !p.capabilities.isCreativeMode){
                        this.shootDart();
                        this.spikeTimer = i;
                        this.didShoot = true;
                        this.world.setEntityState(this, (byte)4);
                        if(this.entityAISleep != null)
                        {
                            this.entityAISleep.setSleeping(false);
                        }
                    }
                }
                else if(base instanceof EntityVillager || base instanceof EntityMob || base instanceof EntityWolf || base instanceof EntityPolarBear) {
                    if (!this.isChild()) {
                        this.shootDart();
                        this.spikeTimer = i;
                        this.didShoot = true;
                        this.world.setEntityState(this, (byte) 4);
                        if(this.entityAISleep != null)
                        {
                            this.entityAISleep.setSleeping(false);
                        }
                    }
                }
            }
        }
        if(!this.world.isDaytime() || this.world.isDaytime() && this.world.canSeeSky(new BlockPos(this.posX, this.posY, this.posZ)))
        {
            if(this.entityAISleep != null)
            {
                this.entityAISleep.setSleeping(false);
            }
        }
        if(this.spikeTimer > 0)
        {
            --this.spikeTimer;
        }
        if(this.didShoot && this.spikeTimer <= 0)
        {
            this.didShoot = false;
        }
        if(this.entityAISleep != null) {
            if (this.entityAISleep.nextSleepTicks > 0) {
                --this.entityAISleep.nextSleepTicks;
            }
        }
        if (!this.world.isRemote && !this.isChild() && this.spikeTimer <= 0 && --this.spikeShedTime <= 0)
        {
            this.playSound(ModSounds.ICE, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            if(this.isRed()){
                this.entityDropItem(new ItemStack(ModItems.ICE_DART, this.world.rand.nextInt(3), 1), 0.0F);
            }
            else {
                if(this.getVariant() == 2) {
                    this.entityDropItem(new ItemStack(ModItems.ICE_DART, this.world.rand.nextInt(3), this.world.rand.nextInt(1)), 0.0F);
                }
                else {
                    this.entityDropItem(new ItemStack(ModItems.ICE_DART, this.world.rand.nextInt(3), 0), 0.0F);
                }
            }
            this.spikeShedTime = this.rand.nextInt(6000) + 4000;
        }
        if(!this.world.isRemote && ++this.lastSleepTimer >= 24000){
            if(this.aiSit != null){
                this.aiSit.setSitting(true);
                this.sitTimer = 400;
                this.lastSleepTimer = 0;
            }
        }
        if(!this.world.isRemote && --this.sitTimer <= 0){
            if(this.aiSit != null) {
                this.aiSit.setSitting(false);
            }
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(SLEEPING, Boolean.FALSE);
        this.dataManager.register(SECONDGEN, Boolean.FALSE);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }
        this.playSound(ModSounds.FROSTASH_FOX_BITE, 1.0F, 1.0F);
        return flag;
    }

    public static int getRandVariant(Random rand)
    {
        int i = rand.nextInt(100);
        if (i < 5)
        {
            return 0;
        }
        if(i < 15){
            return 3;
        }
        else if (i < 18)
        {
            return 4;
        }
        else {
            return rand.nextInt(300) == 0 ? 2 : 1;
        }
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 4);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
        compound.setBoolean("Sleeping", this.isSleeping());
        compound.setBoolean("SecondGen", this.isSecondGen());
        compound.setInteger("IceShedTime", this.spikeShedTime);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
        if (this.entityAISleep != null)
        {
            this.entityAISleep.setSleeping(compound.getBoolean("Sleeping"));
        }

        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setSecondGen(compound.getBoolean("SecondGen"));
        if (compound.hasKey("IceShedTime"))
        {
            this.spikeShedTime = compound.getInteger("IceShedTime");
        }
    }

    public boolean isRed(){
        return this.getVariant() > 2;
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.RABBIT;
    }

    public EntityFrostashFox createChild(EntityAgeable ageable) {
        EntityFrostashFox frostashFox = new EntityFrostashFox(this.world);
        EntityFrostashFox fox = (EntityFrostashFox) ageable;
        UUID uuid = this.getOwnerId();

        if (uuid != null)
        {
            frostashFox.setOwnerId(uuid);
            frostashFox.setTamed(true);
            frostashFox.setSecondGen(true);
        }
        frostashFox.setVariant(this.getParentsMix(this, fox));
        return frostashFox;
    }

    private int getParentsMix(EntityFrostashFox father, EntityFrostashFox mother)
    {
        int i = father.getVariant();
        int j = mother.getVariant();
        int k = (i + j);
        int l;
        if(k > 6){
           l = 4;
        }
        else if(k >= 5)
        {
            l = 3;
        }
        else if(k == 4)
        {
            l = 2;
        }
        else if(k >= 2)
        {
            l = 1;
        }
        else {
            l = 0;
        }
        return l;
    }
    public boolean isSleeping()
    {
        return this.dataManager.get(SLEEPING);
    }
    public void setSleeping(boolean sleeping)
    {
        this.dataManager.set(SLEEPING, sleeping);
    }
    public boolean isSecondGen()
    {
        return this.dataManager.get(SECONDGEN);
    }
    public void setSecondGen(boolean secondGen)
    {
        this.dataManager.set(SECONDGEN, secondGen);
    }
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(otherAnimal instanceof EntityFrostashFox))
        {
            return false;
        }
        else
        {
            EntityFrostashFox frostashFox = (EntityFrostashFox)otherAnimal;

            if (!frostashFox.isTamed())
            {
                return false;
            }
            else if (frostashFox.isSleeping()){
                return false;
            }
            else
            {
                return this.isInLove() && frostashFox.isInLove();
            }
        }
    }
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem()))
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }
            if (!this.isSilent())
            {
                this.world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.FROSTASH_FOX_EAT, this.getSoundCategory(), 1.0F, 1.0F);
            }

            if (!this.world.isRemote)
            {
                if (this.rand.nextInt(2) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }
        else if(this.isTamed() && itemstack.getItem() instanceof ItemFood){
            ItemFood itemfood = (ItemFood)itemstack.getItem();
            if (TAME_ITEMS.contains(itemfood) && this.getHealth() < 14.0F)
            {
                if (!player.capabilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }
                if (!this.isSilent())
                {
                    this.world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.FROSTASH_FOX_EAT, this.getSoundCategory(), 1.0F, 1.0F);
                }
                this.heal((float)itemfood.getHealAmount(itemstack));
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    private void shootDart(){
        double d0 = 10D - this.posX;
        double d1 = this.posY + this.height;
        double d2 = 10D - this.posZ;
        float f = (float) MathHelper.atan2( 10.0F - this.posZ, 10.0F - this.posX);
        this.playSound(ModSounds.ICE, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        for (int k = 0; k < 4; ++k){
            float f2 = f + (float)k * (float)Math.PI * 3F / 8F + ((float)Math.PI * 3F / 5F);
            EntityIceDart iceDart = new EntityIceDart(this.world, this, this.isRed());
            iceDart.shoot(d0 + (double)MathHelper.cos(f2) * (k + 1 * 10000D), d1, d2 + (double)MathHelper.sin(f2) * (k + 1 * 10000D), 1.0F, 0.0F);
            this.world.spawnEntity(iceDart);
        }
        EntityIceDart iceDart1 = new EntityIceDart(this.world, this, this.isRed());
        iceDart1.shoot(0D, 10000D, 0D, 1.0F, 0.0F);
    }
    protected void setupTamedAI()
    {
        if (this.avoidEntity == null)
        {
            this.avoidEntity = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.33D);
        }
        this.tasks.removeTask(this.avoidEntity);

        if (!this.isTamed())
        {
            this.tasks.addTask(4, this.avoidEntity);
        }
    }
    class AIFrostashFollow extends EntityAIFollowOwner{
        public AIFrostashFollow(EntityTameable tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
            super(EntityFrostashFox.this, followSpeedIn, minDistIn, maxDistIn);
        }
        public boolean shouldExecute()
        {
            return super.shouldExecute() && EntityFrostashFox.this.isSecondGen();
        }
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 4)
        {
            this.spikeTimer = i;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (this.world.rand.nextInt(7) == 0)
        {
            for (int i = 0; i < 2; ++i)
            {
                EntityFrostashFox fox = new EntityFrostashFox(this.world);
                fox.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                fox.setGrowingAge(-24000);
                this.world.spawnEntity(fox);
            }
        }
        this.setVariant(getRandVariant(this.world.rand));
        return livingdata;
    }
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }

            if(this.entityAISleep != null)
            {
                this.entityAISleep.setSleeping(false);
            }

            if(!this.world.isRemote && !this.isChild() && !this.didShoot && this.spikeTimer <= 0){
                this.shootDart();
                this.spikeTimer = i;
                this.didShoot = true;
                this.world.setEntityState(this, (byte)4);
            }

            if (source.isFireDamage())
            {
                amount = amount * 3.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }
    @Override
    public boolean canDespawn(){return !this.isTamed();}

    @Override
    protected SoundEvent getAmbientSound() {
        if(this.isSleeping()){
            return ModSounds.FROSTASH_FOX_SLEEP;
        }
        else if(!this.isTamed() && !this.world.isDaytime() && this.rand.nextInt(500) == 0)
        {
            return ModSounds.FROSTASH_FOX_SCREECH;
        }
        else {
            return ModSounds.FROSTASH_FOX_IDLE;
        }
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.FROSTASH_FOX_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.FROSTASH_FOX_DEATH; }
    protected float getSoundVolume()
    {
        return 0.5F;
    }
}