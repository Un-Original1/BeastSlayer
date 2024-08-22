package com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.action;

import com.sun.javafx.css.parser.LadderConverter;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.IActionBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.projectile.ProjectileMeteor;
import com.unoriginal.beastslayer.util.ModRand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class ActionMeteorShower implements IActionBoss {
    private int showerLength;
    private int distance;

    private int waves;

    public ActionMeteorShower(int showerLength, int distanceRand, int waves) {
        this.showerLength = showerLength * 20;
        this.distance = distanceRand;
        this.waves = waves;
    }
    @Override
    public void performAction(EntityAbstractBoss actor, EntityLivingBase target) {

        for(int i = 0; i < showerLength; i+=5) {
            actor.addEvent(()-> {
                ProjectileMeteor proj = new ProjectileMeteor(actor.world, actor, 7.0f, waves);
                Vec3d randPos = new Vec3d(actor.posX + ModRand.range(-distance, distance), actor.posY + 10, actor.posZ + ModRand.range(-distance, distance));
                proj.setPosition(randPos.x, randPos.y, randPos.z);
                proj.setVelocity(0, -0.5, 0);
                actor.world.spawnEntity(proj);
            }, i);
        }
    }
}
