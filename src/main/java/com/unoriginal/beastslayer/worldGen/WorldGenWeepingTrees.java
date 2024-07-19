package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.blocks.BlockLeavesAB;
import com.unoriginal.beastslayer.blocks.BlockRotatedPillarBase;
import com.unoriginal.beastslayer.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldGenWeepingTrees extends WorldGenAbstractTree {
    public static final IBlockState LEAF = ModBlocks.CURSED_LEAVES.getDefaultState().withProperty(BlockLeavesAB.CHECK_DECAY, Boolean.FALSE);
    public static final IBlockState FRUIT = ModBlocks.HANGING_LEAVES.getDefaultState();
    public int minHeight = 3;
    public int maxHeight = 3;

    public WorldGenWeepingTrees(boolean notify) {
        super(notify);
    }

    public boolean generate(World world, Random rand, BlockPos pos) {

        //height 3-6
        //max ext 0-4

        int i = rand.nextInt(maxHeight + 1) + minHeight;
        boolean flag = true;
        if (pos.getY() >= 1 && pos.getY() + i + 1 <= 256) {
            for (int j = pos.getY(); j <= pos.getY() + 1 + i; ++j) {
                int k = 1;

                if (j == pos.getY()) {
                    k = 0;
                }

                if (j >= pos.getY() + 1 + i - 2) {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
                    for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < world.getHeight()) {
                            if (!this.isReplaceable(world, blockpos$mutableblockpos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }
            if (!flag) {
                return false;
            } else {
                BlockPos down = pos.down();
                IBlockState state = world.getBlockState(down);
                boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (IPlantable)ModBlocks.CURSED_SAPLING);
                boolean branch = true;
                if (isSoil && pos.getY() < world.getHeight() - i - 1) {
                    state.getBlock().onPlantGrow(state, world, down, pos);

                    int i3 = pos.getX();
                    int j1 = pos.getZ();
                    int k1 = 0;

                    for (int l1 = 0; l1 < i; ++l1) {
                        List<EnumFacing> list = Lists.newArrayList(EnumFacing.Plane.HORIZONTAL);
                        Collections.shuffle(list, rand);

                        int i2 = pos.getY() + l1;

                        BlockPos blockpos = new BlockPos(i3, i2, j1);

                        state = world.getBlockState(blockpos);

                        if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos)) {
                            this.placeLogAt(world, blockpos);
                            k1 = i2;
                            if(rand.nextInt(20) == 0 && branch && k1 >= 3){
                                int k = rand.nextInt(4);
                                EnumFacing facing = EnumFacing.getHorizontal(k);
                                this.placeBushWithRot(world, blockpos.offset(facing, 1), facing, rand);
                                branch = false;
                            }

                        }
                    }

                    BlockPos blockpos2 = new BlockPos(i3, k1, j1);

                    this.placeExtendedLog(world, blockpos2.up(), rand);

                    return true;
                } else{
                    return false;
                }
            }
        }
        else{
                return false;
        }
    }

    private void placeLogAt(World worldIn, BlockPos pos)
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, ModBlocks.CURSED_LOG.getDefaultState().withProperty(BlockRotatedPillarBase.AXIS, EnumFacing.Axis.Y));
        }
    private void placeLogWithRot(World worldIn, BlockPos pos, EnumFacing facing)
    {
        if(facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
            this.setBlockAndNotifyAdequately(worldIn, pos, ModBlocks.CURSED_LOG.getDefaultState().withProperty(BlockRotatedPillarBase.AXIS, EnumFacing.Axis.X));
        } else {
            this.setBlockAndNotifyAdequately(worldIn, pos, ModBlocks.CURSED_LOG.getDefaultState().withProperty(BlockRotatedPillarBase.AXIS, EnumFacing.Axis.Z));
        }
    }
        private void placeExtendedLog(World worldIn, BlockPos pos, Random rand){
            int k = rand.nextInt(4);
            if(k == 0){
                this.placeLotofLeaves(worldIn, pos, rand);
            } else {
                int j = k+1;
                for(int l = 0; l < j; l++){
                EnumFacing facing = EnumFacing.getHorizontal(k);
                this.placeLogWithRot(worldIn,pos.offset(facing, l), facing);
                    if(l == j - 1){
                        this.placeLotofLeaves(worldIn, pos.offset(facing, l).up(), rand);
                    }
                }
            }
        }
        private void placeLotofLeaves(World world, BlockPos pos, Random random){
            this.placeLeafAt(world, pos);
            int maxleaves = 0;
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
            {
                this.placeLeafAt(world, pos.offset(facing, 1));
                this.placeLeafAt(world, pos.offset(facing, 1).down());
                if(random.nextInt(3) == 0 && maxleaves < 2){
                    maxleaves = maxleaves + 1;
                    this.PlaceFruitAt(world, pos.offset(facing, 1).down(2));
                }
                this.placeLeafAt(world, pos.offset(facing, 1).offset(facing.rotateY()).down());
                if(random.nextInt(3) == 0 && maxleaves < 2){
                    maxleaves = maxleaves + 1;
                    this.PlaceFruitAt(world, pos.offset(facing, 1).offset(facing.rotateY()).down(2));
                }
                if(random.nextInt(4) == 0){
                    this.placeLeafAt(world, pos.offset(facing, 1).offset(facing.rotateY()).down(2));
                    if(random.nextInt(3) == 0){
                        this.PlaceFruitAt(world, pos.offset(facing, 1).offset(facing.rotateY()).down(3));
                    }
                }
            }
        }
        private void PlaceFruitAt(World world, BlockPos pos){
            IBlockState state = world.getBlockState(pos);
            IBlockState state1 = world.getBlockState(pos.up());

            if (state.getBlock().isAir(state, world, pos) &&  state1.getBlock() == ModBlocks.CURSED_LEAVES)
            {
                this.setBlockAndNotifyAdequately(world, pos, FRUIT);
            }
        }

        private void placeLeafAt(World world, BlockPos pos){
            IBlockState state = world.getBlockState(pos);

            if (state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos))
            {
                this.setBlockAndNotifyAdequately(world, pos, LEAF);
            }
        }
    private void placeBushWithRot(World worldIn, BlockPos pos, EnumFacing facing, Random rand)
    {
        if(facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
            this.setBlockAndNotifyAdequately(worldIn, pos, ModBlocks.CURSED_LOG.getDefaultState().withProperty(BlockRotatedPillarBase.AXIS, EnumFacing.Axis.X));
            this.placeBushAround(worldIn, pos, rand);
        } else {
            this.setBlockAndNotifyAdequately(worldIn, pos, ModBlocks.CURSED_LOG.getDefaultState().withProperty(BlockRotatedPillarBase.AXIS, EnumFacing.Axis.Z));
            this.placeBushAround(worldIn, pos, rand);
        }
    }
    private void placeBushAround(World world, BlockPos pos, Random random){
        this.placeLeafAt(world, pos.up());
        int maxfruit=0;
        for (EnumFacing facing: EnumFacing.Plane.HORIZONTAL) {

            this.placeLeafAt(world, pos.offset(facing, 1));
            if (random.nextInt(4)== 0 && maxfruit == 0){
                maxfruit = 1;
                this.PlaceFruitAt(world, pos.offset(facing, 1).down());
            }
        }
    }
}

