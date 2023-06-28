package com.unoriginal.beastslayer.gui;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class InventoryWitchcraft extends InventoryBasic implements IInteractionObject {

    private final BlockPos pos;

    public InventoryWitchcraft(String s, BlockPos pos) {
        super(s, true, 5);
        this.pos = pos;

    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerWitchcraft(inventoryPlayer, this, new InventoryWitchcraftResult("witchcraft_table", this.pos), player, this.pos);
    }

    @Override
    public String getGuiID() {
        return "ancientbeasts:witchcraft_table";
    }
}
