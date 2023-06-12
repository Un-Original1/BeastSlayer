package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityRiftedEnderman;
import com.unoriginal.beastslayer.entity.Model.ModelShockwave;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
    public class LayerShockwave implements LayerRenderer<EntityRiftedEnderman> {
        private final ResourceLocation TEXTURE;
        private final RenderLiving<EntityRiftedEnderman> renderLiving;
        private final ModelShockwave MODEL = new ModelShockwave();

        public LayerShockwave(RenderLiving<EntityRiftedEnderman> renderLiving, ResourceLocation resourceLocation)
        {
            this.renderLiving = renderLiving;
            TEXTURE = resourceLocation;
        }

        public void doRenderLayer(EntityRiftedEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {

            if(entitylivingbaseIn.getShockwaveTicks() > 0) {
                GlStateManager.disableLighting();
                GlStateManager.pushMatrix();
                if (TEXTURE != null) {
                    this.renderLiving.bindTexture(TEXTURE);
                }
                float f = 20 - entitylivingbaseIn.getShockwaveTicks();
                f = 1.0F - (f - partialTicks) / 20F;

                float f1 = 2.0F;

                if (f > 0.9F)
                {
                    f1 = (float)((double)f1 * ((1.0D - (double)f) / 0.10000000149011612D));
                }

                GlStateManager.translate(0.0F, 0.8F, 0.0F);
                GlStateManager.scale(-f1, -f1, f1);



             /*   GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                GlStateManager.disableLighting();
                GlStateManager.depthFunc(514);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

                GlStateManager.enableLighting();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);*/

                this.MODEL.setModelAttributes(this.renderLiving.getMainModel());
                this.MODEL.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                this.MODEL.render(entitylivingbaseIn, 0F, 0F, f, netHeadYaw, headPitch, 0.03125F);
/*
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                this.renderLiving.setLightmap(entitylivingbaseIn);
                GlStateManager.depthFunc(515);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();*/

                GlStateManager.popMatrix();
                GlStateManager.enableLighting();

            }
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
    }

