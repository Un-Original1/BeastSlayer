package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.blocks.tile.TileEntityMovingLight;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMovingLight extends Block implements ITileEntityProvider {
    public static AxisAlignedBB boundingBox = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 0.5D, 0.5D, 0.5D);
    public BlockMovingLight(String name)
    {
        super(Material.AIR);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setDefaultState(blockState.getBaseState());
        this.setTickRandomly(false);
        this.setLightLevel(1.0F);
        this.translucent = true;
      //  this.setBlockUnbreakable();
        //setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
    }

    @Override
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
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return true;
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return getDefaultState();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
    }
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

   /* @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.I;
    }*/
   @Override
   public EnumBlockRenderType getRenderType(IBlockState state) {
       return EnumBlockRenderType.INVISIBLE;
   }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this);
    }
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return boundingBox;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        TileEntityMovingLight te = new TileEntityMovingLight();
        return te;
    }

}
