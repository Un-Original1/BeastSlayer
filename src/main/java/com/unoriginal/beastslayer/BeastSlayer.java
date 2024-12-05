package com.unoriginal.beastslayer;

import com.unoriginal.beastslayer.command.CommandLocateAB;
import com.unoriginal.beastslayer.gui.ABGuiHandler;
import com.unoriginal.beastslayer.proxy.CommonProxy;
import com.unoriginal.beastslayer.tab.ModTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;


@Mod(modid = BeastSlayer.MODID, name = BeastSlayer.NAME, version = BeastSlayer.VERSION)
public class BeastSlayer
{
    public static final String MODID = "ancientbeasts";
    public static final String NAME = "Beast Slayer";
    public static final String VERSION = "1.9.99999";
    public static final CreativeTabs BEASTSTAB = new ModTab("beaststab");
    @SidedProxy(serverSide = "com.unoriginal.beastslayer.proxy.CommonProxy", clientSide = "com.unoriginal.beastslayer.proxy.ClientProxy")
    public static CommonProxy commonProxy;

    @Mod.Instance
    public static BeastSlayer instance;

    public static Logger logger;

    public static ABGuiHandler guiHandler = new ABGuiHandler();

    public enum GUIs {
        WITCHCRAFT_TABLE
    }

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

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // register server commands

        event.registerServerCommand(new CommandLocateAB());
    }


}
