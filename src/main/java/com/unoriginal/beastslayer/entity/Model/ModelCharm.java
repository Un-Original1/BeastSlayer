package com.unoriginal.beastslayer.entity.Model;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCharm extends ModelBase {
    private final ModelRenderer all;
    private final ModelRenderer l;
    private final ModelRenderer r;

    public ModelCharm() {
        textureWidth = 64;
        textureHeight = 64;

        all = new ModelRenderer(this);
        all.setRotationPoint(0.0F, 0.0F, 1.0F);
        all.cubeList.add(new ModelBox(all, 0, 22, -2.0F, -1.5F, -1.0F, 4, 3, 3, 0.25F, false));

        l = new ModelRenderer(this);
        l.setRotationPoint(1.0F, -0.5F, -1.0F);
        all.addChild(l);
        l.cubeList.add(new ModelBox(l, 0, 11, 0.0F, -1.0F, -12.0F, 6, 3, 8, 0.0F, false));
        l.cubeList.add(new ModelBox(l, 26, 22, 0.0F, -1.0F, -4.0F, 2, 3, 4, 0.0F, false));
        l.cubeList.add(new ModelBox(l, 0, 28, -1.0F, -1.0F, -10.0F, 1, 3, 2, 0.0F, false));

        r = new ModelRenderer(this);
        r.setRotationPoint(-1.0F, -0.5F, -1.0F);
        all.addChild(r);
        r.cubeList.add(new ModelBox(r, 0, 11, -6.0F, -1.0F, -12.0F, 6, 3, 8, 0.0F, true));
        r.cubeList.add(new ModelBox(r, 0, 28, 0.0F, -1.0F, -10.0F, 1, 3, 2, 0.0F, true));
        r.cubeList.add(new ModelBox(r, 26, 22, -2.0F, -1.0F, -4.0F, 2, 3, 4, 0.0F, true));
    }



    @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		all.render(f5);
        this.all.rotateAngleX = f2 * 0.017453292F;
        this.all.rotateAngleY = f3 * 0.017453292F;
        this.r.rotateAngleY = -this.l.rotateAngleY;
        this.l.rotateAngleY = -60F * (float) Math.PI /180F + entity.ticksExisted * 0.09F;
        if (this.l.rotateAngleY  > 0.0F) {
            this.l.rotateAngleY = 0F;
        }
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}