package com.unoriginal.beastslayer.worldGen;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityVessel;
import com.unoriginal.beastslayer.entity.Entities.EntityZealot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CircusWorldGen extends WorldGenerator {
    private static final List<Biome> BIOMES = Arrays.asList(Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST);
    private static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "structures/Circus");
    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        int i = 0;
        int j = -8;
        int k = 7;
        WorldServer worldserver = (WorldServer) world;
        Biome biome = world.getBiome(position);
        MinecraftServer minecraftserver = world.getMinecraftServer();
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation("ancientbeasts:circus2"));
        Template template1 = templatemanager.getTemplate(minecraftserver, new ResourceLocation("ancientbeasts:circus1")); //I just realised I named circus 2 1 and 1 2

        if(ModWorldGen.canSpawnHere(template, worldserver, position) /*&& ModWorldGen.canSpawnHere(template1, worldserver, position)*/ && BIOMES.contains(biome)) {
            IBlockState iblockstate = world.getBlockState(position);
            world.notifyBlockUpdate(position, iblockstate, iblockstate, 3);

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
                    .setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null)
                    .setReplacedBlock(Blocks.STRUCTURE_VOID).setIgnoreStructureBlock(false);

            template.getDataBlocks(position, placementsettings);
            template1.getDataBlocks(position, placementsettings);
            template.addBlocksToWorld(world, position.add(j, 1, i), placementsettings, 2 | 16);
            template1.addBlocksToWorld(world, position.add(k, 1, i), placementsettings, 2 | 16);
            Map<BlockPos, String> map = template.getDataBlocks(position, placementsettings);

            for (Map.Entry<BlockPos, String> entry : map.entrySet())
            {
                if ("chest".equals(entry.getValue()))
                {
                    BlockPos blockpos2 = entry.getKey().add(j, 0, i);
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(blockpos2);

                    if (tileentity instanceof TileEntityChest)
                    {
                        ((TileEntityChest)tileentity).setLootTable(LOOT, rand.nextLong());
                    }
                }
                if ("zombie".equals(entry.getValue()) || "evoker".equals(entry.getValue()) || "zealot".equals(entry.getValue()) || "witch".equals(entry.getValue()) ){ //initially I wanted a fixed audience, on second thought bad idea

                    BlockPos blockpos2 = entry.getKey().add(j, 0, i);
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);

                    if(rand.nextInt(10) > 2 ){
                        EntityZombieVillager zombie = new EntityZombieVillager(world);
                        VillagerRegistry.setRandomProfession(zombie, world.rand);
                        zombie.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        zombie.enablePersistence();
                        world.spawnEntity(zombie);
                    }
                    if(rand.nextInt(10) > 6 ){
                        EntityWitch witch = new EntityWitch(world);
                        witch.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        witch.enablePersistence();
                        world.spawnEntity(witch);
                    }
                    if(rand.nextInt(10) == 9){
                        EntityZealot entityZealot = new EntityZealot(world);
                        entityZealot.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        entityZealot.enablePersistence();
                        world.spawnEntity(entityZealot);
                    }
                }
            }
            Map<BlockPos, String> map2 = template1.getDataBlocks(position, placementsettings);
            for (Map.Entry<BlockPos, String> entry2 : map2.entrySet())
            {
                if ("chest".equals(entry2.getValue()))
                {
                    BlockPos blockpos2 = entry2.getKey().add(k, 0, i);
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(blockpos2);

                    if (tileentity instanceof TileEntityChest)
                    {
                        ((TileEntityChest)tileentity).setLootTable(LOOT, rand.nextLong());
                    }
                }
                if ("vessel".equals(entry2.getValue()))
                {
                    BlockPos blockpos2 = entry2.getKey().add(k, 0, i);
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);
                    EntityVessel vessel = new EntityVessel(world);
                    vessel.setPosition(blockpos2.getX() + 0.5F, blockpos2.getY() + 1F, blockpos2.getZ() + 0.5F);
                    vessel.enablePersistence();
                    world.spawnEntity(vessel);
                }
                if ("zombie".equals(entry2.getValue()) || "evoker".equals(entry2.getValue()) || "zealot".equals(entry2.getValue()) || "witch".equals(entry2.getValue()) ){

                    BlockPos blockpos2 = entry2.getKey().add(k, 0, i);
                    world.setBlockState(blockpos2.up(), Blocks.AIR.getDefaultState(), 3);

                    if(rand.nextInt(10) > 2 ){
                        EntityZombieVillager zombie = new EntityZombieVillager(world);
                        zombie.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        zombie.enablePersistence();
                        world.spawnEntity(zombie);
                    }
                    if(rand.nextInt(10) > 6 ){
                        EntityWitch witch = new EntityWitch(world);
                        witch.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        witch.enablePersistence();
                        world.spawnEntity(witch);
                    }
                    if(rand.nextInt(10) == 9){
                        EntityZealot zealot = new EntityZealot(world);
                        zealot.setPosition(blockpos2.getX(), blockpos2.getY() + 1F, blockpos2.getZ());
                        zealot.enablePersistence();
                        world.spawnEntity(zealot);
                    }
                }
            }
            return true;
        }

        return false;
    }
    //worldgen lag may still exist, BUT I'm lazy so XD
}
