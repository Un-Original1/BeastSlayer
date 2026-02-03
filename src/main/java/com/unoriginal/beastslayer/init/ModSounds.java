package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModSounds {
    public static final SoundEvent TORNADO_AMBIENT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "tornado_ambient")).setRegistryName("tornado_ambient");
    public static final SoundEvent SANDMONSTER_AMBIENT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "sandmonster_ambient")).setRegistryName("sandmonster_ambient");
    public static final SoundEvent SANDMONSTER_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "sandmonster_hurt")).setRegistryName("sandmonster_hurt");
    public static final SoundEvent SANDMONSTER_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "sandmonster_death")).setRegistryName("sandmonster_death");
    public static final SoundEvent ZEALOT_AMBIENT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "zealot_ambient")).setRegistryName("zealot_ambient");
    public static final SoundEvent ZEALOT_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "zealot_hurt")).setRegistryName("zealot_hurt");
    public static final SoundEvent ZEALOT_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "zealot_death")).setRegistryName("zealot_death");
    public static final SoundEvent GHOST_AMBIENT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "ghost_ambient")).setRegistryName("ghost_ambient");
    public static final SoundEvent GHOST_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "ghost_hurt")).setRegistryName("ghost_hurt");
    public static final SoundEvent GHOST_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "ghost_death")).setRegistryName("ghost_death");
    public static final SoundEvent GHOST_POSSESS = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "ghost_possess")).setRegistryName("ghost_possess");
    public static final SoundEvent FROSTASH_FOX_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_idle")).setRegistryName("frostash_fox_idle");
    public static final SoundEvent FROSTASH_FOX_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_death")).setRegistryName("frostash_fox_death");
    public static final SoundEvent FROSTASH_FOX_EAT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_eat")).setRegistryName("frostash_fox_eat");
    public static final SoundEvent FROSTASH_FOX_BITE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_bite")).setRegistryName("frostash_fox_bite");
    public static final SoundEvent FROSTASH_FOX_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_hurt")).setRegistryName("frostash_fox_hurt");
    public static final SoundEvent FROSTASH_FOX_SCREECH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_screech")).setRegistryName("frostash_fox_screech");
    public static final SoundEvent FROSTASH_FOX_SLEEP = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostash_fox_sleep")).setRegistryName("frostash_fox_sleep");
    public static final SoundEvent ICE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "ice")).setRegistryName("ice");
    public static final SoundEvent FROSTWALKER_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostwalker_idle")).setRegistryName("frostwalker_idle");
    public static final SoundEvent FROSTWALKER_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostwalker_hurt")).setRegistryName("frostwalker_hurt");
    public static final SoundEvent FROSTWALKER_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostwalker_death")).setRegistryName("frostwalker_death");
    public static final SoundEvent FROSTWALKER_PAIN = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "frostwalker_ice")).setRegistryName("frostwalker_ice");
    public static final SoundEvent VESSEL_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "vessel_idle")).setRegistryName("vessel_idle");
    public static final SoundEvent VESSEL_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "vessel_hurt")).setRegistryName("vessel_hurt");
    public static final SoundEvent VESSEL_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "vessel_death")).setRegistryName("vessel_death");
    public static final SoundEvent LITTLE_VESSEL_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "little_vessel_hurt")).setRegistryName("little_vessel_hurt");
    public static final SoundEvent LITTLE_VESSEL_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "little_vessel_death")).setRegistryName("little_vessel_death");
    public static final SoundEvent LITTLE_V_ACTIVATE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "little_vessel_activate")).setRegistryName("little_vessel_activate");
    public static final SoundEvent LITTLE_V_DEACTIVATE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "little_vessel_deactivate")).setRegistryName("little_vessel_deactivate");
    public static final SoundEvent DAMCELL_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_idle")).setRegistryName("damcell_idle");
    public static final SoundEvent DAMCELL_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_hurt")).setRegistryName("damcell_hurt");
    public static final SoundEvent DAMCELL_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_death")).setRegistryName("damcell_death");
    public static final SoundEvent DAMCELL_OPEN = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_open")).setRegistryName("damcell_open");
    public static final SoundEvent DAMCELL_CLOSE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_close")).setRegistryName("damcell_close");
    public static final SoundEvent DAMCELL_SHOOT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "damcell_shoot")).setRegistryName("damcell_shoot");
    public static final SoundEvent SPIKES_ACTIVATE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "trap_activate")).setRegistryName("trap_activate");
    public static final SoundEvent SPIKES_DEACTIVATE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "trap_deactivate")).setRegistryName("trap_deactivate");

    public static final SoundEvent NETHERHOUND_CHARGE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "heckhound_charge")).setRegistryName("heckhound_charge");
    public static final SoundEvent NETHERHOUND_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "heckhound_death")).setRegistryName("heckhound_death");
    public static final SoundEvent NETHERHOUND_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "heckhound_hurt")).setRegistryName("heckhound_hurt");
    public static final SoundEvent NETHERHOUND_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "heckhound_idle")).setRegistryName("heckhound_idle");

    public static final SoundEvent OWLSTACK_IDLE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "owlstack_idle")).setRegistryName("owlstack_idle");
    public static final SoundEvent OWLSTACK_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "owlstack_hurt")).setRegistryName("owlstack_hurt");
    public static final SoundEvent OWLSTACK_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "owlstack_death")).setRegistryName("owlstack_death");
    public static final SoundEvent OWLSTACK_SCREAM = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "owlstack_screech")).setRegistryName("owlstack_screech");

    public static final SoundEvent KUNAI = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "kunai_chain")).setRegistryName("kunai_chain");
    public static final SoundEvent MAGIC_SHIELD = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "shield_magic")).setRegistryName("shield_magic");
    public static final SoundEvent KUNAI_HIT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "kunai_hit")).setRegistryName("kunai_hit");

    public static final SoundEvent SMOKE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "smoke")).setRegistryName("smoke");
    public static final SoundEvent SHOCKWAVE = new SoundEvent(new ResourceLocation(BeastSlayer.MODID,"shockwave")).setRegistryName("shockwave");

    public static final SoundEvent SUCC_HURT = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_hurt")).setRegistryName("succ_hurt");
    public static final SoundEvent SUCC_AMB = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_ambient")).setRegistryName("succ_ambient");
    public static final SoundEvent SUCC_SUCK = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_suck")).setRegistryName("succ_suck");
    public static final SoundEvent SUCC_SIGH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_sigh")).setRegistryName("succ_sigh");
    public static final SoundEvent SUCC_SPELL = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_spell")).setRegistryName("succ_spell");
    public static final SoundEvent SUCC_DEATH = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "succ_death")).setRegistryName("succ_death");

    public static final SoundEvent MOSQ_LOOP = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "mosq_loop")).setRegistryName("mosq_loop");
    public static final SoundEvent MOSQ_GULP = new SoundEvent(new ResourceLocation(BeastSlayer.MODID, "mosq_gulp")).setRegistryName("mosq_gulp");

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().register(TORNADO_AMBIENT);
            event.getRegistry().register(SANDMONSTER_AMBIENT);
            event.getRegistry().register(SANDMONSTER_HURT);
            event.getRegistry().register(SANDMONSTER_DEATH);
            event.getRegistry().register(ZEALOT_AMBIENT);
            event.getRegistry().register(ZEALOT_HURT);
            event.getRegistry().register(ZEALOT_DEATH);
            event.getRegistry().register(GHOST_AMBIENT);
            event.getRegistry().register(GHOST_HURT);
            event.getRegistry().register(GHOST_DEATH);
            event.getRegistry().register(GHOST_POSSESS);
            event.getRegistry().register(FROSTASH_FOX_IDLE);
            event.getRegistry().register(FROSTASH_FOX_DEATH);
            event.getRegistry().register(FROSTASH_FOX_BITE);
            event.getRegistry().register(FROSTASH_FOX_EAT);
            event.getRegistry().register(FROSTASH_FOX_HURT);
            event.getRegistry().register(FROSTASH_FOX_SCREECH);
            event.getRegistry().register(FROSTASH_FOX_SLEEP);
            event.getRegistry().register(ICE);
            event.getRegistry().register(FROSTWALKER_IDLE);
            event.getRegistry().register(FROSTWALKER_HURT);
            event.getRegistry().register(FROSTWALKER_DEATH);
            event.getRegistry().register(FROSTWALKER_PAIN);
            event.getRegistry().register(VESSEL_IDLE);
            event.getRegistry().register(VESSEL_HURT);
            event.getRegistry().register(VESSEL_DEATH);
            event.getRegistry().register(LITTLE_VESSEL_HURT);
            event.getRegistry().register(LITTLE_VESSEL_DEATH);
            event.getRegistry().register(LITTLE_V_ACTIVATE);
            event.getRegistry().register(LITTLE_V_DEACTIVATE);
            event.getRegistry().register(DAMCELL_IDLE);
            event.getRegistry().register(DAMCELL_HURT);
            event.getRegistry().register(DAMCELL_DEATH);
            event.getRegistry().register(DAMCELL_OPEN);
            event.getRegistry().register(DAMCELL_CLOSE);
            event.getRegistry().register(DAMCELL_SHOOT);
            event.getRegistry().register(SPIKES_ACTIVATE);
            event.getRegistry().register(SPIKES_DEACTIVATE);
            event.getRegistry().register(NETHERHOUND_CHARGE);
            event.getRegistry().register(NETHERHOUND_DEATH);
            event.getRegistry().register(NETHERHOUND_HURT);
            event.getRegistry().register(NETHERHOUND_IDLE);
            event.getRegistry().register(OWLSTACK_IDLE);
            event.getRegistry().register(OWLSTACK_HURT);
            event.getRegistry().register(OWLSTACK_DEATH);
            event.getRegistry().register(OWLSTACK_SCREAM);
            event.getRegistry().register(KUNAI);
            event.getRegistry().register(MAGIC_SHIELD);
            event.getRegistry().register(KUNAI_HIT);
            event.getRegistry().register(SMOKE);
            event.getRegistry().register(SHOCKWAVE);
            event.getRegistry().register(SUCC_HURT);
            event.getRegistry().register(SUCC_AMB);
            event.getRegistry().register(SUCC_SIGH);
            event.getRegistry().register(SUCC_SPELL);
            event.getRegistry().register(SUCC_DEATH);
            event.getRegistry().register(SUCC_SUCK);
            event.getRegistry().register(MOSQ_LOOP);
            event.getRegistry().register(MOSQ_GULP);
        }

    }
}
