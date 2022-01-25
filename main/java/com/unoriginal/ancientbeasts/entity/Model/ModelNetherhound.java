package com.unoriginal.ancientbeasts.entity.Model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.ancientbeasts.entity.Entities.EntityNetherhound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelNetherhound extends ModelBase {
	private final ModelRenderer leg_fl;
	private final ModelRenderer leg_br;
	private final ModelRenderer leg_bl;
	private final ModelRenderer leg_fr;
	private final ModelRenderer mane;
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer head;

	public ModelNetherhound() {
		textureWidth = 80;
		textureHeight = 64;

		leg_fl = new ModelRenderer(this);
		leg_fl.setRotationPoint(2.5F, 15.0F, -5.5F);
		leg_fl.cubeList.add(new ModelBox(leg_fl, 0, 0, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, true));

		leg_br = new ModelRenderer(this);
		leg_br.setRotationPoint(-2.5F, 15.0F, 6.5F);
		leg_br.cubeList.add(new ModelBox(leg_br, 1, 47, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));

		leg_bl = new ModelRenderer(this);
		leg_bl.setRotationPoint(2.5F, 15.0F, 6.5F);
		leg_bl.cubeList.add(new ModelBox(leg_bl, 1, 47, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, true));

		leg_fr = new ModelRenderer(this);
		leg_fr.setRotationPoint(-2.5F, 15.0F, -5.5F);
		leg_fr.cubeList.add(new ModelBox(leg_fr, 0, 0, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));

		mane = new ModelRenderer(this);
		mane.setRotationPoint(0.0F, 11.0F, -8.0F);
		mane.cubeList.add(new ModelBox(mane, 0, 12, -6.0F, -7.0F, 0.0F, 12, 12, 8, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 1.0F, 8.0F);
		mane.addChild(body);
		body.cubeList.add(new ModelBox(body, 20, 38, -4.0F, -4.0F, 0.01F, 8, 8, 9, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -2.0F, 9.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 26, 18, -3.0F, -3.0F, 0.0F, 6, 6, 14, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 52, 26, -3.0F, -3.0F, 10.0F, 6, 6, 0, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 11.0F, -8.0F);
		head.cubeList.add(new ModelBox(head, 35, 10, -9.0F, -3.0F, -3.0F, 5, 4, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 35, 10, 4.0F, -3.0F, -3.0F, 5, 4, 3, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 36, 0, -2.0F, 1.0F, -10.0F, 4, 4, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 45, 42, -4.0F, -7.0F, -2.0F, 8, 3, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 32, -4.0F, -4.0F, -5.0F, 8, 7, 6, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 54, 9, -9.0F, -9.0F, -3.0F, 2, 6, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 54, 9, 7.0F, -9.0F, -3.0F, 2, 6, 3, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 5.0F * f5, 3.0F * f5);
			this.head.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
			this.leg_fl.render(f5);
			this.leg_fr.render(f5);
			this.leg_bl.render(f5);
			this.leg_br.render(f5);
			this.mane.render(f5);
			GlStateManager.popMatrix();
		}
		else
		{
			leg_fl.render(f5);
			leg_br.render(f5);
			leg_bl.render(f5);
			leg_fr.render(f5);
			mane.render(f5);
			head.render(f5);
		}
	}
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.leg_fl.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.leg_fr.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.leg_bl.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.leg_br.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.tail.rotateAngleY =  MathHelper.cos(limbSwing * 0.6662F) * -0.5F * limbSwingAmount;
		this.tail.rotateAngleX = -22.5F * (float) Math.PI / 180F;
		if(entityIn instanceof EntityNetherhound) {
			EntityNetherhound netherhound = (EntityNetherhound) entityIn;
			if (netherhound.isSprinting()) {
					this.tail.rotateAngleX = 22.5F * (float) Math.PI / 180F;
			}
			else if (netherhound.isSitting())
			{
				this.mane.rotateAngleX = -22.5F * (float)Math.PI / 180F;
				this.body.rotateAngleX = -22.5F * (float)Math.PI / 180F;
				this.tail.rotateAngleX = 40F * (float)Math.PI / 180F;
				this.leg_fl.rotateAngleX = -11.25F * (float)Math.PI / 180F;
				this.leg_fr.rotateAngleX = -11.25F * (float)Math.PI / 180F;
				this.leg_br.rotateAngleX = -(float)Math.PI / 2F;
				this.leg_bl.rotateAngleX = this.leg_br.rotateAngleX;
				this.leg_br.setRotationPoint(-2.5F, 23.0F, 3.5F);
				this.leg_bl.setRotationPoint(2.5F, 23.0F, 3.5F);
				this.leg_fl.setRotationPoint(2.51F, 15.0F, -5.5F);
				this.leg_fr.setRotationPoint(-2.51F, 15.0F, -5.5F);
			}
			else
			{
				this.mane.rotateAngleX = 0F;
				this.body.rotateAngleX = 0F;
				this.leg_bl.setRotationPoint(2.5F, 15.0F, 6.5F);
				this.leg_br.setRotationPoint(-2.5F, 15.0F, 6.5F);
				this.leg_fl.setRotationPoint(2.5F, 15.0F, -5.5F);
				this.leg_fr.setRotationPoint(-2.5F, 15.0F, -5.5F);
			}
		}
	}
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		EntityNetherhound entitywolf = (EntityNetherhound) entitylivingbaseIn;
		this.head.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, 0.0F);
		this.mane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
		this.body.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
		this.tail.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
		this.tail.rotateAngleX = 22.5F * (float) Math.PI / 180F;
	}
}