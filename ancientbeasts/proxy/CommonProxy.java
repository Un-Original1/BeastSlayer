package com.unoriginal.ancientbeasts.proxy;

import com.unoriginal.ancientbeasts.init.ModEntities;
import com.unoriginal.ancientbeasts.init.ModEvents;
import com.unoriginal.ancientbeasts.init.ModItems;
import com.unoriginal.ancientbeasts.init.ModParticles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        ModEntities.init();
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
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
   }
}
