package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EntityAIRestrictRain extends EntityAIRestrictSun {
    private final EntityCreature entity;
    public EntityAIRestrictRain(EntityCreature creature) {
        super(creature);
        this.entity = creature;
    }
    @Override
    public boolean shouldExecute()
    {
        return this.entity.world.isRaining() && this.entity.world.getBiome(this.entity.getPosition()).canRain();
    }
}
