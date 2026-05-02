package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EntityEarthling extends EntityCreature implements IAnimals, IShearable {

    private static final DataParameter<ItemStack> FLOWER = EntityDataManager.createKey(EntityEarthling.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> BONEMEAL = EntityDataManager.createKey(EntityEarthling.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SLEEP = EntityDataManager.createKey(EntityEarthling.class, DataSerializers.VARINT);
    private float sleepYaw;
    private static final DataParameter<Integer> PATCH = EntityDataManager.createKey(EntityEarthling.class, DataSerializers.VARINT);

    public EntityEarthling(World worldIn){
        super(worldIn);
        this.setSize(1.1F,1.4F);
        this.experienceValue = 0;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.6D));
        this.tasks.addTask(3, new EarthlingTempt(this, 0.6D, Items.DYE , false));
        this.tasks.addTask(3, new EarthlingDoNothing(this));
        this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 0.6D, this.getBonemeal() > 0 ? 0.0001f : 0.001f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAIAvoidEntity<>(this, EntityMob.class, 8F, 0.6D, 0.8D));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.1D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FLOWER, ItemStack.EMPTY);
        this.dataManager.register(BONEMEAL, 0);
        this.dataManager.register(PATCH, 0);
        this.dataManager.register(SLEEP, 0);
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.BLOCK_GRAVEL_STEP, 0.15F, 1.0F);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        this.rotationYaw = this.rotationYawHead;
        if(this.getBonemeal() > 0){
            this.setBonemeal(this.getBonemeal() - 1);
            if(this.world.isRemote){

                this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextGaussian() * 0.6D, this.posY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D);

            }
        }
        if(this.getSleep() >= 0 && !this.world.isRemote){
            this.rotationYawHead = this.prevRotationYawHead;
            this.rotationPitch = 0F;
        }
    }

    @Override
    public void onLivingUpdate() {

        if(!this.world.isRemote && this.world.isRaining() && this.world.canSeeSky(this.getPosition()) && this.getPatch() <= 0){
            this.setPatch(1000 + rand.nextInt(1000));
        }
        if(this.getPatch() > 0 && !this.world.isRemote && this.getSleep() < 0){
            this.setPatch(this.getPatch() - 1);


            for (int l = 0; l < 4; ++l)
            {
               int i = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
               int j = MathHelper.floor(this.posY-1);
               int k = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);

                int count = 0;
                boolean neighborFlag = true;
                for(int x1 = -2; x1 < 3; ++x1){
                    for (int z1 = -2; z1 < 3; ++z1){
                        blockpos = new BlockPos(i + x1, j, k + z1);
                        if(this.world.getBlockState(blockpos).getMaterial() != Material.AIR){
                            count++;
                            if(count > 16) {
                                neighborFlag = false;
                                break;
                            }
                        }

                    }
                }
                for(int x1 = -1; x1 < 2; ++x1) {
                    for (int z1 = -1; z1 < 2; ++z1) {
                        blockpos = new BlockPos(i + x1, j, k + z1);
                        if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.GRASS.canPlaceBlockAt(this.world, blockpos) && !neighborFlag) {
                            this.world.setBlockState(blockpos, Blocks.GRASS.getDefaultState());
                        }
                    }
                }
            }
        }
        if (this.onGround && !this.world.isRemote && this.getBonemeal() > 0)
        {
            IBlockState iblockstate1 = this.world.getBlockState(this.getPosition().down());
            if(iblockstate1.getBlock() instanceof IGrowable && this.ticksExisted % 20 == 0){
                IGrowable igrowable = (IGrowable)iblockstate1.getBlock();
                if(igrowable.canGrow(this.world, this.getPosition(), iblockstate1, world.isRemote)) {
                    igrowable.grow(this.world, this.rand, this.getPosition().down(), iblockstate1);
                }
            }

            BlockPos pos = new BlockPos(this.posX + (this.rand.nextInt(12) - 6), this.posY, this.posZ + (this.rand.nextInt(12) - 6));
            Block myblock = Block.getBlockFromItem(this.getFlower().getItem());

           if(myblock != Blocks.AIR && myblock.canPlaceBlockAt(this.world, pos) && this.ticksExisted % 20 == 0 && canSpawnIfSapling(myblock)) {
               if(myblock.canPlaceBlockAt(this.world, pos)) {
                   IBlockState iblockstate2 = myblock.getStateForPlacement(this.world, pos, this.getHorizontalFacing(), pos.getX(), pos.getY(), pos.getZ(), this.getFlower().getMetadata(), this, EnumHand.MAIN_HAND);
                   this.world.setBlockState(pos, iblockstate2, 11);
                   myblock.onBlockPlacedBy(this.world, pos, iblockstate2, this, this.getFlower());
                   this.world.scheduleUpdate(pos, myblock, 5);
               }
            }
        }
        if(this.world.isDaytime() && !this.world.isRemote && this.getSleep() < -5000 && this.getPatch() <= 0 && this.getBonemeal() <= 0){
            this.setSleep(2000 + rand.nextInt(2000));
        }
        if(this.getSleep() >= -5000 && !this.world.isRemote){
            this.setSleep(this.getSleep() - 1);
        }
        super.onLivingUpdate();
    }

    public boolean canSpawnIfSapling(Block blockIn) {
        if(blockIn instanceof BlockSapling){
            return rand.nextInt(10) == 0;
        }else {
            return true;
        }

    }

    protected boolean canDespawn()
    {
        return false;
    }

    public IBlockState getFlowerState() {
        IBlockState iblockstate2 = null;
        BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        if(this.getFlower() != ItemStack.EMPTY) {
            Block myblock = Block.getBlockFromItem(this.getFlower().getItem());
            iblockstate2 = myblock.getStateForPlacement(this.world, pos, this.getHorizontalFacing(), pos.getX(), pos.getY(), pos.getZ(), this.getFlower().getMetadata(), this, EnumHand.MAIN_HAND);
        }
        return iblockstate2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(this.getSleep() > 0){
            this.setSleep(-4000);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if(compound.hasKey("flower"))
        {
            NBTTagCompound flowerTag = compound.getCompoundTag("flower");
            ItemStack flowerStack = new ItemStack(flowerTag);
            this.setFlower(flowerStack);
        }
        this.setBonemeal(compound.getInteger("bonemeal"));
        if(compound.hasKey("sleep")) {
            this.setSleep(compound.getInteger("sleep"));
        }
        if(compound.hasKey("patch")){
            this.setPatch(compound.getInteger("patch"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("bonemeal", this.getBonemeal());
        compound.setTag("flower", this.getFlower().serializeNBT());
        compound.setInteger("sleep", this.getSleep());
        compound.setInteger("patch", this.getPatch());
    }

    public ItemStack getFlower() {
        return this.dataManager.get(FLOWER);
    }

    public void setFlower(ItemStack flower)
    {
        this.dataManager.set(FLOWER, flower);
    }


    public void setBonemeal(int ticks){
        this.dataManager.set(BONEMEAL, ticks);
    }

    public int getBonemeal(){
        return this.dataManager.get(BONEMEAL);
    }

    public void setSleep(int ticks){
        this.dataManager.set(SLEEP, ticks);
    }
    public int getSleep(){
        return this.dataManager.get(SLEEP);
    }
    @SideOnly(Side.CLIENT)
    public int getSleepClient(){
        return this.dataManager.get(SLEEP);
    }

    public void setPatch(int ticks){this.dataManager.set(PATCH, ticks);}
    public int getPatch(){
        return this.dataManager.get(PATCH);
    }
    @SideOnly(Side.CLIENT)
    public int getPatchClient(){
        return this.dataManager.get(PATCH);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Block block = Block.getBlockFromItem(itemstack.getItem());
        if(block instanceof IPlantable && !(block instanceof BlockCrops)) {
           if(this.getFlower().isEmpty()) {
               this.setFlower(itemstack);
               if(!player.capabilities.isCreativeMode) {
                   itemstack.shrink(1);
               }

           }


        } else if (itemstack.getItem() == Items.DYE && itemstack.getMetadata() == 15) {
            if(!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            if(this.getSleep() <= 0) {
                this.setBonemeal(600);
            } else {
                this.setSleep(this.getSleep() - 1000 - this.rand.nextInt(200));
            }
            this.playSound(ModSounds.EARTHLING_BONEMEAL, 0.75F, 1.0F);
            return true;
        } else if (itemstack.getItem() == ModItems.FLOWER_DUST){
            if(!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
                this.setPatch(1000);
                this.setBonemeal(1000);

            this.playSound(ModSounds.EARTHLING_BONEMEAL, 0.75F, 1.0F);
            return true;
        }
        return super.processInteract(player, hand);

    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return !this.getFlower().isEmpty() ;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        List<ItemStack> result = Lists.newArrayList();
        result.add(this.getFlower());
        this.setFlower(ItemStack.EMPTY);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return result;
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    { return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null; }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }


    @Override
    public float getEyeHeight() {
        return 0.6F;
    }

    public static class EarthlingTempt extends EntityAITempt {


        public EarthlingTempt(EntityCreature temptedEntityIn, double speedIn, Item temptItemIn, boolean scaredByPlayerMovementIn) {
            super(temptedEntityIn, speedIn, temptItemIn, scaredByPlayerMovementIn);
        }


        protected boolean isTempting(ItemStack stack)
        {
            return super.isTempting(stack) && stack.getMetadata() == 15; // 15 == BONEMEAL
        }
    }
    public static class EarthlingDoNothing extends EntityAIBase {
        private final EntityEarthling tameable;

        public EarthlingDoNothing(EntityEarthling entityIn)
        {
            this.tameable = entityIn;
            this.setMutexBits(5);
        }

        public boolean shouldExecute()
        {
            if (this.tameable.isInWater())
            {
                return false;
            }
            else if (!this.tameable.onGround)
            {
                return false;
            }
            else
            {
                return this.tameable.getSleep() > 0;
            }
        }


        public void startExecuting()
        {
            this.tameable.getNavigator().clearPath();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && !this.tameable.isInWater() && this.tameable.getSleep() > 0;
        }

        public void resetTask()
        {
            super.resetTask();
        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {

        if(this.world.getBiome(this.getPosition()) == Biomes.MUTATED_FOREST && rand.nextInt(3)==0 || this.rand.nextInt(32)==0) {
            this.setFlower(new ItemStack(ModBlocks.WONDER_FLOWER));
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.EARTHLING_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.EARTHLING_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.EARTHLING_IDLE;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }
}
