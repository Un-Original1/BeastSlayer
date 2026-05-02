package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGloop extends ModelBase {
	private final ModelRenderer leg2; //left
	private final ModelRenderer leg1;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer tail;

	public ModelGloop() {
		textureWidth = 64;
		textureHeight = 64;

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(5.0F, 22.0F, -0.5F);
		leg2.cubeList.add(new ModelBox(leg2, 0, 0, -1.0F, -1.0F, -1.5F, 2, 3, 3, 0.0F, true));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-5.0F, 22.0F, -0.5F);
		leg1.cubeList.add(new ModelBox(leg1, 0, 0, -1.0F, -1.0F, -1.5F, 2, 3, 3, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 23.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 30, 5, -4.0F, -8.0F, -5.0F, 8, 8, 9, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 30, 23, -4.0F, -0.5F, -5.0F, 8, 8, 9, 0.25F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -3.0F, 3.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 0, 0.0F, -9.0F, -7.0F, 0, 12, 12, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 32, -4.0F, -5.0F, -8.0F, 8, 8, 9, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 39, 54, -4.0F, 0.0F, -8.0F, 8, 0, 9, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 4.0F);
		body.addChild(tail);
		setRotationAngle(tail, -0.3927F, 0.0F, 0.0F);
		tail.cubeList.add(new ModelBox(tail, 6, 0, -4.0F, 0.0F, 0.0F, 8, 0, 5, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12F*f5, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            leg2.render(f5);
            leg1.render(f5);
            body.render(f5);
            GlStateManager.popMatrix();
        }
        else {
            leg2.render(f5);
            leg1.render(f5);
            body.render(f5);
        }
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.body.rotateAngleY = netHeadYaw * 0.017453292F;
        this.body.rotateAngleX = headPitch * 0.017453292F;
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 1.8F) * 3F * limbSwingAmount * 0.5F;

        this.leg1.rotationPointY =  22F + MathHelper.cos(limbSwing * 3.6F) * 4F * limbSwingAmount;
        this.leg2.rotationPointY =  22F + MathHelper.cos(limbSwing * 3.6F + (float)Math.PI) * 4F * limbSwingAmount ;

        this.leg1.rotationPointZ =  0F - MathHelper.cos(limbSwing * 1.8F+ (float)Math.PI) * 6F * limbSwingAmount;
        this.leg2.rotationPointZ =  0F - MathHelper.cos(limbSwing * 1.8F)  * 6F * limbSwingAmount ;
    }
}