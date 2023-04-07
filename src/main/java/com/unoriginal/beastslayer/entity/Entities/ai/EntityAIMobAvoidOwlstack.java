package com.unoriginal.beastslayer.entity.Entities.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class EntityAIMobAvoidOwlstack<T extends Entity> extends EntityAIBase {
    private final Predicate<Entity> canBeSeenSelector;
    protected EntityCreature entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T closestLivingEntity;
    private final float avoidDistance;
    private Path path;
    private final PathNavigate navigation;
    private final Class<T> classToAvoid;
    private final Predicate<? super T> avoidTargetSelector;

    public EntityAIMobAvoidOwlstack(EntityCreature entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        this(entityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);
    }

    public EntityAIMobAvoidOwlstack(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        this.canBeSeenSelector = p_apply_1_ -> p_apply_1_.isEntityAlive() && EntityAIMobAvoidOwlstack.this.entity.getEntitySenses().canSee(p_apply_1_) && !EntityAIMobAvoidOwlstack.this.entity.isOnSameTeam(p_apply_1_);
        this.entity = entityIn;
        this.classToAvoid = classToAvoidIn;
        this.avoidTargetSelector = avoidTargetSelectorIn;
        this.avoidDistance = avoidDistanceIn;
        this.farSpeed = farSpeedIn;
        this.nearSpeed = nearSpeedIn;
        this.navigation = entityIn.getNavigator();
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        List blacklist = Arrays.asList(BeastSlayerConfig.owlstack_blacklist);
        if(!entity.isNonBoss()){
            return false;
        }
        if (entity instanceof AbstractTribesmen){
            AbstractTribesmen tribesmen = (AbstractTribesmen) entity;
            if(tribesmen.isFiery()){
                return false;
            }
        }
        if(BeastSlayerConfig.owlstack_affects_undead) {
            if (!entity.isEntityUndead()) {
                return false;
            }
        }
        List<T> list = this.entity.world.getEntitiesWithinAABB(this.classToAvoid, this.entity.getEntityBoundingBox().grow(this.avoidDistance, 3.0D, this.avoidDistance), Predicates.and(EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector, this.avoidTargetSelector));
        List<T> list1 = this.entity.world.getEntitiesWithinAABB(this.classToAvoid, this.entity.getEntityBoundingBox().grow(this.avoidDistance * list.size() + 1, 3.0D, this.avoidDistance * list.size() + 1), Predicates.and(EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector, this.avoidTargetSelector));
        if (list1.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list1.get(0);
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

            if (vec3d == null) {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) < this.closestLivingEntity.getDistanceSq(this.entity)) {
                return false;
            } else {
                this.path = this.navigation.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
                return this.path != null && !blacklist.contains(EntityList.getKey(entity).toString());
            }
        }
    }

    public boolean shouldContinueExecuting() {
        return !this.navigation.noPath();
    }

    public void startExecuting() {
        this.navigation.setPath(this.path, this.farSpeed);
    }

    public void resetTask() {
        this.closestLivingEntity = null;
    }

    public void updateTask() {
        if (this.entity.getDistanceSq(this.closestLivingEntity) < 49.0D) {
            this.entity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
