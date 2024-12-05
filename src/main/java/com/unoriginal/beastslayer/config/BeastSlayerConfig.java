package com.unoriginal.beastslayer.config;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = BeastSlayer.MODID)
public class BeastSlayerConfig {

    @Name("Desert Robes Enabled")
    @Comment("Are desert robes enabled")
    @RequiresMcRestart
    public static boolean isDesertRobesEnabled = true;

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

    @Name("Sandmonster Spawn Rate")
    @Comment("Sandmonster spawn rate, higher numbers make it more common")
    @RequiresMcRestart
    public static int sandmonsterSpawnChance = 1;

    @Name("Sandmonster tame stack size")
    @Comment("this is mostly due to compatibility")
    @RequiresMcRestart
    public static int sandmonsterTameStackSize = 64;

    @Name("Armor Cooldown Hurt")
    @Comment("Armor cooldown ticks on hurt")
    @RequiresMcRestart
    public static int armorCooldownHurt = 200;

    @Name("Armor Cooldown Ability")
    @Comment("Armor cooldown ticks on ability used")
    @RequiresMcRestart
    public static int armorCooldownClick = 400;

    @Name("Ghost spawn Chance")
    @Comment("Ghost spawn Chance")
    @RequiresMcRestart
    public static int ghostSpawnChance = 50;

    @Name("Are Ghostly Form Potions Enabled")
    @Comment("Disable or enable ghostly form potions")
    @RequiresMcRestart
    public static boolean ghostPotions = true;


    @Name("Occult Tome Timer Bonus")
    @Comment("Add extra time to the occult tome passive ability, every 20 = 1 second")
    @RequiresMcRestart
    public static int sBTimerBonus = 0;

    @Name("Giant spawn chance")
    @Comment("giant spawn chance")
    @RequiresMcRestart
    public static int giantSpawnChance = 2;

    @Name("Frostash fox spawn chance")
    @Comment("Frostash fox spawn chance")
    @RequiresMcRestart
    public static int foxSpawnChance = 7;

    @Name("Ice Spikes cooldown")
    @Comment("20 = + 1 sec")
    @RequiresMcRestart
    public static int iceSpikesCooldown = 60;

    @Name("Frost walker spawn chance")
    @Comment("Frost walker spawn chance")
    @RequiresMcRestart
    public static int frostWalkerSpawnChance = 40;

    @Name("Frost wand cooldown")
    @Comment("20 = + 1 sec")
    @RequiresMcRestart
    public static int frostWandCooldown = 240;

    @Name("Should Vessel Consume item")
    @Comment("Applied for Gunpowder and Sugar")
    @RequiresMcRestart
    public static boolean shouldVesselConsumeItem = true;

    @Name("Circus Spawn Chance")
    @Comment("A lower number makes the spawning more frequent, however 0 is none")
    @RequiresMcRestart
    public static int CircusSpawnChance = 5;

    @Name("Damcell spawn Chance")
    @Comment("Damcell spawn Chance")
    @RequiresMcRestart
    public static int damcellSpawnChance = 40;

    @Name("Netherhound spawn chance")
    @Comment("Netherhound spawn chance")
    @RequiresMcRestart
    public static int netherhoundSpawnChance = 40;

    @Name("Bouldering spawn chance")
    @Comment("Bouldering Zombie spawn chance")
    @RequiresMcRestart
    public static int boulderingSpawnChance = 60;

    @Name("Miner Helmet Light")
    @Comment("Does miner helmet emits light?")
    @RequiresMcRestart
    public static boolean MinerHelmetLight = true;

    @Name("Miner Helmet Light Flickers")
    @Comment("Does miner helmet light flickers?")
    @RequiresMcRestart
    public static boolean MinerHelmetFlickers = true;

    @Name("Sandmonster Miniboss Health Bonus")
    @Comment("Turn that creature into a real beast!")
    @RequiresMcRestart
    public static double SandyHealthBonus = 0D;

    @Name("Sandmonster Health Bonus When tamed")
    @Comment("Turn that creature into a real beast!")
    @RequiresMcRestart
    public static double SandyTamedHealthBonus = 0D;

