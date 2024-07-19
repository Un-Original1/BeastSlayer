package com.unoriginal.beastslayer.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWitchResult extends Slot {
   // private final IInventory craftMatrix;
  //  private final WitchcraftRecipeHandler handler;
  //  private final InventoryWitchcraftResult resultInv;
    private final EntityPlayer player;

    public SlotWitchResult(EntityPlayer player,IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
      //  this.craftMatrix = craftingInventory;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

 /*  public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {

        if(stack != ItemStack.EMPTY) {
      /*     List<ItemStack> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ItemStack leftover = this.craftMatrix.getStackInSlot(i);
                list.add(leftover);
                leftover.shrink(1);
                this.craftMatrix.setInventorySlotContents(i, leftover);


            }
           /* ItemStack newItem = this.handler.getResults(list);
            if (newItem != null && !this.handler.world.isRemote) {
                this.resultInv.setInventorySlotContents(0, newItem);
                this.handler.setLastCraftablebyQuality(0, null);
                BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageItemWitchcraft(null, newItem));
                this.putStack(newItem);
            }

        }

        return stack;
    }*/
}
