package com.unoriginal.beastslayer.entity.Entities.ai;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class EntityAIStalk extends EntityAIBase
{
    private final EntitySucc temptedEntity;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private EntityPlayer temptingPlayer;
    private int delayTemptCounter;


    public EntityAIStalk(EntitySucc temptedEntityIn, double speedIn)
    {
        this.temptedEntity = temptedEntityIn;
        this.speed = speedIn;
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
            this.temptingPlayer = this.temptedEntity.world.getClosestPlayerToEntity(this.temptedEntity, 16.0D);

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
        return this.shouldExecute();
    }

    public void startExecuting()
    {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
    }

    public void resetTask()
    {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPath();
        this.teleportRandomly();
        this.delayTemptCounter = 100;
    }

    public void updateTask()
    {

        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, (float)(this.temptedEntity.getHorizontalFaceSpeed() + 20), (float)this.temptedEntity.getVerticalFaceSpeed());

        if(this.temptedEntity.isLookingAtMe(this.temptingPlayer)){
            this.teleportRandomly();
        }

        if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 9D)
        {
            this.temptedEntity.getNavigator().clearPath();
        }
        else
        {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
        }
    }

    public void teleportRandomly()
    {

        double d0 = this.temptedEntity.posX + (this.temptedEntity.getRNG().nextDouble() - 0.5D) * 32.0D;
        double d1 = this.temptedEntity.posY + (double)(this.temptedEntity.getRNG().nextInt(16) - 8);
        double d2 = this.temptedEntity.posZ + (this.temptedEntity.getRNG().nextDouble() - 0.5D) * 32.0D;


        for (int l = 0; l <= 30; ++l) {
            if (isTeleportFriendlyBlock(d0, d1, d2)) {
                this.temptedEntity.teleport();
                this.temptedEntity.setLocationAndAngles(d0, d1, d2, this.temptedEntity.rotationYaw, this.temptedEntity.rotationPitch);
                break;
            }
        }
    }

    protected boolean isTeleportFriendlyBlock(double x, double y, double z)
    {
        BlockPos blockpos = new BlockPos(x, y -1, z);
        IBlockState iblockstate = this.temptedEntity.world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(this.temptedEntity.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.temptedEntity) && this.temptedEntity.world.isAirBlock(blockpos.up()) && this.temptedEntity.world.isAirBlock(blockpos.up(2) ) && this.temptedEntity.world.isAirBlock(blockpos.up(3));
    }

}
