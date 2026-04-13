package com.unoriginal.beastslayer.entity.Render.Layer;

import com.unoriginal.beastslayer.entity.Entities.EntityEarthling;
import com.unoriginal.beastslayer.entity.Render.RenderEarthling;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEarthling implements LayerRenderer<EntityEarthling> {
    private final RenderEarthling renderer;;

    public LayerEarthling(RenderEarthling renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityEarthling entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

            this.renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.enableCull();
            GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
            GlStateManager.pushMatrix();
            this.renderer.getMainModel().head.postRender(0.0625F);


            GlStateManager.scale(0.5F, -0.5F, 0.5F);
            GlStateManager.translate(-0.6F, 2.3F, -0.2F);
            GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, 0.5F);
            blockrendererdispatcher.renderBlockBrightness(Blocks.TALLGRASS.getStateFromMeta(1), 1.0F);
            GlStateManager.popMatrix();

            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            GlStateManager.translate(1.1F, 0.4F, 0.85F);
            GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.5F);

            int i = entitylivingbaseIn.getBrightnessForRender();
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);

            if(!entitylivingbaseIn.getFlower().isEmpty()){
                IBlockState state = entitylivingbaseIn.getFlowerState();

                if(state.getBlock() instanceof BlockDoublePlant){
                    state = state.withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER);
                    int color = ColorizerGrass.getGrassColor(0.5D, 1.0D);
                    //I need to adda custom colorizer here cuz the game checks for a custom colorizer for tall grass stuff, thus a new system is required like the one used on other foliage (like colorizer grass)

                }
                blockrendererdispatcher.renderBlockBrightness(state, 1.0F);

            } else {
                blockrendererdispatcher.renderBlockBrightness(Blocks.TALLGRASS.getStateFromMeta(1), 1.0F);
            }
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();

            GlStateManager.cullFace(GlStateManager.CullFace.BACK);

            GlStateManager.disableCull();
           // GlStateManager.disableRescaleNormal();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

}
