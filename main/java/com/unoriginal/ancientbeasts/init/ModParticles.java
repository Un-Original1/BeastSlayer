package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.AncientBeasts;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;

public class ModParticles {

   public static EnumParticleTypes SAND_SPIT;
   public static EnumParticleTypes SHIELDED;
   public static EnumParticleTypes SHIELDED_EVIL;

   public static void init() {
        Class<?>[] particlesParams = {
                String.class, int.class, boolean.class
        };
       int id = EnumParticleTypes.values().length;

       SAND_SPIT = EnumHelper.addEnum(EnumParticleTypes.class, "SAND_SPIT", particlesParams, "sandSpit", id++, true);
       SHIELDED = EnumHelper.addEnum(EnumParticleTypes.class, "SHIELDED", particlesParams, "shielded", id++, true);
       SHIELDED_EVIL = EnumHelper.addEnum(EnumParticleTypes.class, "SHIELDED_EVIL", particlesParams, "shielded_evil", id++, true);

       EnumParticleTypes.PARTICLES.put(SAND_SPIT.getParticleID(), SAND_SPIT);
       EnumParticleTypes.PARTICLES.put(SHIELDED.getParticleID(), SHIELDED);
       EnumParticleTypes.PARTICLES.put(SHIELDED_EVIL.getParticleID(), SHIELDED_EVIL);

       EnumParticleTypes.BY_NAME.put(SAND_SPIT.getParticleName(), SAND_SPIT);
       EnumParticleTypes.BY_NAME.put(SHIELDED.getParticleName(), SHIELDED);
       EnumParticleTypes.BY_NAME.put(SHIELDED_EVIL.getParticleName(), SHIELDED_EVIL);

       AncientBeasts.commonProxy.registerParticles();
   }
}
