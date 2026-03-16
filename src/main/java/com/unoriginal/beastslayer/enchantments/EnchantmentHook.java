package com.unoriginal.beastslayer.enchantments;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.items.ItemKunai;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentHook extends Enchantment {
    public EnchantmentHook(Rarity rarityIn, EnumEnchantmentType typeIn) {
        super(rarityIn, typeIn, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(BeastSlayer.MODID, "hook");
        this.setName(BeastSlayer.MODID + "." + "hook");
    }

    public int getMinEnchantability(int enchantmentLevel)
    {
        return 6;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof EnchantmentPulling) && super.canApplyTogether(ench);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return stack.getItem() instanceof ItemKunai;
    }
}
