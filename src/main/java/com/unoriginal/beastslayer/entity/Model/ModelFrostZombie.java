package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityFrostWalker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFrostZombie extends ModelBase {
	private final ModelRenderer leg_left;
	private final ModelRenderer body;
	private final ModelRenderer arm_left;
	private final ModelRenderer bone2;
	private final ModelRenderer arm_right;
	private final ModelRenderer bone6;
	private final ModelRenderer bone5;
	private final ModelRenderer head;
	private final ModelRenderer bone;
	private final ModelRenderer bone3;
	private final ModelRenderer leg_right;
	private State state = State.NORMAL;

	public ModelFrostZombie() {
		textureWidth = 64;
		textureHeight = 96;

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(2.0F, 12.0F, 0.0F);
		leg_left.cubeList.add(new ModelBox(leg_left, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 12.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 16, -4.0F, -12.0F, -2.0F, 8, 12, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 16, 32, -4.0F, -12.0F, -2.0F, 8, 12, 4, 0.25F, false));

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(4.0F, -10.0F, 0.0F);
		body.addChild(arm_left);
		arm_left.cubeList.add(new ModelBox(arm_left, 40, 16, 0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));
		arm_left.cubeList.add(new ModelBox(arm_left, 40, 32, 0.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(4.5F, 3.5F, 3.0F);
		arm_left.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 2.3562F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 20, 58, -1.5F, -2.5F, 0.0F, 3, 5, 0, 0.0F, false));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-4.0F, -10.0F, 0.0F);
		body.addChild(arm_right);
		arm_right.cubeList.add(new ModelBox(arm_right, 0, 74, -6.0F, -2.0F, -3.0F, 6, 16, 6, 0.0F, false));

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(-3.0F, -4.0F, 0.0F);
		arm_right.addChild(bone6);
		setRotationAngle(bone6, 0.0F, -0.7854F, 0.0F);
		bone6.cubeList.add(new ModelBox(bone6, 32, 80, -9.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F, false));

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(-3.0F, 2.0F, 0.0F);
		arm_right.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.7854F, 0.0F);
		bone5.cubeList.add(new ModelBox(bone5, 36, 67, -7.0F, -6.0F, 0.0F, 14, 12, 0, 0.0F, true));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -8.0F, 0.0F);
		head.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 48, -12.0F, -8.0F, 0.0F, 20, 16, 0, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-5.0F, -5.0F, 5.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.7854F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 56, 20, -2.0F, -5.0F, 0.0F, 4, 10, 0, 0.0F, false));

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(-2.0F, 12.0F, 0.0F);
		leg_right.cubeList.add(new ModelBox(leg_right, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
		leg_right.cubeList.add(new ModelBox(leg_right, 0, 32, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leg_left.render(f5);
		body.render(f5);
		leg_right.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		if(this.state == State.NORMAL) {
			boolean flag = entityIn instanceof EntityFrostWalker && ((EntityFrostWalker) entityIn).isArmsRaised();
			this.head.rotateAngleY = netHeadYaw * 0.017453292F;
			this.head.rotateAngleX = -(float) Math.PI / 18F + headPitch * 0.017453292F;
			this.arm_right.rotateAngleZ = (float) Math.PI / 12F;
			this.arm_left.rotateAngleZ = 0F;
			this.arm_right.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.arm_left.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.body.rotateAngleZ = 2.5F * -(float) Math.PI / 36F;
			this.body.rotateAngleX = 2.5F * (float) Math.PI / 36F;
			this.arm_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
			this.arm_left.rotateAngleX = -(float) Math.PI / (flag ? 1.5F : 2.25F);
			this.arm_right.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			this.arm_left.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			this.head.rotateAngleZ = 2.5F * (float) Math.PI / 36F;
		}
		if (this.isRiding)
		{
			this.arm_right.rotateAngleX += -((float)Math.PI / 5F);
			this.arm_left.rotateAngleX += -((float)Math.PI / 5F);
			this.leg_right.rotateAngleY = ((float)Math.PI / 10F);
			this.leg_right.rotateAngleZ = 0.07853982F;
			this.leg_left.rotateAngleY = -((float)Math.PI / 10F);
			this.leg_left.rotateAngleZ = -0.07853982F;
		}
		this.leg_left.rotateAngleX = this.isRiding ?  -1.4137167F : MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg_right.rotateAngleX = this.isRiding ?  -1.4137167F : MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

		if(entityIn instanceof EntityFrostWalker){
			EntityFrostWalker jackFrost = (EntityFrostWalker) entityIn;
			if(jackFrost.isCastingMagic())
			{
				this.body.rotateAngleX = -4.5F * (float) Math.PI / 36F + ageInTicks * 0.6F;
				this.body.rotateAngleZ = 0.0F;
				this.arm_right.rotateAngleZ = (float) Math.PI / 18F;
				this.arm_left.rotateAngleZ = -this.arm_right.rotateAngleZ;
				this.arm_right.rotateAngleX = 31.5F * (float) Math.PI / 36F + ageInTicks * 0.05F;
				this.arm_left.rotateAngleX = this.arm_right.rotateAngleX;
				this.state = State.SPELLCASTING;
			}
			else {
				this.state = State.NORMAL;
			}
		}
	}
	@SideOnly(Side.CLIENT)
	enum State
	{
		NORMAL,
		SPELLCASTING,
	}
}