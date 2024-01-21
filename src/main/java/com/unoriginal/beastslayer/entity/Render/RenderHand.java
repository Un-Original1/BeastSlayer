package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityHand;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Model.ModelHand;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGlint;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderHand extends RenderLiving<EntityHand> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/artifact/hand.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("ancientbeasts:textures/entity/artifact/hand_glint.png");
    public static final Factory FACTORY = new Factory();

    public RenderHand(RenderManager manager) {
        super(manager, new ModelHand(), 0.8F);
        this.addLayer(new LayerGlowGlint(this, OVERLAY));
    }

    @Override
    public void doRender(EntityHand entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        float alpha = -0.0005263157895F * entity.ticksExisted * entity.ticksExisted + 0.1052631578947F * entity.ticksExisted;
        if(alpha > 1.0F){
            alpha = 1.0F;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHand entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(EntityHand entityHand, float partialTickTime)
    {
        GlStateManager.scale(1.25F, 1.25F, 1.25F);
    }

    public static class Factory implements IRenderFactory<EntityHand> {

        @Override
        public RenderLiving<? super EntityHand> createRenderFor(RenderManager manager) {
            return new RenderHand(manager);
        }

    }
}
