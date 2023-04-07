package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

//Blueberry's idea
public class BlockSkewer extends BlockDirectional {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0.0625D, 0.0625D, 0.95D, 0.95D, 0.95D);
    protected static final AxisAlignedBB AABB2 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
 //   public static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockSkewer(String name) {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setSoundType(SoundType.METAL);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            this.updateSkewerState(worldIn, pos);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!worldIn.isRemote){
            this.updateSkewerState(worldIn, pos);
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

    public void updateSkewerState(World world, BlockPos pos) {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);

        int distance = 1, oldDistance = 1;
        updateSkewerState(world, pos, facing, distance, oldDistance);
    }

    private void updateSkewerState(World world, BlockPos pos, EnumFacing direction, int newDistance, int previousDistance) {
        // If distance has changed remove spikes, this is impossible to happen but just in case
        if(newDistance != previousDistance) {
            BlockPos oldPos = pos.offset(direction, previousDistance);
            removeSpike(world, oldPos);
        }
        BlockPos spikePos = pos.offset(direction, newDistance);
        AxisAlignedBB axisalignedbb = AABB2.offset(pos);
        List<? extends Entity > list = world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
        if (!world.isRemote && list.isEmpty() ) {
            removeSpike(world, spikePos);
        }
        if(world.getBlockState(spikePos).getBlock() == ModBlocks.SPIKE) {
            world.scheduleUpdate(new BlockPos(pos), this, 40);
        }
    }

    public void removeSpike(World world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock() == ModBlocks.SPIKE) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, ModSounds.SPIKES_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);//placeholder
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos spikePos = pos.offset(state.getValue(FACING), 1);
        this.removeSpike(worldIn, spikePos);
        super.breakBlock(worldIn, pos, state);
    }
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entityIn)
    {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);
        BlockPos spikePos = pos.offset(facing, 1);

        if(!world.isRemote) {
            float hardness = world.getBlockState(spikePos).getBlock().getDefaultState().getBlockHardness(world, spikePos);
            if( hardness <= 4.0F && hardness >= 0.0F) {
                if(world.getBlockState(spikePos).getBlock() != ModBlocks.SPIKE){
                    world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, ModSounds.SPIKES_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                world.destroyBlock(spikePos, true);
                world.setBlockState(spikePos, ModBlocks.SPIKE.getDefaultState().withProperty(BlockSpike.FACING, facing));
                this.updateSkewerState(world, pos);
                world.scheduleUpdate(new BlockPos(pos), this, 20);
            }
        }
    }
}
