package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.AncientBeasts;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;



public class ModParticles {

   public static EnumParticleTypes SAND_SPIT;

   public static void init() {
        Class<?>[] particlesParams = {
                String.class, int.class, boolean.class
        };
       int id = EnumParticleTypes.values().length;

       SAND_SPIT = EnumHelper.addEnum(EnumParticleTypes.class, "SAND_SPIT", particlesParams, "sandSpit", id++, true);

       EnumParticleTypes.PARTICLES.put(SAND_SPIT.getParticleID(), SAND_SPIT);

       EnumParticleTypes.BY_NAME.put(SAND_SPIT.getParticleName(), SAND_SPIT);

       AncientBeasts.commonProxy.registerParticles();
   }
}
