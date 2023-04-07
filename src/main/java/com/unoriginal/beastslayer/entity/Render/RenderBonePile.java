package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityBonepile;
import com.unoriginal.beastslayer.entity.Model.ModelBonepile;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBonePile extends RenderLiving<EntityBonepile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/bonepile.png");
    public static final Factory FACTORY = new Factory();
    public RenderBonePile(RenderManager manager){
        super(manager, new ModelBonepile(), 0.8F);
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBonepile entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityBonepile> {
        @Override
        public RenderLiving<? super EntityBonepile> createRenderFor(RenderManager manager) {
            return new RenderBonePile(manager);
        }
    }
}
