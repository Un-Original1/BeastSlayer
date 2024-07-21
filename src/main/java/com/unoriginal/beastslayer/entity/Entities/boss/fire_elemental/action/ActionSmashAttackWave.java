package com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.action;

import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.IActionBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.misc.EntityMoveTile;
import com.unoriginal.beastslayer.entity.Entities.boss.util.BossUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ActionSmashAttackWave implements IActionBoss {
    private final int lengthOfWave;
    public ActionSmashAttackWave(int lengthOfWave) {
        this.lengthOfWave = lengthOfWave;
    }
    @Override
    public void performAction(EntityAbstractBoss actor, EntityLivingBase target) {
        //this keeps the origin of the wave to this point even if the boss moves after the animation ends
        Vec3d savedPos = actor.getPositionVector();
        //Now we need to make this in a loop relative
        for(int t = 1; t < lengthOfWave; t++ ) {
            int finalT = t;
            actor.addEvent(()-> {
                BossUtil.circleCallback(finalT, (4 * finalT), (pos) -> {
                    pos = new Vec3d(pos.x, 0, pos.y).add(savedPos);
                    EntityMoveTile tile = new EntityMoveTile(actor.world, actor);
                    tile.setPosition(pos.x, pos.y, pos.z);
                    int y = getSurfaceHeight(actor.world, new BlockPos(pos.x, 0, pos.z), (int) actor.posY - 8, (int) actor.posY + 4);
                    BlockPos posToo = new BlockPos(pos.x, y, pos.z);
                    tile.setOrigin(posToo, 5, posToo.getX() + 0.5D, posToo.getZ() + 0.5D);
                    tile.setLocationAndAngles(posToo.getX() + 0.5D, posToo.getY(), posToo.getZ() + 0.5D, 0.0f, 0.0F);
                    tile.setBlock(Blocks.MAGMA, 0);
                    actor.world.spawnEntity(tile);

                });
            }, 5 * t);
        }
    }


    private int getSurfaceHeight(World world, BlockPos pos, int min, int max)
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


