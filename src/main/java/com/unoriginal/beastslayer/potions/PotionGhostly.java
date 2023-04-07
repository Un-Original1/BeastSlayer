package com.unoriginal.beastslayer.potions;

import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionGhostly extends PotionBase{

    public PotionGhostly(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.ghostly");
        setIconIndex(1,1);
    }
    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public void performEffect(EntityLivingBase e, int amplifier){
        if(e.isPotionActive(ModPotions.GHOSTLY)) {
           if(e instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer) e;
               if (player.getActivePotionEffect(ModPotions.GHOSTLY).getDuration() > 1) {
                   if (!player.getEntityWorld().isRemote) {
                       player.capabilities.allowFlying = true;
                       player.capabilities.isFlying = true;
                       player.capabilities.disableDamage = true;
                       player.sendPlayerAbilities();
                   }
                   player.noClip = true;
                   player.setInvisible(true);
               }
               else {
                   if(!player.getEntityWorld().isRemote) {
                       player.setInvisible(false);
                       if(!player.capabilities.isCreativeMode && !player.isSpectator()) {
                           player.capabilities.allowFlying = false;
                           player.capabilities.isFlying = false;
                           player.capabilities.disableDamage = false;
                       }
                       player.sendPlayerAbilities();
                   }
                   if(!player.isSpectator()) {
                       player.noClip = false;
                   }
               }
           }
        }
    }
}
