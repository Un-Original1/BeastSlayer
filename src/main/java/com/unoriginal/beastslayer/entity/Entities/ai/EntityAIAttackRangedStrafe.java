package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRangedStrafe<T extends EntityMob & IRangedAttackMob> extends EntityAIBase
    {
        private final T entity;
        private final double moveSpeedAmp;
        private int attackCooldown;
        private final float maxAttackDistance;
        private int attackTime = -1;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;
        private float strafeValue;

        public EntityAIAttackRangedStrafe(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float maxDistance)
        {
            this.entity = p_i47515_1_;
            this.moveSpeedAmp = p_i47515_2_;
            this.attackCooldown = p_i47515_4_;
            this.maxAttackDistance = maxDistance * maxDistance;
            this.strafeValue = 0.5F;
            this.setMutexBits(3);
        }

        public EntityAIAttackRangedStrafe(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_, float strafeValue)
        {
            this.entity = p_i47515_1_;
            this.moveSpeedAmp = p_i47515_2_;
            this.attackCooldown = p_i47515_4_;
            this.maxAttackDistance = p_i47515_5_ * p_i47515_5_;
            this.strafeValue = strafeValue;
            this.setMutexBits(3);
        }


        public boolean shouldExecute()
        {
            return this.entity.getAttackTarget() != null;
        }

        public boolean shouldContinueExecuting()
        {
            return (this.shouldExecute() || !this.entity.getNavigator().noPath());
        }

        public void startExecuting()
        {
            super.startExecuting();
            this.entity.setSwingingArms(true);
        }

        public void resetTask()
        {
            super.resetTask();
            this.entity.setSwingingArms(false);
            this.seeTime = 0;
            this.attackTime = -1;
            this.entity.resetActiveHand();
        }

        public void updateTask()
        {
            EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

            if (entitylivingbase != null)
            {
                double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);

                if (flag)
                {
                    ++this.seeTime;
                }
                else
                {
                    this.seeTime = 0;
                }

                if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20)
                {
                    this.entity.getNavigator().clearPath();
                    ++this.strafingTime;
                }
                else
                {
                    this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
                    this.strafingTime = -1;
                }

                this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);

                if (--this.attackTime == 0)
                {
                    if (!flag)
                    {
                        return;
                    }

                    float f = MathHelper.sqrt(d0) / this.maxAttackDistance;
                    float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
                    this.entity.attackEntityWithRangedAttack(entitylivingbase, lvt_5_1_);
                    this.attackTime = MathHelper.floor(f * (float)(this.attackCooldown - this.attackCooldown) + (float)this.attackCooldown);
                }
                else if (this.attackTime < 0)
                {
                    float f2 = MathHelper.sqrt(d0) / this.maxAttackDistance;
                    this.attackTime = MathHelper.floor(f2 * (float)(this.attackCooldown - this.attackCooldown) + (float)this.attackCooldown);
                }

                if (this.strafingTime >= 20)
                {
                    if ((double)this.entity.getRNG().nextFloat() < 0.3D)
                    {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if ((double)this.entity.getRNG().nextFloat() < 0.3D)
                    {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                if (this.strafingTime > -1)
                {
                    if (d0 > (double)(this.maxAttackDistance * 0.75F))
                    {
                        this.strafingBackwards = false;
                    }
                    else if (d0 < (double)(this.maxAttackDistance * 0.25F))
                    {
                        this.strafingBackwards = true;
                    }

                    this.entity.getMoveHelper().strafe(this.strafingBackwards ? -this.strafeValue : this.strafeValue, this.strafingClockwise ? this.strafeValue : - this.strafeValue);
                    this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
                }
            }
        }
    }

