package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.EntityIceCrystal;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemIceWand extends Item {
    public ItemIceWand(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        pos = pos.offset(facing);

        boolean flag = isRed(itemstack);
        if (world.isAirBlock(pos))
        {
            if (!player.getCooldownTracker().hasCooldown(ModItems.ICE_WAND) && !player.getCooldownTracker().hasCooldown(ModItems.ICE_WAND_RED) && !world.isRemote) {
                for (int k = 0; k < 10; ++k) {
                    float f = (float) MathHelper.atan2(((double)pos.getZ() + 0.5) - (player.posZ), ((double)pos.getX() + 0.5) - (player.posX));
                    double d2 = 1.5D * (double)(k + 1);

                    EntityIceCrystal crystal = new EntityIceCrystal(world, player.posX + (double) MathHelper.cos(f) * d2, player.posY, player.posZ + (double) MathHelper.sin(f) * d2, player.rotationYaw, k, player, flag);
                    world.spawnEntity(crystal);
                }
                player.getCooldownTracker().setCooldown(ModItems.ICE_WAND, BeastSlayerConfig.frostWandCooldown);
                player.getCooldownTracker().setCooldown(ModItems.ICE_WAND_RED, BeastSlayerConfig.frostWandCooldown);
                if(!player.capabilities.isCreativeMode) {
                    itemstack.damageItem(1, player);
                }
                player.swingArm(hand);
            }
        }
        return EnumActionResult.SUCCESS;
    }
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        World world = player.getEntityWorld();
        boolean flag = isRed(stack);
        if (!player.getCooldownTracker().hasCooldown(ModItems.ICE_WAND) && !player.getCooldownTracker().hasCooldown(ModItems.ICE_WAND_RED) && !world.isRemote){
            for (int k = 0; k < 10; ++k) {
                float f = (float) MathHelper.atan2(target.posZ - player.posZ, target.posX - player.posX);
                double d2 = 1.5D * (double)(k + 1);
                EntityIceCrystal crystal = new EntityIceCrystal(world, player.posX + (double) MathHelper.cos(f) * d2, player.posY, player.posZ + (double) MathHelper.sin(f) * d2, player.rotationYaw, k, player, flag);
                world.spawnEntity(crystal);
            }
            player.getCooldownTracker().setCooldown(ModItems.ICE_WAND, BeastSlayerConfig.frostWandCooldown);
            player.getCooldownTracker().setCooldown(ModItems.ICE_WAND_RED, BeastSlayerConfig.frostWandCooldown);
            if(!player.capabilities.isCreativeMode) {
                stack.damageItem(1, player);
            }
            player.swingArm(hand);
        }
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    public boolean isRed(ItemStack stack){
        return stack.getItem() == ModItems.ICE_WAND_RED;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if(this.isRed(toRepair)){
            return repair.getItem() == toRepair.getItem() || repair.getItem() == ModItems.ICE_DART && repair.getMetadata() == 1;
        }
        else {
            return repair.getItem() == toRepair.getItem() || repair.getItem() == ModItems.ICE_DART && repair.getMetadata() == 0;
        }
    }
}
