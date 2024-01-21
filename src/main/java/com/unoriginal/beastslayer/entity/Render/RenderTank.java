package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Model.ModelTank;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowIf;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerTribeItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTank extends RenderLiving<EntityTank> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/tribe/scorcher.png");
    private static final ResourceLocation OVERLAY2 = new ResourceLocation("ancientbeasts:textures/entity/tribe/scorcher_e.png");
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_scorcher.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_scorcher_e.png");
    public static final Factory FACTORY = new Factory();
    public RenderTank(RenderManager manager) {
        super(manager, new ModelTank(), 1.2F);
        this.addLayer(new LayerGlowIf(this, OVERLAY, OVERLAY2));
        this.addLayer(new LayerTribeItem(this));
    }
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTank entity) {
        return entity.isFiery() ? FIRE : TEXTURE;
    }
    protected void preRenderCallback(EntityTank entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
    }
    public static class Factory implements IRenderFactory<EntityTank> {

        @Override
        public RenderLiving<? super EntityTank> createRenderFor(RenderManager manager) {
            return new RenderTank(manager);
        }

    }
}
