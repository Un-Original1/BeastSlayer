package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockModCauldron extends Block{
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockModCauldron(String name) {
        super(Material.IRON, MapColor.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 3));
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(0.5F);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    public void addCollisionBoxToList(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
        addCollisionBoxToList(p_185477_3_, p_185477_4_, p_185477_5_, AABB_LEGS);
        addCollisionBoxToList(p_185477_3_, p_185477_4_, p_185477_5_, AABB_WALL_WEST);
        addCollisionBoxToList(p_185477_3_, p_185477_4_, p_185477_5_, AABB_WALL_NORTH);
        addCollisionBoxToList(p_185477_3_, p_185477_4_, p_185477_5_, AABB_WALL_EAST);
        addCollisionBoxToList(p_185477_3_, p_185477_4_, p_185477_5_, AABB_WALL_SOUTH);
    }

    public AxisAlignedBB getBoundingBox(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
        return FULL_BLOCK_AABB;
    }

    public boolean isOpaqueCube(IBlockState p_149662_1_) {
        return false;
    }

    public boolean isFullCube(IBlockState p_149686_1_) {
        return false;
    }

    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        int lvt_5_1_ = state.getValue(LEVEL);
        float lvt_6_1_ = (float)pos.getY() + (6.0F + (float)(3 * lvt_5_1_)) / 16.0F;
        if (!world.isRemote && entity.isBurning() && lvt_5_1_ > 0 && entity.getEntityBoundingBox().minY <= (double)lvt_6_1_) {
            entity.extinguish();
            this.setWaterLevel(world, pos, state, lvt_5_1_ - 1);
        }

    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
        ItemStack itemStack = player.getHeldItem(hand);
        world.scheduleUpdate(new BlockPos(pos), this, 20);
        if (itemStack.isEmpty()) {
            return true;
        }
        else {
            int level = state.getValue(LEVEL);
            Item item = itemStack.getItem();
            if (item == ModItems.DARK_GOOP) {
                if (level < 3 && !world.isRemote) {
                    if (!player.capabilities.isCreativeMode) {
                        player.getHeldItem(hand).shrink(1);
                    }

                    player.addStat(StatList.CAULDRON_FILLED);
                    this.setWaterLevel(world, pos, state, level + 1);
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return true;
            } else if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() == Blocks.LOG || itemBlock.getBlock() == Blocks.LOG2) {

                    if (level > 0 && !world.isRemote) {
                        if (!player.capabilities.isCreativeMode) {
                            itemStack.shrink(1);
                            if (itemStack.isEmpty()) {
                                player.setHeldItem(hand, new ItemStack(ModItems.CURSED_WOOD));
                            } else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.CURSED_WOOD))) {
                                player.dropItem(new ItemStack(ModItems.CURSED_WOOD), false);
                            }
                        }

                        player.addStat(StatList.CAULDRON_USED);
                        this.setWaterLevel(world, pos, state, level - 1);
                        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    return true;
                } else {
                    return false;
                }
            }
            else {
                    return false;
            }

        }

    }

    public void setWaterLevel(World world, BlockPos pos, IBlockState state, int value) {
        world.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp(value, 0, 3)), 2);
        world.updateComparatorOutputLevel(pos, this);
        if(value == 0 && !world.isRemote){
            //same as if level was 0
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
        }
    }

    public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
        return Items.CAULDRON;
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Items.CAULDRON);
    }

    public boolean hasComparatorInputOverride(IBlockState p_149740_1_) {
        return true;
    }

    public int getComparatorInputOverride(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
        return p_180641_1_.getValue(LEVEL);
    }

    public IBlockState getStateFromMeta(int p_176203_1_) {
        return this.getDefaultState().withProperty(LEVEL, p_176203_1_);
    }

    public int getMetaFromState(IBlockState p_176201_1_) {
        return p_176201_1_.getValue(LEVEL);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    public boolean isPassable(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
        return true;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        if (p_193383_4_ == EnumFacing.UP) {
            return BlockFaceShape.BOWL;
        } else {
            return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
        }
    }
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if(this.getMetaFromState(world.getBlockState(pos)) == 0 && !world.isRemote){
            //same as if level was 0
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
        }
    }
}
