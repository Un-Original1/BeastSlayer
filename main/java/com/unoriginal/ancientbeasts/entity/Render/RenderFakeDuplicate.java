package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityFakeDuplicate;
import com.unoriginal.ancientbeasts.entity.Model.ModelVessel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderFakeDuplicate extends RenderLiving<EntityFakeDuplicate> {
    public static final Factory FACTORY = new Factory();
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/illager/puppet_fake.png");
    public RenderFakeDuplicate(RenderManager manager) {
        super(manager, new ModelVessel(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFakeDuplicate entity) {
        return TEXTURE;
    }
    protected float getDeathMaxRotation(EntityFakeDuplicate entityLivingBaseIn)
    {
        return 0.0F;
    }

    public static class Factory implements IRenderFactory<EntityFakeDuplicate> {

        @Override
        public RenderLiving<? super EntityFakeDuplicate> createRenderFor(RenderManager manager) {
            return new RenderFakeDuplicate(manager);
        }
    }
}
