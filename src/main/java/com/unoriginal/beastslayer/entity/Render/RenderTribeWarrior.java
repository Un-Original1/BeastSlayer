package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import com.unoriginal.beastslayer.entity.Model.ModelTribeWarrior;
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
public class RenderTribeWarrior extends RenderLiving<EntityTribeWarrior> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/tribe/marauder.png");
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_marauder.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_marauder_e.png");
    private static final ResourceLocation OVERLAY2 = new ResourceLocation("ancientbeasts:textures/entity/tribe/marauder_e.png");
    public static final Factory FACTORY = new Factory();

    public RenderTribeWarrior(RenderManager manager)
    {
        super(manager, new ModelTribeWarrior(), 0.8F);
        this.addLayer(new LayerGlowIf(this, OVERLAY, OVERLAY2));
        this.addLayer(new LayerTribeItem(this));
    }
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
    protected ResourceLocation getEntityTexture(EntityTribeWarrior entity)
    {
        return entity.isFiery() ? FIRE : TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityTribeWarrior> {

        @Override
        public RenderLiving<? super EntityTribeWarrior> createRenderFor(RenderManager manager) {
            return new RenderTribeWarrior(manager);
        }

    }
}
