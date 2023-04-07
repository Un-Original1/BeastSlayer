package com.unoriginal.beastslayer.entity.Entities.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EntitySelectors;

public class EntityAIMeleeConditional extends EntityAIAttackMelee {

    protected final Predicate <? super EntityLivingBase > conditional;
    public EntityAIMeleeConditional(EntityCreature creature, double speedIn, boolean useLongMemory, Predicate<? super EntityLivingBase > conditional) {
        super(creature, speedIn, useLongMemory);
      //  this.conditional = conditional;
        this.conditional = (Predicate<EntityLivingBase>) p_apply_1_ -> {
            if (p_apply_1_ == null)
            {
                return false;
            }
            else if (conditional != null && !conditional.apply(p_apply_1_))
            {
                return false;
            }
            else
            {
                return EntitySelectors.NOT_SPECTATING.apply(p_apply_1_);
            }
        };

    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && conditional.apply(this.attacker.getAttackTarget());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && conditional.apply(this.attacker.getAttackTarget());
    }
}
