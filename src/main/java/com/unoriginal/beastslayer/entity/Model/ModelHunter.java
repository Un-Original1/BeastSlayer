package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHunter extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer head;
	private final ModelRenderer hood;
	private final ModelRenderer bone;
	private final ModelRenderer mask;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer cloak;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer leftFoot;
	private final ModelRenderer RightLeg;
	private final ModelRenderer rightFoot;

	public ModelHunter() {
		textureWidth = 80;
		textureHeight = 80;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 26, 12, -5.0F, -11.0F, -3.0F, 10, 6, 6, 0.1F, false));
		body.cubeList.add(new ModelBox(body, 16, 48, -3.0F, -3.0F, -2.0F, 6, 7, 4, 0.5F, false));
		body.cubeList.add(new ModelBox(body, 52, 11, -3.0F, -9.0F, -1.5F, 6, 3, 3, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 44, 39, -3.0F, -10.0F, -1.5F, 6, 11, 3, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 27, 29, -4.0F, -11.0F, -3.0F, 8, 5, 5, 0.25F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 1.5F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 0, 34, -1.0F, -8.0F, 0.0F, 2, 8, 8, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 17, 0, -2.0F, -8.0F, 7.0F, 4, 0, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 18, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 50, -2.0F, -8.0F, -6.0F, 4, 4, 4, 0.25F, false));

		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.cubeList.add(new ModelBox(hood, 0, 0, -4.0F, -8.0F, -4.0F, 8, 10, 8, 0.3F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -7.0F, 5.0F);
		hood.addChild(bone);
		setRotationAngle(bone, -0.7854F, 0.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 20, 39, -3.05F, -0.1743F, -1.1642F, 6, 3, 6, 0.25F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, -4.0F, -4.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 32, 0, -5.0F, -4.0F, -1.0F, 10, 9, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 0, 5.0F, -4.0F, 0.01F, 3, 8, 0, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 0, -8.0F, -4.0F, 0.01F, 3, 8, 0, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 12, 34, 2.0F, -8.0F, -1.0F, 3, 4, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 12, 34, -5.0F, -8.0F, -1.0F, 3, 4, 2, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(3.0F, -9.0F, 0.0F);
		body.addChild(LeftArm);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 50, 53, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-3.0F, -9.0F, 0.0F);
		body.addChild(RightArm);
		RightArm.cubeList.add(new ModelBox(RightArm, 50, 53, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));
		//RightArm.cubeList.add(new ModelExtTexture(RightArm, 0, 0, 16,16));

		cloak = new ModelRenderer(this);
		cloak.setRotationPoint(0.0F, -8.0F, 2.0F);
		body.addChild(cloak);
		cloak.cubeList.add(new ModelBox(cloak, 53, 24, -4.0F, -2.0F, 0.0F, 8, 10, 1, 0.0F, false));
		cloak.cubeList.add(new ModelBox(cloak, 32, 24, -4.0F, 8.0F, 0.0F, 8, 4, 1, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(3.0F, 15.0F, -1.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 36, 53, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, true));

		leftFoot = new ModelRenderer(this);
		leftFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		LeftLeg.addChild(leftFoot);
		leftFoot.cubeList.add(new ModelBox(leftFoot, 56, 0, -1.0F, -1.0F, 0.0F, 2, 7, 2, 0.0F, false));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-3.0F, 15.0F, -1.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 36, 53, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, false));

		rightFoot = new ModelRenderer(this);
		rightFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		RightLeg.addChild(rightFoot);
		rightFoot.cubeList.add(new ModelBox(rightFoot, 56, 0, -1.0F, -1.0F, 0.0F, 2, 7, 2, 0.0F, true));
	}


	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
	//	ModelHelper.renderRelative(body, Arrays.asList(LeftArm, RightArm), f5);
		LeftLeg.render(f5);
		RightLeg.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
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
		//t//his.art1.rotateAngleX= 115F * (float) Math.PI /180F;
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

		this.cloak.rotateAngleX = MathHelper.cos(0.6662F) * 1.6F * limbSwingAmount * 1F;
		if(entityIn instanceof AbstractTribesmen){
			AbstractTribesmen tribesmen = (AbstractTribesmen) entityIn;
			/** Fiery Logic starts here**/
			if( tribesmen.isFiery() && tribesmen.getFieryTicks() <= 0 ) {

				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				/*if (((AbstractTribesmen) entityIn).getAttackTarget() != null) { //why was this here? I'm dumb
					this.RightArm.rotateAngleX = -(float) Math.PI / 1.25F;
					this.LeftArm.rotateAngleX = -(float) Math.PI / 1.25F;
				}*/

				ItemStack itemstack = ( tribesmen.getHeldItem(EnumHand.MAIN_HAND));
				if(entityIn instanceof EntityHunter) {
					if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && (((EntityHunter) entityIn)).isSwingingArms()) {
						this.RightArm.rotateAngleX -= 0F -MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.LeftArm.rotateAngleX -=  0F -MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.RightArm.rotateAngleZ -= 0F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.LeftArm.rotateAngleZ -=0F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.body.rotateAngleX = 0F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
						this.head.rotateAngleX -= 0F ;
					}
					else {
						this.RightArm.rotateAngleX -= 36.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.LeftArm.rotateAngleX -= 36.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.RightArm.rotateAngleZ -= -17.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.LeftArm.rotateAngleZ -= 17.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
						this.body.rotateAngleX = 36.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
						this.head.rotateAngleX -= 36.5F * (float) Math.PI / 180F;
					}
				}
			}
			//fiery transformation animation
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
			/** Fiery logic ends here **/

			else if (!tribesmen.isFiery()) {
				this.mask.rotateAngleZ = MathHelper.sin(ageInTicks * 0.067F) * 0.025F;
				if(tribesmen.isTrading()){
					if(EnumHandSide.LEFT == tribesmen.getPrimaryHand()){
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
			ItemStack itemstack = ((AbstractTribesmen)entityIn).getHeldItem(EnumHand.MAIN_HAND);
			if(entityIn instanceof EntityHunter) {
				if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && (((EntityHunter) entityIn)).isSwingingArms()) {
					if (((EntityHunter)entityIn).getPrimaryHand() == EnumHandSide.RIGHT) {
						this.RightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
						this.LeftArm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
						this.RightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
						this.LeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
					} else {
						this.RightArm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
						this.LeftArm.rotateAngleY = 0.1F + this.head.rotateAngleY;
						this.RightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
						this.LeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
					}
				}
				else if (((EntityHunter)entityIn).isSwingingArms() && (itemstack.isEmpty() || !(itemstack.getItem() instanceof net.minecraft.item.ItemBow)))
				{
					float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
					float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
					this.RightArm.rotateAngleZ = 0.0F;
					this.LeftArm.rotateAngleZ = 0.0F;
					this.RightArm.rotateAngleY = -(0.1F - f * 0.6F);
					this.LeftArm.rotateAngleY = 0.1F - f * 0.6F;
					this.RightArm.rotateAngleX = -((float)Math.PI / 2F);
					this.LeftArm.rotateAngleX = -((float)Math.PI / 2F);
					this.RightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
					this.LeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
					this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
					this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
					this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
					this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
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