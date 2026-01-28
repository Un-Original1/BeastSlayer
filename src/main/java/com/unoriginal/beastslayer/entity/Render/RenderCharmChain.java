package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityCharmChain;
import com.unoriginal.beastslayer.entity.Model.ModelCharm;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderCharmChain extends Render<EntityCharmChain> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ancientbeasts:textures/entity/succ/charm_chain.png");
    private static final ResourceLocation CHAIN = new ResourceLocation("ancientbeasts:textures/entity/succ/heart_chain.png");
    private final ModelCharm charm = new ModelCharm();
    public static final Factory FACTORY = new Factory();

    public RenderCharmChain(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }
    private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_)
    {
        float f;

        for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F)
        {
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return p_82400_1_ + p_82400_3_ * f;
    }

    public boolean shouldRender(EntityCharmChain chained, ICamera camera, double camX, double camY, double camZ)
    {
        if (super.shouldRender(chained, camera, camX, camY, camZ))
        {
            return true;
        }
        else
        {
            if (chained.hasBuffedEntity())
            {
                EntityLivingBase zealot = chained.getBuffedEntity();
                if (zealot != null)
                {
                    Vec3d vec3d = this.getPosition(zealot, (double)zealot.height * 0.5D, 1.0F);
                    Vec3d vec3d1 = this.getPosition(chained, chained.height / 2, 1.0F);

                    return camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y, vec3d.z));
                }
            }

            return false;
        }
    }
    private Vec3d getPosition(Entity beam, double p_177110_2_, float p_177110_4_)
    {
        double d0 = beam.lastTickPosX + (beam.posX - beam.lastTickPosX) * (double)p_177110_4_;
        double d1 = p_177110_2_ + beam.lastTickPosY + (beam.posY - beam.lastTickPosY) * (double)p_177110_4_;
        double d2 = beam.lastTickPosZ + (beam.posZ - beam.lastTickPosZ) * (double)p_177110_4_;
        return new Vec3d(d0, d1, d2);
    }

    public void doRender(EntityCharmChain entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);

        float f = this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.charm.render(entity, 0.0F, 0.0F, 0.0F, f, f1, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.popMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        EntityLivingBase origin = entity.getBuffedEntity();
        if (origin != null) {
            this.bindTexture(CHAIN);
            GlStateManager.glTexParameteri(3553, 10242, 10497);
            GlStateManager.glTexParameteri(3553, 10243, 10497);
            // GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            float f4 = entity.height / 2;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y + f4, (float) z);
            Vec3d vec3d = this.getPosition(origin, (double) origin.height * 0.5D, partialTicks);
            Vec3d vec3d1 = this.getPosition(entity, f4, partialTicks);
            Vec3d vec3d2 = vec3d.subtract(vec3d1);
            double d0 = vec3d2.lengthVector();
            vec3d2 = vec3d2.normalize();
            float f5 = (float) Math.acos(vec3d2.y);
            float f6 = (float) Math.atan2(vec3d2.z, vec3d2.x);
            GlStateManager.rotate((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f5 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            double d1 =/* (double)f2 * 0.05D * -1.5D*/ 0d;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            int j = 255;
            int k = 255;
            int l = 255;
            double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * 0.282D;
            double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * 0.282D;
            double d6 = 0.0D + Math.cos(d1 + (Math.PI / 4D)) * 0.282D;
            double d7 = 0.0D + Math.sin(d1 + (Math.PI / 4D)) * 0.282D;
            double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * 0.282D;
            double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * 0.282D;
            double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * 0.282D;
            double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * 0.282D;
            double d12 = 0.0D + Math.cos(d1 + Math.PI) * 0.5D;
            double d13 = 0.0D + Math.sin(d1 + Math.PI) * 0.5D;
            double d14 = 0.0D + Math.cos(d1 + 0.0D) * 0.5D;
            double d15 = 0.0D + Math.sin(d1 + 0.0D) * 0.5D;
            double d22 = (-1.0F /*+ f3*/);
            double d23 = d0 * 2.5D + d22;
            bufferbuilder.pos(d12, d0, d13).tex(1D, d23).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d12, 0.0D, d13).tex(1D, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d14, 0.0D, d15).tex(0.0D, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d14, d0, d15).tex(0.0D, d23).color(j, k, l, 255).endVertex();
            double d24 = 0.0D;

            if (entity.ticksExisted % 2 == 0) {
                d24 = 0.5D;
            }

            bufferbuilder.pos(d4, d0, d5).tex(0.5D, d24 + 0.5D).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d6, d0, d7).tex(1.0D, d24 + 0.5D).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d10, d0, d11).tex(1.0D, d24).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d8, d0, d9).tex(0.5D, d24).color(j, k, l, 255).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }
    }
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCharmChain entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityCharmChain> {

        @Override
        public Render<? super EntityCharmChain> createRenderFor(RenderManager manager) {
            return new RenderCharmChain(manager);
        }
    }
}
