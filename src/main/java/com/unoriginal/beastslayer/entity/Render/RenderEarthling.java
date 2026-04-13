package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityEarthling;
import com.unoriginal.beastslayer.entity.Model.ModelEarthling;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerEarthling;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEarthling extends RenderLiving<EntityEarthling> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastSlayer.MODID,"textures/entity/earthling.png");
    private static final ResourceLocation SLEEP = new ResourceLocation(BeastSlayer.MODID,"textures/entity/earthling_sleep.png");
    private static final ResourceLocation GLOW = new ResourceLocation(BeastSlayer.MODID,"textures/entity/earthling_glow.png");
    public static final Factory FACTORY = new Factory();

    public RenderEarthling(RenderManager manager){
        super(manager, new ModelEarthling(), 0.6F);
        this.addLayer(new LayerGlowGeneric(this, GLOW));
        this.addLayer(new LayerEarthling(this));
    }

    public ModelEarthling getMainModel(){
        return (ModelEarthling)super.getMainModel();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEarthling entity) {
        return entity.getSleepClient() > 0 ? SLEEP : TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityEarthling> {
        @Override
        public RenderLiving<? super EntityEarthling> createRenderFor(RenderManager manager) {
            return new RenderEarthling(manager);
        }
    }

}
