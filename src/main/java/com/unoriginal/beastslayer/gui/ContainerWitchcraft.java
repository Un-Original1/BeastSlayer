package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageItemWitchcraft;
import com.unoriginal.beastslayer.recipe.WitchcraftRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ContainerWitchcraft extends Container {
    private final BlockPos pos;
    private InventoryWitchcraft inventoryWitchcraft;
    private InventoryWitchcraftResult result ;
    private WitchcraftRecipeHandler handler;

    public ContainerWitchcraft(InventoryPlayer playerInventory, InventoryWitchcraft inventoryWitchcraft, InventoryWitchcraftResult witchcraftResult, EntityPlayer player, BlockPos pos){
        this.pos = pos;
        this.inventoryWitchcraft = inventoryWitchcraft;
        this.result = witchcraftResult;
        this.handler = new WitchcraftRecipeHandler(player.world);

        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 0, 30 + 18, 17));
        for(int x = 0; x < 3; ++x){
            this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, x + 1, 30 + x * 18, 17 + 18));
        }
        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 4, 30 + 18, 17 + 2 * 18));


        for (int y = 0; y < 3; ++y)
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18)); //inv
            }
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142)); //main hand
        }

        this.addSlotToContainer(new SlotWitchResult(player, this.inventoryWitchcraft, this.result,0, 124,35, this.handler, this.result));

    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        ItemStack clickItemStack = super.slotClick(slotId, dragType, clickTypeIn, player);
        if(inventorySlots.size() > slotId && slotId >= 0)
        {
            if(inventorySlots.get(slotId) != null)
            {
                if(( inventorySlots.get(slotId)).inventory == this.inventoryWitchcraft)
                {
                    onCraftMatrixChanged(this.inventoryWitchcraft);
                }
            }
        }
        return clickItemStack;
    }


    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if(this.inventoryWitchcraft == inventoryIn){
            List<ItemStack> list = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                list.add(this.inventoryWitchcraft.getStackInSlot(i));
            }
          //   if(!this.handler.getWorld().isRemote) {
                ItemStack result = this.handler.getResults(list);
                if (result != null && !this.handler.world.isRemote) {
                    this.result.setInventorySlotContents(0, result);
                    this.handler.setLastCraftablebyQuality(0, null);
                    BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageItemWitchcraft(null, result));

            }
        }
      //  this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix, this.craftResult);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {

            super.onContainerClosed(playerIn);

            if (!playerIn.world.isRemote)
            {
                this.clearContainer(playerIn, playerIn.world, this.inventoryWitchcraft);
            }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);


        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            // //42 is the max size of our slots
            if (index == 0) //guess index is the slot we click,  merge item stack the range we can return it to
            {
                itemstack1.getItem().onCreated(itemstack1, playerIn.world, playerIn);

                if (!this.mergeItemStack(itemstack1, 5, 42, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index < 32 && index > 5) { //5 - 31
                if (!this.mergeItemStack(itemstack1, 32, 42, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 42 && index >= 32){ // 32 - 41
                if (!this.mergeItemStack(itemstack1, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
            }

            else if (!this.mergeItemStack(itemstack1, 5, 42, false)) { //1-5
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public void putStackInResultSlot( ItemStack stack)
    {
        this.result.setInventorySlotContents(0, stack);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.result && super.canMergeSlot(stack, slotIn);
    }
}
