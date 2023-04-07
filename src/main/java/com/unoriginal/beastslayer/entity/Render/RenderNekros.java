package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityNekros;
import com.unoriginal.beastslayer.entity.Model.ModelNekros;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderNekros extends RenderLiving<EntityNekros> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/nekros.png");
    public static final Factory FACTORY = new Factory();
    public RenderNekros(RenderManager manager){
        super(manager, new ModelNekros(), 0.0F);
    }
    @Override
    public void doRender(EntityNekros entity, double x, double y, double z, float f, float partialTicks) {
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
    protected float getDeathMaxRotation(EntityNekros entityLivingBaseIn)
    {
        return 0.0F;
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityNekros entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityNekros> {
        @Override
        public RenderLiving<? super EntityNekros> createRenderFor(RenderManager manager) {
            return new RenderNekros(manager);
        }
    }
}
