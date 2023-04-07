package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityBoulderer;
import com.unoriginal.beastslayer.entity.Model.ModelBoulderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.culling.ICamera;
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
    public boolean shouldRender(EntityBoulderer  livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
        return super.shouldRender(livingEntity, camera, camX, camY, camZ) && !livingEntity.isBuried();
    }

    public void doRender(EntityBoulderer boulderer, double x, double y , double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = boulderer.isBuried() ? 0.0F : 1.0F;
        super.doRender(boulderer, x, y, z, entityYaw, partialTicks);
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
