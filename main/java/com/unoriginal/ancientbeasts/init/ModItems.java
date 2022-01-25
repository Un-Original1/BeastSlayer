package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        if(AncientBeastsConfig.isDesertRobesEnabled) {
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
        if(AncientBeastsConfig.isDesertRobesEnabled) {
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
        registerRender(TOUGH_GLOVE);
        registerRender(ICE_DART);
        registerRender(ICE_WAND);
        registerRender(ICE_WAND_RED);
        registerRender(VESSEL);
        registerRender(SPIKE);
        registerRender(FUR);
        registerRender(CHARRED_CLOAK);
        registerRender(MINER_HELMET);
        if(AncientBeastsConfig.isDesertRobesEnabled) {
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
}
