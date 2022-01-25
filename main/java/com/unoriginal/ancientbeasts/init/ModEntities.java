package com.unoriginal.ancientbeasts.init;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.*;
import com.unoriginal.ancientbeasts.entity.Render.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@Mod.EventBusSubscriber
public class ModEntities
{
    public static void init() {
        int id = 1;
        Multimap<BiomeDictionary.Type, Biome> biomesAndTypes = HashMultimap.create();
        for (Biome b : Biome.REGISTRY)
        {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(b);
            for (BiomeDictionary.Type t : types)
            {
                biomesAndTypes.put(t, b);
            }
        }
        if(AncientBeastsConfig.isSandmonsterEnabled) {
            EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Sandy"), EntitySandy.class, "SandMonster", id++, AncientBeasts.instance, 140, 3, true, 10577723, 10038792);
        }

        EntityRegistry.addSpawn(EntitySandy.class, 1 , 1, 1, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS);
        EntityRegistry.addSpawn(EntityZealot.class, AncientBeastsConfig.zealotSpawnChance, 1, 1, EnumCreatureType.MONSTER, biomesAndTypes.get(BiomeDictionary.Type.SPOOKY).toArray(new Biome[0]));

        EntityRegistry.addSpawn(EntityGhost.class, AncientBeastsConfig.ghostSpawnChance, 1, 3, EnumCreatureType.MONSTER, biomesAndTypes.get(BiomeDictionary.Type.SPOOKY).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityGiant.class, AncientBeastsConfig.giantSpawnChance, 1, 1, EnumCreatureType.MONSTER, Biomes.PLAINS, Biomes.MUTATED_PLAINS);
        EntityRegistry.addSpawn(EntityFrostashFox.class, AncientBeastsConfig.foxSpawnChance, 1, 6, EnumCreatureType.CREATURE, biomesAndTypes.get(BiomeDictionary.Type.SNOWY).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityFrostWalker.class, AncientBeastsConfig.frostWalkerSpawnChance, 1, 2, EnumCreatureType.MONSTER, biomesAndTypes.get(BiomeDictionary.Type.SNOWY).toArray(new Biome[0]));

        EntityRegistry.addSpawn(EntityNetherhound.class, AncientBeastsConfig.netherhoundSpawnChance, 4, 4, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).toArray(new Biome[0]));

        for(BiomeDictionary.Type type : BiomeDictionary.Type.getAll()) {
            if (!type.equals(BiomeDictionary.Type.NETHER) && !type.equals(BiomeDictionary.Type.END) && !type.equals(BiomeDictionary.Type.MUSHROOM)) {
                EntityRegistry.addSpawn(EntityDamcell.class, AncientBeastsConfig.damcellSpawnChance, 1, 1, EnumCreatureType.MONSTER, biomesAndTypes.get(type).toArray(new Biome[0]));
                EntityRegistry.addSpawn(EntityBoulderer.class, AncientBeastsConfig.boulderingSpawnChance, 1, 4, EnumCreatureType.MONSTER, biomesAndTypes.get(type).toArray(new Biome[0]));
                if(AncientBeastsConfig.zealotSpawnEverywhere){
                    EntityRegistry.addSpawn(EntityZealot.class, AncientBeastsConfig.zealotEverywhereSpawnChance, 1, 1, EnumCreatureType.MONSTER, biomesAndTypes.get(type).toArray(new Biome[0]));
                }
            }
        } // I have no idea why I declare spawns first and then register entities, bad habit
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Ghost"), EntityGhost.class, "Ghost", id++, AncientBeasts.instance, 64, 3, true, 1936548, 10610664);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Zealot"), EntityZealot.class, "Zealot", id++, AncientBeasts.instance, 64, 3, true, 12698049, 6894452);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Ice_dart"), EntityIceDart.class, "Ice_dart", id++, AncientBeasts.instance, 64, 3, false);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "V_head"), EntityVesselHead.class, "V_head", id++, AncientBeasts.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Ball"), EntityBall.class, "Ball", id++, AncientBeasts.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Vessel"), EntityVessel.class, "Vessel", id++, AncientBeasts.instance, 64, 3, true, 4618612, 8139824);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Fake_Vessel"), EntityFakeDuplicate.class, "Fake_Vessel", id++, AncientBeasts.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Frostash_fox"), EntityFrostashFox.class, "Frostash_fox", id++, AncientBeasts.instance, 64, 3, true , 1285375, 14631743);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Frost_walker"), EntityFrostWalker.class, "Frost_walker", id++, AncientBeasts.instance, 64, 3, true , 5403256, 9748157);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Ice_crystal"), EntityIceCrystal.class, "Ice_crystal", id++, AncientBeasts.instance, 64, 3,false);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Giant_Zombie"), EntityGiant.class, "Giant_Zombie", id++, AncientBeasts.instance, 128, 3, true, 6501393, 7969893);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Boulder"), EntityBoulder.class, "Boulder", id++, AncientBeasts.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Tornado"), EntityTornado.class, "Tornado", id++, AncientBeasts.instance, 64, 3,false);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Sand_Shot"), EntitySandSpit.class, "Sand_Shot", id++, AncientBeasts.instance, 64, 10,true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID,"Beam"), EntityBeam.class, "Beam", id++, AncientBeasts.instance, 64, 3,true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Lil_Vessel"), EntityLilVessel.class, "Lil_Vessel", id++, AncientBeasts.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Damcell"), EntityDamcell.class, "Damcell", id++, AncientBeasts.instance, 64, 3, true, 3750992, 6060434);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Damcell_Spike"), EntityDamcellSpike.class, "Damcell_Spike", id++, AncientBeasts.instance, 64, 6, false);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Netherhound"), EntityNetherhound.class, "Netherhound", id++, AncientBeasts.instance, 64, 3, true, 3287332, 12698049);
        EntityRegistry.registerModEntity(new ResourceLocation(AncientBeasts.MODID, "Boulderer"), EntityBoulderer.class, "Boulderer", id++, AncientBeasts.instance, 64, 3, true, 7876898, 7956065);
    }
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySandy.class, RenderSandy.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityTornado.class, RenderTornado.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySandSpit.class, RenderSandSpit.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityZealot.class, RenderZealot.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, RenderBeam.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, RenderGhost.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGiant.class, RenderGiant.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoulder.class, RenderBoulder.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFrostashFox.class, RenderFrostashFox.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityIceDart.class, RenderIceDart.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFrostWalker.class, RenderFrostWalker.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityIceCrystal.class, RenderIceCrystal.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityVessel.class, RenderVessel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFakeDuplicate.class, RenderFakeDuplicate.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBall.class, RenderBall.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityVesselHead.class, RenderVesselHead.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityLilVessel.class, RenderLilVessel.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDamcell.class, RenderDamcell.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDamcellSpike.class, RenderDamcellSpike.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityNetherhound.class, RenderNetherhound.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoulderer.class, RenderBoulderer.FACTORY);
    }
}
