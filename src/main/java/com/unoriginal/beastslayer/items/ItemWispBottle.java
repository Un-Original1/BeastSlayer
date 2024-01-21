package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.entity.Entities.EntityWisp;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemWispBottle extends ItemBase {

    public ItemWispBottle(String name) {
        super(name);
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return stack.getMetadata();
            }
        });
    }
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        pos = pos.offset(facing);
        if (world.isAirBlock(pos))
        {
            if (!world.isRemote) {
                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                    player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                }
                RayTraceResult raytraceresult = this.rayTrace(world, player, true);

                if (raytraceresult.typeOfHit != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    if (world.isBlockModifiable(player, blockpos) && player.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
                        EntityWisp wisp = new EntityWisp(world, true, player);
                        wisp.setVariant(this.getMetadata(itemstack));
                        if (itemstack.hasDisplayName()) {
                            wisp.setCustomNameTag(itemstack.getDisplayName());
                        }
                        wisp.setPosition(blockpos.getX() + 0.5F, blockpos.getY() + 1.5F, blockpos.getZ() + 0.5F);
                        world.spawnEntity(wisp);
                        return EnumActionResult.SUCCESS;
                    }
                    player.swingArm(hand);
                } else {
                    return EnumActionResult.FAIL;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
            items.add(new ItemStack(this, 1, 1));
            items.add(new ItemStack(this, 1, 2));
            items.add(new ItemStack(this, 1, 3));
        }
    }
}
