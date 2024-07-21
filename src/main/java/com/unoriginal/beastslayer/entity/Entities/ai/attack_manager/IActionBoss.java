package com.unoriginal.beastslayer.entity.Entities.ai.attack_manager;

import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import net.minecraft.entity.EntityLivingBase;

public interface IActionBoss {

    void performAction(EntityAbstractBoss actor, EntityLivingBase target);

    IActionBoss NONE = (actor, target) -> {};
}
