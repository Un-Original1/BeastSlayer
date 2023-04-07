package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSpikeTrap extends BlockDirectional {
   // public static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockSpikeTrap(String name) {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setSoundType(SoundType.METAL);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!world.isRemote) {
            this.updatePokeyState(world, pos);
        }
    }


    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            this.updatePokeyState(worldIn, pos);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);

        if (!worldIn.isRemote) {
            this.updatePokeyState(worldIn, pos);
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(meta));
        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public void updatePokeyState(World world, BlockPos pos) {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);

        int distance = 1, oldDistance = 1;
        updatePokeyState(world, pos, world.isBlockPowered(pos), facing, distance, oldDistance);
    }

    private void updatePokeyState(World world, BlockPos pos, boolean powered, EnumFacing direction, int newDistance, int previousDistance) {
        // If distance has changed remove spikes, this is impossible to happen but just in case
        if(newDistance != previousDistance) {
            BlockPos oldPos = pos.offset(direction, previousDistance);
            removeSpike(world, oldPos);
        }

        BlockPos spikePos = pos.offset(direction, newDistance);

        if(powered && !world.isRemote) {
            float hardness = world.getBlockState(spikePos).getBlock().getDefaultState().getBlockHardness(world, spikePos);
            if( hardness <= 4.0F && hardness >= 0.0F) {

                world.destroyBlock(spikePos, true);
                world.setBlockState(spikePos, ModBlocks.SPIKE.getDefaultState().withProperty(BlockSpike.FACING, direction));
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, ModSounds.SPIKES_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        } else if (!world.isRemote) {
            removeSpike(world, spikePos);
        }
    }

    public void removeSpike(World world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock() == ModBlocks.SPIKE) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            if(!world.isRemote) {
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, ModSounds.SPIKES_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F); //placeholder
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos spikePos = pos.offset(state.getValue(FACING), 1);
        this.removeSpike(worldIn, spikePos);
        super.breakBlock(worldIn, pos, state);
    }
}
