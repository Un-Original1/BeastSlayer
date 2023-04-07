package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityLilVessel;
import com.unoriginal.beastslayer.entity.Model.ModelLilArmor;
import com.unoriginal.beastslayer.entity.Model.ModelLilVessel;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerVesselHeldItem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderLilVessel extends RenderLiving<EntityLilVessel> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{ new ResourceLocation("ancientbeasts:textures/entity/v_steve.png"), new ResourceLocation("ancientbeasts:textures/entity/v_alex.png")};
    public static final Factory FACTORY = new Factory();
    public RenderLilVessel(RenderManager manager){
        super(manager, new ModelLilVessel(0.0F), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                //this.modelArmor = new ModelBiped(0.5F);
              //  this.modelLeggings = new ModelBiped(0.25F);
                this.modelArmor = new ModelLilArmor(1.0F);
                this.modelLeggings = new ModelLilArmor(0.5F);
            }
        };
        this.addLayer(layerbipedarmor);
        this.addLayer(new LayerVesselHeldItem(this));
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLilVessel entity) {
        return TEXTURE[entity.getVariant()];
    }

    public static class Factory implements IRenderFactory<EntityLilVessel> {
        @Override
        public RenderLiving<? super EntityLilVessel> createRenderFor(RenderManager manager) {
            return new RenderLilVessel(manager);
        }
    }
}
