package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMask extends ItemArmor {
    protected int tier;
    public ItemMask(String name) {
        super(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.HEAD);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        setRegistryName(name);
        setUnlocalizedName(name);
       // setTier(tier);
    }
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ModItems.SANDMONSTER_SCALE;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        return (ModelBiped) BeastSlayer.commonProxy.getArmorModel(this, entityLiving);
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return BeastSlayer.MODID + ":textures/models/armor/mask_" + tiername(this.getTier()) + ".png";
    }
    private String tiername(int tier){
        String s = null;
        switch (tier){
            case 0:
                s = "marauder";
                break;
            case 1:
                s = "hunter";
                break;
            case 2:
                s = "scorcher";
                break;
            case 3:
                s = "spiritweaver";
                break;
        }
        return s;
    }
    public ItemMask setTier(int tier){
        this.tier = tier;
        return this;
    }
    public int getTier()
    {
        return tier;
    }
}
