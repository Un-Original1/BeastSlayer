package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityEarthling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEarthling extends ModelBase {
	private final ModelRenderer leg_r;
	private final ModelRenderer leg_l;
	public final ModelRenderer head;

	public ModelEarthling() {
		textureWidth = 64;
		textureHeight = 64;

		leg_r = new ModelRenderer(this);
		leg_r.setRotationPoint(-4.5F, 18.0F, 0.5F);
		leg_r.cubeList.add(new ModelBox(leg_r, 0, 40, -2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F, false));

		leg_l = new ModelRenderer(this);
		leg_l.setRotationPoint(4.5F, 18.0F, 0.5F);
		leg_l.cubeList.add(new ModelBox(leg_l, 32, 30, -2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F, true));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 18.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -9.0F, -16.0F, -7.0F, 18, 16, 14, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 30, 1.0F, -18.0F, -1.0F, 8, 2, 8, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		leg_r.render(f5);
		leg_l.render(f5);
		head.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
       // super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.head.rotateAngleZ = MathHelper.cos(limbSwing * 1.4F) * 2F * limbSwingAmount;
        this.leg_l.rotateAngleX = MathHelper.cos(limbSwing * 1.8F) * 3F * limbSwingAmount * 0.5F;
        this.leg_r.rotateAngleX = MathHelper.cos(limbSwing * 1.8F + (float)Math.PI) * 3F * limbSwingAmount * 0.5F;
        if(this.head.rotateAngleZ > (float)Math.PI * 25F/180){
            this.head.rotateAngleZ = (float)Math.PI * 25F/180F;
        }
        if(this.head.rotateAngleZ < -(float)Math.PI * 25F/180){
            this.head.rotateAngleZ = -(float)Math.PI * 25F/180F;
        }
        this.head.rotationPointY = 18F + MathHelper.cos(ageInTicks * 0.2F) * 0.05F;

        if(entityIn instanceof EntityEarthling){
            EntityEarthling e = (EntityEarthling)entityIn;
            if(e.getSleepClient() > 0){
                this.head.rotationPointY = 18F + ageInTicks * 0.75F;

                if(this.head.rotationPointY > 24F){
                    this.head.rotationPointY = 24F;
                }
            }
        }
    }
}