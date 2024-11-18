package com.unoriginal.beastslayer.worldGen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import net.minecraft.block.Block;
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
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NewJungleVillagePieces {
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
    public static World worldIn;
    private static final ResourceLocation LOOT_HOUSE = new ResourceLocation(BeastSlayer.MODID, "structures/j_house");
    private static final ResourceLocation LOOT_WIZARD = new ResourceLocation(BeastSlayer.MODID, "structures/j_wizard");
    private static final ResourceLocation LOOT_FARM = new ResourceLocation(BeastSlayer.MODID, "structures/j_farm");

    public static void generateVillage(TemplateManager manager, BlockPos pos, Rotation rot, Random rand, List<StructureComponent> components, World world){
        worldIn = world;
        BRIDGE_GENERATOR.init();
        HOUSE_GENERATOR.init();
        BRIDGETOHOUSE_GENERATOR.init();
        STAIRS_GENERATOR.init();
        FARMLAND_GENERATOR.init();
        TREEHOUSE_GENERATOR.init();

        //TODO GENERATORS
        /* bridge to house, bridge to ladderhouse, bridge to farmland,
          ladderhouse to bridge, ladderhouse to treetop
          treetop to ladderhouse, treetop to farmland, treetop to watchtower
          stairs to watchtower, watchtower to stairs, watchtawer to treetop,
          farmland to bridge, farmland to treetop
          house to stairs
          stairs to watchtower */
        NewJungleVillageTemplate prevTemplate = NewJungleVillagePieces.addHelper(components,  new NewJungleVillageTemplate(manager, "stairs", pos, rot));
        for (int i =0;i < 3; i++){
            if(i <=1) {
                String s = rand.nextInt(2) == 0 ? "stairs" : "stairs_lit";
                prevTemplate = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPiece(manager, prevTemplate, new BlockPos(0, 6, 0),s , rot));

            }
            else {
                NewJungleVillagePieces.recursiveChildren2(manager, HOUSE_GENERATOR, Rotation.NONE, 0, prevTemplate, new BlockPos(0, 6, 0), components, rand);
            }
        }
    }
    public static World getWorldIn(){
        return worldIn;
    }

    public static class NewJungleVillageTemplate extends StructureComponentTemplate {

        private String templateName;
        private Rotation rotation;
        private int id;
        private boolean overwrite;

        public NewJungleVillageTemplate() {
        }

        public NewJungleVillageTemplate(TemplateManager p_i47356_1_, String name, BlockPos pos, Rotation rotation)
        {
            super(0);
            this.templateName = name;
            this.templatePosition = pos;
            this.rotation = rotation;
            id = id++;
            this.overwrite = true;
            this.loadTemplate(p_i47356_1_);
        }

        public NewJungleVillageTemplate(TemplateManager manager, String name, BlockPos pos, Rotation rotation, boolean overwrite){
            super(0);
            this.templateName = name;
            this.templatePosition = pos;
            this.rotation = rotation;
            id = id++;
            this.overwrite = overwrite;
            this.loadTemplate(manager);
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

        private void loadTemplate(TemplateManager manager)
        {
            Template template = manager.getTemplate(null, new ResourceLocation("ancientbeasts:" + this.templateName));
            PlacementSettings placementsettings = (this.overwrite ? OVERWRITE : INSERT).copy().setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementsettings);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Template", this.templateName);
            tagCompound.setString("Rot", this.placeSettings.getRotation().name());
            tagCompound.setString("Mi", this.placeSettings.getMirror().name());
            tagCompound.setBoolean("OW", this.overwrite);

        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.templateName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.overwrite = tagCompound.getBoolean("OW");
            this.loadTemplate(p_143011_2_);
        }
        public int getId(){
            return this.id;
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox sbb) {

            switch (function) {
                case "chest_tribesmen": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());
                    if(rand.nextInt(8) == 0){
                        world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 3);
                        break;
                    } else {
                        if (tileentity instanceof TileEntityChest) {
                            if(rand.nextInt(40) != 0) {
                                ((TileEntityChest) tileentity).setLootTable(LOOT_HOUSE, rand.nextLong());
                            } else {
                                ((TileEntityChest) tileentity).setLootTable(LOOT_WIZARD, rand.nextLong());
                            }
                        }
                    }

                    break;
                }
                case "chest_treasure": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());

                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest)tileentity).setLootTable(LOOT_WIZARD, rand.nextLong());
                    }
                    break;
                }
                case "chest_farm": {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    TileEntity tileentity = world.getTileEntity(pos.down());
                    if(rand.nextInt(4) == 0){
                        world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 3);
                        break;
                    } else {
                        if (tileentity instanceof TileEntityChest) {
                            ((TileEntityChest)tileentity).setLootTable(LOOT_FARM, rand.nextLong());
                        }
                    }
                    break;
                }
                case "tribesmen":
                    if (rand.nextInt(10) < 1) {
                        EntityTank tank = new EntityTank(world);
                        tank.enablePersistence();
                        tank.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        tank.setHomePosAndDistance(pos, 10);
                        tank.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(tank);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    } else if (rand.nextInt(10) < 4) {
                        EntityHunter hunter = new EntityHunter(world);
                        hunter.enablePersistence();
                        hunter.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        hunter.setHomePosAndDistance(pos, 10);
                        hunter.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(hunter);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    } else {
                        EntityTribeWarrior warrior = new EntityTribeWarrior(world);
                        warrior.enablePersistence();
                        warrior.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                        warrior.setHomePosAndDistance(pos, 10);
                        warrior.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                        world.spawnEntity(warrior);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        break;
                    }
                case "priest":
                    EntityPriest magicman = new EntityPriest(world);
                    magicman.enablePersistence();
                    magicman.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                    magicman.setHomePosAndDistance(pos, 10);
                    magicman.onInitialSpawn(world.getDifficultyForLocation(pos),null);
                    world.spawnEntity(magicman);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    break;
            }
        }

        public boolean isCollidingExcParent(TemplateManager manager, NewJungleVillageTemplate parent, List<StructureComponent> structures) {
            List<StructureComponent> collisions = findAllIntersecting(structures);

            boolean foundCollision = false;

            for (StructureComponent collision : collisions) {
                if (((NewJungleVillageTemplate) collision).getId() != parent.getId()) {
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
                if (structurecomponent.getBoundingBox().intersectsWith(intersection)) {
                    list.add(structurecomponent);
                }
            }

            return list;
        }
    }

    private static NewJungleVillageTemplate addPiece(TemplateManager manager, NewJungleVillageTemplate parent, BlockPos pos, String templateName, Rotation rotation)
    {
        NewJungleVillageTemplate newerTemplate = new NewJungleVillageTemplate(manager, templateName, parent.getTemplatePosition(), rotation);
        BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newerTemplate.getPlacementSettings(), BlockPos.ORIGIN);
        newerTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return newerTemplate;
    }
    private static NewJungleVillageTemplate addPieceInsert(TemplateManager manager, NewJungleVillageTemplate parent, BlockPos pos, String templateName, Rotation rotation)
    {
        NewJungleVillageTemplate newerTemplate = new NewJungleVillageTemplate(manager, templateName, parent.getTemplatePosition(), rotation, false);
        BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newerTemplate.getPlacementSettings(), BlockPos.ORIGIN);
        newerTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return newerTemplate;
    }

    private static NewJungleVillageTemplate addHelper(List<StructureComponent> structureComponents, NewJungleVillageTemplate template)
    {
        structureComponents.add(template);
        return template;
    }

    private static boolean recursiveChildren2(TemplateManager manager, IGeneratorRot generator, Rotation rotation, int generation, NewJungleVillageTemplate parent, BlockPos pos, List<StructureComponent> components, Random random)
    {
        if (generation <= 2) {
            List<StructureComponent> list = Lists.newArrayList();

            if (generator.generate(manager, generation, parent, rotation, pos, list, random)) {
                boolean flag = false;
                int i = random.nextInt();
                for (StructureComponent structurecomponent : list) {
                    structurecomponent.componentType = i;
                    StructureComponent structurecomponent1 = StructureComponent.findIntersecting(components, structurecomponent.getBoundingBox());

                    if (structurecomponent1 != null && structurecomponent1.getComponentType() != parent.getComponentType()) {
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
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 3, 6)));//up = none, left = cc90, right=c90, down = 180 //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_2_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 3, 7))); //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_3_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 2, 4)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(2, 2,0 ))); //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_4_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(12, 5, 5)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(7,5,12))); //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_5_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(5, 1, 0)), new Tuple<>(Rotation.NONE, new BlockPos(11,1,5))); //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_6_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(0, 3, 7)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(4,3,0)), new Tuple<>(Rotation.NONE, new BlockPos(10, 3, 2))); //done
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_7_BRIDGES = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(12, 2, 3)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(5,2,11))); //done



    private static List<Tuple<Rotation, BlockPos>> getBridgesbyInt(int houseid){
        List<Tuple<Rotation, BlockPos>> finallist = null;
        switch (houseid){

            case 1:
                finallist = HOUSE_1_BRIDGES;
                break;

            case 2:
                finallist = HOUSE_2_BRIDGES;
                break;
            case 3:
                finallist = HOUSE_3_BRIDGES;
                break;
            case 4:
                finallist = HOUSE_4_BRIDGES;
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
        }

        return finallist;
    }

    interface IGeneratorRot
    {
        void init();

        boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation rot, BlockPos pos, List<StructureComponent> components, Random rand);
    }


    private static final BlockPos HOUSE1_OFFSET = new BlockPos(-4, 0, -3);
    private static final BlockPos HOUSE2_OFFSET = new BlockPos(-3, 0, -3);
    private static final BlockPos HOUSE3_OFFSET = new BlockPos(-1, 0, -1); //-2 - 3
    private static final BlockPos HOUSE4_OFFSET = new BlockPos(-4, 0, -4);
    private static final BlockPos HOUSE5_OFFSET = new BlockPos(-3, 0, -3);//to
    private static final BlockPos HOUSE6_OFFSET = new BlockPos(-3, 0, -3);
    private static final BlockPos HOUSE7_OFFSET = new BlockPos(-3, 0, -3);


    private static final IGeneratorRot HOUSE_GENERATOR = new IGeneratorRot() {
        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation rot, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = parent.getPlacementSettings().getRotation();
            //int i = random.nextInt(7) + 1;
            int i = random.nextInt(5) + 3;
       //     BeastSlayer.logger.debug("spawning house base!");
            String s = "house" + i;
            NewJungleVillageTemplate villageTemplate = NewJungleVillagePieces.addHelper(structureComponents, NewJungleVillagePieces.addPiece(manager, parent, pos.add(NewJungleVillagePieces.getHouseOffset(i)), s, rotation));
          //  villageTemplate.setComponentType(1);
            boolean b = villageTemplate.isCollidingExcParent(manager, parent, structureComponents);
            if(b){
               // BeastSlayer.logger.debug("oops!");
                return false;
            }
            if(NewJungleVillagePieces.getBridgesbyInt(i) != null && !NewJungleVillagePieces.getBridgesbyInt(i).isEmpty() && generation < 3) {
                for (Tuple<Rotation, BlockPos> tuple : NewJungleVillagePieces.getBridgesbyInt(i)) {

                   // NewJungleVillagePieces.BRIDGE_GENERATOR.generate(manager, generation, villageTemplate, tuple.getFirst(),tuple.getSecond(), structureComponents, random);
                    NewJungleVillagePieces.recursiveChildren2(manager, BRIDGE_GENERATOR,  tuple.getFirst(), generation + 1, villageTemplate, tuple.getSecond(), structureComponents, random);
                }
            }


            return true;
        }

    };
    private static BlockPos getHouseOffset(int i){
        BlockPos pos = null;
        switch (i){
            case 1:
                pos = HOUSE1_OFFSET;
                break;
            case 2:
                pos = HOUSE2_OFFSET;
                break;
            case 3:
                pos = HOUSE3_OFFSET;
                break;
            case 4:
                pos = HOUSE4_OFFSET;
                break;
            case 5:
                pos = HOUSE5_OFFSET;
                break;
            case 6:
                pos = HOUSE6_OFFSET;
                break;
            case 7:
                pos = HOUSE7_OFFSET;
                break;
        }
        return pos;
    }

    private static final IGeneratorRot BRIDGE_GENERATOR = new IGeneratorRot(){

        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation rot, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = parent.getPlacementSettings().getRotation().add(rot);
            if(generation < 3) {

             int randLength = random.nextInt(3);
                String s =  (randLength == 0 ? "bridge_l" : (randLength == 1 ? "bridge_m" : "bridge_s")) + "_ll";
                NewJungleVillageTemplate bridge_piece =  NewJungleVillagePieces.addHelper(structureComponents, NewJungleVillagePieces.addPiece(manager, parent, pos, s, rotation)); //first log piece is added
                bridge_piece.componentType = -1;

                boolean b = bridge_piece.isCollidingExcParent(manager, parent, structureComponents);
                if(b){
                return false;
                 } else {
                    if(random.nextInt(3) != 0) {
                        NewJungleVillagePieces.recursiveChildren2(manager, BRIDGETOHOUSE_GENERATOR, bridge_piece.getRotation(), generation, bridge_piece, new BlockPos(bridge_piece.getTemplate().getSize().getX(), 0, 0), structureComponents, random);
                       // BeastSlayer.logger.debug(bridge_piece.getRotation());
                    } else {
                        NewJungleVillagePieces.recursiveChildren2(manager, FARMLAND_GENERATOR, bridge_piece.getRotation(), generation, bridge_piece, new BlockPos(bridge_piece.getTemplate().getSize().getX(), 0, 0), structureComponents, random);
                    }
                }
            }

            return true;

        }
    };

    private static final IGeneratorRot BRIDGETOHOUSE_GENERATOR = new IGeneratorRot(){

        @Override
        public void init() {

        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation InheritedRot, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = parent.getPlacementSettings().getRotation();

            int i = random.nextInt(7) + 1;
            String s = "house" + i;
           // BeastSlayer.logger.debug("spawning new house!");
            int safeRand = random.nextInt(getHouseBridgeOffset(i).size());
            BlockPos pos1 = getHouseBridgeOffset(i).get(safeRand).getSecond();
            Rotation rot1 = getHouseBridgeOffset(i).get(safeRand).getFirst();
            NewJungleVillageTemplate villageTemplate = NewJungleVillagePieces.addHelper(structureComponents, NewJungleVillagePieces.addPiece(manager, parent, pos.add(pos1), s, rotation.add(rot1)));
            //bridge_piece.setComponentType(-1);
            //7 9 13
            boolean b = villageTemplate.isCollidingExcParent(manager, parent, structureComponents);
            if(b){
              //  BeastSlayer.logger.debug("oops!");
                return false;
            }

            if(NewJungleVillagePieces.getBridgesbyInt(i) != null && !NewJungleVillagePieces.getBridgesbyInt(i).isEmpty() && generation < 3) {
                for (Tuple<Rotation, BlockPos> tuple : NewJungleVillagePieces.getBridgesbyInt(i)) {


                    if (tuple.getFirst().add(villageTemplate.getRotation()).add(Rotation.CLOCKWISE_180) != InheritedRot && NewJungleVillagePieces.getBridgesbyInt(i).size() > 1) {
                        // BRIDGE_GENERATOR.generate(manager, generation + 1, villageTemplate, tuple.getFirst(),tuple.getSecond(), structureComponents, random);
                        //BeastSlayer.logger.debug("my inherited rotation " + InheritedRot.add(rotation).add(rot1));
                        //BeastSlayer.logger.debug("my rotation " + tuple.getFirst().add(Rotation.CLOCKWISE_180));
                        NewJungleVillagePieces.recursiveChildren2(manager, BRIDGE_GENERATOR, tuple.getFirst(), generation + 1, villageTemplate, tuple.getSecond(), structureComponents, random);
                    }
                }
            }
            NewJungleVillagePieces.recursiveChildren2(manager, STAIRS_GENERATOR, rotation, generation, villageTemplate, new BlockPos(0, -6, 0).add(getHouseOffset(i).getX() * -1, getHouseOffset(i).getY(), getHouseOffset(i).getZ() * -1), structureComponents, random);
            return true;
        }
    };

    private static final List<Tuple<Rotation, BlockPos>> HOUSE_1_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0,-3,-4)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_2_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0,-3,-5)));//down 0, left cc90, right c90, up 180
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_3_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0,-2,-2)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -2, 4)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_4_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(12,-5,7)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(12, -5, -5)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_5_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(11,-1,7)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 7)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_6_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(0,-3,-5)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -3, 6)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(11, -3,4)));
    private static final List<Tuple<Rotation, BlockPos>> HOUSE_7_BRIDGETOHOUSE = Lists.newArrayList(new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(11,-2,-3)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(12, -2,5)));

    private static List<Tuple<Rotation, BlockPos>> getHouseBridgeOffset(int houseid){
        List<Tuple<Rotation, BlockPos>> finallist = null;
        switch (houseid){

            case 1:
                finallist = HOUSE_1_BRIDGETOHOUSE;
                break;

            case 2:
                finallist = HOUSE_2_BRIDGETOHOUSE;
                break;
            case 3:
                finallist = HOUSE_3_BRIDGETOHOUSE;
                break;
            case 4:
                finallist = HOUSE_4_BRIDGETOHOUSE;
                break;
            case 5:
                finallist = HOUSE_5_BRIDGETOHOUSE;
                break;
            case 6:
                finallist = HOUSE_6_BRIDGETOHOUSE;
                break;
            case 7:
                finallist = HOUSE_7_BRIDGETOHOUSE;
                break;
        }
        return finallist;
    }
    private static final Set<Block> REPLACE = Sets.newHashSet(
            Blocks.AIR,
            Blocks.LOG,
            Blocks.LEAVES,
            Blocks.VINE
    );
     private static final IGeneratorRot STAIRS_GENERATOR = new IGeneratorRot() {
        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation InheritedRot, BlockPos pos, List<StructureComponent> structureComponents, Random random) {
            Rotation rotation = parent.getPlacementSettings().getRotation();
            int lit = random.nextInt(2);
            String s = "stairs" + (lit == 0 ? "_lit" : "");

            NewJungleVillageTemplate template = NewJungleVillagePieces.addHelper(structureComponents, NewJungleVillagePieces.addPiece(manager, parent, pos,s, rotation));
          //  BeastSlayer.logger.debug(template.getTemplatePosition().down());
           // BeastSlayer.logger.debug(NewJungleVillagePieces.getWorldIn().getBlockState(template.getTemplatePosition().down()).getBlock());
            if(REPLACE.contains(NewJungleVillagePieces.getWorldIn().getBlockState(template.getTemplatePosition()).getBlock()) && template.getTemplatePosition().getY() > 4){
               // NewJungleVillageTemplate template2 = NewJungleVillagePieces.addHelper(structureComponents, NewJungleVillagePieces.addPiece(manager, template, ,s, rotation));
                NewJungleVillagePieces.recursiveChildren2(manager, STAIRS_GENERATOR, Rotation.NONE, generation, template, new BlockPos(0, -6, 0), structureComponents,random );
            }



            return true;
        }
    };

    private static final  List<Tuple<Rotation, BlockPos>> FARMLAND_OFFSETS = Lists.newArrayList(
            new Tuple<>(Rotation.NONE, new BlockPos( 0, -1, -4)),
            new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(9, -1, 5)),
            new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(9, -1, -4)),
            new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 6))
    );

    private static final  List<Tuple<Rotation, BlockPos>> FARMLAND_BRIDGE_OFFSETS = Lists.newArrayList(
            new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos( -1, 1, 6)),
            new Tuple<>(Rotation.NONE, new BlockPos(10, 1, 3)),
            new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(4, 1, -1)),
            new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6, 1, 10))
    );

    private static final IGeneratorRot FARMLAND_GENERATOR = new IGeneratorRot() {
        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation InheritedRot, BlockPos pos, List<StructureComponent> components, Random rand) {
            Rotation rotation = parent.getPlacementSettings().getRotation();
            int variant = rand.nextInt(3) + 1;
            int variantvar = rand.nextInt(3);
            int z = rand.nextInt(FARMLAND_OFFSETS.size());
            Rotation myrot = FARMLAND_OFFSETS.get(z).getFirst();
            BlockPos myOffset = FARMLAND_OFFSETS.get(z).getSecond();
            String s = "farmland" + variant + (variantvar == 0 ? "" : "stairs");
            String alt = "farmland4";
            if(rand.nextInt(10)== 0){
                s = alt;
                variant = 4;
            }

            NewJungleVillageTemplate template = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPiece(manager, parent, pos.add(myOffset),s, rotation.add(myrot)));
            String treedown = "treetop_ladder";
            NewJungleVillageTemplate treetop_down = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPieceInsert(manager, template, new BlockPos(-1, -4, -1), treedown, rotation.add(myrot)));

            int i = rand.nextInt(2) + 1;
            String watchtowers = "watchtower" + i + "stairs";

            NewJungleVillageTemplate tower = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPiece(manager, treetop_down, getBlockposString(watchtowers, treetop_down), watchtowers, treetop_down.getRotation()));
            NewJungleVillagePieces.recursiveChildren2(manager, STAIRS_GENERATOR, Rotation.NONE, generation, tower, getBlockposStringStairs(watchtowers,tower), components,rand );
            if(s.contains("stairs")){
                String treeup = "treetop" + (rand.nextInt(2) == 0 ? "_ladder" : "");
                NewJungleVillageTemplate treetop_up = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPieceInsert(manager, template, new BlockPos(-1, template.getTemplate().getSize().getY(), -1), treeup, rotation.add(myrot)));
                if(treeup.contains("ladder")) {
                    NewJungleVillagePieces.recursiveChildren2(manager, TREEHOUSE_GENERATOR, InheritedRot, generation, treetop_up, new BlockPos(0, treetop_up.getTemplate().getSize().getY() - 1, 0), components, rand);
                }
            }


            if(FARMLAND_BRIDGE_OFFSETS.size() > 0 && generation < 3) {
                for (Tuple<Rotation, BlockPos> tuple : FARMLAND_BRIDGE_OFFSETS) {


                    if (tuple.getFirst().add(template.getRotation()).add(Rotation.CLOCKWISE_180) != InheritedRot) {
                        // BRIDGE_GENERATOR.generate(manager, generation + 1, villageTemplate, tuple.getFirst(),tuple.getSecond(), structureComponents, random);
                      //  BeastSlayer.logger.debug("my inherited rotation " + InheritedRot.add(rotation).add(rot1));
                        if(rand.nextInt(4) != 0) {
                           // BeastSlayer.logger.debug("my rotation " + tuple.getFirst().add(Rotation.CLOCKWISE_180));
                            NewJungleVillagePieces.recursiveChildren2(manager, BRIDGE_GENERATOR, tuple.getFirst(), generation + 1, template, tuple.getSecond(), components, rand);
                        }
                    }


                }
            }
            return true;
        }
    };
    private static BlockPos getBlockposString(String s, NewJungleVillageTemplate template){

        if(s.contains("1")){
            return new BlockPos(2, -template.getTemplate().getSize().getY(), 2);
        } else {
            return new BlockPos(1 ,-template.getTemplate().getSize().getY(),1);
        }
    }
    private static BlockPos getBlockposStringStairs(String s, NewJungleVillageTemplate template){

        if(s.contains("1")){
            return new BlockPos(1, -template.getTemplate().getSize().getY() - 1, 1);
        } else {
            return new BlockPos(2 ,-template.getTemplate().getSize().getY(),2);
        }
    }

    private static final IGeneratorRot TREEHOUSE_GENERATOR = new IGeneratorRot() {
        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager manager, int generation, NewJungleVillageTemplate parent, Rotation rot, BlockPos pos, List<StructureComponent> components, Random rand) {
            Rotation rotation = parent.getPlacementSettings().getRotation();
            int i = rand.nextInt(7) + 1;
            //int i = 7;
            String s = "house_l" + i;
            NewJungleVillageTemplate house_l = NewJungleVillagePieces.addHelper(components, NewJungleVillagePieces.addPiece(manager,parent, pos.add(getBlockposTreehouse(i)), s,rotation));
            return true;
        }
    } ;

    private static BlockPos getBlockposTreehouse(int i){
        BlockPos pos = null;
        switch (i){
            case 1:
                pos = new BlockPos( -1, 0, 0);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                pos = new BlockPos( 0, 0, 0); //surprisingly this is correct
                break;
        }
        return pos;
    }
}