    @Name("Giant Miniboss Health Bonus")
    @Comment("Turn that creature into a real beast!")
    @RequiresMcRestart
    public static double GiantHealthBonus = 0D;

    @Name("Vessel Miniboss Health Bonus")
    @Comment("Turn that creature into a real beast!")
    @RequiresMcRestart
    public static double VesselHealthBonus = 0D;

    @Name("Little Vessel Item Blacklist")
    @Comment("Blacklist for items that little vessels should not be able to equip")
    @RequiresMcRestart
    public static String[] lil_v_blacklist = {};

    @Name("Owlstack Spawn Chance")
    @Comment("Owlstack Spawn Chance")
    @RequiresMcRestart
    public static int owlstackSpawnChance = 40;

    @Name("Rifted Enderman Spawn Chance")
    @Comment("Rifted Enderman Spawn Chance")
    @RequiresMcRestart
    public static int riftedEndermanSpawnChance = 4;

    @Name("Global Armor")
    @Comment("Make every mob have more armor")
    @RequiresMcRestart
    public static double GlobalArmor = 0.0D;

    @Name("Global Health Multiplier")
    @Comment("Make every mob have more health")
    @RequiresMcRestart
    public static double GlobalHealthMultiplier = 1.0D;

    @Name("Global Damage Multiplier")
    @Comment("Masochist")
    @RequiresMcRestart
    public static double GlobalDamageMultiplier = 1.0D;

    @Name("Rifted Enderman laser range")
    @Comment("I wouldn't raise it if I were you...")
    @RequiresMcRestart
    public static double RELaserRange = 18.0D;

    @Name("Owlstack Blacklist")
    @Comment("A list of creatures owlstack should be unable to affect")
    @RequiresMcRestart
    public static String[] owlstack_blacklist = {};

    @Name("Owlstack Affects Undead")
    @Comment("If true owlstack will only scare undead creatures")
    @RequiresMcRestart
    public static boolean owlstack_affects_undead = true;

    @Name("Bonepile Spawn Chance")
    @Comment("Bonepile Spawn Chance")
    @RequiresMcRestart
    public static int bonepileSpawnChance = 15;

    @Name("Nekros Spawn Chance")
    @Comment("Nekros Spawn Chance")
    @RequiresMcRestart
    public static int nekrosSpawnChance = 10;

    @Name("Witchcraft Table Decimals")
    @Comment("Wether witchcraft table can include lower quality items")
    @RequiresMcRestart
    public static boolean WitchcraftTableQualityDecimalRand = true;

    @Name("Enable Experimental features")
    @Comment("Toggle experimental features (likely buggy and unfinished)")
    @RequiresMcRestart
    public static boolean EnableExperimentalFeatures = false;

    @Name("ScaleArmorDurability")
    @Comment("Change the durability of every scale armor piece, 15 is iron like")
    @RequiresMcRestart
    public static int ScaleArmorDurability = 28;

    @Name("ScaleArmorValue")
    @Comment("Change the amount of armor pieces each part gives")
    @RequiresMcRestart
    public static int[] ScaleArmorValue = new int[]{1,3,5,3} ;

    @Name("GlobalDamageDamcellMultiplier")
    @Comment("Damcell ignores armor so it might require a special damage multiplier just for itself")
    @RequiresMcRestart
    public static double GlobalDamageDamcellMultiplier = 1D ;

    @Name("Mob AI Blacklist")
    @Comment("A list of creatures whose AI will be left untouched by the mod (no owlstack effects, etc)")
    @RequiresMcRestart
    public static String[] AI_blacklist = {
            "netherex:spinout"
    };

    @Name("Cauldron fill chance")
    @Comment("chances that vines from weeping trees fill a cauldron with goop, less numbers mean more likely")
    @RequiresMcRestart
    public static int CauldronFillChance = 40;

    @Name("Potion Blacklist")
    @Comment("A list of potion effects that concoction will not be able to grant (usage: modid:potion_name)")
    @RequiresMcRestart
    public static String[] Potion_blacklist = {
            "ancientbeasts:fire_frenzy"
    };

}
