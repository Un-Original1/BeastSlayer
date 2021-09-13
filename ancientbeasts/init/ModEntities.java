package com.unoriginal.ancientbeasts.init;


import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.*;
import com.unoriginal.ancientbeasts.entity.Render.*;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEntities
{
    public static void init() {
        int id = 1;
        if(AncientBeastsConfig.isSandmonsterEnabled) {
            EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Sandy"), EntitySandy.class, "SandMonster", id++, AncientBeasts.instance, 140, 3, true, 10577723, 10038792);
        }
        EntityRegistry.addSpawn(EntitySandy.class, 1 , 1, 1, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS);
        EntityRegistry.addSpawn(EntityZealot.class, AncientBeastsConfig.zealotSpawnChance, 1, 1, EnumCreatureType.MONSTER, Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST);
        if(AncientBeastsConfig.zealotSpawnEverywhere){
            EntityRegistry.addSpawn(EntityZealot.class, AncientBeastsConfig.zealotEverywhereSpawnChance, 1, 1, EnumCreatureType.MONSTER);
        }
        if(AncientBeastsConfig.isZealotEnabled) {
            EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Zealot"), EntityZealot.class, "Zealot", id++, AncientBeasts.instance, 64, 3, true, 10855845, 6894452);
        }
        EntityRegistry.addSpawn(EntityGhost.class, AncientBeastsConfig.ghostSpawnChance, 1, 3, EnumCreatureType.MONSTER, Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST);
        if(AncientBeastsConfig.isGhostEnabled) {
            EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Ghost"), EntityGhost.class, "Ghost", id++, AncientBeasts.instance, 64, 3, true, 1936548, 10610664);
        }
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Tornado"), EntityTornado.class, "Tornado", id++, AncientBeasts.instance, 64, 3,true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Sand_Shot"), EntitySandSpit.class, "Sand_Shot", id++, AncientBeasts.instance, 64, 3,true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Shooter"), EntitySandShooter.class, "Shooter", id++, AncientBeasts.instance, 64, 3,true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Beam"), EntityBeam.class, "Beam", id++, AncientBeasts.instance, 64, 3,true);
    }
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySandy.class, RenderSandy.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTornado.class, RenderTornado.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySandSpit.class, RenderSandSpit.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySandShooter.class, RenderSandShooter.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityZealot.class, RenderZealot.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, RenderBeam.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, RenderGhost.FACTORY);
    }
}
