package com.unoriginal.beastslayer.potions;

import net.minecraft.entity.SharedMonsterAttributes;

public class PotionFrenzy extends PotionBase{
    public PotionFrenzy(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.fire_frenzy");
        setIconIndex(0,2);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", 0.5D, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 20.0D, 0);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.3D, 2);

    }
}
