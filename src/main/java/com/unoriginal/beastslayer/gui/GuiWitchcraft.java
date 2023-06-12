package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiWitchcraft extends GuiContainer {
    private InventoryPlayer inventoryPlayer;


    public GuiWitchcraft(ContainerWitchcraft containerWitchcraft, InventoryPlayer inventoryPlayer) {
        super(containerWitchcraft);
        this.inventoryPlayer = inventoryPlayer;
    }
//text
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        {
            String s = new ItemStack(ModBlocks.WITCHCRAFT_TABLE).getDisplayName();
            this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
            this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);}
    }
//png
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
