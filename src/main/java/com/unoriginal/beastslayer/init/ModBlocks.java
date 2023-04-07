package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModBlocks {
    public static Block POKEY;
    public static Block SPIKE;
    public static Block SKEWER;
    public static Block LIGHT;
    public static Block STICK;
    public static Block THATCH;

    public static void init(){
        POKEY = new BlockSpikeTrap("pokey");
        SKEWER = new BlockSkewer("skewer");
        SPIKE = new BlockSpike("spiky");
        LIGHT = new BlockMovingLight("moving_light_source");
        STICK = new BlockRotatedPillarBase("stick_wall", Material.WOOD, SoundType.WOOD, 1.0F, true, 60);
        THATCH = new BlockRotatedPillarBase("Thatch", Material.GRASS, SoundType.PLANT, 0.8F, false, 60);
    }
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
         event.getRegistry().registerAll(POKEY);
         event.getRegistry().registerAll(SKEWER);
         event.getRegistry().registerAll(SPIKE);
         event.getRegistry().registerAll(LIGHT);
         event.getRegistry().registerAll(STICK);
         event.getRegistry().registerAll(THATCH);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new ItemBlock(POKEY).setRegistryName(POKEY.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SKEWER).setRegistryName(SKEWER.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SPIKE).setRegistryName(SPIKE.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(LIGHT).setRegistryName(LIGHT.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(STICK).setRegistryName(STICK.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(THATCH).setRegistryName(THATCH.getRegistryName()));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(ModelRegistryEvent event) {
         registerRender(Item.getItemFromBlock(POKEY));
         registerRender(Item.getItemFromBlock(SKEWER));
         registerRender(Item.getItemFromBlock(SPIKE));
         registerRender(Item.getItemFromBlock(LIGHT));
         registerRender(Item.getItemFromBlock(STICK));
         registerRender(Item.getItemFromBlock(THATCH));
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}
