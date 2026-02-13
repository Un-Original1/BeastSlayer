package com.unoriginal.beastslayer.potions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionTarget extends PotionBase {

    public PotionTarget(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("potion.target");
        setIconIndex(2,1);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        if(entityLivingBaseIn instanceof EntityLiving){
            EntityLiving entityLiving = (EntityLiving)entityLivingBaseIn;
            entityLiving.setAttackTarget(null);
        }
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);

    }
}
