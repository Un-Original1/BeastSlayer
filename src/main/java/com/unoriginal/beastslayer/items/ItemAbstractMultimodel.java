package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.item.Item;

public abstract class ItemAbstractMultimodel extends Item {

    public ItemAbstractMultimodel(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(64);
    }

    public abstract void registerModels();
}
