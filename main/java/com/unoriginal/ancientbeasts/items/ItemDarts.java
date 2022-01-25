package com.unoriginal.ancientbeasts.items;

import com.unoriginal.ancientbeasts.AncientBeasts;
import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.EntityIceDart;
import com.unoriginal.ancientbeasts.init.ModSounds;
import com.unoriginal.ancientbeasts.tab.ModTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDarts extends ItemBase{
    public ItemDarts(String name) {
        super(name);
        this.setHasSubtypes(true);
        this.setMaxStackSize(16);
        this.addPropertyOverride(new ResourceLocation("red"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return (stack.getMetadata() > 0) ? 1.0F : 0F;
            }
        });
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        World world = player.getEntityWorld();
        double d1 =  -MathHelper.sin(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F);
        double d2 =  -MathHelper.sin(player.rotationPitch * 0.017453292F);
        double d3 =  MathHelper.cos(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F);
        if(!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.ICE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (stack.getMetadata() > 0) {
                for (int x = 0; x < 3; ++x) {
                    EntityIceDart dart = new EntityIceDart(world, player, true);
                    dart.shoot(d1, d2, d3, 1.0F, 20.0F);
                    player.getCooldownTracker().setCooldown(this, AncientBeastsConfig.iceSpikesCooldown);
                    world.spawnEntity(dart);
                }
            } else {
                for (int x = 0; x < 3; ++x) {
                    EntityIceDart dart = new EntityIceDart(world, player, false);
                    dart.shoot(d1, d2, d3, 1.0F, 20.0F);
                    player.getCooldownTracker().setCooldown(this, AncientBeastsConfig.iceSpikesCooldown);
                    world.spawnEntity(dart);
                }
            }
            stack.shrink(1);
            player.swingArm(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
            items.add(new ItemStack(this, 1, 1));
        }
    }

}
