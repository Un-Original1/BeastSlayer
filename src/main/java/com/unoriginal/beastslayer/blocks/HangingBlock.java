package com.unoriginal.beastslayer.blocks;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.init.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class HangingBlock extends Block implements IShearable {

    public HangingBlock(Material blockMaterialIn, String name   ) {
        super(blockMaterialIn);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setTickRandomly(true);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setHardness(1.0F);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {

        IBlockState soil = worldIn.getBlockState(pos.up());
        return soil.getBlock() == ModBlocks.CURSED_LEAVES;

    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);
        if (worldIn.rand.nextInt(40) == 1) {
            for (int i = 0; i < 11; i++) {
                IBlockState state1 = worldIn.getBlockState(pos.down(i + 1));
                if(state1.getBlock() == ModBlocks.CAULDRON){
                    BlockModCauldron cauldron = (BlockModCauldron) state1.getBlock();
                    cauldron.fillWithDrip(worldIn, pos.down(i+1));
                    break;
                } else if (state1.getBlock() == Blocks.CAULDRON){
                    BlockCauldron blockCauldron = (BlockCauldron) state1.getBlock();
                    if(blockCauldron.getMetaFromState(state1) == 0){
                        worldIn.setBlockState(pos.down(i + 1), ModBlocks.CAULDRON.getDefaultState().withProperty(BlockModCauldron.LEVEL, 1));
                    }
                }
                else if (state1.getBlock() != Blocks.AIR){
                    break;
                }
            }
        }
    }
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            //this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.CURSED_LEAVES;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if(rand.nextInt(3)== 0) {
            EnumFacing enumfacing = EnumFacing.random(rand);

            if (enumfacing != EnumFacing.UP && !worldIn.getBlockState(pos.offset(enumfacing)).isTopSolid()) {
                double d0 = pos.getX();
                double d1 = pos.getY();
                double d2 = pos.getZ();

                if (enumfacing == EnumFacing.DOWN) {
                    d1 = d1 - 0.05D;
                    d0 += rand.nextDouble();
                    d2 += rand.nextDouble();
                } else {
                    d1 = d1 + rand.nextDouble() * 0.8D;

                    if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                        d2 += rand.nextDouble();

                        if (enumfacing == EnumFacing.EAST) {
                            ++d0;
                        } else {
                            d0 += 0.05D;
                        }
                    } else {
                        d0 += rand.nextDouble();

                        if (enumfacing == EnumFacing.SOUTH) {
                            ++d2;
                        } else {
                            d2 += 0.05D;
                        }
                    }
                }

                worldIn.spawnParticle(ModParticles.DRIP, d0, d1, d2, 0.26D, 0.008D, 0.33D, 1);
            }
        }
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return true;
    }
}
