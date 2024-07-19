package com.unoriginal.beastslayer.gui;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.blocks.tile.TileEntityWitchcraftTable;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class ContainerWitchcraft extends Container {
    private int cookTime;
    private int totalCookTime;
    private final IInventory tileWitchcraft;
    private static final Set<Item> CRAFT_ITEMS = Sets.newHashSet(
            ModItems.DUST,
            ModItems.BROKEN_TALISMAN,
            ModItems.TABLET,
            ModItems.CLOTH
    );

    public ContainerWitchcraft(InventoryPlayer playerInventory, TileEntityWitchcraftTable inventoryWitchcraft){

        this.tileWitchcraft = inventoryWitchcraft;

        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 0, 30 + 18, 17));
        for(int x = 0; x < 3; ++x){
            this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, x + 1, 30 + x * 18, 17 + 18));
        }
        this.addSlotToContainer(new SlotWitchCraft(inventoryWitchcraft, 4, 30 + 18, 17 + 2 * 18));
        this.addSlotToContainer(new SlotWitchResult(playerInventory.player, inventoryWitchcraft,5, 124,35));

        for (int y = 0; y < 3; ++y)
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18)); //inv
            }
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142)); //main hand
        }



    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        ItemStack clickItemStack = super.slotClick(slotId, dragType, clickTypeIn, player);
        if(inventorySlots.size() > slotId && slotId >= 0)
        {
            if(inventorySlots.get(slotId) != null)
            {
                if(( inventorySlots.get(slotId)).inventory == this.tileWitchcraft)
                {
                    onCraftMatrixChanged(this.tileWitchcraft);
                }
            }
        }
        return clickItemStack;
    }

//TODO Fully implement in tile entity
  /*  public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if(this.tileWitchcraft == inventoryIn){
            List<ItemStack> list = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                list.add(this.tileWitchcraft.getStackInSlot(i));
            }
          //   if(!this.handler.getWorld().isRemote) {
                ItemStack result = this.handler.getResults(list);
                if (result != null && !this.handler.world.isRemote) {
                    this.result.setInventorySlotContents(0, result);
                    //this.handler.setLastCraftablebyQuality(0, null);
                    BeastSlayerPacketHandler.sendPacketToAllPlayers(new MessageItemWitchcraft(null, result));

            }
        }
      //  this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix, this.craftResult);
    }
*/
   /* @Override
    public void onContainerClosed(EntityPlayer playerIn) {

            super.onContainerClosed(playerIn);

            if (!playerIn.world.isRemote)
            {
                this.clearContainer(playerIn, playerIn.world, this.inventoryWitchcraft);
            }
    }*/
   public void addListener(IContainerListener listener)
   {
       super.addListener(listener);
       listener.sendAllWindowProperties(this, this.tileWitchcraft);
   }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);


        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            // //42 is the max size of our slots
            if (index == 5) //guess index is the slot we click,  merge item stack the range we can return it to
            {
                itemstack1.getItem().onCreated(itemstack1, playerIn.world, playerIn);

                if (!this.mergeItemStack(itemstack1, 6, 42, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index > 5 && index < 42){
                if(CRAFT_ITEMS.contains(itemstack1.getItem())) {
                    if (!this.mergeItemStack(itemstack1, 0, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (index < 32 && index > 5) { //6 - 31
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

            if (index == 5)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public IInventory getTile(){
       return this.tileWitchcraft;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileWitchcraft.setField(id, data);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileWitchcraft.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileWitchcraft.getField(0));
            }

            if (this.totalCookTime != this.tileWitchcraft.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileWitchcraft.getField(1));
            }
        }

        this.cookTime = this.tileWitchcraft.getField(0);
        this.totalCookTime = this.tileWitchcraft.getField(1);
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileWitchcraft.isUsableByPlayer(playerIn);
    }

  public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return /*slotIn.inventory != this.result && */super.canMergeSlot(stack, slotIn);
    }
}
