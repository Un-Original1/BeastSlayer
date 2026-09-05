package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.animation.EZAnimation;
import com.unoriginal.beastslayer.animation.EZAnimationHandler;
import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageDismountRidingEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGloop extends EntityAnimal implements IAnimatedEntity {
    public static final EZAnimation ANIMATION_WALK = EZAnimation.create(20);
    private EZAnimation currAnimation;
    private int ballonInfTick;
    private int animTick;
    private int walkticks;
    private static final DataParameter<Integer> BALLOON = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MAX_JUMP = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BUBBLE = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BUBBLE_COOLDOWN = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private int jumpCooldown;
    private boolean wasMoving = false;
    private int bubbleTicks;
    private final PathNavigateSwimmer  waterNavigator ;
    private final PathNavigateGround groundNavigator ;

    public EntityGloop(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
        this.moveHelper = new EntityGloop.FishMoveHelper(this);
        this.waterNavigator = new PathNavigateSwimmer(this, worldIn);
        this.groundNavigator = new PathNavigateGround(this, worldIn);
        this.walkticks = -1;
        this.bubbleTicks = 0;
        this.setBubbleCooldown(200 + this.rand.nextInt(200));
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIPanic(this, 1D));
        this.tasks.addTask(4, new EntityAIWander(this, 0.6D, 30));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIGloopBubble(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
    }

    @Override
    public void onUpdate() {

        this.rotationYawHead = this.rotationYaw;
        EZAnimationHandler.INSTANCE.updateAnimations(this);
        if(!this.world.isRemote) {
            if(this.isServerWorld() && !this.isRiding()) {
                if(this.isInWater()) {
                    if(this.navigator != this.waterNavigator) {
                    this.navigator = this.waterNavigator;
                        }
                }
                else {
                    if(this.navigator != this.groundNavigator) {
                        this.navigator = this.groundNavigator;
                    }
                }
            }
        }
        if(this.dimension == -1){
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0F, 1.0F);
            this.damageEntity(DamageSource.ON_FIRE, 20.0F);
        }
        if(this.getBalloon() > 0 ){
            if(this.getBalloon() == 10) {
                this.playSound(ModSounds.GLOOP_DEFLATE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
            }
            this.setBalloon(this.getBalloon() - 1);
            double d4 = 1.7F / 2F;
            double d3 = 1.7F;
            this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d4, this.posY, this.posZ - d4, this.posX + d4, this.posY + d3, this.posZ + d4));
        } else {
            double d4 = 0.7F / 2F;
            double d3 = 0.7F;
            this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d4, this.posY, this.posZ - d4, this.posX + d4, this.posY + d3, this.posZ + d4));
        }

        if(this.getBubbleCooldown() > 0){
            this.setBubbleCooldown(this.getBubbleCooldown() - 1);
        }
        if(this.bubbleTicks > 0){
            --this.bubbleTicks;
        }
        if(this.jumpCooldown > 0){
            --this.jumpCooldown;
        }
        if(this.ballonInfTick > 0){
            --this.ballonInfTick;
        }
        if (!this.onGround && this.motionY < 0.0D && this.getBalloon() > 0 && !this.isRiding())
        {
            this.motionY *= 0.6D;
        }
        if(this.isInWater() && this.getBalloon() > 0 && !this.isRiding())
        {
            this.motionY += 0.2D;
        }

        if(this.isRiding() && this.getRidingEntity() instanceof EntityPlayer) {
            this.rotationYawHead = ((EntityPlayer) this.getRidingEntity()).rotationYawHead;
            EntityPlayer player = (EntityPlayer)this.getRidingEntity();
            if(player.onGround && this.getMaxJump() != 0){
                this.setMaxJump(0);
            }
            if (player.isSneaking()) {
                this.dismountRidingEntity();
                this.resetRiding();
            }

            if(this.getBalloon() <= 0){
                this.dismountRidingEntity();
                this.resetRiding();
            }

            if(player == null || player.isDead){
                this.dismountRidingEntity();
                this.resetRiding();
            }

           if(player.isInWater() && this.isInWater()) {

                player.motionY += 0.2D;
                player.velocityChanged = true;

                if(this.world.isRemote) {
                    for (int i = 0; i < 2; ++i)
                    {
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,this.posX + (this.rand.nextDouble() - 0.25D) * (double) this.width, this.posY + (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.25D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
                    }
                }
            }
        }

        double dx = this.posX - this.prevPosX;
        double dz = this.posZ - this.prevPosZ;

        double speedSq = dx * dx + dz * dz;

        boolean moving = speedSq > 1.0E-5;

        if (moving && !this.wasMoving) {
            this.walkticks = this.ticksExisted;
            this.setAnimation(ANIMATION_WALK);
            EZAnimationHandler.INSTANCE.sendAnimationMessage(this, ANIMATION_WALK);
        }

        if(moving && this.walkticks != -1 && (this.ticksExisted - this.walkticks) % 20 == 0) {
            this.setAnimation(ANIMATION_WALK);
            EZAnimationHandler.INSTANCE.sendAnimationMessage(this, ANIMATION_WALK);
        }

        if (!moving && this.wasMoving) {
            this.walkticks = -1;
           this.setAnimation(NO_ANIMATION);
        }

        this.wasMoving = moving;

        super.onUpdate();
    }

    public boolean canBreatheUnderwater()
    { return true; }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isServerWorld() && this.isInWater())
        {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9D;
            this.motionY *= 0.9D;
            this.motionZ *= 0.9D;
        }
        else
        {
            super.travel(strafe, vertical, forward);
        }

    }

    @Override
    protected int decreaseAirSupply(int air) {
        return air;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BALLOON, 0);
        this.dataManager.register(BUBBLE, 0);
        this.dataManager.register(MAX_JUMP, 0);
        this.dataManager.register(BUBBLE_COOLDOWN, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("balloon", this.getBalloon());
        compound.setInteger("maxJump", this.getMaxJump());
        compound.setInteger("InflateAnim", this.ballonInfTick);
        compound.setInteger("walkticks", this.walkticks);
        compound.setBoolean("wasMoving", this.wasMoving);
        compound.setInteger("JumpCooldown", this.jumpCooldown);
        compound.setInteger("bubbleTicks", this.bubbleTicks);
        compound.setInteger("bubbleCooldown", this.getBubbleCooldown());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setBalloon(compound.getInteger("balloon"));
        this.setMaxJump(compound.getInteger("maxJump"));
        this.walkticks = compound.getInteger("walkticks");
        this.wasMoving = compound.getBoolean("wasMoving");
        this.jumpCooldown = compound.getInteger("JumpCooldown");
        this.ballonInfTick = compound.getInteger("InflateAnim");
        this.bubbleTicks = compound.getInteger("bubbleTicks");
        this.setBubbleCooldown(compound.getInteger("bubbleCooldown"));
    }

    public void setBalloon(int balloon) {
        this.dataManager.set(BALLOON, balloon);
    }
    public int getBalloon() {
        return this.dataManager.get(BALLOON);
    }
    @SideOnly(Side.CLIENT)
    public int getBalloonClient() {
        return this.dataManager.get(BALLOON);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {

    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityGloop(this.world);
    }

    @Override
    public float getEyeHeight() {
        return this.isChild()? 0.15F : 0.3F;
    }

    @Override
    public int getAnimationTick() {
        return this.animTick;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!this.isChild() && !this.isBeingRidden() && !this.isRiding() && player.getHeldItemMainhand().isEmpty() && !player.isSneaking()) {
            this.setBalloon(800);
            this.playSound(ModSounds.GLOOP_INFLATE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
            this.startRiding(player,true);
            if(player instanceof EntityPlayerMP) {
                this.getServer().getPlayerList().sendPacketToAllPlayers(new SPacketSetPassengers(player));

            }
            this.navigator.clearPath();
            player.setActiveHand(hand);
            return true;
        } else if(player.getHeldItemMainhand().getItem() == Items.BUCKET || player.getHeldItemMainhand().getItem() == Items.WATER_BUCKET) {
            ItemStack bucket = player.getHeldItemMainhand();
            ItemStack gloopBucket = new ItemStack(ModItems.GLOOP_BUCKET);
            bucket.setCount(bucket.getCount() - 1);

            if(this.hasCustomName()){
                gloopBucket.setStackDisplayName(this.getCustomNameTag());
            }
            if(bucket.isEmpty()){
                player.setHeldItem(EnumHand.MAIN_HAND, gloopBucket);
            } else if(!player.inventory.addItemStackToInventory(gloopBucket)){
                player.dropItem(gloopBucket, false);
            }
            this.setDead();
        }

        return super.processInteract(player, hand);

    }

    public void resetRiding(){
        //this.setFriend(null);
       // this.setHappy(0);
        this.dismountRidingEntity();
        // this.removePassengers();
        BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageDismountRidingEntity(null, this));
      //  this.happyTime = -2000;
       // this.maxHappyTime = 20000 + rand.nextInt(4000);
    }

    public boolean shouldRiderSit()
    {
        return false;
    }

    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return this.height;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() != null) {
            return this.getRidingEntity().isSneaking() ? 0.2D : 0.4D;
        } else {
            return 0.0D;
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return this.getBalloon() <= 0 && !this.isRiding();
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animTick = tick;
    }

    @Override
    public EZAnimation getAnimation() {
        return this.currAnimation;
    }

    @Override
    public void setAnimation(EZAnimation animation) {
        this.currAnimation = animation;
    }

    @Override
    public EZAnimation[] getAnimations() {
        return new EZAnimation[]{ANIMATION_WALK};
    }

    public int getMaxJump() {
        return this.dataManager.get(MAX_JUMP);
    }
    public void setMaxJump(int maxJump) {
        this.dataManager.set(MAX_JUMP, maxJump);
        this.jumpCooldown = 20;
        if(maxJump != 0){
            this.ballonInfTick = 10;
            this.playSound(ModSounds.GLOOP_JUMP, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
            if(this.world.isRemote) {
                for (int i = 0; i < 2; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.CLOUD,this.posX + (this.rand.nextDouble() - 0.25D) * (double) this.width, this.posY + (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.25D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
                }
            }
        }
    }
    public boolean isCooldownActive(){
        return this.jumpCooldown > 0;
    }


    @SideOnly(Side.CLIENT)
    public int getBallonInfTick() {
        return this.ballonInfTick;
    }

    static class FishMoveHelper extends EntityMoveHelper
    {
        private final EntityGloop gloop;

        public FishMoveHelper(EntityGloop Fish)
        {
            super(Fish);
            this.gloop = Fish;
        }

        @Override
        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.gloop.getNavigator().noPath() && this.gloop.isInWater())
            {
                if (this.gloop.isInsideOfMaterial(Material.WATER))
                { this.gloop.motionY += 0.005; }

                double d0 = this.posX - this.gloop.posX;
                double d1 = this.posY - this.gloop.posY;
                double d2 = this.posZ - this.gloop.posZ;
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                this.gloop.rotationYaw = this.limitAngle(this.gloop.rotationYaw, f, 90.0F);
                this.gloop.renderYawOffset = this.gloop.rotationYaw;

                float f1 = (float)(this.speed * this.gloop.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                this.gloop.setAIMoveSpeed(this.gloop.getAIMoveSpeed() + (f1 - this.gloop.getAIMoveSpeed()) * 0.125F);

                this.gloop.motionY += (double)this.gloop.getAIMoveSpeed() * d1 * 0.1D;



                EntityLookHelper entitylookhelper = this.gloop.getLookHelper();
                double d7 = this.gloop.posX + d0 / d3 * 3.0D;
                double d8 = (double)this.gloop.getEyeHeight() + this.gloop.posY + d1 / d3 * 5.0;
                double d9 = this.gloop.posZ + d2 / d3 * 3.0D;
                double d10 = entitylookhelper.getLookPosX();
                double d11 = entitylookhelper.getLookPosY();
                double d12 = entitylookhelper.getLookPosZ();

                if (!entitylookhelper.getIsLooking())
                {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                this.gloop.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125, d11 + (d8 - d11) * 0.125, d12 + (d9 - d12) * 0.125, 5.0F, 30.0F);
            } else {
                super.onUpdateMoveHelper();
            }
        }
    }

    public void shootBubbles(EntityLivingBase target){
        Vec3d vec3d = this.getLookVec();
        //this.playSound(ModSounds.ICE, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        for (int k = 0; k < (3 + rand.nextInt(3)); ++k){
            EntityProjectileBubble iceDart = new  EntityProjectileBubble(this.world, this);
            iceDart.setPosition(this.posX + vec3d.x * 1.4D,this.posY + vec3d.y + this.height / 2, this. posZ + vec3d.z * 1.4D);
            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - iceDart.posY;
            double d2 = target.posZ - this.posZ;
            float f =MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;

            iceDart.shoot(d0, d1 + f, d2, 0.1F, 25.0F);
            this.world.spawnEntity(iceDart);
        }
    }

    public static class EntityAIGloopBubble extends EntityAIBase{
        private final EntityGloop gloop;
        private EntityLivingBase target;

        public EntityAIGloopBubble(EntityGloop fish){
            this.gloop = fish;
        }

        @Override
        public boolean shouldExecute() {
            this.target = gloop.getAttackTarget();
            if(this.gloop.isInWater() || this.gloop.isRiding() || this.gloop.getBalloon() > 0){
                return false;
            }
            return this.target != null && this.gloop.getBubbleCooldown() <= 0 && this.gloop.canEntityBeSeen(this.target);
        }

        public void startExecuting(){
            this.gloop.bubbleTicks = 40;
            this.gloop.playSound(ModSounds.GLOOP_BUBBLE, 1.0F, 0.8F + this.gloop.getRNG().nextFloat() * 0.4F);
            this.gloop.world.setEntityState(this.gloop, (byte)8);
        }

        @Override
        public void updateTask() {
            if(this.target != null) {
                this.gloop.getLookHelper().setLookPositionWithEntity(this.target, 180.0F, 180.0F);

                if(this.gloop.bubbleTicks == 20){
                    this.gloop.shootBubbles(this.target);
                }
            }
        }

        @Override
        public void resetTask() {
            this.gloop.bubbleTicks = 0;
            this.gloop.setBubbleCooldown( 200 + this.gloop.getRNG().nextInt(800));
            this.gloop.world.setEntityState(this.gloop, (byte)9);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.target != null && this.gloop.getBubbleCooldown() <= 0 && this.gloop.bubbleTicks > 0 && !this.gloop.isRiding();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 8){
            this.bubbleTicks = 40;
            this.playSound(ModSounds.GLOOP_BUBBLE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        }
        if(id == 9){
            this.bubbleTicks = 0;
        }
        super.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    public int getBubbleTicksClient(){
        return this.bubbleTicks;
    }

    public int getBubbleCooldown(){
        return this.dataManager.get(EntityGloop.BUBBLE_COOLDOWN);
    }
    public void setBubbleCooldown(int cooldown){
        this.dataManager.set(EntityGloop.BUBBLE_COOLDOWN, cooldown);
    }

    @Override
    public void dismountEntity(Entity entityIn)
    {

            double d0 = (double)(this.width / 2.0F + entityIn.width / 2.0F) + 0.4D;
            float f;

            if (entityIn instanceof EntityBoat)
            {
                f = 0.0F;
            }
            else
            {
                f = ((float)Math.PI / 2F) * (float)(this.getPrimaryHand() == EnumHandSide.RIGHT ? -1 : 1);
            }

            float f1 = -MathHelper.sin(-this.rotationYaw * 0.017453292F - (float)Math.PI + f);
            float f2 = -MathHelper.cos(-this.rotationYaw * 0.017453292F - (float)Math.PI + f);
            double d2 = Math.abs(f1) > Math.abs(f2) ? d0 / (double)Math.abs(f1) : d0 / (double)Math.abs(f2);
            double d3 = this.posX + (double)f1 * d2;
            double d4 = this.posZ + (double)f2 * d2;
            this.setPosition(d3, entityIn.posY + (double)entityIn.height + 0.001D, d4);

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GLOOP_IDLE;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.GLOOP_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GLOOP_DEATH;
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.FISH;
    }
}
