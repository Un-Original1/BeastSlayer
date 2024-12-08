package com.unoriginal.beastslayer.init;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.*;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.misc.EntityMoveTile;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.misc.render.RenderMoveTile;
import com.unoriginal.beastslayer.entity.Entities.boss.projectile.ProjectileMeteor;
import com.unoriginal.beastslayer.entity.Render.*;
import com.unoriginal.beastslayer.entity.Render.projectile.RenderProjectile;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type;

@Mod.EventBusSubscriber
public class ModEntities
{
    public static void init() {
        int id = 1;


        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Sandy"), EntitySandy.class, "SandMonster", id++, BeastSlayer.instance, 140, 3, true, 10577723, 10038792);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Ghost"), EntityGhost.class, "Ghost", id++, BeastSlayer.instance, 80, 3, true, 1936548, 10610664);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Zealot"), EntityZealot.class, "Zealot", id++, BeastSlayer.instance, 80, 3, true, 12698049, 6894452);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Ice_dart"), EntityIceDart.class, "Ice_dart", id++, BeastSlayer.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "V_head"), EntityVesselHead.class, "V_head", id++, BeastSlayer.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Ball"), EntityBall.class, "Ball", id++, BeastSlayer.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Vessel"), EntityVessel.class, "Vessel", id++, BeastSlayer.instance, 80, 3, true, 4618612, 8139824);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Fake_Vessel"), EntityFakeDuplicate.class, "Fake_Vessel", id++, BeastSlayer.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Frostash_fox"), EntityFrostashFox.class, "Frostash_fox", id++, BeastSlayer.instance, 80, 3, true , 1285375, 14631743);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Frost_walker"), EntityFrostWalker.class, "Frost_walker", id++, BeastSlayer.instance, 80, 3, true , 5403256, 9748157);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID,"Ice_crystal"), EntityIceCrystal.class, "Ice_crystal", id++, BeastSlayer.instance, 80, 3,false);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Giant_Zombie"), EntityGiant.class, "Giant_Zombie", id++, BeastSlayer.instance, 80, 3, true, 6501393, 7969893);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Boulder"), EntityBoulder.class, "Boulder", id++, BeastSlayer.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID,"Tornado"), EntityTornado.class, "Tornado", id++, BeastSlayer.instance, 80, 3,false);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID,"Sand_Shot"), EntitySandSpit.class, "Sand_Shot", id++, BeastSlayer.instance, 64, 10,true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID,"Beam"), EntityBeam.class, "Beam", id++, BeastSlayer.instance, 64, 1,true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Lil_Vessel"), EntityLilVessel.class, "Lil_Vessel", id++, BeastSlayer.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Damcell"), EntityDamcell.class, "Damcell", id++, BeastSlayer.instance, 80, 3, true, 3750992, 6060434);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Damcell_Spike"), EntityDamcellSpike.class, "Damcell_Spike", id++, BeastSlayer.instance, 64, 6, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Netherhound"), EntityNetherhound.class, "Netherhound", id++, BeastSlayer.instance, 80, 3, true, 3287332, 12698049);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Boulderer"), EntityBoulderer.class, "Boulderer", id++, BeastSlayer.instance, 80, 3, true, 7876898, 7956065);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Owlstack"), EntityOwlstack.class, "Owlstack", id++, BeastSlayer.instance, 80, 3, true , 3941649 , 13534776);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Rifted_Enderman"), EntityRiftedEnderman.class, "Rifted_Enderman", id++, BeastSlayer.instance, 80, 3, true, 16777215, 12833728);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Rifted_pearl"), EntityRiftedPearl.class, "Rifted_pearl", id++, BeastSlayer.instance, 64, 3, true);

        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "TribeWarrior"), EntityTribeWarrior.class, "Tribe_Warrior", id++, BeastSlayer.instance, 80, 1, true, 4679293, 4864586);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Hunter"), EntityHunter.class, "Hunter", id++, BeastSlayer.instance, 80, 1, true, 9061427, 4864586);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Scorcher"), EntityTank.class, "Scorcher", id++, BeastSlayer.instance, 80, 1, true, 4679293, 6759439);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Priest"), EntityPriest.class, "Priest", id++, BeastSlayer.instance, 80, 1, true, 6759439, 4864586);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Wispfire"), EntityWispfire.class, "Wispfire", id++, BeastSlayer.instance, 64, 1, true);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Wisp"), EntityWisp.class, "Wisp", id++, BeastSlayer.instance, 64, 3, true);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Fire_Elemental"), EntityFireElemental.class, "Fire_Elemental", id++, BeastSlayer.instance, 128, 1, true, 0, 0);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Windforce_dart"), EntityWindforceDart.class, "Windforce_dart", id++, BeastSlayer.instance, 64, 1, true);

            //Fire Boss Misc
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "shock_wave_tile"), EntityMoveTile.class, "shock_wave_tile", id++, BeastSlayer.instance, 64, 1, true);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "elemental_meteor"), ProjectileMeteor.class, "elemental_meteor", id++, BeastSlayer.instance, 64, 1, true);
            //artifact entities
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Hand"), EntityHand.class, "Hand", id++, BeastSlayer.instance, 64, 3, true);
            EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Hunter_Wolf"), EntitySpiritWolf.class, "Hunter_Wolf", id++,BeastSlayer.instance, 64, 3, true);
        }
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Bonepile"), EntityBonepile.class, "Bonepile", id++, BeastSlayer.instance, 80, 3, true, 14540253, 11315887);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Chained"), EntityChained.class, "Chained", id++, BeastSlayer.instance,64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Nekros"), EntityNekros.class, "Nekros", id++, BeastSlayer.instance, 80, 3, true, 5058665, 3416400);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Storm_Mob"), EntityStormSetter.class, "Storm_Mob", id++, BeastSlayer.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BeastSlayer.MODID, "Tribe_Child"), EntityTribeChild.class, "Tribe_Child", id++, BeastSlayer.instance, 64, 1, true, 0, 0);


    }

    //it never worked cause it was run on pre init, it must be done on post init
    public static void registerSpawns(){

        EntityRegistry.addSpawn(EntitySandy.class, BeastSlayerConfig.sandmonsterSpawnChance, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.SANDY).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityZealot.class, BeastSlayerConfig.zealotSpawnChance, 1, 1, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(Type.SPOOKY).toArray(new Biome[0]));

        EntityRegistry.addSpawn(EntityGhost.class, BeastSlayerConfig.ghostSpawnChance, 1, 3, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(Type.SPOOKY).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityGiant.class, BeastSlayerConfig.giantSpawnChance, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.PLAINS).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityFrostashFox.class, BeastSlayerConfig.foxSpawnChance, 1, 6, EnumCreatureType.CREATURE,  BiomeDictionary.getBiomes(Type.SNOWY).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityFrostWalker.class, BeastSlayerConfig.frostWalkerSpawnChance, 1, 2, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(Type.SNOWY).toArray(new Biome[0]));

        EntityRegistry.addSpawn(EntityNetherhound.class, BeastSlayerConfig.netherhoundSpawnChance, 2, 4, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityOwlstack.class, BeastSlayerConfig.owlstackSpawnChance, 2, 4, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.MUTATED_BIRCH_FOREST, Biomes.MUTATED_BIRCH_FOREST_HILLS);

        EntityRegistry.addSpawn(EntityNekros.class, BeastSlayerConfig.nekrosSpawnChance, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.SPOOKY).toArray(new Biome[0]));

        EntityRegistry.addSpawn(EntityTribeWarrior.class, 70, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.JUNGLE).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityHunter.class, 60, 1, 4, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.JUNGLE).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityTribeChild.class, 60, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.JUNGLE).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityPriest.class, 20, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.JUNGLE).toArray(new Biome[0]));
        EntityRegistry.addSpawn(EntityTank.class, 40, 1, 2, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.JUNGLE).toArray(new Biome[0]));


        Multimap<Type, Biome> ExclusiveList = HashMultimap.create();

        for (Biome b : Biome.REGISTRY)
        {
            Set<Type> types = BiomeDictionary.getTypes(b);
            for (BiomeDictionary.Type t : types)
            {
                if(!BiomeDictionary.hasType(b, Type.NETHER) && !BiomeDictionary.hasType(b, Type.MUSHROOM) && !BiomeDictionary.hasType(b, Type.END)) {
                    ExclusiveList.put(t, b);
                }
            }
        }

        for(Type type : Type.getAll()) {
            EntityRegistry.addSpawn(EntityDamcell.class, BeastSlayerConfig.damcellSpawnChance, 1, 1, EnumCreatureType.MONSTER, ExclusiveList.get(type).toArray(new Biome[0]));
            EntityRegistry.addSpawn(EntityBoulderer.class, BeastSlayerConfig.boulderingSpawnChance, 1, 4, EnumCreatureType.MONSTER,  ExclusiveList.get(type).toArray(new Biome[0]));
            EntityRegistry.addSpawn(EntityRiftedEnderman.class, BeastSlayerConfig.riftedEndermanSpawnChance, 1, 1, EnumCreatureType.MONSTER,  ExclusiveList.get(type).toArray(new Biome[0]));

            EntityRegistry.addSpawn(EntityBonepile.class, BeastSlayerConfig.bonepileSpawnChance, 1, 3, EnumCreatureType.MONSTER,  ExclusiveList.get(type).toArray(new Biome[0]));

            if (BeastSlayerConfig.zealotSpawnEverywhere) {
                EntityRegistry.addSpawn(EntityZealot.class, BeastSlayerConfig.zealotEverywhereSpawnChance, 1, 1, EnumCreatureType.MONSTER,  ExclusiveList.get(type).toArray(new Biome[0]));
            }
        }
        //Left here in case above breaks
     /*   for(Type type : Type.getAll()) {
            if (!type.equals(Type.NETHER) && !type.equals(Type.END) && !type.equals(Type.MUSHROOM)) {
                EntityRegistry.addSpawn(EntityDamcell.class, BeastSlayerConfig.damcellSpawnChance, 1, 1, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(type).toArray(new Biome[0]));
                EntityRegistry.addSpawn(EntityBoulderer.class, BeastSlayerConfig.boulderingSpawnChance, 1, 4, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(type).toArray(new Biome[0]));
                EntityRegistry.addSpawn(EntityRiftedEnderman.class, BeastSlayerConfig.riftedEndermanSpawnChance, 1, 1, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(type).toArray(new Biome[0]));

                EntityRegistry.addSpawn(EntityBonepile.class, BeastSlayerConfig.bonepileSpawnChance, 1, 3, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(type).toArray(new Biome[0]));

                if(BeastSlayerConfig.zealotSpawnEverywhere){
                    EntityRegistry.addSpawn(EntityZealot.class, BeastSlayerConfig.zealotEverywhereSpawnChance, 1, 1, EnumCreatureType.MONSTER,  BiomeDictionary.getBiomes(type).toArray(new Biome[0]));
                }
            }
        }*/

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
        RenderingRegistry.registerEntityRenderingHandler(EntityOwlstack.class, RenderOwlstack.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRiftedEnderman.class, RenderRiftedEnderman.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRiftedPearl.class, RenderRiftedPearl.FACTORY);

        if(BeastSlayerConfig.EnableExperimentalFeatures) {
            RenderingRegistry.registerEntityRenderingHandler(EntityTribeWarrior.class, RenderTribeWarrior.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityHunter.class, RenderHunter.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityTank.class, RenderTank.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityPriest.class, RenderPriest.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityWispfire.class, RenderWispFire.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityWisp.class, RenderWisp.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityFireElemental.class, RenderFireElemental.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntityWindforceDart.class, RenderWindforceDart.FACTORY);

            RenderingRegistry.registerEntityRenderingHandler(EntityHand.class, RenderHand.FACTORY);
            RenderingRegistry.registerEntityRenderingHandler(EntitySpiritWolf.class, RenderSpiritWolf.FACTORY);

            //Fire Boss Misc
            RenderingRegistry.registerEntityRenderingHandler(EntityMoveTile.class, RenderMoveTile::new);
            registerProjectileRenderer(ProjectileMeteor.class, Items.FIRE_CHARGE);

            RenderingRegistry.registerEntityRenderingHandler(EntityTribeChild.class, RenderTribeChild.FACTORY);
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityBonepile.class, RenderBonePile.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityChained.class, RenderChained.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityNekros.class, RenderNekros.FACTORY);

        RenderingRegistry.registerEntityRenderingHandler(EntityStormSetter.class, RenderStormSetter.FACTORY);
    }


    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass) {
        registerProjectileRenderer(projectileClass, null);
    }

    /**
     * Makes a projectile render with the given item
     *
     * @param projectileClass
     */
    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass, Item item) {
        RenderingRegistry.registerEntityRenderingHandler(projectileClass, new IRenderFactory<T>() {
            @Override
            public Render<? super T> createRenderFor(RenderManager manager) {
                return new RenderProjectile<T>(manager, Minecraft.getMinecraft().getRenderItem(), item);
            }
        });
    }
}
