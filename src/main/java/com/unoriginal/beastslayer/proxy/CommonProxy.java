package com.unoriginal.beastslayer.proxy;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.blocks.tile.TileEntityMovingLight;
import com.unoriginal.beastslayer.init.*;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.worldGen.ModWorldGen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
    private static final ResourceLocation D_LIGHT = new ResourceLocation(BeastSlayer.MODID,"dynamic_light");
    public void preInit(FMLPreInitializationEvent e)
    {
        ModBlocks.init();
        ModEntities.init();
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        GameRegistry.registerTileEntity(TileEntityMovingLight.class, D_LIGHT);
        ModTriggers.init();
        BeastSlayerPacketHandler.initMessages();
    }
    public void registerParticles() {

    }

    public void openGui(ItemStack bestiary) {
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
