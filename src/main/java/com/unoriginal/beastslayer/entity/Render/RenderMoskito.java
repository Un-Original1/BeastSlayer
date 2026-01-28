package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityMosquito;
import com.unoriginal.beastslayer.entity.Model.ModelMosquito;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderMoskito extends RenderLiving<EntityMosquito> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/mosquito.png");
    private static final ResourceLocation GLOW = new ResourceLocation("ancientbeasts:textures/entity/mosquito_glow.png");
    public static final Factory FACTORY = new Factory();

    public RenderMoskito(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMosquito(), 0.2F);
        this.addLayer(new LayerGlowGeneric(this, GLOW));
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMosquito entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityMosquito> {

        @Override
        public RenderLiving<? super EntityMosquito> createRenderFor(RenderManager manager) {
            return new RenderMoskito(manager);
        }

    }
}
