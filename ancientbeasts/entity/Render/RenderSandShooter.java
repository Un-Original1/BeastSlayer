package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntitySandShooter;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;


@SideOnly(Side.CLIENT)
public class RenderSandShooter extends Render<EntitySandShooter>
{
    public RenderSandShooter(RenderManager manager)
    {
        super(manager);
    }
    public static final RenderSandShooter.Factory FACTORY = new RenderSandShooter.Factory();
    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(EntitySandShooter entity) {
        return null;
    }
    public static class Factory implements IRenderFactory<EntitySandShooter> {

        @Override
        public Render<? super EntitySandShooter> createRenderFor(RenderManager manager) {
            return new RenderSandShooter(manager);
        }

    }
}
