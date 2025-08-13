package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFireIdol extends Block {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);

    public BlockFireIdol(String name) {
        super(Material.ROCK);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(3.0F);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(0.8F);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }



    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = ((float)pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
        double d1 = ((float)pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
        double d2 = ((float)pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        // Uses the gui handler registered to your mod to open the gui for the given gui id
        // open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)

        if (!playerIn.world.isRemote && playerIn.getHeldItemMainhand().getItem() == ModItems.FIRE_KEY) {
            if(!playerIn.capabilities.isCreativeMode) {
                playerIn.getHeldItemMainhand().shrink(1);
            }
            EntityFireElemental fireElemental = new EntityFireElemental(worldIn);
            fireElemental.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            worldIn.spawnEntity(fireElemental);
            worldIn.setBlockToAir(pos);
        }
        return true;
    }
}
