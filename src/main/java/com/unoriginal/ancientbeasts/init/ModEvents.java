package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.EntitySandy;
import com.unoriginal.ancientbeasts.entity.Entities.EntityTornado;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (!world.isRemote && event.getEntity() instanceof EntityVillager) {
            EntityVillager villager = (EntityVillager) event.getEntity();
            villager.tasks.addTask(0, new EntityAIAvoidEntity<>(villager, EntitySandy.class, 16F, 0.8D, 0.8D));
            villager.tasks.addTask(3, new EntityAIAvoidEntity<>(villager, EntityTornado.class, 6F, 0.8D, 0.8D));
        }
        if (!world.isRemote && event.getEntity() instanceof AbstractIllager) {
            AbstractIllager illager = (AbstractIllager) event.getEntity();
            illager.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(illager, EntitySandy.class, true));
            illager.targetTasks.addTask(4, new EntityAIAvoidEntity<>(illager, EntityTornado.class, 6F, 0.8D, 0.8D));
        }
    }

    @SubscribeEvent
    public void DesertArmorEffectHurt(LivingHurtEvent e) {
        World world = e.getEntityLiving().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase entityLivingBase = e.getEntityLiving();
            if (entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.SCALE_HOOD && entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.SCALE_ARMOR) {
                if (entityLivingBase instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entityLivingBase;
                    if (!player.getCooldownTracker().hasCooldown(ModItems.SCALE_ARMOR)) {
                        for (int k = 0; k < 5; ++k) {
                            float f = (float) MathHelper.atan2(10F - entityLivingBase.posZ, 10F - entityLivingBase.posX);
                            float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + ((float) Math.PI * 2F / 5F);
                            EntityTornado tornado = new EntityTornado(world, entityLivingBase.posX + (double) MathHelper.cos(f2) * 3.0D, entityLivingBase.posY, entityLivingBase.posZ + (double) MathHelper.sin(f2) * 3.0D, 0, entityLivingBase);
                            world.spawnEntity(tornado);
                            player.getCooldownTracker().setCooldown(ModItems.SCALE_ARMOR, AncientBeastsConfig.armorCooldownHurt);
                        }
                    }
                }
                else {
                    if (world.rand.nextInt(5)== 0) {
                        for (int k = 0; k < 5; ++k) {
                            float f = (float) MathHelper.atan2(10F - entityLivingBase.posZ, 10F - entityLivingBase.posX);
                            float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + ((float) Math.PI * 2F / 5F);
                            EntityTornado tornado = new EntityTornado(world, entityLivingBase.posX + (double) MathHelper.cos(f2) * 2.5D, entityLivingBase.posY, entityLivingBase.posZ + (double) MathHelper.sin(f2) * 2.5D, 0, entityLivingBase);
                            world.spawnEntity(tornado);
                        }
                    }
                }
            }
        }

    }
    @SubscribeEvent
    public void OnArmorRightClick(PlayerInteractEvent.EntityInteractSpecific e) {
        World world = e.getWorld();
        if(!world.isRemote) {
            Entity target = e.getTarget();
            EntityPlayer player = e.getEntityPlayer();
            ItemStack stack = player.getHeldItemMainhand();
            if (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ModItems.SCALE_ARMOR && player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.SCALE_HOOD && stack.isEmpty() && player.isSneaking()){
                if(!player.getCooldownTracker().hasCooldown(ModItems.SCALE_HOOD) && target != null){
                    EnumHand hand = EnumHand.MAIN_HAND;
                    for (int l = 0; l < 4; ++l) {
                        float f = (float) MathHelper.atan2(target.posZ - player.posZ, target.posX - player.posX);
                        double d2 = 4.0D * (double) (l + 1);
                        EntityTornado tornado1 = new EntityTornado(world, player.posX + (double) MathHelper.cos(f) * d2, player.posY, player.posZ + (double) MathHelper.sin(f) * d2, 0, player);
                        world.spawnEntity(tornado1);
                        player.swingArm(hand);
                        player.getCooldownTracker().setCooldown(ModItems.SCALE_HOOD, AncientBeastsConfig.armorCooldownClick);
                        e.setCancellationResult(EnumActionResult.SUCCESS);
                        e.setCanceled(true);
                    }
                }
            }
        }
    }

}
