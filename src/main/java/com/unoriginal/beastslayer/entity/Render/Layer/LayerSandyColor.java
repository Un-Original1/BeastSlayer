package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntitySandy;
import com.unoriginal.beastslayer.entity.Render.RenderSandy;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSandyColor implements LayerRenderer<EntitySandy> {
    private static final ResourceLocation LOCATION = new ResourceLocation("ancientbeasts:textures/entity/sandmonster_decor/");
    private final RenderSandy renderSandy;
    public LayerSandyColor(RenderSandy sandy){
        this.renderSandy = sandy;
    }
    @Override
    public void doRenderLayer(EntitySandy entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.getColor() != null)
        {

            this.renderSandy.bindTexture(new ResourceLocation(LOCATION + entitylivingbaseIn.getColor().getDyeColorName() + ".png"));
            this.renderSandy.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
