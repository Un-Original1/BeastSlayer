package com.unoriginal.beastslayer.potions;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionTarget extends PotionBase {

    public PotionTarget(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.target");
        setIconIndex(2,1);
    }

}
