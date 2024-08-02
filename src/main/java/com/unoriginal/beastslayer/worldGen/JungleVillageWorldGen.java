package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JungleVillageWorldGen extends WorldGenerator {
    public static List<Biome> VALID_BIOMES = Arrays.asList(Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE);
    private final List<Biome.SpawnListEntry> spawnList = Lists.newArrayList();
    private int separation;
    private int spacing;

    public JungleVillageWorldGen(){
        this.spacing = 40;
        this.separation = 22;
        //add spawns to biome

        this.spawnList.add(new Biome.SpawnListEntry(EntityTribeWarrior.class, 20, 1, 3));
        this.spawnList.add(new Biome.SpawnListEntry(EntityTank.class, 20, 1, 2));
        this.spawnList.add(new Biome.SpawnListEntry(EntityHunter.class, 10, 1, 3));
        this.spawnList.add(new Biome.SpawnListEntry(EntityPriest.class, 10, 1, 1));
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        boolean canSpawn = canSpawnStructureAtCoords(world, (position.getX() - 8)  >> 4, (position.getZ() - 8)  >> 4);
       // if (new Random().nextInt(9) == 0) {
        if(canSpawn) {
            int new_size = 96;
          //  BeastSlayer.logger.debug("generating village!" + position);
            getStructureStart(world, position.getX(), position.getZ(), rand).generateStructure(world, rand, new StructureBoundingBox(position.getX() - new_size, position.getZ() - new_size, position.getX() + new_size, position.getZ() + new_size));
            //    }
        }
        return canSpawn;
    }

    public boolean generateBypass(World world, Random rand, BlockPos position){
        int new_size = 96;
        //  BeastSlayer.logger.debug("generating village!" + position);
        getStructureStart(world, position.getX(), position.getZ(), rand).generateStructure(world, rand, new StructureBoundingBox(position.getX() - new_size, position.getZ() - new_size, position.getX() + new_size, position.getZ() + new_size));
        return true;
    }

    public void addToBiome(IChunkGenerator generator, BlockPos pos, World world){
        boolean canSpawn = canSpawnStructureAtCoords(world, (pos.getX() - 8)  >> 4, (pos.getZ() - 8)  >> 4);
        if (generator != null && canSpawn) {
            for (int i =0; i < this.spawnList.size(); i++) {
                generator.getPossibleCreatures(EnumCreatureType.CREATURE, pos).add(spawnList.get(i));
            //    BeastSlayer.logger.debug(generator.getPossibleCreatures(EnumCreatureType.CREATURE, pos));
            }
        }
    }

    public String getStructureName() {
        return "Jungle_village";
    }

    protected boolean canSpawnStructureAtCoords(World world, int chunkX, int chunkZ) {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.spacing - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.spacing - 1;
        }

        int k = chunkX / this.spacing;
        int l = chunkZ / this.spacing;
        Random random =  world.setRandomSeed(k, l, 10387312);
        k = k * this.spacing;
        l = l * this.spacing;
        k = k + (random.nextInt(this.spacing - this.separation) + random.nextInt(this.spacing - this.separation)) / 2;
        l = l + (random.nextInt(this.spacing - this.separation) + random.nextInt(this.spacing - this.separation)) / 2;

        if (i == k && j == l)
        {

            return world.getBiomeProvider().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, VALID_BIOMES) /*&& random.nextInt(20) == 0*/;
        } else {

            return false;
        }
    }

    protected StructureStart getStructureStart(World world, int chunkX, int chunkZ, Random rand) {
        return new JungleVillageWorldGen.Start(world, rand , chunkX, chunkZ, 1);
    }

    public static class Start extends StructureStart {
        private boolean valid;

        public Start(){

        }
        public Start(World worldIn, Random rand, int x, int z, int size){
            super(x, z);
            this.create(worldIn, rand, x, z, size);
        }

        public void create(World worldIn, Random rand, int x, int z, int size)
        {
            Rotation rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
            BlockPos pos = new BlockPos(x , getGroundFromAbove(worldIn, x,z), z );
            List<NewJungleVillagePieces.NewJungleVillageTemplate> houses = Lists.newLinkedList();
            NewJungleVillagePieces.generateVillage(worldIn.getSaveHandler().getStructureTemplateManager(), pos, rotation, rand, this.components, worldIn);

            this.components.addAll(houses);
            this.updateBoundingBox();
            this.valid = true;
        }
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);
        }
        public static int getGroundFromAbove(World world, int x, int z)
        {
            int y = 255;
            boolean foundGround = false;
            while(!foundGround && y-- >=3){ //max 31
                Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
                foundGround =  blockAt == Blocks.GRASS || blockAt == Blocks.SAND || blockAt == Blocks.SNOW || blockAt == Blocks.SNOW_LAYER || blockAt == Blocks.MYCELIUM;
            }

            return y + 1;
        }
        public boolean isSizeableStructure()
        {
            return this.valid;
        }

        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.valid);
        }

        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            this.valid = tagCompound.getBoolean("Valid");
        }
    }

}
