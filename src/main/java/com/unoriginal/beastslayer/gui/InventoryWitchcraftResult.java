package com.unoriginal.beastslayer.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public class InventoryWitchcraftResult extends InventoryBasic implements IInteractionObject {


  //  private final NonNullList<ItemStack> stackResult = NonNullList.withSize(1, ItemStack.EMPTY);
    private final BlockPos pos;
    public InventoryWitchcraftResult(String title, BlockPos pos) {
        super(title, true, 1);
        this.pos = pos;

    }
    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerWitchcraft(inventoryPlayer, new InventoryWitchcraft("witchcraft_table", this.pos),this, player, this.pos);
    }
    @Override
    public String getGuiID() {
        return "ancientbeasts:witchcraft_table";
    }
}
