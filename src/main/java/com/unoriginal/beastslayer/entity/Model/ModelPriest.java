package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class ModelPriest extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer bone3;
	private final ModelRenderer tail;
	private final ModelRenderer art1;
	private final ModelRenderer point;
	private final ModelRenderer head;
	private final ModelRenderer horn0;
	private final ModelRenderer horn1;
	private final ModelRenderer mask;
	private final ModelRenderer bone;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer thingr;
	private final ModelRenderer thingl;
	private final ModelRenderer robes;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;

	public ModelPriest() {
		textureWidth = 80;
		textureHeight = 80;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 27, 42, -5.0F, -10.0F, -2.0F, 10, 4, 7, 0.3F, false));
		body.cubeList.add(new ModelBox(body, 0, 64, -3.0F, -9.0F, -1.5F, 6, 9, 3, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 52, 53, -3.0F, -9.0F, -1.5F, 6, 9, 3, 0.25F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 9.0F, 0.0F);
		body.addChild(bone3);
		bone3.cubeList.add(new ModelBox(bone3, 35, 31, -7.0F, -19.0F, -2.0F, 14, 4, 7, 0.0F, false));
		bone3.cubeList.add(new ModelBox(bone3, 0, 38, -5.0F, -19.0F, -2.0F, 10, 4, 7, 0.25F, false));
		bone3.cubeList.add(new ModelBox(bone3, 0, 21, -7.0F, -15.0F, -2.0F, 14, 10, 7, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 2.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 42, 22, 0.0F, 0.0F, -1.0F, 0, 1, 8, 0.0F, false));

		art1 = new ModelRenderer(this);
		art1.setRotationPoint(0.0F, 0.5F, 6.5F);
		tail.addChild(art1);
		art1.cubeList.add(new ModelBox(art1, 42, 21, -0.001F, -0.5F, -0.5F, 0, 1, 8, 0.0F, false));

		point = new ModelRenderer(this);
		point.setRotationPoint(0.0F, 0.0F, 7.5F);
		art1.addChild(point);
		point.cubeList.add(new ModelBox(point, 55, 47, -2.0F, 0.0F, -1.0F, 4, 0, 6, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -9.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 49, -4.0F, -8.0F, -3.0F, 8, 8, 7, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 42, 14, -4.0F, -8.0F, -3.0F, 8, 8, 7, 0.25F, false));
		head.cubeList.add(new ModelBox(head, 65, 11, 4.0F, -3.0F, -2.0F, 4, 2, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 65, 11, -8.0F, -3.0F, -2.0F, 4, 2, 3, 0.0F, true));

		horn0 = new ModelRenderer(this);
		horn0.setRotationPoint(-2.5F, -4.0F, -0.5F);
		head.addChild(horn0);
		setRotationAngle(horn0, 0.0F, 0.0F, 0.0F);
		horn0.cubeList.add(new ModelBox(horn0, 18, 64, -4.5F, -10.0F, -1.5F, 2, 10, 3, 0.0F, false));
		horn0.cubeList.add(new ModelBox(horn0, 0, 24, -4.5F, -10.0F, 1.5F, 2, 1, 1, 0.0F, true));
		horn0.cubeList.add(new ModelBox(horn0, 50, 65, -2.5F, -3.0F, -1.5F, 1, 3, 3, 0.0F, false));

		horn1 = new ModelRenderer(this);
		horn1.setRotationPoint(2.5F, -4.0F, -0.5F);
		head.addChild(horn1);
		setRotationAngle(horn1, 0.0F, 0.0F, 0.0F);
		horn1.cubeList.add(new ModelBox(horn1, 50, 65, 1.5F, -3.0F, -1.5F, 1, 3, 3, 0.0F, true));
		horn1.cubeList.add(new ModelBox(horn1, 18, 64, 2.5F, -10.0F, -1.5F, 2, 10, 3, 0.0F, true));
		horn1.cubeList.add(new ModelBox(horn1, 0, 24, 2.5F, -10.0F, 1.5F, 2, 1, 1, 0.0F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, -4.0F, -3.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 30, 53, -5.0F, -6.0F, -1.001F, 10, 10, 1, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 0, -12.0F, -15.0F, -0.01F, 24, 21, 0, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 21, -1.0F, -6.0F, -1.0F, 2, 2, 1, 0.0F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -7.0F, 6.0F);
		head.addChild(bone);
		setRotationAngle(bone, -0.7418F, 0.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 54, 42, -4.0F, 0.5007F, -1.8947F, 8, 2, 3, 0.24F, false));

		LeftArm = new ModelRenderer(this);
		body.addChild(LeftArm);
		LeftArm.setRotationPoint(3.0F, -8.0F, 0.0F);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 28, 64, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));

		RightArm = new ModelRenderer(this);
		body.addChild(RightArm);
		RightArm.setRotationPoint(-3.0F, -8.0F, 0.0F);
		RightArm.cubeList.add(new ModelBox(RightArm, 28, 64, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));

		thingr = new ModelRenderer(this);
		thingr.setRotationPoint(-3.5F, -6.0436F, -2.0019F);
		body.addChild(thingr);
		setRotationAngle(thingr, -0.0436F, 0.0F, 0.0F);
		thingr.cubeList.add(new ModelBox(thingr, 44, 64, -1.75F, 0.2936F, -0.2481F, 3, 12, 0, 0.0F, true));

		thingl = new ModelRenderer(this);
		thingl.setRotationPoint(3.5F, -6.0F, -2.0F);
		body.addChild(thingl);
		thingl.cubeList.add(new ModelBox(thingl, 44, 64, -1.25F, 0.25F, -0.25F, 3, 12, 0, 0.0F, false));

		robes = new ModelRenderer(this);
		robes.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(robes);
		robes.cubeList.add(new ModelBox(robes, 48, 0, -3.0F, -1.0F, -2.0F, 6, 10, 4, 0.4F, false));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-2.0F, 15.0F, 0.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 36, 64, -1.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(2.0F, 15.0F, 0.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 36, 64, -1.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));
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
		this.head.rotateAngleZ = 0F;
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
		this.tail.rotateAngleX= -65.0F * (float) Math.PI /180F;
		this.art1.rotateAngleX= 115F * (float) Math.PI /180F;
		this.point.rotateAngleX = -55F * (float) Math.PI / 180F;
		this.tail.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.art1.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.point.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleY = 0F;
		this.tail.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

		this.body.rotateAngleY= 0.0F;
		this.body.rotateAngleX= 0.0F;
		this.RightArm.rotateAngleY = 0F;
		this.LeftArm.rotateAngleY = 0F;

		if(entityIn instanceof AbstractTribesmen){
			AbstractTribesmen tribesmen = (AbstractTribesmen) entityIn;
			if(tribesmen.isFiery() && tribesmen.getFieryTicks() <= 0 ) {
				this.body.rotateAngleX = 22.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
				this.head.rotateAngleX -= 22.5F * (float) Math.PI / 180F;
				this.head.rotateAngleZ -= 22.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleX -= 22.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleX -= 22.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				if (tribesmen.getAttackTarget() != null) {
					this.RightArm.rotateAngleX = -(float) Math.PI / 1.25F;
					this.LeftArm.rotateAngleX = -(float) Math.PI / 1.25F;
				}
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
					if(tribesmen.getPrimaryHand() == EnumHandSide.LEFT){
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
				if(entityIn instanceof EntityPriest){
					EntityPriest p = (EntityPriest) entityIn;
					if(p.isCastingMagic()){
						if(!p.isFiery()) {
							if (EnumHandSide.LEFT == tribesmen.getPrimaryHand()) {
								this.LeftArm.rotateAngleX = -125.5F * (float) Math.PI / 180F;
								this.LeftArm.rotateAngleY = -32.5F * (float) Math.PI / 180F;
							} else if (EnumHandSide.RIGHT == tribesmen.getPrimaryHand()) {
								this.RightArm.rotateAngleX = -125.5F * (float) Math.PI / 180F;
								this.RightArm.rotateAngleY = 32.5F * (float) Math.PI / 180F;
							}
						} else {
							this.RightArm.rotationPointZ = 0.0F;
							this.RightArm.rotationPointX = -5.0F;
							this.LeftArm.rotationPointZ = 0.0F;
							this.LeftArm.rotationPointX = 5.0F;
							this.RightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
							this.LeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
							this.RightArm.rotateAngleZ = 2.3561945F;
							this.LeftArm.rotateAngleZ = -2.3561945F;
							this.RightArm.rotateAngleY = 0.0F;
							this.LeftArm.rotateAngleY = 0.0F;
						}
					} else{
						if(!p.isFiery() && tribesmen.hasItemInSlot(EntityEquipmentSlot.MAINHAND)) {
							if (EnumHandSide.LEFT == tribesmen.getPrimaryHand()) {
								this.LeftArm.rotateAngleX += -62.5F * (float) Math.PI / 180F;
								this.LeftArm.rotateAngleY += -30F * (float) Math.PI / 180F;
							} else if (EnumHandSide.RIGHT == tribesmen.getPrimaryHand()) {
								this.RightArm.rotateAngleX += -62.5F * (float) Math.PI / 180F;
								this.RightArm.rotateAngleY += 30F * (float) Math.PI / 180F;
							}
						}
					}
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