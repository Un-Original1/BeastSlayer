package com.unoriginal.beastslayer.items;

import com.google.common.collect.Multimap;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityChained;
import com.unoriginal.beastslayer.init.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemKunai extends Item {
    private final float attackDamage;
    public ItemKunai(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(BeastSlayer.BEASTSTAB);
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
        this.attackDamage = 3.0F;

    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if(itemRand.nextInt(2)==0) {
            stack.damageItem(1, attacker);

        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
      //  double d1 =  -MathHelper.sin(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F);
      //  double d2 =  -MathHelper.sin(player.rotationPitch * 0.017453292F);
      //  double d3 =  MathHelper.cos(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F);
        if(!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.KUNAI, SoundCategory.PLAYERS, 0.8F, 1.0F / itemRand.nextFloat() * 0.4F + 0.8F);

            EntityChained dart = new EntityChained(world, player);
            dart.setMob(false);
            Vec3d vec3d = player.getLookVec();
            dart.setPosition(player.posX + vec3d.x * 1.4D,player.posY + vec3d.y + player.getEyeHeight(), player. posZ + vec3d.z * 1.4D);
            dart.shoot( vec3d.x * 1.4D, vec3d.y * 1.4D, vec3d.z * 1.4D, 1.0F, 0.0F);
            player.getCooldownTracker().setCooldown(this,80);
            world.spawnEntity(dart);

            stack.damageItem(1, player);
            player.swingArm(hand);
            player.setActiveHand(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
