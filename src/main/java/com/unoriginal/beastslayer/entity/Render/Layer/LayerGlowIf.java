package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LayerGlowIf implements LayerRenderer<EntityLiving> {
    private final ResourceLocation TEXTURE;
    private final RenderLiving renderLiving;

    public LayerGlowIf(RenderLiving renderLiving, ResourceLocation resourceLocation)
    {
        this.renderLiving = renderLiving;
        TEXTURE = resourceLocation;
    }

    public void doRenderLayer(EntityLiving entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if(entitylivingbaseIn instanceof AbstractTribesmen && ((AbstractTribesmen) entitylivingbaseIn).isFiery()) {
            if (TEXTURE != null) {
                this.renderLiving.bindTexture(TEXTURE);
            }

            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            GlStateManager.disableLighting();
            GlStateManager.depthFunc(514);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 65536.0F, 0.0F);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.renderLiving.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            this.renderLiving.setLightmap(entitylivingbaseIn);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}