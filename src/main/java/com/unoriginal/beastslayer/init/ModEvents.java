package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.*;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMobAvoidOwlstack;
import com.unoriginal.beastslayer.items.ItemSpear;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageAttackER;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
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
            villager.tasks.addTask(2, new EntityAIAvoidEntity<>(villager, EntityVessel.class, 10F, 0.8D, 0.8D) );
            villager.tasks.addTask(2, new EntityAIAvoidEntity<>(villager, EntityNekros.class, 10F, 0.8D, 0.8D) );
        }
        if (!world.isRemote && event.getEntity() instanceof AbstractIllager) {
            AbstractIllager illager = (AbstractIllager) event.getEntity();
            illager.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(illager, EntitySandy.class, true));
            illager.targetTasks.addTask(4, new EntityAIAvoidEntity<>(illager, EntityTornado.class, 6F, 0.8D, 0.8D));
        }
        if (!world.isRemote && event.getEntity() instanceof EntityMob) {
            EntityMob mob = (EntityMob) event.getEntity();
            if(mob.isNonBoss()){
                mob.tasks.addTask(0, new EntityAIMobAvoidOwlstack<>(mob, EntityOwlstack.class, 6F, 1.0D, 1.4D));
            }
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
                            player.getCooldownTracker().setCooldown(ModItems.SCALE_ARMOR, BeastSlayerConfig.armorCooldownHurt);
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
                        player.getCooldownTracker().setCooldown(ModItems.SCALE_HOOD, BeastSlayerConfig.armorCooldownClick);
                        e.setCancellationResult(EnumActionResult.SUCCESS);
                        e.setCanceled(true);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void hurtEvents(LivingHurtEvent e){
        World world = e.getEntityLiving().getEntityWorld();
        EntityLivingBase entity = e.getEntityLiving();
        if(entity.isPotionActive(ModPotions.SHIELDED)){
            if(!world.isRemote) {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, ModSounds.MAGIC_SHIELD, SoundCategory.NEUTRAL, 1.0F, 1.0F / world.rand.nextFloat() * 0.4F + 0.9F);
                e.setCanceled(true);

            }
        }
        if(entity.isPotionActive(ModPotions.RIFTED)){
            double x = entity.posX + (entity.getRNG().nextDouble() - 0.5D) * 64.0D;
            double y = entity.posY + (double)(entity.getRNG().nextInt(64) - 32);
            double z = entity.posZ + (entity.getRNG().nextDouble() - 0.5D) * 64.0D;
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entity, x, y, z, 0);
            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
                boolean flag = entity.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                if (flag) {
                    entity.world.playSound(null, entity.prevPosX, entity.prevPosY, entity.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
                    entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
                }
            }
        }
        if(entity.isPotionActive(ModPotions.UNDEAD)){
            EntityLivingBase target = entity.getRevengeTarget();
            List<EntityMob> reinforcements = world.getEntitiesWithinAABB(EntityMob.class, entity.getEntityBoundingBox().grow(16D), entityMob -> entityMob.isEntityUndead());
            for (EntityMob mob : reinforcements){
                if(target != null) {
                    mob.setAttackTarget(target);
                }
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
        if (e.isPotionActive(ModPotions.UNDEAD)){
            if(e.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && e.world.canSeeSky(e.getPosition()) && e.world.isDaytime()) {
                e.setFire(4);
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
            } else if (l instanceof EntityMob){
                EntityMob m = (EntityMob) l;
                EntityLivingBase p = m.getAttackTarget();
                if(m.isEntityUndead() && p != null && p.isPotionActive(ModPotions.UNDEAD) && p.getActivePotionEffect(ModPotions.UNDEAD).getDuration() > 0 && m.isNonBoss()){
                    if((m.getRevengeTarget() != null && m.getRevengeTarget() != p) || m.getRevengeTarget() == null) {
                        if (m.getMaxHealth() == m.getHealth()) {
                            m.getNavigator().clearPath();
                            m.setAttackTarget(null);
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
        if (!entityLiving.world.isRemote && BeastSlayerConfig.MinerHelmetLight)
        {
            if(!BeastSlayerConfig.MinerHelmetFlickers || entityLiving.ticksExisted % 150 > (40 + rand.nextInt(20))) {
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

    @SubscribeEvent
    public void Reach(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = player.getEntityWorld();

        if (player.getHeldItemMainhand().getItem() instanceof ItemSpear) {

            Vec3d v = player.getLookVec();
            for(int i = 1; i < 7;i++){

                AxisAlignedBB aabb = new AxisAlignedBB(player.posX + v.x * i, player.posY + v.y * i, player.posZ + v.z * i, player.posX + v.x * i, player.posY + v.y * i, player.posZ + v.z * i).grow(1.1D);
                List list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                if(list.iterator().hasNext()){
                    EntityLivingBase entity = (EntityLivingBase)list.get(0);
                    if(entity != null && entity != player){
                        BeastSlayerPacketHandler.sendToServer(new MessageAttackER(player, entity));

                    }
                }
            }
        }
    }

}
