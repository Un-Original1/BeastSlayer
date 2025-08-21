package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntityNetherhound;
import com.unoriginal.beastslayer.entity.Render.RenderNetherhound;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerNetherhoundCollar implements LayerRenderer<EntityNetherhound> {
    private static final ResourceLocation WOLF_COLLAR1 = new ResourceLocation("ancientbeasts:textures/entity/netherhound_collar1.png");
    private static final ResourceLocation WOLF_COLLAR2 = new ResourceLocation("ancientbeasts:textures/entity/netherhound_collar2.png");
    private final RenderNetherhound wolfRenderer;
    public LayerNetherhoundCollar(RenderNetherhound netherhound){
        this.wolfRenderer = netherhound;
    }
    @Override
    public void doRenderLayer(EntityNetherhound entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible())
        {
            this.wolfRenderer.bindTexture(WOLF_COLLAR1);
            float[] afloat = entitylivingbaseIn.getCollarColor().getColorComponentValues();
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.wolfRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            this.wolfRenderer.bindTexture(WOLF_COLLAR2);
            GlStateManager.color(1, 1, 1);
            this.wolfRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
