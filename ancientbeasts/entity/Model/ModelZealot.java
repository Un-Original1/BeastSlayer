package com.unoriginal.ancientbeasts.entity.Model;
// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelZealot extends ModelBase {
	private final ModelRenderer arms;
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer armLeft;
	private final ModelRenderer armRight;
	private final ModelRenderer legRight;
	private final ModelRenderer legLeft;
	private final ModelRenderer fire;

	public ModelZealot() {
		textureWidth = 64;
		textureHeight = 64;

		arms = new ModelRenderer(this);
		arms.setRotationPoint(0.0F, 2.0F, 0.0F);
		arms.cubeList.add(new ModelBox(arms, 44, 22, 4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, true));
		arms.cubeList.add(new ModelBox(arms, 44, 22, -8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, false));
		arms.cubeList.add(new ModelBox(arms, 40, 38, -4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 32, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.26F, false));
		head.cubeList.add(new ModelBox(head, 24, 0, -1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false));

		fire = new ModelRenderer(this);
		fire.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(fire);
		fire.cubeList.add(new ModelBox(head, 28, 50, 0.0F, -14.0F, -7.0F, 0, 8, 6, 0.0F, false));
		fire.cubeList.add(new ModelBox(head, 28, 56, -3.0F, -14.0F, -4.1F, 6, 8, 0, 0.0F, true));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 38, -4.0F, 0.0F, -3.0F, 8, 20, 6, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 16, 20, -4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F, false));

		armLeft = new ModelRenderer(this);
		armLeft.setRotationPoint(4.0F, 2.0F, 0.0F);
		armLeft.cubeList.add(new ModelBox(armLeft, 40, 46, 0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, true));

		armRight = new ModelRenderer(this);
		armRight.setRotationPoint(-4.0F, 2.0F, 0.0F);
		armRight.cubeList.add(new ModelBox(armRight, 40, 46, -4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));

		legRight = new ModelRenderer(this);
		legRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
		legRight.cubeList.add(new ModelBox(legRight, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		legLeft = new ModelRenderer(this);
		legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
		legLeft.cubeList.add(new ModelBox(legLeft, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		legRight.render(f5);
		legLeft.render(f5);
		AbstractIllager abstractillager = (AbstractIllager)entity;

		if (abstractillager.getArmPose() == AbstractIllager.IllagerArmPose.CROSSED)
		{
			this.arms.render(f5);
		}
		else
		{
			this.armRight.render(f5);
			this.armLeft.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.arms.rotationPointY = 3.0F;
		this.arms.rotationPointZ = -1.0F;
		this.arms.rotateAngleX = -0.75F;
		this.fire.rotationPointY= MathHelper.cos(ageInTicks * 0.4F * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(- 1));
		this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.legLeft.rotateAngleY = 0.0F;
		this.legRight.rotateAngleY = 0.0F;
		AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = ((AbstractIllager)entityIn).getArmPose();
		 if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING)
		{
			this.armRight.rotationPointZ = 0.0F;
			this.armRight.rotationPointX = -5.0F;
			this.armLeft.rotationPointZ = 0.0F;
			this.armLeft.rotationPointX = 5.0F;
			this.armRight.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
			this.armLeft.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
			this.armRight.rotateAngleZ = 2.3561945F;
			this.armLeft.rotateAngleZ = -2.3561945F;
			this.armRight.rotateAngleY = 0.0F;
			this.armLeft.rotateAngleY = 0.0F;
		}
	}
}