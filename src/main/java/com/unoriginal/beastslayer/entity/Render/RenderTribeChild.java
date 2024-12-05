package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityTribeChild;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import com.unoriginal.beastslayer.entity.Model.ModelTribeChild;
import com.unoriginal.beastslayer.entity.Model.ModelTribeWarrior;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowIf;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerTribeChildEye;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerTribeItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTribeChild extends RenderLiving<EntityTribeChild> {
    private static final ResourceLocation[]  TEXTURE = new ResourceLocation[] {
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_1.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_2.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_3.png")),
            (new ResourceLocation("ancientbeasts:textures/entity/tribe/tribe_child_4.png")
            )};
   // private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_marauder.png");
    private static final ResourceLocation OVERLAY2 = new ResourceLocation("ancientbeasts:textures/entity/tribe/marauder_e.png");
    public static final Factory FACTORY = new Factory();

    public RenderTribeChild(RenderManager manager)
    {
        super(manager, new ModelTribeChild(), 0.5F);
        this.addLayer(new LayerTribeChildEye(this));
        //this.addLayer(new LayerTribeItem(this));
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
    protected ResourceLocation getEntityTexture(EntityTribeChild entity)
    {
        return TEXTURE[entity.getVariant()];
    }

    public static class Factory implements IRenderFactory<EntityTribeChild> {

        @Override
        public RenderLiving<? super EntityTribeChild> createRenderFor(RenderManager manager) {
            return new RenderTribeChild(manager);
        }

    }

}
