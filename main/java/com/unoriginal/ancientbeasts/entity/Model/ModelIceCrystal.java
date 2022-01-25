package com.unoriginal.ancientbeasts.entity.Model;
// Made with Blockbench 3.9.3

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIceCrystal extends ModelBase {
	private final ModelRenderer bone2;
	private final ModelRenderer bone;

	public ModelIceCrystal() {
		textureWidth = 32;
		textureHeight = 32;

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 8.0F, 0.0F);
		setRotationAngle(bone2, 0.0F, 0.7854F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 0, -8.0F, -8.0F, 0.0F, 16, 24, 0, 0.0F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 8.0F, 0.0F);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 0.0F, 16, 24, 0, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone2.render(f5);
		bone.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}