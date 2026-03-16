package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.item.Item;

public class ItemSelfContainer extends Item {
    public ItemSelfContainer(String registryName) {
        this.setRegistryName(registryName);
        this.setUnlocalizedName(registryName);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
    }
}
