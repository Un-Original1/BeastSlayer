package com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.action;

import com.unoriginal.beastslayer.entity.Entities.*;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.IActionBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.util.BossUtil;
import com.unoriginal.beastslayer.util.ModRand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.Sys;

/**
 * The Action system making it easier to condese down the Entity Class
 */
public class ActionSummonMinions implements IActionBoss {
    @Override
    public void performAction(EntityAbstractBoss actor, EntityLivingBase target) {
        for(int i = 0; i <= 20; i+=10) {
            //an add event that summons creatures every half second to a max of 3
            //Also need some randomness to which minion gets summoned

            actor.addEvent(()-> {
                int r = ModRand.range(1, 5); //rel val 1-4

                AbstractTribesmen minion;
                if(r == 1) {
                    minion = new EntityTribeWarrior(actor.world);
                } else if(r == 2) {
                    minion = new EntityPriest(actor.world);
                } else if(r == 3) {
                    minion = new EntityHunter(actor.world);
                } else {
                    minion = new EntityTank(actor.world);
                }

                Vec3d randomPosTooSpawn = actor.getPositionVector().add(BossUtil.getRelativeOffset(actor, new Vec3d(ModRand.range(-10, 10), 0, ModRand.range(-10, 10))));
                minion.setFire(10);
                //function to find ground
                int y = getSurfaceHeight(actor.world, new BlockPos(randomPosTooSpawn.x, 0, randomPosTooSpawn.z), (int) actor.posY - 8, (int) actor.posY + 10);
                //basically saying if theres no ground that's acceptable within bound it will instead spawn them right on the boss pos
                if(y > 0) {
                    //Spawns minion at random loc within 10 blocks
                    minion.setPosition(randomPosTooSpawn.x, y + 1, randomPosTooSpawn.z);
                } else {
                    //fails to find a prevelant y height, spawns minion at bosses cords
                    minion.setPosition(actor.posX, actor.posY + 1, actor.posZ);
                }
                //spawns Entity
                actor.world.spawnEntity(minion);
                minion.setFire(10);


                //play a sound for summoning each minion
            }, i);
        }
    }



    public int getSurfaceHeight(World world, BlockPos pos, int min, int max)
    {
        int currentY = max;

        while(currentY >= min)
        {
            if(!world.isAirBlock(pos.add(0, currentY, 0)) && !world.isRemote) {
                    return currentY;
            }

            currentY--;
        }

        return 0;
    }
}
