package com.unoriginal.beastslayer.entity.Entities.ai;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;

import java.util.Set;

public class EntityAIStalk extends EntityAIBase
{
    private final EntitySucc temptedEntity;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double pitch;
    private double yaw;
    private EntityPlayer temptingPlayer;
    private int delayTemptCounter;
    private boolean isRunning;
    private final boolean scaredByPlayerMovement;


    public EntityAIStalk(EntitySucc temptedEntityIn, double speedIn, boolean scaredByPlayerMovementIn)
    {
        this.temptedEntity = temptedEntityIn;
        this.speed = speedIn;
        this.scaredByPlayerMovement = scaredByPlayerMovementIn;
        this.setMutexBits(3);

        if (!(temptedEntityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for StalkGoal");
        }
    }

    public boolean shouldExecute()
    {
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
        else
        {
            this.temptingPlayer = this.temptedEntity.world.getClosestPlayerToEntity(this.temptedEntity, 10.0D);

            if (this.temptingPlayer == null)
            {
                return false;
            }
            else
            {
                return !this.temptedEntity.isFriendly() && this.temptedEntity.isStalking();
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        if (this.scaredByPlayerMovement)
        {
            if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 36.0D)
            {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D)
                {
                    return false;
                }

                if (Math.abs((double)this.temptingPlayer.rotationPitch - this.pitch) > 5.0D || Math.abs((double)this.temptingPlayer.rotationYaw - this.yaw) > 5.0D)
                {
                    return false;
                }
            }
            else
            {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }

            this.pitch = (double)this.temptingPlayer.rotationPitch;
            this.yaw = (double)this.temptingPlayer.rotationYaw;
        }

        return this.shouldExecute();
    }

    public void startExecuting()
    {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
    }

    public void resetTask()
    {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPath();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    public void updateTask()
    {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, (float)(this.temptedEntity.getHorizontalFaceSpeed() + 20), (float)this.temptedEntity.getVerticalFaceSpeed());

        if(this.temptedEntity.isLookingAtMe(this.temptingPlayer)){
            this.temptedEntity.teleportRandomly();
        }

        if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 6.25D)
        {
            this.temptedEntity.getNavigator().clearPath();
        }
        else
        {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
        }
    }

    public boolean isRunning()
    {
        return this.isRunning;
    }
}
