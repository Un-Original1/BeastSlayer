package com.unoriginal.beastslayer.entity.Entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.world.World;

public class EntityAIFleeRain extends EntityAIFleeSun {
    private final World world;
    private final EntityCreature creature;

    public EntityAIFleeRain(EntityCreature theCreatureIn, double movementSpeedIn) {
        super(theCreatureIn, movementSpeedIn);
        this.creature = theCreatureIn;
        this.world = theCreatureIn.world;
    }

    public boolean shouldExecute() {
        if (!this.world.isRaining() && this.world.getBiome(creature.getPosition()).canRain()) {
            return false;
        } else {
            return super.shouldExecute();
        }
    }
}
