package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFatGloop extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer leg_l;
	private final ModelRenderer leg_r;
	private final ModelRenderer up;

	public ModelFatGloop() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(14.0F, 24.0F, -15.0F);
		body.cubeList.add(new ModelBox(body, 0, 64, -29.0F, -28.0F, -1.0F, 30, 28, 32, 0.25F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-14.0F, -20.0F, 31.0F);
		body.addChild(tail);
		setRotationAngle(tail, -0.3491F, 0.0F, 0.0F);
		tail.cubeList.add(new ModelBox(tail, 93, 6, -4.0F, 0.0F, 0.0F, 8, 0, 6, 0.0F, false));

		leg_l = new ModelRenderer(this);
		leg_l.setRotationPoint(1.0F, -15.0F, 15.0F);
		body.addChild(leg_l);
		leg_l.cubeList.add(new ModelBox(leg_l, 100, 18, 0.0F, -2.0F, -3.0F, 3, 4, 6, 0.0F, true));

		leg_r = new ModelRenderer(this);
		leg_r.setRotationPoint(-29.0F, -15.0F, 15.0F);
		body.addChild(leg_r);
		leg_r.cubeList.add(new ModelBox(leg_r, 100, 18, -3.0F, -2.0F, -3.0F, 3, 4, 6, 0.0F, false));

		up = new ModelRenderer(this);
		up.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(up);
		up.cubeList.add(new ModelBox(up, 0, 0, -29.0F, -28.0F, -1.0F, 30, 28, 32, 0.0F, false));
		up.cubeList.add(new ModelBox(up, 0, -2, -14.0F, -34.0F, 19.0F, 0, 12, 16, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}