package com.unoriginal.beastslayer.entity.Entities.boss.projectile;

import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.misc.EntityMoveTile;
import com.unoriginal.beastslayer.entity.Entities.boss.util.BossUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.PriorityQueue;

public class ProjectileMeteor extends Projectile {
    private int waves;

    private PriorityQueue<EntityAbstractBoss.TimedEvent> events = new PriorityQueue<EntityAbstractBoss.TimedEvent>();

    public ProjectileMeteor(World worldIn) {
        super(worldIn);
    }


    public ProjectileMeteor(World worldIn, EntityLivingBase throwerIn, float damage, int waves) {
        super(worldIn, throwerIn, damage);
        this.waves = waves;
    }

    public void addEvent(Runnable runnable, int ticksFromNow) {
        events.add(new EntityAbstractBoss.TimedEvent(runnable, this.ticksExisted + ticksFromNow));
    }

    @Override
    protected void onHit(RayTraceResult result) {
        Vec3d savedPos = this.getPositionVector();
        for(int t = 1; t < waves; t++ ) {
            int finalT = t;
            this.addEvent(()-> {
                BossUtil.circleCallback(finalT, (4 * finalT), (pos) -> {
                    pos = new Vec3d(pos.x, 0, pos.y).add(savedPos);
                    EntityMoveTile tile = new EntityMoveTile(this.world, this);
                    tile.setPosition(pos.x, pos.y, pos.z);
                    int y = getSurfaceHeight(this.world, new BlockPos(pos.x, 0, pos.z), (int) this.posY - 8, (int) this.posY + 2);
                    BlockPos posToo = new BlockPos(pos.x, y, pos.z);
                    tile.setOrigin(posToo, 5, posToo.getX() + 0.5D, posToo.getZ() + 0.5D);
                    tile.setLocationAndAngles(posToo.getX() + 0.5D, posToo.getY(), posToo.getZ() + 0.5D, 0.0f, 0.0F);
                    tile.setBlock(Blocks.MAGMA, 0);
                    this.world.spawnEntity(tile);

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
