package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JungleVillagePieces {
    private static final ResourceLocation LOOT_HOUSE = new ResourceLocation(BeastSlayer.MODID, "structures/j_house");
    private static final ResourceLocation LOOT_WIZARD = new ResourceLocation(BeastSlayer.MODID, "structures/j_wizard");
    private static final ResourceLocation LOOT_FARM = new ResourceLocation(BeastSlayer.MODID, "structures/j_farm");

         /*for classification:
    house 1= slab 19, log 24
    house 2 = slab x3 19
    house 3= slab 11
    house 4 = slab 8
    house 5: log 11, 14
    house 6: log, 11,
    house 7: log 11
    house 8: log 13
    house 9: log 16, slab 22
    house 10: slab 11, log 15
    house 11: log 12
    house 12: log 13, slab 13
    */

            //all of above -3!
    //TODO:  fix overlapping, smoothing terrain?
    public static void generateVillage(TemplateManager manager, BlockPos pos, Rotation rot, Random rand, List<StructureComponent> components){
        BRIDGE_LOG_END_GENERATOR.init();
        SLAB_END_GENERATOR.init();
        HOUSE_GENERATOR.init();
        BRIDGE_GENERATOR.init();
        SLAB_GENERATOR.init();
        int i = rand.nextInt(12) + 1;
        String s = "house_" + i;
        JungleVillageTemplate villageTemplate = JungleVillagePieces.addHelper(components, new JungleVillageTemplate(manager, s, pos, rot));
        if(JungleVillagePieces.getBridgesbyInt(i) != null && !JungleVillagePieces.getBridgesbyInt(i).isEmpty()) {
            for (Tuple<Rotation, BlockPos> tuple : JungleVillagePieces.getBridgesbyInt(i)) {

                JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(components, JungleVillagePieces.addPiece(manager, villageTemplate, tuple.getSecond(), "log_end", rot.add(tuple.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.BRIDGE_GENERATOR, 0, structureendcitypieces$citytemplate1, null, components, rand);

            }
        }
        if(JungleVillagePieces.getSlabsbyInt(i) != null && !JungleVillagePieces.getSlabsbyInt(i).isEmpty()) {
            for (Tuple<Rotation, BlockPos> tuple : JungleVillagePieces.getSlabsbyInt(i)) {

                JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(components, JungleVillagePieces.addPiece(manager, villageTemplate, tuple.getSecond(), "cross_slab", rot.add(tuple.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.SLAB_GENERATOR, 0, structureendcitypieces$citytemplate1, null, components, rand);

            }
        }
    }

    public static class JungleVillageTemplate extends StructureComponentTemplate {

        private String templateName;
        private Rotation rotation;
        private int id;

        public JungleVillageTemplate() {
        }

        public JungleVillageTemplate(TemplateManager p_i47356_1_, String name, BlockPos pos, Rotation rotation)
        {
            super(0);
            this.templateName = name;
            this.templatePosition = pos;
            this.rotation = rotation;
            id = id++;
            this.loadTemplate(p_i47356_1_);
        }

        public BlockPos getTemplatePosition() {
            return this.templatePosition;
        }

        public Template getTemplate(){
            return this.template;
        }

        public PlacementSettings getPlacementSettings(){
            return this.placeSettings;
        }

        public Rotation getRotation() {
            return rotation;
        }

        public StructureBoundingBox getBoundingBox(){
            return this.boundingBox;
        }


        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
            super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            int extrax = 0;
            int extraz = 0;
            switch (this.rotation){
                case NONE:
                    extraz = 1;
                    break;
                case CLOCKWISE_90:
                    extraz = 1;
                    extrax = 1;
                    break;
                case CLOCKWISE_180:
                    extrax = 1; //this is somehow right?
                    break;
                case COUNTERCLOCKWISE_90:
                    //this one also works
                    break;
            }

            if(this.templateName.contains("house")) {

                for (int i = 0; i < this.template.getSize().getX(); i++) {
                    for (int j = 0; j < this.template.getSize().getZ(); j++) {
                        for (int k = 0; k < 30; k++) {

                            boolean b = this.getRotation() == Rotation.CLOCKWISE_90 || this.getRotation() == Rotation.COUNTERCLOCKWISE_90;
                            BlockPos pos = new BlockPos((b ? j : i) + extrax, -1-k, (b ? i : j) + extraz);
                            if(worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER) {
                                this.replaceAirAndLiquidDownwards(worldIn, Blocks.GRASS.getDefaultState(),(b ? j : i) + extrax, -1 - k, (b ? i : j) + extraz, structureBoundingBoxIn);
                            }
                        }
                    }
                }
            }
            else if(this.templateName.contains("cross_pillar")) {

                for (int i = 0; i < this.template.getSize().getX(); i++) {
                    for (int j = 0; j < this.template.getSize().getZ(); j++) {
                        for (int k = 0; k < 30; k++) {

                            boolean b = this.getRotation() == Rotation.CLOCKWISE_90 || this.getRotation() == Rotation.COUNTERCLOCKWISE_90;
                            BlockPos pos = new BlockPos((b ? j : i) + extrax, -1-k, (b ? i : j) + extraz);
                            if(worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER) {
                                this.replaceAirAndLiquidDownwards(worldIn, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE),(b ? j : i) + extrax, -1 - k, (b ? i : j) + extraz, structureBoundingBoxIn);
                            }
                        }
                    }
                }
            }
            else if(this.templateName.contains("bridge")) {

                for (int i = 0; i < this.template.getSize().getX(); i++) {
                    for (int j = 0; j < this.template.getSize().getZ(); j++) {
                        for (int k = 0; k < 4; k++) {
                            boolean b = this.getRotation() == Rotation.CLOCKWISE_90 || this.getRotation() == Rotation.COUNTERCLOCKWISE_90;
                            this.clearCurrentPositionBlocksUpwards(worldIn,(b ? j : i) + extrax, this.template.getSize().getY() + k, (b ? i : j) + extraz, structureBoundingBoxIn );
                        }
                    }
                }
            }
            else if(this.templateName.contains("cross") && this.templateName.contains("log")){
                for (int k = 0; k < 30; k++) {

                    BlockPos pos = new BlockPos((1) + extrax, -1-k, (1) + extraz);
                    if(worldIn.isAirBlock(pos) ||  worldIn.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER) {
                        this.replaceAirAndLiquidDownwards(worldIn, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE),1 + extrax, -1 - k, 1 + extraz, structureBoundingBoxIn);
                    }
                }
            }
            return true;
        }

        private void loadTemplate(TemplateManager manager)
        {
            Template template = manager.getTemplate(null, new ResourceLocation("ancientbeasts:" + this.templateName));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).copy().setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementsettings);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Template", this.templateName);
            tagCompound.setString("Rot", this.placeSettings.getRotation().name());
            tagCompound.setString("Mi", this.placeSettings.getMirror().name());
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.templateName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.loadTemplate(p_143011_2_);
        }
        public int getId(){
            return this.id;
        }

        public void setComponentType(int set){
            this.componentType = set;
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox sbb) {

            switch (function) {
                case "chest": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());

                    if (tileentity instanceof TileEntityChest) {
                          ((TileEntityChest)tileentity).setLootTable(LOOT_HOUSE, rand.nextLong());
                    }
                    break;
                }
                case "chest_w": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());

                    if (tileentity instanceof TileEntityChest) {
                          ((TileEntityChest)tileentity).setLootTable(LOOT_WIZARD, rand.nextLong());
                    }
                    break;
                }
                case "chest_f": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());

                    if (tileentity instanceof TileEntityChest) {
                          ((TileEntityChest)tileentity).setLootTable(LOOT_FARM, rand.nextLong());
                    }
                    break;
                }
                case "tribesmen":
                    if (rand.nextInt(10) < 1) {
                        EntityTank tank = new EntityTank(world);
                        tank.enablePersistence();
                        tank.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        tank.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(tank);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    } else if (rand.nextInt(10) < 4) {
                        EntityHunter hunter = new EntityHunter(world);
                        hunter.enablePersistence();
                        hunter.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        hunter.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(hunter);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    } else {
                        EntityTribeWarrior warrior = new EntityTribeWarrior(world);
                        warrior.enablePersistence();
                        warrior.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        warrior.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(warrior);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    }
                case "magicman":
                    EntityPriest magicman = new EntityPriest(world);
                    magicman.enablePersistence();
                    magicman.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                    magicman.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                    world.spawnEntity(magicman);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    break;
            }
        }

        public boolean isCollidingExcParent(TemplateManager manager, JungleVillageTemplate parent, List<StructureComponent> structures) {
            List<StructureComponent> collisions = findAllIntersecting(structures);

            boolean foundCollision = false;

            for (StructureComponent collision : collisions) {
                if (((JungleVillageTemplate) collision).getId() != parent.getId()) {
                    foundCollision = true;
                    break;
                }
            }

            return foundCollision;
        }

        public List<StructureComponent> findAllIntersecting(List<StructureComponent> listIn) {
            List<StructureComponent> list = new ArrayList<>();
            for (StructureComponent structurecomponent : listIn) {
                StructureBoundingBox intersection = new StructureBoundingBox(this.boundingBox.minX + 1, this.boundingBox.minY + 1, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY - 1, this.boundingBox.maxZ - 1);
                if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(intersection)) {
                    list.add(structurecomponent);
                }
            }

            return list;
        }
    }

    private static JungleVillageTemplate addPiece(TemplateManager manager, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, String templateName, Rotation rotation)
    {
        JungleVillageTemplate structureendcitypieces$citytemplate = new JungleVillageTemplate(manager, templateName, jungleVillageTemplate.getTemplatePosition(), rotation);
        BlockPos blockpos = jungleVillageTemplate.getTemplate().calculateConnectedPos(jungleVillageTemplate.getPlacementSettings(), pos, structureendcitypieces$citytemplate.getPlacementSettings(), BlockPos.ORIGIN);
        structureendcitypieces$citytemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return structureendcitypieces$citytemplate;
    }

    private static JungleVillagePieces.JungleVillageTemplate addHelper(List<StructureComponent> structureComponents, JungleVillageTemplate template)
    {
        structureComponents.add(template);
        return template;
    }

    private static boolean recursiveChildren(TemplateManager manager, IGenerator generator, int generation, JungleVillageTemplate cityTemplate, BlockPos pos, List<StructureComponent> components, Random random)
    {
        if (generation <= 2) {
            List<StructureComponent> list = Lists.newArrayList();

            if (generator.generate(manager, generation, cityTemplate, pos, list, random)) {
                boolean flag = false;
                int i = random.nextInt();
                for (StructureComponent structurecomponent : list) {
                    structurecomponent.componentType = i;
                    StructureComponent structurecomponent1 = StructureComponent.findIntersecting(components, structurecomponent.getBoundingBox());

                    if (structurecomponent1 != null && structurecomponent1.getComponentType() != cityTemplate.getComponentType()) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    components.addAll(list);
                    return true;
                }
            }

        }
        return false;
    }


    interface IGenerator
    {
        void init();

        boolean generate(TemplateManager p_191086_1_, int p_191086_2_, JungleVillageTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_);
    }

    //logs
