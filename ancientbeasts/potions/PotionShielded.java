package com.unoriginal.ancientbeasts.potions;

import com.unoriginal.ancientbeasts.init.ModParticles;
import com.unoriginal.ancientbeasts.init.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionShielded extends PotionBase{
    public PotionShielded(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.shielded");
        setIconIndex(0,0);
    }
    @Override
    public boolean isReady(int duration, int amplifier) {return true;}

    @Override
    @SideOnly(Side.CLIENT)
    public void performEffect(EntityLivingBase e, int amplifier){
        Minecraft mc = Minecraft.getMinecraft();
        if(e.isPotionActive(ModPotions.SHIELDED) && mc.world.isRemote) {
           if (e instanceof EntityMob) {
                EntityMob mob = (EntityMob)e;
                mc.effectRenderer.spawnEffectParticle(ModParticles.SHIELDED_EVIL.getParticleID(), mob.posX, mob.posY + mob.height / 2, mob.posZ, 0D, 0D, 0D);
            } else {
                mc.effectRenderer.spawnEffectParticle(ModParticles.SHIELDED.getParticleID(), e.posX, e.posY + e.height / 2, e.posZ, 0D, 0D, 0D);
            }
        }
    }
}
