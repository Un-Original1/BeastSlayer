package com.unoriginal.ancientbeasts.config;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.proxy.CommonProxy;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

@Config(modid = AncientBeasts.MODID)
public class AncientBeastsConfig {
    public static boolean isSandmonsterEnabled = true;
    public static boolean isDesertRobesEnabled = true;
    public static int sandmonsterSpawnChance = 5;
    public static int armorCooldownHurt = 200;
    public static int armorCooldownClick = 400;

    private static final String CATEGORY_GENERAL = "general";
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            AncientBeasts.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        isSandmonsterEnabled = cfg.getBoolean("is Sandmonster Enabled", CATEGORY_GENERAL, isSandmonsterEnabled, "set false to disable the creature");
        isDesertRobesEnabled = cfg.getBoolean("is Desert Robes Enabled", CATEGORY_GENERAL, isSandmonsterEnabled, "set false to disable the desert hood and robes");
        sandmonsterSpawnChance = cfg.getInt("Sandmonster spawn chance", CATEGORY_GENERAL, sandmonsterSpawnChance, 0, 10, "set 0 to make it more common, set to 10 to make it almost impossible to find");
        armorCooldownClick = cfg.getInt("Armor Cooldown On Click Ability", CATEGORY_GENERAL, armorCooldownClick, 100, 1000, "set 100 to make it quicker, set 1000 to make it slower");
        armorCooldownHurt = cfg.getInt("Armor Cooldown On Hurt Ability Triggered", CATEGORY_GENERAL, armorCooldownHurt, 100, 1000, "set 100 to make it quicker, set 1000 to make it slower, it might be more annoying to use if set lower");
    }
}
