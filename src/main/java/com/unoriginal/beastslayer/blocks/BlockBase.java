package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {
    public BlockBase(String name, Material materialIn, SoundType type, float hardness) {
        super(materialIn);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setSoundType(type);
        this.setHardness(hardness);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }
}
