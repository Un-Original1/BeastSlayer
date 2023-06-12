package com.unoriginal.beastslayer.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ContainerWitchcraft extends Container {
    private InventoryWitchcraft inventory;
    public ContainerWitchcraft(InventoryPlayer playerInventory, InventoryWitchcraft inventoryWitchcraft, EntityPlayer player){
        this.inventory = inventoryWitchcraft;

        //
        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 3, 62 * 18, 17 * 18));
        for(int x = 0; x < 3; ++x){
            this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, x + 3, 62 + x * 18, 18 * 18));
        }
        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 2 + 2 * 3, 64 * 18, 17 + 2 * 18));


        for (int y = 0; y < 3; ++y)
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }

    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        // write inventory to launcher item
      /*  if(launcherInv.launcher != null && launcherInv.launcher.getItem() instanceof ItemBlockBombLauncher) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            for (int i=0; i<launcherInv.getSizeInventory(); ++i)
                if (launcherInv.getStackInSlot(i) != null)
                    list.appendTag(launcherInv.getStackInSlot(i).writeToNBT(new NBTTagCompound()));
            if (!list.isEmpty()) {
                nbt.setTag("items", list);
                launcherInv.launcher.setTagCompound(nbt);
            }
            else
                launcherInv.launcher.setTagCompound(null);
        }*/
    }
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);


        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 45 && index > 8) {
                if (!this.mergeItemStack(itemstack1, 0, 9, false))
                    return ItemStack.EMPTY;
            }
            else if (!this.mergeItemStack(itemstack1, 9, 45, true))
                return ItemStack.EMPTY;

            if (itemstack1.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
