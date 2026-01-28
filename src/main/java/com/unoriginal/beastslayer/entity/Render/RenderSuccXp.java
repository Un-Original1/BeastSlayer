package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import com.unoriginal.beastslayer.entity.Entities.EntitySuccXp;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSuccXp extends Render<EntitySuccXp> {
    private static final ResourceLocation EXPERIENCE_ORB_TEXTURES = new ResourceLocation("textures/entity/experience_orb.png");
    public static final Factory FACTORY = new Factory();

    public RenderSuccXp(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void doRender(EntitySuccXp entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (!this.renderOutlines)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y, (float)z);
            this.bindEntityTexture(entity);
            RenderHelper.enableStandardItemLighting();
            int i = entity.getTextureByXP();
            float f = (float)(i % 4 * 16 + 0) / 64.0F;
            float f1 = (float)(i % 4 * 16 + 16) / 64.0F;
            float f2 = (float)(i / 4 * 16 + 0) / 64.0F;
            float f3 = (float)(i / 4 * 16 + 16) / 64.0F;
            float f4 = 1.0F;
            float f5 = 0.5F;
            float f6 = 0.25F;
            int j = entity.getBrightnessForRender();
            int k = j % 65536;
            int l = j / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k, (float)l);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f8 = 255.0F;
            float f9 = ((float)entity.xpColor + partialTicks) / 2.0F;
            l = (int)((MathHelper.sin(f9 ) + 1.0F) * 0.5F * 75.0F + 180);
            int i1 = 255;
            int j1 = (int)((MathHelper.sin(f9 + 4.1887903F) + 1.0F) * 0.1F * 255.0F) + 200;
            GlStateManager.translate(0.0F, 0.1F, 0.0F);
            GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            float f7 = 0.3F;
            GlStateManager.scale(0.3F, 0.3F, 0.3F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).color(l, 0, j1, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).color(l, 0, j1, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).color(l, 0, j1, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
            bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).color(l, 0, j1, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
            GlStateManager.disableBlend();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    protected ResourceLocation getEntityTexture(EntitySuccXp entity)
    {
        return EXPERIENCE_ORB_TEXTURES;
    }

    public static class Factory implements IRenderFactory<EntitySuccXp>
    {
        @Override
        public Render<? super EntitySuccXp> createRenderFor(RenderManager manager){
            return new  RenderSuccXp(manager);
        }
    }
}
