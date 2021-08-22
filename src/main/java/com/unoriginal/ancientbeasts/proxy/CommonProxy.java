package com.unoriginal.ancientbeasts.proxy;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.init.ModEntities;
import com.unoriginal.ancientbeasts.init.ModEvents;
import com.unoriginal.ancientbeasts.init.ModItems;
import com.unoriginal.ancientbeasts.init.ModParticles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public static Configuration config;
    public void preInit(FMLPreInitializationEvent e)
    {
        ModEntities.init();
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "ancientbeasts.cfg"));
        AncientBeastsConfig.readConfig();
    }
    public void registerParticles() {

    }
    public Object getArmorModel(Item item, EntityLivingBase entity) {
        return null;
    }
    public void init(FMLInitializationEvent e) {
        ModParticles.init();
    }
   public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
