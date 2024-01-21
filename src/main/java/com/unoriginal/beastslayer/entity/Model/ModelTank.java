package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class ModelTank extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer head;
	private final ModelRenderer mask;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer RightFoot;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer LeftFoot;

	public ModelTank() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 32, -7.0F, -16.0F, -5.0F, 14, 16, 10, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -7.0F, -16.0F, -5.0F, 14, 22, 10, 0.5F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 5.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 84, 20, -1.0F, -8.0F, 0.0F, 2, 8, 8, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 70, 19, -2.0F, -8.0F, 7.0F, 4, 0, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -10.0F, -5.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 48, 29, -4.0F, -4.0F, -7.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 42, 110, -4.0F, -4.0F, -6.0F, 8, 8, 7, 0.25F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, 0.0F, -5.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 48, 46, -5.0F, -10.01F, -3.01F, 10, 15, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 39, 2, -11.0F, 0.0F, -3.0F, 6, 3, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 1, 76, -7.0F, -10.01F, -3.0F, 2, 3, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 1, 76, 5.0F, -10.01F, -3.01F, 2, 3, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 39, 2, 5.0F, 0.0F, -3.0F, 6, 3, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 61, 6, -11.0F, -1.0F, -3.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 61, 6, 10.0F, -1.0F, -3.0F, 1, 1, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 61, 6, 6.0F, -11.01F, -3.01F, 1, 1, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 61, 6, -7.0F, -11.01F, -3.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 4, 67, -1.0F, -10.01F, -3.01F, 2, 6, 2, 0.0F, false));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-7.0F, -12.0F, 0.0F);
		body.addChild(RightArm);
		RightArm.cubeList.add(new ModelBox(RightArm, 48, 10, -7.0F, 6.0F, -3.5F, 7, 12, 7, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 53, 74, -5.0F, -2.0F, -2.0F, 5, 8, 4, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(7.0F, -12.0F, 0.0F);
		body.addChild(LeftArm);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 48, 10, 0.0F, 6.0F, -3.5F, 7, 12, 7, 0.0F, true));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 53, 74, 0.0F, -2.0F, -2.0F, 5, 8, 4, 0.0F, true));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-7.0F, 16.0F, -4.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 106, 116, -2.0F, -2.0F, -2.5F, 5, 6, 6, 0.0F, true));

		RightFoot = new ModelRenderer(this);
		RightFoot.setRotationPoint(0.0F, 2.0F, 4.0F);
		RightLeg.addChild(RightFoot);
		RightFoot.cubeList.add(new ModelBox(RightFoot, 76, 114, -2.01F, -2.0F, -0.5F, 5, 8, 5, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(7.0F, 16.0F, -4.0F);
		setRotationAngle(LeftLeg, 0.0F, 0.0F, 0.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 106, 116, -3.0F, -2.0F, -2.5F, 5, 6, 6, 0.0F, false));

		LeftFoot = new ModelRenderer(this);
		LeftFoot.setRotationPoint(0.0F, 2.0F, 4.0F);
		LeftLeg.addChild(LeftFoot);
		LeftFoot.cubeList.add(new ModelBox(LeftFoot, 76, 114, -3.01F, -2.0F, -0.5F, 5, 8, 5, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		//ModelHelper.renderRelative(body, Arrays.asList(LeftArm, RightArm), f5);
		RightLeg.render(f5);
		LeftLeg.render(f5);
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
		this.head.rotateAngleZ= 0F;
		this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.RightArm.rotateAngleZ = 0F;
		this.LeftArm.rotateAngleZ = 0F;
		this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F+ (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleX= -25.0F * (float) Math.PI /180F;
		//this.art1.rotateAngleX= 115F * (float) Math.PI /180F;
		//this.point.rotateAngleX = -55F * (float) Math.PI / 180F;
		this.tail.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		//this.art1.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		//this.point.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleY = 0F;
		this.tail.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

		this.body.rotateAngleY= 0.0F;
		this.body.rotateAngleX= 0.0F;

		this.RightArm.rotateAngleY = 0F;
		this.LeftArm.rotateAngleY = 0F;

		this.RightArm.rotationPointZ = 0F;
		this.RightArm.rotationPointX = -7F;
		this.LeftArm.rotationPointZ = 0F;
		this.LeftArm.rotationPointX = 7F;
		if(entityIn instanceof AbstractTribesmen){
			AbstractTribesmen tribesmen = (AbstractTribesmen) entityIn;
			if(tribesmen.isFiery() && tribesmen.getFieryTicks() <= 0 ) {
				this.body.rotateAngleX = 12.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
				this.head.rotateAngleX -= 12.5F * (float) Math.PI / 180F;
				this.RightArm.rotateAngleX -= 12.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleX -= 12.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleZ -= MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleZ -= MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
			}
			else if (tribesmen.getFieryTicks() > 0)
			{
				this.body.rotateAngleX = -32.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.05F;

				this.RightArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;

				this.head.rotateAngleX -= 40F * (float) Math.PI / 180F;
				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
			}
			else if (!tribesmen.isFiery()) {
				this.mask.rotateAngleZ = MathHelper.sin(ageInTicks * 0.067F) * 0.025F;
				if(tribesmen.isTrading()){
					if(tribesmen.getPrimaryHand() == EnumHandSide.LEFT ){
						this.LeftArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleY = 13F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleZ = -12F * (float) Math.PI / 180F;

					}
					else if (tribesmen.getPrimaryHand() == EnumHandSide.RIGHT)
					{
						this.RightArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleY = -13F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleZ = 12F * (float) Math.PI / 180F;
					}
					this.head.rotateAngleX = 22.5F * (float) Math.PI / 180F;
				}
			}
		}
		if(entityIn instanceof EntityTank){
			EntityTank t = (EntityTank) entityIn;
			if(t.isBlocking() && !t.isAttacking()){
				this.LeftArm.rotateAngleX = -75F * (float)Math.PI / 180F;
				this.LeftArm.rotateAngleY = 30F * (float)Math.PI / 180F;

				this.RightArm.rotateAngleX = -47.5F * (float)Math.PI / 180F;
				this.RightArm.rotateAngleY = -30F * (float)Math.PI / 180F;

				this.head.rotateAngleX = 16F * (float)Math.PI / 180F;
				this.head.rotateAngleZ = 12.5F * (float)Math.PI / 180F;
			}
			if(t.isAttacking()){
				this.LeftArm.rotateAngleX = (-75F + (-6F * (10 - t.getAttackTick()))) * (float)Math.PI / 180F;
				this.RightArm.rotateAngleX = (-47.5F + (-8.76F * (10 - t.getAttackTick()))) * (float)Math.PI / 180F;

				this.LeftArm.rotateAngleZ = -0.5F * (10 - t.getAttackTick()) * (float)Math.PI / 180F;
				this.RightArm.rotateAngleZ = 0.5F * (10 - t.getAttackTick()) * (float)Math.PI / 180F;

				this.LeftArm.rotateAngleY = 0F;
				this.RightArm.rotateAngleY = 0F;
			}
			if(t.isTorchInHand() && !t.isBlocking() && !t.isAttacking()){
				if(!t.isFiery()) {
					if(t.getPrimaryHand() == EnumHandSide.LEFT) {
						this.LeftArm.rotateAngleX = this.LeftArm.rotateAngleX * 0.25F - 0.7424779F;
						this.LeftArm.rotateAngleY = 0.3235988F;
					}
					else {
						this.RightArm.rotateAngleX = this.RightArm.rotateAngleX * 0.25F - 0.7424779F;
						this.RightArm.rotateAngleY = -0.5235988F;
					}
				}
				else {
					this.LeftArm.rotateAngleX = this.LeftArm.rotateAngleX + (17F * (float)Math.PI / 72F) + MathHelper.sin(ageInTicks * 0.9F) * 0.05F;
					this.LeftArm.rotateAngleY = 5F * (float)Math.PI / 72F;
					this.LeftArm.rotateAngleZ = 5F * (float)Math.PI / 24F;

					this.RightArm.rotateAngleX = this.LeftArm.rotateAngleX + (17F * (float)Math.PI / 72F) - MathHelper.sin(ageInTicks * 0.9F) * 0.05F;
					this.RightArm.rotateAngleY = -5F * (float)Math.PI / 72F;
					this.RightArm.rotateAngleZ = -5F * (float)Math.PI / 24F;
				}
			}
		}
		if (this.swingProgress > 0.0F)
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
		//	this.RightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 3.0F;
			this.LeftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
		//	this.LeftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 3.0F;
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