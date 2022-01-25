package com.unoriginal.ancientbeasts.entity.Entities.ai;

import com.unoriginal.ancientbeasts.entity.Entities.EntityLilVessel;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIPatrol extends EntityAIBase
{
    private final EntityLilVessel guard;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private final double movementSpeed;

    public EntityAIPatrol(EntityLilVessel creatureIn, double speedIn)
    {
        this.guard = creatureIn;
        this.movementSpeed = speedIn;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        if (this.guard.isWithinHomeDistanceCurrentPosition())
        {
            return false;
        }
        else if (!guard.isTamed()){
            return false;
        }
        else if (!guard.isPatrolling()){
            return false;
        }
        else
        {
            BlockPos blockpos = this.guard.getHomePosition();
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.guard, 16, 7, new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ()));

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.movePosX = vec3d.x;
                this.movePosY = vec3d.y;
                this.movePosZ = vec3d.z;
                return true;
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        return !this.guard.getNavigator().noPath();
    }

    public void startExecuting()
    {
        this.guard.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}
