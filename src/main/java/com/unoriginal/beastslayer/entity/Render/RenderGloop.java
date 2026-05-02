package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityGloop;
import com.unoriginal.beastslayer.entity.Model.ModelGloop;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderGloop extends RenderLiving<EntityGloop> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastSlayer.MODID,"textures/entity/gloop.png");
    public static final Factory FACTORY = new Factory();

    public RenderGloop(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGloop(), 0.4F);
    }

    public static class  Factory implements IRenderFactory<EntityGloop> {

        @Override
        public Render<? super EntityGloop> createRenderFor(RenderManager manager) {
            return new RenderGloop(manager);
        }
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGloop entity) {
        return TEXTURE;
    }
}
