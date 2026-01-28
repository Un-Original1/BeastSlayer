package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntitySandy;
import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import com.unoriginal.beastslayer.entity.Model.ModelSuccubus;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderSuccubus extends RenderLiving<EntitySucc> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/succ/succubus.png");
    private static final ResourceLocation GLOW = new ResourceLocation("ancientbeasts:textures/entity/succ/succubus_glow.png");
    public static final Factory FACTORY = new Factory();

    public RenderSuccubus(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSuccubus(), 0.5F);
        this.addLayer(new LayerGlowGeneric(this, GLOW));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySucc entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntitySucc> {

        @Override
        public RenderLiving<? super EntitySucc> createRenderFor(RenderManager manager) {
            return new RenderSuccubus(manager);
        }

    }
}
