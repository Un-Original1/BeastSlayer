package com.unoriginal.beastslayer.animation;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.ArrayUtils;

public enum EZAnimationHandler {

    INSTANCE;

    public<T extends Entity & IAnimatedEntity> void sendAnimationMessage(T entity, EZAnimation animation) {
        if(entity.world.isRemote) {
            return;
        }
        entity.setAnimation(animation);
        BeastSlayer.sendMSGToAll(new AnimationMessage(entity.getEntityId(), ArrayUtils.indexOf(entity.getAnimations(), animation)));
    }

    public<T extends Entity & IAnimatedEntity> void updateAnimations(T entity) {
        if(entity.getAnimation() == null) {
            entity.setAnimation(IAnimatedEntity.NO_ANIMATION);

        } else {
            if(entity.getAnimation() != IAnimatedEntity.NO_ANIMATION) {
                if(entity.getAnimationTick() == 0) {
                    EZAnimationEvent event = new EZAnimationEvent.Start<>(entity, entity.getAnimation());
                    if(!MinecraftForge.EVENT_BUS.post(event)) {
                        this.sendAnimationMessage(entity, entity.getAnimation());
                    }
                }
                if(entity.getAnimationTick() < entity.getAnimation().getDuration()) {
                    entity.setAnimationTick(entity.getAnimationTick() + 1);
                    MinecraftForge.EVENT_BUS.post(new EZAnimationEvent.Tick<>(entity, entity.getAnimation(), entity.getAnimationTick()));

                }
                if(entity.getAnimationTick() == entity.getAnimation().getDuration()) {
                    entity.setAnimationTick(0);
                    entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
                }
            }
        }
    }
}
