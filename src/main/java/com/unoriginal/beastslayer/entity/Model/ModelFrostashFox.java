package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 3.9.3

import com.unoriginal.beastslayer.entity.Entities.EntityFrostashFox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFrostashFox extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer spikes;
	private final ModelRenderer bone2;
	private final ModelRenderer bone;
	private final ModelRenderer legFrontRight;
	private final ModelRenderer legFrontLeft;
	private final ModelRenderer legBackRight;
	private final ModelRenderer legBackLeft;

	public ModelFrostashFox() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 11.0F, -6.0F);
		head.cubeList.add(new ModelBox(head, 38, 27, -4.0F, -4.0F, -5.0F, 8, 6, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 25, 24, 1.0F, -8.0F, -3.0F, 3, 4, 1, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 6, 24, -4.0F, -8.0F, -3.0F, 3, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 6, 29, -6.0F, -1.0F, -4.0F, 2, 3, 0, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 25, 29, 4.0F, -1.0F, -4.0F, 2, 3, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 38, 38, -2.0F, -1.0F, -8.0F, 4, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 36, 2, -7.0F, -10.0F, -5.1F, 14, 14, 0, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 11.0F, -6.0F);
		body.cubeList.add(new ModelBox(body, 0, 47, -3.0F, -1.0F, 0.0F, 6, 6, 11, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 15, 38, 0.0F, 0.0F, -2.0F, 0, 5, 2, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 1.0F, 11.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 6, 25, -2.0F, -2.0F, 0.0F, 4, 5, 9, 0.0F, false));

		spikes = new ModelRenderer(this);
		spikes.setRotationPoint(0.0F, 11.0F, -6.0F);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, -1.6F, 6.0F);
		spikes.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.7854F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 4, 1, -8.0F, -6.5F, 0.0F, 16, 13, 0, 0.0F, true));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -1.6F, 6.0F);
		spikes.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 4, 1, -8.0F, -6.5F, 0.0F, 16, 13, 0, 0.0F, true));

		legFrontRight = new ModelRenderer(this);
		legFrontRight.setRotationPoint(-2.0F, 16.0F, -5.0F);
		legFrontRight.cubeList.add(new ModelBox(legFrontRight, 38, 54, -1.001F, 0.0F, -1.001F, 2, 8, 2, 0.0F, false));

		legFrontLeft = new ModelRenderer(this);
		legFrontLeft.setRotationPoint(2.0F, 16.0F, -5.0F);
		legFrontLeft.cubeList.add(new ModelBox(legFrontLeft, 49, 44, -0.999F, 0.0F, -1.001F, 2, 8, 2, 0.0F, false));

		legBackRight = new ModelRenderer(this);
		legBackRight.setRotationPoint(-2.0F, 16.0F, 3.0F);
		legBackRight.cubeList.add(new ModelBox(legBackRight, 38, 54, -1.001F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		legBackLeft = new ModelRenderer(this);
		legBackLeft.setRotationPoint(2.0F, 16.0F, 3.0F);
		legBackLeft.cubeList.add(new ModelBox(legBackLeft, 49, 54, -0.999F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
			this.head.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
			this.body.render(f5);
			this.spikes.render(f5);
			if(entity instanceof EntityFrostashFox) {
				EntityFrostashFox fox = (EntityFrostashFox)entity;
				if(!fox.isSleeping()) {
					this.legBackRight.render(f5);
					this.legBackLeft.render(f5);
					this.legFrontRight.render(f5);
					this.legFrontLeft.render(f5);
				}
			}
			GlStateManager.popMatrix();
		}
		else {
			head.render(f5);
			body.render(f5);
			if(entity instanceof EntityFrostashFox) {
				EntityFrostashFox fox = (EntityFrostashFox)entity;
				if(fox.spikeTimer <= 0) {
					spikes.render(f5);
				}
				if(!fox.isSleeping()){
					legFrontRight.render(f5);
					legFrontLeft.render(f5);
					legBackRight.render(f5);
					legBackLeft.render(f5);
				}
			}
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.tail.rotateAngleX = 2F * (float)Math.PI / 1.025F;
		this.tail.rotateAngleY =  MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
		this.head.rotationPointY = 11F;
		this.head.rotationPointX = 0F;
		this.body.rotationPointY = 11F;
		this.spikes.rotationPointY = 11F;
		this.body.rotateAngleZ = 0F;
		this.spikes.rotateAngleZ = 0F;
		this.tail.rotationPointY = 1F;

		this.legBackLeft.rotationPointY = 16F;
		this.legBackRight.rotationPointY = 16F;
		this.legFrontLeft.rotationPointY = 16F;
		this.legFrontRight.rotationPointY = 16F;
		this.body.rotateAngleX = 0F;
		this.spikes.rotateAngleX = 0F;
		if(this.isChild)
		{
			this.head.rotationPointZ = -5.0F;
		} else {
			this.head.rotationPointZ = -6.0F;
		}
		if(entityIn instanceof EntityFrostashFox)
		{
			EntityFrostashFox fox = (EntityFrostashFox) entityIn;
			if(fox.isSleeping()){
				this.head.rotationPointZ = -8F;
				this.head.rotateAngleX = (float) Math.PI / 18F;
				this.head.rotationPointY = 21F;
				this.head.rotationPointX = 2F;
				this.body.rotationPointY = 21F;
				this.spikes.rotationPointY = 21F;
				this.body.rotateAngleZ = 3F * (float) Math.PI / 2F;
				this.spikes.rotateAngleZ = 3F * (float) Math.PI / 2F;
				this.head.rotateAngleY = 5F * -(float) Math.PI / 9F;
				this.tail.rotateAngleX = -(float) Math.PI / 1.25F;
				this.tail.rotateAngleY = (float) Math.PI / 30;
				this.tail.rotationPointY = 3F;
				this.body.rotateAngleX = 0F;
				this.spikes.rotateAngleX = 0F;
				if(fox.isChild()){
					head.rotationPointY = 16F;
				}
			}
			if(fox.isSitting()){
				this.body.rotateAngleX = -(float) Math.PI / 4F;
				this.spikes.rotateAngleX = this.body.rotateAngleX;
				this.legFrontRight.rotateAngleX = -(float) Math.PI * 0.125F;
				this.legFrontLeft.rotateAngleX = this.legFrontRight.rotateAngleX;
				this.legBackRight.rotateAngleX = 62.5F * -(float) Math.PI / 180F;
				this.legBackLeft.rotateAngleX = this.legBackRight.rotateAngleX;
				this.tail.rotateAngleX = 32.5F * (float) Math.PI / 180F;
				this.legBackLeft.rotationPointY = 22F;
				this.legBackRight.rotationPointY = this.legBackLeft.rotationPointY;
				this.legFrontLeft.rotationPointY = 19F;
				this.legFrontRight.rotationPointY = this.legFrontLeft.rotationPointY;
				this.body.rotationPointY = 14F;
				this.spikes.rotationPointY = 14F;
				this.head.rotationPointY = 14F;
			}
		}
	}
}