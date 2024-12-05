package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntityFrostashFox;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeChild;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LayerTribeChildEye implements LayerRenderer<EntityLiving> {
    private final RenderLiving renderLiving;
    private static final ResourceLocation[] OVERLAY = new ResourceLocation[]{
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_1eyes.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_2eyes.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_3eyes.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_4eyes.png")
            )};

    public LayerTribeChildEye(RenderLiving renderLiving)
    {
        this.renderLiving = renderLiving;
    }

    public void doRenderLayer(EntityLiving entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
       if(entitylivingbaseIn instanceof EntityTribeChild){
           EntityTribeChild child = (EntityTribeChild) entitylivingbaseIn;
           this.renderLiving.bindTexture(OVERLAY[child.getVariant()]);
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

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
