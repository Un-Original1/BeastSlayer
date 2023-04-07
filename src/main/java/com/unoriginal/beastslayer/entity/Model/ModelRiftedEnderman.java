package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.1.5

import com.unoriginal.beastslayer.entity.Entities.EntityRiftedEnderman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelRiftedEnderman extends ModelBiped {
	public boolean isCarrying;
	public boolean isAttacking;

	private final ModelRenderer bipedLeftLegwear;
	private final ModelRenderer bipedRightLegwear;

	public ModelRiftedEnderman(float scale) {
		super(scale, -14.0F, 64, 96);
		textureWidth = 64;
		textureHeight = 96;

		this.bipedRightLeg = new ModelRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.0F, -2.0F, 0.0F);

		this.bipedRightLeg.cubeList.add(new ModelBox(this.bipedRightLeg, 56, 0, -1.0F, 0.0F, -1.0F, 2, 30, 2, 0F, true));

		this.bipedRightLegwear = new ModelRenderer(this);
		this.bipedRightLegwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedRightLegwear.cubeList.add(new ModelBox(this.bipedRightLegwear, 56, 32, -1.0F, 0.0F, -1.0F, 2, 30, 2, 0.25F, false));
		this.bipedRightLeg.addChild(bipedRightLegwear);

		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedLeftLeg.setRotationPoint(2.0F, -2.0F, 0.0F);

		this.bipedLeftLeg.cubeList.add(new ModelBox( this.bipedLeftLeg, 56, 0, -1.0F, 0.0F, -1.0F, 2, 30, 2, 0F, false));

		this.bipedLeftLegwear = new ModelRenderer(this);
		this.bipedLeftLegwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedLeftLegwear.cubeList.add(new ModelBox( this.bipedLeftLegwear, 56, 32, -1.0F, 0.0F, -1.0F, 2, 30, 2, 0.25F, true));
		this.bipedLeftLeg.addChild(bipedLeftLegwear);

		this.bipedBody = new ModelRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, -14.0F, 0.0F);
		this.bipedBody.cubeList.add(new ModelBox(this.bipedBody, 32, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, false));
		this.bipedBody.cubeList.add(new ModelBox(this.bipedBody, 32, 32, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F, false));
		this.bipedBody.cubeList.add(new ModelBox(this.bipedBody, 32, 48, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.75F, false));

		this.bipedRightArm = new ModelRenderer(this);
		this.bipedRightArm.setRotationPoint(-3.0F, -12.0F, 0.0F);

		this.bipedRightArm.cubeList.add(new ModelBox( this.bipedRightArm, 56, 0, -1.0F, -2.0F, -1.0F, 2, 30, 2, 0F, true));
		this.bipedRightArm.cubeList.add(new ModelBox( this.bipedRightArm, 56, 32, -1.0F, -2.0F, -1.0F, 2, 30, 2, 0.5F, true));

		this.bipedRightArm.cubeList.add(new ModelBox(this.bipedLeftArm, 0, 72, -4.0F, -3.0F, -3.0F, 0, 6, 6, 0F, false));
		this.bipedRightArm.cubeList.add(new ModelBox(this.bipedLeftArm, 6, 84, -4.0F, -7.0F, -3.0F, 6, 4, 6, 0F, true));
		this.bipedRightArm.cubeList.add(new ModelBox(this.bipedLeftArm, 32, 84, -8.0F, -3.0F, -3.0F, 10, 6, 6, 0F, true));

		this.bipedLeftArm = new ModelRenderer(this);
		this.bipedLeftArm.setRotationPoint(5.0F, -12.0F, 0.0F);

		this.bipedLeftArm.cubeList.add(new ModelBox(this.bipedLeftArm, 56, 32, -1.0F, -2.0F, -1.0F, 2, 30, 2, 0.5F, false));
		this.bipedLeftArm.cubeList.add(new ModelBox(this.bipedLeftArm, 56, 0, -1.0F, -2.0F, -1.0F, 2, 30, 2, 0F, false));

		this.bipedLeftArm.cubeList.add(new ModelBox( this.bipedRightArm, 32, 84, -2.0F, -3.0F, -3.0F, 10, 6, 6, 0F, false));
		this.bipedLeftArm.cubeList.add(new ModelBox( this.bipedRightArm, 6, 84, -2.0F, -7.0F, -3.0F, 6, 4, 6, 0F, false));
		this.bipedLeftArm.cubeList.add(new ModelBox( this.bipedRightArm, 0, 72, 4.0F, -3.0F, -3.0F, 0, 6, 6, 0F, false));

		this.bipedHead = new ModelRenderer(this);
		this.bipedHead.setRotationPoint(0.0F, -14.0F, 0.0F);
		this.bipedHead.cubeList.add(new ModelBox(this.bipedHead, 0, 16, -4.0F, -8.01F, -4.0F, 8, 8, 8, 0F, false));
		this.bipedHead.cubeList.add(new ModelBox(this.bipedHead, 0, 48, -4.0F, -8.01F, -4.0F, 8, 8, 8, 0.26F, false));

		this.bipedHeadwear = new ModelRenderer(this);
		this.bipedHeadwear.setRotationPoint(0.0F, -14.0F, 0.0F);
		this.bipedHeadwear.cubeList.add(new ModelBox( this.bipedHeadwear, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F, false));
		this.bipedHeadwear.cubeList.add(new ModelBox( this.bipedHeadwear, 0, 32, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F, false));
		this.bipedHeadwear.cubeList.add(new ModelBox( this.bipedHeadwear, 0, 64, -4.0F, -13.0F, -4.0F, 8, 4, 8, 0.5F, false));
	}


	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		GlStateManager.pushMatrix();

		if (this.isChild)
		{
			float f = 2.0F;
			GlStateManager.scale(0.75F, 0.75F, 0.75F);
			GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
			this.bipedHead.render(scale);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
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

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		this.bipedHead.showModel = true;
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedBody.rotationPointY = -14.0F;
		this.bipedBody.rotationPointZ = -0.0F;
		this.bipedRightLeg.rotateAngleX -= 0.0F;
		this.bipedLeftLeg.rotateAngleX -= 0.0F;
		this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX * 0.5D);
		this.bipedLeftArm.rotateAngleX = (float)((double)this.bipedLeftArm.rotateAngleX * 0.5D);
		this.bipedRightLeg.rotateAngleX = (float)((double)this.bipedRightLeg.rotateAngleX * 0.5D);
		this.bipedLeftLeg.rotateAngleX = (float)((double)this.bipedLeftLeg.rotateAngleX * 0.5D);

		if (this.bipedRightArm.rotateAngleX > 0.4F)
		{
			this.bipedRightArm.rotateAngleX = 0.4F;
		}

		if (this.bipedLeftArm.rotateAngleX > 0.4F)
		{
			this.bipedLeftArm.rotateAngleX = 0.4F;
		}

		if (this.bipedRightArm.rotateAngleX < -0.4F)
		{
			this.bipedRightArm.rotateAngleX = -0.4F;
		}

		if (this.bipedLeftArm.rotateAngleX < -0.4F)
		{
			this.bipedLeftArm.rotateAngleX = -0.4F;
		}

		if (this.bipedRightLeg.rotateAngleX > 0.4F)
		{
			this.bipedRightLeg.rotateAngleX = 0.4F;
		}

		if (this.bipedLeftLeg.rotateAngleX > 0.4F)
		{
			this.bipedLeftLeg.rotateAngleX = 0.4F;
		}

		if (this.bipedRightLeg.rotateAngleX < -0.4F)
		{
			this.bipedRightLeg.rotateAngleX = -0.4F;
		}

		if (this.bipedLeftLeg.rotateAngleX < -0.4F)
		{
			this.bipedLeftLeg.rotateAngleX = -0.4F;
		}

		if (this.isCarrying)
		{
			this.bipedRightArm.rotateAngleX = -0.5F;
			this.bipedLeftArm.rotateAngleX = -0.5F;
			this.bipedRightArm.rotateAngleZ = 0.05F;
			this.bipedLeftArm.rotateAngleZ = -0.05F;
		}

		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointZ = 0.0F;
		this.bipedLeftLeg.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointY = -5.0F;
		this.bipedLeftLeg.rotationPointY = -5.0F;
		this.bipedHead.rotationPointZ = -0.0F;
		this.bipedHead.rotationPointY = -13.0F;
		this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
		this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
		this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

		if (this.isAttacking)
		{
			this.bipedHeadwear.rotationPointY -= 5.0F;
		}
		if(entityIn instanceof EntityRiftedEnderman){
			int d = ((EntityRiftedEnderman) entityIn).getArmorValue();
			boolean b = d == 2;
			this.bipedLeftLegwear.showModel = b;
			this.bipedRightLegwear.showModel = b;
		}
	}
}