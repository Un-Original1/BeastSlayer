package com.unoriginal.beastslayer.entity.Render;


import com.unoriginal.beastslayer.entity.Entities.EntityGhostHolder;
import com.unoriginal.beastslayer.entity.Model.ModelGhost;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderXpGhost extends RenderLiving<EntityGhostHolder> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/ghost_xp.png");
    public static final Factory FACTORY = new Factory();
    public RenderXpGhost(RenderManager manager){
        super(manager, new ModelGhost(), 0.0F);
    }
    @Override
    public void doRender(EntityGhostHolder entity, double x, double y, double z, float f, float partialTicks) {
        int i = entity.ticksExisted;
        float alpha = MathHelper.cos((float) i * (float)i * 0.000125F) * 0.375F + 0.375F;
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, i > 600 ? alpha : 0.75F);
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }
    protected float getDeathMaxRotation(EntityGhostHolder entityLivingBaseIn)
    {
        return 0.0F;
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGhostHolder entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityGhostHolder> {
        @Override
        public RenderLiving<? super EntityGhostHolder> createRenderFor(RenderManager manager) {
            return new RenderXpGhost(manager);
        }
    }
}
