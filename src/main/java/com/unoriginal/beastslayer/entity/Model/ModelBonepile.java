package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityBonepile;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBonepile extends ModelBase {
	private final ModelRenderer leg_left;
	private final ModelRenderer leg_right;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer headup;
	private final ModelRenderer head2;
	private final ModelRenderer thing;
	private final ModelRenderer arm_right;
	private final ModelRenderer arm_left;
	private final ModelRenderer thingy;
	private final ModelRenderer chain;

	public ModelBonepile() {
		textureWidth = 64;
		textureHeight = 64;

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(3.0F, 11.0F, 0.0F);
		leg_left.cubeList.add(new ModelBox(leg_left, 14, 36, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, true));

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(-3.0F, 11.0F, 0.0F);
		leg_right.cubeList.add(new ModelBox(leg_right, 14, 36, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 11.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -5.0F, -12.0F, -3.0F, 10, 12, 6, 0.0F, false));

		thingy = new ModelRenderer(this);
		thingy.setRotationPoint(-4.0F, -12.0F, 3.0F);
		body.addChild(thingy);
		setRotationAngle(thingy, 0.2182F, 0.0F, -0.1309F);
		thingy.cubeList.add(new ModelBox(thingy, 24, 12, -5.0F, -1.0F, -8.0F, 10, 3, 10, 0.0F, false));

		chain = new ModelRenderer(this);
		chain.setRotationPoint(0.0F, 1.0F, -8.0F);
		thingy.addChild(chain);
		setRotationAngle(chain, 0.9599F, 0.0F, 0.1745F);
		chain.cubeList.add(new ModelBox(chain, 41, 2, -1.5F, 0.0F, -6.0F, 3, 0, 6, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(-4.0F, -12.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 32, 50, -4.0F, -6.0F, -4.0F, 8, 6, 8, 0.0F, false));

		headup = new ModelRenderer(this);
		headup.setRotationPoint(0.0F, -3.0F, 3.0F);
		head.addChild(headup);
		headup.cubeList.add(new ModelBox(headup, 32, 0, -7.0F, -10.0F, -4.0F, 2, 6, 2, 0.0F, false));
		headup.cubeList.add(new ModelBox(headup, 0, 19, -4.0F, -7.0F, -7.0F, 8, 7, 8, 0.1F, false));
		headup.cubeList.add(new ModelBox(headup, 38, 7, -5.0F, -6.0F, -4.0F, 1, 2, 2, 0.0F, false));
		headup.cubeList.add(new ModelBox(headup, 24, 35, 4.0F, -5.0F, -4.0F, 2, 2, 2, 0.0F, false));

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(4.0F, -12.0F, 0.0F);
		body.addChild(head2);
		head2.cubeList.add(new ModelBox(head2, 32, 50, -4.0F, -6.0F, -4.0F, 8, 6, 8, 0.0F, false));

		thing = new ModelRenderer(this);
		thing.setRotationPoint(0.0F, -3.0F, 3.0F);
		head2.addChild(thing);
		thing.cubeList.add(new ModelBox(thing, 0, 19, 5.0F, -8.0F, -4.0F, 2, 5, 2, 0.0F, false));
		thing.cubeList.add(new ModelBox(thing, 0, 19, -4.0F, -7.0F, -7.0F, 8, 7, 8, 0.11F, false));
		thing.cubeList.add(new ModelBox(thing, 38, 7, 4.0F, -5.0F, -4.0F, 1, 2, 2, 0.0F, false));
		thing.cubeList.add(new ModelBox(thing, 0, 0, -5.0F, -4.0F, -4.0F, 1, 2, 2, 0.0F, true));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-5.0F, -10.0F, 0.0F);
		body.addChild(arm_right);
		arm_right.cubeList.add(new ModelBox(arm_right, 34, 28, -2.001F, -2.0F, -1.0F, 2, 13, 2, 0.0F, false));
		arm_right.cubeList.add(new ModelBox(arm_right, 43, 28, -2.001F, 6.0F, -1.0F, 2, 3, 2, 0.25F, false));

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(5.0F, -10.0F, 0.0F);
		body.addChild(arm_left);
		arm_left.cubeList.add(new ModelBox(arm_left, 43, 28, -0.001F, 6.0F, -1.0F, 2, 3, 2, 0.25F, false));
		arm_left.cubeList.add(new ModelBox(arm_left, 34, 28, 0.001F, -2.0F, -1.0F, 2, 13, 2, 0.0F, true));
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leg_left.render(f5);
		leg_right.render(f5);
		body.render(f5);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		float f = -22.5F * (float) Math.PI / 180F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = entityIn instanceof EntityBonepile && ((EntityBonepile) entityIn).isSpook() ? f : 0 + headPitch * 0.017453292F;
		this.chain.rotateAngleX = 0.9599F + MathHelper.cos(ageInTicks * 0.05F * (float) Math.PI) * 0.025F;


		this.body.rotateAngleY = 0.0F;


		this.arm_right.rotateAngleX = - 22.5F * (float) Math.PI / 180F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.arm_left.rotateAngleX =  - 22.5F * (float) Math.PI / 180F + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.arm_left.rotateAngleZ = 0.0F;
		this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.leg_right.rotateAngleY = 0.0F;
		this.leg_left.rotateAngleY = 0.0F;
		this.leg_right.rotateAngleZ = 0.0F;
		this.leg_left.rotateAngleZ = 0.0F;

		if (this.isRiding)
		{
			this.arm_right.rotateAngleX += -((float)Math.PI / 5F);
			this.arm_left.rotateAngleX += -((float)Math.PI / 5F);
			this.leg_right.rotateAngleX = -1.4137167F;
			this.leg_right.rotateAngleY = ((float)Math.PI / 10F);
			this.leg_right.rotateAngleZ = 0.07853982F;
			this.leg_left.rotateAngleX = -1.4137167F;
			this.leg_left.rotateAngleY = -((float)Math.PI / 10F);
			this.leg_left.rotateAngleZ = -0.07853982F;
		}

		this.arm_right.rotateAngleY = 0.0F;
		this.arm_right.rotateAngleZ = 0.0F;



		this.body.rotateAngleX = 22.5F * (float) Math.PI / 180F + MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;


		this.arm_right.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.arm_left.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.arm_right.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.arm_left.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;


		if(entityIn instanceof EntityBonepile){
			EntityBonepile bonepile = (EntityBonepile) entityIn;
			if (bonepile.isSpook()){
				this.thing.rotationPointY = -3F + (float) Math.sin(ageInTicks / 0.8F);
				this.headup.rotationPointY = -3F + (float) -Math.sin(ageInTicks);
				this.head.rotationPointY = -12F +   (float)Math.sin(ageInTicks /2F) / 2F;
				this.head2.rotationPointY = -12F +   (float)Math.cos(ageInTicks /2F) / 2F;
			} else {
				this.thing.rotationPointY = -1F;
				this.headup.rotationPointY = -1F;
			}

			if(bonepile.getChainTicks() > 0){
				int i = bonepile.getChainTicks();
			//	body.rotateAngleX = -45F * (float)Math.PI / 180F;
				this.body.rotateAngleX = ((float)Math.cos(i / 6.35F) * 45F) * (float) Math.PI / 180F ;
				this.arm_right.rotateAngleX = (-(float)Math.cos(i / 6.35F) * 27.5F + 27.5F) * (float) Math.PI / 180F;
				this.arm_left.rotateAngleX = (-(float)Math.cos(i / 6.35F) * 30F + 30F) * (float) Math.PI / 180F;
				this.head.rotateAngleX = 14F * (float) Math.PI / 180F;
				this.head2.rotateAngleX = -22F * (float) Math.PI / 180F;
			}

			this.head2.rotateAngleY = head.rotateAngleY - 0.35F;
			this.head2.rotateAngleX = entityIn instanceof EntityBonepile && ((EntityBonepile) entityIn).isSpook() ? f : 0 + headPitch * 0.017453292F;
		}
	}
}