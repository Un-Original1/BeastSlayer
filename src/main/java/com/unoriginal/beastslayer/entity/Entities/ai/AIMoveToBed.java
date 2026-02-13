package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AIMoveToBed extends EntityAIMoveToBlock
{
    private final EntitySucc succ;

    public AIMoveToBed(EntitySucc succ, double p_i45315_2_)
    {
        super(succ, p_i45315_2_, 16);
        this.succ = succ;
    }

    public boolean shouldExecute()
    {
        return (this.succ.isFriendly() && !this.succ.isSitting() && super.shouldExecute());
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.succ.getAISit().setSitting(false);
    }

    public void resetTask()
    {
        super.resetTask();
        this.succ.setSitting(false);
        this.succ.setBed(false);
    }

    public void updateTask()
    {
        super.updateTask();
        this.succ.getAISit().setSitting(false);

        if (!this.getIsAboveDestination())
        {
            this.succ.setSitting(false);
        }
        else if (!this.succ.isSitting())
        {
            this.succ.setSitting(true);
            this.succ.setBed(true);
        }
    }

    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        if (!worldIn.isAirBlock(pos.up()))
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();


            return block == Blocks.BED && !iblockstate.getValue(BlockBed.OCCUPIED) && iblockstate.getValue(BlockBed.PART) == BlockBed.EnumPartType.HEAD;
        }
    }
}
