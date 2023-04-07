package com.unoriginal.beastslayer.entity.Model;
// Made with Blockbench 4.0.1

import com.unoriginal.beastslayer.entity.Entities.EntityFakeDuplicate;
import com.unoriginal.beastslayer.entity.Entities.EntityVessel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVessel extends ModelBase {
	private final ModelRenderer spring;
	private final ModelRenderer head;
	private final ModelRenderer bone3;
	private final ModelRenderer bone2;
	private final ModelRenderer body;
	private final ModelRenderer leg_left;
	private final ModelRenderer leg_right;
	private final ModelRenderer arm_left;
	private final ModelRenderer cube_r1;
	private final ModelRenderer arm_right;
	private final ModelRenderer end;
	private final ModelRenderer stringThing;
	private final ModelRenderer stringsarm;
	private final ModelRenderer stringsleg;
	private final ModelRenderer stringshead;

	public ModelVessel() {
		textureWidth = 64;
		textureHeight = 96;

		spring = new ModelRenderer(this);
		spring.setRotationPoint(0.0F, 10.75F, 0.0F);
		spring.cubeList.add(new ModelBox(spring, 58, 89, -1.5F, -6.75F, 0.0F, 3, 7, 0, 0.0F, false));
		spring.cubeList.add(new ModelBox(spring, 58, 86, 0.0F, -6.75F, -1.5F, 0, 7, 3, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -6.75F, 0.0F);
		spring.addChild(head);
		head.cubeList.add(new ModelBox(head, 28, 1, -4.0F, -9.0F, -4.0F, 8, 9, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 27, -1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 26, -4.0F, -9.0F, -4.0F, 8, 10, 8, 0.25F, false));
		head.cubeList.add(new ModelBox(head, 0, 16, 6.75F, -4.5F, -1.0F, 2, 2, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 16, -8.75F, -4.5F, -1.0F, 2, 2, 2, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-4.0F, -7.0F, 0.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, -0.3927F);
		bone3.cubeList.add(new ModelBox(bone3, 0, 0, -5.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F, true));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(4.0F, -7.0F, -0.1F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, 0.3927F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 8, -1.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 11.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 24, 26, -2.0F, 2.0F, -1.5F, 4, 5, 3, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 84, -5.0F, 0.0F, -5.0F, 10, 2, 10, 0.25F, false));

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(1.0F, 7.0F, 0.0F);
		body.addChild(leg_left);
		leg_left.cubeList.add(new ModelBox(leg_left, 46, 26, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, true));

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(-1.0F, 7.0F, 0.0F);
		body.addChild(leg_right);
		leg_right.cubeList.add(new ModelBox(leg_right, 46, 26, -1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F, false));

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(2.0F, 3.0F, 0.0F);
		body.addChild(arm_left);
		arm_left.cubeList.add(new ModelBox(arm_left, 40, 88, -0.01F, -1.1F, -2.0F, 3, 4, 4, 0.0F, true));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(2.0F, 9.0F, 0.0F);
		arm_left.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0287F, -0.0197F, -0.0265F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 54, 26, -2.0F, -10.0F, -1.0F, 2, 10, 2, 0.0F, true));

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-2.0F, 4.0F, 0.0F);
		body.addChild(arm_right);
		arm_right.cubeList.add(new ModelBox(arm_right, 54, 26, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));
		arm_right.cubeList.add(new ModelBox(arm_right, 40, 88, -2.99F, -1.1F, -2.0F, 3, 4, 4, 0.0F, false));

		end = new ModelRenderer(this);
		end.setRotationPoint(0.0F, 7.0F, 1.5F);
		body.addChild(end);
		end.cubeList.add(new ModelBox(end, 56, 0, -2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F, false));

		stringThing = new ModelRenderer(this);
		stringThing.setRotationPoint(0.0F, -6.0F, 0.0F);
		stringThing.cubeList.add(new ModelBox(stringThing, 0, 0, -2.0F, -9.0F, -10.0F, 4, 1, 20, 0.0F, false));
		stringThing.cubeList.add(new ModelBox(stringThing, 0, 21, -10.0F, -8.0F, -2.0F, 20, 1, 4, 0.0F, false));

		stringsarm = new ModelRenderer(this);
		stringsarm.setRotationPoint(0.0F, -7.0F, 0.0F);
		stringThing.addChild(stringsarm);
		//setRotationAngle(stringsarm, 0.0873F, 0.0F, 0.0F);

		stringsarm.cubeList.add(new ModelBox(stringsarm, 32, 44, 3.0F, 0.0F, 0.0F, 6, 36, 0, 0.0F, true));
		stringsarm.cubeList.add(new ModelBox(stringsarm, 32, 44, -9.0F, 0.0F, 0.0F, 6, 36, 0, 0.0F, false));

		stringsleg = new ModelRenderer(this);
		stringsleg.setRotationPoint(0.0F, -10.0F, 8.0F);
		stringThing.addChild(stringsleg);
	//	setRotationAngle(stringsleg, -0.0873F, 0.0F, 0.0F);

		stringsleg.cubeList.add(new ModelBox(stringsleg, 16, 44, -4.0F, 1.0F, 1.0F, 8, 36, 0, 0.0F, false));

		stringshead = new ModelRenderer(this);
		stringshead.setRotationPoint(0.0F, -8.0F, -8.0F);
		stringThing.addChild(stringshead);
	//	setRotationAngle(stringshead, 0.3054F, 0.0F, 0.0F);
		stringshead.cubeList.add(new ModelBox(stringshead, 0, 44, -4.0F, 0.0F, 0.0F, 8, 36, 0, 0.0F, true));
	}


	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if(entity instanceof EntityVessel){
			EntityVessel vessel = (EntityVessel) entity;
			if(!vessel.hasShotHead()){
				spring.render(f5);
			}
		}
		else {
			spring.render(f5);
		}
		body.render(f5);
		//spring.render(f5);
		body.render(f5);
		stringThing.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleX = -this.spring.rotateAngleX + headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;

		this.spring.rotateAngleX = 80F * (float)Math.PI / 180F;


		this.leg_left.rotateAngleX = -body.rotateAngleX;
		this.leg_right.rotateAngleX = -body.rotateAngleX;

		this.leg_left.rotateAngleX +=  (MathHelper.cos(ageInTicks * 0.25F) * 35F + 17.5F)* (float)Math.PI / 180F ;
		this.leg_right.rotateAngleX += (MathHelper.cos(ageInTicks * 0.25F + (float)Math.PI * 0.5F) * 35F + 17.5F)* (float)Math.PI / 180F;

		this.arm_left.rotateAngleX = -body.rotateAngleX;
		this.arm_right.rotateAngleX = -body.rotateAngleX;

		this.arm_left.rotationPointX = 2.0F;
		this.arm_right.rotationPointX = -2.0F;

		this.arm_right.rotateAngleX += (MathHelper.cos(ageInTicks * 0.25F) * 45F + 22.5F)* (float)Math.PI / 180F ;
		this.arm_left.rotateAngleX += (MathHelper.cos(ageInTicks * 0.25F + (float)Math.PI * 0.5F) * 45F + 22.5F)* (float)Math.PI / 180F;




		this.arm_right.rotateAngleZ = 0.0F;
		this.arm_left.rotateAngleZ = 0.0F;
		this.arm_right.rotateAngleY = 0.0F;
		this.arm_left.rotateAngleY = 0.0F;


		if(entityIn instanceof EntityVessel){
			EntityVessel v = (EntityVessel)entityIn;
			if(v.isInactive()){
			//	this.leg_left.rotateAngleX=0.0F;
			//	this.leg_right.rotateAngleX=0.0F;
				this.head.rotateAngleX = 17.5F * (float)Math.PI / 180F;
				this.body.rotateAngleX=  0F;

				this.arm_left.rotateAngleX=0.0F;
				this.arm_right.rotateAngleX=0.0F;
				this.arm_left.rotateAngleZ= -55F * (float)Math.PI/180F;
				this.arm_right.rotateAngleZ= 55F * (float)Math.PI/180F;
				this.end.rotateAngleX= 80F * (float)Math.PI/180F;

				this.arm_right.rotateAngleX += -((float)Math.PI / 5F);
				this.arm_left.rotateAngleX += -((float)Math.PI / 5F);
				this.leg_right.rotateAngleX = -1.4137167F;
				this.leg_right.rotateAngleY = ((float)Math.PI / 10F);
				this.leg_right.rotateAngleZ = 0.07853982F;
				this.leg_left.rotateAngleX = -1.4137167F;
				this.leg_left.rotateAngleY = -((float)Math.PI / 10F);
				this.leg_left.rotateAngleZ = -0.07853982F;

				this.stringshead.rotateAngleX = 12.5F  * (float)Math.PI / 180F;

				this.stringsleg.rotateAngleX = -12.5F * (float)Math.PI / 180F;
				this.stringsarm.rotateAngleX = 0F;
			}
			else if (!v.isInactive())
			{
				this.body.rotateAngleX=  52.5F * (float)Math.PI / 180F + MathHelper.cos(ageInTicks * 0.15F) * 0.2F;

				double k = Math.cos(ageInTicks * 0.25F);
				this.stringsarm.rotateAngleX = ((float) k * 10F + 5F)  * (float)Math.PI / 180F;

				this.stringsleg.rotateAngleX = (float) (k * 6F - 6F) * (float)Math.PI / 180F;

				this.stringshead.rotateAngleX = 20F * (float) Math.PI / 180F;

				this.end.rotateAngleX = -(float)Math.PI / 4F + MathHelper.cos(ageInTicks * 0.15F) * 0.8F + 0.5F;
			}
			if(v.getArmPose() == AbstractIllager.IllagerArmPose.SPELLCASTING){
				this.arm_right.rotationPointZ = 0.0F;
				this.arm_right.rotationPointX = -3.0F;
				this.arm_left.rotationPointZ = 0.0F;
				this.arm_left.rotationPointX = 3.0F;
				this.arm_right.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F);
				this.arm_left.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F);
				this.arm_right.rotateAngleZ = 2.3561945F;
				this.arm_left.rotateAngleZ = -2.3561945F;
				this.arm_right.rotateAngleY = 0.0F;
				this.arm_left.rotateAngleY = 0.0F;

			}
		}
		if(entityIn instanceof EntityFakeDuplicate){
			EntityFakeDuplicate i = (EntityFakeDuplicate) entityIn;
			if(i.getArmPose() == AbstractIllager.IllagerArmPose.SPELLCASTING){
				this.arm_right.rotationPointZ = 0.0F;
				this.arm_right.rotationPointX = -3.0F;
				this.arm_left.rotationPointZ = 0.0F;
				this.arm_left.rotationPointX = 3.0F;
				this.arm_right.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
				this.arm_left.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
				this.arm_right.rotateAngleZ = 2.3561945F;
				this.arm_left.rotateAngleZ = -2.3561945F;
				this.arm_right.rotateAngleY = 0.0F;
				this.arm_left.rotateAngleY = 0.0F;
			}
		}
	}
}