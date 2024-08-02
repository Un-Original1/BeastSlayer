package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.worldGen.JungleVillageWorldGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemTest extends Item {
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        ItemStack itemstack = player.getHeldItem(hand);
        pos = pos.offset(facing);

        if (world.isAirBlock(pos))
        {
            if (!world.isRemote) {
                if (!player.capabilities.isCreativeMode) {
                    itemstack.damageItem(1, player);
                    itemstack.shrink(1);
                }
                RayTraceResult raytraceresult = this.rayTrace(world, player, true);

                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    if (world.isBlockModifiable(player, blockpos) && player.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
                        JungleVillageWorldGen worldGen = new JungleVillageWorldGen();
                        worldGen.generateBypass(world, itemRand, pos);
                    }
                    player.swingArm(hand);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

}
