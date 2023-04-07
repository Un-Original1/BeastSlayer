package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;

import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(64);
    }
}
