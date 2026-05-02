package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntityEarthling;
import com.unoriginal.beastslayer.entity.Model.ModelEarthling;
import com.unoriginal.beastslayer.entity.Render.RenderEarthling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEarthlingGlow implements LayerRenderer<EntityEarthling> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/flowers.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation("ancientbeasts:textures/entity/earthling_glow.png");
    private final ModelEarthling modelEarthling = new ModelEarthling(0.5F);
    private final RenderEarthling renderEarthling;
    public  LayerEarthlingGlow(RenderEarthling renderEarthling) {
        this.renderEarthling = renderEarthling;
    }

    @Override
    public void doRenderLayer(EntityEarthling entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = entitylivingbaseIn.isInvisible();
        GlStateManager.depthFunc(514);
            if(entitylivingbaseIn.getSleepClient() <= 0) {
                this.renderEarthling.bindTexture(TEXTURE2);
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                GlStateManager.disableLighting();
                GlStateManager.depthFunc(514);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 65536.0F, 0.0F);
                GlStateManager.enableLighting();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                this.renderEarthling.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                this.renderEarthling.setLightmap(entitylivingbaseIn);
                GlStateManager.depthFunc(515);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
            }
        if(entitylivingbaseIn.getPatchClient() > 0) {
            this.renderEarthling.bindTexture(TEXTURE);

            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float) entitylivingbaseIn.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            GlStateManager.color(0.5F, 0.5F, 0.5F, 0.8F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.modelEarthling.setModelAttributes(this.renderEarthling.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.modelEarthling.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();

        }
        GlStateManager.depthFunc(515);
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
