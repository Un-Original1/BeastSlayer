package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntitySucc;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemReusableHealth extends Item {
    public ItemReusableHealth(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxDamage(300);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("off"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    boolean flag = stack.getItemDamage() > (300F / 2F);
                    boolean flag1 = stack.getItemDamage() > (300F / 2F);

                    return (flag || flag1) ? 1.0F : 0.0F;
                }
            }
        });
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        World world = player.getEntityWorld();
        if (!player.getCooldownTracker().hasCooldown(this) && (!(target instanceof EntityMob) || target instanceof EntitySucc) && stack.getItemDamage() < (this.getMaxDamage(stack) / 2F)) {
            if(!world.isRemote) {
                target.heal((float) stack.getItemDamage() / 10F);
                player.getCooldownTracker().setCooldown(this, 2000);
                world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.SUCC_SPELL, SoundCategory.PLAYERS, 0.8F, 1.0F / (player.getRNG().nextFloat() * 0.4F + 0.8F));
                stack.setItemDamage(299);
                player.swingArm(hand);
            }
            if(world.isRemote){
                world.spawnParticle(ModParticles.SPELL, player.posX, player.posY+ 0.1F, player.posZ, 0D, 0D ,0D );
            }
        }
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.getCooldownTracker().hasCooldown(this) && stack.getItemDamage() < (this.getMaxDamage(stack) / 2F)) {
            if(!world.isRemote) {
                player.heal((200F - (float) stack.getItemDamage()) / 10F);
                player.getCooldownTracker().setCooldown(this, 2000);
                world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.SUCC_SPELL, SoundCategory.PLAYERS, 0.8F, 1.0F / (player.getRNG().nextFloat() * 0.4F + 0.8F));
                stack.setItemDamage(299);
                player.swingArm(hand);
            }
            if(world.isRemote){
                world.spawnParticle(ModParticles.SPELL, player.posX, player.posY+ 0.1F, player.posZ, 0D, 0D ,0D );
            }
        }


        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
