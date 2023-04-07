package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class AIMoveRandom extends EntityAIBase
{
    EntityLiving living;
    public AIMoveRandom(EntityLiving livingIn)
    {
        this.setMutexBits(1);
        living = livingIn;
    }

    public boolean shouldExecute()
    {
        return !this.living.getMoveHelper().isUpdating() && this.living.getRNG().nextInt(7) == 0;
    }

    public boolean shouldContinueExecuting()
    {
        return false;
    }

    public void updateTask()
    {
        BlockPos blockpos = new BlockPos(this.living);
        for (int i = 0; i < 3; ++i)
        {
            BlockPos blockpos1 = blockpos.add(this.living.getRNG().nextInt(15) - 7, this.living.getRNG().nextInt(9) - 5, this.living.getRNG().nextInt(15) - 7);

            if (this.living.world.isAirBlock(blockpos1))
            {
                this.living.getMoveHelper().setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                if (this.living.getAttackTarget() == null)
                {
                    this.living.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                }

                break;
            }
        }
    }
}
