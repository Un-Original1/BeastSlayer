package com.unoriginal.ancientbeasts;

import com.unoriginal.ancientbeasts.proxy.CommonProxy;
import com.unoriginal.ancientbeasts.tab.ModTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = AncientBeasts.MODID, name = AncientBeasts.NAME, version = AncientBeasts.VERSION)
public class AncientBeasts
{
    public static final String MODID = "ancientbeasts";
    public static final String NAME = "Ancient Beasts";
    public static final String VERSION = "1.0";
    public static final CreativeTabs BEASTSTAB = new ModTab("beaststab");

    @SidedProxy(serverSide = "com.unoriginal.ancientbeasts.proxy.CommonProxy", clientSide = "com.unoriginal.ancientbeasts.proxy.ClientProxy")
    public static CommonProxy commonProxy;

    @Mod.Instance
    public static AncientBeasts instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        commonProxy.preInit(event);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        commonProxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        commonProxy.postInit(e);
    }
}
