package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntitySandy;
import com.unoriginal.ancientbeasts.entity.Model.ModelSandy;
import com.unoriginal.ancientbeasts.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSandy extends RenderLiving<EntitySandy>{
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/sandmonster.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/sandmonster_angry.png");
    private static final ResourceLocation EYES = new ResourceLocation("ancientbeasts:textures/entity/sandyeyes.png");
    public static final Factory FACTORY = new Factory();

    public RenderSandy(RenderManager manager)
    {
        super(manager, new ModelSandy(), 1.0F);
        this.addLayer(new LayerGlowGeneric(this, EYES));
    }
    protected ResourceLocation getEntityTexture(EntitySandy entity)
    {
        return entity.isAngry() ? ANGRY_TEXTURE : TEXTURE;
    }
    public void doRender(EntitySandy sandy, double x, double y ,double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = sandy.isBuried() ? 0.0F : 1.0F;
        super.doRender(sandy, x, y, z, entityYaw, partialTicks);
    }
    public static class Factory implements IRenderFactory<EntitySandy> {

        @Override
        public RenderLiving<? super EntitySandy> createRenderFor(RenderManager manager) {
            return new RenderSandy(manager);
        }

    }
}
