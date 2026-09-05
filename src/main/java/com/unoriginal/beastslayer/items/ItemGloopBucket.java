package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityGloop;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemGloopBucket extends Item {
    public ItemGloopBucket(String name) {
        this.setMaxStackSize(1);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    { return EnumActionResult.PASS; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult rtresult = this.rayTrace(worldIn, playerIn, false);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, rtresult);
        if (ret != null) return ret;

        if (rtresult == null || rtresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else
        {
            BlockPos blockpos = rtresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            { return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack); }
            else {
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = flag1 && rtresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(rtresult.sideHit);
                playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                if (!worldIn.isRemote) {

                    EntityGloop gloop = new EntityGloop(worldIn);
                    if (itemstack.hasDisplayName()) {
                        gloop.setCustomNameTag(itemstack.getDisplayName());
                    }

                    gloop.enablePersistence();
                    gloop.setPosition((double) blockpos1.getX() + 0.5, (double) blockpos1.getY(), (double) blockpos1.getZ() + 0.5);
                    worldIn.spawnEntity(gloop);

                }

                playerIn.addStat(StatList.getObjectUseStats(this));
                return !playerIn.capabilities.isCreativeMode ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(Items.BUCKET)) : new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }

        }
    }
}
