package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAISandyFollowOwner extends EntityAIFollowOwner {

    World world;
    private final EntityTameable tameable;
    public EntityAISandyFollowOwner(EntityTameable tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        super(tameableIn, followSpeedIn, minDistIn, maxDistIn);

        this.tameable = tameableIn;
        this.world = tameableIn.world;
    }
    @Override
    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
        IBlockState iblockstate = world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID
                && iblockstate.canEntitySpawn(this.tameable)
                && this.world.isAirBlock(blockpos.up())
                && this.world.isAirBlock(blockpos.up(2))
                && this.world.isAirBlock(blockpos.up(3))
                && this.world.isAirBlock(blockpos.up(4));
    }
}
