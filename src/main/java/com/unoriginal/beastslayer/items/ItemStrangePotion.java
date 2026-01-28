package com.unoriginal.beastslayer.items;

import com.google.common.collect.Iterables;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
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

public class ItemStrangePotion extends Item {
    public ItemStrangePotion(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setMaxStackSize(16);
        this.setCreativeTab(BeastSlayer.BEASTSTAB);
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
            for (int i = 0; i < 2; i++) {
                if (entityLiving instanceof EntityPlayer) {

                    Potion potion = randEffect(entityLiving.getRNG().nextInt(7));
                    entityLiving.addPotionEffect(new PotionEffect(potion, 1800));
                    if(entityLiving.getRNG().nextInt(2) == 0) {
                        entityLiving.addPotionEffect(new PotionEffect(ModPotions.CHARMED, 20, 0));
                    }
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
        }
        if(entityplayer == null || !entityplayer.capabilities.isCreativeMode){
            stack.shrink(1);
            entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    public Potion randEffect (int random){
        Potion newPotion = null;
        switch (random)
        {
            case 0:
                newPotion = MobEffects.STRENGTH;
                break;
            case 1:
                newPotion = MobEffects.REGENERATION;
                break;
            case 2:
                newPotion = MobEffects.JUMP_BOOST;
                break;
            case 3:
                newPotion = MobEffects.FIRE_RESISTANCE;
                break;
            case 4:
                newPotion = MobEffects.WATER_BREATHING;
                break;
            case 5:
                newPotion = MobEffects.SPEED;
                break;
            case 6:
                newPotion = MobEffects.INVISIBILITY;
                break;
        }
        return newPotion;
    }
}
