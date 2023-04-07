package com.unoriginal.beastslayer.entity.Model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFireElemental extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer big_horn2;
	private final ModelRenderer lowerthingy2;
	private final ModelRenderer notsobighorn1;
	private final ModelRenderer notsobighorn2;
	private final ModelRenderer lowerthingy1;
	private final ModelRenderer bone;
	private final ModelRenderer big_horn;
	private final ModelRenderer mask;
	private final ModelRenderer horn4;
	private final ModelRenderer horn2;
	private final ModelRenderer horn1;
	private final ModelRenderer horn3;
	private final ModelRenderer backpart;
	private final ModelRenderer body;
	private final ModelRenderer arm2;
	private final ModelRenderer lower_arm2;
	private final ModelRenderer arm1;
	private final ModelRenderer lower_arm;
	private final ModelRenderer lowerbody;
	private final ModelRenderer firetrail;
	private final ModelRenderer spin;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;

	public ModelFireElemental() {
		textureWidth = 160;
		textureHeight = 160;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -39.0F, -3.0F);
		head.cubeList.add(new ModelBox(head, 24, 114, -4.0F, -9.0F, -4.0F, 8, 9, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 24, 0.0F, -16.0F, -4.0F, 0, 10, 12, 0.0F, false));

		big_horn2 = new ModelRenderer(this);
		big_horn2.setRotationPoint(6.0F, -7.0F, 2.0F);
		head.addChild(big_horn2);
		setRotationAngle(big_horn2, 0.9163F, -1.0908F, 0.0F);
		big_horn2.cubeList.add(new ModelBox(big_horn2, 0, 13, -4.0F, -14.0F, 12.0F, 7, 2, 1, 0.0F, true));
		big_horn2.cubeList.add(new ModelBox(big_horn2, 82, 114, -4.0F, -12.0F, -3.0F, 7, 16, 6, 0.0F, true));
		big_horn2.cubeList.add(new ModelBox(big_horn2, 54, 100, -4.0F, -12.0F, 3.0F, 7, 6, 10, 0.0F, true));

		lowerthingy2 = new ModelRenderer(this);
		lowerthingy2.setRotationPoint(8.0F, -1.0F, 4.0F);
		head.addChild(lowerthingy2);
		setRotationAngle(lowerthingy2, -0.672F, -1.0908F, 0.0F);
		lowerthingy2.cubeList.add(new ModelBox(lowerthingy2, 0, 131, -8.0F, -5.0F, 0.0F, 16, 10, 0, 0.0F, true));

		notsobighorn1 = new ModelRenderer(this);
		notsobighorn1.setRotationPoint(-3.0F, -4.0F, 3.0F);
		head.addChild(notsobighorn1);
		setRotationAngle(notsobighorn1, 0.0873F, -0.48F, -0.2182F);
		notsobighorn1.cubeList.add(new ModelBox(notsobighorn1, 100, 78, -11.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, false));
		notsobighorn1.cubeList.add(new ModelBox(notsobighorn1, 14, 46, -11.0F, -3.0F, -2.0F, 1, 1, 4, 0.0F, false));

		notsobighorn2 = new ModelRenderer(this);
		notsobighorn2.setRotationPoint(3.0F, -4.0F, 3.0F);
		head.addChild(notsobighorn2);
		setRotationAngle(notsobighorn2, 0.0873F, 0.48F, 0.2182F);
		notsobighorn2.cubeList.add(new ModelBox(notsobighorn2, 100, 78, 1.0F, -2.0F, -2.0F, 10, 4, 4, 0.0F, true));
		notsobighorn2.cubeList.add(new ModelBox(notsobighorn2, 14, 46, 10.0F, -3.0F, -2.0F, 1, 1, 4, 0.0F, true));

		lowerthingy1 = new ModelRenderer(this);
		lowerthingy1.setRotationPoint(-7.0F, -1.0F, 4.0F);
		head.addChild(lowerthingy1);
		setRotationAngle(lowerthingy1, -0.672F, 1.0908F, 0.0F);
		lowerthingy1.cubeList.add(new ModelBox(lowerthingy1, 0, 131, -8.0F, -5.0F, -1.0F, 16, 10, 0, 0.0F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -9.0F, -4.0F);
		head.addChild(bone);
		setRotationAngle(bone, -0.7854F, 0.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 48, 68, -4.0F, -9.0F, 0.0F, 8, 9, 0, 0.0F, false));

		big_horn = new ModelRenderer(this);
		big_horn.setRotationPoint(-6.0F, -7.0F, 2.0F);
		head.addChild(big_horn);
		setRotationAngle(big_horn, 0.9163F, 1.0908F, 0.0F);
		big_horn.cubeList.add(new ModelBox(big_horn, 0, 13, -4.0F, -14.0F, 12.0F, 7, 2, 1, 0.0F, false));
		big_horn.cubeList.add(new ModelBox(big_horn, 82, 114, -4.0F, -12.0F, -3.0F, 7, 16, 6, 0.0F, false));
		big_horn.cubeList.add(new ModelBox(big_horn, 54, 100, -4.0F, -12.0F, 3.0F, 7, 6, 10, 0.0F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, 20.0F, -1.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 98, 34, -6.0F, -20.0F, -5.0F, 12, 0, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 98, 32, -6.0F, -30.0F, -5.0F, 12, 0, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 128, 32, -6.0F, -34.0F, -5.0F, 12, 17, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 108, 114, -6.0F, -34.0F, -5.0F, 12, 17, 2, 0.25F, false));

		horn4 = new ModelRenderer(this);
		horn4.setRotationPoint(4.5F, -29.0F, -4.0F);
		mask.addChild(horn4);
		setRotationAngle(horn4, -0.3927F, -0.7854F, 0.0F);
		horn4.cubeList.add(new ModelBox(horn4, 0, 57, -2.5F, -8.0F, -7.0F, 5, 1, 2, 0.0F, true));
		horn4.cubeList.add(new ModelBox(horn4, 0, 0, -2.5F, -8.0F, -5.0F, 5, 10, 3, 0.0F, true));
		horn4.cubeList.add(new ModelBox(horn4, 68, 36, -2.5F, -2.0F, -2.0F, 5, 4, 2, 0.0F, true));
		horn4.cubeList.add(new ModelBox(horn4, 0, 46, -2.5F, -2.0F, 0.0F, 2, 4, 2, 0.0F, true));

		horn2 = new ModelRenderer(this);
		horn2.setRotationPoint(4.0F, -19.0F, -4.0F);
		mask.addChild(horn2);
		setRotationAngle(horn2, 0.3927F, -0.3927F, 0.0F);
		horn2.cubeList.add(new ModelBox(horn2, 0, 46, -1.0F, -2.0F, -8.0F, 3, 3, 8, 0.0F, true));
		horn2.cubeList.add(new ModelBox(horn2, 0, 16, -1.0F, -3.0F, -8.0F, 3, 1, 1, 0.0F, true));

		horn1 = new ModelRenderer(this);
		horn1.setRotationPoint(-4.0F, -20.0F, -4.0F);
		mask.addChild(horn1);
		setRotationAngle(horn1, 0.3927F, 0.3927F, 0.0F);
		horn1.cubeList.add(new ModelBox(horn1, 0, 46, -2.0F, -1.0F, -8.0F, 3, 3, 8, 0.0F, false));
		horn1.cubeList.add(new ModelBox(horn1, 0, 16, -2.0F, -2.0F, -8.0F, 3, 1, 1, 0.0F, false));

		horn3 = new ModelRenderer(this);
		horn3.setRotationPoint(-4.5F, -29.0F, -4.0F);
		mask.addChild(horn3);
		setRotationAngle(horn3, -0.3927F, 0.7854F, 0.0F);
		horn3.cubeList.add(new ModelBox(horn3, 0, 57, -2.5F, -8.0F, -7.0F, 5, 1, 2, 0.0F, false));
		horn3.cubeList.add(new ModelBox(horn3, 0, 0, -2.5F, -8.0F, -5.0F, 5, 10, 3, 0.0F, false));
		horn3.cubeList.add(new ModelBox(horn3, 68, 36, -2.5F, -2.0F, -2.0F, 5, 4, 2, 0.0F, false));
		horn3.cubeList.add(new ModelBox(horn3, 0, 46, 0.5F, -2.0F, 0.0F, 2, 4, 2, 0.0F, false));

		backpart = new ModelRenderer(this);
		backpart.setRotationPoint(0.0F, -9.0F, 4.0F);
		head.addChild(backpart);
		setRotationAngle(backpart, 0.589F, 0.0F, 0.0F);
		backpart.cubeList.add(new ModelBox(backpart, 128, 78, -8.0F, 0.0F, 0.0F, 16, 14, 0, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -36.0F, -4.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -16.0F, 2.0F, -5.0F, 32, 18, 18, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 36, -11.0F, 0.0F, -7.0F, 22, 8, 24, 0.0F, false));

		arm2 = new ModelRenderer(this);
		arm2.setRotationPoint(14.0F, 7.0F, 4.0F);
		body.addChild(arm2);
		arm2.cubeList.add(new ModelBox(arm2, 88, 88, 0.0F, -7.0F, -6.0F, 14, 14, 12, 0.0F, true));

		lower_arm2 = new ModelRenderer(this);
		lower_arm2.setRotationPoint(10.0F, 29.0F, 0.0F);
		arm2.addChild(lower_arm2);
		lower_arm2.cubeList.add(new ModelBox(lower_arm2, 0, 68, -8.0F, 0.0F, -8.0F, 16, 20, 16, 0.0F, true));
		lower_arm2.cubeList.add(new ModelBox(lower_arm2, 0, 88, 0.0F, -18.0F, -8.0F, 0, 18, 16, 0.0F, true));
		lower_arm2.cubeList.add(new ModelBox(lower_arm2, 100, 60, -8.0F, -17.0F, 0.0F, 16, 18, 0, 0.0F, true));

		arm1 = new ModelRenderer(this);
		arm1.setRotationPoint(-14.0F, 7.0F, 4.0F);
		body.addChild(arm1);
		arm1.cubeList.add(new ModelBox(arm1, 88, 88, -14.0F, -7.0F, -6.0F, 14, 14, 12, 0.0F, false));

		lower_arm = new ModelRenderer(this);
		lower_arm.setRotationPoint(-10.0F, 29.0F, 0.0F);
		arm1.addChild(lower_arm);
		lower_arm.cubeList.add(new ModelBox(lower_arm, 0, 68, -8.0F, 0.0F, -8.0F, 16, 20, 16, 0.0F, false));
		lower_arm.cubeList.add(new ModelBox(lower_arm, 100, 60, -8.0F, -17.0F, 0.0F, 16, 18, 0, 0.0F, false));
		lower_arm.cubeList.add(new ModelBox(lower_arm, 0, 88, 0.0F, -18.0F, -8.0F, 0, 18, 16, 0.0F, false));

		lowerbody = new ModelRenderer(this);
		lowerbody.setRotationPoint(0.0F, 20.0F, 4.0F);
		body.addChild(lowerbody);
		lowerbody.cubeList.add(new ModelBox(lowerbody, 68, 36, -8.0F, 0.0F, -7.0F, 16, 10, 14, 0.0F, false));

		firetrail = new ModelRenderer(this);
		firetrail.setRotationPoint(0.0F, 8.0F, 0.0F);
		lowerbody.addChild(firetrail);
		firetrail.cubeList.add(new ModelBox(firetrail, 100, 0, -9.0F, 0.0F, 0.0F, 18, 32, 0, 0.0F, false));
		firetrail.cubeList.add(new ModelBox(firetrail, 64, 50, 0.0F, 0.0F, -9.0F, 0, 32, 18, 0.0F, false));

		spin = new ModelRenderer(this);
		spin.setRotationPoint(0.0F, 8.0F, 0.0F);
		firetrail.addChild(spin);
		spin.cubeList.add(new ModelBox(spin, 0, 68, -14.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, false));
		spin.cubeList.add(new ModelBox(spin, 0, 68, 10.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F, true));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 2.0F, -12.0F);
		spin.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 1.5708F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 68, -2.0F, -4.0F, -2.0F, 4, 8, 4, 0.0F, true));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 2.0F, 12.0F);
		spin.addChild(bone3);
		setRotationAngle(bone3, 0.0F, -1.5708F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 0, 68, -2.0F, -4.0F, -2.0F, 4, 8, 4, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		head.render(f5);
		body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotationPointY = -39F - MathHelper.cos((2 + ageInTicks) * 0.10F);

		this.spin.rotationPointY = 8.0F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.25F);
		this.spin.rotateAngleY = ageInTicks * (float)Math.PI * -0.1F;

		this.lower_arm.rotationPointY = 29F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.05F);
		this.lower_arm2.rotationPointY = 29F + MathHelper.cos(((4 * 2) + ageInTicks) * 0.07F);

		this.lower_arm.rotationPointX = -10.0F - MathHelper.cos((ageInTicks) * 0.2F);
		this.lower_arm2.rotationPointX = 10.0F + MathHelper.cos((ageInTicks) * 0.2F);
	}
}