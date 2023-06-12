package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.1.5

import com.unoriginal.beastslayer.entity.Entities.EntityRiftedEnderman;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelRiftedEnderman extends ModelBase {
	public boolean isAttacking;

	private final ModelRenderer bipedRightLeg;
	private final ModelRenderer bipedLeftLegwear;
	private final ModelRenderer bipedLeftLeg;
	private final ModelRenderer bipedRightLegwear;
	private final ModelRenderer bipedBody;
	private final ModelRenderer bipedRightArm;
	private final ModelRenderer bipedLeftArm;
	private final ModelRenderer bipedHead;
	private final ModelRenderer bipedHeadwear;

	private final ModelRenderer innerHead;
	private final ModelRenderer innerHead2;

	public ModelRiftedEnderman(float scale) {

		textureWidth = 64;
		textureHeight = 96;

		bipedRightLeg = new ModelRenderer(this);
		bipedRightLeg.setRotationPoint(2.0F, -3.0F, 0.0F);
		bipedRightLeg.cubeList.add(new ModelBox(bipedRightLeg, 56, 0, -1.0F, -3.0F, -1.0F, 2, 30, 2, 0.0F, true));

		bipedLeftLegwear = new ModelRenderer(this);
		bipedLeftLegwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightLeg.addChild(bipedLeftLegwear);
		bipedLeftLegwear.cubeList.add(new ModelBox(bipedLeftLegwear, 56, 32, -1.0F, -3.0F, -1.0F, 2, 30, 2, 0.25F, false));

		bipedLeftLeg = new ModelRenderer(this);
		bipedLeftLeg.setRotationPoint(-2.0F, -3.0F, 0.0F);
		bipedLeftLeg.cubeList.add(new ModelBox(bipedLeftLeg, 56, 0, -1.0F, -3.0F, -1.0F, 2, 30, 2, 0.0F, false));

		bipedRightLegwear = new ModelRenderer(this);
		bipedRightLegwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedLeftLeg.addChild(bipedRightLegwear);
		bipedRightLegwear.cubeList.add(new ModelBox(bipedRightLegwear, 56, 32, -1.0F, -3.0F, -1.0F, 2, 30, 2, 0.25F, true));

		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0F, -3.0F, 0.0F);
		bipedBody.cubeList.add(new ModelBox(bipedBody, 32, 16, -4.0F, -12.0F, -2.0F, 8, 12, 4, 0.0F, false));
		bipedBody.cubeList.add(new ModelBox(bipedBody, 32, 32, -4.0F, -12.0F, -2.0F, 8, 12, 4, 0.25F, false));
		bipedBody.cubeList.add(new ModelBox(bipedBody, 32, 48, -4.0F, -12.0F, -2.0F, 8, 12, 4, 0.75F, false));

		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(5.0F, -11.0F, 0.0F);
		bipedBody.addChild(bipedRightArm);
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 24, 76, -3.0F, 26.0F, -3.0F, 6, 6, 14, 0.0F, true));
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 1, 76, 0.0F, -6.0F, 0.0F, 12, 10, 0, 0.0F, false));
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 56, 0, -1.0F, -1.0F, -1.0F, 2, 30, 2, 0.0F, true));
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 56, 32, -1.0F, -1.0F, -1.0F, 2, 30, 2, 0.25F, false));
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 0, 75, 0.0F, 26.0F, 5.0F, 0, 9, 12, 0.0F, true));
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 36, 0, -1.0F, -3.0F, -2.0F, 4, 12, 4, 0.3F, false));

		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(-5.0F, -11.0F, 0.0F);
		bipedBody.addChild(bipedLeftArm);
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 36, 0, -3.0F, -4.0F, -2.0F, 4, 12, 4, 0.3F, true));
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 1, 76, -12.0F, -6.0F, 0.0F, 12, 10, 0, 0.0F, true));
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 56, 32, -1.0F, -1.0F, -1.0F, 2, 30, 2, 0.25F, true));
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 0, 75, 0.0F, 26.0F, 5.0F, 0, 9, 12, 0.0F, false));
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 56, 0, -1.0F, -1.0F, -1.0F, 2, 30, 2, 0.0F, false));
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 24, 76, -3.0F, 26.0F, -3.0F, 6, 6, 14, 0.0F, false));

		bipedHead = new ModelRenderer(this);
		bipedHead.setRotationPoint(0.0F, -12.0F, 0.0F);
		bipedBody.addChild(bipedHead);
		bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 48, -4.0F, -8.01F, -4.0F, 8, 8, 8, 0.26F, false));

		bipedHeadwear = new ModelRenderer(this);
		bipedHeadwear.setRotationPoint(0.0F, -10.0F, 0.0F);
		bipedHead.addChild(bipedHeadwear);
		bipedHeadwear.cubeList.add(new ModelBox(bipedHeadwear, 0, 32, -4.0F, 2.0F, -4.0F, 8, 8, 8, 0.5F, false));
		bipedHeadwear.cubeList.add(new ModelBox(bipedHeadwear, 0, 64, -4.0F, -3.0F, -4.0F, 8, 4, 8, 0.5F, false));

		innerHead = new ModelRenderer(this);
		innerHead.setRotationPoint(0.0F, 49.0F, 0.0F);
		bipedHeadwear.addChild(innerHead);
		innerHead.cubeList.add(new ModelBox(innerHead, 0, 0, -4.0F, -47.0F, -4.0F, 8, 8, 8, 0.25F, false));

		innerHead2 = new ModelRenderer(this);
		innerHead2.setRotationPoint(0.0F, 39.0F, 0.0F);
		bipedHead.addChild(innerHead2);
		innerHead2.cubeList.add(new ModelBox(innerHead2, 0, 16, -4.0F, -47.01F, -4.0F, 8, 8, 8, 0.0F, false));
	}


	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.bipedBody.render(scale);
		//this.bipedRightArm.render(scale);
		//this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	//	this.bipedHeadwear.render(scale);

	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount ;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;
		this.bipedHead.rotationPointY = -12F;
		this.bipedBody.rotateAngleY= 0F;
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointZ = 0.0F;
		this.bipedLeftLeg.rotationPointZ = 0.0F;
		this.bipedRightArm.rotationPointX=5F;
		this.bipedLeftArm.rotationPointX=-5F;

		this.bipedRightArm.rotateAngleY = 0F;
		this.bipedLeftArm.rotateAngleY = 0F;

		this.bipedHeadwear.rotationPointY=-10F;

		if (this.isRiding)
		{
			this.bipedRightArm.rotateAngleX -= ((float) Math.PI / 5F);
			this.bipedLeftArm.rotateAngleX -= ((float) Math.PI / 5F);
			this.bipedRightLeg.rotateAngleX = -1.4137167F;
			this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
			this.bipedRightLeg.rotateAngleZ = 0.07853982F;
			this.bipedLeftLeg.rotateAngleX = -1.4137167F;
			this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
			this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		}

		if (this.swingProgress > 0.0F)
		{
			EnumHandSide enumhandside = this.getMainHand(entityIn);
			ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT)
			{
				this.bipedBody.rotateAngleY *= -1.0F;
			}

			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float)Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}

		this.bipedHead.showModel = true;
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedBody.rotationPointY = -3.0F;
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



	//	this.bipedRightLeg.rotationPointY = -5.0F;
		//this.bipedLeftLeg.rotationPointY = -5.0F;
		this.bipedHead.rotationPointZ = -0.0F;
		//this.bipedHead.rotationPointY = -13.0F;

		if (this.isAttacking)
		{
			this.bipedHeadwear.rotationPointY = -15.0F;
		}

		if(entityIn instanceof EntityRiftedEnderman){
			int d = ((EntityRiftedEnderman) entityIn).getArmorValue();
			boolean b = d == 2;
			this.bipedLeftLegwear.showModel = b;
			this.bipedRightLegwear.showModel = b;
			if(((EntityRiftedEnderman) entityIn).isShockwaving()){
				int i = ((EntityRiftedEnderman) entityIn).getShockwaveTicks();
				int real_i= 20 - i;
				/*if(real_i < 5){ //20 - 16 (0-4)
					this.bipedBody.rotateAngleX = 0.11999F * real_i; //27.5
				}
				else if( real_i < 9 && i >= 5) { //16 - 12 (4 - 8)
					this.bipedBody.rotationPointX= (real_i * 2.5F + 37.5F) * (float) Math.PI / 180;
				}
				else if( real_i < 13 && i >= 9) { //12 - 8 (8 - 12)
					this.bipedBody.rotationPointX= -(real_i * 9.375F -57.5F) * (float) Math.PI / 180;
				}
				else if( real_i < 15 && i >= 13) { //8 - 6 (12 - 14)
					this.bipedBody.rotationPointX= -55F * (float) Math.PI / 180;
				}
				else if( real_i < 13 && i >= 0) { //6 - 0 (14 - 20)
					this.bipedBody.rotationPointX= -(i *-9.1666667F + 183.3333F) * (float) Math.PI / 180;
				}*/
				this.bipedBody.rotateAngleX= -(47.069F * MathHelper.sin(0.3177F * real_i + 0.1658F) - 11.4133F) *  (float) Math.PI / 180F;
				this.bipedHead.rotateAngleX= -(-0.7291666F * real_i * real_i + 14.5833F * real_i) * (float) Math.PI / 180F;
				this.bipedRightArm.rotateAngleX= -(float) (-0.015F * Math.pow(real_i, 4) + 0.8643F * Math.pow(real_i, 3) -17.0598F * Math.pow(real_i, 2) + 115.8845 * real_i - 1.528) *  (float) Math.PI / 180F;
				this.bipedLeftArm.rotateAngleX= this.bipedRightArm.rotateAngleX;

				this.bipedRightArm.rotateAngleY= -(float) (-0.0021F * Math.pow(real_i, 4) + 0.0575F * Math.pow(real_i, 3) -0.3297 * Math.pow(real_i, 2) + 0.2586 * real_i + 0.1752) *  (float) Math.PI / 180F;
				this.bipedLeftArm.rotateAngleY = -this.bipedRightArm.rotateAngleY;

				this.bipedRightArm.rotateAngleZ= -(float) (-0.00021F * Math.pow(real_i, 4) + 0.03981F * Math.pow(real_i, 3) -1.1393 * Math.pow(real_i, 2) + 8.6089 *real_i - 0.1919) *  (float) Math.PI / 180F;
				this.bipedLeftArm.rotateAngleZ = -this.bipedRightArm.rotateAngleZ;
			}
		}
	}


	protected ModelRenderer getArmForSide(EnumHandSide side)
	{
		return side == EnumHandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
	}

	protected EnumHandSide getMainHand(Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
			EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
			return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
		}
		else
		{
			return EnumHandSide.RIGHT;
		}
	}
}