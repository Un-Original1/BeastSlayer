package com.unoriginal.beastslayer.items;

import com.google.common.collect.ImmutableList;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.EntityWisp;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

public class ItemStaff extends ItemAbstractMultimodel{
    public ItemStaff(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.getCooldownTracker().hasCooldown(this)) {
            EntityWisp wisp = new EntityWisp(world, true, player);
            wisp.setPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            wisp.setVariant(itemRand.nextInt(3));
            world.spawnEntity(wisp);
            player.getCooldownTracker().setCooldown(this, 200);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            stack.damageItem(1, player);
            player.swingArm(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
    @Override
    public void registerModels() {

        List<ModelResourceLocation> mrls = new ArrayList<>();
        ImmutableList<ModelResourceLocation> MList = ImmutableList.of(new ModelResourceLocation("ancientbeasts:tribe_staff", "inventory"),
                new ModelResourceLocation("ancientbeasts:tribe_staff_model", "inventory"));
        mrls.addAll(MList);

        ModelBakery.registerItemVariants(this, mrls.toArray(new ModelResourceLocation[0]));

        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(BeastSlayer.MODID,"tribe_staff_dummy"), "inventory")); //d stands for dummy

    }
}
