package com.unoriginal.ancientbeasts.entity.Model;
// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTornado extends ModelBase {
	private final ModelRenderer middlelow;
	private final ModelRenderer bottom;
	private final ModelRenderer middle;
	private final ModelRenderer top;

	public ModelTornado() {
		textureWidth = 128;
		textureHeight = 128;

		middlelow = new ModelRenderer(this);
		middlelow.setRotationPoint(0.0F, 12.0F, 0.0F);
		middlelow.cubeList.add(new ModelBox(middlelow, 0, 70, -8.0F, -4.0F, -8.0F, 16, 8, 16, 0.0F, false));

		bottom = new ModelRenderer(this);
		bottom.setRotationPoint(0.0F, 16.0F, 0.0F);
		bottom.cubeList.add(new ModelBox(bottom, 48, 70, -4.0F, 0.0F, -4.0F, 8, 8, 8, 0.0F, false));

		middle = new ModelRenderer(this);
		middle.setRotationPoint(0.0F, 4.0F, 0.0F);
		middle.cubeList.add(new ModelBox(middle, 0, 38, -12.0F, -4.0F, -12.0F, 24, 8, 24, 0.0F, false));

		top = new ModelRenderer(this);
		top.setRotationPoint(0.0F, -4.0F, 0.0F);
		top.cubeList.add(new ModelBox(top, 0, 0, -15.0F, -4.0F, -15.0F, 30, 8, 30, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		middlelow.render(f5);
		bottom.render(f5);
		middle.render(f5);
		top.render(f5);
		bottom.rotateAngleY-=this.bottom.rotationPointY * 0.4;
		middlelow.rotateAngleY+=this.middlelow.rotationPointY * 0.4;
		middle.rotateAngleY+=this.middle.rotationPointY * 0.8;
		top.rotateAngleY+=this.top.rotationPointY * 1.2;
	}
}