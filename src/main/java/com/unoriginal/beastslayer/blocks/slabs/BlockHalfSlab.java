package com.unoriginal.beastslayer.blocks.slabs;

import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemSlab;

public class BlockHalfSlab extends BlockSlabBase{

    public BlockHalfSlab(String name, Material materialIn,  BlockSlab half) {
        super(name, materialIn, half);
        this.setHardness(2.0F);
    }
    @Override
    public boolean isDouble() {
        return false;
    }
}
