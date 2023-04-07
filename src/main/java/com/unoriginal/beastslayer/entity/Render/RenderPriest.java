package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.entity.Model.ModelPriest;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowIf;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerTribeItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPriest extends RenderLiving<EntityPriest> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_p.png");
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_p.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_p_e.png"); //+25 saturation
    public static final Factory FACTORY = new Factory();

    public RenderPriest(RenderManager manager) {
        super(manager, new ModelPriest(), 0.8F);
        this.addLayer(new LayerGlowIf(this, OVERLAY));
        this.addLayer(new LayerTribeItem(this));
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375F, 0.0F, 0.0F);
    }


    protected ResourceLocation getEntityTexture(EntityPriest entity) {
        return entity.isFiery() ? FIRE : TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityPriest> {

        @Override
        public RenderLiving<? super EntityPriest> createRenderFor(RenderManager manager) {
            return new RenderPriest(manager);
        }

    }
}