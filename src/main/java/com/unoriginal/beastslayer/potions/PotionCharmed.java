package com.unoriginal.beastslayer.potions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionCharmed extends PotionBase {

         public PotionCharmed(boolean isBadEffectIn, int liquidColorIn) {
             super(isBadEffectIn, liquidColorIn);
             setPotionName("potion.charmed");
             setIconIndex(1, 2);
             registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.01D, 2);
         }
}
