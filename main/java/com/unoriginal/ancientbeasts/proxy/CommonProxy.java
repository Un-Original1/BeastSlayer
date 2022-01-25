package com.unoriginal.ancientbeasts.proxy;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.blocks.tile.TileEntityMovingLight;
import com.unoriginal.ancientbeasts.init.*;
import com.unoriginal.ancientbeasts.worldGen.ModWorldGen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
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
    private static final ResourceLocation D_LIGHT = new ResourceLocation(AncientBeasts.MODID,"dynamic_light");
    public void preInit(FMLPreInitializationEvent e)
    {
        ModBlocks.init();
        ModEntities.init();
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        GameRegistry.registerTileEntity(TileEntityMovingLight.class, D_LIGHT);
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
