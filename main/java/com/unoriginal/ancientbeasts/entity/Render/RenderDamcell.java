package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityDamcell;
import com.unoriginal.ancientbeasts.entity.Model.ModelDamcell;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderDamcell extends RenderLiving<EntityDamcell> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/damcell.png");
    private static final ResourceLocation SCREAM = new ResourceLocation("ancientbeasts:textures/entity/damcell_scream.png");
    public static final Factory FACTORY = new Factory();
    private final Random rnd = new Random();

    public RenderDamcell(RenderManager manager){
        super(manager, new ModelDamcell(), 0.8F);
    }
    protected ResourceLocation getEntityTexture(EntityDamcell entity)
    {
        return (entity.getSuckTimer() > 0 || entity.getShootTick() > 0) ? SCREAM : TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityDamcell> {

        @Override
        public RenderLiving<? super EntityDamcell> createRenderFor(RenderManager manager) {
            return new RenderDamcell(manager);
        }

    }

    public void doRender(EntityDamcell entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

        if ((entity.getSuckTimer() > 0 || entity.getShootTick() > 0) && !entity.isBeingRidden())
        {
            x += this.rnd.nextGaussian() * 0.02D;
            z += this.rnd.nextGaussian() * 0.02D;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
