package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityFrostWalker;
import com.unoriginal.beastslayer.entity.Model.ModelFrostZombie;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class
RenderFrostWalker extends RenderLiving<EntityFrostWalker> {
    private static final ResourceLocation TEXTURE[] = new ResourceLocation[]{ new ResourceLocation("ancientbeasts:textures/entity/frost_walker_b.png"), new ResourceLocation("ancientbeasts:textures/entity/frost_walker_r.png")};
    private static final ResourceLocation EYE = new ResourceLocation("ancientbeasts:textures/entity/frost_walker_eye.png");
    public static final Factory FACTORY = new Factory();

    public RenderFrostWalker(RenderManager manager) {
        super(manager, new ModelFrostZombie(), 0.6F);
        this.addLayer(new LayerGlowGeneric(this, EYE));
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityFrostWalker entity) {
        return TEXTURE[entity.getVariant()];
    }

    public static class Factory implements IRenderFactory<EntityFrostWalker> {

        @Override
        public RenderLiving<? super EntityFrostWalker> createRenderFor(RenderManager manager) {
            return new RenderFrostWalker(manager);
        }
    }
}