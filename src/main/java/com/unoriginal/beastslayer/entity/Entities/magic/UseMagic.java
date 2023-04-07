package com.unoriginal.beastslayer.entity.Entities.magic;

import com.unoriginal.beastslayer.entity.Entities.EntitySandy;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public abstract class UseMagic <T extends EntityLiving & IMagicUser> extends EntityAIBase {
    protected int magicWarmup;
    protected int magicCooldown;
    private final T hostMobEntity;

    protected UseMagic(T magicUserMob) {
        this.hostMobEntity = magicUserMob;
    }

    public boolean shouldExecute() {
        EntityLivingBase livingentity = this.hostMobEntity.getAttackTarget();
        if (livingentity != null && livingentity.isEntityAlive()) {
            if (this.hostMobEntity.isUsingMagic()) {
                return false;
            }
            if(this.hostMobEntity instanceof EntitySandy)
            {
                EntitySandy sandy = (EntitySandy) this.hostMobEntity;
                if(sandy.isBuried() || sandy.isSitting()) {
                    return false;
                }
            }
            if (this.hostMobEntity.isUsingMagic()) {
                return false;
            } else {
                return this.hostMobEntity.ticksExisted >= this.magicCooldown;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        EntityLivingBase livingentity = this.hostMobEntity.getAttackTarget();
        return livingentity != null && livingentity.isEntityAlive() && this.magicWarmup > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.magicWarmup = this.getMagicWarmupTime();
        this.hostMobEntity.setMagicUseTicks(this.getMagicUseTime());
        this.magicCooldown = this.hostMobEntity.ticksExisted + this.getMagicUseInterval();
        SoundEvent soundevent = this.getMagicPrepareSound();

        if (soundevent != null) {
            this.hostMobEntity.playSound(soundevent, 1.0F, 1.0F);
        }

        this.hostMobEntity.setMagicType(this.getMagicType());
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        --this.magicWarmup;
        if (this.magicWarmup == 0) {
            this.useMagic();
        }
    }

    protected abstract void useMagic();

    protected int getMagicWarmupTime() {
        return 20;
    }

    protected abstract int getMagicUseTime();

    protected abstract int getMagicUseInterval();

    @Nullable
    protected abstract SoundEvent getMagicPrepareSound();

    protected abstract MagicType getMagicType();
}
