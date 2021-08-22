package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.init.ModItems;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScaleArmor extends ItemArmor {
    public ScaleArmor(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot) {
        super(material, renderIndex, slot);
        setCreativeTab(AncientBeasts.BEASTSTAB);
        setRegistryName(name);
        setTranslationKey(name);
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ModItems.SANDMONSTER_SCALE;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        return (ModelBiped) AncientBeasts.commonProxy.getArmorModel(this, entityLiving);
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return AncientBeasts.MODID+":textures/models/armor/desert_armor.png";
    }
}
