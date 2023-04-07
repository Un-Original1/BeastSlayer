package com.unoriginal.beastslayer.items.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.function.Function;

public class CustomModel implements IModel {
    private String name;

    public CustomModel(String name) {
        this.name = name;
    }

    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        return ImmutableList.of();
    }

    @Override
    public Collection<ResourceLocation> getTextures()
    {
        return ImmutableList.of();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {

        return new CustomModelPerspective(name);
    }

    @Override
    public IModelState getDefaultState()
    {
        return TRSRTransformation.identity();
    }
}
