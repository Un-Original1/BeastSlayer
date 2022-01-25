package com.unoriginal.ancientbeasts.tab;

import com.unoriginal.ancientbeasts.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {

    public ModTab(String label){ super("beaststab");}

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.LOGO);
    }
}
