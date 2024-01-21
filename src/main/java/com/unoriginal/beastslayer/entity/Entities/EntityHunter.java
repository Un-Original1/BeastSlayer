package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModSounds;
import com.unoriginal.beastslayer.items.ItemMask;
import com.unoriginal.beastslayer.items.ItemWindforce;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityHunter extends AbstractTribesmen implements IRangedAttackMob {

    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.createKey(EntityHunter.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CAMOUFLAGED = EntityDataManager.createKey(EntityHunter.class, DataSerializers.BOOLEAN);
    private ResourceLocation TRADE = new ResourceLocation(BeastSlayer.MODID, "trades/Hunter");
    private final EntityAIAttackRangedBow<EntityHunter> aiArrowAttack = new EntityAIAttackRangedBow<>(this, 1.0D, 20, 20.0F);
   private int camoTicks;
    private int camoCD;

    public EntityHunter(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1.8F);
        this.setCombatTask();
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, AbstractTribesmen.class, 10, true, false, p_apply_1_ -> p_apply_1_.isFiery() && !this.isFiery()));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SWINGING_ARMS, Boolean.FALSE);
        this.dataManager.register(CAMOUFLAGED, Boolean.FALSE);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if(!this.isFiery()) {
            if(this.getHeldItemMainhand().getItem() instanceof ItemBow && !(this.getHeldItemMainhand().getItem() instanceof ItemWindforce)){
                EntityArrow entityarrow = this.getArrow(distanceFactor);
                if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
                    entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
                double d0 = target.posX - this.posX;
                double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - entityarrow.posY;
                double d2 = target.posZ - this.posZ;
                double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (12 - this.world.getDifficulty().getDifficultyId() * 4));
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(entityarrow);
            }
            else if(this.getHeldItemMainhand().getItem() instanceof ItemWindforce){
                EntityWindforceDart dart = new EntityWindforceDart(this.world, this, this.getAttackTarget());
                Vec3d vec3d = this.getLookVec();
                dart.setPosition(this.posX + vec3d.x * 1.4D,this.posY + vec3d.y + this.getEyeHeight(), this.posZ + vec3d.z * 1.4D);
                this.world.spawnEntity(dart);
            }

        }
        else {
            for (int i = 0; i < 4; i++){
                EntityArrow entityarrow = this.getArrow(distanceFactor);
                if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow)
                    entityarrow = ((net.minecraft.item.ItemBow) this.getHeldItemMainhand().getItem()).customizeArrow(entityarrow);
                double d0 = target.posX - this.posX;
                double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - entityarrow.posY;
                double d2 = target.posZ - this.posZ;
                double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (20 - this.world.getDifficulty().getDifficultyId() * 4));
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(entityarrow);
            }
        }
    }

    protected EntityArrow getArrow(float p_190726_1_)
    {
        if(!this.isFiery()) {
            EntityTippedArrow entityarrow = new EntityTippedArrow(this.world, this);
            entityarrow.setEnchantmentEffectsFromEntity(this, p_190726_1_);
            (entityarrow).addEffect(new PotionEffect(MobEffects.NAUSEA, 200));
            return entityarrow;
        }
        else {
            EntityTippedArrow entityarrow2 = new EntityTippedArrow(this.world, this);
            entityarrow2.setEnchantmentEffectsFromEntity(this, p_190726_1_);
            entityarrow2.setFire(100);
            return entityarrow2;
        }
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

    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiArrowAttack);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem() instanceof ItemBow)
            {
                int i = 20;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 40;
                }

                this.aiArrowAttack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiArrowAttack);
            }
        }
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        if(rand.nextFloat() > 0.2F )
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.WINDFORCE));
        }


    }

    @Override
    public float avoidDistance(float distance) {
        return 3F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(this.isCamo() && !this.world.isRemote){
            this.setCamouflaged(false);
            this.camoCD = 800 + rand.nextInt(200);
            this.camoTicks = 0;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        this.setCombatTask();
        return livingdata;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAttackTarget() != null && --this.camoCD <= 0 && !this.isCamo() && !this.isFiery() && !this.world.isRemote){
            this.setCamouflaged(true);
            this.camoTicks = 400 + rand.nextInt(400);
            this.playSound(ModSounds.SMOKE, 0.75F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
        }
        if(--this.camoTicks <= 0 && this.isCamo() && !this.world.isRemote){
            this.setCamouflaged(false);
            this.camoCD = 800 + rand.nextInt(200);
        }

        if(this.isFiery() && this.getHeldItemMainhand().isEmpty() && !this.world.isRemote)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
        if (!world.isRemote && this.ticksExisted % 150 == 0 && this.isCamo()) {
            List<EntityLiving> entities = this.world.getEntitiesWithinAABB(EntityLiving.class, this.getEntityBoundingBox().grow(14D));
            if(!entities.isEmpty()) {
                for (EntityLiving entity : entities)
                    if (entity.getAttackTarget() != null && entity.getAttackTarget() == this){
                        entity.setAttackTarget(null);
                    }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isCamo()
    {
        return this.dataManager.get(CAMOUFLAGED);
    }

    public void setCamouflaged(boolean camo)
    {
        this.dataManager.set(CAMOUFLAGED, camo);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("CamoTicks", this.camoTicks);
        compound.setInteger("CamoCD", this.camoCD);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("CamoTicks")){
            this.camoTicks = compound.getInteger("CamoTicks");
        }
        if(compound.hasKey("CamoCD")){
            this.camoCD = compound.getInteger("CamoCD");
        }
        this.setCombatTask();
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        super.setItemStackToSlot(slotIn, stack);

        if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
        {
            this.setCombatTask();
        }
    }
    @SideOnly(Side.CLIENT)
    public int getCamoTicks(){
        return this.camoTicks;
    }

    @Nullable
    @Override
    protected ResourceLocation getBarteringTable()
    {
        return TRADE;
    }
    public boolean shouldTradeWithplayer(EntityPlayer player){
        Item stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        boolean b = stack instanceof ItemMask && ((ItemMask) stack).getTier() >=0;
        return super.shouldTradeWithplayer(player) && !this.isCamo() && b;
    }

}
