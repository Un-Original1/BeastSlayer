package com.unoriginal.beastslayer.potions;

import net.minecraft.entity.SharedMonsterAttributes;

public class PotionUndead extends PotionBase{

    public PotionUndead(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.undead");
        setIconIndex(1,2);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.3D, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, 0);

    }

}
