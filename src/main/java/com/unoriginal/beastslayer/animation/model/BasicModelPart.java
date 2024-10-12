package com.unoriginal.beastslayer.animation.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class BasicModelPart extends ModelRenderer {
    public float defaultRotationX;
    public float defaultRotationY;
    public float defaultRotationZ;
    public float defaultOffsetX;
    public float defaultOffsetY;
    public float defaultOffsetZ;
    private BasicModelPart parent;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    public float defaultPositionX;
    public float defaultPositionY;
    public float defaultPositionZ;

    public float opacity = 1.0F;
    public boolean scaleChildren;

    private BasicModelEntity model;
    public BasicModelPart(ModelBase model)
    {
        this(model, (String)null);
        this.scaleX = 1.0F;
        this.scaleY = 1.0F;
        this.scaleZ = 1.0F;
    }


    public BasicModelPart(ModelBase model, String boxNameIn) {
        super(model, boxNameIn);
        this.scaleX = 1.0F;
        this.scaleY = 1.0F;
        this.scaleZ = 1.0F;

    }

    /**
     * Sets the current pose to the previously set default pose.
     */
    public void resetToDefaultPose() {
        this.rotateAngleX = this.defaultRotationX;
        this.rotateAngleY = this.defaultRotationY;
        this.rotateAngleZ = this.defaultRotationZ;
        this.rotationPointX = this.defaultPositionX;
        this.rotationPointY = this.defaultPositionY;
        this.rotationPointZ = this.defaultPositionZ;
    }

    /**
     * Sets this ModelRenderer's default pose to the current pose.
     */
    public void updateDefaultPose() {
        this.defaultRotationX = this.rotateAngleX;
        this.defaultRotationY = this.rotateAngleY;
        this.defaultRotationZ = this.rotateAngleZ;
        this.defaultPositionX = this.rotationPointX;
        this.defaultPositionY = this.rotationPointY;
        this.defaultPositionZ = this.rotationPointZ;
    }

    public void setScale(float scaleX, float scaleY, float scaleZ) {
        //GlStateManager.scale(scaleX, scaleY, scaleZ);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }


    /**
     * If true, when using setScale, the children of this model part will be scaled as well as just this part. If false, just this part will be scaled.
     *
     * @param scaleChildren true if this parent should scale the children
     */
    public void setShouldScaleChildren(boolean scaleChildren) {
        this.scaleChildren = scaleChildren;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void addChild(ModelRenderer child) {
        super.addChild(child);
        if (child instanceof BasicModelPart) {
            BasicModelPart advancedChild = (BasicModelPart) child;
            advancedChild.setParent(this);
        }
    }

    public BasicModelPart getParent() {
        return this.parent;
    }

    public void setParent(BasicModelPart parent) {
        this.parent = parent;
    }

    public void setScaleWithPart(BasicModelPart part, float scaleX, float scaleY, float scaleZ) {
        part.setScale(scaleX, scaleY, scaleZ);
    }

    //Changes to Render to help support box movement
    @Override
    public void render(float scale) {
        if (!this.isHidden) {
            if (this.showModel) {
                GlStateManager.pushMatrix();
                if (!this.compiled) {
                    this.compileDisplayList(scale);
                }
                GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                if (this.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleZ), 0.0F, 0.0F, 1.0F);
                }
                if (this.rotateAngleY != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleY), 0.0F, 1.0F, 0.0F);
                }
                if (this.rotateAngleX != 0.0F) {
                    GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleX), 1.0F, 0.0F, 0.0F);
                }
                if (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F) {
                    GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
                }
                if (this.opacity != 1.0F) {
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    GlStateManager.color(1F, 1F, 1F, this.opacity);
                }
                GlStateManager.callList(this.displayList);
                if (this.opacity != 1.0F) {
                    GlStateManager.disableBlend();
                    GlStateManager.color(1F, 1F, 1F, 1F);
                }
                if (!this.scaleChildren && (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F)) {
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    if (this.rotateAngleZ != 0.0F) {
                        GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleZ), 0.0F, 0.0F, 1.0F);
                    }
                    if (this.rotateAngleY != 0.0F) {
                        GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleY), 0.0F, 1.0F, 0.0F);
                    }
                    if (this.rotateAngleX != 0.0F) {
                        GlStateManager.rotate((float) Math.toDegrees(this.rotateAngleX), 1.0F, 0.0F, 0.0F);
                    }
                }
                if (this.childModels != null) {
                    for (ModelRenderer childModel : this.childModels) {
                        childModel.render(scale);
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }


    private void compileDisplayList(float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, 4864);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        for (ModelBox box : this.cubeList) {
            box.render(buffer, scale);
        }
        GlStateManager.glEndList();
        this.compiled = true;
    }

    public void parentedPostRender(float scale) {
        if (this.parent != null) {
            this.parent.parentedPostRender(scale);
        }
        this.postRender(scale);
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }


    /**
     * Rotates this box back and forth (rotateAngleX). Useful for arms and legs.
     *
     * @param speed      is how fast the animation runs
     * @param degree     is how far the box will rotate;
     * @param invert     will invert the rotation
     * @param offset     will offset the timing of the animation
     * @param weight     will make the animation favor one direction more based on how fast the mob is moving
     * @param walk       is the walked distance
     * @param walkAmount is the walk speed
     */
    public void walk(float speed, float degree, boolean invert, float offset, float weight, float walk, float walkAmount) {
        this.rotateAngleX += this.calculateRotation(speed, degree, invert, offset, weight, walk, walkAmount);
    }


    /**
     * Rotates this box up and down (rotateAngleZ). Useful for wing and ears.
     *
     * @param speed      is how fast the animation runs
     * @param degree     is how far the box will rotate;
     * @param invert     will invert the rotation
     * @param offset     will offset the timing of the animation
     * @param weight     will make the animation favor one direction more based on how fast the mob is moving
     * @param flap       is the flapped distance
     * @param flapAmount is the flap speed
     */
    public void flap(float speed, float degree, boolean invert, float offset, float weight, float flap, float flapAmount) {
        this.rotateAngleZ += this.calculateRotation(speed, degree, invert, offset, weight, flap, flapAmount);
    }


    /**
     * Rotates this box side to side (rotateAngleY).
     *
     * @param speed       is how fast the animation runs
     * @param degree      is how far the box will rotate;
     * @param invert      will invert the rotation
     * @param offset      will offset the timing of the animation
     * @param weight      will make the animation favor one direction more based on how fast the mob is moving
     * @param swing       is the swung distance
     * @param swingAmount is the swing speed
     */
    public void swing(float speed, float degree, boolean invert, float offset, float weight, float swing, float swingAmount) {
        this.rotateAngleY += this.calculateRotation(speed, degree, invert, offset, weight, swing, swingAmount);
    }

    /**
     * Moves this box up and down (rotationPointY). Useful for body bobbing.
     *
     * @param speed  is how fast the animation runs;
     * @param degree is how far the box will move;
     * @param bounce will make the box bounce;
     * @param f      is the walked distance;
     * @param f1     is the walk speed.
     */
    public void bob(float speed, float degree, boolean bounce, float f, float f1) {
        float movementScale = 1.0F;
        degree *= movementScale;
        speed *= movementScale;
        float bob = (float)(Math.sin((double)(f * speed)) * (double)f1 * (double)degree - (double)(f1 * degree));
        if (bounce) {
            bob = (float)(-Math.abs(Math.sin((double)(f * speed)) * (double)f1 * (double)degree));
        }

        this.rotationPointY += bob;
    }


    public void transitionTo(BasicModelPart to, float timer, float maxTime) {
        this.rotateAngleX += (to.rotateAngleX - this.rotateAngleX) / maxTime * timer;
        this.rotateAngleY += (to.rotateAngleY - this.rotateAngleY) / maxTime * timer;
        this.rotateAngleZ += (to.rotateAngleZ - this.rotateAngleZ) / maxTime * timer;
        this.rotationPointX += (to.rotationPointX - this.rotationPointX) / maxTime * timer;
        this.rotationPointY += (to.rotationPointY - this.rotationPointY) / maxTime * timer;
        this.rotationPointZ += (to.rotationPointZ - this.rotationPointZ) / maxTime * timer;
        this.offsetX += (to.offsetX - this.offsetX) / maxTime * timer;
        this.offsetY += (to.offsetY - this.offsetY) / maxTime * timer;
        this.offsetZ += (to.offsetZ - this.offsetZ) / maxTime * timer;
    }



    private float calculateRotation(float speed, float degree, boolean invert, float offset, float weight, float f, float f1) {
        float movementScale = 1.0F;
        float rotation = (float) Math.cos(f * speed * movementScale + offset) * degree * movementScale * f1 + weight * f1;
        return invert ? -rotation : rotation;
    }

    /**
     * Returns the position of the model renderer in world space.
     */
    public Vec3d getWorldPos(Entity entity) {
        Vec3d modelPos = this.getModelPos(this, new Vec3d(this.rotationPointX /16, this.rotationPointY /16, this.rotationPointZ /16));
        double x = modelPos.x;
        double y = modelPos.y + 1.5f;
        double z = modelPos.z;
        Matrix4d entityTranslate = new Matrix4d();
        Matrix4d entityRotate = new Matrix4d();
        entityTranslate.set(new Vector3d(entity.posX, entity.posY, entity.posZ));
        entityRotate.rotY(-Math.toRadians(entity.rotationYaw));
        Point3d rendererPos = new Point3d(x, y, z);
        entityRotate.transform(rendererPos);
        entityTranslate.transform(rendererPos);
        return new Vec3d(rendererPos.getX(), rendererPos.getY(), rendererPos.getZ());
    }


    /**
     * Returns the position of the model renderer relative to the center and facing axis of the model.
     */
    public Vec3d getModelPos(BasicModelPart modelRenderer, Vec3d recurseValue) {
        double x = recurseValue.x;
        double y = recurseValue.y;
        double z = recurseValue.z;
        Point3d rendererPos = new Point3d(x, y, z);

        BasicModelPart parent = modelRenderer.getParent();
        if (parent != null) {
            Matrix4d boxTranslate = new Matrix4d();
            Matrix4d boxRotateX = new Matrix4d();
            Matrix4d boxRotateY = new Matrix4d();
            Matrix4d boxRotateZ = new Matrix4d();
            boxTranslate.set(new Vector3d(parent.rotationPointX/16, -parent.rotationPointY/16, -parent.rotationPointZ/16));
            boxRotateX.rotX(parent.rotateAngleX);
            boxRotateY.rotY(-parent.rotateAngleY);
            boxRotateZ.rotZ(-parent.rotateAngleZ);

            boxRotateX.transform(rendererPos);
            boxRotateY.transform(rendererPos);
            boxRotateZ.transform(rendererPos);
            boxTranslate.transform(rendererPos);

            return this.getModelPos(parent, new Vec3d(rendererPos.getX(), rendererPos.getY(), rendererPos.getZ()));
        }
        return new Vec3d(rendererPos.getX(), rendererPos.getY(), rendererPos.getZ());
    }
}
