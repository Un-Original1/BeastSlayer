package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntityFrostashFox;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAISleep extends EntityAIBase {
    private final EntityFrostashFox fox;
    private boolean isSleeping;
    public int nextSleepTicks;

    public EntityAISleep(EntityFrostashFox entityIn)
    {
        this.fox = entityIn;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        if (this.fox.isInWater())
        {
            return false;
        }
        else if (!this.fox.onGround)
        {
            return false;
        }
        else if(!this.fox.world.isDaytime()){
            return false;
        }
        else if(this.nextSleepTicks > 0){
            return false;
        }
        else return !this.fox.world.canSeeSky(new BlockPos(fox.posX, fox.posY, fox.posZ));
    }
    public void startExecuting()
    {
        this.fox.getNavigator().clearPath();
        this.fox.setSleeping(true);
    }

    public void resetTask()
        {
            this.fox.setSleeping(false);
            this.nextSleepTicks = 12000;
        }

    public void setSleeping(boolean sleeping)
        {
            this.isSleeping = sleeping;
        }
}
