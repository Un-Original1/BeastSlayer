package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.items.*;
import com.unoriginal.beastslayer.items.client.CustomModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModItems {
    public static Item SANDMONSTER_SCALE;
    public static Item LOGO;
    public static Item SANDY_LOVE;
    public static Item SCALE_ARMOR;
    public static Item SCALE_HOOD;
    public static Item CACTUS_BROTH;
    public static Item SHIELD_BOOK;
    public static Item ECTOPLASM;
    public static Item TOUGH_GLOVE;
    public static Item ICE_DART;
    public static Item ICE_WAND;
    public static Item ICE_WAND_RED;
    public static Item VESSEL;
    public static Item SPIKE;
    public static Item FUR;
    public static Item CHARRED_CLOAK;
    public static Item MINER_HELMET;
    public static Item PORTABLE_WIKI;
    public static Item RIFTED_PEARL;

    public static Item CURSED_WOOD;
    public static Item SPEAR;
    public static Item STAFF;
    public static Item TORCH;
    public static Item DUST;
    public static Item WISP_BOTTLE;
    public static Item WINDFORCE_DART;
    public static Item WINDFORCE;
    public static Item BROKEN_TALISMAN;
    public static Item TABLET;
    public static Item CLOTH;

    public static Item IRONGRASS;
    public static Item SOUL_LOCKET;
    public static Item PROTECTION_TALISMAN;
    public static Item BLAST_SKULL;
    public static Item TAMERS_CHARM;
    public static Item WARRIORS_LOCK;
    public static Item DREAM_CATCHER;
    public static Item MAGIC_FEATHER;
    public static Item HUNTERS_EYE;
    public static Item BOUNTIFUL_SACK;
    public static Item WOLF_AMULET;
    public static Item FIRE_KEY;
    public static Item AGILITY_TALON;
    public static Item TELEKINESIS;
    public static Item WATER_RUNE;
    public static Item WHETSTONE;
    public static Item PAW;
    public static Item HORN;
    public static Item FIRERAIN;
    public static Item TRAITORS_BLADE;
    public static Item HUNGRY_TOOTH;
    public static Item GLASS_SHARD;
    public static Item HEART;
    public static Item PICKAXE_AMULET;
    public static Item LEECH;
    public static Item FISH_TAIL;
    public static Item ROCK;
    public static Item SPRING;
    public static Item FALL_FEATHER;

    public static Item MASK_W;
    public static Item MASK_H;
    public static Item MASK_S;
    public static Item MASK_P;

    public static Item CONCOCTION;

    public static Item KUNAI;
    public static Item DARK_GOOP;
    public static Item TEST;
    public static Item STORM_BOTTLE;
    //public static Item BROKEN_ARTIFACT;
    //public static Item SLAB;

    public static ItemArmor.ArmorMaterial DESERT_ROBES = EnumHelper.addArmorMaterial("desert_robes", "ancientbeasts:textures/models/armor/desert_armor.png",BeastSlayerConfig.ScaleArmorDurability, BeastSlayerConfig.ScaleArmorValue, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    public static void init() {
        SANDMONSTER_SCALE = quickItemRegistry("sandmonster_scale");
        CACTUS_BROTH = new ItemCactusBroth("cactus_broth", 12, 0.8F, false);
        LOGO = new ItemBossMask("logo_beast");
        SANDY_LOVE = new HiddenItem("sandy_love");
        SHIELD_BOOK = new ItemShieldBook("shield_book");
        ECTOPLASM = quickItemRegistry("ectoplasm");
        TOUGH_GLOVE = new ItemToughGlove("tough_glove");
        ICE_DART = new ItemDarts("ice_dart");
        ICE_WAND = new ItemIceWand("ice_wand");
        ICE_WAND_RED = new ItemIceWand("ice_wand_red");
        VESSEL = new ItemVessel("vessel");
        SPIKE = quickItemRegistry("spike");
        FUR = quickItemRegistry("charred_fur");
        CHARRED_CLOAK = new CharredArmor("charred_cloak", ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.HEAD);
        MINER_HELMET = new MinerHelmet("miner_helmet", ItemArmor.ArmorMaterial.IRON, 0 , EntityEquipmentSlot.HEAD);
        PORTABLE_WIKI = new ItemBestiary("bestiary");
        RIFTED_PEARL = new ItemRiftedPearl("rifted_pearl");

        CURSED_WOOD = quickItemRegistry("cursed_wood");
        SPEAR = new ItemSpear("spear");
        STAFF = new ItemStaff("tribe_staff");
        TORCH = new ItemWispflame("wisp_torch");
        DUST = quickItemRegistry("mysterious_dust");
        WISP_BOTTLE = new ItemWispBottle("wisp_bottle");
        KUNAI = new ItemKunai("kunai");
        DARK_GOOP = quickItemRegistry("dark_goop");
        WINDFORCE_DART = quickItemRegistry("windforce_dart");
        WINDFORCE = new ItemWindforce("windforce");
        BROKEN_TALISMAN = quickItemRegistry("broken_talisman");
        TABLET = quickItemRegistry("ancient_tablet");
        CLOTH = quickItemRegistry("cloth");

        CONCOCTION = new ItemConcoction("concoction");
        //BORIP BOD
        IRONGRASS = new ItemArtifact("irongrass", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(3);
        SOUL_LOCKET = new ItemArtifact("wisp_locket", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(1);
        PROTECTION_TALISMAN = new ItemArtifact("protection_talisman", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(2);
        BLAST_SKULL = new ItemArtifact("blast_skull", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(3);
        TAMERS_CHARM = new ItemArtifact("tamers_charm", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(2);
        WARRIORS_LOCK = new ItemArtifact("warriors_lock", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(2);
        DREAM_CATCHER = new ItemArtifact("dream_catcher", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(0);
        MAGIC_FEATHER = new ItemArtifact("magic_feather", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(3);
        HUNTERS_EYE = new ItemArtifact("hunters_eye", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(2);
        BOUNTIFUL_SACK = new ItemArtifact("bountiful_sack", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(3);
        WOLF_AMULET = new ItemArtifact("wolf_amulet", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(3);
        FIRE_KEY = new ItemArtifact("fire_key", ItemArtifact.baubleSlot.CHARM, false, false).setRarity(3);
        AGILITY_TALON = new ItemArtifact("agility_talon", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(1);
        TELEKINESIS = new ItemArtifact("telekinesis", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(1);
        WATER_RUNE = new ItemArtifact("water_rune", ItemArtifact.baubleSlot.CHARM, true, false).setRarity(2);
        WHETSTONE = new ItemArtifact("mossy_whetstone", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(0);
        PAW = new ItemArtifact("ocelot_paw", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(1);
        HORN = new ItemArtifact("fire_horn", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(1);
        FIRERAIN = new ItemFireRain("fire_rain");
        TRAITORS_BLADE = new ItemArtifact("traitors_blade", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(1);
        HUNGRY_TOOTH = new ItemArtifact("hungry_tooth", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(2);
        GLASS_SHARD = new ItemArtifact("glass_shard", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(3);
        HEART = new ItemArtifact("heart_amulet", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(3);
        PICKAXE_AMULET = new ItemArtifact("miners_charm", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(2);
        LEECH = new ItemArtifact("leech", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(2);
        FISH_TAIL = new ItemArtifact("fish_tail", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(0);
        ROCK = new ItemArtifact("heavy_rock", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(0);
        SPRING = new ItemArtifact("blazing_spring", ItemArtifact.baubleSlot.CHARM, false, false).setRarity(0);
        FALL_FEATHER = new ItemArtifact("light_feather", ItemArtifact.baubleSlot.CHARM, false, true).setRarity(0);

        MASK_W = new ItemMask("marauder_mask").setTier(0);
        MASK_H = new ItemMask("hunter_mask").setTier(1);
        MASK_S = new ItemMask("scorcher_mask").setTier(2);
        MASK_P = new ItemMask("spiritweaver_mask").setTier(3);

        STORM_BOTTLE = new ItemWeather("storm_bottle");

      //  BROKEN_ARTIFACT = quickItemRegistry("broken_artifact");

        //SLAB = new ItemSlab(ModBlocks.CURSED_SLAB_HALF, ModBlocks.CURSED_SLAB_HALF, ModBlocks.CURSED_SLAB_DOUBLE).setRegistryName("cursed_slab").setCreativeTab(BeastSlayer.BEASTSTAB);

        if(BeastSlayerConfig.isDesertRobesEnabled) {
            SCALE_ARMOR = new ScaleArmor("scale_armor", ModItems.DESERT_ROBES, 1, EntityEquipmentSlot.CHEST);
            SCALE_HOOD = new ScaleArmor("scale_hood", ModItems.DESERT_ROBES, 0, EntityEquipmentSlot.HEAD);
        }
        TEST = new ItemTest().setRegistryName("test").setUnlocalizedName("test").setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    public static Item quickItemRegistry(String name){
        return new Item().setRegistryName(name).setUnlocalizedName(name).setCreativeTab(BeastSlayer.BEASTSTAB).setMaxStackSize(64);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(SANDMONSTER_SCALE);
        event.getRegistry().registerAll(SHIELD_BOOK);
        event.getRegistry().registerAll(CACTUS_BROTH);
        event.getRegistry().registerAll(LOGO);
        event.getRegistry().registerAll(SANDY_LOVE);
        event.getRegistry().registerAll(ECTOPLASM);
        event.getRegistry().registerAll(TOUGH_GLOVE);
        event.getRegistry().registerAll(ICE_DART);
        event.getRegistry().registerAll(ICE_WAND);
        event.getRegistry().registerAll(ICE_WAND_RED);
        event.getRegistry().registerAll(VESSEL);
        event.getRegistry().registerAll(SPIKE);
        event.getRegistry().registerAll(FUR);
        event.getRegistry().registerAll(CHARRED_CLOAK);
        event.getRegistry().registerAll(MINER_HELMET);
        event.getRegistry().registerAll(PORTABLE_WIKI);
        event.getRegistry().registerAll(RIFTED_PEARL);
        event.getRegistry().registerAll(CURSED_WOOD);
        event.getRegistry().registerAll(SPEAR);
        event.getRegistry().registerAll(STAFF);
        event.getRegistry().registerAll(TORCH);
        event.getRegistry().registerAll(DUST);
        event.getRegistry().registerAll(WISP_BOTTLE);
        event.getRegistry().registerAll(KUNAI);
        event.getRegistry().registerAll(DARK_GOOP);
        event.getRegistry().registerAll(WINDFORCE_DART);
        event.getRegistry().registerAll(WINDFORCE);
        event.getRegistry().registerAll(BROKEN_TALISMAN);
        event.getRegistry().registerAll(TABLET);
        event.getRegistry().registerAll(CLOTH);

        event.getRegistry().registerAll(CONCOCTION);

        event.getRegistry().registerAll(IRONGRASS);
        event.getRegistry().registerAll(SOUL_LOCKET);
        event.getRegistry().registerAll(PROTECTION_TALISMAN);
        event.getRegistry().registerAll(BLAST_SKULL);
        event.getRegistry().registerAll(TAMERS_CHARM);
        event.getRegistry().registerAll(WARRIORS_LOCK);
        event.getRegistry().registerAll(DREAM_CATCHER);
        event.getRegistry().registerAll(MAGIC_FEATHER);
        event.getRegistry().registerAll(HUNTERS_EYE);
        event.getRegistry().registerAll(BOUNTIFUL_SACK);
        event.getRegistry().registerAll(WOLF_AMULET);
        event.getRegistry().registerAll(FIRE_KEY);
        event.getRegistry().registerAll(AGILITY_TALON);
        event.getRegistry().registerAll(TELEKINESIS);
        event.getRegistry().registerAll(WATER_RUNE);
        event.getRegistry().registerAll(WHETSTONE);
        event.getRegistry().registerAll(PAW);
        event.getRegistry().registerAll(HORN);
        event.getRegistry().registerAll(FIRERAIN);
        event.getRegistry().registerAll(TRAITORS_BLADE);
        event.getRegistry().registerAll(HUNGRY_TOOTH);
        event.getRegistry().registerAll(GLASS_SHARD);
        event.getRegistry().registerAll(HEART);
        event.getRegistry().registerAll(PICKAXE_AMULET);
        event.getRegistry().registerAll(LEECH);
        event.getRegistry().registerAll(FISH_TAIL);
        event.getRegistry().registerAll(ROCK);
        event.getRegistry().registerAll(SPRING);
        event.getRegistry().registerAll(FALL_FEATHER);
       // event.getRegistry().registerAll(BROKEN_ARTIFACT);

        event.getRegistry().registerAll(MASK_W);
        event.getRegistry().registerAll(MASK_H);
        event.getRegistry().registerAll(MASK_S);
        event.getRegistry().registerAll(MASK_P);

        event.getRegistry().registerAll(STORM_BOTTLE);

     //   event.getRegistry().registerAll(SLAB);

        if(BeastSlayerConfig.isDesertRobesEnabled) {
            event.getRegistry().registerAll(SCALE_ARMOR);
            event.getRegistry().registerAll(SCALE_HOOD);
        }
        event.getRegistry().registerAll(TEST);
    }
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {
        registerRender(SANDMONSTER_SCALE);
        registerRender(CACTUS_BROTH);
        registerRender(SHIELD_BOOK);
        registerRender(LOGO);
        registerRender(SANDY_LOVE);
        registerRender(ECTOPLASM);
      //  registerRender(TOUGH_GLOVE);
        registerRender(ICE_DART);
        registerRender(ICE_WAND);
        registerRender(ICE_WAND_RED);
        registerRender(VESSEL);
        registerRender(SPIKE);
        registerRender(FUR);
        registerRender(CHARRED_CLOAK);
        registerRender(MINER_HELMET);
        registerRender(PORTABLE_WIKI);
        registerRender(RIFTED_PEARL);
        registerRender(CURSED_WOOD);
        registerRender(SPEAR);
        //registerRender(STAFF);
        registerRender(TORCH);
        registerRender(DUST);
        registerRender(WISP_BOTTLE);
        registerRender(KUNAI);
        registerRender(DARK_GOOP);
        registerRender(WINDFORCE_DART);
        registerRender(WINDFORCE);
        registerRender(BROKEN_TALISMAN);
        registerRender(TABLET);
        registerRender(CLOTH);

        registerRender(CONCOCTION);
        registerRender(IRONGRASS);
        registerRender(SOUL_LOCKET);
        registerRender(PROTECTION_TALISMAN);
        registerRender(BLAST_SKULL);
        registerRender(TAMERS_CHARM);
        registerRender(WARRIORS_LOCK);
        registerRender(DREAM_CATCHER);
        registerRender(MAGIC_FEATHER);
        registerRender(HUNTERS_EYE);
        registerRender(BOUNTIFUL_SACK);
        registerRender(WOLF_AMULET);
        registerRender(FIRE_KEY);
        registerRender(AGILITY_TALON);
        registerRender(TELEKINESIS);
        registerRender(WATER_RUNE);
        registerRender(WHETSTONE);
        registerRender(PAW);
        registerRender(HORN);
        registerRender(FIRERAIN);
        registerRender(TRAITORS_BLADE);
        registerRender(HUNGRY_TOOTH);
        registerRender(GLASS_SHARD);
        registerRender(HEART);
        registerRender(PICKAXE_AMULET);
        registerRender(LEECH);
        registerRender(FISH_TAIL);
        registerRender(ROCK);
        registerRender(SPRING);
        registerRender(FALL_FEATHER);
       // registerRender(BROKEN_ARTIFACT);

        registerRender(MASK_W);
        registerRender(MASK_H);
        registerRender(MASK_S);
        registerRender(MASK_P);
        registerRender(TEST);
        registerRender(STORM_BOTTLE);
        if(BeastSlayerConfig.isDesertRobesEnabled) {
            registerRender(SCALE_ARMOR);
            registerRender(SCALE_HOOD);
        }
    }
    public static void registerRender(Item item)
    {
        if(!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        } else {
            NonNullList<ItemStack> list = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), list);
            for(ItemStack stack : list) {
                ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new CustomModelLoader());
            ((ItemAbstractMultimodel)TOUGH_GLOVE).registerModels();
            ((ItemAbstractMultimodel)STAFF).registerModels();
    }
}
