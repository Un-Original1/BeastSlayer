package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityFireElemental;
import com.unoriginal.beastslayer.entity.Model.ModelFireElemental;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireElemental extends RenderLiving<EntityFireElemental> {
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/tribe/fire_elemental.png");
    public static final Factory FACTORY = new Factory();

    public RenderFireElemental(RenderManager manager) {
        super(manager, new ModelFireElemental(), 1.2F);
    }

    protected ResourceLocation getEntityTexture(EntityFireElemental entity) {

        return FIRE;
    }

    public static class Factory implements IRenderFactory<EntityFireElemental> {

        @Override
        public RenderLiving<? super EntityFireElemental> createRenderFor(RenderManager manager) {
            return new RenderFireElemental(manager);
        }

    }
}
