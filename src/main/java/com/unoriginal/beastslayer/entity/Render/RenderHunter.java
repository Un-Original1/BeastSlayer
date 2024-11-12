package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Model.ModelHunter;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowIf;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerTribeItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHunter extends RenderLiving<EntityHunter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/tribe/hunter.png");
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_hunter.png");
    private static final ResourceLocation OVERLAY2 = new ResourceLocation("ancientbeasts:textures/entity/tribe/hunter_e.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_hunter_e.png"); //+25 saturation
    private static final ResourceLocation CAMO = new ResourceLocation("ancientbeasts:textures/entity/tribe/hunter_camo.png");
    public static final Factory FACTORY = new Factory();

    public RenderHunter(RenderManager manager) {
        super(manager, new ModelHunter(), 0.8F);
        this.addLayer(new LayerGlowIf(this, OVERLAY, OVERLAY2));
        this.addLayer(new LayerTribeItem(this));
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    @Override
    public void doRender(EntityHunter entity, double x, double y, double z, float f, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if(entity.isCamoClient()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, (MathHelper.sin(entity.getCamoTicks() / 10F) * 0.4F) + 0.4F);
        }
        else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();

    }

    protected ResourceLocation getEntityTexture(EntityHunter entity) {
        if(!entity.isFiery()){
            return entity.isCamoClient() ? CAMO : TEXTURE;
        }
        else {
            return FIRE;
        }
    }

    public static class Factory implements IRenderFactory<EntityHunter> {

        @Override
        public RenderLiving<? super EntityHunter> createRenderFor(RenderManager manager) {
            return new RenderHunter(manager);
        }

    }
}