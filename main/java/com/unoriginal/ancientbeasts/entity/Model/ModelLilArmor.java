package com.unoriginal.ancientbeasts.entity.Model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLilArmor extends ModelBiped {
    public ModelLilArmor(float modelSize)
    {
        super(modelSize, 0, 64, 32);
    }
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1F, 1F, 1F);
        GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
        this.bipedHead.render(scale);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
        this.bipedHeadwear.render(scale);
        GlStateManager.popMatrix();
    }
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.bipedHead.rotationPointY = -2F;
        this.bipedBody.rotationPointY = 4F;
        this.bipedLeftArm.rotationPointY = 6F;
        this.bipedRightArm.rotationPointY = 6F;
        this.bipedLeftLeg.rotationPointY = 13F;
        this.bipedRightLeg.rotationPointY = 13F;
    }
}
