package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityRiftedEnderman;
import com.unoriginal.beastslayer.entity.Model.ModelRiftedEnderman;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerGlowGeneric;
import com.unoriginal.beastslayer.entity.Render.Layer.LayerShockwave;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderRiftedEnderman extends RenderLiving<EntityRiftedEnderman> {
    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("ancientbeasts:textures/entity/enderman/rifted_enderman.png");
    private static final ResourceLocation ENDERMAN_HURT = new ResourceLocation("ancientbeasts:textures/entity/enderman/rifted_enderman_b.png");
    private static final ResourceLocation ENDERMAN_NOARMOR = new ResourceLocation("ancientbeasts:textures/entity/enderman/rifted_enderman_a.png");
    private static final ResourceLocation EYES = new ResourceLocation("ancientbeasts:textures/entity/enderman/rifted_enderman_eyes.png");
    private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/enderman/shockwave1.png");
    private final Random rnd = new Random();
    public static final Factory FACTORY = new Factory();

    public RenderRiftedEnderman(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelRiftedEnderman(0.0F), 0.5F);
        this.addLayer(new LayerGlowGeneric(this, EYES));
        this.addLayer(new LayerShockwave(this, BEAM_TEXTURE));
    }

    public ModelRiftedEnderman getMainModel() {
        return (ModelRiftedEnderman) super.getMainModel();
    }

    protected ResourceLocation getEntityTexture(EntityRiftedEnderman entity) {
       if(entity.isArmored()){
           int d = entity.getArmorValue();
           if (d == 2) {
               return ENDERMAN_TEXTURES;
           } else {
               return ENDERMAN_HURT;
           }
       } else {
           return ENDERMAN_NOARMOR;
       }
    }

    public void doRender(EntityRiftedEnderman entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ModelRiftedEnderman modelenderman = this.getMainModel();
        modelenderman.isAttacking = (entity.isScreaming() || entity.isShockwaving());

        if (entity.isScreaming()) {
            x += this.rnd.nextGaussian() * 0.02D;
            z += this.rnd.nextGaussian() * 0.02D;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }

    public static class Factory implements IRenderFactory<EntityRiftedEnderman> {

        @Override
        public RenderLiving<? super EntityRiftedEnderman> createRenderFor(RenderManager manager) {
            return new RenderRiftedEnderman(manager);
        }

    }

}