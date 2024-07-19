package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.blocks.tile.TileEntityWitchcraftTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWitchcraft extends GuiContainer {
    private InventoryPlayer inventoryPlayer;
    private final TileEntityWitchcraftTable witchcraftTable;
    private static final ResourceLocation BACKGROUND = new ResourceLocation("ancientbeasts:textures/gui/witchcraft_table.png");

    public GuiWitchcraft(ContainerWitchcraft containerWitchcraft, InventoryPlayer inventoryPlayer, TileEntityWitchcraftTable witchcraftTable) {
        super(containerWitchcraft);
        this.witchcraftTable = witchcraftTable;
        this.inventoryPlayer = inventoryPlayer;
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

//text
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        {
            String s = this.witchcraftTable.getDisplayName().getUnformattedText();
            this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 4, 4210752);
            this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 4, 4210752);

         //   this.renderHoveredToolTip(mouseX - this.xSize / 2 - 35, mouseY - this.ySize / 2 + 45);
        }
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);



        if(!this.witchcraftTable.getStackInSlot(5).isEmpty()){
            this.drawTexturedModalRect(i + 113, j + 23, 178, 57, 38, 40);
        } else {
            if ((this.witchcraftTable.getField(0)!=0 && this.witchcraftTable.getField(1)!= 0))
            {
                this.drawTexturedModalRect(i + 31, j + 26, 178, 21, 50, 34);
            }
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 90, j + 34, 178, 2, l, 17);

    }
    private int getCookProgressScaled(int pixels)
    {
        int i = this.witchcraftTable.getField(0);
        int j = this.witchcraftTable.getField(1);

        return j != 0 && i != 0 ? i * pixels / j : 0;
    }


}
