package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntityFrostashFox;
import com.unoriginal.beastslayer.entity.Render.RenderFrostashFox;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerFrostashSleep implements LayerRenderer<EntityFrostashFox> {
    private static final ResourceLocation BLUE = new ResourceLocation("ancientbeasts:textures/entity/frostash_fox/eyes_closed_b.png");
    private static final ResourceLocation RED = new ResourceLocation("ancientbeasts:textures/entity/frostash_fox/eyes_closed_r.png");
    private static final ResourceLocation MIX = new ResourceLocation("ancientbeasts:textures/entity/frostash_fox/eyes_closed_m.png");
    private final RenderFrostashFox renderFrostashFox;

    public LayerFrostashSleep(RenderFrostashFox renderFrostashFox)
    {
        this.renderFrostashFox = renderFrostashFox;
    }

    public void doRenderLayer(EntityFrostashFox entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.isSleeping() && !entitylivingbaseIn.isInvisible())
        {
            if (entitylivingbaseIn.getVariant() < 2) {
                this.renderFrostashFox.bindTexture(BLUE);
            } else if (entitylivingbaseIn.getVariant() > 2) {
                this.renderFrostashFox.bindTexture(RED);
            } else {
                this.renderFrostashFox.bindTexture(MIX);
            }
           this.renderFrostashFox.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
