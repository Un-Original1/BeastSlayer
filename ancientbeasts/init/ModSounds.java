package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.AncientBeasts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModSounds {
    public static final SoundEvent TORNADO_AMBIENT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "tornado_ambient")).setRegistryName("tornado_ambient");
    public static final SoundEvent SANDMONSTER_AMBIENT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "sandmonster_ambient")).setRegistryName("sandmonster_ambient");
    public static final SoundEvent SANDMONSTER_HURT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "sandmonster_hurt")).setRegistryName("sandmonster_hurt");
    public static final SoundEvent SANDMONSTER_DEATH = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "sandmonster_death")).setRegistryName("sandmonster_death");
    public static final SoundEvent ZEALOT_AMBIENT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "zealot_ambient")).setRegistryName("zealot_ambient");
    public static final SoundEvent ZEALOT_HURT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "zealot_hurt")).setRegistryName("zealot_hurt");
    public static final SoundEvent ZEALOT_DEATH = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "zealot_death")).setRegistryName("zealot_death");
    public static final SoundEvent GHOST_AMBIENT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "ghost_ambient")).setRegistryName("ghost_ambient");
    public static final SoundEvent GHOST_HURT = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "ghost_hurt")).setRegistryName("ghost_hurt");
    public static final SoundEvent GHOST_DEATH = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "ghost_death")).setRegistryName("ghost_death");
    public static final SoundEvent GHOST_POSSESS = new SoundEvent(new ResourceLocation(AncientBeasts.MODID, "ghost_possess")).setRegistryName("ghost_possess");
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
        }

    }
}
