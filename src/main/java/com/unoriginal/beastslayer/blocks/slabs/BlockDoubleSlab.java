package com.unoriginal.beastslayer.blocks.slabs;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class BlockDoubleSlab extends BlockSlabBase{

    public BlockDoubleSlab(String name, Material materialIn,  BlockSlab half) {
        super(name, materialIn, half);
        this.setCreativeTab(null);
        this.setHardness(2.0F);
    }

    @Override
    public boolean isDouble() {
        return true;
    }
}
