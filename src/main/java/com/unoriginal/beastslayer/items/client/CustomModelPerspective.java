package com.unoriginal.beastslayer.items.client;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CustomModelPerspective implements IBakedModel {

    String name;
    ModelResourceLocation GUIMRL;
    ModelResourceLocation normalMRL;
    public CustomModelPerspective(String Itemname) {
        this.name = Itemname;
        GUIMRL = new ModelResourceLocation(new ResourceLocation(BeastSlayer.MODID, Itemname),"inventory");
        normalMRL = new ModelResourceLocation(new ResourceLocation(BeastSlayer.MODID,Itemname + "_model"),"inventory");
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType)
    {
        ModelManager manager = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager();

        IBakedModel model;
        if(cameraTransformType != ItemCameraTransforms.TransformType.GUI && cameraTransformType != ItemCameraTransforms.TransformType.GROUND && cameraTransformType != ItemCameraTransforms.TransformType.FIXED ) {
            model =  manager.getModel(normalMRL);//Get held model
        }
        else {
            model = manager.getModel(GUIMRL); //Get inventory model
        }

        return model.handlePerspective(cameraTransformType);
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return null;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
    {
        return new ArrayList<>();
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return new ItemOverrideList(new ArrayList<>());
    }
}
