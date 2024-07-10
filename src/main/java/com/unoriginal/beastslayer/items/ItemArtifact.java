package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArtifact extends Item {

    public ItemArtifact(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
        //I cannot define much about artifacts because most work very differently, I'll build onto it later, hopefully not a lot of (if x == y)
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack,worldIn,tooltip,flagIn);
        String s = stack.getItem().getUnlocalizedName() + ".tooltip";
        String result = I18n.format(s);
        tooltip.add(result);
    }
}
