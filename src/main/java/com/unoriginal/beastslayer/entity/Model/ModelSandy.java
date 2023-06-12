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
	private final ModelRenderer hair_r;
	private final ModelRenderer hair_l;
	private final ModelRenderer awooga;
	private State state = State.NORMAL;

	public ModelSandy() {
		textureWidth = 128;
		textureHeight = 128;

		body2 = new ModelRenderer(this);
		body2.setRotationPoint(0.0F, -4.0F, -2.0F);
		body2.cubeList.add(new ModelBox(body2, 0, 66, -6.0F, 0.0F, -3.0F, 12, 10, 10, 0.0F, false));

		body3 = new ModelRenderer(this);
		body3.setRotationPoint(0.0F, 10.0F, 0.0F);
		body2.addChild(body3);
		body3.cubeList.add(new ModelBox(body3, 52, 0, -7.0F, 0.0F, -4.0F, 14, 18, 12, 0.0F, false));

		body4 = new ModelRenderer(this);
		body4.setRotationPoint(0.0F, 18.0F, 2.0F);
		body3.addChild(body4);
		body4.cubeList.add(new ModelBox(body4, 0, 0, -7.0F, 0.0F, -6.0F, 14, 24, 12, 0.1F, false));

		body5 = new ModelRenderer(this);
		body5.setRotationPoint(0.0F, 24.0F, 0.0F);
		body4.addChild(body5);
		body5.cubeList.add(new ModelBox(body5, 0, 36, -7.0F, 0.0F, -6.0F, 14, 18, 12, 0.0F, false));

		body6 = new ModelRenderer(this);
		body6.setRotationPoint(0.0F, 18.0F, 0.0F);
		body5.addChild(body6);
		body6.cubeList.add(new ModelBox(body6, 52, 49, -5.0F, 0.0F, -5.0F, 10, 16, 10, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 16.0F, -1.0F);
		body6.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 48, 97, -3.0F, 0.0F, -3.0F, 6, 16, 6, 0.0F, false));

		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 16.0F, 0.0F);
		tail.addChild(tail2);
		tail2.cubeList.add(new ModelBox(tail2, 0, 102, -2.0F, 0.0F, -2.0F, 4, 16, 4, 0.0F, false));
		tail2.cubeList.add(new ModelBox(tail2, 0, 74, 0.0F, 12.0F, -6.0F, 0, 16, 12, 0.0F, false));

		body1 = new ModelRenderer(this);
		body1.setRotationPoint(0.0F, -4.0F, -2.0F);
		body1.cubeList.add(new ModelBox(body1, 72, 98, -4.0F, -12.0F, -1.0F, 8, 12, 4, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 84, 75, -7.0F, -19.0F, 4.0F, 14, 19, 4, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 44, 75, -22.0F, -24.0F, 2.0F, 18, 20, 2, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 44, 75, 4.0F, -24.0F, 2.0F, 18, 20, 2, 0.0F, true));
		body1.cubeList.add(new ModelBox(body1, 96, 98, -14.0F, -4.0F, 2.0F, 10, 12, 2, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 96, 98, 4.0F, -4.0F, 2.0F, 10, 12, 2, 0.0F, true));
		body1.cubeList.add(new ModelBox(body1, 40, 8, -22.0F, -26.0F, 2.0F, 6, 2, 2, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 40, 8, 16.0F, -26.0F, 2.0F, 6, 2, 2, 0.0F, true));
		body1.cubeList.add(new ModelBox(body1, 52, 30, -7.0F, -26.0F, -4.0F, 14, 7, 12, 0.0F, false));
		body1.cubeList.add(new ModelBox(body1, 104, 14, -4.0F, 0.0F, 2.0F, 8, 8, 2, 0.0F, false));

		awooga = new ModelRenderer(this);
		awooga.setRotationPoint(0.0F, -9.0F, -3.0F);
		body1.addChild(awooga);
		setRotationAngle(awooga, -0.7854F, 0.0F, 0.0F);
		awooga.cubeList.add(new ModelBox(awooga, 40, 0, -4.0F, -3.0F, 0.0F, 8, 4, 4, 0.25F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body1.addChild(head);
		head.cubeList.add(new ModelBox(head, 92, 49, -4.0F, -7.0F, -3.0F, 8, 8, 8, 0.25F, false));
		head.cubeList.add(new ModelBox(head, 16, 94, -4.0F, -8.0F, -3.0F, 8, 8, 8, 0.0F, false));

		hair_l = new ModelRenderer(this);
		hair_l.setRotationPoint(5.0F, -7.0F, 0.0F);
		head.addChild(hair_l);
		hair_l.cubeList.add(new ModelBox(hair_l, 98, 24, -2.6F, 0.0F, -2.3F, 6, 8, 6, 0.0F, true));
		hair_l.cubeList.add(new ModelBox(hair_l, 92, 65, -2.6F, 8.0F, -2.3F, 6, 1, 6, 0.0F, true));
		setRotationAngle(hair_l, 0.0F, -0.479F, 0.0F);

		hair_r = new ModelRenderer(this);
		hair_r.setRotationPoint(-5.0F, -7.0F, 0.0F);
		head.addChild(hair_r);
		hair_r.cubeList.add(new ModelBox(hair_r, 98, 24, -3.4F, 0.0F, -2.3F, 6, 8, 6, 0.0F, false));
		hair_r.cubeList.add(new ModelBox(hair_r, 92, 65, -3.4F, 8.0F, -2.3F, 6, 1, 6, 0.0F, false));
		setRotationAngle(hair_r, 0.0F, 0.479F, 0.0F);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-4.0F, -10.0F, 1.0F);
		body1.addChild(rightArm);
		rightArm.cubeList.add(new ModelBox(rightArm, 16, 110, -3.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 104, 0, -4.0F, 10.0F, -3.0F, 5, 8, 6, 0.0F, false));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(4.0F, -10.0F, 1.0F);
		body1.addChild(leftArm);
		leftArm.cubeList.add(new ModelBox(leftArm, 16, 110, 0.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F, true));
		leftArm.cubeList.add(new ModelBox(leftArm, 104, 0, -1.0F, 10.0F, -3.0F, 5, 8, 6, 0.0F, true));
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if(!((EntitySandy) entity).isBuried()) {
			body1.render(f5);
			body2.render(f5);
		}
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
		this.hair_l.rotateAngleX = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.005F;
		this.hair_l.rotateAngleZ = MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.025F;
		this.hair_r.rotateAngleX = this.hair_l.rotateAngleX;
		this.hair_r.rotateAngleZ = this.hair_l.rotateAngleZ;

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