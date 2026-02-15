package com.unoriginal.beastslayer.proxy;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.blocks.BlockModCauldron;
import com.unoriginal.beastslayer.blocks.tile.TileEntityMovingLight;
import com.unoriginal.beastslayer.blocks.tile.TileEntityWitchcraftTable;
import com.unoriginal.beastslayer.entity.Entities.EntityIceDart;
import com.unoriginal.beastslayer.gui.ABGuiHandler;
import com.unoriginal.beastslayer.init.*;
import com.unoriginal.beastslayer.integration.IntegrationBaubles;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.worldGen.ModWorldGen;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber
public class CommonProxy
{
    private static Method CriterionRegister;
    private static final ResourceLocation D_LIGHT = new ResourceLocation(BeastSlayer.MODID,"dynamic_light");
    private static final ResourceLocation WITCHCRAFT = new ResourceLocation(BeastSlayer.MODID, "witchcraft_table");
    public void preInit(FMLPreInitializationEvent e)
    {
        ModBlocks.init();
        ModEntities.init();
        ModItems.init();

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        GameRegistry.registerTileEntity(TileEntityMovingLight.class, D_LIGHT);
        GameRegistry.registerTileEntity(TileEntityWitchcraftTable.class, WITCHCRAFT);
        ModTriggers.init();
        BeastSlayerPacketHandler.initMessages();
        IntegrationBaubles.init();
    }
    public void registerParticles() {

    }

    public void openGui(ItemStack bestiary) {
    }

    public Object getArmorModel(Item item, EntityLivingBase entity) {
        return null;
    }


    public void init(FMLInitializationEvent e) {
        ModParticles.init();
        ABGuiHandler.registerGuiHandler();
        ConfigManager.sync(BeastSlayer.MODID, Config.Type.INSTANCE);
        OreDictionary.registerOre("ectoplasm", ModItems.ECTOPLASM);
        OreDictionary.registerOre("plankWood", ModBlocks.CURSED_PLANK);
        OreDictionary.registerOre("logWood", ModBlocks.CURSED_LOG);
        OreDictionary.registerOre("slabWood", ModBlocks.CURSED_SLAB_HALF);
        OreDictionary.registerOre("stairWood", ModBlocks.CURSED_STAIR);
        OreDictionary.registerOre("doorWood", ModBlocks.CURSED_DOOR);
        OreDictionary.registerOre("fenceWood", ModBlocks.CURSED_FENCE);
        OreDictionary.registerOre("fenceGateWood", ModBlocks.CURSED_GATE);
        OreDictionary.registerOre("treeSapling", ModBlocks.CURSED_SAPLING);
        OreDictionary.registerOre("treeLeaves", ModBlocks.CURSED_LEAVES);

        NetworkRegistry.INSTANCE.registerGuiHandler(BeastSlayer.instance, new ABGuiHandler());
        registerAdvancementTrigger(ModTriggers.OWLSTACK_INTERACT);
        registerAdvancementTrigger(ModTriggers.SUCCUBUS_FRIEND);
        registerAdvancementTrigger(ModTriggers.SUCCUBUS_BLOOD);
        registerAdvancementTrigger(ModTriggers.SUCCUBUS_BED);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        if(CriterionRegister == null) {
            CriterionRegister = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            CriterionRegister.setAccessible(true);
        }
        try {
            trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Failed to register trigger " + trigger.getId() + "!");
            e.printStackTrace();
        }
        return trigger;
    }

    public void postInit(FMLPostInitializationEvent e) {
        ModEntities.registerSpawns();


        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.ICE_DART, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EntityIceDart iceDart = new EntityIceDart(worldIn);
                iceDart.setRed(stackIn.getMetadata() > 0);
                iceDart.setPosition(position.getX(), position.getY(), position.getZ());

                return iceDart;
            }
        });


        NonNullList<ItemStack> logs = OreDictionary.getOres("logWood");
        for (ItemStack log : logs) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(log.getItem(), new Bootstrap.BehaviorDispenseOptional() {
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
                {
                    World world = source.getWorld();
                    BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
                    this.successful = true;

                    if (!world.isAirBlock(blockpos) && world.getBlockState(blockpos).getBlock() == ModBlocks.CAULDRON )
                    {
                        IBlockState iblockstate = world.getBlockState(blockpos);
                        BlockModCauldron blockModCauldron = (BlockModCauldron) world.getBlockState(blockpos).getBlock();
                        if(iblockstate.getValue(BlockModCauldron.LEVEL) > 0 && !world.isRemote)
                        {
                            EntityItem entityitem = new EntityItem(world);
                            entityitem.setItem(new ItemStack(ModBlocks.CURSED_LOG, 1));
                            entityitem.setPosition(blockpos.getX(), blockpos.getY() + 1D, blockpos.getZ());
                            world.spawnEntity(entityitem);
                            stack.shrink(1);
                            blockModCauldron.setWaterLevel(world, blockpos, iblockstate,iblockstate.getValue(BlockModCauldron.LEVEL) - 1);
                            world.playSound(null, blockpos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }


                    }
                    return stack;
                }
                @Override
                protected void playDispenseSound(IBlockSource source) {
                    super.playDispenseSound(source);
                }
            });
        }


        NonNullList<ItemStack> saplings = OreDictionary.getOres("treeSapling");
        for (ItemStack sapling : saplings) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(sapling.getItem(), new Bootstrap.BehaviorDispenseOptional() {
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
                {
                    World world = source.getWorld();
                    BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
                    this.successful = true;

                    if (!world.isAirBlock(blockpos) && world.getBlockState(blockpos).getBlock() == ModBlocks.CAULDRON )
                    {
                        IBlockState iblockstate = world.getBlockState(blockpos);
                        BlockModCauldron blockModCauldron = (BlockModCauldron) world.getBlockState(blockpos).getBlock();
                        if(iblockstate.getValue(BlockModCauldron.LEVEL)== 3 && !world.isRemote)
                        {
                            EntityItem entityitem = new EntityItem(world);
                            entityitem.setItem(new ItemStack(ModBlocks.CURSED_SAPLING, 1));
                            entityitem.setPosition(blockpos.getX(), blockpos.getY() + 1D, blockpos.getZ());
                            world.spawnEntity(entityitem);
                            stack.shrink(1);
                            blockModCauldron.setWaterLevel(world, blockpos, iblockstate,0);
                            world.playSound(null, blockpos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }


                    }
                    return stack;
                }
                @Override
                protected void playDispenseSound(IBlockSource source) {
                    super.playDispenseSound(source);
                }
            });
        }
   }


    public void handleAnimationPacket(int entityId, int index) {

    }

}
