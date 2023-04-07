package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityWisp;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWisp extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer trail;
	private final ModelRenderer top;

	public ModelWisp() {
		textureWidth = 32;
		textureHeight = 32;


		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 3.0F, 0.0F);
		main.cubeList.add(new ModelBox(main, 0, 1, -3.0F, -3.0F, -3.0F, 6, 7, 6, 0.0F, false));
		main.cubeList.add(new ModelBox(main, 12, 1, -3.0F, 3.0F, -3.0F, 6, 0, 6, 0.0F, false));

		trail = new ModelRenderer(this);
		trail.setRotationPoint(0.0F, 4.0F, 0.0F);
		main.addChild(trail);
		trail.cubeList.add(new ModelBox(trail, 0, 14, -3.0F, 0.001F, -3.0F, 6, 4, 6, 0.0F, false));

		top = new ModelRenderer(this);
		top.setRotationPoint(0.0F, -3.0F, 0.0F);
		main.addChild(top);
		top.cubeList.add(new ModelBox(top, 0, 14, -3.0F, -3.999F, -3.0F, 6, 4, 6, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		main.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		if(entity instanceof EntityWisp){
			EntityWisp w = (EntityWisp) entity;
			if(w.getVariant() == 3){
				this.trail.isHidden = true;
				this.top.isHidden = false;
				this.main.rotateAngleY = netHeadYaw * 0.017453292F;
				this.main.rotateAngleX = headPitch * 0.017453292F;
			}
			else {
				this.trail.isHidden = false;
				this.top.isHidden = true;
			}
		}
		this.main.rotateAngleX = MathHelper.sin(ageInTicks * 1.0F) * 0.05F;
	}
}