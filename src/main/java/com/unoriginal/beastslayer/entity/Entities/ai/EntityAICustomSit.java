package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICustomSit extends EntityAIBase {
    private final EntitySucc succ;
    private boolean isSitting;

    public EntityAICustomSit(EntitySucc entityIn)
    {
        this.succ = entityIn;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        if (!this.succ.isFriendly())
        {
            return false;
        }
        else if (this.succ.isInWater())
        {
            return false;
        }
        else if (!this.succ.onGround)
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.succ.getFriend();

            if (entitylivingbase == null)
            {
                return true;
            }
            else
            {
                return this.succ.getDistanceSq(entitylivingbase) < 144.0D && entitylivingbase.getRevengeTarget() != null ? false : this.isSitting;
            }
        }
    }

    public void startExecuting()
    {
        this.succ.getNavigator().clearPath();
        this.succ.setSitting(true);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(this.succ.getFriend() != null && this.isSitting){
            this.resetTask();
            return false;
        } else {
            return super.shouldContinueExecuting();
        }
    }

    public void resetTask()
    {
        this.succ.setSitting(false);
    }

    public void setSitting(boolean sitting)
    {
        this.isSitting = sitting;
    }
}
