package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.EntityBeam;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShieldBook extends Item {

    public ItemShieldBook(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
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
        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this) && !(target instanceof EntityMob)) {
            EntityBeam beam = new EntityBeam(world, player, target,280 + BeastSlayerConfig.sBTimerBonus * 2);
            target.addPotionEffect(new PotionEffect(ModPotions.SHIELDED, 280 + BeastSlayerConfig.sBTimerBonus * 2, 0));
            world.spawnEntity(beam);
            player.getCooldownTracker().setCooldown(this, 400);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            stack.damageItem(1, player);
            player.swingArm(hand);
        }
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            EntityBeam beam = new EntityBeam(world, player, player, 120 + BeastSlayerConfig.sBTimerBonus);
            player.addPotionEffect(new PotionEffect(ModPotions.SHIELDED, 120 + BeastSlayerConfig.sBTimerBonus, 0));
            world.spawnEntity(beam);
            player.getCooldownTracker().setCooldown(this, 400);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            stack.damageItem(1, player);
            player.swingArm(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}