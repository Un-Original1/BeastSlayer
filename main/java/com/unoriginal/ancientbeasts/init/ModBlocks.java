package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.blocks.BlockMovingLight;
import com.unoriginal.ancientbeasts.blocks.BlockSkewer;
import com.unoriginal.ancientbeasts.blocks.BlockSpike;
import com.unoriginal.ancientbeasts.blocks.BlockSpikeTrap;
import net.minecraft.block.Block;
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
    public static void init(){
        POKEY = new BlockSpikeTrap("pokey");
        SKEWER = new BlockSkewer("skewer");
        SPIKE = new BlockSpike("spiky");
        LIGHT = new BlockMovingLight("moving_light_source");
    }
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
         event.getRegistry().registerAll(POKEY);
         event.getRegistry().registerAll(SKEWER);
         event.getRegistry().registerAll(SPIKE);
         event.getRegistry().registerAll(LIGHT);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new ItemBlock(POKEY).setRegistryName(POKEY.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SKEWER).setRegistryName(SKEWER.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SPIKE).setRegistryName(SPIKE.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(LIGHT).setRegistryName(LIGHT.getRegistryName()));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(ModelRegistryEvent event) {
         registerRender(Item.getItemFromBlock(POKEY));
         registerRender(Item.getItemFromBlock(SKEWER));
         registerRender(Item.getItemFromBlock(SPIKE));
         registerRender(Item.getItemFromBlock(LIGHT));
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}
