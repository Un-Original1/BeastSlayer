package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityChained extends EntityProjectileGeneric{
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(EntityChained.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> Mob = EntityDataManager.createKey(EntityChained.class, DataSerializers.BOOLEAN);

    public EntityChained(World worldIn) {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }
    public EntityChained(World worldIn, EntityLivingBase owner)
    {

        super(worldIn);
        this.owner = owner;
        this.setBuffedEntity(owner.getEntityId());
        float f1 = MathHelper.sin(owner.renderYawOffset * 0.017453292F + (float)Math.PI);
        float f2 = MathHelper.cos(owner.renderYawOffset * 0.017453292F);
        this.setPosition(owner.posX + f1 * 1.25F, owner.posY + (double)owner.getEyeHeight() - 0.10000000149011612D, owner.posZ + f2 * 1.25F);
        this.setSize(0.5F, 0.5F);
    }

    public void onHit(RayTraceResult rayTraceResult)
    {
        if (rayTraceResult.entityHit != null && this.owner != null && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric || rayTraceResult.entityHit == this.owner))
        {
            rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 6.0F  * (float) BeastSlayerConfig.GlobalDamageMultiplier);
        }
        if(this.owner != null && !(this.owner instanceof EntityMob) && this.isMob() && rayTraceResult.entityHit == null && rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK)){
            owner.motionX += (rayTraceResult.hitVec.x - owner.posX) * 0.4D;
            owner.motionY += (rayTraceResult.hitVec.y - owner.posY) * 0.4D;
            owner.motionZ +=  (rayTraceResult.hitVec.z - owner.posZ) * 0.4D;
        }

        if (!this.world.isRemote && !(rayTraceResult.entityHit instanceof EntityProjectileGeneric) && !(rayTraceResult.entityHit == this.owner))
        {

            if (isMob() && !(rayTraceResult.entityHit instanceof EntityMob) && rayTraceResult.entityHit instanceof EntityLivingBase){
                EntityLivingBase newOwner = (EntityLivingBase)rayTraceResult.entityHit;
                EntityChained entityarrow = new EntityChained(world, newOwner);
                entityarrow.setMob(true);
                double d0 = (newOwner.getPosition().getX() + rand.nextInt(16)) - newOwner.getPosition().getX();
                double d1 = newOwner.getPosition().getY();
                double d2 = (newOwner.getPosition().getZ() + rand.nextInt(16)) - newOwner.getPosition().getZ();
                double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

                entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float) (14 - this.world.getDifficulty().getDifficultyId() * 4));
                this.playSound(ModSounds.KUNAI, 0.8F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(entityarrow);
            }
            else if(!isMob() && rayTraceResult.entityHit instanceof EntityLivingBase){
                EntityLivingBase target = (EntityLivingBase)rayTraceResult.entityHit;
                target.motionX += (owner.posX - target.posX) * 0.3D;
                target.motionY += (owner.posY - target.posY) * 0.1D;
                target.motionZ +=  (owner.posZ - target.posZ) * 0.3D;
            }
            this.playSound(ModSounds.KUNAI_HIT, 0.7F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.8F));
            this.setDead();
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OWNER, 0);
        this.dataManager.register(Mob, false);
    }

    private void setBuffedEntity(int entityId)
    {
        this.dataManager.set(OWNER, entityId);
        Entity mob = this.world.getEntityByID(entityId);
        if(mob instanceof EntityLivingBase) {
            this.owner = (EntityLivingBase) mob;
        }
    }

    public boolean hasBuffedEntity()
    {
        return this.dataManager.get(OWNER) != 0;
    }

    @Nullable
    public EntityLivingBase getBuffedEntity()
    {
        if (!this.hasBuffedEntity())
        {
            return null;
        }
        else if(this.world.isRemote){
            Entity entity = this.world.getEntityByID(this.dataManager.get(OWNER));

            if (entity instanceof EntityLivingBase)
            {
                this.owner = (EntityLivingBase) entity;
                return this.owner;
            }
            else
            {
                return null;
            }
        }
        else {
            return owner;
        }
    }

    public void setMob (boolean mob){
        this.getDataManager().set(Mob, mob);
    }

    public boolean isMob(){
        return this.getDataManager().get(Mob);
    }
}
