package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
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
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TavernWorldGen extends WorldGenerator {
    private static final ResourceLocation LOOT = new ResourceLocation(BeastSlayer.MODID, "structures/tavern_up");
    private static final ResourceLocation LOOT2 = new ResourceLocation(BeastSlayer.MODID, "structures/tavern_down");
    public static List<Biome> VALID_BIOMES =  Lists.newArrayList(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));

    private int separation;
    private int spacing;

    public TavernWorldGen(){
        this.spacing = BeastSlayerConfig.TavernSpacing;
        this.separation = BeastSlayerConfig.TavernSeparation;
    }
    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        boolean canSpawn = canSpawnStructureAtCoords(world, (pos.getX() - 8)  >> 4, (pos.getZ() - 8)  >> 4);
        // if (new Random().nextInt(9) == 0) {
        if(canSpawn) {
            int new_size = 32;
            BeastSlayer.logger.debug("generating tavern!" + pos);
            Start start =  (Start) this.getStructureStart(world, pos.getX(), pos.getZ(), rand);

            start.generateStructure(world, rand, new StructureBoundingBox(pos.getX() - new_size, pos.getZ() - new_size, pos.getX() + new_size, pos.getZ() + new_size));



            //    }
        }
        return canSpawn;
    }

    public boolean generateSimple(IChunkGenerator generator, BlockPos pos, World world, Random rand){
        boolean canSpawn = canSpawnStructureAtCoords(world, (pos.getX() - 8)  >> 4, (pos.getZ() - 8)  >> 4);
        if(canSpawn){
            int new_size = 32;
            Start start = (Start) this.getStructureStart(world, pos.getX(), pos.getZ(), rand);

            start.generateStructure(world, rand, new StructureBoundingBox(pos.getX() - new_size, pos.getZ() - new_size, pos.getX() + new_size, pos.getZ() + new_size));
            BeastSlayer.logger.debug("generating tavern!" +  (((pos.getX() - 8)  >> 4)+ "chunkx") + (((pos.getZ() - 8)  >> 4) + "chunkz"));
        }
        return canSpawn;
    }

    public boolean generateBypass(World world, Random rand, BlockPos position){
        int new_size = 96;
        //  BeastSlayer.logger.debug("generating village!" + position);
        getStructureStart(world, position.getX(), position.getZ(), rand).generateStructure(world, rand, new StructureBoundingBox(position.getX() - new_size, position.getZ() - new_size, position.getX() + new_size, position.getZ() + new_size));
        return true;
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

        return new Start(world, rand , chunkX, chunkZ, 1);
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
            Random random = new Random((x + z * 10387313L));
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            BlockPos pos = new BlockPos(x , getGroundFromAbove(worldIn, x,z), z );
            // List<NewJungleVillagePieces.NewJungleVillageTemplate> houses = Lists.newLinkedList();
            generateTavern(worldIn.getSaveHandler().getStructureTemplateManager(), pos, rotation, rand, worldIn);

            this.updateBoundingBox();
            this.valid = true;
        }
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);
        }
        public NBTTagCompound writeDataToNBT(int chunkX, int chunkZ){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("id", "Tavern");
            nbttagcompound.setInteger("ChunkX", chunkX);
            nbttagcompound.setInteger("ChunkZ", chunkZ);
            nbttagcompound.setTag("BB", this.boundingBox.toNBTTagIntArray());
            nbttagcompound.setString("Location", "[" + chunkX + "," + chunkZ + "]");
            this.writeToNBT(nbttagcompound);
            return nbttagcompound;
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

        public boolean generateTavern(TemplateManager manager, BlockPos pos, Rotation rotation, Random rand, World world){
            WorldServer worldserver = (WorldServer) world;
            MinecraftServer minecraftserver = world.getMinecraftServer();
            Template template = manager.getTemplate(minecraftserver, new ResourceLocation("ancientbeasts:tavern"));
            IBlockState iblockstate = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);



            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
                    .setRotation(rotation).setIgnoreEntities(false).setReplacedBlock(Blocks.STRUCTURE_VOID).setIgnoreStructureBlock(false);

            template.addBlocksToWorld(world, pos, placementsettings, 2 | 16);
            template.getDataBlocks(pos, placementsettings);
            Map<BlockPos, String> map = template.getDataBlocks(pos, placementsettings);


            for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
                if ("chestup".equals(entry.getValue())) {
                    world.setBlockState(entry.getKey(), Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(entry.getKey().down());

                    if (tileentity instanceof TileEntityChest) {
                        if(rand.nextInt(4) == 0){
                            world.setBlockState(entry.getKey().down(), Blocks.AIR.getDefaultState(), 3);
                        } else {
                            ((TileEntityChest) tileentity).setLootTable(LOOT, rand.nextLong());
                        }
                    }
                }
                if ("chestdown".equals(entry.getValue())) {
                    world.setBlockState(entry.getKey(), Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(entry.getKey().down());

                    if (tileentity instanceof TileEntityChest) {
                        if(rand.nextInt(4) == 0){
                            world.setBlockState(entry.getKey().down(), Blocks.AIR.getDefaultState(), 3);
                        } else {
                            ((TileEntityChest) tileentity).setLootTable(LOOT2, rand.nextLong());
                        }
                    }
                }
            }
            return true;
        }
    }

}
