package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.EntityNetherhound;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIRamAtTarget extends EntityAIBase
{
    EntityLiving rammer;
    EntityLivingBase ramTarget;
    protected int ramWarmup;
    protected int ramCD;

    public EntityAIRamAtTarget(EntityLiving rammingEntity)
    {
        this.rammer = rammingEntity;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        this.ramTarget = this.rammer.getAttackTarget();

        if (this.ramTarget == null)
        {
            return false;
        }
        else
        {
            double d0 = this.rammer.getDistanceSq(this.ramTarget);

            if (d0 >= 8.0D && d0 <= 32.0D)
            {
                if (!this.rammer.onGround)
                {
                    return false;
                }
                else
                {
                    return this.rammer.getRNG().nextInt(8) == 0 && this.rammer.ticksExisted >= this.ramCD;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        return this.rammer.onGround && this.ramWarmup >= -0;
    }

    public void startExecuting() {

        this.ramWarmup = MathHelper.abs(20 + this.rammer.getRNG().nextInt(40)); //just in case...
        this.ramCD = 200 + this.rammer.ticksExisted + this.rammer.getRNG().nextInt(200);
        this.rammer.setSprinting(true);

    }
    public void updateTask()
    {
        --this.ramWarmup;

        if (this.ramWarmup > 0) {
            this.rammer.motionX *= 0F;
            this.rammer.motionZ *= 0F;
        } else {
            double d0 = this.ramTarget.posX - this.rammer.posX;
            double d1 = this.ramTarget.posZ - this.rammer.posZ;
            float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
            if(this.rammer instanceof EntityNetherhound && !this.rammer.world.isRemote){
                rammer.playSound(ModSounds.NETHERHOUND_CHARGE, 1.0F, 1.0F);
            }

            if ((double) f >= 1.0E-4D) {
                this.rammer.motionX += (d0 + this.rammer.motionX) * 0.4;
                this.rammer.motionZ += (d1 + this.rammer.motionZ) * 0.4;
            }
        }

    }
    public void resetTask(){
        this.rammer.setSprinting(false);
        super.resetTask();
    }
}
