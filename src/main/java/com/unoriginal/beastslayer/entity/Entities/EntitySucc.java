package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIAttackRangedStrafe;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIFollowFriend;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMeleeConditional;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIStalk;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModPotions;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

//I have become lust... the gooner of worlds -Unoriginal while very drunk
public class EntitySucc extends EntityMob implements IRangedAttackMob {
    private static final DataParameter<Integer> EAT = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SELL = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GIVE = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> BUFF = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> STALK = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FRIEND = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Optional<UUID>> FRIEND_UNIQUE_ID = EntityDataManager.createKey(EntitySucc.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private int friendlyTicks; //mayhaps I can reuse this one for potions too!
    private int spellTime;

    final Predicate<EntityLivingBase> targetSelector = target -> target != null && target.isPotionActive(ModPotions.CHARMED) && this.friendlyTicks <= 0 && !this.isStalking() && !this.isFriendly();
    public EntitySucc(World worldIn) {
        super(worldIn);
        this.setSize(1F, 3F);
        this.isImmuneToFire = true;

    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackRangedStrafe<>(this, 1.0D, this.world.getDifficulty()== EnumDifficulty.HARD ? 30 : 50, 20.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(5, new EntityAIMeleeConditional(this, 0.5D, true, this.targetSelector));
        this.tasks.addTask(6, new EntityAIFollowFriend(this, 1.0D, 8.0F, 2.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(10, new EntityAIStalk(this, 0.4D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntitySucc.class));
        this.targetTasks.addTask(2, new EntityAITargetAggro<>(this, EntityPlayer.class, false, this.targetSelector));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BUFF, 1);
        this.dataManager.register(STALK, Boolean.TRUE);
        this.dataManager.register(FRIEND, Boolean.FALSE);
        this.dataManager.register(FRIEND_UNIQUE_ID, Optional.absent());

        this.dataManager.register(EAT, 0);
        this.dataManager.register(SELL, 0);
        this.dataManager.register(GIVE, 0);
    }

    public boolean isLookingAtMe(EntityPlayer player) {
        Vec3d vec3d = player.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + (double)this.getEyeHeight() - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
        double d0 = vec3d1.lengthVector();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > 1.0D - 0.025D / d0 && player.canEntityBeSeen(this);
    }


    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.8D;
        }

        if(this.friendlyTicks < 8000 && this.isFriendly() && this.ticksExisted % 20 == 0){
            if(rand.nextInt(3)== 0 ){
                this.world.setEntityState(this, (byte)13);
            }
        }
        if(this.getEat() > 0){
            this.setEat(this.getEat() - 1);
        }
        if(this.getSell() > 0){
            this.setSell(this.getSell() - 1);
        }
        if(this.getGive() > 0){
            this.setGive(this.getGive() - 1);
        }
        if(this.isStalking()) {
            List<EntityLivingBase> list2 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(1.5D));
            if (!list2.isEmpty() && this.isStalking()) {
                for (EntityLivingBase entitylivingbase1 : list2) {
                    if (entitylivingbase1 instanceof EntityPlayer) {
                        List<EntitySucc> list3 = this.world.getEntitiesWithinAABB(EntitySucc.class, this.getEntityBoundingBox().grow(16D));
                        if(!list3.isEmpty()) {
                            boolean taken = false;
                            for (EntitySucc entitysucc : list3) {
                                if(entitysucc.isFriendly() && entitysucc.getFriendID() == entitylivingbase1.getUniqueID()) {
                                    taken = true;
                                    break;
                                }
                            }
                            if (!taken){
                                EntityPlayer entityplayer = (EntityPlayer) entitylivingbase1;

                                this.world.setEntityState(entityplayer, (byte)14);
                                this.friendlyTicks = 24000 + rand.nextInt(12000);
                                this.setFriend(true);
                                this.setFriendBy(entityplayer);
                                this.setFriendID(entityplayer.getUniqueID());

                                this.enablePersistence();
                                this.setStalking(false);
                            }
                        } else {
                            EntityPlayer entityplayer = (EntityPlayer) entitylivingbase1;

                            this.world.setEntityState(entityplayer, (byte)14);
                            this.friendlyTicks = 24000 + rand.nextInt(12000);
                            this.setFriend(true);
                            this.setFriendBy(entityplayer);
                            this.setFriendID(entityplayer.getUniqueID());
                            this.enablePersistence();
                            this.setStalking(false);
                        }
                    }
                }
            }
        }

        if(this.getRevengeTarget() != null && this.isFriendly()) {
            this.setRevengeTarget(null); //No targeting when friendly
        }

        if(this.getAttackTarget() != null) {
            EntityLivingBase e = this.getAttackTarget();
            if(this.getAttackTarget().isPotionActive(ModPotions.CHARMED)){

                if(this.rand.nextInt(3) == 0)
                {
                    this.world.setEntityState(this, (byte)15);
                }

                if (e.isPotionActive(ModPotions.CHARMED)) { //Should not happen a scenario where amp is null but who knows, better safe than sorry

                    int amp = e.getActivePotionEffect(ModPotions.CHARMED).getAmplifier();
                    if (amp > 0) {
                        double X = (this.posX - e.posX) * 0.005D;
                        double Y = (this.posY - e.posY + 1) * 0.005D;
                        double Z = (this.posZ - e.posZ) * 0.005D;
                        e.addVelocity(X, Y, Z);
                        e.velocityChanged = true;
                    }
                }

            }
        }
        if(this.friendlyTicks > 0 && !this.isStalking() && this.isFriendly() && !this.world.isRemote) {
            this.friendlyTicks--;
        }
        if(this.friendlyTicks <= 0 && !this.isStalking() && this.isFriendly() && !this.world.isRemote) {
            this.setFriend(false);
            this.setFriendID(null);
        }

        if (!this.world.isRemote && this.isFriendly() && !this.isStalking() && this.ticksExisted % 18000 == 0)
        {
            this.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 0.8F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ModItems.WEIRD_BOTTLE, 1);
        }

