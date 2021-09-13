package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShieldBook extends ItemBase{

    public ItemShieldBook(String name) {
        super(name);
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
    }
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        World world = player.getEntityWorld();
        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            target.addPotionEffect(new PotionEffect(ModPotions.SHIELDED, 200, 0));
            player.getCooldownTracker().setCooldown(this, 400);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            stack.damageItem(1, player);
            player.swingArm(hand);
        }
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        World world = player.getEntityWorld();
        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            player.addPotionEffect(new PotionEffect(ModPotions.SHIELDED, 100, 0));
            player.getCooldownTracker().setCooldown(this, 400);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            stack.damageItem(1, player);
            player.swingArm(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}