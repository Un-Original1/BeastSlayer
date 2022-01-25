package com.unoriginal.ancientbeasts.items;

import net.minecraft.item.Item;

public class HiddenItem extends Item {
    public HiddenItem(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setMaxStackSize(1);
    }
}
