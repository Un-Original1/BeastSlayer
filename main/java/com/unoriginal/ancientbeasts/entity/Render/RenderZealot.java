package com.unoriginal.ancientbeasts.entity.Render;

import com.unoriginal.ancientbeasts.entity.Entities.EntityZealot;
import com.unoriginal.ancientbeasts.entity.Model.ModelZealot;
import com.unoriginal.ancientbeasts.entity.Render.Layer.LayerGlowGeneric;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderZealot extends RenderLiving<EntityZealot> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/illager/zealot.png");
    private static final ResourceLocation FIRE = new ResourceLocation("ancientbeasts:textures/entity/illager/zfire.png");
    public static final Factory FACTORY = new Factory();
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityZealot entity) {
        return TEXTURE;
    }
    public RenderZealot(RenderManager manager)
    {
        super(manager, new ModelZealot(), 0.5F);
        this.addLayer(new LayerGlowGeneric(this, FIRE));
        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                if (((EntitySpellcasterIllager)entitylivingbaseIn).isSpellcasting())
                {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelIllager)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    protected void preRenderCallback(EntityZealot zealot, float partialTickTime)
    {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
    public static class Factory implements IRenderFactory<EntityZealot> {
        @Override
        public RenderLiving<? super EntityZealot> createRenderFor(RenderManager manager) {
            return new RenderZealot(manager);
        }
    }
}
