package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityFrostashFox;
import com.unoriginal.ancientbeasts.entity.Model.ModelFrostashFox;
import com.unoriginal.ancientbeasts.entity.Render.Layer.LayerFrostashSleep;
import com.unoriginal.ancientbeasts.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderFrostashFox extends RenderLiving<EntityFrostashFox> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{ new ResourceLocation ("ancientbeasts:textures/entity/frostash_fox/frostash_fox_bl.png"), new ResourceLocation ("ancientbeasts:textures/entity/frostash_fox/frostash_fox_b.png"), new ResourceLocation ("ancientbeasts:textures/entity/frostash_fox/frostash_fox_m.png"), new ResourceLocation ("ancientbeasts:textures/entity/frostash_fox/frostash_fox_r.png"), new ResourceLocation ("ancientbeasts:textures/entity/frostash_fox/frostash_fox_rl.png")};
    public static final Factory FACTORY = new Factory();

    public RenderFrostashFox(RenderManager manager) {
        super(manager, new ModelFrostashFox(), 0.5F);
        this.addLayer(new LayerGlowGeneric(this, null));
        this.addLayer(new LayerFrostashSleep(this)); //note: layer glow generic makes other layers glow as well
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFrostashFox entity) {
        return TEXTURE[entity.getVariant()];
    }

    public static class Factory implements IRenderFactory<EntityFrostashFox> {

        @Override
        public RenderLiving<? super EntityFrostashFox> createRenderFor(RenderManager manager) {
            return new RenderFrostashFox(manager);
        }

    }
}

