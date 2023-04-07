package com.unoriginal.beastslayer.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class EntityWindforceDart extends EntityProjectileGeneric {
    private EntityLivingBase owner;
    private EntityLivingBase target;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private UUID targetUUID;
    private int targetmax;

    public EntityWindforceDart(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.noClip = true;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    public EntityWindforceDart(World worldIn, EntityLivingBase ownerIn, @Nullable EntityLivingBase targetIn) {
        this(worldIn);
        this.setSize(0.5F, 0.5F);
        this.owner = ownerIn;
        this.setPosition(owner.posX, owner.getEyeHeight() + owner.posY, owner.posZ);
        this.target = targetIn;
        this.targetmax = 0;
        this.noClip = true;
    }
    public EntityWindforceDart(World worldIn, EntityLivingBase ownerIn, @Nullable EntityLivingBase targetIn, int targetmax) {
        this(worldIn);
        this.setSize(0.5F, 0.5F);
        this.owner = ownerIn;
        this.setPosition(owner.posX, owner.getEyeHeight() + owner.posY, owner.posZ);
        this.target = targetIn;
        this.targetmax = targetmax;
        this.noClip = true;
    }

    protected void writeEntityToNBT(NBTTagCompound compound) {
        if (this.owner != null) {
            NBTTagCompound nbttagcompound = NBTUtil.createUUIDTag(this.owner.getUniqueID());
            compound.setTag("Owner", nbttagcompound);
        }

        if (this.target != null) {
            NBTTagCompound nbttagcompound1 = NBTUtil.createUUIDTag(this.target.getUniqueID());
            compound.setTag("Target", nbttagcompound1);
        }

        compound.setInteger("HitCount", this.targetmax);

    }

    protected void readEntityFromNBT(NBTTagCompound compound) {

        if (compound.hasKey("Owner", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Owner");
            this.ownerUUID = NBTUtil.getUUIDFromTag(nbttagcompound);
        }

        if (compound.hasKey("Target", 10)) {
            NBTTagCompound nbttagcompound1 = compound.getCompoundTag("Target");
            this.targetUUID = NBTUtil.getUUIDFromTag(nbttagcompound1);
        }
        this.targetmax = compound.getInteger("HitCount");
    }

    public void onUpdate() {
            super.onUpdate();

            if (!this.world.isRemote) {
                this.setNoGravity(true);
                if (this.target == null && this.targetUUID != null) {
                   this.getTarget();
                }

                if (this.owner == null && this.ownerUUID != null) {
                  this.getOwner();
                }

                if(this.target != null && this.owner != null){
                    noClip = true;

                    Vec3d mypos = getPositionVector();

                    EntityLivingBase target = this.target;

                    Vec3d targetpos = target.getPositionVector().addVector(0D, (double) target.height / 2D, 0D);



                    Vec3d motion = targetpos.subtract(mypos);

                    motion = motion.normalize().scale(0.7 * 0.325F);
                    motionX = motion.x * 3.2F;
                    motionY = motion.y * 3.2F;
                    motionZ = motion.z * 3.2F;

                    this.getLookVec().add(motion);
                }
                if(this.ticksExisted > 140){
                    this.setDead();
                }
                if(this.getTarget() == null && this.targetUUID == null){
                    this.motionY *= 1.5F;
                }
                if(this.getTarget() != null && this.getTarget().isDead){
                    this.setRandTarget(false);
                }

            }
        this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (this.world.isRemote) {
            this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX - this.motionX, this.posY - this.motionY + 0.15D, this.posZ - this.motionZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if(this.world.isRemote) {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0D, 0D, 0D);
        }
        if (rayTraceResult.entityHit != null && this.owner != null && this.owner != rayTraceResult.entityHit)
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
            if(this.targetmax < 7) {
                EntityLivingBase target = this.getTarget();
                EntityLivingBase base = this.setRandTarget(false);
                EntityWindforceDart dart = new EntityWindforceDart(world, this.getOwner(), base, this.targetmax + 1);
                Vec3d vec3d = this.getLookVec();
                dart.setPosition(target.posX + vec3d.x * 1.4D,target.posY + vec3d.y + target.getEyeHeight(), target.posZ + vec3d.z * 1.4D);
                this.world.spawnEntity(dart);

            }
        } else {
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F);
        }
        this.setDead();

    }

    @Nullable
    public EntityLivingBase getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.ownerUUID);
            if (entity instanceof EntityLivingBase) {
                this.owner = (EntityLivingBase)entity;
            }
        }

        return this.owner;
    }

    public void setTarget(@Nullable EntityLivingBase target){
        this.target = target;
        this.targetUUID = target == null ? null : target.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getTarget() {
        if (this.target == null && this.targetUUID != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.targetUUID);
            if (entity instanceof EntityLivingBase) {
                this.target = (EntityLivingBase)entity;
            }
        }

        return this.target;
    }

    public EntityLivingBase setRandTarget(boolean sort){
        EntityLivingBase target = null;
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition()).grow(20D), livingbase -> livingbase != this.getOwner() && livingbase != this.getTarget());
        if (!list.isEmpty()) {
            if(sort) {
                list.sort(Comparator.comparingDouble(t -> t.getDistanceSq(this.posX, this.posY, this.posZ)));
            }
            EntityLivingBase livingBase = list.get(0);
            List<EntityMob> list2 = world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(this.getPosition()).grow(20D), mob -> mob != this.owner && mob != this.getTarget());
            if (!list2.isEmpty()) {
                if(sort) {
                    list2.sort(Comparator.comparingDouble(t -> t.getDistanceSq(this.posX, this.posY, this.posZ)));
                }
                target = list2.get(0);
            } else if (!livingBase.isOnSameTeam(this.owner)) {
                target = livingBase;
            }
        }
        return target;
    }



}
