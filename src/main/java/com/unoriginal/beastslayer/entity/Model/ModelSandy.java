package com.unoriginal.beastslayer.entity.Model;

import com.unoriginal.beastslayer.entity.Entities.EntitySandy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSandy extends ModelBase {
	private final ModelRenderer body1;
	private final ModelRenderer head;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;
	private final ModelRenderer body2;
	private final ModelRenderer body3;
	private final ModelRenderer body4;
	private final ModelRenderer body5;
	private final ModelRenderer body6;
	private final ModelRenderer tail;
	private final ModelRenderer tail2;
	private final ModelRenderer hairRight;
	private final ModelRenderer hairLeft;
	private State state = State.NORMAL;

	public ModelSandy() {
		textureWidth = 256;
		textureHeight = 128;

		body1 = new ModelRenderer(this);
		body1.setRotationPoint(0.0F, -4.0F, -2.0F);
		body1.cubeList.add(new ModelBox(body1, 88, 0, -4.0F, -12.0F, -1.0F, 8, 12, 4, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 2, 104, -9.0F, -19.0F, 3.0F, 18, 19, 5, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 168, 53, -22.0F, -24.0F, 3.0F, 44, 31, 0, 0.01F, false));
		body1.cubeList.add(new ModelBox(body1, 3, 34, -14.0F, -25.0F, -4.0F, 28, 6, 12, 0.0F, false));

		ModelRenderer cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -9.0F, -3.0F);
		body1.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 86, 41, -4.0F, -3.0F, 0.0F, 8, 4, 4, 0.25F, false));

		ModelRenderer cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-5.0F, -8.0F, 2.5F);
		body1.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -0.0873F, -0.48F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 45, 52, 21.5F, -7.0F, -3.5F, 10, 6, 4, 0.0F, false));

		ModelRenderer cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-17.0F, -24.0F, 7.0F);
		body1.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0873F, 0.48F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 45, 52, -4.5F, -3.0F, -6.0F, 10, 6, 4, 0.0F, true));

		ModelRenderer cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -24.0F, 3.0F);
		body1.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.2182F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 5, 57, -7.0F, -3.0F, -8.0F, 14, 7, 12, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body1.addChild(head);
		head.cubeList.add(new ModelBox(head, 68, 82, -4.0F, -8.0F, -3.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 36, 76, -4.0F, -7.0F, -3.0F, 8, 4, 8, 0.25F, false));

		hairRight = new ModelRenderer(this);
		hairRight.setRotationPoint(5.0F, -7.0F, 0.0F);
		head.addChild(hairRight);
		setRotationAngle(hairRight, 0.0F, -0.48F, 0.0F);
		hairRight.cubeList.add(new ModelBox(hairRight, 0, 76, -2.6F, 0.0F, -2.28F, 6, 8, 6, 0.0F, true));
		hairRight.cubeList.add(new ModelBox(hairRight, 0, 90, -2.6F, 8.0F, -2.28F, 6, 1, 6, 0.0F, true));

		hairLeft = new ModelRenderer(this);
		hairLeft.setRotationPoint(-5.0F, -7.0F, 0.0F);
		head.addChild(hairLeft);
		setRotationAngle(hairLeft, 0.0F, 0.48F, 0.0F);
		hairLeft.cubeList.add(new ModelBox(hairLeft, 0, 76, -3.4F, 0.0F, -2.28F, 6, 8, 6, 0.0F, false));
		hairLeft.cubeList.add(new ModelBox(hairLeft, 0, 90, -3.4F, 8.0F, -2.28F, 6, 1, 6, 0.0F, false));


		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-4.0F, -10.0F, 1.0F);
		body1.addChild(rightArm);
		rightArm.cubeList.add(new ModelBox(rightArm, 0, 52, -3.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 94, 92, -4.0F, 10.0F, -3.0F, 5, 8, 6, 0.0F, false));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(4.0F, -10.0F, 1.0F);
		body1.addChild(leftArm);
		leftArm.cubeList.add(new ModelBox(leftArm, 0, 52, 0.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F, true));
		leftArm.cubeList.add(new ModelBox(leftArm, 94, 92, -1.0F, 10.0F, -3.0F, 5, 8, 6, 0.0F, true));

		body2 = new ModelRenderer(this);
		body2.setRotationPoint(0.0F, 0.0F, 0.0F);
		body1.addChild(body2);
		body2.cubeList.add(new ModelBox(body2, 80, 21, -6.0F, 0.0F, -3.0F, 12, 10, 10, 0.0F, false));

		body3 = new ModelRenderer(this);
		body3.setRotationPoint(0.0F, 10.0F, 0.0F);
		body2.addChild(body3);
		body3.cubeList.add(new ModelBox(body3, 62, 52, -8.0F, 0.0F, -4.0F, 16, 18, 12, 0.0F, false));

		body4 = new ModelRenderer(this);
		body4.setRotationPoint(0.0F, 18.0F, 2.0F);
		body3.addChild(body4);
		body4.cubeList.add(new ModelBox(body4, 192, 90, -9.0F, 0.0F, -7.0F, 18, 24, 14, 0.0F, false));

		body5 = new ModelRenderer(this);
		body5.setRotationPoint(0.0F, 24.0F, 0.0F);
		body4.addChild(body5);
		body5.cubeList.add(new ModelBox(body5, 204, 0, -7.0F, 0.0F, -6.0F, 14, 18, 12, 0.0F, false));

		body6 = new ModelRenderer(this);
		body6.setRotationPoint(0.0F, 18.0F, 0.0F);
		body5.addChild(body6);
		body6.cubeList.add(new ModelBox(body6, 49, 102, -5.0F, 0.0F, -5.0F, 10, 16, 10, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 16.0F, 0.0F);
		body6.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 127, 0, -3.0F, 0.0F, -3.0F, 6, 16, 6, 0.0F, false));

		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 16.0F, 0.0F);
		tail.addChild(tail2);
		tail2.cubeList.add(new ModelBox(tail2, 138, 26, -2.0F, 0.0F, -2.0F, 4, 16, 4, 0.0F, false));
		tail2.cubeList.add(new ModelBox(tail2, 156, 12, 0.0F, 13.0F, -9.0F, 0, 16, 16, 0.0F, false));
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if(!((EntitySandy) entity).isBuried())
			body1.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleY = netHeadYaw * 0.003F;
		this.head.rotateAngleX = headPitch * 0.002F;
																		//speed 			//distance
		this.hairLeft.rotateAngleX = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.005F;
		this.hairLeft.rotateAngleZ = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.025F;
		this.hairRight.rotateAngleX = this.hairLeft.rotateAngleX;
		this.hairRight.rotateAngleZ = this.hairLeft.rotateAngleZ;

		this.body4.rotateAngleX = (float)Math.PI / 3.25F - MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.025F;

		this.body1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.025F;
		this.body2.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.05F;

		if(this.state != ModelSandy.State.SITTING) {
			this.body5.rotateAngleX = -0.02F + MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.015F;
			this.body6.rotateAngleX = -0.02F -MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.005F;
			this.tail.rotateAngleX = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.0025F;
			this.tail2.rotateAngleX = 0.07F -MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.0015F;

			this.body4.rotateAngleZ = 0.0F;
			this.body4.rotateAngleY = MathHelper.cos(limbSwing * 0.2F) * limbSwingAmount *  0.5F;
			this.body5.rotateAngleZ = -MathHelper.sin(limbSwing * 0.2F) * limbSwingAmount * 0.7F;
			this.body6.rotateAngleZ = MathHelper.sin(limbSwing * 0.2F) * limbSwingAmount * 1.2F;
			this.tail.rotateAngleZ = -MathHelper.sin(limbSwing * 0.2F) * limbSwingAmount * 0.8F;
			this.tail2.rotateAngleZ = MathHelper.sin(limbSwing * 0.2F) * limbSwingAmount * 0.4F;
		}
		if(this.state != ModelSandy.State.SPELLCASTING && this.state != State.ATTACKING) {
			this.rightArm.rotateAngleX = -0.5F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.4F;
			this.leftArm.rotateAngleX = -0.5F + -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.4F;

			this.rightArm.rotateAngleZ = 0.6F + MathHelper.cos(ageInTicks * 0.3F) * 0.3F * 0.04F;
			this.leftArm.rotateAngleZ = -0.6F + -MathHelper.cos(ageInTicks * 0.3F) * 0.3F * 0.04F;
		}
		if(this.state == State.DODGING){
			if(entityIn.motionX < 0) {
				this.body1.rotateAngleZ = 17.5F * (float) Math.PI / 180F + MathHelper.cos(ageInTicks * 0.3F) * 0.3F * 0.4F;
				this.body2.rotateAngleZ = 2.5F * (float) Math.PI / 180F  - MathHelper.cos(ageInTicks * 0.3F) * 0.3F * 0.4F;
				this.body3.rotateAngleZ = -22.5F * (float) Math.PI / 180F;
				this.body3.rotateAngleY = -15F * (float) Math.PI / 180F;
				this.body3.rotateAngleX = 31.5F * (float) Math.PI / 180F;

				this.body4.rotateAngleX = 57.5F * (float) Math.PI / 180F;

				this.leftArm.rotateAngleZ = -63.5F * (float) Math.PI / 180F;
				this.rightArm.rotateAngleZ = 27.5F * (float) Math.PI / 180F;

				this.leftArm.rotateAngleX = -34.5F * (float) Math.PI / 180F;
				this.rightArm.rotateAngleX = -55.5F * (float) Math.PI / 180F;
			} else {
				this.body1.rotateAngleZ = -17.5F * (float) Math.PI / 180F - MathHelper.cos(ageInTicks * 0.5F) * 0.3F;
				this.body2.rotateAngleZ = -2.5F * (float) Math.PI / 180F  + MathHelper.cos(ageInTicks * 0.5F) * 0.3F;
				this.body3.rotateAngleZ = 22.5F * (float) Math.PI / 180F;
				this.body3.rotateAngleY = 15F * (float) Math.PI / 180F;
				this.body3.rotateAngleX = 31.5F * (float) Math.PI / 180F;

				this.body4.rotateAngleX = 57.5F * (float) Math.PI / 180F;

				this.rightArm.rotateAngleZ = 63.5F * (float) Math.PI / 180F;
				this.leftArm.rotateAngleZ = -27.5F * (float) Math.PI / 180F;

				this.rightArm.rotateAngleX = -34.5F * (float) Math.PI / 180F;
				this.leftArm.rotateAngleX = -55.5F * (float) Math.PI / 180F;
			}
		} else {
			this.body3.rotateAngleX = 0.6F;
			this.body3.rotateAngleY = 0F;
			this.body3.rotateAngleZ = 0F;
		}
		if(this.state == State.SPELLCASTING){
			this.rightArm.rotateAngleX = 0F - 0.5F + MathHelper.cos(ageInTicks * 0.662F) * -0.25F;
			this.leftArm.rotateAngleX = 0F - 0.5F - MathHelper.cos(ageInTicks  * 0.662F) * 0.25F;
		}
	}

	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		if(entitylivingbaseIn instanceof EntitySandy) {
			EntitySandy sandy = (EntitySandy) entitylivingbaseIn;
			int i = sandy.getAttackTimer();
			if (sandy.isSitting()) {
				this.body4.rotateAngleY = 1.0F;
				this.body4.rotateAngleZ= -0.35F;
				this.body5.rotateAngleX = -0.3F + MathHelper.cos(partialTickTime * 0.05F * (float) Math.PI) * 0.05F;
				this.body5.rotateAngleZ = 1.0F;
				this.body6.rotateAngleX = -0.2F - MathHelper.cos(partialTickTime * 0.05F * (float) Math.PI) * 0.05F;
				this.body6.rotateAngleZ = 1.0F;
				this.tail.rotateAngleX = -0.15F + MathHelper.cos(partialTickTime * 0.05F * (float) Math.PI) * 0.05F;
				this.tail.rotateAngleZ= 0.8F;
				this.tail2.rotateAngleX = 0.5F - MathHelper.cos(partialTickTime * 0.05F * (float) Math.PI) * 0.25F;
				this.tail2.rotateAngleZ= 1.0F;
				this.state = State.SITTING;
			}
			else if(sandy.isUsingMagic())
			{
				this.rightArm.rotateAngleZ = 2.1F;
				this.leftArm.rotateAngleZ = -2.1F;

				this.state= State.SPELLCASTING;
			}
			else if(sandy.isAttacking()){
				this.rightArm.rotateAngleZ = -0.1F;
				this.leftArm.rotateAngleZ = 0.1F;

				this.rightArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTickTime);
				this.leftArm.rotateAngleX = this.rightArm.rotateAngleX;
				this.state = State.ATTACKING;
			}
			else if(sandy.isBuried()){
				this.state = State.BURIED;
			} else if (sandy.getDodgeTicks() > 0){
				this.state = State.DODGING;
			}
			else {
				this.state = State.NORMAL;
			}
		}
	}
	private float triangleWave(float p_78172_1_)
	{
		return (Math.abs(p_78172_1_ % (float) 10.0 - (float) 10.0 * 0.5F) - (float) 10.0 * 0.25F) / ((float) 10.0 * 0.25F);
	}
	@SideOnly(Side.CLIENT)
	enum State
	{
		NORMAL,
		SITTING,
		ATTACKING,
		SPELLCASTING,
		BURIED,
		DODGING
	}
	//cube_r5 cube_r6
}