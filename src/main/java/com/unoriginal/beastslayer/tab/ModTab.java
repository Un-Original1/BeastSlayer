package com.unoriginal.beastslayer.tab;

import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {

    public ModTab(String label){ super("beaststab");}

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.LOGO);
    }
}
