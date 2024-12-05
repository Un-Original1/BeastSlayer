package com.unoriginal.beastslayer.items;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityWindforceDart;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Comparator;
import java.util.List;


public class ItemWindforce extends ItemBow {
    public ItemWindforce(String name){
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxStackSize(1);
    }

    @Override
    protected boolean isArrow(ItemStack stack)
    {
        return stack.getItem() == ModItems.WINDFORCE_DART;
    }

    public EntityLivingBase setRandTarget(World world, EntityPlayer player){
        int i = 0;
        EntityLivingBase target = null;
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition()).grow(20D),  livingbase -> livingbase != player);
        if (!list.isEmpty()) {
            list.sort(Comparator.comparingDouble(t -> t.getDistanceSq(player.posX, player.posY, player.posZ)));
            EntityLivingBase livingBase = list.get(0);
            List<EntityMob> list2 = world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(player.getPosition()).grow(20D));
            if (!list2.isEmpty()) {
                list2.sort(Comparator.comparingDouble(t -> t.getDistanceSq(player.posX, player.posY, player.posZ)));
                target = list2.get(0);
                if(!player.canEntityBeSeen(target) && i < list.size() ){
                    i++;
                    target = list.get(i);
                }
            } else if (!livingBase.isOnSameTeam(player)) {
                target = livingBase;
            }
        }
        return target;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entityLiving;
            boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(player);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, world, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.6D)
                {
                    boolean flag1 = player.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, player));

                    if (!world.isRemote)
                    {


                        EntityWindforceDart windforceDart = new EntityWindforceDart(world, player, this.setRandTarget(world, player));



                        stack.damageItem(1, player);

                        Vec3d vec3d = player.getLookVec();
                        windforceDart.setPosition(player.posX + vec3d.x * 1.4D,player.posY + vec3d.y + player.getEyeHeight(), player. posZ + vec3d.z * 1.4D);
                        world.spawnEntity(windforceDart);

                    }

                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            player.inventory.deleteStack(itemstack);
                        }
                    }

                    player.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }
}
