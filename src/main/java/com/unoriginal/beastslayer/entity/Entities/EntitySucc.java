package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.*;
import com.unoriginal.beastslayer.init.*;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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
import net.minecraft.util.math.AxisAlignedBB;
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
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Succubus");
    private static final DataParameter<Boolean> BED = EntityDataManager.createKey(EntitySucc.class, DataSerializers.BOOLEAN);
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
    public int dropTime;
    private int spellCooldown;
    protected EntityAICustomSit aiSit;
    private static final Set<Item> GIVE_ITEMS = Sets.newHashSet(Items.DIAMOND, Items.GOLD_INGOT, Items.EMERALD);

    final Predicate<EntityLivingBase> targetSelector = target -> target != null && (target.isPotionActive(ModPotions.CHARMED) && this.friendlyTicks <= 0 && !this.isStalking() && !this.isFriendly());
    final Predicate<EntityLivingBase> targetSelector2 = target -> this.getHealth() < this.getMaxHealth() / 4F || (this.getSell() <=-8000 && this.isFriendly());

    public EntitySucc(World worldIn) {
        super(worldIn);
        this.setSize(0.95F, 2.8F);
        this.isImmuneToFire = true;
        this.experienceValue = 15;
        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
        this.dropTime = rand.nextInt(8000) + 8000;

    }

    protected void initEntityAI() {
        this.aiSit = new EntityAICustomSit(this);
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAISuccSpell(this));
        this.tasks.addTask(3, new EntityAIAttackRangedStrafe<>(this, 1.0D, this.world.getDifficulty()== EnumDifficulty.HARD ? 100 : 130, 20.0F));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new AIMoveToBed(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMeleeConditional(this, 0.7D, true, this.targetSelector));
        this.tasks.addTask(6, new EntityAIFollowFriend(this, 1.0D, 8.0F, 2.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(4, new EntityAIStalk(this, 0.4D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntitySucc.class));
        this.targetTasks.addTask(2, new EntityAITargetAggro<>(this, EntityPlayer.class, false, this.targetSelector));
        this.targetTasks.addTask(3, new EntityAITargetAggro<>(this, EntityAnimal.class , false, this.targetSelector2));
        this.targetTasks.addTask(3, new EntityAITargetAggro<>(this, EntityVillager.class, false, this.targetSelector2));
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
        this.dataManager.register(BED, Boolean.FALSE);
    }

    public boolean isLookingAtMe(EntityPlayer player) {
        Vec3d vec3d = player.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + (double)this.getEyeHeight() - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
        double d0 = vec3d1.lengthVector();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > 1.0D - 0.025D / d0;
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
        double d3 = this.isBed() ? 1D : 2.8D;
        double d4 = this.width / 2F;
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d4, this.posY, this.posZ - d4, this.posX + d4, this.posY + d3, this.posZ + d4));

        if(this.isBed()){
            IBlockState iBlockState = this.world.getBlockState(this.getPosition().down());
            if(iBlockState.getBlock() == Blocks.BED){

                    if(this.getFriend() != null && this.getFriend() instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) this.getFriend();
                        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
                            ModTriggers.SUCCUBUS_BED.trigger((EntityPlayerMP) player);

                        }
                    }
                    this.setRotation(iBlockState.getValue(BlockBed.FACING).rotateYCCW().getHorizontalAngle(), this.rotationPitch);
                    this.rotationYawHead = this.rotationYaw;
                  //  this.rotationYaw = iBlockState.getValue(BlockBed.FACING).getHorizontalAngle();

            }/* else {
                this.setBed(false);
            }*/
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
                    int amp = this.getGive() <= -15000 ? 0 : 1;
                    this.getFriend().addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, amp));
                    if(this.isSitting() && !this.isBed()) {
                        this.aiSit.setSitting(false);
                    }
                }
            }
        }
        if(this.getEat() > -1200  && !this.isStalking() && this.isFriendly() && this.getFriend() != null){
            this.setEat(this.getEat() - 1);
        }
        if(this.getSell() > -8000  && !this.isStalking() && this.isFriendly() && this.getFriend() != null){
            this.setSell(this.getSell() - 1);
        }
        if(this.getGive() > -15000 && !this.isStalking() && this.isFriendly() && this.getFriend() != null){
            this.setGive(this.getGive() - 1);
        }
        if(this.isStalking() && !this.isFriendly() && !this.world.isRemote) {
            List<EntityPlayer> list2 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(1.5D));
            if (!list2.isEmpty() && this.isStalking()) {
                for (EntityPlayer  entityplayer : list2) {
                        List<EntitySucc> list3 = this.world.getEntitiesWithinAABB(EntitySucc.class, this.getEntityBoundingBox().grow(64D));
                        if(!list3.isEmpty()) {
                            boolean taken = false;
                            for (EntitySucc entitysucc : list3) {
                                if(entitysucc.isFriendly() && entitysucc.getFriendID() == entityplayer.getUniqueID()) {
                                    taken = true;
                                    break;
                                }
                            }
                            if (!taken && entityplayer.getHeldItemOffhand().getItem() != ModItems.GARLIC_NECK && entityplayer.getHeldItemMainhand().getItem() != ModItems.GARLIC_NECK){

                                    this.spawnHeartParticles();
                                    this.world.setEntityState(this, (byte) 14);
                                    this.friendlyTicks = 24000 + rand.nextInt(12000);
                                    this.setFriend(true);
                                    this.setFriendBy(entityplayer);
                                    this.setFriendID(entityplayer.getUniqueID());

                                    this.enablePersistence();
                                    this.setStalking(false);

                            }
                        } else {
                            if(entityplayer.getHeldItemOffhand().getItem() != ModItems.GARLIC_NECK && entityplayer.getHeldItemMainhand().getItem() != ModItems.GARLIC_NECK) {
                                this.spawnHeartParticles();
                                this.world.setEntityState(this, (byte) 14);
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
                    this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
                    this.world.setEntityState(this, (byte)15);
                }

                if (e.isPotionActive(ModPotions.CHARMED)) { //Should not happen a scenario where amp is null but who knows, better safe than sorry

                    int amp = e.getActivePotionEffect(ModPotions.CHARMED).getAmplifier();
                    if (amp > 0) {
                        double X = (this.posX - e.posX) * 0.008D;
                        double Y = (this.posY - e.posY + 1) * 0.008D;
                        double Z = (this.posZ - e.posZ) * 0.008D;
                        e.addVelocity(X, Y, Z);
                        e.velocityChanged = true;
                    }
                }

            }
        }
        if(this.friendlyTicks >= 0 && !this.isStalking() && this.isFriendly() && this.getFriend() != null) {
            this.friendlyTicks--;
        }
        if(this.friendlyTicks <= 0 && !this.isStalking() && this.isFriendly() && !this.world.isRemote) {
            this.setFriend(false);
            this.setFriendID(null);
        }

        if (!this.world.isRemote && this.isFriendly() && !this.isStalking() && --this.dropTime <= 0)
        {
            this.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 0.8F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ModItems.WEIRD_BOTTLE, 1);
            this.dropTime = this.rand.nextInt(8000) + 8000;
        }

    }
    public void spawnHeartParticles(){

            for (int i = 0; i < this.rand.nextInt(5) + 10; ++i) {
                this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
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
                if(source.getTrueSource() instanceof EntityPlayer) {
                    EntityPlayer entityplayer = (EntityPlayer)source.getTrueSource();

                    if(this.getDistanceSq(entityplayer) < 8D && !this.world.isRemote && rand.nextInt(3) == 0) {
                        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                        entityareaeffectcloud.setRadius(4F);
                        entityareaeffectcloud.setRadiusOnUse(-0.1F);
                        entityareaeffectcloud.setWaitTime(20);
                        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 70));
                        entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
                        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

                        this.world.spawnEntity(entityareaeffectcloud);

                    }
                }
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
            this.spawnHeartParticles();
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
        if(id == 19){
            this.friendlyTicks = 0;
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
        return this.isBed()? 0.75F : 1.9F;
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

        compound.setInteger("dropTime", this.dropTime);

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
        compound.setBoolean("bed", this.isBed());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        String s;
        if (this.aiSit != null)
        {
            this.aiSit.setSitting(compound.getBoolean("Sitting"));
        }
        if(compound.hasKey("bed")) {
            this.setBed(compound.getBoolean("bed"));
        }
        if(compound.hasKey("magic")){
            this.spellTime = compound.getInteger("magic");
        }
        if(compound.hasKey("dropTime")){
            this.dropTime = compound.getInteger("dropTime");
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
                if(itemstack.getItem() == ModItems.GARLIC_NECK && !this.world.isRemote){
                    this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 0.3F + this.rand.nextFloat() * 0.5F, this.rand.nextFloat() * 0.7F + 0.3F);
                    this.friendlyTicks = 0;
                    this.world.setEntityState(this, (byte)19);
                    this.playHurtSound(DamageSource.GENERIC);
                }
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (this.getHealth() < this.getMaxHealth() || this.getEat() <= 0)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }
                        if (this.getEat() <= 0) {
                            this.friendlyTicks += 2000;
                            this.setEat(1800);
                        }
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
                if(this.isBed()){
                    this.setBed(false);
                }
                this.isJumping = false;
                this.navigator.clearPath();
            }

            else if(player.isSneaking() && this.getSell() <= 0){
                player.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 4F);
                if(!this.world.isRemote) {
                    int i = this.rand.nextInt(7);
                    player.addPotionEffect(new PotionEffect(this.randEffect(i), i == 0 ? 100 : 3000, 0));
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
                newPotion = MobEffects.NAUSEA;
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
        if(this.getSell() <= 0){
            this.setSell(this.getSell() + 300);
        }
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
        int spacing = BeastSlayerConfig.InnSpacing;
        int separation = BeastSlayerConfig.InnSeparation;
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

        this.experienceValue = (int)(this.experienceValue + this.getBuff() * 2F);


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

    public void teleport(){
        this.world.setEntityState(this, (byte)16);
        this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_BAT_TAKEOFF, this.getSoundCategory(), 0.5F, 1.0F);
    }

    public void setSitting(boolean sitting)
    {
        this.dataManager.set(WAIT, sitting);
    }

    public double getYOffset() {
        return -0.5D;
    }

    @Override
    public void addPotionEffect(PotionEffect potioneffectIn) {
        Potion potion = potioneffectIn.getPotion();
        if(potion == MobEffects.WITHER) {

        }else {
            super.addPotionEffect(potioneffectIn);
        }

    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return !this.getLeashed() && this.isFriend(player);
    }

    public int getMood(){
        return this.friendlyTicks;
    }

    @SideOnly(Side.CLIENT)
    public boolean isBedClient(){
        return this.dataManager.get(BED);
    }
    public boolean isBed(){
        return this.dataManager.get(BED);
    }
    public void setBed(boolean bed){
        this.dataManager.set(BED, bed);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LOOT;
    }
}
