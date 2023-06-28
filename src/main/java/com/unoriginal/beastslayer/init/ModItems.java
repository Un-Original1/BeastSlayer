package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.items.*;
import com.unoriginal.beastslayer.items.client.CustomModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
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

    public static Item CONCOCTION;

    public static Item KUNAI;
    public static Item DARK_GOOP;

    public static ItemArmor.ArmorMaterial DESERT_ROBES = EnumHelper.addArmorMaterial("desert_robes", "ancientbeasts:textures/models/armor/desert_armor.png",28, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    public static void init() {
        SANDMONSTER_SCALE = new ItemBase("sandmonster_scale");
        CACTUS_BROTH = new ItemCactusBroth("cactus_broth", 12, 0.8F, false);
        LOGO = new HiddenItem("logo");
        SANDY_LOVE = new HiddenItem("sandy_love");
        SHIELD_BOOK = new ItemShieldBook("shield_book");
        ECTOPLASM = new ItemBase("ectoplasm");
        TOUGH_GLOVE = new ItemToughGlove("tough_glove");
        ICE_DART = new ItemDarts("ice_dart");
        ICE_WAND = new ItemIceWand("ice_wand");
        ICE_WAND_RED = new ItemIceWand("ice_wand_red");
        VESSEL = new ItemVessel("vessel");
        SPIKE = new ItemBase("spike");
        FUR = new ItemBase("charred_fur");
        CHARRED_CLOAK = new CharredArmor("charred_cloak", ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.HEAD);
        MINER_HELMET = new MinerHelmet("miner_helmet", ItemArmor.ArmorMaterial.IRON, 0 , EntityEquipmentSlot.HEAD);
        PORTABLE_WIKI = new ItemBestiary("bestiary");
        RIFTED_PEARL = new ItemRiftedPearl("rifted_pearl");

        CURSED_WOOD = new ItemBase("cursed_wood");
        SPEAR = new ItemSpear("spear");
        STAFF = new ItemStaff("tribe_staff");
        TORCH = new ItemWispflame("wisp_torch");
        DUST = new ItemBase("mysterious_dust");
        WISP_BOTTLE = new ItemWispBottle("wisp_bottle");
        KUNAI = new ItemKunai("kunai");
        DARK_GOOP = new ItemBase("dark_goop");
        WINDFORCE_DART = new ItemBase("windforce_dart");
        WINDFORCE = new ItemWindforce("windforce");
        BROKEN_TALISMAN = new ItemBase("broken_talisman");
        TABLET = new ItemBase("ancient_tablet");
        CLOTH = new ItemBase("cloth");

        CONCOCTION = new ItemConcoction("concoction");

        if(BeastSlayerConfig.isDesertRobesEnabled) {
            SCALE_ARMOR = new ScaleArmor("scale_armor", ModItems.DESERT_ROBES, 1, EntityEquipmentSlot.CHEST);
            SCALE_HOOD = new ScaleArmor("scale_hood", ModItems.DESERT_ROBES, 0, EntityEquipmentSlot.HEAD);
        }
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

        if(BeastSlayerConfig.isDesertRobesEnabled) {
            event.getRegistry().registerAll(SCALE_ARMOR);
            event.getRegistry().registerAll(SCALE_HOOD);
        }
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
