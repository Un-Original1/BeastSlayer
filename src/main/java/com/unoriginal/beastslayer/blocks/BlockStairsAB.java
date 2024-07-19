package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairsAB extends BlockStairs {

    public BlockStairsAB(IBlockState modelState, String name) {
        super(modelState);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);

    }
}
