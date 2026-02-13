package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveControl;
import com.unoriginal.beastslayer.entity.Entities.ai.AIMoveRandom;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageDismountRidingEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityMosquito extends EntityAnimal {
    private static final DataParameter<Integer> FAT = EntityDataManager.createKey(EntityMosquito.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SUCK = EntityDataManager.createKey(EntityMosquito.class, DataSerializers.BOOLEAN);
    private float takenXP;

    public EntityMosquito(World p_i1738_1_) {
        super(p_i1738_1_);
        this.setSize(0.8F, 0.8F);
        this.moveHelper = new AIMoveControl(this);
        this.experienceValue = 1;
        this.takenXP = 0.0F;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityMosquito(this.world);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AIMoveRandom(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityLivingBase.class,Predicate -> this.getStoredXP() >= 6, 6.0F, 0.8D, 1.2D ));
        this.tasks.addTask(4, new AIChargeAttack());
        this.tasks.addTask(5, new EntityAITempt(this, 1D, Items.REEDS, false));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 1.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMob.class));
        this.targetTasks.addTask(0, (new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true)));
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.REEDS && this.getStoredXP() != 0;
    }

    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((10.0D));
            this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    /*protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }*/

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FAT, 0);
        this.dataManager.register(SUCK, false);
    }

    public void onLivingUpdate()
    {
        if(this.getRidingEntity() != null){
            Vec3d vec3d = this.getRidingEntity().getLookVec();
            this.setPositionAndRotation(this.getRidingEntity().posX + vec3d.x * 1.4D, this.getRidingEntity().posY + this.getRidingEntity().getEyeHeight(), this.getRidingEntity().posZ + vec3d.z * 1.4D, this.getRidingEntity().rotationYaw, this.getRidingEntity().rotationPitch);
        }
        if((this.ticksExisted % 100 == 0 || this.ticksExisted == 1) && this.isEntityAlive()) {
            this.playSound(ModSounds.MOSQ_LOOP, 1.7F,  1F);
        }

        if (this.world.isRemote && this.getStoredXP() > 0) {
            if(this.ticksExisted % 20 == 0) {
                this.world.spawnParticle(ModParticles.DRIP, this.posX + (this.rand.nextDouble() - 0.25D) * (double) this.width, this.posY + (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.25D) * (double) this.width, 0.59D, 0.99D, 0D, 1, 2);
            }

        }
        super.onLivingUpdate();
    }

    public void onUpdate()
    {
        super.onUpdate();
        this.setNoGravity(true);


        if(this.isSuckingSrv() && !this.world.isRemote) {
            if(this.ticksExisted % 3 == 0) {
                this.playSound(ModSounds.MOSQ_GULP, 2.5F,  1F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F,  (this.rand.nextFloat() - this.rand.nextFloat()) * 0.35F + 0.9F);
            }
        }
    }

    @Override
    public void fall(float p_180430_1_, float p_180430_2_) {

    }

    public void resetRiding(EntityPlayer player){
        BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageDismountRidingEntity(player, this));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if(entityIn instanceof EntityPlayer && this.getStoredXP() < 6) {

            EntityPlayer entityplayer = (EntityPlayer)entityIn;
            if(entityplayer.experienceLevel > 6) {



                decreaseExp(entityplayer, 2);
                if(entityplayer instanceof EntityPlayerMP && !entityplayer.isBeingRidden()) {
                    EntityPlayerMP entityplayer1 = (EntityPlayerMP)entityplayer;
                    this.startRiding(entityplayer1, true);
                    this.getServer().getPlayerList().sendPacketToAllPlayers(new SPacketSetPassengers(entityplayer1));
                }
                this.setSucking(true);
                this.enablePersistence();
            }

        }
        if(entityIn == this.getRidingEntity()){
            return false;
        } else {
            return super.attackEntityAsMob(entityIn);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.getStoredXP() > 3){
            return super.attackEntityFrom(source, amount * 2);
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void updateRidden() {
        Entity entity = this.getRidingEntity();


        if (entity != null && !this.world.isRemote)
        {

            if(entity instanceof EntityPlayer && this.getStoredXP() < 6) {

                EntityPlayer entityplayer = (EntityPlayer)entity;
                decreaseExp(entityplayer, 1);
            } else {
                this.dismountRidingEntity();
            }
        }
        super.updateRidden();
    }

    public void dismountRidingEntity()
    {
        Entity entity = this.getRidingEntity();
        super.dismountRidingEntity();

        if (entity != null && entity != this.getRidingEntity() && !this.world.isRemote)
        {
            this.dismountEntity(entity);
        }
        if(entity instanceof EntityPlayerMP){
            EntityPlayerMP entityplayer = (EntityPlayerMP) entity;
            this.resetRiding(entityplayer);
        }
        this.setSucking(false);
    }

    public void decreaseExp(EntityPlayer player, float amount)
    {
        if (player.experienceTotal - amount <= 0) // If not enough levels or will be negative
        {
            player.experienceLevel = 0;
            player.experience = 0;
            player.experienceTotal = 0;
            return;
        }

        player.experienceTotal -= amount;

        if (player.experience * (float)player.xpBarCap() < amount) // Removing experience within current level to floor it to player.experience == 0.0f
        {
            amount -= player.experience * (float)player.xpBarCap();
            player.experience = 1.0f;
            player.experienceLevel--;
            this.onLostLevel();

        }

        while (player.xpBarCap() < amount) // Removing whole levels
        {
            amount -= player.xpBarCap();
            player.experienceLevel--;
            this.onLostLevel();
        }
        player.experience -= amount / (float)player.xpBarCap();
        this.updateXp(amount);
        // Removing experience from remaining level
    }
    public void onLostLevel(){
        this.setStoredXP(this.getStoredXP() + 1);
    }

    public void updateXp(float val){
        this.takenXP += val;
    }
    public float getTakenXp(){
        return this.takenXP;
    }

    public void setStoredXP(float amount){
        this.dataManager.set(FAT, (int)amount);
    }
    public int getStoredXP(){
        return this.dataManager.get(FAT);
    }

    public void setSucking(boolean b)
    {
        this.dataManager.set(SUCK, b);
    }
    public boolean isSuckingSrv(){
        return this.dataManager.get(SUCK);
    }
    @SideOnly(Side.CLIENT)
    public boolean isSucking()
    {
        return this.dataManager.get(SUCK);
    }

    @SideOnly(Side.CLIENT)
    public int getStoredXPClient(){
        return this.dataManager.get(FAT);
    }

    @SideOnly(Side.CLIENT)
    protected int getExperiencePoints(EntityPlayer player)
    {
        int fat = this.getStoredXP();
        //100 is the max that players can drop
        return this.experienceValue + (int)this.getTakenXp();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("stolenXP", this.getStoredXP());
        compound.setFloat("stolenExperience", this.takenXP);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("stolenXP")) {
            this.setStoredXP(compound.getInteger("stolenXP"));
        }
        if(compound.hasKey("stolenExperience")) {
            this.takenXP = compound.getFloat("stolenExperience");
        }
    }

    public double getYOffset() {
        return -0.5D;
    }

    @Override
    protected boolean canDespawn() {
        return this.getStoredXP() == 0;
    }

    public boolean isOnLadder()
    {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return !this.getLeashed();
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getEyeHeight() {
        return 0.5F;
    }

    class AIChargeAttack extends EntityAIBase
    {
        public AIChargeAttack()
        {
            this.setMutexBits(1);
        }

        public boolean shouldExecute()
        {
            if(EntityMosquito.this.isChild()){
                return false;
            }
            if (EntityMosquito.this.getAttackTarget() != null && EntityMosquito.this.getMoveHelper().isUpdating() &&EntityMosquito.this.rand.nextInt(5) == 0)
            {
                return EntityMosquito.this.getDistanceSq(EntityMosquito.this.getAttackTarget()) > 4.0D && EntityMosquito.this.getStoredXP() < 6;
            }
            else
            {
                return false;
            }
        }

        public boolean shouldContinueExecuting()
        {
            return EntityMosquito.this.getMoveHelper().isUpdating() && EntityMosquito.this.getAttackTarget() != null && EntityMosquito.this.getAttackTarget().isEntityAlive();
        }

        public void startExecuting()
        {
            EntityLivingBase entitylivingbase = EntityMosquito.this.getAttackTarget();
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityMosquito.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        }

        public void updateTask()
        {
            EntityLivingBase entitylivingbase = EntityMosquito.this.getAttackTarget();

            if (EntityMosquito.this.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox()))
            {
                EntityMosquito.this.attackEntityAsMob(entitylivingbase);
            }
            else
            {
                double d0 = EntityMosquito.this.getDistanceSq(entitylivingbase);

                if (d0 < 9.0D)
                {
                    Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                    EntityMosquito.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                }
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.MOSQ_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MOSQ_DEATH;
    }
}
