package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntityBoulderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILeapAtTarget;

public class EntityAILeapLadder extends EntityAILeapAtTarget {
    EntityLiving leaper;
    public EntityAILeapLadder(EntityBoulderer leapingEntity, float leapMotionYIn) {
        super(leapingEntity, leapMotionYIn);
        this.leaper = leapingEntity;
    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && this.leaper.isOnLadder();
    }
}
