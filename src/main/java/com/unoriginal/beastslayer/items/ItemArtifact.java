package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.integration.IntegrationBaubles;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArtifact extends Item {
    private final baubleSlot baubleSlot;
    protected int tier;
    protected boolean breaksOnDeath;
    protected boolean breaksOnDamage;
    public enum baubleSlot {
        CHARM(1);
        baubleSlot(int max){

        }
    }

    public ItemArtifact(String name, baubleSlot slot, boolean BORIP, boolean BOD) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
        this.setMaxDamage(3 + this.getRarity());
        baubleSlot = slot;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt){
        return IntegrationBaubles.isEnabled() ? new IntegrationBaubles.BaubleProvider(baubleSlot) : null;
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
