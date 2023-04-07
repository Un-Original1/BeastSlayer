package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityGiant;
import com.unoriginal.beastslayer.entity.Model.ModelGiant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderGiant extends RenderLiving<EntityGiant> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/biggie.png");
    public static final Factory FACTORY = new Factory();
    public RenderGiant(RenderManager manager){
        super(manager, new ModelGiant(), 2.0F);
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGiant entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityGiant> {
        @Override
        public RenderLiving<? super EntityGiant> createRenderFor(RenderManager manager) {
            return new RenderGiant(manager);
        }
    }
    protected void preRenderCallback(EntityGiant entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1.25F, 1.25F, 1.25F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }
}
