package com.unoriginal.beastslayer.entity.Model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModelHelper {
    @SideOnly(Side.CLIENT)
    public static void renderRelative(ModelRenderer parent, List<ModelRenderer> child, float scale){
        if (!parent.isHidden)
        {
            if (parent.showModel)
            {
			/*	if (!parent.compiled)
				{
					parent.compileDisplayList(scale);
				}*/

                	GlStateManager.translate(parent.offsetX, parent.offsetY, parent.offsetZ);

                if (parent.rotateAngleX == 0.0F && parent.rotateAngleY == 0.0F && parent.rotateAngleZ == 0.0F)
                {
                    if (parent.rotationPointX == 0.0F && parent.rotationPointY == 0.0F && parent.rotationPointZ == 0.0F)
                    {
                        //	GlStateManager.callList(parent.displayList);

                        if (!child.isEmpty())
                        {
                            for (ModelRenderer modelRenderer : child) {
                                modelRenderer.render(scale);
                            }
                        }
                    }
                    else
                    {
                        GlStateManager.translate(parent.rotationPointX * scale, parent.rotationPointY * scale, parent.rotationPointZ * scale);
                        //GlStateManager.callList(parent.displayList);

                        if (!child.isEmpty())
                        {
                            for (ModelRenderer modelRenderer : child) {
                                modelRenderer.render(scale);
                            }
                        }

                        	GlStateManager.translate(-parent.rotationPointX * scale, -parent.rotationPointY * scale, -parent.rotationPointZ * scale);
                    }
                }
                else
                {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(parent.rotationPointX * scale, parent.rotationPointY * scale, parent.rotationPointZ * scale);

                    if (parent.rotateAngleZ != 0.0F)
                    {
                        GlStateManager.rotate(parent.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (parent.rotateAngleY != 0.0F)
                    {
                        GlStateManager.rotate(parent.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (parent.rotateAngleX != 0.0F)
                    {
                        GlStateManager.rotate(parent.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }

                    //GlStateManager.callList(parent.displayList);

                    if (!child.isEmpty())
                    {
                        for (ModelRenderer modelRenderer : child) {
                            modelRenderer.render(scale);
                        }
                    }

                    GlStateManager.popMatrix();
                }

                GlStateManager.translate(-parent.offsetX, -parent.offsetY, -parent.offsetZ);
            }
        }
    }
}
