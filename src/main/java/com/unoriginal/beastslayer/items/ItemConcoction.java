package com.unoriginal.beastslayer.items;

import com.google.common.collect.Iterables;
import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemConcoction extends Item {

    public ItemConcoction(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxDamage(16);
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.getCooldownTracker().hasCooldown(this)) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(!worldIn.isRemote) {
            for (int i = 0; i < 3; i++) {
                if (!(entityLiving instanceof EntityPlayer) || !((EntityPlayer) entityLiving).getCooldownTracker().hasCooldown(this)) {

                     Potion potion = Iterables.get(ForgeRegistries.POTIONS, itemRand.nextInt(ForgeRegistries.POTIONS.getValuesCollection().size()));
                     entityLiving.addPotionEffect(new PotionEffect(potion, 800));

                }
                ///   MobEffects randomEffect = effects.get(new Random().nextInt(effects.size()));
            }
        }

        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer != null)
        {
            if (entityplayer instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
            }
            entityplayer.addStat(StatList.getObjectUseStats(this));
            if(!worldIn.isRemote) {
                entityplayer.getCooldownTracker().setCooldown(this, 3000);
            }
        }
        if(entityplayer == null || !entityplayer.capabilities.isCreativeMode){
            stack.damageItem(1, entityplayer);
        }

        return stack;
    }

}
