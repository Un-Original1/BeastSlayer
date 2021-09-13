package com.unoriginal.ancientbeasts.potions;

import com.unoriginal.ancientbeasts.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionGhostly extends PotionBase{

    public PotionGhostly(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.ghostly");
        setIconIndex(2,0);
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
               if(!player.getEntityWorld().isRemote) {
                   player.capabilities.allowFlying = true;
                   player.capabilities.isFlying = true;
                   player.capabilities.disableDamage = true;
                   player.sendPlayerAbilities();
              }
               player.noClip=true;
               player.setInvisible(true);
           }
        }
    }
}
