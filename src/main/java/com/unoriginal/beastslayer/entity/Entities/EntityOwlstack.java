package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.init.ModTriggers;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageDismountRidingEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EntityOwlstack extends EntityAnimal {
    private static final DataParameter<Integer> HAPPY = EntityDataManager.createKey(EntityOwlstack.class, DataSerializers.VARINT);
    private int happyTime;
    private int maxHappyTime = 20000 + rand.nextInt(4000);
    private UUID friendUUID;
    private EntityLivingBase friend;
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Owlstack");

    public EntityOwlstack(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1F);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.STICK, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityRabbit.class, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAPPY, 0);
    }

    public float getEyeHeight() {
        return this.height / 2;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }
    public void onUpdate(){
        Entity entity = this.getRidingEntity();
        if (this.isRiding() && entity != null && this.getHappyTime() >= 0 && entity instanceof EntityLivingBase && !this.world.isRemote) {
            EntityLivingBase rider = (EntityLivingBase) this.getRidingEntity();
            if (rider != null && !(rider instanceof EntityOwlstack)) {
                if(this.getHappyTime() > this.getMaxHappyTime()) {
                 //   this.dismountEntity(entity);
                   this.dismountRidingEntity();
                    this.resetRiding();
                 //   entity.removePassengers();
                }
                else if(rider.isElytraFlying()){
                   // this.dismountEntity(entity);
                    this.dismountRidingEntity();
                   this.resetRiding();
                }
                else if ( this.isInWater()){
                  //  this.dismountEntity(entity);
                    this.dismountRidingEntity();
                    this.resetRiding();
                  //  entity.removePassengers();
                }
                else if(rider.isSneaking()){
                  //  this.dismountEntity(entity);
                    this.dismountRidingEntity();
                   this.resetRiding();
                   // entity.removePassengers();
                }
            }
            else if((rider == null || this.getFriend() == null) && this.isRiding() && !(rider instanceof EntityOwlstack)){
                this.dismountRidingEntity();
                this.resetRiding();
            }

        }
        super.onUpdate();
    }

    @Override
    public boolean canRiderInteract()
    {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        if (!this.isBeingRidden() && !this.isRiding()) {
            if (this.ticksExisted % 2000 == 0) {
                List<EntityOwlstack> list = this.world.getEntitiesWithinAABB(EntityOwlstack.class, this.getEntityBoundingBox().grow(10D));
                if (!list.isEmpty()) {
                    EntityOwlstack owlstack = list.get(this.world.rand.nextInt(list.size()));
                    if (this != owlstack) {
                        this.getNavigator().tryMoveToEntityLiving(owlstack, 1.0F);
                        if (this.getDistanceSq(owlstack) < 4) {
                            if (!owlstack.isBeingRidden()) {
                                this.startRiding(owlstack);
                            } else {
                                Entity top = getTopPassenger(owlstack);
                                if (top instanceof EntityOwlstack) {
                                    this.startRiding(top);
                                }
                            }
                        }
                    }
                }
            }
        }
        EntityLivingBase entity = this.getFriend();
        if (entity != null && this.getHappyTime() >= 0 && this.isHappy()) {
            this.happyTime++;
            int x = this.getPassengers().size() + 1;
            if (entity instanceof EntityPlayer && entity.isBeingRidden()) {
                EntityPlayer p = (EntityPlayer) entity;
                if (this.getRidingEntity() == p) {
                    if (!this.world.isRemote) {
                        p.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, x - 1, false, false));
                    }
                    if (world.isRemote && !p.capabilities.isFlying && p.motionY > -3.5d) {
                        if (p.motionY > 0) {
                            p.motionY -= 0.3d * x;
                        } else {
                            p.motionY *= 1.0d * x;
                        }
                    }
                }
            }
        } else if (this.getHappyTime() < 0) {
            this.happyTime++;
        }
    }
    //TODO: Code scream animation
    public void setFriend(@Nullable EntityLivingBase friend) {
        this.friend = friend;
        this.friendUUID = friend == null ? null : friend.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getFriend() {
        if (this.friend == null && this.friendUUID != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer) this.world).getEntityFromUuid(this.friendUUID);
            if (entity instanceof EntityLivingBase) {
                this.friend = (EntityLivingBase) entity;
            }
        }

        return this.friend;
    }

    public int getHappyTime(){
        return happyTime;
    }
    public int getMaxHappyTime(){
        return maxHappyTime;
    }

    public void resetRiding(){
        this.setFriend(null);
        this.setHappy(0);
        this.dismountRidingEntity();
       // this.removePassengers();
        BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageDismountRidingEntity(null, this));
        this.happyTime = -2000;
        this.maxHappyTime = 20000 + rand.nextInt(4000);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        float tilting = MathHelper.cos(this.ticksExisted * 0.4F * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(-1));
        float f = MathHelper.sin(this.renderYawOffset * 0.017453292F);
        float f1 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
        passenger.setPosition(this.posX + (tilting * f), this.posY + (double) (this.height - 0.2F), this.posZ - (tilting * f1));
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() != null) {
            return this.getRidingEntity().isSneaking() ? 0.2D : 0.4D;
        } else {
            return 0.0D;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isRiding() || this.isBeingRidden()) {
            this.dismountRidingEntity();
            this.removePassengers();
        }
        return super.attackEntityFrom(source, amount);
    }

    public boolean canMateWith(EntityAnimal otherAnimal) {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return this.height;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty() && !this.isHappy()) {
            if (itemstack.getItem() == Items.STICK && !this.world.isRemote) {

                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                if (this.rand.nextInt(3) == 0) {
                    this.world.setEntityState(this, (byte) 18);
                    this.setHappy(player.getEntityId());
                    this.happyTime = 0;
                    this.setFriend(player);
                }
                return true;
            }

        } else if (player.getHeldItemMainhand().isEmpty() && this.getFriend() == player && !this.getLeashed() && this.isHappy() && this.getHappyTime() >= 0 && !this.world.isRemote) {
            if (this.getRidingEntity() != null) {
                   this.dismountRidingEntity();
                   this.removePassengers();
            }
            this.startRidingTopEntity(player);
            ModTriggers.OWLSTACK_INTERACT.trigger((EntityPlayerMP)player);
            player.swingArm(hand);
            return true;
        }

        return super.processInteract(player, hand);
    }

    public void startRidingTopEntity(Entity entity)
    {
        Entity top = getTopPassenger(entity);
        if(top != null && !top.isDead)
        {
            if(top instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = (EntityPlayerMP)top;
                this.startRiding(player, true);
                this.getServer().getPlayerList().sendPacketToAllPlayers(new SPacketSetPassengers(player));
            }
            else
            {
                this.startRiding(top, true);
            }
            this.navigator.clearPath();
        }
    }

    public static Entity getTopPassenger(Entity entity) {
        Entity top = entity;
        while (entity.isBeingRidden()) {
            List<Entity> list = entity.getPassengers();
            if (!list.isEmpty()) {
                entity = list.get(0);
                top = entity;
            }
        }
        return top;
    }


    public boolean isHappy() {
        return this.dataManager.get(HAPPY) != 0;
    }

    @Nullable
    public void setHappy(int id) {
        this.dataManager.set(HAPPY, id);
    }

    public boolean canDespawn() {
        return false;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);


        int i = this.world.rand.nextInt(20);
        if (i >= 8 && i < 15) {
            Entity top = getTopPassenger(this);
            EntityOwlstack owlstack = new EntityOwlstack(world);
            owlstack.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            owlstack.onInitialSpawn(difficulty, null);
            this.world.spawnEntity(owlstack);
            owlstack.startRiding(top);
        } else if (i == 15) {
            Entity top = getTopPassenger(this);
            EntityOwlstack owlstack = new EntityOwlstack(world);
            owlstack.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            owlstack.onInitialSpawn(difficulty, null);
            this.world.spawnEntity(owlstack);
            owlstack.startRiding(top);

            EntityOwlstack owlstack2 = new EntityOwlstack(world);
            owlstack2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            owlstack2.onInitialSpawn(difficulty, null);
            this.world.spawnEntity(owlstack2);
            owlstack2.startRiding(top);
        } else {

        }
        return livingdata;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.friendUUID != null) {
            compound.setUniqueId("friend", this.friendUUID);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasUniqueId("friend")) {
            this.friendUUID = compound.getUniqueId("friend");
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    public boolean isMobNearby(){
        List<EntityMob> mobs = this.world.getEntitiesWithinAABB(EntityMob.class, this.getEntityBoundingBox().grow(6.0D));
        return !mobs.isEmpty();
    }
    @Override
    protected SoundEvent getAmbientSound() { return this.isMobNearby() ? ModSounds.OWLSTACK_SCREAM : ModSounds.OWLSTACK_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.OWLSTACK_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.OWLSTACK_DEATH; }

}
