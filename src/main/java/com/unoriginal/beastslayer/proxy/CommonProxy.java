package com.unoriginal.beastslayer.proxy;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.blocks.tile.TileEntityMovingLight;
import com.unoriginal.beastslayer.blocks.tile.TileEntityWitchcraftTable;
import com.unoriginal.beastslayer.gui.ABGuiHandler;
import com.unoriginal.beastslayer.init.*;
import com.unoriginal.beastslayer.integration.IntegrationBaubles;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.worldGen.ModWorldGen;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
   }


    public void handleAnimationPacket(int entityId, int index) {

    }

}