       /* BeastSlayer.logger.info(this.friendlyTicks + "friendlyTicks");
        BeastSlayer.logger.info(this.isStalking() + "isStalking");
        BeastSlayer.logger.info(this.isFriendly() + "isFriendly");
        BeastSlayer.logger.info(this.getGive() + "getGive");
        BeastSlayer.logger.info(this.getSell() + "getSell");
        BeastSlayer.logger.info(this.getEat() + "getEat");
*/
    }
    @Override
    public boolean canDespawn(){return !this.isFriendly() && this.isStalking();}

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.getTrueSource() instanceof EntityPlayer)
        {
            if(this.isFriendly()){
                return false;
            } else {
                return super.attackEntityFrom(source, amount);
            }
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }
    public void fall(float distance, float damageMultiplier)
    {
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        if (flag && entityIn instanceof EntityLivingBase)
        {
            if(((EntityLivingBase) entityIn).isPotionActive(ModPotions.CHARMED)) {
                flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f * 2F);
                if(this.getHealth() < this.getMaxHealth()) {
                    this.heal(6F);
                } else if(this.getHealth() >= this.getMaxHealth() && this.getBuff() <= 9) {
                    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", this.getBuff(), 0).setSaved(true));
                    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).setSaved(true));
                }
            }

        }

        return flag;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if(id == 13){
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY,  this.posX, this.posY + (double)this.height - 0.75D, this.posZ, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);

        }
        if(id == 14){
            for (int i = 0; i < this.rand.nextInt(5) + 10; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.HEART,  this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        if (id == 15)
        {
            this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        }
        if(id == 16)
        {
            for (int i = 0; i < this.rand.nextInt(35) + 10; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,  this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    private void setBuff(int i)
    {
        this.dataManager.set(BUFF, i);
    }
    public int getBuff()
    {
        return this.dataManager.get(BUFF);
    }

    @Override
    public float getEyeHeight() {
        return 1.9F;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (!this.world.isRemote) {
           // this.chainTicks = 20;
            //this.world.setEntityState(this, (byte)6);

           // entityarrow.setMob(true);
            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
            double d2 = target.posZ - this.posZ;
            EntityCharmChain entityarrow = new EntityCharmChain(world, this, (float)d0, (float)d1, (float)d2 );
        //    entityarrow.setPositionAndRotation(this.posX, this.posY + this.getEyeHeight(), this.posZ, this.rotationYaw, this.rotationPitch);
            entityarrow.setPosition(this.posX, this.posY + this.height / 2f, this.posZ);
            this.playSound(ModSounds.KUNAI, 0.8F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(entityarrow);

        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("friendTimer", this.friendlyTicks);

        compound.setInteger("buff", this.getBuff());

        if (this.getFriendID() == null)
        {
            compound.setString("FriendUUID", "");
        }
        else
        {
            compound.setString("FriendUUID", this.getFriendID().toString());
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        String s;

        if (compound.hasKey("FriendUUID", 8))
        {
            s = compound.getString("FriendUUID");
        }
        else
        {
            String s1 = compound.getString("Friend");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }
        if(compound.hasKey("friendlyTicks")){
            this.friendlyTicks = compound.getInteger("friendlyTicks");
        }

        if(compound.hasKey("buff")){
            this.setBuff(compound.getInteger("buff"));
        }

        if (!s.isEmpty())
        {
            try
            {
                this.setFriendID(UUID.fromString(s));
                this.setFriend(true);
            }
            catch (Throwable var4)
            {
                this.setFriend(false);
            }
        }

    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        if(!this.isFriendly()){
            return false;
        } else {
            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (this.getEat() <= 0)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }
                        this.friendlyTicks += 2000;
                        this.setEat(1800);
                        this.world.setEntityState(this, (byte)14);
                        this.heal((float)itemfood.getHealAmount(itemstack));
                        return true;
                    }
                }

                else if (itemstack.getItem() == Items.DIAMOND)
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }
                    this.world.setEntityState(this, (byte)14);
                    this.friendlyTicks += 16000;
                    this.setGive(4000);
                    return true;
                }
                //add "goodies"
            }
            else if(player.isSneaking() && this.getSell() <= 0){ //TODO add a sound for the exchange
                player.attackEntityFrom(DamageSource.causeMobDamage(this), 4F);
                if(!this.world.isRemote) {
                    player.addPotionEffect(new PotionEffect(this.randEffect(this.rand.nextInt(7)), 3000, 0));
                }
                this.world.playSound(null, this.getPosition(), ModSounds.SUCC_SUCK,  SoundCategory.HOSTILE, 1.2F, this.rand.nextFloat() * 0.2F + 0.8F);
                this.world.setEntityState(this, (byte)14);
                this.friendlyTicks += 8000;
                this.setSell(6000);
            }
        }
        return super.processInteract(player, hand);
    }

    public Potion randEffect (int random){
        Potion newPotion = null;
        switch (random)
        {
            case 0:
                newPotion = MobEffects.STRENGTH;
                break;
            case 1:
                newPotion = MobEffects.REGENERATION;
                break;
            case 2:
                newPotion = MobEffects.JUMP_BOOST;
                break;
            case 3:
                newPotion = MobEffects.FIRE_RESISTANCE;
                break;
            case 4:
                newPotion = MobEffects.WATER_BREATHING;
                break;
            case 5:
                newPotion = MobEffects.SPEED;
                break;
            case 6:
                newPotion = MobEffects.INVISIBILITY;
                break;
        }
        return newPotion;
    }

    @Nullable
    public UUID getFriendID()
    {
        return (UUID)((Optional)this.dataManager.get(FRIEND_UNIQUE_ID)).orNull();
    }

    public void setFriendID(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(FRIEND_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    public void setFriendBy(EntityPlayer player)
    {
        this.setFriend(true);
        this.setFriendID(player.getUniqueID());
    }

    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getFriendID();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    public boolean isFriend(EntityLivingBase entityIn)
    {
        return entityIn == this.getOwner();
    }

    public boolean isFriendly()
    {
        return this.dataManager.get(FRIEND);
    }

    public void setFriend(boolean tamed)
    {
        this.dataManager.set(FRIEND, tamed);
    }

    public boolean isStalking(){
        return this.dataManager.get(STALK);
    }
    public void setStalking(boolean stalk){
        this.dataManager.set(STALK, stalk);
    }

    public boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
        this.world.setEntityState(this, (byte)16);
        this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_BAT_TAKEOFF, this.getSoundCategory(), 0.5F, 1.0F);
        for (int l = 0; l <= 5; ++l) {
            if (isTeleportFriendlyBlock(d0, d1, d2)) {
                this.setPosition(d0, d1, d2);
                break;
            }
        }
        return true;
    }

    protected boolean isTeleportFriendlyBlock(double x, double y, double z)
    {
        BlockPos blockpos = new BlockPos(x , y, z);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2) ) && this.world.isAirBlock(blockpos.up(3));
    }

    public void setEat(int cooldown){
        this.dataManager.set(EAT, cooldown);
    }
    public int getEat(){
        return this.dataManager.get(EAT);
    }


    public void setSell(int cooldown){
        this.dataManager.set(SELL, cooldown);
    }
    public int getSell(){
        return this.dataManager.get(SELL);
    }

    public void setGive(int cooldown){
        this.dataManager.set(GIVE, cooldown);
    }

    public int getGive(){
        return this.dataManager.get(GIVE);
    }

    public class EntityAITargetAggro<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        private final EntitySucc tameable;

        public EntityAITargetAggro(EntitySucc entityIn, Class<T> classTarget, boolean checkSight, Predicate <? super T > targetSelector)
        {
            super(entityIn, classTarget, 10, checkSight, false, targetSelector);
            this.tameable = entityIn;
        }

        public boolean shouldExecute()
        {
            return !this.tameable.isFriendly() && !this.tameable.isStalking() && super.shouldExecute();
        }
    }

    public void giveXpBonus(){
        this.friendlyTicks += 400;
        if(this.getHealth() < this.getMaxHealth()) {
            this.heal(6F);
        } else if(this.getHealth() >= this.getMaxHealth() && this.getBuff() <= 9) {
            this.setBuff(this.getBuff() + 1);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", this.getBuff(), 0).setSaved(true));
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).setSaved(true));
            this.setHealth(this.getMaxHealth());
        }
    }

    protected SoundEvent getAmbientSound()
    {
        return ModSounds.SUCC_AMB;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSounds.SUCC_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return ModSounds.SUCC_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 1.3F;
    }
}
