package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityDamcell extends EntityMob implements IRangedAttackMob {
    public static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "entities/Damcell");
    private float previousYaw = -1F;
    private int suckTimer;
    private int trapTimer;
    private int suckCooldown;
    private boolean didShoot;
    private int shootCooldown;
    private int shootTick;
    private boolean b;
    public EntityDamcell(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 3.125F);
        this.experienceValue = 5;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(3, new EntityAIAttackRanged(this, 0.0D, 40, 20.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMob.class));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityTameable.class, true));
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    }

    @Override
    public float getEyeHeight() {
        return 2.5F;
    }

    public void onUpdate() {
        super.onUpdate();
        if(this.previousYaw == -1F) {
            this.previousYaw = rotation(rand.nextInt(4));
        }
        this.renderYawOffset = this.previousYaw;
    }

    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(this.suckTimer > 0){
            --this.suckTimer;
        }
        if(this.trapTimer > 0){
            --this.trapTimer;
        }
        if(this.suckCooldown > 0){
            --this.suckCooldown;
        }
        if(--this.shootCooldown <= 0 ){
            this.didShoot = false;
        }
        if(this.shootTick > 0){
            --this.shootTick;
        }
        //best way to get rid of damcell bug would be debugging each part individually
        if(!this.isBeingRidden() && this.suckCooldown <= 0)
        {
            if(rand.nextInt(5) == 0 ){
                Predicate<EntityLivingBase> selector = entityLivingBase -> !(entityLivingBase instanceof EntityDamcell);
                List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8D), selector);
                if(!list.isEmpty()) {
                    if(this.b) {
                        this.suckTimer = 240;
                        this.playSound(ModSounds.DAMCELL_OPEN, 1.0F, 1.0F);
                        this.b = false;
                    }
                    for(EntityLivingBase livingBase : list){
                        if((livingBase instanceof EntityPlayer && !((EntityPlayer) livingBase).capabilities.isCreativeMode || livingBase instanceof EntityTameable) && this.suckTimer > 0) {
                            this.world.setEntityState(this, (byte) 5);
                            if (this.canEntityBeSeen(livingBase) && world.isRemote && !this.isBeingRidden()) {
                                double d0 = (this.posX - livingBase.posX) * 0.1;
                                double d2 = (this.posY - livingBase.posY) * 0.1;
                                double d1 = (this.posZ - livingBase.posZ) * 0.1;
                                livingBase.addVelocity(d0, d2, d1);
                               // AncientBeasts.logger.debug("brooooooooooom!"); //should always be client
                            }
                        }
                    }
                    if(this.suckTimer <= 0){
                        this.suckCooldown = 600;
                        this.b = true;
                    }
                }
                else {
                    this.suckCooldown = 600;
                    this.b = true;
                }
            }
        }
        if(this.isBeingRidden()){
            for(Entity e : this.getPassengers())
            {
                if(e instanceof EntityLivingBase && !world.isRemote) {
                    EntityLivingBase l = (EntityLivingBase)e;
                    l.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 2.0F * (float) BeastSlayerConfig.GlobalDamageMultiplier);
                 //   AncientBeasts.logger.debug("damaging");//should always be server
                }
            }
            if(this.trapTimer <= 0 && this.suckTimer <= 0)
            {
                this.removePassengers();
                this.suckCooldown = 600;
                this.b = true;
            }
        }
    }
    public float rotation(int r){
        switch (r){
            case 0:
            default:
                return 0F;
            case 1:
                return 90F;
            case 2:
                return 180F;
            case 3:
                return 270F;
        }
    }
    //damage = server, update = client???,
    @Override
    protected void collideWithEntity(Entity entityIn)
    {
        if (!this.isRidingSameEntity(entityIn) && this.trapTimer <= 0) {
            if (!entityIn.noClip && !this.noClip) {
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double) (1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double) (1.0F - this.entityCollisionReduction);

                    if (!entityIn.isBeingRidden() && !this.isBeingRidden()) {
                        entityIn.addVelocity(d0, 0.0D, d1);
                        if (!world.isRemote && this.suckTimer > 0) {
                            Predicate<EntityLivingBase> selector = entityLivingBase -> !(entityLivingBase instanceof EntityDamcell);
                            List<EntityLivingBase> list2 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(1.5D), selector);
                            if (!list2.isEmpty() && suckCooldown <= 0) {
                                //AncientBeasts.logger.debug("smth found!");
                                EntityLivingBase l = list2.get(this.world.rand.nextInt(list2.size()));
                                this.playSound(ModSounds.DAMCELL_CLOSE, 1.0F, 1.0F);
                                if (l instanceof EntityPlayer) {
                                    EntityPlayer entityPlayer = (EntityPlayer) l;
                                    if (!entityPlayer.capabilities.isCreativeMode) {
                                        entityPlayer.startRiding(this);
                                        this.trapTimer = 240;
                                    }
                                } else if (l instanceof EntityTameable) {
                                    l.startRiding(this);
                                    this.trapTimer = 240;
                                }
                                this.suckTimer = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updatePassenger(Entity entity) {
        super.updatePassenger(entity);
        this.world.setEntityState(this, (byte)6);
        entity.setPosition(this.posX, this.posY + 0.3D, this.posZ);
         //   AncientBeasts.logger.debug("updating trapped"); //should always be client
        if (entity.isSneaking())
        {
            entity.setSneaking(false);
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
    @Override
    public boolean canRiderInteract()
    {
        return false;
    }
    @Override
    public boolean canPassengerSteer() {
        return false;
    }

    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }
    public void fall(float distance, float damageMultiplier)
    {
    }
    protected int decreaseAirSupply(int air)
    {
        return air;
    }

    public int getMaxSpawnedInChunk()
    {
        return 4;
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 5)
        {
            this.suckTimer = 240;
            this.playSound(ModSounds.DAMCELL_OPEN, 1.0F, 1.0F);
        }
        if (id == 6)
        {
            this.suckTimer = 0;
            this.trapTimer = 240;
            this.playSound(ModSounds.DAMCELL_CLOSE, 1.0F, 1.0F);
        }
        if (id == 7){
            this.shootTick = 10;
            this.playSound(ModSounds.DAMCELL_SHOOT, 1.0F, 1.0F);
        }
        else {
            super.handleStatusUpdate(id);
        }
    }
    @SideOnly(Side.CLIENT)
    public int getSuckTimer(){
        return this.suckTimer;
    }

    @SideOnly(Side.CLIENT)
    public int getShootTick(){
        return this.shootTick;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if(this.suckCooldown > 0 && !this.didShoot && this.trapTimer <= 0) {
            this.shoot();
            this.shootTick = 10;
            this.world.setEntityState(this, (byte)7);
        }
    }
    @Override
    public void setSwingingArms(boolean swingingArms) {
    }

    private void shoot()
    {
        double d0 = 20D - this.posX;
        double d1 = this.posY + this.height;
        double d2 = 20D - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        if(!this.world.isRemote) {
            this.playSound(ModSounds.DAMCELL_SHOOT, 1.0F, 1.0F);
            for (int k = 0; k < 12; ++k) {
                float f2 = f + (float) k * (float) Math.PI * 3F / 8F + ((float) Math.PI * 3F / 5F);
                EntityDamcellSpike damcellSpike = new EntityDamcellSpike(this.world, this);
                damcellSpike.shoot(d0 + (double) MathHelper.cos(f2) * (k + 1 * 10000D), d1, d2 + (double) MathHelper.sin(f2) * (k + 1 * 10000D), 1.0F, 0.0F);
                this.world.spawnEntity(damcellSpike);
            }

            for (int i = 0; i < 8; ++i) {
                float f2 = f + (float) i * (float) Math.PI * 0.5F;
                EntityDamcellSpike damcellSpike = new EntityDamcellSpike(this.world, this);
                damcellSpike.shoot(d0 + (double) MathHelper.cos(f2) * (i + 1 * 10000D), d1, d2 + (double) MathHelper.sin(f2) * (i + 1 * 10000D), 0.6F, 0.0F);
                this.world.spawnEntity(damcellSpike);
            }

            this.didShoot = true;
            this.shootCooldown = 60;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
       return super.getCanSpawnHere() && this.posY > 10.0D && this.posY < 40.0D && this.dimension == 0;
    }
    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.DAMCELL_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.DAMCELL_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.DAMCELL_DEATH; }
}
