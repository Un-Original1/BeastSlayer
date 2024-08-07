package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBestiary extends Item {

    public ItemBestiary(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(worldIn.isRemote){
            BeastSlayer.commonProxy.openGui(stack);
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }
}
