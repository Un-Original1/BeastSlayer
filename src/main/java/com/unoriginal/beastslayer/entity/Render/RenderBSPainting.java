package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityBSPainting;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBSPainting extends Render<EntityBSPainting> {


    private static final ResourceLocation ENDER = new ResourceLocation("ancientbeasts:textures/paintings/paint_ender.png");
    private static final ResourceLocation OWL = new ResourceLocation("ancientbeasts:textures/paintings/paint_owlstack.png");
    private static final ResourceLocation ZEAL = new ResourceLocation("ancientbeasts:textures/paintings/paint_zealot.png");
    private static final ResourceLocation SANDY = new ResourceLocation("ancientbeasts:textures/paintings/paint_sandy.png");
    private static final ResourceLocation JESTER = new ResourceLocation("ancientbeasts:textures/paintings/paint_jester.png");
    private static final ResourceLocation EVIL = new ResourceLocation("ancientbeasts:textures/paintings/paint_evil.png");
    private static final ResourceLocation SIN = new ResourceLocation("ancientbeasts:textures/paintings/paint_sin.png");
    private static final ResourceLocation GIANT = new ResourceLocation("ancientbeasts:textures/paintings/paint_giant.png");
    private static final ResourceLocation FREEZE = new ResourceLocation("ancientbeasts:textures/paintings/paint_ice.png");
    private static final ResourceLocation MINE = new ResourceLocation("ancientbeasts:textures/paintings/paint_mine.png");

    private static final ResourceLocation[] ALL = new ResourceLocation[]{ENDER, OWL, ZEAL, SANDY, JESTER, EVIL, SIN, GIANT, FREEZE, MINE};
    private static final ResourceLocation BACK = new ResourceLocation("ancientbeasts:textures/paintings/paint_back.png");

    public RenderBSPainting(RenderManager manager) {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBSPainting entity) {
        return ALL[entity.getArt()];
    }

    @Override
    public void doRender(EntityBSPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180 - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);



        this.bindEntityTexture(entity);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        float relWidth = entity.getWidthPixels();
        float relHeight = entity.getHeightPixels();

        float width = relWidth / 16 / 2;
        float height = relHeight / 16 / 2;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);

        buffer.pos(width, -height, -0.03D).tex(0, 1).normal(0, 0, 1).endVertex();
        buffer.pos(-width, -height, -0.03D).tex(1, 1).normal(0, 0, 1).endVertex();
        buffer.pos(-width, height, -0.03D).tex(1, 0).normal(0, 0, 1).endVertex();
        buffer.pos(width, height, -0.03D).tex(0, 0).normal(0, 0, 1).endVertex();

        tessellator.draw();



        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180 - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.bindTexture(BACK);


        float realWidth = relWidth / 64F;
        float realHeight = relHeight / 64F;

        float maxdepth = 1F/64F;
        //top
        buffer.pos(width, height, -0.03D).tex(0, maxdepth).normal(0, 1, 0).endVertex();
        buffer.pos(-width, height, -0.03D).tex(realWidth, maxdepth).normal(0, 1, 0).endVertex();
        buffer.pos(-width, height, 0.035D).tex(realWidth, 0).normal(0, 1, 0).endVertex();
        buffer.pos(width, height, 0.035D).tex(0, 0).normal(0, 1, 0).endVertex();

        //bottom
        buffer.pos(width, -height, 0.035D).tex(0, 0).normal(0, -1, 0).endVertex();
        buffer.pos(-width, -height, 0.035D).tex(realWidth, 0).normal(0, -1, 0).endVertex();
        buffer.pos(-width, -height, -0.03D).tex(realWidth, maxdepth).normal(0, -1, 0).endVertex();
        buffer.pos(width, -height, -0.03D).tex(0, maxdepth).normal(0, -1, 0).endVertex();

        //side 1 (-A)
        buffer.pos(-width, height, -0.03D).tex(0, 0).normal(-1, 0, 0).endVertex();
        buffer.pos(-width, -height, -0.03D).tex(0, realHeight).normal(-1, 0, 0).endVertex();
        buffer.pos(-width, -height, 0.035D).tex(maxdepth, realHeight).normal(-1, 0, 0).endVertex();
        buffer.pos(-width, height, 0.035D).tex(maxdepth, 0).normal(-1, 0, 0).endVertex();

        //side 2
        buffer.pos(width, height, 0.035D).tex(0, 0).normal(1, 0, 0).endVertex();
        buffer.pos(width, -height, 0.035D).tex(0, realHeight).normal(1, 0, 0).endVertex();
        buffer.pos(width, -height, -0.03D).tex(maxdepth, realHeight).normal(1, 0, 0).endVertex();
        buffer.pos(width, height, -0.03D).tex(maxdepth, 0).normal(1, 0, 0).endVertex();

        //full
        buffer.pos(-width, -height, 0.035D).tex(realWidth, realHeight).normal(0, 0, -1).endVertex();
        buffer.pos(width, -height, 0.035D).tex(0, realHeight).normal(0, 0, -1).endVertex();
        buffer.pos(width, height, 0.035D).tex(0, 0).normal(0, 0, -1).endVertex();
        buffer.pos(-width, height, 0.035D).tex(realWidth, 0).normal(0, 0, -1).endVertex();

        tessellator.draw();



        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
