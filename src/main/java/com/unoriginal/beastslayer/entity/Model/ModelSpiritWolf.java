package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntitySpiritWolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSpiritWolf extends ModelBase {
	private final ModelRenderer back_leg_r;
	private final ModelRenderer cube_r1;
	private final ModelRenderer back_leg_l;
	private final ModelRenderer body;
	private final ModelRenderer front_leg_r;
	private final ModelRenderer front_leg_l;
	private final ModelRenderer head;
	private final ModelRenderer tail;

	public ModelSpiritWolf() {
		textureWidth = 128;
		textureHeight = 128;

		back_leg_r = new ModelRenderer(this);
		back_leg_r.setRotationPoint(-4.0F, 11.0F, 12.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(5.0F, 13.0F, -12.0F);
		back_leg_r.addChild(cube_r1);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 64, 36, -7.0F, -13.0F, 9.0F, 5, 13, 6, 0.0F, true));

		back_leg_l = new ModelRenderer(this);
		back_leg_l.setRotationPoint(4.0F, 11.0F, 12.0F);
		back_leg_l.cubeList.add(new ModelBox(back_leg_l, 64, 36, -3.0F, 0.0F, -3.0F, 5, 13, 6, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 10.0F, 9.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -11.0F, -9.0F, 12, 12, 14, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 64, -7.0F, -13.0F, -21.0F, 14, 16, 12, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 65, 73, -7.0F, -13.0F, -9.0F, 14, 16, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 1, 29, -7.0F, -13.0F, -21.0F, 14, 16, 7, 0.25F, false));

		front_leg_r = new ModelRenderer(this);
		front_leg_r.setRotationPoint(-6.0F, -7.0F, -14.0F);
		body.addChild(front_leg_r);
		front_leg_r.cubeList.add(new ModelBox(front_leg_r, 42, 46, -2.0F, -3.0F, -3.0F, 5, 24, 6, 0.1F, false));

		front_leg_l = new ModelRenderer(this);
		front_leg_l.setRotationPoint(6.0F, -7.0F, -14.0F);
		body.addChild(front_leg_l);
		front_leg_l.cubeList.add(new ModelBox(front_leg_l, 42, 46, -3.0F, -3.0F, -3.0F, 5, 24, 6, 0.1F, true));

		head = new ModelRenderer(this);
		head.setRotationPoint(1.0F, -7.0F, -21.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 42, 16, -6.0F, -5.0F, -10.0F, 10, 9, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 82, 17, -4.0F, -5.0F, -20.0F, 6, 9, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 6, -8.0F, -10.0F, -5.0F, 5, 7, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 6, 1.0F, -10.0F, -5.0F, 5, 7, 1, 0.0F, true));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -7.5F, 5.0F);
		body.addChild(tail);

		tail.cubeList.add(new ModelBox(tail, 0, 92, -1.5F, -1.5F, 0.0F, 3, 3, 22, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		back_leg_r.render(f5);
		back_leg_l.render(f5);
		body.render(f5);
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

		if(entityIn instanceof EntitySpiritWolf){
			EntitySpiritWolf wolf = (EntitySpiritWolf) entityIn;
			if(wolf.getStalkTicks() <= 0 &&  wolf.getAttackTarget() != null){
				this.back_leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
				this.back_leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.3F) * limbSwingAmount;
				this.front_leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI + 0.3F) * limbSwingAmount;
				this.front_leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
			} else {
				this.back_leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.55F;
				this.back_leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount * 0.55F;
				this.front_leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount * 0.55F;
				this.front_leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.55F;
			}
		}





		this.tail.rotateAngleY =  MathHelper.cos(limbSwing * 0.6662F) * -0.5F * limbSwingAmount;
		this.tail.rotateAngleX = -22.5F * (float) Math.PI / 180F;

	}
}