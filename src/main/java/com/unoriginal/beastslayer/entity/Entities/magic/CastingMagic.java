package com.unoriginal.beastslayer.entity.Entities.magic;

import com.unoriginal.beastslayer.entity.Entities.EntitySandy;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class CastingMagic<T extends EntityLiving & IMagicUser> extends EntityAIBase {
    private T hostMobEntity;
    public CastingMagic(T magicUserMob)
    {
        this.hostMobEntity = magicUserMob;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        if(hostMobEntity instanceof EntitySandy)
        {
            EntitySandy sandy = (EntitySandy) hostMobEntity;
            if(sandy.isSitting() || sandy.isBuried()) {
                return false;
            }
        }
        return this.hostMobEntity.getMagicUseTicks() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.hostMobEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        this.hostMobEntity.setMagicType(MagicType.NONE);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.hostMobEntity.getAttackTarget() != null)
        {
            this.hostMobEntity.getLookHelper().setLookPositionWithEntity(this.hostMobEntity.getAttackTarget(), (float) this.hostMobEntity.getHorizontalFaceSpeed(), (float) this.hostMobEntity.getVerticalFaceSpeed());
        }
    }
}
