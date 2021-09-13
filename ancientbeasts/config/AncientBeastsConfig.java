package com.unoriginal.ancientbeasts.config;

import com.unoriginal.ancientbeasts.AncientBeasts;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = AncientBeasts.MODID)
public class AncientBeastsConfig {
    @Name("Sandmonster Enabled")
    @Comment("Is sandmonster enabled")
    @RequiresMcRestart
    public static boolean isSandmonsterEnabled = true;

    @Name("Desert Robes Enabled")
    @Comment("Are desert robes enabled")
    @RequiresMcRestart
    public static boolean isDesertRobesEnabled = true;

    @Name("Zealot Enabled")
    @Comment("Is zealot enabled")
    @RequiresMcRestart
    public static boolean isZealotEnabled = true;

    @Name("Zealot Spawn Everywhere")
    @Comment("Do zealots spawn everywhere")
    @RequiresMcRestart
    public static boolean zealotSpawnEverywhere = false;

    @Name("Zealot spawn Chance")
    @Comment("Zealot spawn chance on roofed forest")
    @RequiresMcRestart
    public static int zealotSpawnChance = 20;

    @Name("Zealot Everywhere Chance")
    @Comment("Zealot spawn chance everywhere")
    @RequiresMcRestart
    public static int zealotEverywhereSpawnChance = 15;

    @Name("Sandmonster Spawn")
    @Comment("Sandmonster spawn chance 5 is uncommon, 0 is common")
    @RequiresMcRestart
    public static int sandmonsterSpawnChance = 5;

    @Name("Armor Cooldown Hurt")
    @Comment("Armor cooldown ticks on hurt")
    @RequiresMcRestart
    public static int armorCooldownHurt = 200;

    @Name("Armor Cooldown Ability")
    @Comment("Armor cooldown ticks on ability used")
    @RequiresMcRestart
    public static int armorCooldownClick = 400;

    @Name("Is Ghost Enabled")
    @Comment("Are Ghosts enabled")
    @RequiresMcRestart
    public static boolean isGhostEnabled = true;

    @Name("Ghost spawn Chance")
    @Comment("Ghost spawn Chance")
    @RequiresMcRestart
    public static int ghostSpawnChance = 50;

    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(AncientBeasts.MODID)) {
            ConfigManager.sync(AncientBeasts.MODID, Config.Type.INSTANCE);
        }
    }

}
