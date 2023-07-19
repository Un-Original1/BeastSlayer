package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JungleVillageWorldGen extends WorldGenerator {
    public static List<Biome> VALID_BIOMES = Arrays.asList(Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE);

    private int separation;
    private int spacing;

    public JungleVillageWorldGen(){
        this.spacing = 40;
        this.separation = 15;
        //add spawns to biome
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        this.spacing = 40;
        boolean canSpawn = canSpawnStructureAtCoords(world, position.getX() >> 4, position.getZ() >> 4);
        if (new Random().nextInt(9) == 0) {
            int new_size = 96;
             getStructureStart(world, position.getX() >> 4, position.getZ() >> 4, rand).generateStructure(world, rand, new StructureBoundingBox(position.getX() - new_size, position.getZ() - new_size, position.getX() + new_size, position.getZ() + new_size));
        }
        return canSpawn;
    }

    public String getStructureName() {
        return "Jungle Tribal Village";
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

            return world.getBiomeProvider().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, VALID_BIOMES);
        }

        return false;
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
            BlockPos pos = new BlockPos((x << 4) + 8 - (96/2), getGroundFromAbove(worldIn, (x << 4) + 8 - (96/2),(z << 4) + 8 - (96/2)), (z << 4) + 8 - (96/2));
            List<JungleVillagePieces.JungleVillageTemplate> houses = Lists.newLinkedList();
            JungleVillagePieces.generateVillage(worldIn.getSaveHandler().getStructureTemplateManager(), pos, rotation, rand, this.components);

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
            while(!foundGround && y-- >= 31)
            {
                Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
                foundGround =  blockAt == Blocks.GRASS || blockAt == Blocks.SAND || blockAt == Blocks.SNOW || blockAt == Blocks.SNOW_LAYER || blockAt == Blocks.MYCELIUM;
            }

            return y;
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
