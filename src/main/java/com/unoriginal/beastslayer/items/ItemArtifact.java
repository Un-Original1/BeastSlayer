package com.unoriginal.beastslayer.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemArtifact extends Item implements IBauble {
    protected int tier;
    protected boolean breaksOnDeath;
    protected boolean breaksOnDamage;

    @Optional.Method(modid = "baubles")
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.CHARM;
    }

    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        int max = 0;
        if(player instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)player;
            for (int i = 0; i < BaublesApi.getBaublesHandler(entityplayer).getSlots(); i++) {
                ItemStack stack = BaublesApi.getBaublesHandler(entityplayer).getStackInSlot(i);
                if(stack.getItem() instanceof ItemArtifact) {
                   max = max + 1;
                }
            }
        }
        return max <= BeastSlayerConfig.MaxBaublesPerPlayer - 1;
    }

    public ItemArtifact(String name, boolean BORIP, boolean BOD) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
        this.setMaxDamage(3 + this.getRarity());
        breaksOnDamage = BOD;
        breaksOnDeath = BORIP;
        //I cannot define much about artifacts because most work very differently, I'll build onto it later, hopefully not a lot of (if x == y)

        //2025: I failed to prevent what is stated above lol
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack,worldIn,tooltip,flagIn);
        String s = stack.getItem().getUnlocalizedName() + ".tooltip";
        String result = I18n.format(s);
        tooltip.add(result);
    }

    public boolean canBreakOnDeath(){
        return this.breaksOnDeath;
    }
    public boolean canBreakOnDamage(){
        return this.breaksOnDamage;
    }

    public ItemArtifact setRarity(int tier){
        this.tier = tier;
        return this;
    }
    public int getRarity()
    {
        return tier;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        EnumRarity rarity = null;
        switch (this.tier){
            case 0 :
                rarity = EnumRarity.COMMON;
                break;
            case 1:
                rarity = EnumRarity.UNCOMMON;
                break;
            case 2:
                rarity = EnumRarity.RARE;
                break;
            case 3:
                rarity = EnumRarity.EPIC;
                break;
        }
        return rarity;
    }
}
