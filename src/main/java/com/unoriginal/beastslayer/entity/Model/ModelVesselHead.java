package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.0.1


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVesselHead extends ModelBase {
	private final ModelRenderer head;

	public ModelVesselHead() {
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 14, -4.0F, -8.0F, -4.0F, 8, 9, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 16, -1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 14, -4.0F, -8.0F, -4.0F, 8, 10, 8, 0.25F, false));
		head.cubeList.add(new ModelBox(head, 0, 28, 6.75F, -2.5F, -1.0F, 2, 2, 2, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 0, 28, -8.75F, -2.5F, -1.0F, 2, 2, 2, 0.0F, false));

		ModelRenderer bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-4.0F, -8.0F, 0.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, -0.3927F);
		bone3.cubeList.add(new ModelBox(bone3, 20, 6, -5.0F, 0.0F, -2.0F, 6, 4, 4, 0.0F, true));

		ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(4.0F, -8.0F, -0.1F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, 0.3927F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 6, -1.0F, 0.0F, -2.0F, 6, 4, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	}
}