/*** hey! unoriginal here, the tuples are wrong... I rotated them all -90° to actually make this work **/

//cc = x+1, 180 = z-1, 0 = z+1, c = x-1
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(13, 20, 7)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_5_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(8, 10, 0)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 7,5)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_6_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 6, 7)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_7_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(4, 7, 12)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_8_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(5, 9, 12)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_9_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 12, 5)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_10_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(6, 11, 0)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_11_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(12, 17, 6)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 8, 4)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_12_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(6, 9, 0)));

    //slab
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_SLABS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(10, 15, 8)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(6,7,2)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_2_SLABS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(5, 15, 14)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(6,15,-1)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(-1, 15, 6)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_3_SLABS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6,7,16)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_4_SLABS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(5, 4, 12)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_9_SLABS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(10, 18, 14)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_10_SLABS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(13, 7, 6)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_12_SLABS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(5, 9, 12)));


    private static List<Tuple<Rotation, BlockPos>> getBridgesbyInt(int houseid){
        List<Tuple<Rotation, BlockPos>> finallist = null;
        switch (houseid){

            case 1:
                finallist = HOUSE_1_BRIDGES;
                break;
            case 0:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                finallist = HOUSE_5_BRIDGES;
                break;
            case 6:
                finallist = HOUSE_6_BRIDGES;
                break;
            case 7:
                finallist = HOUSE_7_BRIDGES;
                break;
            case 8:
                finallist = HOUSE_8_BRIDGES;
                break;
            case 9:
                finallist = HOUSE_9_BRIDGES;
                break;
            case 10:
                finallist = HOUSE_10_BRIDGES;
                break;
            case 11:
                finallist = HOUSE_11_BRIDGES;
                break;
            case 12:
                finallist = HOUSE_12_BRIDGES;
                break;
        }
        if (finallist != null && finallist.size() > 1) {
            Collections.shuffle(finallist);
        }
        return finallist;
    }

    private static List<Tuple<Rotation, BlockPos>> getSlabsbyInt(int houseid){
        List<Tuple<Rotation, BlockPos>> finallist = null;
        switch (houseid){

            case 1:
                finallist = HOUSE_1_SLABS;
                break;
            case 0:
            case 5:
            case 6:
            case 7:
            case 11:
            case 8:
                break;
            case 2:
                finallist = HOUSE_2_SLABS;
                break;
            case 3:
                finallist = HOUSE_3_SLABS;
                break;
            case 4:
                finallist = HOUSE_4_SLABS;
                break;
            case 9:
                finallist = HOUSE_9_SLABS;
                break;
            case 10:
                finallist = HOUSE_10_SLABS;
                break;
            case 12:
                finallist = HOUSE_12_SLABS;
                break;
        }
        if (finallist != null && finallist.size() > 1) {
            Collections.shuffle(finallist);
        }
        return finallist;
    }
    private static final JungleVillagePieces.IGenerator HOUSE_GENERATOR = new JungleVillagePieces.IGenerator() {
        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager manager, int generation, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = jungleVillageTemplate.getPlacementSettings().getRotation();
            int i = random.nextInt(13);
            String s = "house_" + i;
            JungleVillageTemplate villageTemplate = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, jungleVillageTemplate, pos, s, rotation));
            if(JungleVillagePieces.getBridgesbyInt(i) != null && !JungleVillagePieces.getBridgesbyInt(i).isEmpty()) {
                for (Tuple<Rotation, BlockPos> tuple : JungleVillagePieces.getBridgesbyInt(i)) {

                          JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, villageTemplate, tuple.getSecond(), "log_end", rotation.add(tuple.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                          JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.BRIDGE_GENERATOR, generation + 1, structureendcitypieces$citytemplate1, null, structureComponents, random);
                }
            }
            if(JungleVillagePieces.getSlabsbyInt(i) != null && !JungleVillagePieces.getSlabsbyInt(i).isEmpty()) {
                for (Tuple<Rotation, BlockPos> tuple : JungleVillagePieces.getSlabsbyInt(i)) {

                    JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, villageTemplate, tuple.getSecond(), "cross_slab", rotation.add(tuple.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                    JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.SLAB_GENERATOR, generation + 1, structureendcitypieces$citytemplate1, null, structureComponents, random);
                }
            }

            return true;
        }
    };


    //tuple time!; cross crossx3 needed

    private static final  List<Tuple<Rotation, BlockPos>> CROSS_CONNECTIONS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0, 0, 3)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(-1, 0, 0)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(3, 0, 2)));
    private static final  List<Tuple<Rotation, BlockPos>> CROSS_2_CONNECTIONS = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(-1, 0, 0)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(3, 0, 2)));
    private static final  List<Tuple<Rotation, BlockPos>> CROSS_290_CONNECTIONS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0, 0, 3)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(3, 0, 2)));
    private static final  List<Tuple<Rotation, BlockPos>> CROSS_2minus90_CONNECTIONS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0, 0, 3)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(-1, 0, 0)));



    private static final JungleVillagePieces.IGenerator BRIDGE_GENERATOR = new JungleVillagePieces.IGenerator(){

        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = jungleVillageTemplate.getPlacementSettings().getRotation();
            int randLength = random.nextInt(3);
            String s =  randLength == 0 ? "bridge_log" : "bridge_log_s";
            JungleVillageTemplate bridge_piece =  JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, jungleVillageTemplate, new BlockPos(0, 0, 0), s, rotation)); //first log piece is added
           // bridge_piece.setComponentType(-1);
            int selector = random.nextInt(4);
            boolean b = randLength == 0;
            bridge_piece = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece,new BlockPos(0,0, b ? 12 : 8), this.getBridgeCorner(selector), rotation)); //the z offset here is rotated for all instances so no need to create several offsets
            //bridge_piece.setComponentType(-1);

            if(getConnectorById(selector) != null && !getConnectorById(selector).isEmpty()) {
                for (Tuple<Rotation, BlockPos> tuple : getConnectorById(selector)) {

                    JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece, tuple.getSecond(), "log_end", rotation.add(tuple.getFirst())));
                    JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.BRIDGE_LOG_END_GENERATOR, generation, structureendcitypieces$citytemplate1, null, structureComponents, random);

                }
            }
            return generation < 3;
        }

        public String getBridgeCorner(int i){
            String s = null;
            switch (i){
                case 0:
                    s="cross_log";//4 sides
                    break;
                case 1:
                    s = "cross_3_log";
                    break;
                case 2:
                    s = "cross_3_log_-90";
                    break;
                case 3:
                    s = "cross_3_log_90";
                    break;

            }
            return s;
        }

        public List<Tuple<Rotation, BlockPos>> getConnectorById(int i){
            List<Tuple<Rotation, BlockPos>> finallist = null;
            switch (i){
                case 0:
                    finallist = CROSS_CONNECTIONS;//3 sides
                    break;
                case 1:
                    finallist = CROSS_2_CONNECTIONS;
                    break;
                case 2:
                   finallist = CROSS_2minus90_CONNECTIONS;
                    break;
                case 3:
                    finallist = CROSS_290_CONNECTIONS;
                    break;

            }
            if (finallist != null && finallist.size() > 1) {
                Collections.shuffle(finallist);
            }
            return finallist;
        }
    };

    private static final List<Tuple<Rotation, BlockPos>> SLAB_CONNECTIONS = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0, 0, 2)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(-1, 0 ,0)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(2, 0,1)));
    private static final JungleVillagePieces.IGenerator SLAB_GENERATOR = new JungleVillagePieces.IGenerator(){

        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = jungleVillageTemplate.getPlacementSettings().getRotation();
            int randLenght = random.nextInt(3);
            int i = 0;
            String s =  randLenght == 0 ? "bridge_slab_top" : "bridge_slab_top_s";
            JungleVillageTemplate bridge_piece =  JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, jungleVillageTemplate, new BlockPos(0, 0, 0), s, rotation));
            bridge_piece.setComponentType(-1);
            bridge_piece = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece ,new BlockPos(0,0,randLenght == 0 ? 12:8), "cross_pillar", rotation));
            for (Tuple<Rotation, BlockPos> tuple : SLAB_CONNECTIONS){
                if(random.nextBoolean() || i == 0){
                    JungleVillageTemplate crossBridge = JungleVillagePieces.addHelper(structureComponents,JungleVillagePieces.addPiece(manager, bridge_piece, tuple.getSecond(), "cross_slab", rotation.add(tuple.getFirst())));
                    JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.SLAB_END_GENERATOR, generation, crossBridge, null, structureComponents, random);
                    i++;
                }
            }
            bridge_piece.setComponentType(-1);
            return generation < 3 && i > 0;
        }
    };



    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(-5,-20,21) )); //bridgec = bridge connection
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_5_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.NONE, new BlockPos(-6,-10,8) ), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(7,-7,8)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_6_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(9,-6,8) ));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_7_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(6,-7,20) ));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_8_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(7,-9,20) ));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_9_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(7,-12,8) ));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_10_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.NONE, new BlockPos(-4,-11,8) ));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_11_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(-4,-17,20) ), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6,-8,8)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_12_BRIDGEC = Lists.newArrayList( new Tuple<>(Rotation.NONE, new BlockPos(-4,-9,7) ));



    private static final JungleVillagePieces.IGenerator BRIDGE_LOG_END_GENERATOR = new JungleVillagePieces.IGenerator(){

        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, List<StructureComponent> structureComponents, Random random) {

            Rotation rotation = jungleVillageTemplate.getPlacementSettings().getRotation();
            int randLength = random.nextInt(3);

            String s = randLength == 0 ? "bridge_log" : "bridge_log_s";
            JungleVillageTemplate bridge_piece =  JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, jungleVillageTemplate, new BlockPos(0, 0, 0), s, rotation));
            bridge_piece.setComponentType(-1);

            int selector = random.nextInt(9);
            //int real_house_id =
            BlockPos addposition = new BlockPos(0,0, randLength == 0 ? 4 : 0);

            String house = getHouseS(selector);

            if(getBridgecByInt(selector) != null && !getBridgecByInt(selector).isEmpty()) {
                for (Tuple<Rotation, BlockPos> tuple : getBridgecByInt(selector)) {
                   // BeastSlayer.logger.debug(house, tuple.getSecond(), pos);

                    JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece, tuple.getSecond(), house, rotation.add(tuple.getFirst())));
                    if(generation < 3) {
                        if (JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house)) != null && !JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house)).isEmpty()) {
                            for (Tuple<Rotation, BlockPos> tuple1 : JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house))) {

                                JungleVillageTemplate structureendcitypieces$citytemplate2 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, structureendcitypieces$citytemplate1, tuple1.getSecond()/*.add(new BlockPos(0,0,-1))*/, "log_end", rotation.add(tuple.getFirst()).add(tuple1.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.BRIDGE_GENERATOR, generation + 1, structureendcitypieces$citytemplate2, null, structureComponents, random);
                            }
                        }

                        if (JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house)) != null && !JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house)).isEmpty()) {
                            for (Tuple<Rotation, BlockPos> tuple2 : JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house))) {

                                JungleVillageTemplate structureendcitypieces$citytemplate2 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, structureendcitypieces$citytemplate1, tuple2.getSecond()/*.add(new BlockPos(0,0,-1))*/, "cross_slab", rotation.add(tuple.getFirst()).add(tuple2.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.SLAB_GENERATOR, generation + 1, structureendcitypieces$citytemplate2, null, structureComponents, random);
                            }
                        }
                    }
                    break;
                }
            }
            if(generation < 3) {
                JungleVillageTemplate end = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece, new BlockPos(0, 0, 7).add(addposition), "log_end", rotation));
                //  end.setComponentType(-1);
            }
            return true;
        }

        private List<Tuple<Rotation, BlockPos>> getBridgecByInt(int random){
            List<Tuple<Rotation, BlockPos>> finallist = null;
            switch (random){

                case 0:
                    finallist = HOUSE_1_BRIDGEC;
                    break;
                case 1:
                    finallist = HOUSE_5_BRIDGEC;
                    break;
                case 2:
                    finallist = HOUSE_6_BRIDGEC;
                    break;
                case 3:
                    finallist = HOUSE_7_BRIDGEC;
                    break;
                case 4:
                    finallist = HOUSE_8_BRIDGEC;
                    break;
                case 5:
                    finallist = HOUSE_9_BRIDGEC;
                    break;
                case 6:
                    finallist = HOUSE_10_BRIDGEC;
                    break;
                case 7:
                    finallist = HOUSE_11_BRIDGEC;
                    break;
                case 8:
                    finallist = HOUSE_12_BRIDGEC;
                    break;
            }
            if (finallist != null && finallist.size() > 1) {
                Collections.shuffle(finallist);
            }
            return finallist;
        }
        private String getHouseS(int i){
            String s = null;
            switch (i){
                case 0:
                    s = "house_1";
                    break;
                case 1:
                    s = "house_5";
                    break;
                case 2:
                    s = "house_6";
                    break;
                case 3:
                    s = "house_7";
                    break;
                case 4:
                    s = "house_8";
                    break;
                case 5:
                    s = "house_9";
                    break;
                case 6:
                    s = "house_10";
                    break;
                case 7:
                    s = "house_11";
                    break;
                case 8:
                    s = "house_12";
                    break;
            }
            return s;
        }

        public int houseStringtoInt(String s){
            return Integer.parseInt(s.replaceAll("[\\D]", ""));
        }

    };

    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_SLABC = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(-4, -7, 8)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(-7,-15,16)));//none lowest
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_2_SLABC = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(-4, -15, 8)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(8,-15,8)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(6,-15,21)));//
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_3_SLABC = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(7,-7,23))); //reduced by 1 to hopefully fix wrong offset
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_4_SLABC = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(5,-4,19)));//
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_9_SLABC = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(11,-18,21)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_10_SLABC = Lists.newArrayList(new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(-4,-7,20)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_12_SLABC = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(6,-9,19)));//


    private static final JungleVillagePieces.IGenerator SLAB_END_GENERATOR = new IGenerator() {
        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, JungleVillageTemplate jungleVillageTemplate, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = jungleVillageTemplate.getPlacementSettings().getRotation();
            int randLength = random.nextInt(3);

            String s = randLength == 0 ? "bridge_slab_top" : "bridge_slab_top_s";
            JungleVillageTemplate bridge_piece =  JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, jungleVillageTemplate, new BlockPos(0, 0, 0), s, rotation));
            bridge_piece.setComponentType(-1);

            int selector = random.nextInt(7);
            //int real_house_id =
            BlockPos addposition = new BlockPos(0,0, randLength == 0 ? 4 : 0);

            String house = getHouseS(selector);

            if(getSlabcByInt(selector) != null && !getSlabcByInt(selector).isEmpty()) {
                for (Tuple<Rotation, BlockPos> tuple : getSlabcByInt(selector)) {
                    BeastSlayer.logger.debug(house, tuple.getSecond(), pos);

                    JungleVillageTemplate structureendcitypieces$citytemplate1 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece, tuple.getSecond(), house, rotation.add(tuple.getFirst())));
                    if(generation < 3) {
                        if (JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house)) != null && !JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house)).isEmpty()) {
                            for (Tuple<Rotation, BlockPos> tuple1 : JungleVillagePieces.getBridgesbyInt(houseStringtoInt(house))) {

                                JungleVillageTemplate structureendcitypieces$citytemplate2 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, structureendcitypieces$citytemplate1, tuple1.getSecond()/*.add(new BlockPos(0,0,-1))*/, "log_end", rotation.add(tuple.getFirst()).add(tuple1.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.BRIDGE_GENERATOR, generation + 1, structureendcitypieces$citytemplate2, null, structureComponents, random);
                            }
                        }

                        if (JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house)) != null && !JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house)).isEmpty()) {
                            for (Tuple<Rotation, BlockPos> tuple2 : JungleVillagePieces.getSlabsbyInt(houseStringtoInt(house))) {

                                JungleVillageTemplate structureendcitypieces$citytemplate2 = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, structureendcitypieces$citytemplate1, tuple2.getSecond()/*.add(new BlockPos(0,0,-1))*/, "cross_slab", rotation.add(tuple.getFirst()).add(tuple2.getFirst()).add(Rotation.COUNTERCLOCKWISE_90)));
                                JungleVillagePieces.recursiveChildren(manager, JungleVillagePieces.SLAB_GENERATOR, generation + 1, structureendcitypieces$citytemplate2, null, structureComponents, random);
                            }
                        }
                    }
                    break;
                }
            }
            if(generation < 3) {
                JungleVillageTemplate end = JungleVillagePieces.addHelper(structureComponents, JungleVillagePieces.addPiece(manager, bridge_piece, new BlockPos(0, 0, 7).add(addposition), "cross_slab", rotation));
                //  end.setComponentType(-1);
            }
            return true;
        }

        private List<Tuple<Rotation, BlockPos>> getSlabcByInt(int random){
            List<Tuple<Rotation, BlockPos>> finallist = null;
            switch (random){

                case 0:
                    finallist = HOUSE_1_SLABC;
                    break;
                case 1:
                    finallist = HOUSE_2_SLABC;
                    break;
                case 2:
                    finallist = HOUSE_3_SLABC;
                    break;
                case 3:
                    finallist = HOUSE_4_SLABC;
                    break;
                case 4:
                    finallist = HOUSE_9_SLABC;
                    break;
                case 5:
                    finallist = HOUSE_10_SLABC;
                    break;
                case 6:
                    finallist = HOUSE_12_SLABC;
                    break;
            }
            if (finallist != null && finallist.size() > 1) {
                Collections.shuffle(finallist);
            }
            return finallist;
        }
        private String getHouseS(int i){
            String s = null;
            switch (i){
                case 0:
                    s = "house_1";
                    break;
                case 1:
                    s = "house_2";
                    break;
                case 2:
                    s = "house_3";
                    break;
                case 3:
                    s = "house_4";
                    break;
                case 4:
                    s = "house_9";
                    break;
                case 5:
                    s = "house_10";
                    break;
                case 6:
                    s = "house_12";
                    break;
            }
            return s;
        }

        public int houseStringtoInt(String s){
            return Integer.parseInt(s.replaceAll("[\\D]", ""));
        }
    };
}

