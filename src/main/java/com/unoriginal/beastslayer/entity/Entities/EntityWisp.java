package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityWisp extends EntityCreature {
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float squidRotation;
    public float prevSquidRotation;
    private float rotationVelocity;
    private float rotateSpeed;
    private int burnTicks;
    private BlockPos spawnPosition;

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityWisp.class, DataSerializers.VARINT);
    final Predicate<EntityLivingBase> selector = entityLivingBase -> !(entityLivingBase instanceof EntityWisp);

    public EntityWisp(World worldIn) {
        super(worldIn);
        this.setSize(0.4F, 0.7F);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask( 1, new EntityAILookIdle(this));
    }
   protected void updateAITasks() {
       super.updateAITasks();

       if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
       {
           this.spawnPosition = null;
       }

       if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq(((int)this.posX), ((int)this.posY), ((int)this.posZ)) < 4.0D)
       {
           this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
       }

       double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
       double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
       double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
       this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.01D;
       this.motionY += (Math.signum(d1) * 0.69D - this.motionY) * 0.01D;
       this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.01D;
       this.moveForward = 0.4F;

   }

    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 3);
    }

    public void setVariant(int variant)
    {
        this.dataManager.set(VARIANT,variant);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    @Override
    public void onUpdate() {
        this.noClip = true;
        super.onUpdate();
        this.noClip = false;
        this.setNoGravity(true);
        if(world.isRemote){
            if(this.getVariant() != 3) {
                for (int i = 0; i < 1; ++i) {
                    if(this.ticksExisted % 10 == 0) {
                        this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX, this.posY + this.height / 2F, this.posZ, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
            else {
                for (int i = 0; i < 1; ++i) {
                    if(this.ticksExisted % 10 == 0) {
                        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + this.height / 2F, this.posZ, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8D), selector);
        if(!list.isEmpty() && !this.world.isRemote){
            for(EntityLivingBase livingBase : list){
                if(this.getVariant() == 0) {
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10));
                } else if (this.getVariant() == 1) {
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10));
                } else if (this.getVariant() == 2) {
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40));
                    if (livingBase instanceof AbstractTribesmen && ((AbstractTribesmen) livingBase).isFiery()) {
                        AbstractTribesmen t = (AbstractTribesmen) livingBase;
                        t.setFiery(false);
                        if(rand.nextInt(3) == 0){
                            this.setHealth(0.0F);
                        }
                    }
                } else if (this.getVariant() == 3 && this.burnTicks == 0) {
                    this.burnTicks = 80;
                    livingBase.setFire(6);
                    this.world.setEntityState(this, (byte) 5);
                }
            }
        }
        if(this.burnTicks > 0){
            --this.burnTicks;
        }
        if(this.getVariant() != 3) {
            this.prevSquidPitch = this.squidPitch;
            this.prevSquidYaw = this.squidYaw;
            this.prevSquidRotation = this.squidRotation;
            this.squidRotation += this.rotationVelocity;

            if ((double) this.squidRotation > (Math.PI * 2D)) {
                if (this.world.isRemote) {
                    this.squidRotation = ((float) Math.PI * 2F);
                } else {
                    this.squidRotation = (float) ((double) this.squidRotation - (Math.PI * 2D));

                    if (this.rand.nextInt(10) == 0) {
                        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                    }

                    this.world.setEntityState(this, (byte) 19);
                }
            }


            if (this.squidRotation < (float) Math.PI) {
                float f = this.squidRotation / (float) Math.PI;

                if ((double) f > 0.75D) {

                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.rotateSpeed *= 0.99F;
            }

            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float) MathHelper.atan2(this.motionX, this.motionZ)) * (180F / (float) Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw = (float) ((double) this.squidYaw + Math.PI * (double) this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float) MathHelper.atan2(f1, this.motionY)) * (180F / (float) Math.PI) - this.squidPitch) * 0.1F;
        }
        if(this.ticksExisted > 600){
            this.setHealth(0F);
        }
    }
    @SideOnly(Side.CLIENT)
    public int getBurnTicks(){
        return this.burnTicks;
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 5) {
            this.burnTicks = 20;

            for (int i = 0; i < 50; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + (double)this.height + 0.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
            }

        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty())
        {
            if (itemstack.getItem() == Items.GLASS_BOTTLE && !this.world.isRemote)
            {
                itemstack.shrink(1);
                ItemStack itemStack2 = new ItemStack (ModItems.WISP_BOTTLE, 1, this.getVariant());
                player.addItemStackToInventory(itemStack2);
                this.setDead();
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public float getBrightness()
    {
        return 1.0F;
    }
}
