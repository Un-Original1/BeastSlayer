package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.ai.navigation.PathNavigateAvoidLeaves;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.pathfinding.PathNavigateGround;

public class AIRestrictLeaves extends EntityAIBase {
    private final EntityCreature entity;

    public AIRestrictLeaves(EntityCreature creature)
    {
        this.entity = creature;
    }

    public boolean shouldExecute()
    {
        if(this.entity instanceof AbstractTribesmen){
            return !((AbstractTribesmen)this.entity).isFiery();
        } else {
            return true;
        }
    }

    public void startExecuting()
    {
        ((PathNavigateAvoidLeaves)this.entity.getNavigator()).setAvoidLeaves(true);
    }

    public void resetTask()
    {
        ((PathNavigateAvoidLeaves)this.entity.getNavigator()).setAvoidLeaves(false);
    }
}
