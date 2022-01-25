package com.unoriginal.ancientbeasts.potions;

public class PotionPossessed extends PotionBase{
    public PotionPossessed(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.possessed");
        setIconIndex(1,0);
    }
}
