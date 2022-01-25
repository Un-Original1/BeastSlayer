package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.potions.PotionGhostly;
import com.unoriginal.ancientbeasts.potions.PotionPossessed;
import com.unoriginal.ancientbeasts.potions.PotionShielded;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = AncientBeasts.MODID)
public class ModPotions {
    private ModPotions(){
    }
    public static final PotionShielded SHIELDED = (PotionShielded) new PotionShielded(false, /*3844804*/ 0).setRegistryName(AncientBeasts.MODID, "shielded");
    public static final PotionPossessed POSSESSED = (PotionPossessed) new PotionPossessed(true, 1486006).setRegistryName(AncientBeasts.MODID, "possessed");
    public static final PotionGhostly GHOSTLY = (PotionGhostly) new PotionGhostly(false, 1486006).setRegistryName(AncientBeasts.MODID, "ghostly");

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event){
        event.getRegistry().registerAll(
                SHIELDED,
                POSSESSED,
                GHOSTLY
        );
    }
    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        PotionType ghostly = new PotionType("potionGhostly", new PotionEffect[]{new PotionEffect(ModPotions.GHOSTLY, 100)}).setRegistryName("poison_ghostly");
        PotionType longGhostly = new PotionType("potionGhostly", new PotionEffect[]{new PotionEffect(ModPotions.GHOSTLY, 200)}).setRegistryName("long_potion_ghostly");
        event.getRegistry().registerAll(
                ghostly,
                longGhostly
        );
        if(AncientBeastsConfig.ghostPotions) {
            PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.ECTOPLASM, ghostly);
            PotionHelper.addMix(ghostly, Items.REDSTONE, longGhostly);
        }
    }
}
