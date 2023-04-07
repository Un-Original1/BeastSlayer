package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.0.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityLilVessel;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLilVessel extends ModelBiped {
	public ModelLilVessel(float scale) {
		super(scale, 0, 32, 32);
		textureWidth = 32;
		textureHeight = 32;

		this.bipedRightArm = new ModelRenderer(this);
		this.bipedRightArm.setRotationPoint(-2.0F, 15.0F, 0.0F);
		this.bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 0, 0, -2.0F, -1.0F, -1.0F, 2, 5, 2, scale, false));

		this.bipedLeftArm = new ModelRenderer(this);
		this.bipedLeftArm.setRotationPoint(2.0F, 15.0F, 0.0F);
		this.bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 12, 16, 0.0F, -1.0F, -1.0F, 2, 5, 2, scale, false));

		this.bipedRightLeg = new ModelRenderer(this);
		this.bipedRightLeg.setRotationPoint(-1.0F, 19.0F, 0.0F);
		this.bipedRightLeg.cubeList.add(new ModelBox(bipedRightLeg, 0, 23, -1.0F, 0.0F, -1.0F, 2, 5, 2, scale, false));

		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedLeftLeg.setRotationPoint(1.0F, 19.0F, 0.0F);
		this.bipedLeftLeg.cubeList.add(new ModelBox(bipedLeftLeg, 20, 16, -1.0F, 0.0F, -1.0F, 2, 5, 2, scale, false));

		this.bipedBody = new ModelRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 16, -2.0F, 0.0F, -1.0F, 4, 5, 2, scale, false));

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, scale, false));
	}
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		GlStateManager.pushMatrix();

		if (this.isChild)
		{
			GlStateManager.translate(0.0F, 14.0F * scale, 0.0F); //head?
			this.bipedHead.render(scale);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
		}
		else
		{
			if (entityIn.isSneaking())
			{
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}

			this.bipedHead.render(scale);
		}
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedHeadwear.render(scale);

		GlStateManager.popMatrix();
	}
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		this.rightArmPose = ModelBiped.ArmPose.EMPTY;
		this.leftArmPose = ModelBiped.ArmPose.EMPTY;
		ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);

		if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && ((EntityLilVessel)entitylivingbaseIn).isSwingingArms())
		{
			if (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT)
			{
				this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			}
			else
			{
				this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			}
		}

		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		this.bipedHeadwear.isHidden = true;
		this.bipedBody.rotationPointY = 14F;
		this.bipedLeftLeg.rotationPointY = 19F;
		this.bipedRightLeg.rotationPointY = 19F;
		this.bipedLeftArm.rotationPointX = 2F;
		this.bipedRightArm.rotationPointX = -2F;
		ItemStack itemstack = ((EntityLivingBase)entityIn).getHeldItemMainhand();
		EntityLilVessel vessel = (EntityLilVessel)entityIn;

		if (vessel.isSwingingArms() && (itemstack.isEmpty() || !(itemstack.getItem() instanceof ItemBow)))
		{
			float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
			float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
			this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F);
			this.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		}
		// /summon ancientbeasts:Lil_Vessel
	}
}