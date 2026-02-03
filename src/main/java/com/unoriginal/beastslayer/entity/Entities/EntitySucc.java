package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.*;
import com.unoriginal.beastslayer.init.*;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

//I have become lust... the gooner of worlds -Unoriginal while very drunk
public class EntitySucc extends EntityMob implements IRangedAttackMob {
    private static final DataParameter<Boolean> WAIT = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> EAT = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SELL = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GIVE = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> BUFF = EntityDataManager.createKey(EntitySucc.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> STALK = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FRIEND = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Optional<UUID>> FRIEND_UNIQUE_ID = EntityDataManager.createKey(EntitySucc.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private int friendlyTicks; //mayhaps I can reuse this one for potions too!
    private int spellTime;
    private int dropTime;
    private int spellCooldown;
    protected EntityAICustomSit aiSit;
    private static final Set<Item> GIVE_ITEMS = Sets.newHashSet(Items.DIAMOND, Items.GOLD_INGOT, Items.EMERALD);

    final Predicate<EntityLivingBase> targetSelector = target -> target != null && target.isPotionActive(ModPotions.CHARMED) && this.friendlyTicks <= 0 && !this.isStalking() && !this.isFriendly();
    public EntitySucc(World worldIn) {
        super(worldIn);
        this.setSize(0.95F, 2.8F);
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
        this.dropTime = rand.nextInt(8000) + 8000;

    }

    protected void initEntityAI() {
        this.aiSit = new EntityAICustomSit(this);
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAISuccSpell(this));
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

        this.dataManager.register(WAIT, Boolean.FALSE);
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
        if(this.spellTime > 0){
            --this.spellTime;
        }

        if(this.spellCooldown > 0){
            --this.spellCooldown;
        }

        if(this.friendlyTicks < 6000 && this.isFriendly() && this.ticksExisted % 20 == 0){

            this.world.setEntityState(this, (byte)13);

        }



        if(this.world.isRemote && this.ticksExisted % 20 == 0 && this.getSpellTime() > 0){
            for (int i = 0; i < this.rand.nextInt(35) + 10; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.6D, this.posY + 1.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D);
            }
            this.world.spawnParticle(ModParticles.SPELL, this.posX, this.posY+ 0.1F, this.posZ, 0D, 0D ,0D );
        }

        if(this.isFriendly() && this.getFriend() != null){
            if(MathHelper.sqrt(this.getDistanceSq(this.getFriend())) < 5D){
                if(this.ticksExisted % 20 == 0 && this.world.isRemote) {
                    this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.6D, this.posY + 1.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D);
                }
                if (!this.world.isRemote) {
                    this.getFriend().addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 1));
                    this.aiSit.setSitting(false);
                }
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
        if(this.isStalking() && !this.isFriendly()) {
            List<EntityLivingBase> list2 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(1.5D));
            if (!list2.isEmpty() && this.isStalking()) {
                for (EntityLivingBase entitylivingbase1 : list2) {
                    if (entitylivingbase1 instanceof EntityPlayer) {
                        List<EntitySucc> list3 = this.world.getEntitiesWithinAABB(EntitySucc.class, this.getEntityBoundingBox().grow(64D));
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
        if(this.friendlyTicks > 0 && !this.isStalking() && this.isFriendly() && !this.world.isRemote && this.getFriend() != null) {
            this.friendlyTicks--;
        }
        if(this.friendlyTicks <= 0 && !this.isStalking() && this.isFriendly() && !this.world.isRemote) {
            this.setFriend(false);
            this.setFriendID(null);
        }

        if (!this.world.isRemote && this.isFriendly() && !this.isStalking() && --this.dropTime < 0)
        {
            this.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 0.8F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ModItems.WEIRD_BOTTLE, 1);
            this.dropTime = this.rand.nextInt(8000) + 8000;
        }

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
        if(id==10){
            this.spellTime = 80;
        }
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
            this.world.setEntityState(this, (byte)10);
            this.setSpellTime(80);

           // entityarrow.setMob(true);
            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
            double d2 = target.posZ - this.posZ;
            EntityCharmChain entityarrow = new EntityCharmChain(world, this, (float)d0, (float)d1, (float)d2 );
        //    entityarrow.setPositionAndRotation(this.posX, this.posY + this.getEyeHeight(), this.posZ, this.rotationYaw, this.rotationPitch);
            entityarrow.setPosition(this.posX, this.posY + this.height / 2f, this.posZ);
            this.world.playSound(null, this.posX, this.posY, this.posZ ,ModSounds.SUCC_SPELL, SoundCategory.HOSTILE, 0.8F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(entityarrow);

        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("friendlyTicks", this.friendlyTicks);

        compound.setBoolean("stalk", this.isStalking());

        compound.setInteger("buff", this.getBuff());

        compound.setInteger("magic", this.spellTime);

        compound.setInteger("drop", this.dropTime);

        compound.setInteger("cooldown", this.spellCooldown);

        if (this.getFriendID() == null)
        {
            compound.setString("FriendUUID", "");
        }
        else
        {
            compound.setString("FriendUUID", this.getFriendID().toString());
        }

        compound.setBoolean("Sitting", this.isSitting());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        String s;
        if (this.aiSit != null)
        {
            this.aiSit.setSitting(compound.getBoolean("Sitting"));
        }
        if(compound.hasKey("magic")){
            this.spellTime = compound.getInteger("magic");
        }
        if(compound.hasKey("drop")){
            this.dropTime = compound.getInteger("drop");
        }
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
        if(compound.hasKey("stalk")){
            this.setStalking(compound.getBoolean("stalk"));
        }
        if(compound.hasKey("buff")){
            this.setBuff(compound.getInteger("buff"));
        }

        if(compound.hasKey("cooldown")){
            this.spellCooldown = compound.getInteger("cooldown");
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
            if (!itemstack.isEmpty() && this.isFriend(player))
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

                else if (GIVE_ITEMS.contains(itemstack.getItem()) && this.getGive() <= 0)
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }
                    this.world.setEntityState(this, (byte)14);
                    this.friendlyTicks += 10000;
                    this.setGive(8000);
                    return true;
                }
            }
            if (this.isFriend(player) && !this.world.isRemote && this.isSitting())
            {
                this.aiSit.setSitting(false);
                this.isJumping = false;
                this.navigator.clearPath();
            }

            else if(player.isSneaking() && this.getSell() <= 0){
                player.attackEntityFrom(DamageSource.causeMobDamage(this), 4F);
                if(!this.world.isRemote) {
                    player.addPotionEffect(new PotionEffect(this.randEffect(this.rand.nextInt(7)), 3000, 0));
                    if (player instanceof EntityPlayerMP && !this.world.isRemote){
                        ModTriggers.SUCCUBUS_BLOOD.trigger((EntityPlayerMP)player);

                    }
                }
                this.world.playSound(null, this.posX, this.posY, this.posZ , ModSounds.SUCC_SUCK, this.getSoundCategory(), 1.2F, this.rand.nextFloat() * 0.2F + 0.8F);
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
        if(p_184754_1_ != null){
            if(this.world.getPlayerEntityByUUID(p_184754_1_) != null){
                EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(p_184754_1_);
                if (entityplayer instanceof EntityPlayerMP && !this.world.isRemote){
                    ModTriggers.SUCCUBUS_FRIEND.trigger((EntityPlayerMP)entityplayer);

                }
            }
        }
    }

    public void setFriendBy(EntityPlayer player)
    {
        this.setFriend(true);
        this.setFriendID(player.getUniqueID());
    }

    @Nullable
    public EntityLivingBase getFriend()
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
        return entityIn == this.getFriend();
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

        for (int l = 0; l <= 5; ++l) {
            if (isTeleportFriendlyBlock(d0, d1, d2)) {
                this.world.setEntityState(this, (byte)16);
                if (!this.world.isRemote) {
                    this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_BAT_TAKEOFF, this.getSoundCategory(), 0.5F, 1.0F);
                }
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

    public static class EntityAITargetAggro<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
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
            if(this.getBuff() % 3  == 0) {
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).setSaved(true));
            }
            this.setHealth(this.getMaxHealth());
        }
    }

    protected SoundEvent getAmbientSound()
    {
        boolean b = this.isFriendly() && this.friendlyTicks < 6000;
        return b ? ModSounds.SUCC_SIGH : ModSounds.SUCC_AMB;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSounds.SUCC_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return ModSounds.SUCC_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.3F;
    }


    @Override
    public boolean getCanSpawnHere() {
        boolean b = false;

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                Chunk chunk = world.getChunkFromBlockCoords(this.getPosition());
                boolean validspawn = this.tavernPos(this.world, chunk.x + i, chunk.z + j);
                if(validspawn){
                    b = true;
                    break;
                }
            }
        }
        //  boolean b = chunk.equals(  TribeSavedData.loadData(this.world).getLocation());
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        //BeastSlayer.logger.debug(this.world.getBlockState(blockpos).getBlock());

        return b && super.getCanSpawnHere() && blockpos.getY() >= 50;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 3;
    }

    protected boolean tavernPos(World world, int chunkX, int chunkZ) {
        int spacing = BeastSlayerConfig.TavernSpacing;
        int separation = BeastSlayerConfig.TavernSeparation;
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= spacing - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random =  world.setRandomSeed(k, l, 10387312);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
        l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;

        if (i == k && j == l)
        {

            return world.getBiomeProvider().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, Lists.newArrayList(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST))); /*&& random.nextInt(20) == 0*/
        } else {

            return false;
        }
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        return false;
    }

    public int getSpellTime(){
        return this.spellTime;
    }
    @SideOnly(Side.CLIENT)
    public int getSpellTimeClient(){
        return this.spellTime;
    }
    public void setSpellTime(int spellTime){
        if(!this.world.isRemote) {
            this.playSound(ModSounds.SUCC_SPELL, 1.2F, 1.0F - (this.getRNG().nextFloat() * 0.1F) + 0.1F);
        }
        this.spellTime = spellTime;
    }

    public int getSpellCooldown(){
        return this.spellCooldown;
    }
    public void setSpellCooldown(int spellCooldown){
        this.spellCooldown = spellCooldown;

    }
    public void setSpellTimeId(int spell, int otherid) {
        this.setSpellTime(spell);
        this.world.setEntityState(this, (byte)otherid);
    }

    protected int getExperiencePoints(EntityPlayer player)
    {

        this.experienceValue = (int)((float)this.experienceValue + this.getBuff() * 2F);


        return super.getExperiencePoints(player);
    }

    public EntityAICustomSit getAISit()
    {
        return this.aiSit;
    }

    public boolean isSitting()
    {
        return this.dataManager.get(WAIT);
    }

    public void setSitting(boolean sitting)
    {
        this.dataManager.set(WAIT, sitting);
    }

    public double getYOffset() {
        return -0.5D;
    }
}
