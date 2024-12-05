package com.unoriginal.beastslayer.entity.Entities.ai.navigation;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PathNavigateAvoidLeaves extends PathNavigateGround {
    private boolean avoidLeaves;

    public PathNavigateAvoidLeaves(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    public boolean setPath(@Nullable Path pathentityIn, double speedIn)
    {
        if (pathentityIn == null)
        {
            this.currentPath = null;
            return false;
        }
        else
        {
            this.AvoidLeavesPath();
           return super.setPath(pathentityIn, speedIn);
        }
    }

    public void AvoidLeavesPath()
    {
        if (this.currentPath != null)
        {
            for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
            {
                PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
                PathPoint pathpoint1 = i + 1 < this.currentPath.getCurrentPathLength() ? this.currentPath.getPathPointFromIndex(i + 1) : null;
                IBlockState iblockstate = this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z));
                Block block = iblockstate.getBlock();

                if (block == Blocks.CAULDRON)
                {
                    this.currentPath.setPoint(i, pathpoint.cloneMove(pathpoint.x, pathpoint.y + 1, pathpoint.z));

                    if (pathpoint1 != null && pathpoint.y >= pathpoint1.y)
                    {
                        this.currentPath.setPoint(i + 1, pathpoint1.cloneMove(pathpoint1.x, pathpoint.y + 1, pathpoint1.z));
                    }
                }
            }
        }

        if (this.avoidLeaves)
        {
           // BeastSlayer.logger.debug(this.world.getBlockState(new BlockPos(MathHelper.floor(this.entity.posX), (int)(this.entity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor(this.entity.posZ)).down()).getBlock() );
            if (!(this.world.getBlockState(new BlockPos(MathHelper.floor(this.entity.posX), (int)(this.entity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor(this.entity.posZ)).down()).getBlock() instanceof BlockLeaves))
            {
                return;
            }
            if((this.currentPath != null ? this.currentPath.getCurrentPathLength() : 0) > 0) {


                for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
                    PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
                    if (!(this.world.getBlockState(new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z).down()).getBlock() instanceof BlockLeaves)) {
                        if (this.currentPath.getCurrentPathLength() > 0) {
                            this.currentPath.setCurrentPathLength(i - 1);
                        } else {
                            break;
                        }
                        return;
                    }
                }
            }
       }
    }


    public boolean canEntityStandOnPos(BlockPos pos)
    {
        return super.canEntityStandOnPos(pos) && !(this.world.getBlockState(pos.down()).getBlock() instanceof BlockLeaves);
    }

    public void setAvoidLeaves(boolean avoidSun)
    {
        this.avoidLeaves = avoidSun;
    }
}
