package com.unoriginal.beastslayer.entity.Model;

import com.google.common.collect.ImmutableList;
import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import com.unoriginal.beastslayer.animation.model.BasicModelEntity;
import com.unoriginal.beastslayer.animation.model.BasicModelPart;
import com.unoriginal.beastslayer.animation.model.EZModelAnimator;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import net.minecraft.client.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFireElemental extends BasicModelEntity {

	private final BasicModelPart head;
	private final BasicModelPart big_horn2;
	private final BasicModelPart lowerthingy2;
	private final BasicModelPart notsobighorn1;
	private final BasicModelPart notsobighorn2;
	private final BasicModelPart lowerthingy1;
	private final BasicModelPart firehair;
	private final BasicModelPart big_horn;
	private final BasicModelPart mask;
	private final BasicModelPart horn4;
	private final BasicModelPart horn2;
	private final BasicModelPart horn5;
	private final BasicModelPart horn3;
	private final BasicModelPart backpart;
	private final BasicModelPart body;
	private final BasicModelPart arm2;
	private final BasicModelPart lower_arm2;
	private final BasicModelPart arm1;
	private final BasicModelPart lower_arm;
	private final BasicModelPart lowerbody;
	private final BasicModelPart firetrail;
	private final BasicModelPart spin;
	private final BasicModelPart bone2;
	private final BasicModelPart bone3;

	//The Model Animator
	private final EZModelAnimator animator;
	public ModelFireElemental() {
				textureWidth = 160;
				textureHeight = 160;

				head = new BasicModelPart(this);
				head.setRotationPoint(0.0F, -43.0F, -3.0F);
				setRotationAngle(head, 0.0F, 0.0F, 0.0F);
				head.cubeList.add(new ModelBox(head, 24, 114, -4.0F, -9.0F, -4.0F, 8, 9, 8, 0.0F, false));
				head.cubeList.add(new ModelBox(head, 0, 24, 0.0F, -16.0F, -4.0F, 0, 10, 12, 0.0F, false));

				big_horn2 = new BasicModelPart(this);
				big_horn2.setRotationPoint(6.0F, -7.0F, 2.0F);
				head.addChild(big_horn2);
				setRotationAngle(big_horn2, 0.9163F, -1.0908F, 0.0F);
				big_horn2.cubeList.add(new ModelBox(big_horn2, 0, 13, -4.0F, -14.0F, 12.0F, 7, 2, 1, 0.0F, true));
				big_horn2.cubeList.add(new ModelBox(big_horn2, 82, 114, -4.0F, -12.0F, -3.0F, 7, 16, 6, 0.0F, true));
				big_horn2.cubeList.add(new ModelBox(big_horn2, 54, 100, -4.0F, -12.0F, 3.0F, 7, 6, 10, 0.0F, true));

				lowerthingy2 = new BasicModelPart(this);
				lowerthingy2.setRotationPoint(8.0F, -1.0F, 4.0F);
				head.addChild(lowerthingy2);
				setRotationAngle(lowerthingy2, -0.672F, -1.0908F, 0.0F);
				lowerthingy2.cubeList.add(new ModelBox(lowerthingy2, 0, 131, -8.0F, -5.0F, 0.0F, 16, 10, 0, 0.0F, true));

				notsobighorn1 = new BasicModelPart(this);
				notsobighorn1.setRotationPoint(-3.0F, -4.0F, 3.0F);
				head.addChild(notsobighorn1);
				setRotationAngle(notsobighorn1, 0.0873F, -0.48F, -0.2182F);
				notsobighorn1.cubeList.add(new ModelBox(notsobighorn1, 100, 78, -11.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, false));
				notsobighorn1.cubeList.add(new ModelBox(notsobighorn1, 14, 46, -11.0F, -3.0F, -2.0F, 1, 1, 4, 0.0F, false));

				notsobighorn2 = new BasicModelPart(this);
				notsobighorn2.setRotationPoint(3.0F, -4.0F, 3.0F);
				head.addChild(notsobighorn2);
				setRotationAngle(notsobighorn2, 0.0873F, 0.48F, 0.2182F);
				notsobighorn2.cubeList.add(new ModelBox(notsobighorn2, 100, 78, 1.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, true));
				notsobighorn2.cubeList.add(new ModelBox(notsobighorn2, 14, 46, 10.0F, -3.0F, -2.0F, 1, 1, 4, 0.0F, true));

				lowerthingy1 = new BasicModelPart(this);
				lowerthingy1.setRotationPoint(-7.0F, -1.0F, 4.0F);
				head.addChild(lowerthingy1);
				setRotationAngle(lowerthingy1, -0.672F, 1.0908F, 0.0F);
				lowerthingy1.cubeList.add(new ModelBox(lowerthingy1, 0, 131, -8.0F, -5.0F, -1.0F, 16, 10, 0, 0.0F, false));

				firehair = new BasicModelPart(this);
				firehair.setRotationPoint(0.0F, -9.0F, -4.0F);
				head.addChild(firehair);
				setRotationAngle(firehair, -0.7854F, 0.0F, 0.0F);
				firehair.cubeList.add(new ModelBox(firehair, 48, 68, -4.0F, -9.0F, 0.0F, 8, 9, 0, 0.0F, false));

				big_horn = new BasicModelPart(this);
				big_horn.setRotationPoint(-6.0F, -7.0F, 2.0F);
				head.addChild(big_horn);
				setRotationAngle(big_horn, 0.9163F, 1.0908F, 0.0F);
				big_horn.cubeList.add(new ModelBox(big_horn, 0, 13, -4.0F, -14.0F, 12.0F, 7, 2, 1, 0.0F, false));
				big_horn.cubeList.add(new ModelBox(big_horn, 82, 114, -4.0F, -12.0F, -3.0F, 7, 16, 6, 0.0F, false));
				big_horn.cubeList.add(new ModelBox(big_horn, 54, 100, -4.0F, -12.0F, 3.0F, 7, 6, 10, 0.0F, false));

				mask = new BasicModelPart(this);
				mask.setRotationPoint(0.0F, 20.0F, -1.0F);
				head.addChild(mask);
				mask.cubeList.add(new ModelBox(mask, 98, 34, -6.0F, -20.0F, -5.0F, 12, 0, 2, 0.0F, false));
				mask.cubeList.add(new ModelBox(mask, 98, 32, -6.0F, -30.0F, -5.0F, 12, 0, 2, 0.0F, false));
				mask.cubeList.add(new ModelBox(mask, 128, 32, -6.0F, -34.0F, -5.0F, 12, 17, 2, 0.0F, false));
				mask.cubeList.add(new ModelBox(mask, 108, 114, -6.0F, -34.0F, -5.0F, 12, 17, 2, 0.25F, false));

				horn4 = new BasicModelPart(this);
				horn4.setRotationPoint(4.5F, -29.0F, -4.0F);
				mask.addChild(horn4);
				setRotationAngle(horn4, -0.3927F, -0.7854F, 0.0F);
				horn4.cubeList.add(new ModelBox(horn4, 0, 57, -2.5F, -8.0F, -7.0F, 5, 1, 2, 0.0F, true));
				horn4.cubeList.add(new ModelBox(horn4, 0, 0, -2.5F, -8.0F, -5.0F, 5, 10, 3, 0.0F, true));
				horn4.cubeList.add(new ModelBox(horn4, 68, 36, -2.5F, -2.0F, -2.0F, 5, 4, 2, 0.0F, true));
				horn4.cubeList.add(new ModelBox(horn4, 0, 46, -2.5F, -2.0F, 0.0F, 2, 4, 2, 0.0F, true));

				horn2 = new BasicModelPart(this);
				horn2.setRotationPoint(4.0F, -19.0F, -4.0F);
				mask.addChild(horn2);
				setRotationAngle(horn2, 0.3927F, -0.3927F, 0.0F);
				horn2.cubeList.add(new ModelBox(horn2, 0, 46, -1.0F, -2.0F, -8.0F, 3, 3, 8, 0.0F, true));
				horn2.cubeList.add(new ModelBox(horn2, 0, 16, -1.0F, -3.0F, -8.0F, 3, 1, 1, 0.0F, true));

				horn5 = new BasicModelPart(this);
				horn5.setRotationPoint(-4.0F, -19.0F, -4.0F);
				mask.addChild(horn5);
				setRotationAngle(horn5, 0.3927F, 0.3927F, 0.0F);
				horn5.cubeList.add(new ModelBox(horn5, 0, 46, -2.0F, -2.0F, -8.0F, 3, 3, 8, 0.0F, false));
				horn5.cubeList.add(new ModelBox(horn5, 0, 16, -2.0F, -3.0F, -8.0F, 3, 1, 1, 0.0F, false));

				horn3 = new BasicModelPart(this);
				horn3.setRotationPoint(-4.5F, -29.0F, -4.0F);
				mask.addChild(horn3);
				setRotationAngle(horn3, -0.3927F, 0.7854F, 0.0F);
				horn3.cubeList.add(new ModelBox(horn3, 0, 57, -2.5F, -8.0F, -7.0F, 5, 1, 2, 0.0F, false));
				horn3.cubeList.add(new ModelBox(horn3, 0, 0, -2.5F, -8.0F, -5.0F, 5, 10, 3, 0.0F, false));
				horn3.cubeList.add(new ModelBox(horn3, 68, 36, -2.5F, -2.0F, -2.0F, 5, 4, 2, 0.0F, false));
				horn3.cubeList.add(new ModelBox(horn3, 0, 46, 0.5F, -2.0F, 0.0F, 2, 4, 2, 0.0F, false));

				backpart = new BasicModelPart(this);
				backpart.setRotationPoint(0.0F, -9.0F, 4.0F);
				head.addChild(backpart);
				setRotationAngle(backpart, 0.589F, 0.0F, 0.0F);
				backpart.cubeList.add(new ModelBox(backpart, 128, 74, -8.0F, 0.0F, 0.0F, 16, 14, 0, 0.0F, false));

				body = new BasicModelPart(this);
				body.setRotationPoint(0.0F, -39.0F, -4.0F);
				body.cubeList.add(new ModelBox(body, 0, 0, -16.0F, 2.0F, -5.0F, 32, 18, 18, 0.0F, false));
				body.cubeList.add(new ModelBox(body, 0, 36, -11.0F, 0.0F, -8.0F, 22, 8, 24, 0.0F, false));
				body.cubeList.add(new ModelBox(body, 97, 145, -11.0F, 0.0F, 16.0F, 22, 8, 6, 0.0F, false));

				arm2 = new BasicModelPart(this);
				arm2.setRotationPoint(16.0F, 7.0F, 4.0F);
				body.addChild(arm2);
				arm2.cubeList.add(new ModelBox(arm2, 88, 88, -2.0F, -7.0F, -6.0F, 18, 14, 12, 0.0F, true));
				arm2.cubeList.add(new ModelBox(arm2, 85, 0, 2.0F, -13.0F, -6.0F, 14, 6, 12, 0.0F, true));

				lower_arm2 = new BasicModelPart(this);
				lower_arm2.setRotationPoint(8.0F, 29.0F, 0.0F);
				arm2.addChild(lower_arm2);
				lower_arm2.cubeList.add(new ModelBox(lower_arm2, 0, 68, -8.0F, 0.0F, -8.0F, 16, 20, 16, 0.0F, true));
				lower_arm2.cubeList.add(new ModelBox(lower_arm2, 0, 88, 0.0F, -17.0F, -8.0F, 0, 18, 16, 0.0F, true));
				lower_arm2.cubeList.add(new ModelBox(lower_arm2, 0, 104, -8.0F, -17.0F, 0.0F, 16, 18, 0, 0.0F, true));
				lower_arm2.cubeList.add(new ModelBox(lower_arm2, 95, 40, 1.0F, 11.0F, -8.0F, 7, 9, 16, 0.5F, false));

				arm1 = new BasicModelPart(this);
				arm1.setRotationPoint(-16.0F, 7.0F, 4.0F);
				body.addChild(arm1);
				arm1.cubeList.add(new ModelBox(arm1, 88, 88, -16.0F, -7.0F, -6.0F, 18, 14, 12, 0.0F, false));
				arm1.cubeList.add(new ModelBox(arm1, 85, 0, -16.0F, -13.0F, -6.0F, 14, 6, 12, 0.0F, false));

				lower_arm = new BasicModelPart(this);
				lower_arm.setRotationPoint(-8.0F, 29.0F, 0.0F);
				arm1.addChild(lower_arm);
				lower_arm.cubeList.add(new ModelBox(lower_arm, 0, 68, -8.0F, 0.0F, -8.0F, 16, 20, 16, 0.0F, false));
				lower_arm.cubeList.add(new ModelBox(lower_arm, 0, 104, -8.0F, -17.0F, 0.0F, 16, 18, 0, 0.0F, false));
				lower_arm.cubeList.add(new ModelBox(lower_arm, 95, 40, -8.0F, 11.0F, -8.0F, 7, 9, 16, 0.5F, true));
				lower_arm.cubeList.add(new ModelBox(lower_arm, 95, 40, -8.0F, 11.0F, -8.0F, 7, 9, 16, 0.5F, true));
				lower_arm.cubeList.add(new ModelBox(lower_arm, 0, 88, 0.0F, -17.0F, -8.0F, 0, 18, 16, 0.0F, false));

				lowerbody = new BasicModelPart(this);
				lowerbody.setRotationPoint(0.0F, 20.0F, 4.0F);
				body.addChild(lowerbody);
				lowerbody.cubeList.add(new ModelBox(lowerbody, 19, 134, -9.0F, 0.0F, -7.0F, 18, 12, 14, 0.0F, false));

				firetrail = new BasicModelPart(this);
				firetrail.setRotationPoint(0.0F, 11.0F, 0.0F);
				lowerbody.addChild(firetrail);
				firetrail.cubeList.add(new ModelBox(firetrail, 64, 68, -9.0F, 0.0F, 0.0F, 18, 32, 0, 0.0F, false));
				firetrail.cubeList.add(new ModelBox(firetrail, 64, 50, 0.0F, 1.0F, -9.0F, 0, 32, 18, 0.0F, false));

				spin = new BasicModelPart(this);
				spin.setRotationPoint(0.0F, 6.0F, 0.0F);
				firetrail.addChild(spin);
				spin.cubeList.add(new ModelBox(spin, 0, 68, -14.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, false));
				spin.cubeList.add(new ModelBox(spin, 0, 68, 10.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, true));

				bone2 = new BasicModelPart(this);
				bone2.setRotationPoint(0.0F, 2.0F, -12.0F);
				spin.addChild(bone2);
				setRotationAngle(bone2, 0.0F, 1.5708F, 0.0F);
				bone2.cubeList.add(new ModelBox(bone2, 0, 68, -2.0F, -4.0F, -2.0F, 4, 8, 4, 0.0F, true));

				bone3 = new BasicModelPart(this);
				bone3.setRotationPoint(0.0F, 2.0F, 12.0F);
				spin.addChild(bone3);
				setRotationAngle(bone3, 0.0F, -1.5708F, 0.0F);
				bone3.cubeList.add(new ModelBox(bone3, 0, 68, -2.0F, -4.0F, -2.0F, 4, 8, 4, 0.0F, true));

		//ALWAYS include this in the bottom, first statement sets this as the default pose
		//that way the animator knows what default is and after each animation will go back to it's original pose
		this.updateDefaultPose();

		this.animator = EZModelAnimator.create();
			}

	@Override
	public Iterable<BasicModelPart> getAllParts() {
		return ImmutableList.of(head, big_horn, big_horn2, notsobighorn1, notsobighorn2, firehair, firetrail, lowerthingy1, lowerthingy2, lower_arm, lower_arm2
		, lowerbody, mask, horn2, horn3, horn4, horn5, bone2, bone3, arm1, arm2, body, backpart, spin);
	}

	@Override
			public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
				head.render(f5);
				body.render(f5);
			}

			public void setRotationAngle(BasicModelPart BasicModelPart, float x, float y, float z) {
				BasicModelPart.rotateAngleX = x;
				BasicModelPart.rotateAngleY = y;
				BasicModelPart.rotateAngleZ = z;
			}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		//this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		//this.head.rotateAngleX = headPitch * 0.017453292F;
		//this.head.rotationPointY = -43F - MathHelper.cos((2 + ageInTicks) * 0.10F);
		this.faceTarget(netHeadYaw, headPitch, 1, head);
		this.spin.rotationPointY = 8.0F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.25F);
		this.spin.rotateAngleY = ageInTicks * (float)Math.PI * -0.1F;
		//haha funny spin
		//this.swing(lower_arm2, 0.1F, 10F, false, 3F, 0.1F,ageInTicks, MathHelper.cos(((4 * 2) + ageInTicks) * 0.05F));

		if(entityIn instanceof EntityFireElemental) {
			if(!((EntityFireElemental)entityIn).isFightMode()) {
				this.lower_arm.rotationPointY = 29F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.05F);
				this.lower_arm2.rotationPointY = 29F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.07F);

				this.lower_arm.rotationPointX = -10.0F - MathHelper.cos((ageInTicks) * 0.2F);
				this.lower_arm2.rotationPointX = 10.0F + MathHelper.cos((ageInTicks) * 0.2F);
			}
		}

	}

	//this is where you do set animations
	@Override
	public void animate(IAnimatedEntity entity) {
		animator.update(entity);

		//Punch Attack Animation
		animator.setAnimation(EntityFireElemental.ANIMATION_PUNCH);
		//
		animator.startKeyframe(10);
		animator.rotate(arm2, 0, 0, (float) Math.toRadians(-10));
		animator.rotate(arm1, (float) Math.toRadians(-170), 0, (float) Math.toRadians(-30));
		animator.move(lower_arm, 0, -20, 0);
		animator.rotate(body, 0, (float) Math.toRadians(20), 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(5);
		//
		animator.startKeyframe(5);
		animator.rotate(body, 0, (float) Math.toRadians(-30), 0);
		animator.rotate(arm2, (float) Math.toRadians(20), 0, (float) Math.toRadians(-10));
		animator.rotate(arm1, (float) Math.toRadians(-60), (float) Math.toRadians(10), 0);
		//animator.move(lower_arm, 0, -20 ,0);
		animator.endKeyframe();
		//
		animator.startKeyframe(4);
		animator.move(lower_arm, 0, 30, 0);
		animator.rotate(body, 0, (float) Math.toRadians(-30), 0);
		animator.rotate(arm2, (float) Math.toRadians(10), 0, (float) Math.toRadians(-10));
		animator.rotate(arm1, (float) Math.toRadians(-60), (float) Math.toRadians(10), 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(7);
		//
		animator.startKeyframe(4);
		animator.move(lower_arm, 0, 30, 0);
		animator.rotate(arm2, (float) Math.toRadians(10), 0, (float) Math.toRadians(-5));
		animator.rotate(arm1, (float) Math.toRadians(-60), (float) Math.toRadians(10), 0);
		animator.rotate(body, 0, (float) Math.toRadians(-30), 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(5);
		//
		animator.resetKeyframe(15);
		//End Animation
		animator.setAnimation(EntityFireElemental.ANIMATION_SMASH_GROUND);
		//
		animator.startKeyframe(10);
		animator.rotate(body, (float) Math.toRadians(-10), 0, 0);
		animator.move(body, 0, -10, 0);
		animator.move(head, 0, -10, 0);
		animator.rotate(arm1, (float) Math.toRadians(-75), 0, (float) Math.toRadians(-10));
		animator.rotate(arm2, (float) Math.toRadians(-75), 0, (float) Math.toRadians(10));
		animator.endKeyframe();
		//
		animator.startKeyframe(10);
		animator.rotate(body, (float) Math.toRadians(-20), 0, 0);
		animator.move(body, 0, -10, 0);
		animator.move(head, 0, -10, 0);
		animator.rotate(arm1, (float) Math.toRadians(-150), 0, (float) Math.toRadians(-20));
		animator.rotate(arm2, (float) Math.toRadians(-150), 0, (float) Math.toRadians(20));
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(10);
		//
		animator.startKeyframe(5);
		animator.rotate(arm1, (float) Math.toRadians(-30), 0, 0);
		animator.rotate(arm2, (float) Math.toRadians(-30), 0, 0);
		animator.rotate(body, (float) Math.toRadians(30), 0, 0);
		animator.move(body, 0, -28, 0);
		animator.move(head, 0, -28, 0);
		animator.endKeyframe();
		//
		animator.startKeyframe(5);
		animator.move(body, 0, 8, 0);
		animator.move(head, 0, 8, 0);
		animator.rotate(body, (float) Math.toRadians(15), 0,0);
		animator.rotate(arm2, (float) Math.toRadians(-15), 0, 0);
		animator.rotate(arm1, (float) Math.toRadians(-15), 0, 0);
		animator.endKeyframe();
		//
		animator.resetKeyframe(10);
		//End Animation
		animator.setAnimation(EntityFireElemental.ANIMATION_SUMMONS);
		//
		animator.startKeyframe(10);
		animator.rotate(arm2, 0, 0, (float) Math.toRadians(-50));
		animator.rotate(arm1, 0, 0, (float) Math.toRadians(50));
		animator.move(head, 0, -5, 0);
		animator.move(body, 0, -5, 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(5);
		//
		animator.startKeyframe(5);
		animator.rotate(arm1, 0, 0, 0);
		animator.rotate(arm2, 0, 0, 0);
		animator.move(head, 0, 10, 0);
		animator.move(body, 0, 10, 0);
		animator.endKeyframe();
		//
		animator.startKeyframe(5);
		animator.move(lower_arm2, 0, 20, 0);
		animator.move(lower_arm, 0,20,0);
		animator.rotate(arm1, 0, 0, 0);
		animator.rotate(arm2, 0, 0, 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(35);
		//
		animator.resetKeyframe(15);

		animator.setAnimation(EntityFireElemental.ANIMATION_PUSH);
		//
		animator.startKeyframe(10);
		animator.rotate(body, 0, (float) Math.toRadians(30), 0);
		animator.rotate(arm2, (float) Math.toRadians(-50), (float) Math.toRadians(50), 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(5);
		//
		animator.startKeyframe(5);
		animator.rotate(body, 0, (float) Math.toRadians(-10), 0);
		animator.rotate(arm2, (float) Math.toRadians(-100), (float) Math.toRadians(-40), 0);
		animator.move(arm2, 0, 10, 0);
		animator.endKeyframe();
		//
		animator.resetKeyframe(10);
		// Get over here
		animator.setAnimation(EntityFireElemental.ANIMATION_METEOR_SHOWER);
		//
		animator.startKeyframe(10);
		animator.rotate(arm2, (float) Math.toRadians(-30), (float) Math.toRadians(-15), 0);
		animator.rotate(arm1, (float) Math.toRadians(-30), (float) Math.toRadians(15), 0);
		animator.rotate(body, (float) Math.toRadians(-5), 0, 0);
		animator.rotate(head, (float) Math.toRadians(-15), 0,0);
		animator.endKeyframe();
		//
		animator.startKeyframe(10);
		animator.rotate(arm2, (float) Math.toRadians(-30), (float) Math.toRadians(-15), 0);
		animator.rotate(arm1, (float) Math.toRadians(-30), (float) Math.toRadians(15), 0);
		animator.rotate(body, (float) Math.toRadians(-5), 0, 0);
		animator.rotate(head, (float) Math.toRadians(-15), 0, 0);
		animator.endKeyframe();
		//
		animator.setStaticKeyframe(25);
		//
		animator.resetKeyframe(20);
		//just make sure the duration of the animation doens't exceed ANIMATION_EXAMPLE time which is 20
	}
}