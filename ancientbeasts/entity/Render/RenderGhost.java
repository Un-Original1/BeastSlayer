package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityGhost;
import com.unoriginal.ancientbeasts.entity.Model.ModelGhost;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderGhost extends RenderLiving<EntityGhost> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/ghost.png");
    public static final Factory FACTORY = new Factory();
    public RenderGhost(RenderManager manager){
        super(manager, new ModelGhost(), 0.0F);
    }
    @Override
    public void doRender(EntityGhost entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }
    protected float getDeathMaxRotation(EntityGhost entityLivingBaseIn)
    {
        return 0.0F;
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGhost entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityGhost> {
        @Override
        public RenderLiving<? super EntityGhost> createRenderFor(RenderManager manager) {
            return new RenderGhost(manager);
        }
    }
}
