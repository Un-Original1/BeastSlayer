package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.entity.Entities.EntityLilVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemVessel extends ItemBase{

    public ItemVessel(String name) {
        super(name);
        this.setMaxDamage(6);
        this.setMaxStackSize(1);
    }

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
                        EntityLilVessel lilV = new EntityLilVessel(world, player, this.getDamage(itemstack));
                        lilV.setVariant(world.rand.nextInt(2));
                        if (itemstack.hasDisplayName()) {
                            lilV.setCustomNameTag(itemstack.getDisplayName());
                        }
                        lilV.setPosition(blockpos.getX() + 0.5F, blockpos.getY() + 1F, blockpos.getZ() + 0.5F);
                        world.spawnEntity(lilV);
                    }
                    player.swingArm(hand);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
