package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.0.5

import com.unoriginal.beastslayer.entity.Entities.EntityDamcell;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDamcell extends ModelBase {
	private final ModelRenderer door_1;
	private final ModelRenderer spikes3;
	private final ModelRenderer head;
	private final ModelRenderer door_2;
	private final ModelRenderer spikes4;
	private final ModelRenderer body;
	private final ModelRenderer spikes1;
	private final ModelRenderer spikes2;

	public ModelDamcell() {
		textureWidth = 128;
		textureHeight = 128;

		door_1 = new ModelRenderer(this);
		door_1.setRotationPoint(-6.0F, 4.0F, -2.0F);
		door_1.cubeList.add(new ModelBox(door_1, 0, 40, 0.0F, -16.0F, -4.0F, 6, 32, 4, 0.0F, false));
		door_1.cubeList.add(new ModelBox(door_1, 60, 60, 0.0F, -16.0F, -4.0F, 6, 32, 4, 0.2F, false));

		spikes3 = new ModelRenderer(this);
		spikes3.setRotationPoint(6.0F, 4.0F, 2.0F);
		door_1.addChild(spikes3);
		setRotationAngle(spikes3, 0.0F, -0.7854F, 0.0F);
		spikes3.cubeList.add(new ModelBox(spikes3, 88, 0, -14.0F, -12.0F, 0.0F, 14, 24, 0, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 72, 92, -4.0F, -8.3F, -5.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 40, 0, -8.0F, -14.0F, -3.0F, 16, 14, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 68, 24, -4.0F, -8.3F, -5.0F, 8, 8, 8, 0.25F, false));

		door_2 = new ModelRenderer(this);
		door_2.setRotationPoint(6.0F, 4.0F, -2.0F);
		door_2.cubeList.add(new ModelBox(door_2, 0, 40, -6.0F, -16.0F, -4.0F, 6, 32, 4, 0.0F, true));
		door_2.cubeList.add(new ModelBox(door_2, 60, 60, -6.0F, -16.0F, -4.0F, 6, 32, 4, 0.25F, true));

		spikes4 = new ModelRenderer(this);
		spikes4.setRotationPoint(-1.0F, 4.0F, -2.0F);
		door_2.addChild(spikes4);
		setRotationAngle(spikes4, 0.0F, 0.7854F, 0.0F);
		spikes4.cubeList.add(new ModelBox(spikes4, 88, 0, -6.3F, -12.0F, -0.7F, 14, 24, 0, 0.0F, true));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 40, 44, -6.0F, -36.0F, -2.0F, 12, 8, 8, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -36.0F, -2.0F, 12, 32, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 26, 26, -7.0F, -4.0F, -7.0F, 14, 4, 14, 0.0F, false));

		spikes1 = new ModelRenderer(this);
		spikes1.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(spikes1);
		setRotationAngle(spikes1, 0.0F, 0.7854F, 0.0F);
		spikes1.cubeList.add(new ModelBox(spikes1, 88, 0, -14.0F, -28.0F, 0.0F, 14, 24, 0, 0.0F, false));

		spikes2 = new ModelRenderer(this);
		spikes2.setRotationPoint(0.0F, -16.0F, 0.0F);
		body.addChild(spikes2);
		setRotationAngle(spikes2, 0.0F, -0.7854F, 0.0F);
		spikes2.cubeList.add(new ModelBox(spikes2, 88, 0, 0.0F, -12.0F, 0.0F, 14, 24, 0, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		door_1.render(f5);
		head.render(f5);
		door_2.render(f5);
		body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

	}
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		EntityDamcell damcell = (EntityDamcell) entitylivingbaseIn;
		int i = damcell.getSuckTimer();
		if(i > 0 && !damcell.isBeingRidden()){
			this.door_2.rotateAngleY = 3 * -(float)Math.PI / 4F;
			this.door_1.rotateAngleY = -this.door_2.rotateAngleY;
		}
		else {
			this.door_1.rotateAngleY = 0F;
			this.door_2.rotateAngleY = 0F;
		}
	}
}