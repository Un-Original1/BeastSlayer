package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTribeChild extends ModelBase {
	private final ModelRenderer leg_right;
	private final ModelRenderer leg_right2;
	private final ModelRenderer leg_left;
	private final ModelRenderer leg_left2;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer bone;
	private final ModelRenderer arm_right;
	private final ModelRenderer arm_left;
	private final ModelRenderer tail;

	public ModelTribeChild() {
		textureWidth = 64;
		textureHeight = 64;

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(2.0F, 19.0F, 0.0F);
		leg_right.cubeList.add(new ModelBox(leg_right, 0, 28, -0.5F, -1.0F, -2.0F, 2, 4, 4, 0.05F, true));

		leg_right2 = new ModelRenderer(this);
		leg_right2.setRotationPoint(0.5F, 2.0F, 2.0F);
		leg_right.addChild(leg_right2);
		setRotationAngle(leg_right2, 0.0436F, 0.0F, 0.0F);
		leg_right2.cubeList.add(new ModelBox(leg_right2, 32, 8, -1.0F, -1.0F, 0.0F, 2, 4, 2, 0.0F, true));

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(-2.0F, 19.0F, 0.0F);
		leg_left.cubeList.add(new ModelBox(leg_left, 0, 28, -1.5F, -1.0F, -2.0F, 2, 4, 4, 0.05F, false));

		leg_left2 = new ModelRenderer(this);
		leg_left2.setRotationPoint(-0.5F, 2.0F, 2.0F);
		leg_left.addChild(leg_left2);
		leg_left2.cubeList.add(new ModelBox(leg_left2, 32, 8, -1.0F, -1.0F, 0.0F, 2, 4, 2, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 19.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 27, -2.5F, -6.0F, -1.0F, 5, 7, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 19, 16, -2.5F, -6.0F, -1.0F, 5, 9, 2, 0.25F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -6.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 34, 15, -4.0F, -11.0F, -4.0F, 2, 3, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 34, 15, 2.0F, -11.0F, -4.0F, 2, 3, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 24, 0, 2.0F, -12.0F, -6.0F, 2, 6, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 24, 0, -4.0F, -12.0F, -6.0F, 2, 6, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 1, -4.0F, -12.0F, -4.0F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 1, 2.0F, -12.0F, -4.0F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 4, -7.0F, -4.0F, -3.0F, 3, 2, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 32, 4, 4.0F, -4.0F, -3.0F, 3, 2, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 39, 0, -7.0F, -5.0F, -3.0F, 1, 1, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 39, 0, 6.0F, -5.0F, -3.0F, 1, 1, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 0, 37, -2.0F, -8.0F, -5.0F, 4, 3, 4, 0.25F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -5.0F, 4.0F);
		head.addChild(bone);
		setRotationAngle(bone, 0.2182F, 0.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 1, 47, -2.0F, -1.0F, 0.0F, 4, 5, 3, 0.0F, false));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(2.5F, -5.0F, 0.0F);
		body.addChild(arm_right);
		arm_right.cubeList.add(new ModelBox(arm_right, 0, 0, 0.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F, true));

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(-2.5F, -5.0F, 0.0F);
		body.addChild(arm_left);
		arm_left.cubeList.add(new ModelBox(arm_left, 0, 0, -2.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 0.0F, 1.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 0, 16, -1.0F, -5.0F, 0.0F, 2, 5, 7, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 6, 18, -2.0F, -5.0F, 6.0F, 4, 0, 5, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leg_right.render(f5);
		leg_left.render(f5);
		body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;

		this.body.rotateAngleX = (float)Math.toRadians(MathHelper.cos(limbSwing * 0.6662F ) * 2.5F * limbSwingAmount + 2.5F);

		this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;

		this.arm_left.rotateAngleZ =  5.5F * (float) Math.PI / 180F +  (float)Math.toRadians(MathHelper.cos(ageInTicks * 0.2F) * 1.25F + 3.75F);
		this.arm_right.rotateAngleZ =  -5.5F * (float) Math.PI / 180F -  (float)Math.toRadians(MathHelper.cos(ageInTicks * 0.2F) * 1.25F + 3.75F);

		this.arm_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.arm_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		//this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		//this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleX= -25.0F * (float) Math.PI /180F;


		this.tail.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		this.tail.rotateAngleY = 0F;
		this.tail.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

		this.body.rotateAngleY= 0.0F;
		//this.body.rotateAngleX= 0.0F;
		this.arm_right.rotateAngleY = 0F;
		this.arm_left.rotateAngleY = 0F;
	}
}