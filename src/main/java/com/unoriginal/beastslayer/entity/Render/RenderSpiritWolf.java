package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityGhost;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntitySpiritWolf;
import com.unoriginal.beastslayer.entity.Model.ModelSpiritWolf;
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
public class RenderSpiritWolf extends RenderLiving<EntitySpiritWolf> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/artifact/wolfy.png");
    private static final ResourceLocation CAMO = new ResourceLocation("ancientbeasts:textures/entity/artifact/wolfy_camo.png");
    public static final Factory FACTORY = new Factory();
    public RenderSpiritWolf(RenderManager manager) {
        super(manager, new ModelSpiritWolf(), 1.2F);
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySpiritWolf entity) {
        return entity.getStalkTicks() > 0 ? CAMO : TEXTURE;
    }

    @Override
    public void doRender(EntitySpiritWolf entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if(entity.getStalkTicks() > 0) {
            int i = (200 - entity.getStalkTicks());
           // float f1 = -0.000138888F * i * i +  0.027777777F * i;
            float f2 = 0.0000013888889F *i*i*i -0.0004166666667F *i*i + 0.0327777777778F* i;
         /*   if(f1 > 0.5F){
                f1 = 0.5F;
            }*/
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2 );
        }
        else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }

    protected float getDeathMaxRotation(EntitySpiritWolf entityLivingBaseIn)
    {
        return 0.0F;
    }

    public static class Factory implements IRenderFactory<EntitySpiritWolf> {

        @Override
        public RenderLiving<? super EntitySpiritWolf> createRenderFor(RenderManager manager) {
            return new RenderSpiritWolf(manager);
        }

    }
}
