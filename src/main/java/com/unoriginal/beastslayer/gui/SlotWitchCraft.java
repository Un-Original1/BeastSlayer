package com.unoriginal.beastslayer.gui;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Set;

public class SlotWitchCraft extends Slot {

    private static final Set<Item> CRAFT_ITEMS = Sets.newHashSet(
            ModItems.DUST,
            ModItems.BROKEN_TALISMAN,
            ModItems.TABLET,
            ModItems.CLOTH
    );

    public SlotWitchCraft(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
    //basically just for item classification
    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return stack == null || CRAFT_ITEMS.contains(stack.getItem());
    }
}
