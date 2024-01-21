package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMeleeConditional;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityTribeWarrior extends AbstractTribesmen{
    private int avoidTicks;
    private int angryTick;
    private boolean wasAngry = false;
    private ResourceLocation TRADE = new ResourceLocation(BeastSlayer.MODID, "trades/Warrior");
    private static final DataParameter<Boolean> Angry = EntityDataManager.createKey(EntityTribeWarrior.class, DataSerializers.BOOLEAN);
    private static final UUID ANGRY_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier ANGRY_SPEED_BOOST = (new AttributeModifier(ANGRY_BOOST_ID, "Buried speed boost", 0.10D, 0)).setSaved(false);

    public EntityTribeWarrior(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1.8F);
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(Angry, false);
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIMeleeConditional(this, rCS(), false, Predicate -> this.avoidTicks <= 0 && !this.isFiery()));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIMeleeConditional(this, rCS(), false, Predicate -> this.isFiery()));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityLivingBase.class, Predicate -> this.avoidTicks > 0 && !this.isFiery() ,2F, 1.3D, 1.3D));
    }

    public float rCS(){
        return this.isFiery() ? 2.0F : 1.5F;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.avoidTicks >= 0){
            --this.avoidTicks;
        }
        if(this.angryTick >= 0){
            --this.angryTick;
        }
        if(this.avoidTicks == 0){
            this.getNavigator().clearPath();
        }

        if(this.getHealth() < this.getMaxHealth() / 2F && !this.world.isRemote && !this.isAngry() && !this.wasAngry){
            this.setAngry(true);
            this.angryTick = 30;
            this.world.setEntityState(this, (byte) 9);
            this.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 10, false, false));
        }
        if(this.isAngry() && this.world.isRemote && this.ticksExisted % 10 == 0){
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        }
    }

    @Override
    public float getEyeHeight() {
        return 1.3F;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.avoidTicks = 10 + rand.nextInt(60);
        if(this.isAngry() && entityIn instanceof EntityLivingBase){
            EntityLivingBase b = (EntityLivingBase)entityIn;
            b.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 4));
            b.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
            this.setAngry(false);
            this.wasAngry = true;
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isAngry() && source.getTrueSource() instanceof EntityLivingBase){
            this.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 2));
            this.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 120));
            this.setAngry(false);
            this.wasAngry = true;
        }
        return super.attackEntityFrom(source, amount);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        int i = this.rand.nextInt(7);

        if (i == 0)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BONE));
        }
        else if (i < 3){
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item) null));
        }
        else
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.SPEAR));
        }

    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Avoid", this.avoidTicks);
        compound.setInteger("Angriest", this.angryTick);
        compound.setBoolean("Angry", this.dataManager.get(Angry));
        compound.setBoolean("Angrier", this.wasAngry);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.avoidTicks = compound.getInteger("Avoid");
        this.dataManager.set(Angry, compound.getBoolean("Angry"));
        this.angryTick = compound.getInteger("Angriest");
        this.wasAngry = compound.getBoolean("Angrier");
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return livingdata;
    }

    public void setAngry(boolean b){
        if(!this.wasAngry) {
            this.dataManager.set(Angry, b);
            if (!this.world.isRemote) {
                IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                if (b) {
                    if (!iattributeinstance.hasModifier(ANGRY_SPEED_BOOST)) {
                        iattributeinstance.applyModifier(ANGRY_SPEED_BOOST);
                    }
                } else {
                    iattributeinstance.removeModifier(ANGRY_SPEED_BOOST);
                }
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if(id == 9){
            this.angryTick = 30;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAngryTick(){
        return this.angryTick;
    }
    public boolean isAngry(){
        return this.dataManager.get(Angry);
    }

    @Nullable
    @Override
    protected ResourceLocation getBarteringTable()
    {
        return TRADE;
    }
    public boolean shouldTradeWithplayer(EntityPlayer player){
        return super.shouldTradeWithplayer(player) && !this.isAngry();
    }

}
