package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.init.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWitchcraft extends GuiContainer {
    private InventoryPlayer inventoryPlayer;
    private static final ResourceLocation BACKGROUND = new ResourceLocation("ancientbeasts:textures/gui/witchcraft_table.png");

    public GuiWitchcraft(ContainerWitchcraft containerWitchcraft, InventoryPlayer inventoryPlayer) {
        super(containerWitchcraft);
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
            String s = new ItemStack(ModBlocks.WITCHCRAFT_TABLE).getDisplayName();
            this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 4, 4210752);
            this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 4, 4210752);

         //   this.renderHoveredToolTip(mouseX - this.xSize / 2 - 35, mouseY - this.ySize / 2 + 45);
        }
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
       // this.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

    }

}
