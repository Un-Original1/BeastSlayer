package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.AncientBeasts;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemCactusBroth extends ItemFood{

    public ItemCactusBroth(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(AncientBeasts.BEASTSTAB);
        this.setMaxStackSize(64);
    }
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if(!worldIn.isRemote){
            if(itemRand.nextInt(4) == 0){
                player.attackEntityFrom(DamageSource.CACTUS, 2.0F);
            }
        }
    }
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
