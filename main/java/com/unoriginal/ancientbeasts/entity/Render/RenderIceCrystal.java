package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityIceCrystal;
import com.unoriginal.ancientbeasts.entity.Model.ModelIceCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceCrystal extends Render<EntityIceCrystal> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/ice_crystal_b.png");
    private static final ResourceLocation TEXTURE_RED = new ResourceLocation("ancientbeasts:textures/entity/ice_crystal_r.png");
    private final ModelIceCrystal model = new ModelIceCrystal();
    public static final Factory FACTORY = new Factory();

    public RenderIceCrystal(RenderManager manager)
    {
        super(manager);
    }

    public void doRender(EntityIceCrystal entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f = entity.getAnimationProgress(partialTicks);

        if (f != 0.0F)
        {
            float f1 = 2.0F;

            if (f > 0.9F)
            {
                f1 = (float)((double)f1 * ((1.0D - (double)f) / 0.10000000149011612D));
            }

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            this.bindEntityTexture(entity);
            GlStateManager.translate((float)x, (float)y, (float)z);
            GlStateManager.rotate(90.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            GlStateManager.translate(0.0F, -0.626F, 0.0F);
            this.model.render(entity, f, 0.0F, 0.0F, entity.rotationYaw, entity.rotationPitch, 0.03125F);
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    protected ResourceLocation getEntityTexture(EntityIceCrystal entity)
    {
        return entity.isRed()? TEXTURE_RED : TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityIceCrystal> {

        @Override
        public Render<? super EntityIceCrystal> createRenderFor(RenderManager manager) {
            return new RenderIceCrystal(manager);
        }

    }
}
