package com.unoriginal.beastslayer.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.world.IInteractionObject;

public class InventoryWitchcraft extends InventoryBasic implements IInteractionObject {

    public InventoryWitchcraft(String s) {
        super(s, true, 5);
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerWitchcraft(inventoryPlayer, this, player);
    }

    @Override
    public String getGuiID() {
        return "ancientbeasts:witchcraft_table";
    }
}
