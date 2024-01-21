package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.2.3

import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTribeWarrior extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer head;
	private final ModelRenderer mask;
	private final ModelRenderer horn1;
	private final ModelRenderer horn0;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer leftFoot;
	private final ModelRenderer RightLeg;
	private final ModelRenderer rightFoot;

	public ModelTribeWarrior() {
		textureWidth = 80;
		textureHeight = 80;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -11.0F, -3.0F, 12, 8, 7, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 30, -3.0F, -10.0F, -2.0F, 6, 14, 4, 0.5F, false));
		body.cubeList.add(new ModelBox(body, 40, 38, -3.0F, -10.0F, -2.0F, 6, 11, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 30, 15, -4.0F, -11.0F, -3.0F, 8, 2, 6, 0.25F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 2.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 20, 38, -1.0F, -8.0F, 0.0F, 2, 8, 8, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 13, 38, -2.0F, -8.0F, 7.0F, 4, 0, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 48, 64, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 26, 75, -4.0F, -8.0F, 4.0F, 8, 2, 3, 0.0F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, -4.0F, -3.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 0, 15, -4.0F, -4.0F, 0.0F, 8, 8, 7, 0.25F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 15, 8.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 15, -9.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 23, 15, -9.0F, 0.0F, -2.0F, 4, 3, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 23, 15, 5.0F, 0.0F, -2.0F, 4, 3, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 38, 0, -5.0F, -5.0F, -2.001F, 10, 10, 2, 0.0F, false));

		horn1 = new ModelRenderer(this);
		horn1.setRotationPoint(3.5F, -4.0F, -4.5F);
		mask.addChild(horn1);
		horn1.cubeList.add(new ModelBox(horn1, 37, 53, -0.5F, -7.0F, -1.5F, 2, 10, 3, 0.0F, false));
		horn1.cubeList.add(new ModelBox(horn1, 0, 5, -0.5F, -7.0F, 1.5F, 2, 1, 1, 0.0F, true));
		horn1.cubeList.add(new ModelBox(horn1, 0, 0, -0.5F, -1.0F, 1.5F, 2, 4, 1, 0.0F, false));

		horn0 = new ModelRenderer(this);
		horn0.setRotationPoint(-11.5F, -2.0F, -4.5F);
		mask.addChild(horn0);
		horn0.cubeList.add(new ModelBox(horn0, 37, 53, 6.5F, -9.0F, -1.5F, 2, 10, 3, 0.0F, true));
		horn0.cubeList.add(new ModelBox(horn0, 0, 0, 6.5F, -3.0F, 1.5F, 2, 4, 1, 0.0F, false));
		horn0.cubeList.add(new ModelBox(horn0, 0, 5, 6.5F, -9.0F, 1.5F, 2, 1, 1, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(3.0F, -9.0F, 0.0F);
		body.addChild(LeftArm);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 14, 52, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 0, 48, 0.0F, 6.0F, -2.0F, 3, 10, 4, 0.25F, false));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-3.0F, -9.0F, 0.0F);
		body.addChild(RightArm);
		RightArm.cubeList.add(new ModelBox(RightArm, 14, 52, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));
		RightArm.cubeList.add(new ModelBox(RightArm, 0, 48, -3.0F, 6.0F, -2.0F, 3, 10, 4, 0.25F, true));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(3.0F, 15.0F, -1.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 53, 18, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, false));

		leftFoot = new ModelRenderer(this);
		leftFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		LeftLeg.addChild(leftFoot);
		leftFoot.cubeList.add(new ModelBox(leftFoot, 53, 28, -1.0F, -1.0F, 0.0F, 2, 7, 2, 0.0F, false));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-3.0F, 15.0F, -1.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 53, 18, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, true));

		rightFoot = new ModelRenderer(this);
		rightFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		RightLeg.addChild(rightFoot);
		rightFoot.cubeList.add(new ModelBox(rightFoot, 53, 28, -1.0F, -1.0F, 0.0F, 2, 7, 2, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		//ModelHelper.renderRelative(body, Arrays.asList(LeftArm, RightArm), f5);
		RightLeg.render(f5);
		LeftLeg.render(f5);
		//LeftArm.render(f5);
		//RightArm.render(f5);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;

		this.body.rotateAngleX = (float)Math.toRadians(MathHelper.cos(limbSwing * 0.6662F ) * 2.5F * limbSwingAmount + 2.5F);
		this.mask.rotateAngleZ = (float)Math.toRadians(MathHelper.cos(limbSwing * 0.6662F ) * 2.5F * limbSwingAmount);

		this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;

		this.RightArm.rotateAngleZ =  5.5F * (float) Math.PI / 180F +  (float)Math.toRadians(MathHelper.cos(ageInTicks * 0.2F) * 1.25F + 3.75F);
		this.LeftArm.rotateAngleZ =  -5.5F * (float) Math.PI / 180F -  (float)Math.toRadians(MathHelper.cos(ageInTicks * 0.2F) * 1.25F + 3.75F);

		this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		//this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		//this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleX= -25.0F * (float) Math.PI /180F;


		this.tail.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		this.tail.rotateAngleY = 0F;
		this.tail.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

		this.body.rotateAngleY= 0.0F;
		//this.body.rotateAngleX= 0.0F;
		this.RightArm.rotateAngleY = 0F;
		this.LeftArm.rotateAngleY = 0F;
		if(entityIn instanceof AbstractTribesmen){
			AbstractTribesmen tribesmen = (AbstractTribesmen) entityIn;
			if(tribesmen.isFiery() && tribesmen.getFieryTicks() <= 0 ) {
				this.body.rotateAngleX = 36.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
				this.head.rotateAngleX -= 36.5F * (float) Math.PI / 180F;
				this.RightArm.rotateAngleX -= 36.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleX -= 36.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
		

			}
			else if (tribesmen.getFieryTicks() > 0)
			{
				this.body.rotateAngleX = -22.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.05F;

				this.RightArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;

				this.head.rotateAngleX -= 40F * (float) Math.PI / 180F;
				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
			}
			else if (!tribesmen.isFiery()) {
				if(tribesmen.isTrading()){
					if(tribesmen.getPrimaryHand() == EnumHandSide.LEFT){
						this.LeftArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleY = 13F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleZ = -12F * (float) Math.PI / 180F;

					}
					else if ((tribesmen.getPrimaryHand()) == EnumHandSide.RIGHT)
					{
						this.RightArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleY = -13F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleZ = 12F * (float) Math.PI / 180F;
					}
					this.head.rotateAngleX = 22.5F * (float) Math.PI / 180F;
				}
			}
			if(entityIn instanceof EntityTribeWarrior){
				EntityTribeWarrior warrior = (EntityTribeWarrior) entityIn;

				if(warrior.getAngryTick() >= 0){
					this.RightArm.rotateAngleX = -60F * (float) Math.PI / 180F ;
					this.LeftArm.rotateAngleX = -60F * (float) Math.PI / 180F ;

					this.RightArm.rotateAngleZ = 92.5F * (float) Math.PI / 180F;
					this.LeftArm.rotateAngleZ = -92.5F * (float) Math.PI / 180F;

					this.body.rotateAngleX = 62.5F * (float) Math.PI / 180F;
					this.body.rotateAngleX += (-MathHelper.cos(0.63F * ( warrior.getAngryTick() - 30) ) * 2.5F + 2.5F) * (float) Math.PI / 180F;
					this.head.rotateAngleY= 0F;
					if(warrior.getAngryTick() >= 25) {
						this.head.rotateAngleX = 26F * (warrior.getAngryTick() - 30) * (float) Math.PI / 180F;
					} else if (warrior.getAngryTick() < 25 && warrior.getAngryTick() >= 5 ) {
						this.head.rotateAngleX = (MathHelper.cos(0.3F * (30 - warrior.getAngryTick()) + 9.4F) * 40 + 20)* (float) Math.PI / 180F;
					} else{
						this.head.rotateAngleX = (32.5F * warrior.getAngryTick() - 70F) * (float) Math.PI / 180F;
					}
				}
			}
		}
		if (this.swingProgress > 0.0F && entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase l = (EntityLivingBase)entityIn;
			EnumHandSide enumhandside = l.getPrimaryHand();
			ModelRenderer modelrenderer = this.getArm(enumhandside);
			float f1 = this.swingProgress;
			this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT)
			{
				this.body.rotateAngleY *= -1.0F;
			}

			this.RightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.RightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 3.0F;
			this.LeftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.LeftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 3.0F;
			this.RightArm.rotateAngleY += this.body.rotateAngleY;
			this.LeftArm.rotateAngleY += this.body.rotateAngleY;
			this.LeftArm.rotateAngleX += this.body.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float)Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}
	}

	public ModelRenderer getArm(EnumHandSide p_191216_1_)
	{
		return p_191216_1_ == EnumHandSide.LEFT ? this.LeftArm : this.RightArm;
	}

}