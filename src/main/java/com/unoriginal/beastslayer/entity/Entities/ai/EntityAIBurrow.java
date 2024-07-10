package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityAIBurrow extends EntityAIBase {

    private static final UUID BURIED_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier BURIED_SPEED_BOOST = (new AttributeModifier(BURIED_SPEED_BOOST_ID, "Buried speed boost", 0.15D, 0)).setSaved(false);
    private final EntityLiving TheEntity;
    private final World entityWorld;
    int burrowTimer;
    private final int maxburrow;
    private final boolean shouldBoost;

    public EntityAIBurrow(EntityLiving entity, int timer, boolean boost){
        this.TheEntity = entity;
        this.entityWorld = entity.world;
        this.maxburrow = timer;
        this.shouldBoost = boost;
        this.setMutexBits(4);
    }
    @Override
    public boolean shouldExecute() {
        if (this.TheEntity.getRNG().nextInt(190) != 0)
        {
            return false;
        }
        else {
            return this.TheEntity.getAttackTarget() != null && this.TheEntity.onGround;
        }
    }

    public void startExecuting()
    {
        this.burrowTimer = this.maxburrow;


       // this.grassEaterEntity.getNavigator().clearPath();
        if(!entityWorld.isRemote && this.shouldBoost) {
            IAttributeInstance iattributeinstance = this.TheEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            if (!iattributeinstance.hasModifier(BURIED_SPEED_BOOST)) {
                iattributeinstance.applyModifier(BURIED_SPEED_BOOST);
            }
        }
            this.entityWorld.setEntityState(this.TheEntity, (byte) 10);// TODO: implement animation
    }

    public void resetTask()
    {
        this.burrowTimer = 0;

        if(this.shouldBoost) {
            IAttributeInstance iattributeinstance = this.TheEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(BURIED_SPEED_BOOST);
        }
        this.entityWorld.setEntityState(this.TheEntity,(byte)12);
        this.TheEntity.addVelocity(0D, 0.4D, 0.0D);
    }

    public boolean shouldContinueExecuting()
    {
        return this.burrowTimer > 0 && !this.TheEntity.isInWater() && this.TheEntity.getAttackTarget() != null && !checkAttack();
    }

    public int getBuriedTimer()
    {
        return this.burrowTimer;
    }

    public void updateTask()
    {
        this.burrowTimer = Math.max(0, this.burrowTimer - 1);
    }

    protected boolean checkAttack()
    {
        EntityLivingBase livingBase = this.TheEntity.getAttackTarget();
        double d1 = this.TheEntity.getDistanceSq(livingBase.posX, livingBase.getEntityBoundingBox().minY, livingBase.posZ);
        double d0 = (this.TheEntity.width * 2.0F * this.TheEntity.width * 2.0F + livingBase.width);

        return d1 <= d0;
    }


    /*mutex bits (masking)
     1 = swimming, begging, watch closest
     3 = swimming
     5 = begging, watch c
     7 = nothing B)
     8 = everything
     */
}
