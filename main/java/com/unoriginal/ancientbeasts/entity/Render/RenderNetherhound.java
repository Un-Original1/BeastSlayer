package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityNetherhound;
import com.unoriginal.ancientbeasts.entity.Model.ModelNetherhound;
import com.unoriginal.ancientbeasts.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNetherhound extends RenderLiving<EntityNetherhound> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/netherhound.png");
    private static final ResourceLocation EYES = new ResourceLocation("ancientbeasts:textures/entity/netherhound_fire.png");
    public static final Factory FACTORY = new Factory();

    public RenderNetherhound(RenderManager manager)
    {
        super(manager, new ModelNetherhound(), 0.6F);
        this.addLayer(new LayerGlowGeneric(this, EYES));
    }
    protected ResourceLocation getEntityTexture(EntityNetherhound entity)
    {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityNetherhound> {

        @Override
        public RenderLiving<? super EntityNetherhound> createRenderFor(RenderManager manager) {
            return new RenderNetherhound(manager);
        }

    }
}
