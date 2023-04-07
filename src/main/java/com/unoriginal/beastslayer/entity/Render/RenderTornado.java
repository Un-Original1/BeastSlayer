package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityTornado;
import com.unoriginal.beastslayer.entity.Model.ModelTornado;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderTornado extends Render<EntityTornado> {
    private static final ResourceLocation TEXTURES = new ResourceLocation("ancientbeasts:textures/entity/tornado.png");
    public static final Factory FACTORY = new Factory();

    protected final ModelTornado tornado = new ModelTornado();

    public RenderTornado(RenderManager manager)
    {
        super(manager);
        this.shadowSize = 0.5F;
    }
    @Override
    public void doRender(@Nonnull EntityTornado entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f  = entity.getAnimationProgress(partialTicks);
        if (f != 0.0F) {
            float f1 = 2.0F;

            if (f > 0.9F) {
                f1 = (float) ((double) f1 * ((1.0D - (double) f) / 0.10000000149011612D));
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            this.bindEntityTexture(entity);
            GlStateManager.translate((float) x, (float) y, (float) z);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, -f1);
            GlStateManager.translate(0.0F, -0.626F, 0.0F);
            this.tornado.render(entity, f, 0.0F, 0.0F, entity.rotationYaw, entity.rotationPitch, 0.03125F);
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
    @Override
    protected ResourceLocation getEntityTexture(EntityTornado entity){
        return TEXTURES;
    }

    public static class Factory implements IRenderFactory<EntityTornado> {

        @Override
        public Render<? super EntityTornado> createRenderFor(RenderManager manager) {
            return new RenderTornado(manager);
        }

    }
}
