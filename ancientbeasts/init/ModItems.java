package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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

    public static ItemArmor.ArmorMaterial DESERT_ROBES = EnumHelper.addArmorMaterial("desert_robes", "ancientbeasts:textures/models/armor/desert_armor.png",28, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    public static void init() {
        SANDMONSTER_SCALE = new ItemBase("sandmonster_scale");
        CACTUS_BROTH = new ItemCactusBroth("cactus_broth", 12, 12.0F, false);
        LOGO = new HiddenItem("logo");
        SANDY_LOVE = new HiddenItem("sandy_love");
        SHIELD_BOOK = new ItemShieldBook("shield_book");
        ECTOPLASM = new ItemBase("ectoplasm");
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
        if(AncientBeastsConfig.isDesertRobesEnabled) {
            registerRender(SCALE_ARMOR);
            registerRender(SCALE_HOOD);
        }
    }
    public static void registerRender(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
