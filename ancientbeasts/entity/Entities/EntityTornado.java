package com.unoriginal.ancientbeasts.entity.Entities;

import com.unoriginal.ancientbeasts.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

// shamelessly based on guster from alex's mobs
public class EntityTornado extends Entity {
    private static final DataParameter<Integer> LIFTED = EntityDataManager.createKey(EntityTornado.class, DataSerializers.VARINT);
    private EntityLivingBase caster;
    private int lifeticks;
    private int liftTime;
    private int maxLiftTime = 30;
    private UUID casterUuid;
    private boolean isAboutToVanish;

    public EntityTornado(World worldIn) {
        super(worldIn);
        this.lifeticks = 50;
        this.setSize(1.5F,2.0F);
    }

    public EntityTornado(World worldIn, double x, double y, double z, float rotationYaw, EntityLivingBase casterIn)
    {
        this(worldIn);
        this.setCaster(casterIn);
        this.rotationYaw = rotationYaw * (180F / (float)Math.PI);
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(LIFTED, 0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.casterUuid = compound.getUniqueId("OwnerUUID");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        if (this.casterUuid != null)
        {
            compound.setUniqueId("OwnerUUID", this.casterUuid);
        }
    }
    public void setCaster(@Nullable EntityLivingBase entityLivingBase)
    {
        this.casterUuid = entityLivingBase == null ? null : entityLivingBase.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getCaster() {
        if (this.caster == null && this.casterUuid != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.casterUuid);
            if (entity instanceof EntityLivingBase) {
                this.caster = (EntityLivingBase)entity;
            }
        }

        return this.caster;
    }

    public void onUpdate()
    {
        super.onUpdate();
        this.world.setEntityState(this, (byte)4);
        this.lifeticks--;
        if(this.lifeticks <= 0 && !this.world.isRemote){
            this.setDead();
        }
        Entity lifted = this.getLiftedEntity();
        if(lifted == null && !world.isRemote && ticksExisted % 5 == 0){
            List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(0.8F));
            EntityItem closestItem = null;
            for (EntityItem item : list) {
                if (item.onGround) {
                    if (closestItem != null) {
                        this.getDistance(closestItem);
                        this.getDistance(item);
                    }
                }
                {
                    closestItem = item;
                }
            }
            if(closestItem != null){
                this.setLifted(closestItem.getEntityId());
                maxLiftTime = 30 + rand.nextInt(20);
            }
            List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(0.8F));
            for(EntityLivingBase entityLivingBase : list1){
                if(entityLivingBase != null && entityLivingBase.onGround && entityLivingBase != this.getCaster()){
                    this.setLifted(entityLivingBase.getEntityId());
                    entityLivingBase.velocityChanged = true;
                }
            }
        }
        if (lifted != null && liftTime >= 0) {
            liftTime++;
            float resist = 1F;
            if (lifted instanceof EntityLivingBase) {
                resist = (float) MathHelper.clamp((1.0D - ((EntityLivingBase) lifted).getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()), 0, 1);
            }
            float radius = 1F + (liftTime * 0.05F);
            if (lifted instanceof EntityItem) {
                radius = 0.2F + (liftTime * 0.025F);
            }
            float angle = liftTime * -0.25F;
            double extraX = this.posX + radius * MathHelper.sin((float) (Math.PI + angle));
            double extraZ = this.posZ + radius * MathHelper.cos(angle);
            double d0 = (extraX - lifted.posX) * resist;
            double d1 = (extraZ - lifted.posZ) * resist;
            lifted.addVelocity(d0, 0.1 * resist, d1);
            lifted.isAirBorne = true;
            if (liftTime > maxLiftTime) {
                this.setLifted(0);
                liftTime = -20;
                maxLiftTime = 30 + rand.nextInt(30);
            }
        } else if (liftTime < 0) {
            liftTime++; }
        if(this.lifeticks == 49){
            this.playSound(ModSounds.TORNADO_AMBIENT, 1.0F, 1.0F);
        }
    }
    public boolean isLifting()
    {
        return this.dataManager.get(LIFTED) != 0;
    }
    @Nullable
    public Entity getLiftedEntity(){
        if(!this.isLifting()){
            return null;
        }
        else{
            return this.world.getEntityByID(this.dataManager.get(LIFTED));
        }
    }
    private void setLifted(int time)
    {
        this.dataManager.set(LIFTED, time);
    }
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        super.handleStatusUpdate(id);

        if (id == 4)
        {
            this.isAboutToVanish = true;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getAnimationProgress(float partialTicks)
    {
        if (!this.isAboutToVanish)
        {
            return 0.0F;
        }
        else
        {
            int i = this.lifeticks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - partialTicks) / 20.0F;
        }
    }
}
