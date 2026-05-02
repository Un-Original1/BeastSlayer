package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModParticles;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockModFlower extends BlockBush {
    public BlockModFlower(String name) {
        super(Material.PLANTS);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setTickRandomly(true);
        setHardness(0.0F);
        setLightLevel(0.3F);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if(rand.nextInt(2) == 0){
            for(int i = -8; i < 9; ++i){
                for(int j =-8; j < 9; ++j){
                    for(int k = -8; k < 9; ++k){
                        BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                        if(worldIn.getBlockState(pos1).getBlock() instanceof IGrowable && rand.nextInt(3) == 0){
                            IGrowable igrowable = (IGrowable)worldIn.getBlockState(pos1).getBlock();
                            if(igrowable.canGrow(worldIn, pos1, worldIn.getBlockState(pos1), worldIn.isRemote) && !worldIn.getBlockState(pos1).isFullBlock()) {
                                igrowable.grow(worldIn, rand, pos1, worldIn.getBlockState(pos1));
                            }
                        }

                    }
                }
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.8D - (double)(rand.nextFloat() * 0.1F);
        double d1 = (double)pos.getY() + 0.8D - (double)(rand.nextFloat() * 0.1F);
        double d2 = (double)pos.getZ() + 0.8D - (double)(rand.nextFloat() * 0.1F);
        double d3 = (double)(0.4F - (rand.nextFloat() + rand.nextFloat()) * 0.4F);

        if (rand.nextInt(5) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.END_ROD, d0 + d3, d1 + d3, d2 +d3, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
        }
    }
}
