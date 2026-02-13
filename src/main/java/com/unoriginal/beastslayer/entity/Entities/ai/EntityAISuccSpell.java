package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class EntityAISuccSpell extends EntityAIBase {
    EntitySucc tameable;
    EntityLivingBase attacker;
    private int timestamp;

    public EntityAISuccSpell(EntitySucc theEntityTameableIn)
    {
        super();
        this.tameable = theEntityTameableIn;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        if(this.tameable == null){
            return false;
        }
        if(this.tameable.isSitting()){
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.tameable.getFriend();
            if(this.tameable.getSpellCooldown() > 0 ){
                return false;
            }

            if (entitylivingbase == null)
            {
                if(this.tameable.isFriendly()){

                    return false;
                } else {
                    if(this.tameable.getAttackTarget() != null && !this.tameable.isFriendly() && !this.tameable.isStalking()){
                        this.attacker = this.tameable.getAttackTarget();
                        return this.attacker.isEntityAlive();
                    }
                }
                return false;
            }

            else
            {
                this.attacker = entitylivingbase.getLastAttackedEntity();
                int i = entitylivingbase.getLastAttackedEntityTime();
                if(this.attacker != null && this.attacker.isEntityAlive()) {
                    return i != this.timestamp && this.attacker.isEntityAlive();
                } else {
                    return false;
                }
            }
        }
    }

    public void startExecuting()
    {
        this.tameable.setSpellTimeId(80, 10);
        this.tameable.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 10, false, false));
        EntityLivingBase entitylivingbase = this.tameable.getFriend();

        if (entitylivingbase != null) {

            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
            // this.attacker.addPotionEffect(new PotionEffect(ModPotions.TARGETED, 400));
        }
        this.tameable.setSpellCooldown(1200);

        super.startExecuting();
    }

    public void updateTask(){
        if(this.attacker != null && this.attacker.isEntityAlive()) {
            this.tameable.getLookHelper().setLookPositionWithEntity(this.attacker, this.tameable.getHorizontalFaceSpeed(), this.tameable.getVerticalFaceSpeed());
        }
        this.tameable.getNavigator().clearPath();

        super.updateTask();
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(!this.attacker.isEntityAlive() || this.attacker == null){
            return false;
        }
        if(super.shouldContinueExecuting() && this.tameable.getSpellTime() > 0){
            return true;
        } else {
            if(!attacker.isPotionActive(ModPotions.TARGETED)) {
                this.attacker.addPotionEffect(new PotionEffect(ModPotions.TARGETED, 400));
            }
            return false;
        }

    }

    public void resetTask()
    {
        super.resetTask();
    }
}
