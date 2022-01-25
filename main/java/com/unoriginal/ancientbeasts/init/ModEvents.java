package com.unoriginal.ancientbeasts.init;

import com.unoriginal.ancientbeasts.config.AncientBeastsConfig;
import com.unoriginal.ancientbeasts.entity.Entities.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ModEvents {

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (!world.isRemote && event.getEntity() instanceof EntityVillager) {
            EntityVillager villager = (EntityVillager) event.getEntity();
            villager.tasks.addTask(0, new EntityAIAvoidEntity<>(villager, EntityGiant.class, 16F, 1D, 1D));
            villager.tasks.addTask(0, new EntityAIAvoidEntity<>(villager, EntitySandy.class, 16F, 0.8D, 0.8D));
            villager.tasks.addTask(3, new EntityAIAvoidEntity<>(villager, EntityTornado.class, 6F, 0.8D, 0.8D));
            villager.tasks.addTask(3, new EntityAIAvoidEntity<>(villager, EntityZealot.class, 10F, 0.8D, 0.8D));
            villager.tasks.addTask(3, new EntityAIAvoidEntity<>(villager, EntityFrostWalker.class, 8F, 0.8D, 0.8D));
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
    @SubscribeEvent
    public void protection(LivingHurtEvent e){
        World world = e.getEntityLiving().getEntityWorld();
        EntityLivingBase entity = e.getEntityLiving();
        if(entity.isPotionActive(ModPotions.SHIELDED)){
            if(!world.isRemote) {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                e.setCanceled(true);

            }
        }
    }
    @SubscribeEvent
    public void oooSpuki(LivingEvent.LivingUpdateEvent event){
        EntityLivingBase e = event.getEntityLiving();
        if(e.isPotionActive(ModPotions.POSSESSED)){
            Random rand = new Random();
            if(rand.nextInt(4) == 0){
                e.motionX = (rand.nextDouble() - rand.nextDouble()) * (rand.nextDouble() + 0.5D);
                e.motionY = (rand.nextDouble() - rand.nextDouble()) * 0.5D;
                e.motionZ = (rand.nextDouble() - rand.nextDouble()) * (rand.nextDouble() + 0.5D);
            }
            if(e.getActivePotionEffect(ModPotions.POSSESSED).getDuration() <= 1)
            {
                e.removeActivePotionEffect(ModPotions.POSSESSED);
            }
        }
    }
    @SubscribeEvent
    public void Confusion (LivingSetAttackTargetEvent e){
        World world = e.getEntityLiving().getEntityWorld();
        EntityLivingBase l = e.getEntityLiving();
        if (!world.isRemote) {
            if(l instanceof EntityAnimal){
                EntityAnimal a = (EntityAnimal) l;
                EntityLivingBase p = a.getAttackTarget();
                if((a.getRevengeTarget() != null && a.getRevengeTarget() != p) || a.getRevengeTarget() == null) {
                    if(a.getAttackTarget() != null && a.getMaxHealth() == a.getHealth()){
                        if(p.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.CHARRED_CLOAK){
                            a.getNavigator().clearPath();
                            a.setAttackTarget(null);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
        public void onEvent(LivingEvent.LivingUpdateEvent event)
        {
        	EntityLivingBase entityLiving = event.getEntityLiving();
            Random rand = new Random();
            if (!entityLiving.world.isRemote && AncientBeastsConfig.MinerHelmetLight)
            {
                if(!AncientBeastsConfig.MinerHelmetFlickers || entityLiving.ticksExisted % 150 > (40 + rand.nextInt(20))) {
                    if (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.MINER_HELMET) {
                        int blockX = MathHelper.floor(entityLiving.posX);
                        int blockY = MathHelper.floor(entityLiving.posY - 0.2D + event.getEntityLiving().height);
                        int blockZ = MathHelper.floor(entityLiving.posZ);
                        BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
                        Block blockAtLocation = entityLiving.world.getBlockState(blockLocation).getBlock();
                        if (blockAtLocation == Blocks.AIR) {
                            entityLiving.world.setBlockState(blockLocation, ModBlocks.LIGHT.getDefaultState());
                        }
                    }
                }
            }
        }
}
