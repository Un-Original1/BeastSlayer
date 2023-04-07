package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.entity.Entities.EntityWispfire;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemWispflame extends ItemBase{
    public ItemWispflame(String name) {
        super(name);
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof ItemWindforce) ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    public ActionResult onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack itemstack = player.getHeldItem(handIn);
        player.setActiveHand(handIn);

        player.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 60;
    }
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

        int i = this.getMaxItemUseDuration(stack) - count;
        if(i % 10 == 0){
            if(!player.world.isRemote){
                float f1 = MathHelper.sin(player.renderYawOffset * 0.017453292F + (float)Math.PI);
                float f2 = MathHelper.cos(player.renderYawOffset * 0.017453292F);
                EntityWispfire entityWispfire = new EntityWispfire(player.world, player.posX + f1, player.posY + player.getEyeHeight(), player.posZ + f2);
                Vec3d vec3d = player.getLookVec();
                entityWispfire.setPosition(player.posX + vec3d.x * 1.4D,player.posY + vec3d.y + player.getEyeHeight(), player. posZ + vec3d.z * 1.4D);
                entityWispfire.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 1.0F);
                player.world.spawnEntity(entityWispfire);
                if(player instanceof EntityPlayer){
                    EntityPlayer p = (EntityPlayer) player;
                    if(!p.capabilities.isCreativeMode){
                        stack.damageItem(1, player);
                    }
                }
            }
        }
    }
}
