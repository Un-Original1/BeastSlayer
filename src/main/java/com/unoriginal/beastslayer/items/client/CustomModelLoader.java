package com.unoriginal.beastslayer.items.client;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CustomModelLoader implements ICustomModelLoader {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals("ancientbeasts") && modelLocation.getResourcePath().startsWith("models/item/") && modelLocation.getResourcePath().endsWith("dummy");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        CustomModel customModel = new CustomModel(modelLocation.getResourcePath().replace("models/item/", "").replace("_dummy", ""));
        return customModel;
    }
}
