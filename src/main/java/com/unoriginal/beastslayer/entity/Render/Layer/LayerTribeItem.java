package com.unoriginal.beastslayer.entity.Render.Layer;


import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import com.unoriginal.beastslayer.entity.Model.ModelHunter;
import com.unoriginal.beastslayer.entity.Model.ModelPriest;
import com.unoriginal.beastslayer.entity.Model.ModelTank;
import com.unoriginal.beastslayer.entity.Model.ModelTribeWarrior;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerTribeItem implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;

    public LayerTribeItem(RenderLivingBase<?> livingEntityRendererIn)
    {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entity.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entity.getHeldItemMainhand() : entity.getHeldItemOffhand();

        if (!itemstack.isEmpty() || !itemstack1.isEmpty())
        {
            GlStateManager.pushMatrix();
            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.75F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            GlStateManager.translate(0.0F, 1.0F, 0.0F);

            if(entity instanceof EntityTribeWarrior){
                EntityTribeWarrior t = (EntityTribeWarrior) entity;
                if(t.getAngryTick() < 0){
                    this.renderHeldItem(entity, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
                    this.renderHeldItem(entity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
                }
            } else {
                this.renderHeldItem(entity, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
                this.renderHeldItem(entity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            }
            GlStateManager.popMatrix();
        }
    }

    private void renderHeldItem(EntityLivingBase livingBase, ItemStack itemstack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide)
    {
        if (!itemstack.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (livingBase.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.translateToHand(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;

            if (this.livingEntityRenderer.getMainModel() instanceof ModelHunter)
           {
               GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.525F); //the z value affects the item pos on the model's y-axis. The lower the value the lower the pos


            }
            else if (this.livingEntityRenderer.getMainModel() instanceof ModelPriest)
            {
                GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.54F);


            }
            else if (this.livingEntityRenderer.getMainModel() instanceof ModelTank)
            {
                GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -1.2F);


            } else {
                GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            }

            Minecraft.getMinecraft().getItemRenderer().renderItemSide(livingBase, itemstack, camera, flag); //item renderer (the item in question, not pos)
            GlStateManager.popMatrix();
        }
    }

    protected void translateToHand(EnumHandSide hand)
    {
        if(this.livingEntityRenderer.getMainModel() instanceof ModelTribeWarrior) {
            ((ModelTribeWarrior) this.livingEntityRenderer.getMainModel()).getArm(hand).postRender(0.0625F);
        }
        else if (this.livingEntityRenderer.getMainModel() instanceof ModelHunter)
        {
            ((ModelHunter) this.livingEntityRenderer.getMainModel()).getArm(hand).postRender(0.0625F);
        }
        else if (this.livingEntityRenderer.getMainModel() instanceof ModelPriest)
        {
            ((ModelPriest) this.livingEntityRenderer.getMainModel()).getArm(hand).postRender(0.0625F);
        }
        else if (this.livingEntityRenderer.getMainModel() instanceof ModelTank)
        {
            ((ModelTank) this.livingEntityRenderer.getMainModel()).getArm(hand).postRender(0.0625F);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
