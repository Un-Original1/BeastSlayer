package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityNekros;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelNekros extends ModelBase {
	private final ModelRenderer arm_left;
	private final ModelRenderer arm_right;
	private final ModelRenderer body;
	private final ModelRenderer trailthing;
	private final ModelRenderer head;
	private final ModelRenderer bone;
	private State state = State.NORMAL;

	public ModelNekros() {
		textureWidth = 64;
		textureHeight = 64;

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(3.0F, 3.0F, -1.0F);
		arm_left.cubeList.add(new ModelBox(arm_left, 40, 32, 0.0F, -1.0F, -1.0F, 4, 8, 4, 0.0F, false));
		arm_left.cubeList.add(new ModelBox(arm_left, 20, 32, 0.0F, -1.0F, 3.0F, 4, 10, 6, 0.0F, false));
		arm_left.cubeList.add(new ModelBox(arm_left, 0, 48, 0.0F, 7.0F, -1.0F, 4, 5, 4, 0.25F, false));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-3.0F, 3.0F, -1.0F);
		arm_right.cubeList.add(new ModelBox(arm_right, 40, 32, -4.0F, -1.0F, -1.0F, 4, 10, 4, 0.0F, true));
		arm_right.cubeList.add(new ModelBox(arm_right, 20, 32, -4.0F, -1.0F, 3.0F, 4, 10, 6, 0.0F, true));
		arm_right.cubeList.add(new ModelBox(arm_right, 0, 48, -4.0F, 7.0F, -1.0F, 4, 5, 4, 0.25F, true));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 2.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 17, -4.0F, 0.0F, -2.5F, 8, 12, 5, 0.0F, false));

		trailthing = new ModelRenderer(this);
		trailthing.setRotationPoint(0.0F, 12.0F, -2.0F);
		body.addChild(trailthing);
		trailthing.cubeList.add(new ModelBox(trailthing, 26, 17, -4.0F, -0.15F, -0.5F, 8, 10, 5, 0.1F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 2.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -9.0F, -4.0F, 8, 9, 8, 0.1F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -7.0F, 6.0F);
		head.addChild(bone);
		setRotationAngle(bone, -0.7854F, 0.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 33, 9, -4.0F, 0.0F, -3.0F, 8, 4, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		arm_left.render(f5);
		arm_right.render(f5);
		body.render(f5);
		head.render(f5);
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
		this.arm_right.rotateAngleX= f + MathHelper.sin(ageInTicks * 0.067F) * 0.07F;
		this.arm_left.rotateAngleX = f - MathHelper.sin(ageInTicks * 0.067F) * 0.07F;
		this.arm_right.rotateAngleZ = MathHelper.cos(ageInTicks * 0.09F) * 0.07F;
		this.arm_left.rotateAngleZ = -this.arm_right.rotateAngleZ;
		this.arm_right.rotateAngleY = 0.65F;
		this.arm_left.rotateAngleY = -this.arm_right.rotateAngleY;
		this.body.rotateAngleX = 0.5F;
		this.trailthing.rotateAngleX = 0.15F + MathHelper.cos(ageInTicks * 0.09F) * 0.2F;
		if(this.state == State.SPELLCASTING){
			this.arm_right.rotateAngleX = 0F + MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
			this.arm_left.rotateAngleX = 0F -  MathHelper.cos(ageInTicks  * 0.6662F) * 0.25F;

			this.arm_right.rotateAngleZ = 2.4F;
			this.arm_left.rotateAngleZ = -2.4F;

			this.arm_right.rotationPointX = -5.0F;
			this.arm_left.rotationPointX = 5.0F;
		}
	}
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		if(entitylivingbaseIn instanceof EntityNekros){
			EntityNekros nekros = (EntityNekros) entitylivingbaseIn;
			if(nekros.isUsingMagic()){

				this.state= State.SPELLCASTING;
			} else {
				this.state = State.NORMAL;
				this.arm_right.rotationPointX = -3.0F;
				this.arm_left.rotationPointX = 3.0F;
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