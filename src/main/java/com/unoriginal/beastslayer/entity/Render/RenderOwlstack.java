package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityOwlstack;
import com.unoriginal.beastslayer.entity.Model.ModelOwlstack;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOwlstack extends RenderLiving<EntityOwlstack> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/owlstack.png");
    public static final Factory FACTORY = new Factory();

    public RenderOwlstack(RenderManager manager)
    {
        super(manager, new ModelOwlstack(), 0.6F);
    }
    protected ResourceLocation getEntityTexture(EntityOwlstack entity)
    {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityOwlstack> {

        @Override
        public RenderLiving<? super EntityOwlstack> createRenderFor(RenderManager manager) {
            return new RenderOwlstack(manager);
        }

    }
}
