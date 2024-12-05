package com.unoriginal.beastslayer.items.models;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFireMask extends ModelBiped {
	private final ModelRenderer mask;
	private final ModelRenderer bigHornLeft;
	private final ModelRenderer smallHornLeft;
	private final ModelRenderer smallHornRight;
	private final ModelRenderer BigHornRight;
//	private final ModelRenderer head;

	public ModelFireMask(float size) {
		super(size, 0, 64,64);
		textureWidth = 64;
		textureHeight = 64;


		textureWidth = 64;
		textureHeight = 64;

		mask = new ModelRenderer(this);
		mask.setRotationPoint(4.0F, 2.0F, -4.0F);
		mask.cubeList.add(new ModelBox(mask, 24, 16, -9.0F, -13.0F, -1.0F, 10, 13, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 28, -9.0F, -13.0F, -1.0F, 10, 13, 2, 0.25F, false));
		mask.cubeList.add(new ModelBox(mask, 40, 55, -9.0F, -10.0F, -1.0F, 10, 7, 2, 0.0F, false));

		bigHornLeft = new ModelRenderer(this);
		bigHornLeft.setRotationPoint(0.0F, -9.0F, -1.0F);
		mask.addChild(bigHornLeft);
		setRotationAngle(bigHornLeft, -0.3927F, -0.7854F, 0.0F);
		bigHornLeft.cubeList.add(new ModelBox(bigHornLeft, 10, 43, -2.0F, -2.0F, -2.0F, 3, 3, 2, 0.0F, true));
		bigHornLeft.cubeList.add(new ModelBox(bigHornLeft, 0, 59, -2.0F, -2.0F, 0.0F, 3, 3, 2, 0.0F, true));
		bigHornLeft.cubeList.add(new ModelBox(bigHornLeft, 40, 41, -2.0F, -7.0F, -4.0F, 3, 8, 2, 0.0F, true));
		bigHornLeft.cubeList.add(new ModelBox(bigHornLeft, 28, 47, -2.0F, -7.0F, -5.0F, 3, 1, 1, 0.0F, true));

		smallHornLeft = new ModelRenderer(this);
		smallHornLeft.setRotationPoint(0.0F, -2.0F, 0.0F);
		mask.addChild(smallHornLeft);
		setRotationAngle(smallHornLeft, 0.3927F, -0.3927F, 0.0F);
		smallHornLeft.cubeList.add(new ModelBox(smallHornLeft, 24, 39, -1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F, true));
		smallHornLeft.cubeList.add(new ModelBox(smallHornLeft, 6, 48, -1.0F, -2.0F, -6.0F, 2, 1, 1, 0.0F, true));

		smallHornRight = new ModelRenderer(this);
		smallHornRight.setRotationPoint(-8.0F, -2.0F, 0.0F);
		mask.addChild(smallHornRight);
		setRotationAngle(smallHornRight, 0.3927F, 0.3927F, 0.0F);
		smallHornRight.cubeList.add(new ModelBox(smallHornRight, 24, 39, -1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F, false));
		smallHornRight.cubeList.add(new ModelBox(smallHornRight, 6, 48, -1.0F, -2.0F, -6.0F, 2, 1, 1, 0.0F, false));

		BigHornRight = new ModelRenderer(this);
		BigHornRight.setRotationPoint(-8.0F, -9.0F, -1.0F);
		mask.addChild(BigHornRight);
		setRotationAngle(BigHornRight, -0.3927F, 0.7854F, 0.0F);
		BigHornRight.cubeList.add(new ModelBox(BigHornRight, 10, 43, -1.0F, -2.0F, -2.0F, 3, 3, 2, 0.0F, false));
		BigHornRight.cubeList.add(new ModelBox(BigHornRight, 0, 59, -1.0F, -2.0F, 0.0F, 3, 3, 2, 0.0F, false));
		BigHornRight.cubeList.add(new ModelBox(BigHornRight, 40, 41, -1.0F, -7.0F, -4.0F, 3, 8, 2, 0.0F, false));
		BigHornRight.cubeList.add(new ModelBox(BigHornRight, 28, 47, -1.0F, -7.0F, -5.0F, 3, 1, 1, 0.0F, false));

		/*head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 24, 0, -4.0F, -32.0F, -4.0F, 8, 8, 8, 0.0F, false));*/

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
		//head.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
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