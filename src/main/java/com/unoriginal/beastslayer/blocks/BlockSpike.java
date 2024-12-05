package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpike extends BlockDirectional {
    //public static final PropertyDirection FACING = BlockDirectional.FACING;
    private final DamageSource source = new DamageSource("ancientbeasts:spikes").setDamageBypassesArmor();

    protected static final AxisAlignedBB WEST = new AxisAlignedBB(0.25D, 0.0625D, 0.0625D, 1D, 0.9375D, 0.9375D);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0.0D, 0.0625D, 0.0625D, 0.75D, 0.9375D, 0.9375D);

    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.0625D, 0.0625D, 0.25D, 0.9375D, 0.9375D, 1D);
    protected static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.0625D, 0.0625D, 0.0D, 0.9375D, 0.9375D, 0.75D);
    protected static final AxisAlignedBB BOTTOM = new AxisAlignedBB(0.0625D, 0.25D, 0.0625D, 0.9375D, 1D, 0.9375D);
    protected static final AxisAlignedBB TOP = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.75D, 0.9375D);

    public BlockSpike(String name) {
        super(Material.IRON);
        this.setHardness(3.5F);
        this.setBlockUnbreakable();
        this.setSoundType(SoundType.METAL);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
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
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(meta));
        return iblockstate;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
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

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityItem) {
            return;
        }

        if (entity.fallDistance >= 5F) {
            entity.attackEntityFrom(source, 20F);
        }
        else {
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            int damageTodo = (int)((motionX + motionY + motionZ) / 1.5D);
            entity.attackEntityFrom(source, 2F + damageTodo);
        }
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case UP:
            default:
                return TOP;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
            case DOWN:
                return BOTTOM;
        }
    }

}
