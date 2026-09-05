package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityGloop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.JUMPBAR;

@SideOnly(Side.CLIENT)
public class GuiGloopBar extends Gui {
    private final ResourceLocation CUSTOM_ICONS = new ResourceLocation(BeastSlayer.MODID, "textures/gui/gui_icons.png");
    private Minecraft mc;
    public GuiGloopBar(Minecraft mc) {
        this.mc = mc;
    }
    public void renderGameOverlay()
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        renderJumpBar(i,j);
    }

    protected void renderJumpBar(int width, int height)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();

        //mc.mcProfiler.startSection("jumpBar");
        mc.getTextureManager().bindTexture(CUSTOM_ICONS);
        float charge = 1F;

        if(mc.player.isBeingRidden()) {
            if(mc.player.getPassengers().get(0) instanceof EntityGloop) {
                EntityGloop entityGloop = (EntityGloop)mc.player.getPassengers().get(0);
                charge = (float) entityGloop.getBalloonClient() / 800F;
            }
        }

        final int barWidth = 182;
        int x = (width / 2) - (barWidth / 2);
        int filled = (int)(charge * (float)(barWidth + 1));
        int top = height - 32 + 3;

        drawTexturedModalRect(x, top, 0, 84, barWidth, 5);

        if (filled > 0)
        {
            this.drawTexturedModalRect(x, top, 0, 89, filled, 5);
        }

        GlStateManager.enableBlend();
       // mc.mcProfiler.endSection();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
