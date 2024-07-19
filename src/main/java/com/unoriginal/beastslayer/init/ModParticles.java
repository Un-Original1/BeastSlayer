package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;

public class ModParticles {

   public static EnumParticleTypes SAND_SPIT;
   public static EnumParticleTypes SHIELDED;
   public static EnumParticleTypes SHIELDED_EVIL;
   public static EnumParticleTypes RIFT;
   public static EnumParticleTypes WISPFLAME;
   public static EnumParticleTypes DRIP;

   public static void init() {
        Class<?>[] particlesParams = {
                String.class, int.class, boolean.class
        };
       int id = EnumParticleTypes.values().length;

       SAND_SPIT = EnumHelper.addEnum(EnumParticleTypes.class, "SAND_SPIT", particlesParams, "sandSpit", id++, true);
       SHIELDED = EnumHelper.addEnum(EnumParticleTypes.class, "SHIELDED", particlesParams, "shielded", id++, true);
       SHIELDED_EVIL = EnumHelper.addEnum(EnumParticleTypes.class, "SHIELDED_EVIL", particlesParams, "shielded_evil", id++, true);
       RIFT = EnumHelper.addEnum(EnumParticleTypes.class, "RIFT", particlesParams, "rift", id++, true);
       WISPFLAME = EnumHelper.addEnum(EnumParticleTypes.class, "WISPFLAME", particlesParams, "wisp_flame", id++, true);
       DRIP = EnumHelper.addEnum(EnumParticleTypes.class, "DRIP", particlesParams, "drip", id++, true);

       EnumParticleTypes.PARTICLES.put(SAND_SPIT.getParticleID(), SAND_SPIT);
       EnumParticleTypes.PARTICLES.put(SHIELDED.getParticleID(), SHIELDED);
       EnumParticleTypes.PARTICLES.put(SHIELDED_EVIL.getParticleID(), SHIELDED_EVIL);
       EnumParticleTypes.PARTICLES.put(RIFT.getParticleID(), RIFT);
       EnumParticleTypes.PARTICLES.put(WISPFLAME.getParticleID(), WISPFLAME);
       EnumParticleTypes.PARTICLES.put(DRIP.getParticleID(), DRIP);

       EnumParticleTypes.BY_NAME.put(SAND_SPIT.getParticleName(), SAND_SPIT);
       EnumParticleTypes.BY_NAME.put(SHIELDED.getParticleName(), SHIELDED);
       EnumParticleTypes.BY_NAME.put(SHIELDED_EVIL.getParticleName(), SHIELDED_EVIL);
       EnumParticleTypes.BY_NAME.put(RIFT.getParticleName(), RIFT);
       EnumParticleTypes.BY_NAME.put(WISPFLAME.getParticleName(), WISPFLAME);
       EnumParticleTypes.BY_NAME.put(DRIP.getParticleName(), DRIP);

       BeastSlayer.commonProxy.registerParticles();
   }
}
