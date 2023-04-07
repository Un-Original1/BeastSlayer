package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.potions.*;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BeastSlayer.MODID)
public class ModPotions {
    private ModPotions(){
    }
    public static final PotionShielded SHIELDED = (PotionShielded) new PotionShielded(false, /*3844804*/ 0).setRegistryName(BeastSlayer.MODID, "shielded");
    public static final PotionPossessed POSSESSED = (PotionPossessed) new PotionPossessed(true, 1486006).setRegistryName(BeastSlayer.MODID, "possessed");
    public static final PotionGhostly GHOSTLY = (PotionGhostly) new PotionGhostly(false, 1486006).setRegistryName(BeastSlayer.MODID, "ghostly");
    public static final PotionRifted RIFTED = (PotionRifted) new PotionRifted(false, 6335322).setRegistryName(BeastSlayer.MODID, "rifted");
    public static final PotionUndead UNDEAD = (PotionUndead) new PotionUndead(false, 989477).setRegistryName(BeastSlayer.MODID,"undead");

    public static PotionType ghostly =  new PotionType("potionGhostly", new PotionEffect[]{new PotionEffect(ModPotions.GHOSTLY, 100)}).setRegistryName("poison_ghostly");
    public static PotionType longGhostly = new PotionType("potionGhostly", new PotionEffect[]{new PotionEffect(ModPotions.GHOSTLY, 200)}).setRegistryName("long_potion_ghostly");

    public static PotionType rifted =  new PotionType("potionRifted", new PotionEffect[]{new PotionEffect(ModPotions.RIFTED, 600)}).setRegistryName("poison_rifted");
    public static PotionType longRifted = new PotionType("potionRifted", new PotionEffect[]{new PotionEffect(ModPotions.RIFTED, 1800)}).setRegistryName("long_potion_rifted");

    public static PotionType undead =  new PotionType("potionUndead", new PotionEffect[]{new PotionEffect(ModPotions.UNDEAD, 1200)}).setRegistryName("potion_undead");
    public static PotionType longUndead = new PotionType("potionUndead", new PotionEffect[]{new PotionEffect(ModPotions.UNDEAD, 2400)}).setRegistryName("long_potion_undead");

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event){
        event.getRegistry().registerAll(
                SHIELDED,
                POSSESSED,
                GHOSTLY,
                RIFTED,
                UNDEAD
        );
    }
    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(
                ghostly,
                longGhostly,
                rifted,
                longRifted,
                undead,
                longUndead
        );
        if(BeastSlayerConfig.ghostPotions) {
            PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.ECTOPLASM, ghostly);
            PotionHelper.addMix(ghostly, Items.REDSTONE, longGhostly);
        }
        PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.RIFTED_PEARL, rifted);
        PotionHelper.addMix(rifted, Items.REDSTONE, longRifted);

        PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.DARK_GOOP, undead);
        PotionHelper.addMix(undead, Items.REDSTONE, undead);

    }
}
