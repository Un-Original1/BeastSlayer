package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.EntityMosquito;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMosquito extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer leg_l2;
	private final ModelRenderer leg_l1;
	private final ModelRenderer leg_r1;
	private final ModelRenderer leg_r2;
	private final ModelRenderer leg_r3;
	private final ModelRenderer leg_l3;
	private final ModelRenderer wing_r;
	private final ModelRenderer wing_l;
	private final ModelRenderer big_butt;
	private final ModelRenderer butt;

	public ModelMosquito() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 15.5F, -5.0F);
		head.cubeList.add(new ModelBox(head, 40, 16, 0.0F, -1.5F, -13.0F, 0, 4, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 40, 30, -1.5F, -1.5F, -3.0F, 3, 3, 3, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(-0.5F, 16.0F, -5.0F);
		body.cubeList.add(new ModelBox(body, 44, 0, -2.0F, -3.0F, 0.0F, 5, 5, 5, 0.0F, false));

		leg_l2 = new ModelRenderer(this);
		leg_l2.setRotationPoint(13.0F, 0.0F, 3.0F);
		body.addChild(leg_l2);
		leg_l2.cubeList.add(new ModelBox(leg_l2, 28, 36, -10.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, false));

		leg_l1 = new ModelRenderer(this);
		leg_l1.setRotationPoint(3.0F, 0.0F, 2.0F);
		body.addChild(leg_l1);
		leg_l1.cubeList.add(new ModelBox(leg_l1, 28, 36, 0.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, false));

		leg_r1 = new ModelRenderer(this);
		leg_r1.setRotationPoint(-2.0F, 0.0F, 2.0F);
		body.addChild(leg_r1);
		leg_r1.cubeList.add(new ModelBox(leg_r1, 28, 36, -10.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, true));

		leg_r2 = new ModelRenderer(this);
		leg_r2.setRotationPoint(-2.0F, 0.0F, 3.0F);
		body.addChild(leg_r2);
		leg_r2.cubeList.add(new ModelBox(leg_r2, 28, 36, -10.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, true));

		leg_r3 = new ModelRenderer(this);
		leg_r3.setRotationPoint(-2.0F, 0.0F, 4.0F);
		body.addChild(leg_r3);
		leg_r3.cubeList.add(new ModelBox(leg_r3, 28, 36, -10.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, true));

		leg_l3 = new ModelRenderer(this);
		leg_l3.setRotationPoint(3.0F, 0.0F, 4.0F);
		body.addChild(leg_l3);
		leg_l3.cubeList.add(new ModelBox(leg_l3, 28, 36, 0.0F, -5.0F, 0.0F, 10, 16, 0, 0.0F, false));

		wing_r = new ModelRenderer(this);
		wing_r.setRotationPoint(-0.5F, -3.0F, 3.0F);
		body.addChild(wing_r);
		wing_r.cubeList.add(new ModelBox(wing_r, 0, 0, -2.5F, 0.0F, 0.0F, 6, 0, 16, 0.0F, true));

		wing_l = new ModelRenderer(this);
		wing_l.setRotationPoint(1.5F, -3.0F, 3.0F);
		body.addChild(wing_l);
		wing_l.cubeList.add(new ModelBox(wing_l, 0, 0, -3.5F, -0.1F, 0.0F, 6, 0, 16, 0.0F, false));

        butt = new ModelRenderer(this);
        butt.setRotationPoint(0.0F, 15.5F, 0.0F);
        butt.cubeList.add(new ModelBox(butt, 0, 36, -2.0F, -2.0F, 0.0F, 4, 4, 10, 0.0F, false));

        big_butt = new ModelRenderer(this);
        big_butt.setRotationPoint(0.0F, 15.5F, 0.0F);
        big_butt.cubeList.add(new ModelBox(big_butt, 0, 16, -4.0F, -4.0F, 0.0F, 8, 8, 12, 0.0F, false));

    }

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float f5) {
		boolean b = false;
        if(entity instanceof EntityMosquito){
            EntityMosquito mosquito = (EntityMosquito)entity;
            if(mosquito.isSucking()){
                b = true;
            }
        }

        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
            this.head.render(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            this.body.render(f5);
            this.butt.render(f5);
            this.big_butt.render(f5);
            GlStateManager.popMatrix();
        }
        else {

            float f = b ? MathHelper.cos(ageInTicks * 0.5F) * 0.15F : 0F;

            head.render(f5);
            body.render(f5);
            butt.render(f5);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1F + f, 1F + f, 1F + f);
            GlStateManager.translate(0.0F, -f, 0.0F);
            big_butt.render(f5);
            GlStateManager.popMatrix();
        }

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float fastradian = (float)Math.PI / 180F;
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = 27.5F * fastradian + headPitch * 0.017453292F;
        boolean b = false;
        if(entityIn instanceof EntityMosquito){
            EntityMosquito mos = (EntityMosquito)entityIn;
            if(mos.getStoredXPClient() > 0){
                b = true;
            }
        }
        this.big_butt.showModel = b;
        this.butt.showModel = !b;

        this.leg_l1.rotateAngleY = 32.5F * fastradian;
        this.leg_l3.rotateAngleY = -32.5F * fastradian;
        this.leg_r1.rotateAngleY = -32.5F * fastradian;
        this.leg_r3.rotateAngleY = 32.5F * fastradian;
        this.butt.rotateAngleX = -17.5F * fastradian + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
        this.big_butt.rotateAngleX = -17.5F * fastradian  + MathHelper.cos(ageInTicks * 0.1F) * 0.2F;

        this.leg_l1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F) * 0.15F;
        this.leg_l2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F + (float)Math.PI / 2F) * 0.15F;
        this.leg_l3.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F + (float)Math.PI) * 0.15F;
        this.leg_r1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F + (float)Math.PI) * 0.05F;
        this.leg_r2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F + (float)Math.PI / 2F + (float)Math.PI) * 0.15F;
        this.leg_r3.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F + (float)Math.PI + (float)Math.PI) * 0.15F;

        float f = ageInTicks * 0.3F;
        if(entityIn.onGround){
            this.wing_l.rotateAngleX = 30F * fastradian;
            this.wing_l.rotateAngleY = -10F * fastradian;
            this.wing_l.rotateAngleZ = 22.5F* fastradian;

            this.wing_r.rotateAngleX = 30F * fastradian;
            this.wing_r.rotateAngleY = -10F * fastradian;
            this.wing_r.rotateAngleZ = -22.5F* fastradian;
        } else {

            this.wing_l.rotateAngleZ = -30F * fastradian;
            this.wing_l.rotateAngleX = 32F * fastradian + MathHelper.cos(ageInTicks * 2F) * 0.8F;
            this.wing_l.rotateAngleY = 40F * fastradian;

            this.wing_r.rotateAngleZ = 30F * fastradian;
            this.wing_r.rotateAngleX = 32F * fastradian + MathHelper.cos(ageInTicks * 2F) * 0.8F;
            this.wing_r.rotateAngleY = -40F * fastradian;
        }
        if(entityIn.isRiding()){
            this.head.rotateAngleY = 0F ;
            this.head.rotateAngleX = 90F * fastradian;
        }

       // this.wing_r.rotationPointY = 16.94F + f;
    }
}