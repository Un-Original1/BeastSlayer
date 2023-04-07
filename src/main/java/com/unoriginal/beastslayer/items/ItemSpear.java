package com.unoriginal.beastslayer.items;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.item.ItemTool;

public class ItemSpear extends ItemTool {

    public ItemSpear(String name) {
        super(4F, -3.0F, ToolMaterial.IRON, Sets.newHashSet());
        setCreativeTab(BeastSlayer.BEASTSTAB);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(250);
    }
}
