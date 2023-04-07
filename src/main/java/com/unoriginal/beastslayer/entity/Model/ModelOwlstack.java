package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.1.


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelOwlstack extends ModelBase {
	private final ModelRenderer trunk;
	private final ModelRenderer tail;
	private final ModelRenderer leg1;
	private final ModelRenderer leg0;
	private final ModelRenderer wing2;
	private final ModelRenderer wing1;

	public ModelOwlstack() {
		textureWidth = 64;
		textureHeight = 64;


		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-2.5F, 20.0F, -1.5F);
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, -1.5F, 2.0F, -1.5F, 3, 2, 3, 0.0F, true));

		leg0 = new ModelRenderer(this);
		leg0.setRotationPoint(2.5F, 20.0F, -1.5F);
		leg0.cubeList.add(new ModelBox(leg0, 0, 0, -1.5F, 2.0F, -1.5F, 3, 2, 3, 0.0F, false));

		trunk = new ModelRenderer(this);
		trunk.setRotationPoint(0.0F, 22.0F, 0.0F);
		trunk.cubeList.add(new ModelBox(trunk, 0, 0, -6.0F, -11.0F, -7.0F, 12, 12, 12, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 49, 44, -0.5F, -5.0F, -10.0F, 1, 2, 1, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 0, 24, -6.0F, -15.0F, -7.0F, 12, 4, 12, 0.0F, false));
		trunk.cubeList.add(new ModelBox(trunk, 0, 40, -3.5F, -9.0F, -9.0F, 7, 6, 7, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 5.0F);
		trunk.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 36, 57, -4.0F, 0.0F, -2.0F, 8, 0, 7, 0.0F, false));

		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(6.0F, -5.0F, -1.5F);
		trunk.addChild(wing2);
		wing2.cubeList.add(new ModelBox(wing2, 36, 0, 0.0F, -1.0F, -3.5F, 5, 1, 7, 0.0F, false));

		wing1 = new ModelRenderer(this);
		wing1.setRotationPoint(-6.0F, -5.0F, -1.5F);
		trunk.addChild(wing1);
		wing1.cubeList.add(new ModelBox(wing1, 36, 0, -5.0F, -1.0F, -3.5F, 5, 1, 7, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leg1.render(f5);
		leg0.render(f5);
		trunk.render(f5);
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.trunk.rotateAngleX = headPitch * 0.01F;
		this.trunk.rotateAngleY = netHeadYaw * 0.01F;
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.tail.rotateAngleX = -0.4F + MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount +  MathHelper.cos(ageInTicks * 0.3F) * 0.02F;
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount;
		if(entityIn.onGround) {
			this.wing1.rotateAngleZ = -(float) Math.PI / 2F -  MathHelper.cos(ageInTicks * 0.3F) * 0.02F ;
			this.wing2.rotateAngleZ = (float) Math.PI / 2F +  MathHelper.cos(ageInTicks * 0.3F) * 0.02F;
			this.wing1.rotateAngleY = 0.0F + MathHelper.cos(ageInTicks * 0.3F) * 0.02F;
			this.wing2.rotateAngleY = 0.0F + MathHelper.cos(ageInTicks * 0.3F) * 0.02F;
		}
		else if (!entityIn.isBeingRidden() && !entityIn.isRiding()) {
			this.wing1.rotateAngleZ = MathHelper.cos(ageInTicks * 1.25F);
			this.wing2.rotateAngleZ = -MathHelper.cos(ageInTicks * 1.25F);
		}
	}
}