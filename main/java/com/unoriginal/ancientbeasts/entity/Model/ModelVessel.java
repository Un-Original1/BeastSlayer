package com.unoriginal.ancientbeasts.entity.Model;
// Made with Blockbench 4.0.1

import com.unoriginal.ancientbeasts.entity.Entities.EntityFakeDuplicate;
import com.unoriginal.ancientbeasts.entity.Entities.EntityVessel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVessel extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer bone3;
	private final ModelRenderer bone2;
	private final ModelRenderer body;
	private final ModelRenderer leg_left;
	private final ModelRenderer leg_right;
	private final ModelRenderer arm_left;
	private final ModelRenderer arm_right;
	private final ModelRenderer stringThing;
	private final ModelRenderer strings1;
	private final ModelRenderer strings2;

	public ModelVessel() {
		textureWidth = 64;
		textureHeight = 96;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 12.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 28, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 27, -1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 26, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.25F, false));
		head.cubeList.add(new ModelBox(head, 0, 16, 6.75F, -4.5F, -1.0F, 2, 2, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 0, 16, -8.75F, -4.5F, -1.0F, 2, 2, 2, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-4.0F, -8.0F, 0.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, -0.3927F);
		bone3.cubeList.add(new ModelBox(bone3, 0, 0, -5.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F, true));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(4.0F, -8.0F, -0.1F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, 0.3927F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 8, -1.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 84, -5.0F, -2.0F, -4.0F, 10, 2, 10, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 24, 26, -2.0F, 0.0F, -1.5F, 4, 5, 3, 0.0F, false));

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(1.0F, 5.0F, 0.0F);
		body.addChild(leg_left);
		leg_left.cubeList.add(new ModelBox(leg_left, 38, 26, -1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F, false));

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(-1.0F, 5.0F, 0.0F);
		body.addChild(leg_right);
		leg_right.cubeList.add(new ModelBox(leg_right, 46, 26, -1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F, false));

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(2.0F, 1.0F, 0.0F);
		body.addChild(arm_left);
		arm_left.cubeList.add(new ModelBox(arm_left, 54, 26, 0.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F, true));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-2.0F, 1.0F, 0.0F);
		body.addChild(arm_right);
		arm_right.cubeList.add(new ModelBox(arm_right, 39, 80, -2.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stringThing = new ModelRenderer(this);
		stringThing.setRotationPoint(0.0F, -5.0F, 0.0F);
		stringThing.cubeList.add(new ModelBox(stringThing, 0, 0, -2.0F, -9.0F, -10.0F, 4, 1, 20, 0.0F, false));
		stringThing.cubeList.add(new ModelBox(stringThing, 0, 21, -10.0F, -8.0F, -2.0F, 20, 1, 4, 0.0F, false));

		strings1 = new ModelRenderer(this);
		strings1.setRotationPoint(0.0F, 1.0F, 0.0F);
		stringThing.addChild(strings1);
		strings1.cubeList.add(new ModelBox(strings1, 33, 30, 0.0F, -9.0F, -5.0F, 0, 29, 13, 0.0F, false));

		strings2 = new ModelRenderer(this);
		strings2.setRotationPoint(0.0F, 1.0F, 0.0F);
		stringThing.addChild(strings2);
		strings2.cubeList.add(new ModelBox(strings2, 0, 44, -8.0F, -8.0F, 0.0F, 16, 28, 0, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if(entity instanceof EntityVessel){
			EntityVessel vessel = (EntityVessel) entity;
			if(!vessel.hasTargetedEntity()){
				head.render(f5);
			}
		}
		else {
			head.render(f5);
		}
		body.render(f5);
		stringThing.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleX = 11.25F * (float) Math.PI / 180F + headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.leg_left.rotateAngleX = 0.0F;
		this.leg_right.rotateAngleX = 0.0F;
		this.leg_left.rotateAngleX += MathHelper.cos(ageInTicks * 0.15F) * 1.2F + 0.5F * limbSwingAmount;
		this.leg_right.rotateAngleX += MathHelper.cos(ageInTicks * 0.15F + 0.5F) * 1.2F + 0.5F * limbSwingAmount;
		this.arm_left.rotateAngleX = 0.0F;
		this.arm_right.rotateAngleX = 0.0F;
		this.arm_left.rotationPointX = 2.0F;
		this.arm_right.rotationPointX = -2.0F;
		this.arm_right.rotateAngleX += MathHelper.cos(ageInTicks * 0.15F) * 0.8F + 0.5F * limbSwingAmount;
		this.arm_left.rotateAngleX += MathHelper.cos(ageInTicks * 0.15F + 1F) * 0.8F + 0.5F * limbSwingAmount;
		this.strings1.rotateAngleY = MathHelper.cos(ageInTicks * 0.07F) * 0.15F;
		this.strings2.rotateAngleY = MathHelper.cos(ageInTicks * 0.07F) * 0.15F;
		this.arm_right.rotateAngleZ = 0.0F;
		this.arm_left.rotateAngleZ = 0.0F;
		this.arm_right.rotateAngleY = 0.0F;
		this.arm_left.rotateAngleY = 0.0F;
		if(entityIn instanceof EntityVessel){
			EntityVessel v = (EntityVessel)entityIn;
			if(v.isInactive()){
				this.leg_left.rotateAngleX=0.0F;
				this.leg_right.rotateAngleX=0.0F;
				this.arm_left.rotateAngleX=0.0F;
				this.arm_right.rotateAngleX=0.0F;
			}
			if(v.getArmPose() == AbstractIllager.IllagerArmPose.SPELLCASTING){
				this.arm_right.rotationPointZ = 0.0F;
				this.arm_right.rotationPointX = -3.0F;
				this.arm_left.rotationPointZ = 0.0F;
				this.arm_left.rotationPointX = 3.0F;
				this.arm_right.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F);
				this.arm_left.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F);
				this.arm_right.rotateAngleZ = 2.3561945F;
				this.arm_left.rotateAngleZ = -2.3561945F;
				this.arm_right.rotateAngleY = 0.0F;
				this.arm_left.rotateAngleY = 0.0F;
			}
		}
		if(entityIn instanceof EntityFakeDuplicate){
			EntityFakeDuplicate i = (EntityFakeDuplicate) entityIn;
			if(i.getArmPose() == AbstractIllager.IllagerArmPose.SPELLCASTING){
				this.arm_right.rotationPointZ = 0.0F;
				this.arm_right.rotationPointX = -3.0F;
				this.arm_left.rotationPointZ = 0.0F;
				this.arm_left.rotationPointX = 3.0F;
				this.arm_right.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
				this.arm_left.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
				this.arm_right.rotateAngleZ = 2.3561945F;
				this.arm_left.rotateAngleZ = -2.3561945F;
				this.arm_right.rotateAngleY = 0.0F;
				this.arm_left.rotateAngleY = 0.0F;
			}
		}
	}
}