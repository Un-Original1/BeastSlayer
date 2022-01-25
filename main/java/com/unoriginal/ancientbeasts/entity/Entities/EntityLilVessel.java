package com.unoriginal.ancientbeasts.entity.Entities;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.ai.EntityAIPatrol;
import com.unoriginal.ancientbeasts.entity.Entities.ai.EntityAIUseBow;
import com.unoriginal.ancientbeasts.init.ModItems;
import com.unoriginal.ancientbeasts.init.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityLilVessel extends EntityTameable implements IRangedAttackMob{
    private int itemDamage;
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityLilVessel.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.createKey(EntityLilVessel.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PATROL = EntityDataManager.createKey(EntityLilVessel.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> KILL_PLAYER = EntityDataManager.createKey(EntityLilVessel.class, DataSerializers.BOOLEAN);
    public EntityLilVessel(World worldIn) {
        super(worldIn);
        this.setSize(0.6F,  1.2F);
        this.setTamed(false);
    }
    public EntityLilVessel(World worldIn, EntityPlayer ownerIn, int itemDamageIn) {
        super(worldIn);
        this.setSize(0.6F,  1.2F);
        this.setTamedBy(ownerIn);
        this.itemDamage = itemDamageIn;
    }
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityCreeper.class, 8F, 1.0F, 1.0F));
        this.tasks.addTask(4, new EntityAIUseBow<>(this, 1.0D, 20, 15.0F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.5D, true));
        this.tasks.addTask(5, new EntityAIPatrol(this, 1.0D));
        this.tasks.addTask(6, new AIFollowOwnerPatrol(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityMob.class, false));
        this.targetTasks.addTask(5, new EntityLilVessel.AIVesselTarget<>(this, EntityPlayer.class));
    }

    @Override
    public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper)) {
            if (target instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) target;
                if (p == this.getOwner() || ((EntityPlayer) owner).canAttackPlayer(p)) {
                    return false;
                } else return this.shouldKillPlayer() && p != this.getOwner();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag)
        {
            this.swingArm(this.getActiveHand());
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isEntityInvulnerable(source)){
            return false;
        }
        else {
            for(int i = 0; i < 5; i++) {
                ItemStack stack = this.getItemStackFromSlot(getSlot(i));
                if (!stack.isEmpty()) {
                    stack.damageItem(1, this);
                    if (stack.getItemDamage() >= stack.getMaxDamage())
                    {
                        this.renderBrokenItemStack(stack);
                        this.setItemStackToSlot(getSlot(i), ItemStack.EMPTY);
                    }
                }
            }
            return super.attackEntityFrom(source, amount);
        }
    }
    public EntityEquipmentSlot getSlot(int slotIn){
        switch (slotIn) {
            case 1 :
            return EntityEquipmentSlot.HEAD;
            case 2 :
                return EntityEquipmentSlot.CHEST;
            case 3 :
                return EntityEquipmentSlot.LEGS;
            case 4:
                return EntityEquipmentSlot.FEET;
        }
        return EntityEquipmentSlot.CHEST; //yep I don't know what to put here, so random stuff go
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(SWINGING_ARMS, false);
        this.dataManager.register(PATROL, false);
        this.dataManager.register(KILL_PLAYER, false);
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 1);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
        compound.setBoolean("Patrolling", this.isPatrolling());
        compound.setBoolean("Kill", this.shouldKillPlayer());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
        this.setPatrolling(compound.getBoolean("Patrolling"));
        this.setKillPlayer(compound.getBoolean("Kill"));
    }
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.updateArmSwingProgress();
    }

    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() != Items.NAME_TAG && itemstack.getItem() != ModItems.ECTOPLASM && !player.isSneaking())
        {
            if (!this.world.isRemote && !player.isSpectator() && player == this.getOwner())
            {
                EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);

                if (itemstack.isEmpty())
                {
                    EntityEquipmentSlot entityequipmentslot1 = this.getClickedSlot(vec);

                    if (this.hasItemInSlot(entityequipmentslot1))
                    {
                        this.swapItem(player, entityequipmentslot1, itemstack, hand);
                    }
                }
                else
                {
                    this.swapItem(player, entityequipmentslot, itemstack, hand);
                }

            }
            return EnumActionResult.SUCCESS;
        }
        if(this.isTamed() && player == this.getOwner() && player.isSneaking()){
            if(itemstack.isEmpty() && !this.world.isRemote){
                if(!this.isPatrolling()) {
                    this.playSound(ModSounds.LITTLE_V_ACTIVATE, 1.0F, 1.0F);
                    this.setPatrolling(true);
                }
                else {
                   this.playSound(ModSounds.LITTLE_V_DEACTIVATE, 1.0F, 1.0F);
                    this.setPatrolling(false);
                }
                this.navigator.clearPath();
                this.setHomePosAndDistance(this.getPosition(), 10);
            }
            if(itemstack.getItem() == Items.GUNPOWDER && !this.shouldKillPlayer()  && !world.isRemote){
                this.setKillPlayer(true);
                if(!player.capabilities.isCreativeMode && AncientBeastsConfig.shouldVesselConsumeItem) {
                    itemstack.shrink(1);
                }

            }
            if(this.shouldKillPlayer() && itemstack.getItem() == Items.SUGAR  && !world.isRemote) {
                this.setKillPlayer(false);

                if(!player.capabilities.isCreativeMode && AncientBeastsConfig.shouldVesselConsumeItem) {
                    itemstack.shrink(1);
                }
                this.setAttackTarget(null);
            }
            if(itemstack.getItem() == ModItems.ECTOPLASM && this.getHealth() < 18.0F){
                if(!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                this.heal(6.0F);
            }
            return EnumActionResult.SUCCESS;
        }

        else
        {
            return EnumActionResult.PASS;
        }
    }
    protected EntityEquipmentSlot getClickedSlot(Vec3d p_190772_1_)
    {
        EntityEquipmentSlot entityequipmentslot = EntityEquipmentSlot.MAINHAND;
        double d0 = p_190772_1_.y * 2.0D;
        EntityEquipmentSlot entityequipmentslot1 = EntityEquipmentSlot.FEET;

        if (d0 >= 0.1D && d0 < 0.1D + 0.8D && this.hasItemInSlot(entityequipmentslot1))
        {
            entityequipmentslot = EntityEquipmentSlot.FEET;
        }
        else if (d0 >= 0.9D + 0.3D && d0 < 0.9D + 1.0D && this.hasItemInSlot(EntityEquipmentSlot.CHEST))
        {
            entityequipmentslot = EntityEquipmentSlot.CHEST;
        }
        else if (d0 >= 0.4D && d0 < 0.4D + 1.0D && this.hasItemInSlot(EntityEquipmentSlot.LEGS))
        {
            entityequipmentslot = EntityEquipmentSlot.LEGS;
        }
        else if (d0 >= 1.6D && this.hasItemInSlot(EntityEquipmentSlot.HEAD))
        {
            entityequipmentslot = EntityEquipmentSlot.HEAD;
        }

        return entityequipmentslot;
    }
    private void swapItem(EntityPlayer player, EntityEquipmentSlot slot, ItemStack stack, EnumHand hand)
    {
        ItemStack itemstack = this.getItemStackFromSlot(slot);
        if (player.capabilities.isCreativeMode && itemstack.isEmpty() && !stack.isEmpty()) {
            ItemStack itemstack2 = stack.copy();
            itemstack2.setCount(1);
            this.setItemStackToSlot(slot, itemstack2);
        } else if (!stack.isEmpty() && stack.getCount() > 1) {
            if (itemstack.isEmpty()) {
                ItemStack itemstack1 = stack.copy();
                itemstack1.setCount(1);
                this.setItemStackToSlot(slot, itemstack1);
                stack.shrink(1);
            }
        } else {
            this.setItemStackToSlot(slot, stack);
            player.setHeldItem(hand, itemstack);
        }
    }
    protected boolean canDespawn()
    {
        return false;
    }
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }
    public void onDeath(DamageSource cause)
    {
        if(!world.isRemote && this.isTamed()){
            this.dropEquipment(true, 100);
            ItemStack me = new ItemStack(ModItems.VESSEL, 1, this.itemDamage + 1);
            if(this.itemDamage <= 4) {
                if(this.hasCustomName()) {
                    me.setStackDisplayName(this.getCustomNameTag());
                }
                this.entityDropItem(me, 0.0F);
            }
        }
        super.onDeath(cause);
    }
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        EntityArrow entityarrow = this.getArrow(distanceFactor);
        if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
            entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }
    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms()
    {
        return this.dataManager.get(SWINGING_ARMS);
    }

    public void setSwingingArms(boolean swingingArms)
    {
        this.dataManager.set(SWINGING_ARMS, swingingArms);
    }
    protected EntityArrow getArrow(float p_190726_1_)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
        entitytippedarrow.setEnchantmentEffectsFromEntity(this, p_190726_1_);
        return entitytippedarrow;
    }

    public boolean isPatrolling(){
        return this.dataManager.get(PATROL);
    }
    public void setPatrolling(boolean patrol){
        this.dataManager.set(PATROL, patrol);
    }

    public boolean shouldKillPlayer(){
        return this.dataManager.get(KILL_PLAYER);
    }
    public void setKillPlayer(boolean k){
        this.dataManager.set(KILL_PLAYER, k);
    }

    static class AIFollowOwnerPatrol extends EntityAIFollowOwner {
        private final EntityLilVessel lilVessel;
        public AIFollowOwnerPatrol(EntityLilVessel vessel, double followSpeedIn, float minDistIn, float maxDistIn )
        {
            super(vessel, followSpeedIn, minDistIn, maxDistIn);
            this.lilVessel = vessel;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !lilVessel.isPatrolling();
        }
        public boolean shouldContinueExecuting()
        {
            return super.shouldContinueExecuting() && !lilVessel.isPatrolling();
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.LITTLE_VESSEL_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.LITTLE_VESSEL_DEATH; }
    public boolean isChild()
    {
        return true;
    }
    public void setScaleForAge(boolean child)
    {
        this.setScale(1.0F);
    }

    class AIVesselTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        public AIVesselTarget(EntityLilVessel vessel, Class<T> classTarget)
        {
            super(vessel, classTarget, true);
        }

        public boolean shouldExecute()
        {
            return EntityLilVessel.this.shouldKillPlayer() && super.shouldExecute();
        }
    }
}
