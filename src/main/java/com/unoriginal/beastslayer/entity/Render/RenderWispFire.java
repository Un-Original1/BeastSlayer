package com.unoriginal.beastslayer.entity.Render;

import com.unoriginal.beastslayer.entity.Entities.EntityWispfire;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWispFire extends Render<EntityWispfire> {

private final ResourceLocation TEXTURE=new ResourceLocation("ancientbeasts:textures/entity/tribe/wisp_fire.png");
public static final Factory FACTORY=new Factory();
    protected RenderWispFire(RenderManager renderManager){
        super(renderManager);
    }

    public void doRender(EntityWispfire entity,double x,double y,double z,float entityYaw,float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        GlStateManager.translate((float)x,(float)y,(float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1.0F,1.0F,1.0F);
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder bufferbuilder=tessellator.getBuffer();
        GlStateManager.rotate(180.0F-this.renderManager.playerViewY,0.0F,1.0F,0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView==2?-1:1)*-this.renderManager.playerViewX,1.0F,0.0F,0.0F);

        if(this.renderOutlines)
        {
        GlStateManager.enableColorMaterial();
        GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5D,-0.25D,0.0D).tex(0.0D,1.0D).normal(0.0F,1.0F,0.0F).endVertex();
        bufferbuilder.pos(0.5D,-0.25D,0.0D).tex(1.0D,1.0D).normal(0.0F,1.0F,0.0F).endVertex();
        bufferbuilder.pos(0.5D,0.75D,0.0D).tex(1.0D,0.0D).normal(0.0F,1.0F,0.0F).endVertex();
        bufferbuilder.pos(-0.5D,0.75D,0.0D).tex(0.0D,0.0D).normal(0.0F,1.0F,0.0F).endVertex();
        tessellator.draw();

        if(this.renderOutlines)
        {
        GlStateManager.disableOutlineMode();
        GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity,x,y,z,entityYaw,partialTicks);
    }
    protected ResourceLocation getEntityTexture(EntityWispfire entity)
    {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityWispfire> {

        @Override
        public Render<? super EntityWispfire> createRenderFor(RenderManager manager) {
            return new RenderWispFire(manager);
        }
    }

}
