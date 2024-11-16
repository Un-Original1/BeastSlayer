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

    public static Item MASK_W;
    public static Item MASK_H;
    public static Item MASK_S;
    public static Item MASK_P;

    public static Item CONCOCTION;

    public static Item KUNAI;
    public static Item DARK_GOOP;
    public static Item TEST;
    public static Item STORM_BOTTLE;
    //public static Item SLAB;

    public static ItemArmor.ArmorMaterial DESERT_ROBES = EnumHelper.addArmorMaterial("desert_robes", "ancientbeasts:textures/models/armor/desert_armor.png",BeastSlayerConfig.ScaleArmorDurability, BeastSlayerConfig.ScaleArmorValue, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    public static void init() {
        SANDMONSTER_SCALE = quickItemRegistry("sandmonster_scale");
        CACTUS_BROTH = new ItemCactusBroth("cactus_broth", 12, 0.8F, false);
        LOGO = new HiddenItem("logo");
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
        IRONGRASS = new ItemArtifact("irongrass");
        SOUL_LOCKET = new ItemArtifact("wisp_locket");
        PROTECTION_TALISMAN = new ItemArtifact("protection_talisman");
        BLAST_SKULL = new ItemArtifact("blast_skull");
        TAMERS_CHARM = new ItemArtifact("tamers_charm");
        WARRIORS_LOCK = new ItemArtifact("warriors_lock");
        DREAM_CATCHER = new ItemArtifact("dream_catcher");
        MAGIC_FEATHER = new ItemArtifact("magic_feather");
        HUNTERS_EYE = new ItemArtifact("hunters_eye");
        BOUNTIFUL_SACK = new ItemArtifact("bountiful_sack");
        WOLF_AMULET = new ItemArtifact("wolf_amulet");
        FIRE_KEY = new ItemArtifact("fire_key");
        AGILITY_TALON = new ItemArtifact("agility_talon");
        TELEKINESIS = new ItemArtifact("telekinesis");
        WATER_RUNE = new ItemArtifact("water_rune");
        WHETSTONE = new ItemArtifact("mossy_whetstone");
        PAW = new ItemArtifact("ocelot_paw");
        HORN = new ItemArtifact("fire_horn");
        FIRERAIN = new ItemFireRain("fire_rain");

        MASK_W = new ItemMask("marauder_mask").setTier(0);
        MASK_H = new ItemMask("hunter_mask").setTier(1);
        MASK_S = new ItemMask("scorcher_mask").setTier(2);
        MASK_P = new ItemMask("spiritweaver_mask").setTier(3);

        STORM_BOTTLE = new ItemWeather("storm_bottle");

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
