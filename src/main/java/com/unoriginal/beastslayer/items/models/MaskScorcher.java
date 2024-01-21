package com.unoriginal.beastslayer.items.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MaskScorcher extends ModelBiped {
    private final ModelRenderer mask;

    public MaskScorcher(float size) {
        super(size, 0, 64,64);
        textureWidth = 64;
        textureHeight = 64;

        mask = new ModelRenderer(this);
        mask.setRotationPoint(0.0F, -4.0F, -2.0F);
        mask.cubeList.add(new ModelBox(mask, 40, 47, -5.0F, -10.01F, -3.01F, 10, 15, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 38, 35, -11.0F, 0.0F, -3.0F, 6, 3, 2, 0.0F, true));
        mask.cubeList.add(new ModelBox(mask, 52, 41, -7.0F, -10.01F, -3.0F, 2, 3, 2, 0.0F, true));
        mask.cubeList.add(new ModelBox(mask, 52, 41, 5.0F, -10.01F, -3.01F, 2, 3, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 38, 35, 5.0F, 0.0F, -3.0F, 6, 3, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 57, 28, -11.0F, -1.0F, -3.0F, 1, 1, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 57, 28, 10.0F, -1.0F, -3.0F, 1, 1, 2, 0.0F, true));
        mask.cubeList.add(new ModelBox(mask, 57, 28, 6.0F, -11.01F, -3.01F, 1, 1, 2, 0.0F, true));
        mask.cubeList.add(new ModelBox(mask, 57, 28, -7.0F, -11.01F, -3.0F, 1, 1, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 55, 32, -1.0F, -10.01F, -3.01F, 2, 6, 2, 0.0F, false));
        mask.cubeList.add(new ModelBox(mask, 1, 48, -4.0F, -4.0F, -1.0F, 8, 8, 7, 0.25F, false));
        this.bipedHead.addChild(mask);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        this.setRotationAngles(f, f1, f2, f3, f4, scale, entity);
        GlStateManager.pushMatrix();
        if(this.isChild) {
            GlStateManager.scale(1F, 1F, 1F);
            GlStateManager.translate(0.0F, 14.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            this.bipedHeadwear.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 28.0F * scale, 0.0F);
        }
        else
        {
            if (entity.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedHead.render(scale);
            this.bipedHeadwear.render(scale);

        }
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);

        GlStateManager.popMatrix();
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        if (entityIn instanceof EntityArmorStand) {
            EntityArmorStand entityarmorstand = (EntityArmorStand) entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
        else {
            super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        }
    }
}
