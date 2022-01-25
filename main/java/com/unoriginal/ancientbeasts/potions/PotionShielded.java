package com.unoriginal.ancientbeasts.potions;

public class PotionShielded extends PotionBase{
    public PotionShielded(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.shielded");
        setIconIndex(0,0);
    }
    @Override
    public boolean isReady(int duration, int amplifier) {return duration > 0;}
}
