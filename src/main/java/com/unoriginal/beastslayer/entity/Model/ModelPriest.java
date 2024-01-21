package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPriest extends ModelBase {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer head;
	private final ModelRenderer mask;
	private final ModelRenderer bone3;
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer hood;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer cloak;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer leftFoot;
	private final ModelRenderer RightLeg;
	private final ModelRenderer rightFoot;

	public ModelPriest() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 20, 41, -3.0F, -9.0F, -1.5F, 6, 6, 3, 0.25F, false));
		body.cubeList.add(new ModelBox(body, 0, 32, -3.0F, -2.0F, -2.0F, 6, 10, 4, 0.5F, false));
		body.cubeList.add(new ModelBox(body, 36, 0, -3.0F, -9.0F, -1.5F, 6, 11, 3, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 51, 86, -6.0F, -11.0F, -2.0F, 12, 7, 7, 0.5F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 1.0F, 1.5F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 24, 8, -1.0F, -8.0F, 0.0F, 2, 8, 8, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 17, 0, -2.0F, -8.0F, 7.0F, 4, 0, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -11.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 16, -4.0F, -7.9F, -4.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 66, -4.0F, -8.0F, -4.0F, 8, 10, 8, 0.25F, false));

		mask = new ModelRenderer(this);
		mask.setRotationPoint(0.0F, -4.0F, -3.0F);
		head.addChild(mask);
		mask.cubeList.add(new ModelBox(mask, 30, 30, -5.0F, -4.0F, -2.0F, 10, 9, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 48, 46, -5.0F, -12.0F, -2.0F, 3, 8, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 1, -6.0F, -12.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 1, 5.0F, -12.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 48, 46, 2.0F, -12.0F, -2.0F, 3, 8, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 47, 57, -5.0F, 5.0F, -2.0F, 3, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 47, 57, 2.0F, 5.0F, -2.0F, 3, 1, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 38, 41, -15.0F, 0.0F, -2.0F, 10, 3, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 38, 41, 5.0F, 0.0F, -2.0F, 10, 3, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 0, 16, -15.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, false));
		mask.cubeList.add(new ModelBox(mask, 0, 16, 14.0F, -1.0F, -2.0F, 1, 1, 2, 0.0F, true));
		mask.cubeList.add(new ModelBox(mask, 4, 4, -1.0F, -7.0F, -1.0F, 2, 3, 0, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, -4.0F, 0.0F);
		mask.addChild(bone3);
		setRotationAngle(bone3, -0.7854F, 0.0F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 17, 116, -7.0F, -12.0F, 0.0F, 14, 12, 0, 0.0F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-5.0F, 0.0F, 0.0F);
		mask.addChild(bone);
		setRotationAngle(bone, 0.0F, 0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 1, 116, -7.0F, -7.0F, 0.0F, 7, 12, 0, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(5.0F, 0.0F, 0.0F);
		mask.addChild(bone2);
		setRotationAngle(bone2, 0.0F, -0.7854F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 1, 116, 0.0F, -7.0F, 0.0F, 7, 12, 0, 0.0F, true));

		hood = new ModelRenderer(this);
		hood.setRotationPoint(1.0F, -7.0F, 5.0F);
		head.addChild(hood);
		setRotationAngle(hood, -0.7854F, 0.0F, 0.0F);
		hood.cubeList.add(new ModelBox(hood, 33, 75, -4.0F, -0.1036F, -1.1642F, 6, 3, 6, 0.25F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(3.0F, -8.0F, 0.0F);
		body.addChild(LeftArm);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 14, 51, 0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, true));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-3.0F, -8.0F, 0.0F);
		body.addChild(RightArm);
		RightArm.cubeList.add(new ModelBox(RightArm, 14, 51, -2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F, false));

		cloak = new ModelRenderer(this);
		cloak.setRotationPoint(0.0F, -9.0F, 0.0F);
		body.addChild(cloak);
		cloak.cubeList.add(new ModelBox(cloak, 0, 84, -8.0F, -2.0F, -2.0F, 16, 12, 8, 0.25F, false));
		cloak.cubeList.add(new ModelBox(cloak, 80, 102, -8.0F, 1.0F, -2.0F, 16, 18, 8, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(3.0F, 14.0F, -1.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 44, 14, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, true));

		leftFoot = new ModelRenderer(this);
		leftFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		LeftLeg.addChild(leftFoot);
		leftFoot.cubeList.add(new ModelBox(leftFoot, 30, 53, -1.0F, -1.0F, 0.0F, 2, 8, 2, 0.0F, true));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-3.0F, 14.0F, -1.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 44, 14, -1.0F, -1.0F, -2.0F, 2, 5, 5, 0.05F, false));

		rightFoot = new ModelRenderer(this);
		rightFoot.setRotationPoint(0.0F, 3.0F, 3.0F);
		RightLeg.addChild(rightFoot);
		rightFoot.cubeList.add(new ModelBox(rightFoot, 30, 53, -1.0F, -1.0F, 0.0F, 2, 8, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
		RightLeg.render(f5);
		LeftLeg.render(f5);
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
		this.head.rotateAngleZ = 0F;
		this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.RightArm.rotateAngleZ = 0F;
		this.LeftArm.rotateAngleZ = 0F;
		this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleX= -25.0F * (float) Math.PI /180F;
		this.tail.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.tail.rotateAngleY = 0F;
		this.tail.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F;

		this.body.rotateAngleY= 0.0F;
		this.body.rotateAngleX= 0.0F;
		this.RightArm.rotateAngleY = 0F;
		this.LeftArm.rotateAngleY = 0F;

		if(entityIn instanceof AbstractTribesmen){
			AbstractTribesmen tribesmen = (AbstractTribesmen) entityIn;
			/** fiery idle anim **/
			if(tribesmen.isFiery()) {
				if(tribesmen.getFieryTicks() <= 0 ) {
					this.body.rotateAngleX = 22.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 0.9F) * 0.01F;
					this.head.rotateAngleX -= 22.5F * (float) Math.PI / 180F;
					this.head.rotateAngleZ -= 22.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.RightArm.rotateAngleX -= 22.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.LeftArm.rotateAngleX -= 22.5F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					if (tribesmen.getAttackTarget() != null) {
						this.RightArm.rotateAngleX = -(float) Math.PI / 1.25F;
						this.LeftArm.rotateAngleX = -(float) Math.PI / 1.25F;
					}
				}
				/** fiery transform anim **/
				else if (tribesmen.getFieryTicks() > 0)
				{
					this.body.rotateAngleX = -32.5F * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.05F;

					this.RightArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.LeftArm.rotateAngleX += 35F * (float) Math.PI / 180F - MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.RightArm.rotateAngleZ -= -17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.LeftArm.rotateAngleZ -= 17.5 * (float) Math.PI / 180F + MathHelper.sin(ageInTicks * 2.5F) * 0.01F;

					this.head.rotateAngleX -= 40F * (float) Math.PI / 180F;
					this.head.rotateAngleX += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
					this.head.rotateAngleY += MathHelper.sin(ageInTicks * 2.5F) * 0.01F;
				}
			}
			else if (!tribesmen.isFiery()) {
				this.mask.rotateAngleZ = MathHelper.sin(ageInTicks * 0.067F) * 0.025F;
				/** trading anim**/
				if(tribesmen.isTrading()){
					if(tribesmen.getPrimaryHand() == EnumHandSide.LEFT){
						this.LeftArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleY = 13F * (float) Math.PI / 180F;
						this.LeftArm.rotateAngleZ = -12F * (float) Math.PI / 180F;

					}
					else if (tribesmen.getPrimaryHand() == EnumHandSide.RIGHT)
					{
						this.RightArm.rotateAngleX = -47.5F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleY = -13F * (float) Math.PI / 180F;
						this.RightArm.rotateAngleZ = 12F * (float) Math.PI / 180F;
					}
					this.head.rotateAngleX = 22.5F * (float) Math.PI / 180F;
				}
				if(entityIn instanceof EntityPriest){
					EntityPriest p = (EntityPriest) entityIn;
					if(p.getMagicUseTicksClient() >=0 ){
						if(!p.isFiery()) {
							int i = 60;
							int j = i - p.getMagicUseTicksClient();

							double bodyX = Math.toRadians(-0.625F * j * j + 4.375F * j);
							double r_armX = Math.toRadians(3.140625F * j * j - 40.9375 * j);
							double bodyX2 = Math.toRadians(5F * Math.sin(0.3926990816978F * j + 1.5707963267949F));
							double bodyX3 = Math.toRadians(-0.146484375F * j * j * j + 23.3203125F * j * j - 1228.125F * j + 21375F);
							double r_armX2 = Math.toRadians(12.5896783120142F * Math.sin(0.3926990816987F * j + 1.6902252528132) - 114F);
							double r_armX3 = Math.toRadians(0.3834635416667F * j * j * j - 59.575520833333F * j * j + 3067.6875F * j - 52417.5F);
							double headY = Math.toRadians(-0.1573323638593F * j * j + 7.3739714914822F * j - 2.0313160049196F);
							double headY2 = Math.toRadians(-0.5625F * j * j + 59.625F * j - 1552.5F);
							double l_legX = Math.toRadians(-0.1704545454545F * j * j + 8.181818181818F * j);
							double bodyY = Math.toRadians(-0.1278409090909F * j * j + 6.13636363636F * j);
							double r_armY = Math.toRadians(-0.0020714962121F * j * j * j - 0.15625F * j * j + 11.9081439393939F * j);
							double r_armY2 = Math.toRadians(-1.5885416666667F * j * j + 173.020833333333F * j - 4662.5F);
							double bodyY2 = Math.toRadians(-0.703125F * j * j + 75.9375F * j - 2025);
							double l_armX = Math.toRadians(-0.3794642857143F * j * j + 22.7678571428571F * j);
							double l_armY = Math.toRadians(-0.1428571428571F * j * j + 8.5714285714286F * j);
							double headX = Math.toRadians(0.859375F * j * j - 85.9375F * j + 2117.5F);

							if (EnumHandSide.LEFT == tribesmen.getPrimaryHand()) {
								//x is the same, Y changes, left becomes right
								if(j >= 0) {
									if(j <= 8){
										this.body.rotateAngleX =- (float) bodyX;

										this.LeftArm.rotateAngleX = (float) r_armX;
										if(this.LeftArm.rotateAngleX < (float) Math.toRadians(-126.5)){
											this.LeftArm.rotateAngleX = (float) Math.toRadians(-126.5);
										}

									} else if(j<= 32){
										this.body.rotateAngleX = -(float) bodyX2;
									} else if(j <= 44){
										this.body.rotateAngleX = -(float) Math.toRadians(0.20833333333F * j -1.666666666F);
									} else {
										this.body.rotateAngleX =- (float) bodyX3;
									}
									if(j <=44){
										this.LeftArm.rotateAngleX = (float) r_armX2;
										if(this.LeftArm.rotateAngleX < (float) Math.toRadians(-126.5)){
											this.LeftArm.rotateAngleX = (float) Math.toRadians(-126.5);
										}
									} else if(j <= 60){
										this.LeftArm.rotateAngleX = (float) r_armX3;
										if(this.LeftArm.rotateAngleX < (float) Math.toRadians(-112.5)){
											this.LeftArm.rotateAngleX = (float) Math.toRadians(-112.5);
										}
									}

									if (j <= 46) {
										this.head.rotateAngleY = (float) headY;
									} else {
										this.head.rotateAngleY =(float) headY2;
									}
									if (j <= 48) {
										this.RightLeg.rotateAngleX =- (float) l_legX;
										this.body.rotateAngleY = -(float) bodyY;

										this.LeftArm.rotateAngleY =- (float) r_armY;

									} else if(j <= 60){
										this.LeftArm.rotateAngleY = -(float) r_armY2;
									}
									else {
										this.RightLeg.rotateAngleX = 0.0F;
										this.body.rotateAngleY = -(float) bodyY2;
									}

									if(j <= 56){
										this.RightArm.rotateAngleX = -(float) l_armX;
										this.RightArm.rotateAngleY = (float) l_armY;
										if(j > 44){
											this.head.rotateAngleX = (float) headX;
										} else {
											this.head.rotateAngleX = 0.0F;
										}
									} else {
										this.RightArm.rotateAngleX = 0.0F;
										this.RightArm.rotateAngleY = 0.0F;
										this.head.rotateAngleX = 0.0F;
									}

								}

								if(this.RightLeg.rotateAngleX <  (float)Math.toRadians(-30F)){
									this.RightLeg.rotateAngleX =  (float)Math.toRadians(-30F);
								}

								if(this.RightArm.rotateAngleX < (float)Math.toRadians(-85F)){
									this.RightArm.rotateAngleX =  (float)Math.toRadians(-85F);
								}
								if(this.RightArm.rotateAngleY >  (float)Math.toRadians(32F)){
									this.RightArm.rotateAngleY =  (float)Math.toRadians(32F);
								}

								if(this.head.rotateAngleY > (float)Math.toRadians(22.5F)){
									this.head.rotateAngleY =  (float)Math.toRadians(22.5F);
								}

								if(this.head.rotateAngleX < (float)Math.toRadians(-27.5F)){
									this.head.rotateAngleX =  (float)Math.toRadians(-27.5F);
								}

								if(this.body.rotateAngleX < (float)Math.toRadians(-7.5D)){
									this.body.rotateAngleX =  (float)Math.toRadians(-7.5D);
								}
								if(this.body.rotateAngleX >  (float)Math.toRadians(45D)){
									this.body.rotateAngleX =  (float)Math.toRadians(45D);
								}
								if(this.body.rotateAngleY <  (float)Math.toRadians(-22.5D)){
									this.body.rotateAngleY =  (float)Math.toRadians(-22.5D);
								}

								if(this.LeftArm.rotateAngleY <  (float)Math.toRadians(-45D)){
									this.LeftArm.rotateAngleY =  (float)Math.toRadians(-45D);
								}
								if(this.LeftArm.rotateAngleY >  (float)Math.toRadians(17.5D)){
									this.LeftArm.rotateAngleY =  (float)Math.toRadians(17.5D);
								}






							} else if (EnumHandSide.RIGHT == tribesmen.getPrimaryHand()) {
								//anim duration 3 seconds
								//1 sec = 20 ticks so 0.2 sec = 4 ticks
								//I NEED to find an optimized way to put this
								if(j >= 0) {
									if(j <= 8){
										this.body.rotateAngleX =- (float) bodyX;

										this.RightArm.rotateAngleX = (float) r_armX;
										if(this.RightArm.rotateAngleX < (float) Math.toRadians(-126.5)){
											this.RightArm.rotateAngleX = (float) Math.toRadians(-126.5);
										}

									} else if(j<= 32){
										this.body.rotateAngleX = -(float) bodyX2;
									} else if(j <= 44){
										this.body.rotateAngleX = -(float) Math.toRadians(0.20833333333F * j -1.666666666F);
									} else {
										this.body.rotateAngleX =- (float) bodyX3;
									}
									if(j <=44){
										this.RightArm.rotateAngleX = (float) r_armX2;
										if(this.RightArm.rotateAngleX < (float) Math.toRadians(-126.5)){
											this.RightArm.rotateAngleX = (float) Math.toRadians(-126.5);
										}
									} else if(j <= 60){
										this.RightArm.rotateAngleX = (float) r_armX3;
										if(this.RightArm.rotateAngleX < (float) Math.toRadians(-112.5)){
											this.RightArm.rotateAngleX = (float) Math.toRadians(-112.5);
										}
									}

									if (j <= 46) {
										this.head.rotateAngleY = -(float) headY;
									} else {
										this.head.rotateAngleY =-(float) headY2;
									}
									if (j <= 48) {
										this.LeftLeg.rotateAngleX =- (float) l_legX;
										this.body.rotateAngleY = (float) bodyY;

										this.RightArm.rotateAngleY = (float) r_armY;
									} else if ( j <= 60){
										this.RightArm.rotateAngleY = (float) r_armY2;
									}
									else {
										this.LeftLeg.rotateAngleX = 0.0F;
										this.body.rotateAngleY = (float) bodyY2;
									}

									if(j <= 56){
										this.LeftArm.rotateAngleX = -(float) l_armX;
										this.LeftArm.rotateAngleY = -(float) l_armY;
										if(j > 44){
											this.head.rotateAngleX = (float) headX;
										} else {
											this.head.rotateAngleX = 0.0F;
										}
									} else {
										this.LeftArm.rotateAngleX = 0.0F;
										this.LeftArm.rotateAngleY = 0.0F;
										this.head.rotateAngleX = 0.0F;
									}

								}

								if(this.LeftLeg.rotateAngleX <  (float)Math.toRadians(-30F)){
									this.LeftLeg.rotateAngleX =  (float)Math.toRadians(-30F);
								}

								if(this.LeftArm.rotateAngleX < (float)Math.toRadians(-85F)){
									this.LeftArm.rotateAngleX =  (float)Math.toRadians(-85F);
								}
								if(this.LeftArm.rotateAngleY <  (float)Math.toRadians(-32F)){
									this.LeftArm.rotateAngleY =  (float)Math.toRadians(-32F);
								}

								if(this.head.rotateAngleY < (float)Math.toRadians(-22.5F)){
									this.head.rotateAngleY =  (float)Math.toRadians(-22.5F);
								}

								if(this.head.rotateAngleX < (float)Math.toRadians(-27.5F)){
									this.head.rotateAngleX =  (float)Math.toRadians(-27.5F);
								}

								if(this.body.rotateAngleX < (float)Math.toRadians(-7.5D)){
									this.body.rotateAngleX =  (float)Math.toRadians(-7.5D);
								}
								if(this.body.rotateAngleX >  (float)Math.toRadians(45D)){
									this.body.rotateAngleX =  (float)Math.toRadians(45D);
								}
								if(this.body.rotateAngleY >  (float)Math.toRadians(22.5D)){
									this.body.rotateAngleY =  (float)Math.toRadians(22.5D);
								}

								if(this.RightArm.rotateAngleY >  (float)Math.toRadians(45D)){
									this.RightArm.rotateAngleY =  (float)Math.toRadians(45D);
								}
								if(this.RightArm.rotateAngleY <  (float)Math.toRadians(-17.5D)){
									this.RightArm.rotateAngleY =  (float)Math.toRadians(-17.5D);
								}
							}
						} /*else {
							this.RightArm.rotationPointZ = 0.0F;
							this.RightArm.rotationPointX = -5.0F;
							this.LeftArm.rotationPointZ = 0.0F;
							this.LeftArm.rotationPointX = 5.0F;
							this.RightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
							this.LeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
							this.RightArm.rotateAngleZ = 2.3561945F;
							this.LeftArm.rotateAngleZ = -2.3561945F;
							this.RightArm.rotateAngleY = 0.0F;
							this.LeftArm.rotateAngleY = 0.0F;
						}*/
					}

					if(!p.isFiery() && p.getMagicUseTicksClient() < 0) {
						float RotationX = -62.5F * (float) Math.PI / 180F;
						float RotationY = 30F * (float) Math.PI / 180F;
							if(tribesmen.hasItemInSlot(EntityEquipmentSlot.MAINHAND) && tribesmen.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.STAFF) {
								if (EnumHandSide.LEFT == tribesmen.getPrimaryHand()) {
									this.LeftArm.rotateAngleX += RotationX;
									this.LeftArm.rotateAngleY -= RotationY;
								} else if (EnumHandSide.RIGHT == tribesmen.getPrimaryHand()) {
									this.RightArm.rotateAngleX += RotationX ;
									this.RightArm.rotateAngleY += RotationY;
								}
							} else if(tribesmen.hasItemInSlot(EntityEquipmentSlot.OFFHAND) && tribesmen.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.STAFF){
								if (EnumHandSide.LEFT != tribesmen.getPrimaryHand()) {
									this.LeftArm.rotateAngleX += RotationX ;
									this.LeftArm.rotateAngleY -= RotationY;
								} else if (EnumHandSide.RIGHT != tribesmen.getPrimaryHand()) {
									this.RightArm.rotateAngleX += RotationX;
									this.RightArm.rotateAngleY += RotationY;
								}
							}
					}

				}
			}
		}
		if (this.swingProgress > 0.0F)
		{
			EntityLivingBase l = (EntityLivingBase)entityIn;
			EnumHandSide enumhandside = l.getPrimaryHand();
			ModelRenderer modelrenderer = this.getArm(enumhandside);
			float f1 = this.swingProgress;
			this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT)
			{
				this.body.rotateAngleY *= -1.0F;
			}

			this.RightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.RightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 3.0F;
			this.LeftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.LeftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 3.0F;
			this.RightArm.rotateAngleY += this.body.rotateAngleY;
			this.LeftArm.rotateAngleY += this.body.rotateAngleY;
			this.LeftArm.rotateAngleX += this.body.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float)Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}
	}

	public ModelRenderer getArm(EnumHandSide p_191216_1_)
	{
		return p_191216_1_ == EnumHandSide.LEFT ? this.LeftArm : this.RightArm;
	}
	public ModelRenderer getBody()
	{
		return this.body;
	}
}