package com.unoriginal.beastslayer.particles;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleSpellCircle extends Particle {
    private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation(BeastSlayer.MODID,"textures/particles/spell.png");
    private int footstepAge;
    private final int footstepMaxAge;

    protected ParticleSpellCircle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.footstepMaxAge = 20;
    }

    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float f1 = 1.0F;

        //f1 = f1 * 0.2F;
        int i = this.getBrightnessForRender(partialTicks);
        float rot1 = MathHelper.cos((entity.ticksExisted + partialTicks) * 0.1F);
        float rot2 = MathHelper.sin((entity.ticksExisted + partialTicks) * 0.1F);
        float f2 = 2F;
        float f3 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks));
        float f4 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - (entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks));
        float f5 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks)) ;
        float f6 = 1.0F;
        Minecraft.getMinecraft().getTextureManager().bindTexture(FOOTPRINT_TEXTURE);
       // RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        GlStateManager.enableBlend();

        float dx = -f2;

        float rx = dx * rot1 + f2 * rot2;
        float rz = -dx * rot2 + f2 * rot1;

        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos((f3 + rx), f4, (f5 + rz)).tex(0.0D, 1.0D).color(f6, f6, f6, f1).lightmap(i, i).endVertex();

        float rx2 = f2 * rot1 + f2 * rot2;
        float rz2 = -f2 * rot2 + f2 * rot1;

        buffer.pos((f3 + rx2), f4, (f5 + rz2)).tex(1.0D, 1.0D).color(f6, f6, f6, f1).lightmap(i, i).endVertex();


        float dz3 =  -f2;

        float rx3 = f2 * rot1 + dz3 * rot2;
        float rz3 = -f2 * rot2 + dz3 * rot1;

        buffer.pos((f3 + rx3), f4, (f5 + rz3)).tex(1.0D, 0.0D).color(f6, f6, f6, f1).lightmap(i, i).endVertex();


        float dx4 = -f2;
        float dz4 =  -f2;

        float rx4 = dx4 * rot1 + dz4 * rot2;
        float rz4 = -dx4 * rot2 + dz4 * rot1;


        buffer.pos((f3 + rx4), f4, (f5 + rz4)).tex(0.0D, 0.0D).color(f6, f6, f6, f1).lightmap(i, i).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.disableBlend();
    }

    public int getBrightnessForRender(float partialTick)
    {
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int)(15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    public void onUpdate()
    {
        ++this.footstepAge;

        if (this.footstepAge == this.footstepMaxAge)
        {
            this.setExpired();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleSpellCircle(worldIn, xCoordIn, yCoordIn, zCoordIn);
        }
    }
}
