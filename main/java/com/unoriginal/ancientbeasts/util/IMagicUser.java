package com.unoriginal.ancientbeasts.util;

import com.unoriginal.ancientbeasts.entity.Entities.EntitySandy;
import com.unoriginal.ancientbeasts.entity.Entities.magic.MagicType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public interface IMagicUser
{

    static <T extends EntityLivingBase & IMagicUser> void spawnMagicParticles(T magicUser) {
        if (magicUser.world.isRemote && magicUser.isUsingMagic()) {
            MagicType magicType = magicUser.getMagicType();
            double d0 = magicType.particleSpeed()[0];
            double d1 = magicType.particleSpeed()[1];
            double d2 = magicType.particleSpeed()[2];
            float f = magicUser.renderYawOffset * ((float)Math.PI / 180F) + MathHelper.cos((float)magicUser.ticksExisted * 0.6662F) * 0.25F;
            float f1 = MathHelper.cos(f);
            float f2 = MathHelper.sin(f);
            //not the best solution but it works
            if(magicUser instanceof EntitySandy){
                magicUser.world.spawnParticle(EnumParticleTypes.SPELL_MOB, magicUser.posX  + (double) f1, magicUser.posY + 3D, magicUser.posZ + (double) f2, d0, d1, d2);
                magicUser.world.spawnParticle(EnumParticleTypes.SPELL_MOB, magicUser.posX  - (double) f1, magicUser.posY + 3D, magicUser.posZ - (double) f2, d0, d1, d2);
            }
            else {
                magicUser.world.spawnParticle(EnumParticleTypes.SPELL_MOB, magicUser.posX + (double) f1 * 0.6D, magicUser.posY + 1.8D, magicUser.posZ + (double) f2 * 0.6D, d0, d1, d2);
                magicUser.world.spawnParticle(EnumParticleTypes.SPELL_MOB, magicUser.posX - (double) f1 * 0.6D, magicUser.posY + 1.8D, magicUser.posZ - (double) f2 * 0.6D, d0, d1, d2);
            }
        }
    }

    boolean isUsingMagic();

    int getMagicUseTicks();

    void setMagicUseTicks(int magicUseTicks);

    MagicType getMagicType();

    void setMagicType(MagicType spellTypeIn);

    SoundEvent getMagicSound();
}
