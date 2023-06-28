package com.unoriginal.beastslayer.gui;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.recipe.WitchcraftRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import java.util.Stack;

public class SlotWitchResult extends Slot {
    private final InventoryWitchcraft craftMatrix;
    private final WitchcraftRecipeHandler handler;
    private final EntityPlayer player;

    public SlotWitchResult(EntityPlayer player, InventoryWitchcraft craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition, WitchcraftRecipeHandler handler)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = craftingInventory;
        this.handler = handler;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        if(stack != ItemStack.EMPTY) {
            for (int i = 0; i < 5; i++) {
                ItemStack leftover = this.craftMatrix.getStackInSlot(i);
                leftover.shrink(1);
                this.craftMatrix.setInventorySlotContents(i, leftover);
            }
            this.handler.setLastCraftablebyQuality(0, null);
        }

        return stack;
    }
}
