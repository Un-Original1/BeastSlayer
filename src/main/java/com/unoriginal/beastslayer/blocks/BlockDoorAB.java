package com.unoriginal.beastslayer.blocks;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDoorAB extends BlockDoor {

    public BlockDoorAB(Material materialIn) {
        super(materialIn);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random Rand, int fortune) {
        return Item.getItemFromBlock(this);

    }
    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }
}

