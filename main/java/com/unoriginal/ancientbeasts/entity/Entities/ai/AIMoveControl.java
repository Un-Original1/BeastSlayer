package com.unoriginal.ancientbeasts.entity.Entities.ai;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.MathHelper;

public class AIMoveControl extends EntityMoveHelper
{
    EntityMob mob;
    public AIMoveControl(EntityMob mob)
    {
        super(mob);
        this.mob = mob;
    }

    public void onUpdateMoveHelper()
    {
        if (this.action == EntityMoveHelper.Action.MOVE_TO)
        {
            double d0 = this.posX - this.mob.posX;
            double d1 = this.posY - this.mob.posY;
            double d2 = this.posZ - this.mob.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);

            if (d3 < this.mob.getEntityBoundingBox().getAverageEdgeLength())
            {
                this.action = EntityMoveHelper.Action.WAIT;
                this.mob.motionX *= 0.5D;
                this.mob.motionY *= 0.5D;
                this.mob.motionZ *= 0.5D;
            }
            else
            {
                this.mob.motionX += d0 / d3 * 0.05D * this.speed;
                this.mob.motionY += d1 / d3 * 0.05D * this.speed;
                this.mob.motionZ += d2 / d3 * 0.05D * this.speed;

                if (this.mob.getAttackTarget() == null)
                {
                    this.mob.rotationYaw = -((float)MathHelper.atan2(this.mob.motionX, this.mob.motionZ)) * (180F / (float)Math.PI);
                }
                else
                {
                    double d4 = this.mob.getAttackTarget().posX - this.mob.posX;
                    double d5 = this.mob.getAttackTarget().posZ - this.mob.posZ;
                    this.mob.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                }
                this.mob.renderYawOffset = this.mob.rotationYaw;
            }
        }
    }
}
