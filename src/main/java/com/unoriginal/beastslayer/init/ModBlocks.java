package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.blocks.*;
import com.unoriginal.beastslayer.blocks.slabs.BlockDoubleSlab;
import com.unoriginal.beastslayer.blocks.slabs.BlockHalfSlab;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.items.ItemBlockDoor;
import com.unoriginal.beastslayer.items.ItemIceWand;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemSlab;
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
    public static Block CAULDRON;
    public static Block WITCHCRAFT_TABLE;
    public static Block FIRE_IDOL;


    public static Block CURSED_PLANK;
    public static Block CURSED_LOG;
    public static Block CURSED_STAIR;
    public static BlockSlab CURSED_SLAB_DOUBLE;
    public static BlockSlab CURSED_SLAB_HALF;
    public static Block CURSED_FENCE;
    public static Block CURSED_GATE;
    public static Block CURSED_DOOR;
    public static Block CURSED_LEAVES;
    public static Block HANGING_LEAVES;
    public static Block CURSED_SAPLING;

    public static void init(){
        POKEY = new BlockSpikeTrap("pokey");
        SKEWER = new BlockSkewer("skewer");
        SPIKE = new BlockSpike("spiky");
        LIGHT = new BlockMovingLight("moving_light_source");

        STICK = new BlockRotatedPillarBase("stick_wall", Material.WOOD, SoundType.WOOD, 1.0F, true, 60);
        THATCH = new BlockThatch("Thatch", Material.GRASS, SoundType.PLANT, 0.8F, false, 60);
        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            CAULDRON = new BlockModCauldron("magic_cauldron");
            WITCHCRAFT_TABLE = new BlockWitchcraftTable("witchcraft_table");
            FIRE_IDOL = new BlockFireIdol("fire_idol");
            CURSED_LOG = new BlockRotatedPillarBase("cursed_log", Material.WOOD, SoundType.WOOD, 1.0F, true, 60).setHardness(2.0F);
            CURSED_PLANK = new Block(Material.WOOD).setRegistryName("cursed_plank").setUnlocalizedName("cursed_plank").setCreativeTab(BeastSlayer.BEASTSTAB).setHardness(2.0F);
            CURSED_STAIR = new BlockStairsAB(ModBlocks.CURSED_PLANK.getDefaultState(), "cursed_stairs").setHardness(2.0F);
            CURSED_SLAB_HALF = new BlockHalfSlab("cursed_slab_half", Material.WOOD, ModBlocks.CURSED_SLAB_HALF);
            CURSED_SLAB_DOUBLE = new BlockDoubleSlab("cursed_slab_double", Material.WOOD, ModBlocks.CURSED_SLAB_HALF);
            CURSED_FENCE = new BlockFence(Material.WOOD, MapColor.BLUE_STAINED_HARDENED_CLAY ).setRegistryName("cursed_fence").setUnlocalizedName("cursed_fence").setCreativeTab(BeastSlayer.BEASTSTAB).setHardness(2.0F);
            CURSED_GATE = new BlockFenceGate(BlockPlanks.EnumType.JUNGLE).setRegistryName("cursed_gate").setUnlocalizedName("cursed_gate").setCreativeTab(BeastSlayer.BEASTSTAB).setHardness(2.0F);
            CURSED_DOOR = new BlockDoorAB(Material.WOOD).setRegistryName("cursed_door").setUnlocalizedName("cursed_door").setCreativeTab(BeastSlayer.BEASTSTAB).setHardness(2.0F);
            CURSED_LEAVES = new BlockLeavesAB().setRegistryName("cursed_leaves").setUnlocalizedName("cursed_leaves").setCreativeTab(BeastSlayer.BEASTSTAB);
            HANGING_LEAVES = new HangingBlock(Material.PLANTS, "hanging_leaves");
            CURSED_SAPLING = new BlockModSapling("cursed_sapling");
        }
    }
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
         event.getRegistry().registerAll(POKEY);
         event.getRegistry().registerAll(SKEWER);
         event.getRegistry().registerAll(SPIKE);
         event.getRegistry().registerAll(LIGHT);

            event.getRegistry().registerAll(STICK);
            event.getRegistry().registerAll(THATCH);
        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            event.getRegistry().registerAll(CAULDRON);
            event.getRegistry().registerAll(WITCHCRAFT_TABLE);
            event.getRegistry().registerAll(FIRE_IDOL);
            event.getRegistry().registerAll(CURSED_LOG);
            event.getRegistry().registerAll(CURSED_PLANK);
            event.getRegistry().registerAll(CURSED_STAIR);

            event.getRegistry().registerAll(CURSED_SLAB_DOUBLE);
            event.getRegistry().registerAll(CURSED_SLAB_HALF);
            event.getRegistry().registerAll(CURSED_FENCE);
            event.getRegistry().registerAll(CURSED_GATE);
            event.getRegistry().registerAll(CURSED_DOOR);
            event.getRegistry().registerAll(CURSED_LEAVES);
            event.getRegistry().registerAll(HANGING_LEAVES);
            event.getRegistry().registerAll(CURSED_SAPLING);
        }
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new ItemBlock(POKEY).setRegistryName(POKEY.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SKEWER).setRegistryName(SKEWER.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(SPIKE).setRegistryName(SPIKE.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(LIGHT).setRegistryName(LIGHT.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(STICK).setRegistryName(STICK.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(THATCH).setRegistryName(THATCH.getRegistryName()));
        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            event.getRegistry().registerAll(new ItemBlock(CAULDRON).setRegistryName(CAULDRON.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(WITCHCRAFT_TABLE).setRegistryName(WITCHCRAFT_TABLE.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(FIRE_IDOL).setRegistryName(FIRE_IDOL.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_LOG).setRegistryName(CURSED_LOG.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_PLANK).setRegistryName(CURSED_PLANK.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_STAIR).setRegistryName(CURSED_STAIR.getRegistryName()));

            //event.getRegistry().registerAll(new ItemBlock(CURSED_SLAB_DOUBLE).setRegistryName(CURSED_SLAB_DOUBLE.getRegistryName()));
            event.getRegistry().registerAll(new ItemSlab(CURSED_SLAB_HALF, CURSED_SLAB_HALF, CURSED_SLAB_DOUBLE).setRegistryName(CURSED_SLAB_HALF.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_FENCE).setRegistryName(CURSED_FENCE.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_GATE).setRegistryName(CURSED_GATE.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlockDoor(CURSED_DOOR).setRegistryName(CURSED_DOOR.getRegistryName()).setUnlocalizedName("cursed_door").setCreativeTab(BeastSlayer.BEASTSTAB));
            event.getRegistry().registerAll(new ItemBlock(CURSED_LEAVES).setRegistryName(CURSED_LEAVES.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(HANGING_LEAVES).setRegistryName(HANGING_LEAVES.getRegistryName()));
            event.getRegistry().registerAll(new ItemBlock(CURSED_SAPLING).setRegistryName(CURSED_SAPLING.getRegistryName()));
        }
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
        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            registerRender(Item.getItemFromBlock(CAULDRON));
            registerRender(Item.getItemFromBlock(WITCHCRAFT_TABLE));
            registerRender(Item.getItemFromBlock(FIRE_IDOL));
            registerRender(Item.getItemFromBlock(CURSED_LOG));
            registerRender(Item.getItemFromBlock(CURSED_PLANK));
            registerRender(Item.getItemFromBlock(CURSED_STAIR));

            registerRender(Item.getItemFromBlock(CURSED_SLAB_HALF));
          //  registerRender(Item.getItemFromBlock(CURSED_SLAB_DOUBLE));
            registerRender(Item.getItemFromBlock(CURSED_FENCE));
            registerRender(Item.getItemFromBlock(CURSED_GATE));
            registerRender(Item.getItemFromBlock(CURSED_DOOR));
            registerRender(Item.getItemFromBlock(CURSED_LEAVES));
            registerRenderInv(Item.getItemFromBlock(HANGING_LEAVES));
            registerRenderInv(Item.getItemFromBlock(CURSED_SAPLING));
        }
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "normal"));
    }
    //idk
    public static void registerRenderInv(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}
