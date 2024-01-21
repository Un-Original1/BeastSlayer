package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHand extends ModelBase {
	private final ModelRenderer hand;

	public ModelHand() {
		textureWidth = 64;
		textureHeight = 64;

		hand = new ModelRenderer(this);
		hand.setRotationPoint(0.0F, 19.0F, 0.0F);
		hand.cubeList.add(new ModelBox(hand, 0, 0, -4.0F, -5.0F, -6.0F, 5, 10, 11, 0.1F, false));
		hand.cubeList.add(new ModelBox(hand, 5, 21, 1.0F, -5.0F, -6.0F, 4, 10, 3, 0.0F, false));
		hand.cubeList.add(new ModelBox(hand, 21, 0, 1.0F, -5.0F, 1.0F, 4, 4, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		hand.render(f5);
	}


	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.hand.rotateAngleY = netHeadYaw * 0.017453292F;
	}
}