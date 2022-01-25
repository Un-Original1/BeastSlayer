package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityBoulderer;
import com.unoriginal.ancientbeasts.entity.Model.ModelBoulderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBoulderer extends RenderBiped<EntityBoulderer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/bouldering_zombie.png");
    public static final Factory FACTORY = new Factory();
    public RenderBoulderer(RenderManager manager)
    {
        super(manager, new ModelBoulderer(), 0.8F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBoulderer entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityBoulderer> {
        @Override
        public RenderBiped<? super EntityBoulderer> createRenderFor(RenderManager manager) {
            return new RenderBoulderer(manager);
        }
    }
}
