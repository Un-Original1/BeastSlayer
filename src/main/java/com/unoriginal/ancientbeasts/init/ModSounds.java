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
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().register(TORNADO_AMBIENT);
            event.getRegistry().register(SANDMONSTER_AMBIENT);
            event.getRegistry().register(SANDMONSTER_HURT);
            event.getRegistry().register(SANDMONSTER_DEATH);

        }

    }
}
