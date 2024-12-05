package com.unoriginal.beastslayer.entity.Entities.ai;

import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.EntityTribeChild;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class AIPlay extends EntityAIBase {
    private final EntityTribeChild villager;
    private EntityLivingBase targetVillager;
    private final double speed;
    private int playTime;

    public AIPlay(EntityTribeChild villagerIn, double speedIn)
    {
        this.villager = villagerIn;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {

        if (this.villager.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List<EntityTribeChild> list = this.villager.world.getEntitiesWithinAABB(EntityTribeChild.class, this.villager.getEntityBoundingBox().grow(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;

            for (EntityTribeChild entityvillager : list)
            {
                if (entityvillager != this.villager && !entityvillager.isPlaying())
                {
                    double d1 = entityvillager.getDistanceSq(this.villager);

                    if (d1 <= d0)
                    {
                        d0 = d1;
                        this.targetVillager = entityvillager;
                    }
                }
            }

            if (this.targetVillager == null)
            {
                Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.villager, 16, 3);

                if (vec3d == null)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return this.playTime > 0;
    }

    public void startExecuting()
    {
        if (this.targetVillager != null)
        {
            this.villager.setPlaying(true);
        }

        this.playTime = 1000;
    }

    public void resetTask()
    {
        this.villager.setPlaying(false);
        this.targetVillager = null;
    }

    public void updateTask()
    {
        --this.playTime;

        if (this.targetVillager != null)
        {
            if (this.villager.getDistanceSq(this.targetVillager) > 4.0D)
            {
                this.villager.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
            }
        }
        else if (this.villager.getNavigator().noPath())
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.villager, 16, 3);

            if (vec3d == null)
            {
                return;
            }

            this.villager.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, this.speed);
        }
    }
}
