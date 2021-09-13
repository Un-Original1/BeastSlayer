package com.unoriginal.ancientbeasts.entity.Render.layer;

import com.unoriginal.ancientbeasts.entity.Entities.EntitySandy;
import com.unoriginal.ancientbeasts.entity.Render.RenderSandy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSandyEyes implements LayerRenderer<EntitySandy>
{
    private static final ResourceLocation EYES = new ResourceLocation("ancientbeasts:textures/entity/sandyeyes.png");
    private final RenderSandy sandyRenderer;

    public LayerSandyEyes(RenderSandy sandyRendererIn)
    {
        this.sandyRenderer = sandyRendererIn;
    }

    public void doRenderLayer(EntitySandy entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.sandyRenderer.bindTexture(EYES);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthFunc(514);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 65536.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        this.sandyRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        this.sandyRenderer.setLightmap(entitylivingbaseIn);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
