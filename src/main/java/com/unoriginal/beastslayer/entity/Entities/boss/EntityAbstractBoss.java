package com.unoriginal.beastslayer.entity.Entities.boss;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import java.util.PriorityQueue;
import java.util.function.Consumer;

public abstract class EntityAbstractBoss extends EntityMob {


    //the basis of all DataParameter booleans usually ties in with a lot of things
    private static final DataParameter<Boolean> FIGHT_MODE = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> IMMOVABLE = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);

    //This is just a boolean for locking the look of the boss, the Constructor as an example
    public boolean lockLook = false;

    //Basis for Timed Events, you can see the class at the bottom
    private PriorityQueue<TimedEvent> events = new PriorityQueue<TimedEvent>();
    public EntityAbstractBoss(World worldIn) {
        super(worldIn);
    }



    @Override
    public void entityInit() {
        this.dataManager.register(FIGHT_MODE, Boolean.valueOf(false));
        this.dataManager.register(IMMOVABLE, Boolean.valueOf(false));
        super.entityInit();
    }


    protected boolean isImmovable() {
        return this.dataManager == null ? false : this.dataManager.get(IMMOVABLE);
    }

    protected void setImmovable(boolean immovable) {
        this.dataManager.set(IMMOVABLE, immovable);
    }
    public boolean isFightMode() {return this.dataManager.get(FIGHT_MODE);}
    public void setFightMode(boolean value) {this.dataManager.set(FIGHT_MODE, Boolean.valueOf(value));}
    @Override
    public void onUpdate() {
        super.onUpdate();

        //What the lockLook boolean does, used in the bosses AI to halt it from looking around while doing an attack
        if(this.lockLook) {
            this.rotationPitch = this.prevRotationPitch;
            this.rotationYaw = this.prevRotationYaw;
            this.rotationYawHead = this.prevRotationYawHead;
            this.renderYawOffset = this.rotationYaw;
        }


        if (!isDead && this.getHealth() > 0) {
            boolean foundEvent = true;
            while (foundEvent) {
                TimedEvent event = events.peek();
                if (event != null && event.ticks <= this.ticksExisted) {
                    events.remove();
                    event.callback.run();
                } else {
                    foundEvent = false;
                }
            }
        }

    }

    /**
     * Adds an event to be executed at a later time. Negative ticks are executed immediately.
     *
     * @param runnable
     * @param ticksFromNow
     */
    public void addEvent(Runnable runnable, int ticksFromNow) {
        events.add(new TimedEvent(runnable, this.ticksExisted + ticksFromNow));
    }

    public static class TimedEvent implements Comparable<TimedEvent> {
        Runnable callback;
        int ticks;

        public TimedEvent(Runnable callback, int ticks) {
            this.callback = callback;
            this.ticks = ticks;
        }

        @Override
        public int compareTo(TimedEvent event) {
            return event.ticks < ticks ? 1 : -1;
        }
    }

    @Override
    public void move(MoverType type, double x, double y, double z) {
        if(!this.isImmovable()) {
            super.move(type, x, y, z);
        }
    }

    //You can use this to put whatever basis of things, such as if you wanted the scaling system in
}
