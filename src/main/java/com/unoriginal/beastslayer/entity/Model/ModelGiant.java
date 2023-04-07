package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 3.9.3

import com.unoriginal.beastslayer.entity.Entities.EntityGiant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGiant extends ModelBase {
	private State state= State.NORMAL;

	private final ModelRenderer body;
	private final ModelRenderer body_upper;
	private final ModelRenderer armRight;
	private final ModelRenderer armLeft;
	private final ModelRenderer head;
	private final ModelRenderer legLeft;
	private final ModelRenderer legRight;


	public ModelGiant() {
		textureWidth = 256;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -14.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 89, -15.0F, -22.0F, -8.0F, 30, 22, 17, 0.0F, false));

		body_upper = new ModelRenderer(this);
		body_upper.setRotationPoint(-1.0F, -16.0F, 1.0F);
		body.addChild(body_upper);
		body_upper.cubeList.add(new ModelBox(body_upper, 6, 5, -19.0F, -27.0F, -11.5F, 40, 28, 22, 0.0F, false));

		armRight = new ModelRenderer(this);
		armRight.setRotationPoint(-19.0F, -19.0F, 0.0F);
		body_upper.addChild(armRight);
		armRight.cubeList.add(new ModelBox(armRight, 192, 0, -16.0F, -10.0F, -8.5F, 16, 60, 16, 0.0F, false));

		armLeft = new ModelRenderer(this);
		armLeft.setRotationPoint(21.0F, -19.0F, 0.0F);
		body_upper.addChild(armLeft);
		armLeft.cubeList.add(new ModelBox(armLeft, 192, 0, 0.0F, -10.0F, -8.5F, 16, 60, 16, 0.0F, true));
		armLeft.cubeList.add(new ModelBox(armLeft, 168, 91, 0.0F, -10.0F, -11.0F, 23, 16, 21, 0.1F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(1.0F, -25.0F, -6.0F);
		body_upper.addChild(head);
		head.cubeList.add(new ModelBox(head, 133, 46, -7.0F, -14.0F, -7.0F, 14, 14, 14, 0.5F, false));

		legLeft = new ModelRenderer(this);
		legLeft.setRotationPoint(8.0F, -14.0F, 0.0F);
		legLeft.cubeList.add(new ModelBox(legLeft, 97, 76, -7.0F, 0.0F, -6.5F, 14, 38, 14, 0.0F, true));

		legRight = new ModelRenderer(this);
		legRight.setRotationPoint(-8.0F, -14.0F, 0.0F);
		legRight.cubeList.add(new ModelBox(legRight, 97, 76, -7.0F, 0.0F, -6.5F, 14, 38, 14, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		legLeft.render(f5);
		legRight.render(f5);
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.head.rotateAngleY = netHeadYaw * 0.016F;
		this.head.rotateAngleX = -0.4F + headPitch * 0.016F;
		this.body_upper.rotateAngleX = (float)Math.PI / 6.75F;
		this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 1F;
		this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 1F;
		if(this.state == State.NORMAL) {
			this.armRight.rotateAngleY = 0F;
			this.armLeft.rotateAngleY = 0F;
			this.armRight.rotateAngleX = -0.5F + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 1F;
			this.armLeft.rotateAngleX = -0.5F - MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 1F;
		}
		else if(this.state == State.GRABBING)
		{
			this.armRight.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.02F;
			this.armLeft.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.02F;
			this.armRight.rotateAngleY += MathHelper.cos(ageInTicks * 3F) * 0.005F;
			this.armLeft.rotateAngleY -= MathHelper.cos(ageInTicks * 3F) * 0.005F;
		}
	}
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		if(entitylivingbaseIn instanceof EntityGiant) {
			EntityGiant giant = (EntityGiant) entitylivingbaseIn;
			int i = giant.getAttackTimer();
			int j = giant.getThrowTicks();
			if (giant.isAttacking()) {
				this.armRight.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTickTime);
				this.armLeft.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTickTime);
				this.armRight.rotateAngleY = 0F;
				this.armLeft.rotateAngleY = 0F;
				this.state = State.ATTACKING;
			}
			else if(giant.isBeingRidden())
			{
				this.armRight.rotateAngleX = -2.0F;
				this.armLeft.rotateAngleX = -2.0F;
				this.armRight.rotateAngleY = -0.5F;
				this.armLeft.rotateAngleY = 0.5F;
				this.state = State.GRABBING;
			}
			else if(giant.isThrowing())
			{
				this.armRight.rotateAngleX = -9.5F * -this.triangleWave((float) j - partialTickTime);
				this.armLeft.rotateAngleX = -9.5F *  -this.triangleWave((float) j - partialTickTime);
				this.state = State.THROWING;
			}
			else {
				this.state = State.NORMAL;
			}
		}
	}
	private float triangleWave(float p_78172_1_)
	{
		return (Math.abs(p_78172_1_ % (float) 20.0 - (float) 20.0) - (float) 20.0) / ((float) 20.0);
	}
	@SideOnly(Side.CLIENT)
	enum State
	{
		NORMAL,
		ATTACKING,
		GRABBING,
		THROWING
	}
}