package com.unoriginal.beastslayer.gui;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageItemWitchcraft;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SlotWitchResult extends Slot {
    private final InventoryWitchcraft craftMatrix;
    private final WitchcraftRecipeHandler handler;
    private final InventoryWitchcraftResult resultInv;
    private final EntityPlayer player;

    public SlotWitchResult(EntityPlayer player, InventoryWitchcraft craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition, WitchcraftRecipeHandler handler, InventoryWitchcraftResult result)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = craftingInventory;
        this.handler = handler;
        this.resultInv = result;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {

        if(stack != ItemStack.EMPTY) {
            List<ItemStack> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ItemStack leftover = this.craftMatrix.getStackInSlot(i);
                list.add(leftover);
                leftover.shrink(1);
                this.craftMatrix.setInventorySlotContents(i, leftover);


            }
            this.handler.setLastCraftablebyQuality(0, null);
            ItemStack newItem = this.handler.getResults(list);
            if (newItem != null && !this.handler.world.isRemote) {
                this.resultInv.setInventorySlotContents(0, newItem);
                this.handler.setLastCraftablebyQuality(0, null);
                BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageItemWitchcraft(null, newItem));
                this.putStack(newItem);
            }

        }

        return stack;
    }
}
