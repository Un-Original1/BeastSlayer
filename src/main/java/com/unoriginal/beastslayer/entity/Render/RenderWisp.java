package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityWisp;
import com.unoriginal.beastslayer.entity.Model.ModelWisp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderWisp extends RenderLiving<EntityWisp> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{ new ResourceLocation ("ancientbeasts:textures/entity/tribe/wisp_speed.png"), new ResourceLocation("ancientbeasts:textures/entity/tribe/wisp_strength.png"), new ResourceLocation("ancientbeasts:textures/entity/tribe/wisp_purity.png"), new ResourceLocation("ancientbeasts:textures/entity/tribe/wisp_evil.png")};
    public static final Factory FACTORY = new Factory();

    public RenderWisp(RenderManager manager) {
        super(manager, new ModelWisp(), 0.3F);
    }
    @Override
    public void doRender(EntityWisp entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.85F);
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }
    protected void applyRotations(EntityWisp entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        float f = entityLiving.prevSquidPitch + (entityLiving.squidPitch - entityLiving.prevSquidPitch) * partialTicks;
        float f1 = entityLiving.prevSquidYaw + (entityLiving.squidYaw - entityLiving.prevSquidYaw) * partialTicks;
        GlStateManager.translate(0.0F, 0.15F, 0.0F);
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
       // GlStateManager.translate(0.0F, -1.2F, 0.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWisp entity) {
        return TEXTURE[entity.getVariant()];
    }

    public static class Factory implements IRenderFactory<EntityWisp> {

        @Override
        public RenderLiving<? super EntityWisp> createRenderFor(RenderManager manager) {
            return new RenderWisp(manager);
        }

    }
}
