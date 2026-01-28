package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntitySuccXp extends Entity {
    private static final DataParameter<Integer> SUCC = EntityDataManager.createKey(EntitySuccXp.class, DataSerializers.VARINT);
    public int xpColor;
    public int xpOrbAge;
    public int delayBeforeCanPickup;
    private int xpOrbHealth = 5;
    public int xpValue;
    private EntitySucc closestSuccubus;
    private int xpTargetColor;

    public EntitySuccXp(World worldIn, double x, double y, double z, int expValue, EntitySucc owner) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.rotationYaw = (float) (Math.random() * 360.0D);
        this.motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
        this.motionY = (float) (Math.random() * 0.2D) * 2.0F;
        this.motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
        this.xpValue = expValue;
        this.closestSuccubus = owner;
        this.setSucc(owner.getEntityId());
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public EntitySuccXp(World worldIn) {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() {
        this.dataManager.register(SUCC, 0);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        float f = 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int) (f * 15.0F * 16.0F);

        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity()) {
            this.motionY -= 0.029999999329447746D;
        }



        if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
            this.motionY = 0.20000000298023224D;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);


        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.closestSuccubus == null || this.closestSuccubus.isDead) {
                if(this.getSucc() != null && !this.getSucc().isDead) {
                    this.closestSuccubus = this.getSucc();
                } else {
                    List<EntitySucc> list =this.world.getEntitiesWithinAABB(EntitySucc.class,this.getEntityBoundingBox().grow(64D));
                    if(!list.isEmpty()){
                        double d2 = -1D;
                        for(EntitySucc entitysucc : list){
                            double d1 = entitysucc.getDistanceSq(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());

                            if ((8D < 0.0D || d1 < 64D) && (d2 == -1.0D || d1 < d2))
                            {
                                d2 = d1;
                                this.closestSuccubus = entitysucc;

                            }
                        }
                    }

                }
            }

            this.xpTargetColor = this.xpColor;
        }

        if (this.closestSuccubus != null) {
            double d1 = (this.closestSuccubus.posX - this.posX) / 8.0D;
            double d2 = (this.closestSuccubus.posY + (double) this.closestSuccubus.getEyeHeight() / 2.0D - this.posY) / 8.0D;
            double d3 = (this.closestSuccubus.posZ - this.posZ) / 8.0D;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D) {
                d5 = d5 * d5;
                this.motionX += d1 / d4 * d5 * 0.3D;
                this.motionY += d2 / d4 * d5 * 0.3D;
                this.motionZ += d3 / d4 * d5 * 0.3D;
            }
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround) {
            BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
            net.minecraft.block.state.IBlockState underState = this.world.getBlockState(underPos);
            f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
        }

        this.motionX *= f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= f;

        if (this.onGround) {
            this.motionY *= -0.8999999761581421D;
        }

        ++this.xpColor;
        ++this.xpOrbAge;

        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
        this.collideWithNearbyEntities();
    }

    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty())
        {
            int i = this.world.getGameRules().getInt("maxEntityCramming");

            if (i > 0 && list.size() > i - 1 && this.rand.nextInt(4) == 0)
            {
                int j = 0;

                for (int k = 0; k < list.size(); ++k)
                {
                    if (!list.get(k).isRiding())
                    {
                        ++j;
                    }
                }

                if (j > i - 1)
                {
                    this.attackEntityFrom(DamageSource.CRAMMING, 6.0F);
                }
            }

            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                this.collideWithEntity(entity);
            }
        }
    }



    protected void collideWithEntity(Entity entityIn)
    {
        if(entityIn instanceof EntitySucc){
            EntitySucc entity = (EntitySucc)entityIn;
            if (entity == this.closestSuccubus)
            {
                this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.HOSTILE,0.3F,  (this.rand.nextFloat() - this.rand.nextFloat()) * 0.35F + 0.9F);
                entity.giveXpBonus();
                this.setDead();
            }
        }
    }

    public boolean handleWaterMovement() {
        return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
    }

    protected void dealFireDamage(int amount) {
        this.attackEntityFrom(DamageSource.IN_FIRE, (float) amount);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.world.isRemote || this.isDead) return false; //Forge: Fixes MC-53850
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else {
            this.markVelocityChanged();
            this.xpOrbHealth = (int) ((float) this.xpOrbHealth - amount);

            if (this.xpOrbHealth <= 0) {
                this.setDead();
            }

            return false;
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setShort("Health", (short) this.xpOrbHealth);
        compound.setShort("Age", (short) this.xpOrbAge);
        compound.setShort("Value", (short) this.xpValue);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        this.xpOrbHealth = compound.getShort("Health");
        this.xpOrbAge = compound.getShort("Age");
        this.xpValue = compound.getShort("Value");
    }

    @SideOnly(Side.CLIENT)
    public int getTextureByXP() {
        if (this.xpValue >= 2477) {
            return 10;
        } else if (this.xpValue >= 1237) {
            return 9;
        } else if (this.xpValue >= 617) {
            return 8;
        } else if (this.xpValue >= 307) {
            return 7;
        } else if (this.xpValue >= 149) {
            return 6;
        } else if (this.xpValue >= 73) {
            return 5;
        } else if (this.xpValue >= 37) {
            return 4;
        } else if (this.xpValue >= 17) {
            return 3;
        } else if (this.xpValue >= 7) {
            return 2;
        } else {
            return this.xpValue >= 3 ? 1 : 0;
        }
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }
    private void setSucc(int entityId)
    {
        this.dataManager.set(SUCC, entityId);
        Entity mob = this.world.getEntityByID(entityId);
        if(mob instanceof EntitySucc) {
            this.closestSuccubus = (EntitySucc) mob;
        }
    }

    public boolean hasSucc()
    {
        return this.dataManager.get(SUCC) != 0;
    }

    @Nullable
    public EntitySucc getSucc()
    {
        if (!this.hasSucc())
        {
            return null;
        }
        else if(this.world.isRemote){
            Entity entity = this.world.getEntityByID(this.dataManager.get(SUCC));

            if (entity instanceof EntitySucc)
            {
                this.closestSuccubus = (EntitySucc) entity;
                return this.closestSuccubus;
            }
            else
            {
                return null;
            }
        }
        else {
            return closestSuccubus;
        }
    }

}
