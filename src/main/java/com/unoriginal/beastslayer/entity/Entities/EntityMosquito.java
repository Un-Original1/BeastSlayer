package com.unoriginal.beastslayer.entity.Entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityMosquito extends EntityMob {

    public EntityMosquito(World p_i1738_1_) {
        super(p_i1738_1_);
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((16.0D));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
}
