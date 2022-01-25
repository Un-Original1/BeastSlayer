package com.unoriginal.ancientbeasts.blocks.tile;

import com.unoriginal.ancientbeasts.blocks.BlockMovingLight;
import com.unoriginal.ancientbeasts.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityMovingLight extends TileEntity implements ITickable
{
    public EntityLivingBase theEntityLiving;
    protected boolean shouldDie = false;
    protected int deathTimer = 20; //should last a tick dummy jabelar

    public TileEntityMovingLight()
    {
    }
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return (oldState.getBlock() != newSate.getBlock());
    }

    @Override
    public void update()
    {
        if (shouldDie)
        {
            if (deathTimer > 0)
            {
                deathTimer--;
                return;
            }
            else
            {
                world.setBlockToAir(getPos());
            }
        }

        Block blockAtLocation = world.getBlockState(getPos()).getBlock();

        if (theEntityLiving == null)
        {
            if (blockAtLocation instanceof BlockMovingLight)
            {
                shouldDie = true;
            }
            return;
        }

        if (theEntityLiving.isDead)
        {
            if (blockAtLocation instanceof BlockMovingLight)
            {
                shouldDie = true;
            }
            return;
        }

        double distanceSquared = getDistanceSq(theEntityLiving.posX, theEntityLiving.posY, theEntityLiving.posZ);
        if (distanceSquared > 5.0D)
        {
            if (blockAtLocation instanceof BlockMovingLight)
            {
                shouldDie = true;
            }
        }
        if (! theEntityLiving.isBurning())
        {
            theEntityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
            if (theEntityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.MINER_HELMET)
            {
                if (world.getBlockState(getPos()).getBlock() instanceof BlockMovingLight)
                {
                    shouldDie = true;
                }
            }
        }
    }

    public void setEntityLiving(EntityLivingBase parEntityLiving)
    {
        theEntityLiving = parEntityLiving;
    }

    public EntityLivingBase getEntityLiving()
    {
        return theEntityLiving;
    }


    @Override
    public void setPos(BlockPos posIn)
    {
        pos = posIn.toImmutable();
        setEntityLiving(getClosestEntityLiving(world, pos, 2.0D));
    }
    public static EntityLivingBase getClosestEntityLiving(World parWorld, BlockPos parPos, double parMaxDistance)
    {
        if (parMaxDistance <= 0.0D)
        {
            return null;
        }

        EntityLivingBase closestLiving = null;
        double distanceSq = parMaxDistance*parMaxDistance;
        AxisAlignedBB aabb = new AxisAlignedBB(parPos.getX()-parMaxDistance, parPos.getY()-parMaxDistance, parPos.getZ()-parMaxDistance, parPos.getX()+parMaxDistance, parPos.getY()+parMaxDistance, parPos.getZ()+parMaxDistance);
        List<EntityLivingBase> listEntitiesInRange = parWorld.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
        for (EntityLivingBase next : listEntitiesInRange) {
            if (getDistanceSq(next.getPosition(), parPos) < distanceSq) {
                closestLiving = next;
            }
        }
        return closestLiving;
    }

    protected static double getDistanceSq(BlockPos parPos1, BlockPos parPos2)
    {
        return (  (parPos1.getX()-parPos2.getX())*(parPos1.getX()-parPos2.getX()) + (parPos1.getY()-parPos2.getY())*(parPos1.getY()-parPos2.getY()) + (parPos1.getZ()-parPos2.getZ())*(parPos1.getZ()-parPos2.getZ()));
    }
}
