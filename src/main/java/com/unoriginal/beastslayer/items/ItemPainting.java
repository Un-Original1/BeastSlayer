package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityBSPainting;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPainting extends ItemHangingEntity {

    public ItemPainting(Class<? extends EntityHanging> entityClass, String name) {
        super(entityClass);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        BlockPos offsetPos = pos.offset(facing);

        if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && player.canPlayerEdit(offsetPos, facing, itemstack)) {
            EntityBSPainting entity = new EntityBSPainting(world, offsetPos, facing);

            int i = world.rand.nextInt(10);
            NBTTagCompound compound = itemstack.getTagCompound();
            if(compound == null){
                compound = new NBTTagCompound();
                itemstack.setTagCompound(compound);
            }
           // if(!compound.hasKey("Variant")) {
             //   compound.setInteger("Variant", i);
           // }
            if(compound.hasKey("Variant")) {
                entity.setArt(compound.getInteger("Variant"));
            } else{
                entity.setArt(i);
            }
            if (entity != null && entity.onValidSurface()) {
                if (!world.isRemote) {
                    entity.playPlaceSound();
                    world.spawnEntity(entity);
                }

                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this));
            for (int i = 0; i < 10; i++) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("Variant", i);
                ItemStack itemstack = new ItemStack(this);
                itemstack.setTagCompound(compound);
                items.add(itemstack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack,worldIn,tooltip,flagIn);
        NBTTagCompound compound = stack.getTagCompound();
        if(compound != null){
            if(compound.hasKey("Variant")) {
                String s = "beast.paintings" + "." + compound.getInteger("Variant");
                String result = I18n.format(s);
                tooltip.add(result);
            } else {
                String s = "beast.paintings.rand";
                String result = I18n.format(s);
                tooltip.add(result);
            }
        }else {
            String s = "beast.paintings.rand";
            String result = I18n.format(s);
            tooltip.add(result);
        }

    }
}
