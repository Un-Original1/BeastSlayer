package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.EntityIceDart;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
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
        Vec3d vec3d = player.getLookVec();
        if(!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.ICE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (stack.getMetadata() > 0) {
                for (int x = 0; x < 3; ++x) {
                    EntityIceDart dart = new EntityIceDart(world, player, true);
                    dart.setPosition(player.posX + vec3d.x * 1.4D,player.posY + vec3d.y + player.height / 2, player. posZ + vec3d.z * 1.4D);
                    dart.shoot(vec3d.x * 1.4D, vec3d.y, vec3d.z * 1.4D, 1.5F, 20.0F);
                    player.getCooldownTracker().setCooldown(this, BeastSlayerConfig.iceSpikesCooldown);
                    world.spawnEntity(dart);
                }
            } else {
                for (int x = 0; x < 3; ++x) {
                    EntityIceDart dart = new EntityIceDart(world, player, false);
                    dart.setPosition(player.posX + vec3d.x * 1.4D,player.posY + vec3d.y + player.getEyeHeight(), player. posZ + vec3d.z * 1.4D);
                    dart.shoot(vec3d.x * 1.4D, vec3d.y, vec3d.z * 1.4D, 1.5F, 20.0F);
                    player.getCooldownTracker().setCooldown(this, BeastSlayerConfig.iceSpikesCooldown);
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
