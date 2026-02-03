package com.unoriginal.beastslayer.entity.Model;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSuccubus extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer earr;
	private final ModelRenderer earl;
	private final ModelRenderer horn_r;
	private final ModelRenderer horn_l;
	private final ModelRenderer hair;
	private final ModelRenderer arm_r;
	private final ModelRenderer arm_l;
	private final ModelRenderer awooga;
	private final ModelRenderer wing_r;
	private final ModelRenderer wing_l;
	private final ModelRenderer tail;
	private final ModelRenderer point;
	private final ModelRenderer leg_l;
	private final ModelRenderer leg_r;

	public ModelSuccubus() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 10.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 32, 16, -4.0F, -13.0F, -2.1F, 8, 13, 4, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 104, 92, -4.0F, -13.0F, -2.1F, 8, 13, 4, 0.25F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -13.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 72, 12, -4.0F, -8.0F, -4.0F, 8, 10, 8, 0.25F, false));

		earr = new ModelRenderer(this);
		earr.setRotationPoint(-4.0F, -3.0F, -0.5F);
		head.addChild(earr);
		setRotationAngle(earr, 0.0F, 0.0F, -0.3054F);
		earr.cubeList.add(new ModelBox(earr, 0, 70, -7.0F, -2.0F, -0.5F, 7, 4, 1, 0.0F, false));
		earr.cubeList.add(new ModelBox(earr, 18, 70, -7.0F, -2.0F, -0.5F, 7, 4, 1, 0.25F, false));

		earl = new ModelRenderer(this);
		earl.setRotationPoint(4.0F, -3.0F, -0.5F);
		head.addChild(earl);
		setRotationAngle(earl, 0.0F, 0.0F, 0.3054F);
		earl.cubeList.add(new ModelBox(earl, 0, 70, 0.0F, -2.0F, -0.5F, 7, 4, 1, 0.0F, true));

		horn_r = new ModelRenderer(this);
		horn_r.setRotationPoint(2.0F, -7.0F, 1.0F);
		head.addChild(horn_r);
		setRotationAngle(horn_r, 0.0F, -0.3927F, -0.3927F);
		horn_r.cubeList.add(new ModelBox(horn_r, 0, 120, 0.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, false));
		horn_r.cubeList.add(new ModelBox(horn_r, 30, 116, 6.0F, -10.0F, -2.0F, 4, 8, 4, 0.0F, false));
		horn_r.cubeList.add(new ModelBox(horn_r, 50, 123, 10.0F, -10.0F, -2.0F, 2, 1, 4, 0.0F, false));

		horn_l = new ModelRenderer(this);
		horn_l.setRotationPoint(-2.0F, -7.0F, 1.0F);
		head.addChild(horn_l);
		setRotationAngle(horn_l, 0.0F, 0.3927F, 0.3927F);
		horn_l.cubeList.add(new ModelBox(horn_l, 0, 120, -10.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, true));
		horn_l.cubeList.add(new ModelBox(horn_l, 30, 116, -10.0F, -10.0F, -2.0F, 4, 8, 4, 0.0F, true));
		horn_l.cubeList.add(new ModelBox(horn_l, 50, 123, -12.0F, -10.0F, -2.0F, 2, 1, 4, 0.0F, true));

		hair = new ModelRenderer(this);
		hair.setRotationPoint(0.0F, -3.0F, 4.0F);
		head.addChild(hair);
		setRotationAngle(hair, 0.3927F, 0.0F, 0.0F);
		hair.cubeList.add(new ModelBox(hair, 0, 89, -6.0F, 9.0F, -1.0F, 12, 3, 2, 0.0F, false));
		hair.cubeList.add(new ModelBox(hair, 0, 77, -6.0F, 0.0F, -1.0F, 12, 9, 2, 0.0F, false));

		arm_r = new ModelRenderer(this);
		arm_r.setRotationPoint(-4.0F, -11.0F, 0.0F);
		body.addChild(arm_r);
		setRotationAngle(arm_r, 0.0F, 0.0F, 0.3054F);
		arm_r.cubeList.add(new ModelBox(arm_r, 56, 16, -3.0F, -2.0F, -2.0F, 3, 13, 4, 0.0F, false));
		arm_r.cubeList.add(new ModelBox(arm_r, 38, 66, -3.0F, -2.0F, -2.0F, 3, 13, 4, 0.25F, false));

		arm_l = new ModelRenderer(this);
		arm_l.setRotationPoint(4.0F, -11.0F, 0.0F);
		body.addChild(arm_l);
		setRotationAngle(arm_l, 0.0F, 0.0F, -0.3054F);
		arm_l.cubeList.add(new ModelBox(arm_l, 56, 16, 0.0F, -2.0F, -2.0F, 3, 13, 4, 0.0F, true));
		arm_l.cubeList.add(new ModelBox(arm_l, 38, 66, 0.0F, -2.0F, -2.0F, 3, 13, 4, 0.25F, true));

		awooga = new ModelRenderer(this);
		awooga.setRotationPoint(0.0F, -10.0F, -2.0F);
		body.addChild(awooga);
		setRotationAngle(awooga, 0.3927F, 0.0F, 0.0F);
		awooga.cubeList.add(new ModelBox(awooga, 35, 0, -6.5F, -2.0F, -7.0F, 13, 7, 8, 0.0F, false));
		awooga.cubeList.add(new ModelBox(awooga, 86, 57, -6.5F, -2.0F, -7.0F, 13, 7, 8, 0.25F, false));

		wing_r = new ModelRenderer(this);
		wing_r.setRotationPoint(-4.0F, -4.0F, 1.0F);
		body.addChild(wing_r);
		setRotationAngle(wing_r, 0.0F, 0.5236F, 0.6109F);
		wing_r.cubeList.add(new ModelBox(wing_r, 0, 115, -12.0F, -1.0F, 0.0F, 14, 2, 2, 0.0F, false));
		wing_r.cubeList.add(new ModelBox(wing_r, 30, 83, -14.0F, -1.0F, 0.0F, 2, 14, 2, 0.0F, false));
		wing_r.cubeList.add(new ModelBox(wing_r, 59, 76, -14.0F, -5.0F, 1.0F, 14, 20, 0, 0.0F, false));

		wing_l = new ModelRenderer(this);
		wing_l.setRotationPoint(4.0F, -4.0F, 1.0F);
		body.addChild(wing_l);
		setRotationAngle(wing_l, 0.0F, -0.5236F, -0.6109F);
		wing_l.cubeList.add(new ModelBox(wing_l, 0, 115, -2.0F, -1.0F, 0.0F, 14, 2, 2, 0.0F, true));
		wing_l.cubeList.add(new ModelBox(wing_l, 30, 83, 12.0F, -1.0F, 0.0F, 2, 14, 2, 0.0F, true));
		wing_l.cubeList.add(new ModelBox(wing_l, 59, 76, 0.0F, -5.0F, 1.0F, 14, 20, 0, 0.0F, true));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -3.0F, 2.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 26, 87, -1.5F, 0.0F, 5.0F, 3, 13, 14, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, -5, 27, -1.5F, 0.0F, -1.0F, 3, 0, 6, 0.0F, false));

		point = new ModelRenderer(this);
		point.setRotationPoint(0.0F, 13.0F, 19.0F);
		tail.addChild(point);
		point.cubeList.add(new ModelBox(point, -9, 16, -4.5F, 0.0F, -3.0F, 9, 0, 10, 0.0F, false));

		leg_l = new ModelRenderer(this);
		leg_l.setRotationPoint(2.0F, 10.0F, 0.0F);
		leg_l.cubeList.add(new ModelBox(leg_l, 30, 39, -2.0F, 6.0F, -2.0F, 4, 8, 4, 0.0F, true));
		leg_l.cubeList.add(new ModelBox(leg_l, 14, 36, -2.0F, 6.0F, -2.0F, 4, 8, 4, 0.25F, false));
		leg_l.cubeList.add(new ModelBox(leg_l, 22, 51, -2.0F, -2.0F, -2.0F, 6, 8, 5, 0.0F, false));
		leg_l.cubeList.add(new ModelBox(leg_l, 0, 96, -2.0F, -2.0F, -2.0F, 6, 8, 5, 0.25F, false));

		leg_r = new ModelRenderer(this);
		leg_r.setRotationPoint(-2.0F, 10.0F, 0.0F);
		leg_r.cubeList.add(new ModelBox(leg_r, 0, 48, -4.0F, -2.0F, -2.0F, 6, 8, 5, 0.0F, true));
		leg_r.cubeList.add(new ModelBox(leg_r, 0, 96, -4.0F, -2.0F, -2.0F, 6, 8, 5, 0.25F, true));
		leg_r.cubeList.add(new ModelBox(leg_r, 30, 39, -2.0F, 6.0F, -2.0F, 4, 8, 4, 0.0F, false));
		leg_r.cubeList.add(new ModelBox(leg_r, 14, 36, -2.0F, 6.0F, -2.0F, 4, 8, 4, 0.25F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		leg_l.render(f5);
		leg_r.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;

        this.arm_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.arm_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;

        this.wing_l.rotateAngleZ =-0.6109F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.25F;
        this.wing_r.rotateAngleZ = 0.6109F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.25F;

        this.earl.rotateAngleZ = 0.3054F + MathHelper.cos(ageInTicks * 0.12F) * 0.04F;
        this.earr.rotateAngleZ = -0.3054F + MathHelper.cos(ageInTicks * 0.12F + (float)Math.PI) * 0.04F;

        this.wing_r.rotateAngleY = 0.5236F + MathHelper.cos(ageInTicks * 0.09F) * 0.05F; //don't forget 1 -> speed 2 -> amp
        this.wing_l.rotateAngleY = -0.5236F + MathHelper.cos(ageInTicks * 0.09F + (float)Math.PI) * 0.05F;

        this.hair.rotateAngleX = 0.3927F + MathHelper.cos(ageInTicks * 0.06F) * 0.06F;

        this.tail.rotateAngleX = MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

        this.body.rotateAngleX = 0F;
        this.body.rotateAngleZ = 0F;
        //reset other anim

        this.arm_r.rotateAngleY = 0F;
        this.arm_r.rotateAngleZ = 0.3054F;

        this.arm_l.rotateAngleY = 0F;
        this.arm_l.rotateAngleZ = -0.3054F;


        this.leg_l.rotateAngleY = 0F;
        this.leg_l.rotateAngleZ = 0F;

        this.leg_r.rotateAngleY = 0F;
        this.leg_r.rotateAngleZ = 0F;
        float dontforget = 0.007F;
        //people are gonna hate me for this, but I cannot withstand a solid block that does not move at all, makes the model look stiff and lifeless
        //NOTE: I HAVE NO CLUE WHY IT MOVES SO MUCH HELPPPPP
        this.awooga.rotationPointY = -10.0F + MathHelper.cos(ageInTicks * (0.2F + limbSwing * 0.0003F)) * (0.05F + limbSwingAmount * 0.6F);
        if (this.isRiding)
        {
            this.arm_r.rotateAngleX += -((float)Math.PI / 5F);
            this.arm_l.rotateAngleX += -((float)Math.PI / 5F);
            this.leg_r.rotateAngleY = ((float)Math.PI / 10F);
            this.leg_r.rotateAngleZ = 0.07853982F;
            this.leg_l.rotateAngleY = -((float)Math.PI / 10F);
            this.leg_l.rotateAngleZ = -0.07853982F;
            this.leg_l.rotateAngleX = -1.4137167F;
            this.leg_r.rotateAngleX = -1.4137167F;
        }
        if(entityIn instanceof EntitySucc){
            EntitySucc succubus = (EntitySucc)entityIn;
            if(succubus.getSpellTimeClient() > 0){
                float fastpi = (float)Math.PI;
                this.head.rotateAngleY = netHeadYaw * 0.017453292F + 25F * fastpi / 180F;

                this.body.rotateAngleX =  MathHelper.cos(ageInTicks * 0.3F) * 0.1F;
                this.body.rotateAngleZ =  MathHelper.cos(ageInTicks * 0.3F) * 0.1F;

                this.arm_r.rotateAngleY = 4F * fastpi / 180F;
                this.arm_r.rotateAngleX = -177F * fastpi / 180F;
                this.arm_r.rotateAngleZ = -38F * fastpi / 180F + MathHelper.cos(ageInTicks * 0.5F) * 0.3F;

                this.arm_l.rotateAngleY = 0F;
                this.arm_l.rotateAngleZ = -17.5F * fastpi / 180F;
                this.arm_l.rotateAngleX = -97.5F * fastpi / 180F;
                if(!succubus.isRiding()) {

                    this.leg_l.rotateAngleX = 0F;
                    this.leg_l.rotateAngleY = -27.5F * fastpi / 180F;
                    this.leg_l.rotateAngleZ = -10F * fastpi / 180F;

                    this.leg_r.rotateAngleX = 25F * fastpi / 180F;
                }
                //note on blockbench editor reverse x y axis
            }
            if(succubus.isSitting()){
                float fastrad = (float)Math.PI / 180F;
                this.arm_l.rotateAngleX = -20.5F * fastrad;
                this.arm_l.rotateAngleY = 50F * fastrad;
                this.arm_l.rotateAngleZ = 8.5F * fastrad;

                this.arm_r.rotateAngleX = -20.5F * fastrad;
                this.arm_r.rotateAngleY = -50F * fastrad;
                this.arm_r.rotateAngleZ = -8.5F * fastrad;

            }
        }
    }
}