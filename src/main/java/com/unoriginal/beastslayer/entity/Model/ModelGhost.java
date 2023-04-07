package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGhost extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer hood;
	private final ModelRenderer body;
	private final ModelRenderer bodylower;
	private final ModelRenderer armLeft;
	private final ModelRenderer armRight;

	public ModelGhost() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 2.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 32, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 32, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F, false));

		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, -5.0F, 4.0F);
		head.addChild(hood);
		setRotationAngle(hood, -0.7854F, 0.0F, 0.0F);
		hood.cubeList.add(new ModelBox(hood, 0, 48, -4.0F, -2.3F, -2.1F, 8, 4, 4, 0.1F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 2.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 36, 0, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, false));

		bodylower = new ModelRenderer(this);
		bodylower.setRotationPoint(0.0F, 12.0F, -2.0F);
		body.addChild(bodylower);
		bodylower.cubeList.add(new ModelBox(bodylower, 24, 17, -4.0F, 0.0F, 0.0F, 8, 10, 4, 0.1F, false));

		armLeft = new ModelRenderer(this);
		armLeft.setRotationPoint(4.0F, 3.0F, 0.0F);
		armLeft.cubeList.add(new ModelBox(armLeft, 0, 21, 0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F, false));
		armLeft.cubeList.add(new ModelBox(armLeft, 0, 0, -1.0F, -1.0F, -1.0F, 4, 11, 10, 0.25F, true));

		armRight = new ModelRenderer(this);
		armRight.setRotationPoint(-4.0F, 3.0F, 0.0F);
		setRotationAngle(armRight, 0.0F, 0.0F, 0.0F);
		armRight.cubeList.add(new ModelBox(armRight, 0, 0, -3.0F, -1.0F, -1.0F, 4, 11, 10, 0.25F, false));
		armRight.cubeList.add(new ModelBox(armRight, 0, 21, -2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		head.render(f5);
		body.render(f5);
		armLeft.render(f5);
		armRight.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		float f = -(float)Math.PI / 1.9F;
		this.armRight.rotateAngleX= f + MathHelper.sin(ageInTicks * 0.067F) * 0.07F;
		this.armLeft.rotateAngleX = f - MathHelper.sin(ageInTicks * 0.067F) * 0.07F;
		this.armRight.rotateAngleZ = MathHelper.cos(ageInTicks * 0.09F) * 0.07F;
		this.armLeft.rotateAngleZ = -this.armRight.rotateAngleZ;
		this.armRight.rotateAngleY = 0.65F;
		this.armLeft.rotateAngleY = -this.armRight.rotateAngleY;
		this.body.rotateAngleX = 0.5F;
		this.bodylower.rotateAngleX = 0.15F + MathHelper.cos(ageInTicks * 0.09F) * 0.2F;
	}
}