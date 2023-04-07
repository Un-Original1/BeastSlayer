package com.unoriginal.beastslayer.worldGen;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityHunter;
import com.unoriginal.beastslayer.entity.Entities.EntityPriest;
import com.unoriginal.beastslayer.entity.Entities.EntityTank;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeWarrior;
import net.minecraft.block.Block;
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
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JungleVillagePieces {
    private static final ResourceLocation LOOT_HOUSE = new ResourceLocation(BeastSlayer.MODID, "structures/j_house");
    private static final ResourceLocation LOOT_WIZARD = new ResourceLocation(BeastSlayer.MODID, "structures/j_wizard");
    private static final ResourceLocation LOOT_FARM = new ResourceLocation(BeastSlayer.MODID, "structures/j_farm");
    private TemplateManager manager;

    public static class HouseCollection {
        public HouseCollection() {
        }

        public String RandHouse(Random rand) {
            int i = rand.nextInt(13);
            if (i != 0) {
                return "house_" + i;
            } else {
                return "house_1";
            }
        }
        public int houseStringtoInt(String s){
            int i = Integer.parseInt(s.replaceAll("[\\D]", ""));
            return i;
        }

        public String RandHouseLog(Random random) {
            String s = null;
            int i = random.nextInt(8);
            switch (i) {
                case 0:
                    s = "house_5";
                    break;
                case 1:
                    s = "house_6";
                    break;
                case 2:
                    s = "house_7";
                    break;
                case 3:
                    s = "house_8";
                    break;
                case 4:
                    s = "house_9";
                    break;
                case 5:
                    s = "house_11";
                    break;
                case 6:
                    s = "house_12";
                    break;
                case 7:
                    s = "house_10";
                    break;
            }
            return s;
        }

        public String RandHouseSlab(Random random) {
            String s = null;
            int i = random.nextInt(7);
            switch (i) {
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
    house 10: slab 11, h_log 15
    house 11: log 12
    house 12: log 13, slab 13
    */
            return s;
        }
        public int houseOffset(String s){
            int house = houseStringtoInt(s);
            switch (house){

            }
            return 1;
        }
    }
    public static class BridgeCollection {
        public BridgeCollection()
        {
        }

        public String RandBridgeSlab(Random rand, boolean isTop){
            int i = rand.nextInt(2);
            if(isTop){
                if (i != 0) {
                    return "bridge_slab_top" + "_s";
                } else {
                    return "bridge_slab_top";
                }
            } else {
                if (i != 0) {
                    return "bridge_slab" + "_s";
                } else {
                    return "bridge_slab";
                }
            }
        }
        public String RandBridgeLog(Random rand){
            int i = rand.nextInt(2);
            if(i == 0) {
                return "bridge_log" + "_s";
            } else {
                return "bridge_log";
            }
        }
        public String RandHalfBridgeLog(Random rand){
            int i = rand.nextInt(2);
            if(i == 0) {
                return "bridge_h_log" + "_s";
            } else {
                return "bridge_h_log";
            }
        }
    }

    public static void generateVillage(TemplateManager manager, BlockPos pos, Rotation rot, List<JungleVillageTemplate> list, Random rand, World world, List<StructureComponent> components){
        Placer placer = new Placer(manager, rand);
        placer.createTribeVillage(world, pos, rot, list, components);
    }

    public static class JungleVillageTemplate extends StructureComponentTemplate {

        private String templateName;
        private Rotation rotation;
        private Mirror mirror;
        private World world;
        private int id;

        public JungleVillageTemplate()
        {
        }

        public JungleVillageTemplate(TemplateManager p_i47355_1_, String p_i47355_2_, BlockPos p_i47355_3_, Rotation p_i47355_4_, World world)
        {
            this(p_i47355_1_, p_i47355_2_, p_i47355_3_, p_i47355_4_, Mirror.NONE, world);
        }

        public JungleVillageTemplate(TemplateManager p_i47356_1_, String p_i47356_2_, BlockPos p_i47356_3_, Rotation p_i47356_4_, Mirror p_i47356_5_, World world)
        {
            super(0);
            this.templateName = p_i47356_2_;
            this.templatePosition = p_i47356_3_;
            this.rotation = p_i47356_4_;
            this.mirror = p_i47356_5_;
            this.world = world;
            id = id++;
            this.loadTemplate(p_i47356_1_, world);
        }

        public BlockPos getTemplatePosition() {
            return this.templatePosition;
        }




        public PlacementSettings getPlacementSettings(){
            return this.placeSettings;
        }

        public StructureBoundingBox getBoundingBox(){
            return this.boundingBox;
        }

        private void loadTemplate(TemplateManager manager, World worldIn)
        {
            MinecraftServer minecraftserver = worldIn.getMinecraftServer();
            Template template = manager.getTemplate(minecraftserver, new ResourceLocation("ancientbeasts:" + this.templateName));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).setRotation(this.rotation).setMirror(this.mirror);
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
            this.mirror = Mirror.valueOf(tagCompound.getString("Mi"));
            this.loadTemplate(p_143011_2_, world);
        }
        public int getId(){
            return this.id;
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
        public boolean isCollidingExcParent(TemplateManager manager,JungleVillageTemplate parent, List<StructureComponent> structures) {
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
    }

    static class EveryHouseBridgeRotations {
        protected EveryHouseBridgeRotations() {
        }

        public Rotation ApplyExistantRotation(Rotation rotation, String houseN) {
            return this.getDefaultRotationForHouse(houseN).add(rotation);
        }

        public Rotation getDefaultRotationForHouse(String houseN) {
            if (houseN.contains("_c0")) {
                return Rotation.NONE;
            }
            else if (houseN.contains("_c180")) {
                return Rotation.CLOCKWISE_180;
            }
            else if (houseN.contains("_cc90")) {

                return Rotation.COUNTERCLOCKWISE_90;
            } else if (houseN.contains("_c90")) {

                return Rotation.CLOCKWISE_90;

            } else {
                return Rotation.NONE;
            }
        }
        public Rotation OppositeRot (Rotation rot){
            Rotation result = null;
           switch (rot){
               case NONE:
                   result = Rotation.CLOCKWISE_180;
                   break;
               case CLOCKWISE_180:
                   result = Rotation.NONE;
                   break;
               case COUNTERCLOCKWISE_90:
                   result = Rotation.CLOCKWISE_90;
                   break;
               case CLOCKWISE_90:
                   result = Rotation.COUNTERCLOCKWISE_90;
                   break;
           }
           return result;
        }
        public Rotation getAvaliableHouseSlabRot(int houseN)
        {
            Random rand = new Random();
            Rotation result = null;
            switch (houseN) {
                case 1:
                    result = rand.nextInt(2) == 0 ? Rotation.CLOCKWISE_180 : Rotation.COUNTERCLOCKWISE_90;
                    break;
                case 2:
                    int i = rand.nextInt(3);
                    switch (i){
                        case 0:
                            result = Rotation.CLOCKWISE_90;
                            break;
                        case 1:
                            result = Rotation.CLOCKWISE_180;
                            break;
                        case 2:
                            result = Rotation.NONE;
                            break;
                    }
                    break;
                case 3:
                case 4:
                case 9:
                case 12:
                    result = Rotation.NONE;
                    break;
                case 10:
                    result = Rotation.COUNTERCLOCKWISE_90;
                    break;
            }
            return result;
        }
        public Rotation getAvaliableHouseLogRot(int houseN)
        {
            Random rand = new Random();
            Rotation result = null;
            switch (houseN) {
                case 1:
                    result = Rotation.COUNTERCLOCKWISE_90;
                    break;
                case 5:
                    result = rand.nextInt(2) == 0 ? Rotation.CLOCKWISE_180 : Rotation.CLOCKWISE_90;
                    break;
                case 6:
                case 9:
                    result = Rotation.CLOCKWISE_90;
                    break;
                case 7:
                case 8:
                    result = Rotation.NONE;
                    break;

                case 10:
                case 12:
                    result = Rotation.CLOCKWISE_180;
                    break;
                case 11:
                    result = rand.nextInt(2) == 0 ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90;
                    break;
            }
            return result;
        }
        public Rotation getRealRotToADD(Rotation rotA, Rotation rotB){
            //rot a = the rot of the  bridge, b = house rot, rot b must be rotated to be the opposite of rot a
            switch (rotA)
            {
                case CLOCKWISE_180:

                    switch (rotB)
                    {
                        case NONE:
                            return Rotation.NONE;
                        case CLOCKWISE_90:
                            return Rotation.COUNTERCLOCKWISE_90;
                        case CLOCKWISE_180:
                            return Rotation.CLOCKWISE_180;
                        case COUNTERCLOCKWISE_90:
                            return Rotation.CLOCKWISE_90;
                    }

                case COUNTERCLOCKWISE_90:

                    switch (rotB)
                    {
                        case NONE:
                            return Rotation.CLOCKWISE_90;
                        case CLOCKWISE_90:
                            return Rotation.NONE;
                        case CLOCKWISE_180:
                            return Rotation.COUNTERCLOCKWISE_90;
                        case COUNTERCLOCKWISE_90:
                            return Rotation.CLOCKWISE_180;
                    }

                case CLOCKWISE_90:

                    switch (rotB)
                    {
                        case NONE:
                            return Rotation.COUNTERCLOCKWISE_90;
                        case CLOCKWISE_90:
                            return Rotation.CLOCKWISE_180;
                        case CLOCKWISE_180:
                            return Rotation.CLOCKWISE_90;
                        case COUNTERCLOCKWISE_90:
                            return Rotation.NONE;
                    }
                case NONE:
                    switch (rotB)
                    {
                        case NONE:
                            return Rotation.CLOCKWISE_180;
                        case CLOCKWISE_90:
                            return Rotation.CLOCKWISE_90;
                        case CLOCKWISE_180:
                            return Rotation.NONE;
                        case COUNTERCLOCKWISE_90:
                            return Rotation.COUNTERCLOCKWISE_90;
                    }
                default:
                    return rotB;
            }
        }

        public int getIntXForRot(Rotation rotation){
            if (rotation== Rotation.CLOCKWISE_90) {
                return -1;
            }
            else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
                return 1;
            } else {
                return 0;
            }
        }
        public int getIntZForRot(Rotation rotation){
            if (rotation == Rotation.NONE) {
                return 1;
            }
            else if (rotation == Rotation.CLOCKWISE_180) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    static class Placer {
        private final TemplateManager templateManager;
        private final Random random;

            //  private int startX;
            //    private int startY;
       /* private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList(
                new Tuple(Rotation.NONE, new BlockPos(1, 0, 0)),
                new Tuple(Rotation.CLOCKWISE_90, new BlockPos(0, 0, 1)),
                new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, -1)),
                new Tuple(Rotation.CLOCKWISE_180, new BlockPos(0, 0, -1)));*/

        public Placer(TemplateManager p_i47361_1_, Random p_i47361_2_) {
            this.templateManager = p_i47361_1_;
            this.random = p_i47361_2_;
        }

        public void createTribeVillage(World world, BlockPos pos, Rotation rotation, List<JungleVillagePieces.JungleVillageTemplate> list, List<StructureComponent> components) {
            HouseCollection collection = new HouseCollection();

                //  int x = pos.getX() + random.nextInt(40);
                //  int z = pos.getZ() + random.nextInt(40);

            String s = collection.RandHouse(random);

                //  BlockPos newPos = new BlockPos(x, pos.getY(),z);


         //   houseNumber = 0;
            JungleVillageTemplate t = new JungleVillageTemplate(this.templateManager, s,    pos, rotation, world);
            list.add(t);
            placeBridge(s, random, list, world, pos, rotation, t, components,0);


        }

        public boolean placeBridge(String connectedHouse, Random rand, List<JungleVillagePieces.JungleVillageTemplate> list, World world, BlockPos pos, Rotation rotation, JungleVillageTemplate template, List<StructureComponent> components, int houseNumber) {
            if(houseNumber < 1) {
                BridgeCollection collection = new BridgeCollection();


                //  String bridge = collection.RandBridgeSlab(rand,false);
                Template house = templateManager.getTemplate(world.getMinecraftServer(), new ResourceLocation("ancientbeasts:" + connectedHouse));
                Map<BlockPos, String> map = house.getDataBlocks(pos, template.getPlacementSettings());
                if (!world.isRemote) {
                    for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
                        //  EnumFacing facing = template.getCoordBaseMode();

                        String bridgefinder = entry.getValue();


                        EveryHouseBridgeRotations rotations = new EveryHouseBridgeRotations();
                        String bridge_slab = collection.RandBridgeSlab(rand, false);
                        String bridge_slab_top = collection.RandBridgeSlab(rand, true);
                        String log = collection.RandBridgeLog(rand);
                        String log_h = collection.RandHalfBridgeLog(rand);
                        BlockPos position = entry.getKey();

                        //  BlockPos booleanPos = position.add(0, fix, 1).rotate(rotation);

                        Rotation NEWERROT = rotations.ApplyExistantRotation(rotation, bridgefinder);

                        boolean isLog = bridgefinder.contains("log") || bridgefinder.contains("c_fl");
                        BlockPos booleanPos = entry.getKey().add(rotations.getIntXForRot(NEWERROT), isLog ? 1 : 0, rotations.getIntZForRot(NEWERROT));
                        // BlockPos validPos = entry.getKey().

                        //   BlockPos pos1 = template.getTemplate().calculateConnectedPos(template.getPlacementSettings(), new BlockPos(0, 0 , 0), template.getPlacementSettings(), BlockPos.ORIGIN);


                        if (world.getBlockState(booleanPos).getBlock() != Blocks.STRUCTURE_BLOCK) {
                            if (bridgefinder.contains("c_slab")) {
                                if (!bridgefinder.contains("c_slab_r")) {
                                    JungleVillageTemplate boolhouse= new JungleVillageTemplate(templateManager, collection.RandBridgeSlab(rand,true), template.getTemplatePosition(), rotation, Mirror.NONE, world);
                                    if(!boolhouse.isCollidingExcParent(templateManager, template, components)) {
                                        list.add(new JungleVillageTemplate(this.templateManager, bridge_slab_top, position.down(1), NEWERROT, world));
                                        placeHouse(bridge_slab_top, rand, list, world, pos, NEWERROT, template, components, ++houseNumber);
                                        return true;
                                    }

                                } else {
                                    JungleVillageTemplate boolhouse= new JungleVillageTemplate(templateManager, collection.RandBridgeSlab(rand,true), template.getTemplatePosition(), rotation, Mirror.NONE, world);
                                    if(!boolhouse.isCollidingExcParent(templateManager, template, components)) {
                                        list.add(new JungleVillageTemplate(this.templateManager, bridge_slab_top, position.down(1), NEWERROT, world));
                                        placeHouse(bridge_slab_top, rand, list, world, pos,NEWERROT, template, components, ++houseNumber);
                                        return true;
                                    }

                                }
                            }
                            if (bridgefinder.contains("log") || bridgefinder.contains("c_fl")) {
                                JungleVillageTemplate boolhouse= new JungleVillageTemplate(templateManager, collection.RandBridgeLog(rand), template.getTemplatePosition(), rotation, Mirror.NONE, world);
                                if(!boolhouse.isCollidingExcParent(templateManager, template, components)) {
                                    list.add(new JungleVillageTemplate(this.templateManager, log, position, NEWERROT, world));
                                    placeHouse(log, rand, list, world, pos, NEWERROT, template, components, ++houseNumber);
                                    return true;
                                }
                            }
                           /* if (bridgefinder.contains("c_fl")) {
                                JungleVillageTemplate boolhouse= new JungleVillageTemplate(templateManager, collection.RandBridgeLog(rand), template.getTemplatePosition(), rotation, Mirror.NONE, world);
                                if(!boolhouse.isCollidingExcParent(templateManager, template, components)) {
                                    list.add(new JungleVillageTemplate(this.templateManager, log_h, position, NEWERROT, world));
                                    placeHouse(log_h, rand, list, world, pos, rotation, template, components, ++houseNumber);
                                    return true;
                                }
                            }*/
                        } else {
                            return false;
                        }
                    }

                }
            }
            return true;
        }
        public boolean placeHouse(String connectedBridge, Random rand, List<JungleVillagePieces.JungleVillageTemplate> list, World world, BlockPos pos, Rotation rotation, JungleVillageTemplate template, List<StructureComponent> components, int houseNumber){
           //if(houseNumber < 15) {
               HouseCollection collection = new HouseCollection();
               Template bridge = templateManager.getTemplate(world.getMinecraftServer(), new ResourceLocation("ancientbeasts:" + connectedBridge));
               EveryHouseBridgeRotations rotations = new EveryHouseBridgeRotations();
               //  int x = pos.getX() + random.nextInt(40);
               //  int z = pos.getZ() + random.nextInt(40);

               Map<BlockPos, String> map = bridge.getDataBlocks(pos, template.getPlacementSettings());
               if (!world.isRemote) {
                   for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
                       String placefinder = entry.getValue();

                       JungleVillageTemplate boolbridge= new JungleVillageTemplate(templateManager, collection.RandHouse(rand), template.getTemplatePosition(), rotation, Mirror.NONE, world);
                       if(!boolbridge.isCollidingExcParent(templateManager, template, components) && placefinder.contains("end")) {
                           BlockPos newPos = new BlockPos(entry.getKey().getX(), pos.getY(), entry.getKey().getZ());
                           BlockPos PosForCheck = entry.getKey().add(0, 0, 1).rotate(rotation);
                           if(world.getBlockState(PosForCheck).getBlock() == Blocks.STRUCTURE_BLOCK){
                               world.setBlockState(PosForCheck, Blocks.AIR.getDefaultState(), 3);
                           }

                           //String s = collection.RandHouse(random);
                           if(connectedBridge.contains("log"))
                           {
                               String house = collection.RandHouseLog(rand);

                               Rotation houseRot = rotations.getAvaliableHouseLogRot(collection.houseStringtoInt(house));
                               Rotation addRot = rotations.getRealRotToADD(rotation, houseRot);
                               Rotation REALROT = houseRot.add(addRot);

                               JungleVillageTemplate t = new JungleVillageTemplate(this.templateManager, house, newPos, REALROT, world);
                               list.add(t);
                               placeBridge(house, rand, list, world, newPos, REALROT, t, components, houseNumber);
                               return true;
                           }
                           if (connectedBridge.contains("slab")){
                               String house = collection.RandHouseSlab(rand);

                               Rotation houseRot = rotations.getAvaliableHouseSlabRot(collection.houseStringtoInt(house));
                               Rotation addRot = rotations.getRealRotToADD(rotation, houseRot);
                               Rotation REALROT = houseRot.add(addRot);

                               JungleVillageTemplate t = new JungleVillageTemplate(this.templateManager, house, newPos, REALROT, world);
                               list.add(t);
                               placeBridge(house, rand, list, world, newPos, REALROT, t, components, houseNumber);
                               return true;
                           }
                           //  BlockPos newPos = new BlockPos(x, pos.getY(),z);

                       } else {
                           return false;
                       }
                   }
               }
           //}
            return true;
        }

    }

    public static int getGroundFromAbove(World world, int x, int z) {
        int y = 255;
        boolean foundGround = false;
        while (!foundGround && y-- >= 31) {
            Block blockAt = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = blockAt == Blocks.GRASS || blockAt == Blocks.SAND || blockAt == Blocks.SNOW || blockAt == Blocks.SNOW_LAYER || blockAt == Blocks.MYCELIUM;
        }
        return y;
    }
}

