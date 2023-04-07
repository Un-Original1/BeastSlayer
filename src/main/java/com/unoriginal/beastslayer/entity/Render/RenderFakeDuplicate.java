package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityFakeDuplicate;
import com.unoriginal.beastslayer.entity.Model.ModelVessel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderFakeDuplicate extends RenderLiving<EntityFakeDuplicate> {
    public static final Factory FACTORY = new Factory();
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/illager/puppet.png");
    public RenderFakeDuplicate(RenderManager manager) {
        super(manager, new ModelVessel(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFakeDuplicate entity) {
        return TEXTURE;
    }
    @Override
    public void doRender(EntityFakeDuplicate entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.1F, 1.1F, 1.1F, 1.00F);
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }
    protected float getDeathMaxRotation(EntityFakeDuplicate entityLivingBaseIn)
    {
        return 0.0F;
    }

    public static class Factory implements IRenderFactory<EntityFakeDuplicate> {

        @Override
        public RenderLiving<? super EntityFakeDuplicate> createRenderFor(RenderManager manager) {
            return new RenderFakeDuplicate(manager);
        }
    }
}
