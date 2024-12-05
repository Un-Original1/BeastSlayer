package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModPotions;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBossMask extends ItemArmor {
    public ItemBossMask(String name) {
            super(ArmorMaterial.IRON, 0, EntityEquipmentSlot.HEAD);
            setCreativeTab(BeastSlayer.BEASTSTAB);
            setRegistryName(name);
            setUnlocalizedName(name);
            this.setMaxDamage(0);
            // setTier(tier);
        }

        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
        {
            return false;
        }
        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
            return (ModelBiped) BeastSlayer.commonProxy.getArmorModel(this, entityLiving);
        }
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return BeastSlayer.MODID + ":textures/models/armor/mask_fire.png";
        }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if(player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ModItems.LOGO){
            if(!player.getCooldownTracker().hasCooldown(this)) {
                player.addPotionEffect(new PotionEffect(ModPotions.FRENZY, 200));
                player.getCooldownTracker().setCooldown(this, 1000);
            }
        }
    }
}
