package com.unoriginal.beastslayer.items.models;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MaskMarauder extends ModelBiped {
	private final ModelRenderer mask2;
	private final ModelRenderer horn1;
	private final ModelRenderer horn0;

	public MaskMarauder(float modelSize) {
		super(modelSize, 0, 64,64);
		textureWidth = 64;
		textureHeight = 64;

		mask2 = new ModelRenderer(this);
		mask2.setRotationPoint(0.0F, -4.0F, -3.0F);
		mask2.cubeList.add(new ModelBox(mask2, 1, 48, -4.0F, -4.0F, 0.0F, 8, 8, 7, 0.25F, false));
		mask2.cubeList.add(new ModelBox(mask2, 34, 52, 8.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask2.cubeList.add(new ModelBox(mask2, 34, 52, -9.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask2.cubeList.add(new ModelBox(mask2, 51, 57, -9.0F, 0.0F, -2.0F, 4, 3, 2, 0.0F, true));
		mask2.cubeList.add(new ModelBox(mask2, 51, 57, 5.0F, 0.0F, -2.0F, 4, 3, 2, 0.0F, false));
		mask2.cubeList.add(new ModelBox(mask2, 40, 44, -5.0F, -5.0F, -2.001F, 10, 10, 2, 0.0F, false));

		horn1 = new ModelRenderer(this);
		horn1.setRotationPoint(3.5F, -4.0F, -4.5F);
		mask2.addChild(horn1);

		horn1.cubeList.add(new ModelBox(horn1, 53, 30, -0.5F, -7.0F, -1.5F, 2, 10, 3, 0.0F, false));
		horn1.cubeList.add(new ModelBox(horn1, 41, 62, -0.5F, -7.0F, 1.5F, 2, 1, 1, 0.0F, true));
		horn1.cubeList.add(new ModelBox(horn1, 41, 57, -0.5F, -1.0F, 1.5F, 2, 4, 1, 0.0F, true));

		horn0 = new ModelRenderer(this);
		horn0.setRotationPoint(-11.5F, -2.0F, -4.5F);
		mask2.addChild(horn0);
		horn0.cubeList.add(new ModelBox(horn0, 53, 30, 6.5F, -9.0F, -1.5F, 2, 10, 3, 0.0F, true));
		horn0.cubeList.add(new ModelBox(horn0, 41, 57, 6.5F, -3.0F, 1.5F, 2, 4, 1, 0.0F, false));
		horn0.cubeList.add(new ModelBox(horn0, 41, 62, 6.5F, -9.0F, 1.5F, 2, 1, 1, 0.0F, false));
		this.bipedHead.addChild(mask2);
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