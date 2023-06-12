package com.unoriginal.beastslayer.worldGen;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator {
    public static final JungleVillageWorldGen jungle_t = new JungleVillageWorldGen();
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
            int blockX = chunkX * 16;
            int blockZ = chunkZ * 16;
            if(chunkProvider.isChunkGeneratedAt(chunkX, chunkZ)) {
                switch (world.provider.getDimension()) {
                    case -1:
                        generateNether(world, rand, blockX + 8, blockZ + 8);
                        break;
                    case 0:
                        generateOverworld(world, rand, blockX + 8, blockZ + 8);
                        break;
                    case 1:
                        generateEnd(world, rand, blockX + 8, blockZ + 8);
                        break;

                }
            }
    }
    private void generateOverworld(World world, Random rand, int blockX, int blockZ)
    {
        int y = getGroundFromAbove(world, blockX, blockZ);
        BlockPos pos = new BlockPos(blockX, y, blockZ);
       /* if (canStructureSpawn(blockX / 16, blockZ /16, world, 1)) {
            if (pos.getY() > 31) {
                jungle_t.generate(world, rand, pos);
            }
        }*/
        if (canStructureSpawn(blockX / 16, blockZ /16, world, BeastSlayerConfig.CircusSpawnChance))
        {
            WorldGenerator structure = new CircusWorldGen();
            structure.generate(world, rand, pos);

        }
    }

    private void generateNether(World world, Random rand, int chunkX, int chunkZ) {
        //I don't think I'll ever touch this
    }
    private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {}

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

    public static boolean canSpawnHere(Template template, World world, BlockPos posAboveGround)
    {
        int zwidth = template.getSize().getZ();
        int xwidth = template.getSize().getX();

        // check all the corners to see which ones are replaceable
        boolean corner1 = isCornerValid(world, posAboveGround);
        boolean corner2 = isCornerValid(world, posAboveGround.add(xwidth, 0, zwidth));

        // if Y > 31 and all corners pass the test, it's okay to spawn the structure
        return posAboveGround.getY() > 31 && corner1 && corner2;
    }
    public static boolean canStructureSpawn(int chunkX, int chunkZ, World world, int frequency){
        if (frequency <= 0) return false;
        int maxDistanceBetween = frequency + 8;

        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= maxDistanceBetween - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= maxDistanceBetween - 1;
        }

        int k = chunkX / maxDistanceBetween;
        int l = chunkZ / maxDistanceBetween;
        Random random = world.setRandomSeed(k, l, 14357617);
        k = k * maxDistanceBetween;
        l = l * maxDistanceBetween;
        k = k + random.nextInt(maxDistanceBetween - 8);
        l = l + random.nextInt(maxDistanceBetween - 8);

        return i == k && j == l;
    }

    public static boolean isCornerValid(World world, BlockPos pos)
    {
        int variation = 3;
        int highestBlock = getGroundFromAbove(world, pos.getX(), pos.getZ());

        return highestBlock > pos.getY() - variation && highestBlock < pos.getY() + variation;
    }
}
