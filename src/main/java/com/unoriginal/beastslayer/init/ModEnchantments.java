package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.enchantments.EnchantmentHook;
import com.unoriginal.beastslayer.enchantments.EnchantmentPulling;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModEnchantments {
    public static final EnchantmentPulling pulling = new EnchantmentPulling(Enchantment.Rarity.COMMON, EnumEnchantmentType.WEAPON);
    public static final EnchantmentHook hook = new EnchantmentHook(Enchantment.Rarity.COMMON, EnumEnchantmentType.WEAPON);

    @SubscribeEvent
    public static void RegisterEnchantments(RegistryEvent.Register<Enchantment> event){
        event.getRegistry().register(pulling);
        event.getRegistry().register(hook);
    }

}
