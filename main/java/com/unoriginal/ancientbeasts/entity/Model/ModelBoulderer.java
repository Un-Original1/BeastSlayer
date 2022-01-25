package com.unoriginal.ancientbeasts.entity.Model;// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.ancientbeasts.entity.Entities.EntityBoulderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBoulderer extends ModelBiped {

	public ModelBoulderer()
	{
		this(0.0F, false);
	}

	public ModelBoulderer(float modelSize, boolean is32) {
		super(modelSize, 0.0F, 64, is32 ? 32 : 64);
		textureWidth = 64;
		textureHeight = 64;

		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 28, 31, 0.0F, -2.0F, -2.0F, 5, 12, 4, 0.0F, false));

		bipedRightLeg = new ModelRenderer(this);
		bipedRightLeg.setRotationPoint(-2.5F, 12.0F, 0.0F);
		bipedRightLeg.cubeList.add(new ModelBox(bipedRightLeg, 0, 35, -2.5F, 0.0F, -2.0F, 5, 12, 4, 0.0F, false));

		bipedLeftLeg = new ModelRenderer(this);
		bipedLeftLeg.setRotationPoint(2.5F, 12.0F, 0.0F);
		bipedLeftLeg.cubeList.add(new ModelBox(bipedLeftLeg, 42, 43, -2.5F, 0.0F, -2.0F, 5, 12, 4, 0.0F, false));

		bipedHead = new ModelRenderer(this);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 19, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));

		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 32, 15, -5.0F, -2.0F, -2.0F, 5, 12, 4, 0.0F, false));

		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 0, -5.0F, 0.0F, -3.5F, 10, 12, 7, 0.0F, false));
		this.bipedHeadwear.isHidden = true;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		boolean flag = entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isArmsRaised();
		float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
		float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
		this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
		float f2 = -(float)Math.PI / (flag ? 1.5F : 2.25F);
		this.bipedRightArm.rotateAngleX = f2;
		this.bipedLeftArm.rotateAngleX = f2;
		this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
		this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		if(entityIn instanceof EntityBoulderer){
			EntityBoulderer b = (EntityBoulderer) entityIn;
			if(b.isOnLadder()){
				this.bipedRightArm.rotateAngleX = 167.5F * -(float)Math.PI / 180F + MathHelper.cos(ageInTicks * 0.6662F) * 6.0F * limbSwingAmount;
				this.bipedLeftArm.rotateAngleX = 167.5F * -(float)Math.PI / 180F + MathHelper.cos(ageInTicks * 0.6662F + (float)Math.PI) * 6.0F * limbSwingAmount;
				this.bipedLeftArm.rotateAngleZ = 22.5F * (float)Math.PI / 180F;
				this.bipedRightArm.rotateAngleZ = -22.5F * (float)Math.PI / 180F;

				this.bipedLeftLeg.rotateAngleX = 22.5F * -(float)Math.PI / 180F + MathHelper.cos(ageInTicks * 0.6662F) * 4.0F * limbSwingAmount;
				this.bipedRightLeg.rotateAngleX = 22.5F * -(float)Math.PI / 180F + MathHelper.cos(ageInTicks * 0.6662F + (float)Math.PI) * 4.0F * limbSwingAmount;
				this.bipedHead.rotateAngleX = headPitch * 0.017453292F + -(float)Math.PI / 4F;
			}
		}
	}
}