package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.AncientBeasts;

import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(AncientBeasts.BEASTSTAB);
        this.setMaxStackSize(64);
    }
}
