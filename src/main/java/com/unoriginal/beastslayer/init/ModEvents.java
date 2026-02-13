package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.*;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMobAvoidOwlstack;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import com.unoriginal.beastslayer.integration.IntegrationBaubles;
import com.unoriginal.beastslayer.items.ItemArtifact;
import com.unoriginal.beastslayer.items.ItemSpear;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import com.unoriginal.beastslayer.network.MessageAttackER;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ModEvents {

    List<String> blacklist = Arrays.asList(BeastSlayerConfig.AI_blacklist);
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
            if(EntityList.getEntityString(mob) != null) {
                if (mob.isNonBoss() && !blacklist.contains(EntityList.getEntityString(mob))) {
                    if((BeastSlayerConfig.owlstack_affects_undead && mob.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) || BeastSlayerConfig.owlstack_affects_undead == false ) {
                        mob.tasks.addTask(0, new EntityAIMobAvoidOwlstack<>(mob, EntityOwlstack.class, 6F, 1.0D, 1.4D));
                    }
                }
            }
        }
        if(!world.isRemote && event.getEntity() instanceof EntityAnimal) {
            EntityAnimal animal = (EntityAnimal) event.getEntity();
            animal.tasks.addTask(3, new EntityAIAvoidEntity<>(animal, EntitySucc.class, 16F, 0.8F, 1F));
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
    public void OnPlayerRightClick(PlayerInteractEvent.EntityInteractSpecific e) {
        World world = e.getWorld();
        if(!world.isRemote) {
            Entity target = e.getTarget();
            EntityPlayer player = e.getEntityPlayer();
            ItemStack stack = player.getHeldItemMainhand();

            Item item = getActiveItem(player);

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

            else if ((item == ModItems.TELEKINESIS ) && player.getHeldItemMainhand().isEmpty()){
                if(!player.getCooldownTracker().hasCooldown(ModItems.TELEKINESIS) && target instanceof EntityLivingBase && target.isNonBoss()){
                    EntityHand hand = new EntityHand(world, player, (EntityLivingBase) target);
                    world.spawnEntity(hand);
                    target.startRiding(hand);
                    player.swingArm(EnumHand.MAIN_HAND);
                    player.getCooldownTracker().setCooldown(ModItems.TELEKINESIS, 300);
                    e.setCancellationResult(EnumActionResult.SUCCESS);
                    e.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public void BlockEvents(PlayerInteractEvent.RightClickBlock e){
        World world = e.getWorld();
        EntityPlayer player = e.getEntityPlayer();

        Item item = getActiveItem(player);


        if (item == ModItems.TELEKINESIS  && !world.isRemote && player.getCooldownTracker().hasCooldown(ModItems.TELEKINESIS) && player.getHeldItemMainhand().isEmpty()){
            List<EntityHand> hands = world.getEntitiesWithinAABB(EntityHand.class, player.getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D));
            if(!hands.isEmpty()){
                for (EntityHand hand : hands){
                    if(hand.getCaster() == player){
                        hand.setTargetPos(new BlockPos(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()));
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void hurtEvents(LivingHurtEvent e){
        World world = e.getEntityLiving().getEntityWorld();
        EntityLivingBase entity = e.getEntityLiving();
        Entity attacker = e.getSource().getTrueSource();
        if(entity.isPotionActive(ModPotions.SHIELDED)){
            if(!world.isRemote) {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, ModSounds.MAGIC_SHIELD, SoundCategory.NEUTRAL, 1.0F, 1.0F / world.rand.nextFloat() * 0.4F + 0.9F);
                e.setCanceled(true);

            }
        }
        if(getActiveItem(entity)!= null ){
            if((e.getSource().isFireDamage() && e.getEntityLiving().getRNG().nextInt(3) == 0) || (e.getSource() != DamageSource.FALL && e.getEntityLiving().getRNG().nextInt(15) == 0)) {
                ItemStack stack = getActiveStack(entity);
                if (stack.getItem() == getActiveItem(entity) && getActiveItem(entity) != null && getActiveItem(entity) instanceof ItemArtifact) {
                    //ItemArtifact artifact = (ItemArtifact) getActiveItem(entity);
                    if(stack.getItem() instanceof ItemArtifact  && ((ItemArtifact)stack.getItem()).canBreakOnDamage() && !e.isCanceled()) {

                        //   ItemStack me = new ItemStack(ModItems.BROKEN_ARTIFACT, 1);
                        // BeastSlayer.logger.debug(stack.getItem().toString());
                        //  entity.dropItem(ModItems.BROKEN_ARTIFACT, 1);
                        stack.damageItem(1, entity);
                        world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
            if(e.getSource() == DamageSource.LAVA) {
                ItemStack stack = getActiveStack(entity);
                if (stack.getItem() == getActiveItem(entity) && getActiveItem(entity) != null && getActiveItem(entity) == ModItems.SPRING) {
                    //ItemArtifact artifact = (ItemArtifact) getActiveItem(entity);
                    if(stack.getItem() instanceof ItemArtifact && !e.isCanceled()) {

                        //   ItemStack me = new ItemStack(ModItems.BROKEN_ARTIFACT, 1);
                        // BeastSlayer.logger.debug(stack.getItem().toString());
                        //  entity.dropItem(ModItems.BROKEN_ARTIFACT, 1);
                        stack.damageItem(1, entity);
                        world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
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
        if(entity.isPotionActive(ModPotions.FRENZY)){
            if(attacker != null) {
                attacker.setFire(5);
            }
        }
        if(attacker != null && attacker instanceof EntityLivingBase){
            EntityLivingBase base = (EntityLivingBase) attacker;
            if(base.isPotionActive(ModPotions.FRENZY)){
                entity.setFire(5);
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
        if(entity.getHeldItemOffhand().getItem() instanceof ItemArtifact){
            Item item = entity.getHeldItemOffhand().getItem();
            ItemArtifact item2 = null;
            if(entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity ;
                if(!IntegrationBaubles.getEquippedArtifacts(player, ItemArtifact.baubleSlot.CHARM).isEmpty()) {
                    item2 = IntegrationBaubles.getEquippedArtifacts(player, ItemArtifact.baubleSlot.CHARM).get(0);
                }
            }
            if(item == ModItems.PAW || item2 == ModItems.PAW){
                if(e.getSource() == DamageSource.FALL){
                    float f = e.getAmount();
                    e.setAmount(f * 0.5F);
                }
            }
            if(item == ModItems.GLASS_SHARD || item2 == ModItems.GLASS_SHARD){
                float f = e.getAmount();
                e.setAmount(f * 2F);
            }
            if(item == ModItems.SPRING || item2 == ModItems.SPRING){
                if(e.getSource() == DamageSource.LAVA || entity.isInLava()){

                    entity.addVelocity(0D, 15D,0D );
                    if(entity instanceof EntityPlayerMP){
                        ((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity(entity));
                    }
                    entity.extinguish();
                    entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 40, 0, true, false));
                    entity.velocityChanged = true;
                }
            }
            if(item == ModItems.IRONGRASS || item2 == ModItems.IRONGRASS){
                if(entity instanceof EntityPlayer){ //TODO{
                    EntityPlayer p = (EntityPlayer) entity;
                    if(!p.getCooldownTracker().hasCooldown(ModItems.IRONGRASS)){
                        p.world.playSound(null, p.posX, p.posY, p.posZ, ModSounds.MAGIC_SHIELD, SoundCategory.PLAYERS, 1.0F, 0.7F);
                        e.setCanceled(true);
                        p.getCooldownTracker().setCooldown(ModItems.IRONGRASS, 400);
                    }
                } else {
                    if(entity.getRNG().nextInt(6)==0){
                        entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, ModSounds.MAGIC_SHIELD, entity.getSoundCategory(), 1.0F, 0.7F);
                        e.setCanceled(true);
                    }
                }
            }
            if(item == ModItems.AGILITY_TALON || item2 == ModItems.AGILITY_TALON){
                entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 40, 2, true, false));
            }
        }
        if(attacker instanceof EntityLivingBase){
            EntityLivingBase livingAttack = (EntityLivingBase)attacker;

            Item item = getActiveItem(livingAttack);

            if(livingAttack.getHeldItemMainhand().getItem() == ModItems.TOUGH_GLOVE && !world.isRemote){
                if(!(attacker instanceof EntityPlayer)) {
                    double d0 = entity.posX - attacker.posX;
                    double d1 = entity.posZ - attacker.posZ;
                    entity.addVelocity(d0, 0.2, d1);
                }
            }
            if(item == ModItems.WATER_RUNE){
                entity.extinguish();
                if(entity instanceof EntityBlaze || entity instanceof EntityFireElemental || (entity instanceof AbstractTribesmen && ((AbstractTribesmen) entity).isFiery()) || entity.isBurning()){
                    float amount = e.getAmount();
                    e.setAmount(amount * 1.75F);
                }
            }
            if(item == ModItems.HUNGRY_TOOTH){
                if(attacker instanceof EntityPlayer){
                    EntityPlayer p = (EntityPlayer) attacker;

                    p.getFoodStats().addStats(1, 0);
                }
            }
            if(item == ModItems.LEECH){
                if(!entity.getActivePotionEffects().isEmpty()){
                    for (PotionEffect potionEffect1: entity.getActivePotionEffects()) {
                        livingAttack.addPotionEffect(potionEffect1);
                    }
                }
            }
            if(item == ModItems.TRAITORS_BLADE){
                if(entity instanceof EntityPlayer) {
                    if (!entity.canEntityBeSeen(attacker)){
                        float amount = e.getAmount();
                        e.setAmount(amount * 1.5F);

                        world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
                if(entity instanceof EntityCreature){
                    EntityCreature creature = (EntityCreature) entity;
                    if(creature.getAttackTarget() != attacker || !entity.canEntityBeSeen(attacker)){
                        float amount2 = e.getAmount();
                        e.setAmount(amount2 * 1.5F);
                        world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
                else {
                    if(entity.getRevengeTarget() != attacker || !entity.canEntityBeSeen(attacker)){
                        float amount = e.getAmount();
                        e.setAmount(amount * 1.5F);
                        world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);

                    }
                }
            }
            if(item == ModItems.GLASS_SHARD){
                if(livingAttack.getHealth() >= (livingAttack.getMaxHealth() / 2)) {
                    float amount = e.getAmount();
                    e.setAmount(amount * 2F);
                    world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }

            }

            if(item == ModItems.WHETSTONE ){
                entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 1));
            }
            if(item == ModItems.WOLF_AMULET ){
                if(attacker instanceof EntityPlayer && !((EntityPlayer) attacker).getCooldownTracker().hasCooldown(ModItems.WOLF_AMULET)) {
                    EntityPlayer p = (EntityPlayer) attacker;
                    EntitySpiritWolf spiritWolf = new EntitySpiritWolf(world, livingAttack);
                    //attacker = wielder
                    BlockPos blockpos = (new BlockPos(livingAttack)).add(-4 + livingAttack.getRNG().nextInt(8), 1, -4 + livingAttack.getRNG().nextInt(8));
                    spiritWolf.setAttackTarget(entity);
                    spiritWolf.setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                    p.getCooldownTracker().setCooldown(ModItems.WOLF_AMULET, 800);
                    world.spawnEntity(spiritWolf);
                    spiritWolf.setStalkTicks(200);
                }
                if(entity instanceof EntityPlayer){
                    if(!((EntityPlayer) entity).getCooldownTracker().hasCooldown(ModItems.WOLF_AMULET)) {
                        EntityPlayer p = (EntityPlayer)entity;
                        EntitySpiritWolf spiritWolf = new EntitySpiritWolf(world, entity);
                        //victim = wielder
                        BlockPos blockpos = (new BlockPos(entity)).add(-4 +entity.getRNG().nextInt(8), 1, -4 + entity.getRNG().nextInt(8));
                        spiritWolf.setAttackTarget((EntityLivingBase) attacker);
                        spiritWolf.setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                        p.getCooldownTracker().setCooldown(ModItems.WOLF_AMULET, 800);
                        world.spawnEntity(spiritWolf);
                        spiritWolf.setStalkTicks(200);
                    }
                }
            }
            if ((item == ModItems.HORN ) && entity instanceof EntityLiving && entity.isNonBoss() && entity.getRNG().nextInt(3)==0){
                if(!(entity instanceof AbstractTribesmen)) {
                    EntityLiving living = (EntityLiving)entity;
                    List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, living.getEntityBoundingBox().grow(8D), livingBase -> livingBase != attacker);
                    if (!targets.isEmpty() && !world.isRemote) {
                        living.setAttackTarget(null);
                        living.setRevengeTarget(targets.get(0));
                        living.setAttackTarget(targets.get(0));
                        living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100));
                        living.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100));
                    }
                }
                else {
                    AbstractTribesmen t = (AbstractTribesmen) entity;
                    if(!t.isFiery() && !world.isRemote){
                        t.setFire(5);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void oooSpuki(LivingEvent.LivingUpdateEvent event){
        EntityLivingBase e = event.getEntityLiving();
        World world = event.getEntityLiving().world;

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
        if (e.isPotionActive(ModPotions.TARGETED)){
            List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, e.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));

            if(!list.isEmpty() && !world.isRemote ){
                for (EntityMob mob : list){
                    mob.setAttackTarget(e);
                }
            }
        }
    }
    @SubscribeEvent
    public void Confusion (LivingSetAttackTargetEvent e){
        World world = e.getEntityLiving().getEntityWorld();
        EntityLivingBase l = e.getEntityLiving();
        if (!world.isRemote) {
            if (l instanceof EntityMob){
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
            if(l instanceof EntityTameable){
                EntityTameable t = (EntityTameable) l;
                EntityLivingBase p = t.getAttackTarget();
                if(t.getAttackTarget() != null && t.getAttackTarget() instanceof EntityPlayer){
                    EntityPlayer player = (EntityPlayer)p;
                    if(p == null) return;
                    Item item = getActiveItem(player);
                    if(t.isTamed() && item == ModItems.TAMERS_CHARM){
                        t.getNavigator().clearPath();
                        t.setAttackTarget(null);
                    }


                }

                if(t.getAttackTarget() != null && t.getAttackTarget() instanceof EntitySucc){
                    EntitySucc succ = (EntitySucc) p;
                    if(succ == null) return;
                    if(succ.isFriendly()){
                        t.getNavigator().clearPath();
                        t.setAttackTarget(null);
                    }
                }
            }
            if(l instanceof EntityGolem){
                EntityGolem g = (EntityGolem) l;
                EntityLivingBase p = g.getAttackTarget();
                if(g.getAttackTarget() != null && g.getAttackTarget() instanceof EntitySucc){
                    EntitySucc succ = (EntitySucc) p;
                    if(succ == null) return;
                    if(succ.isFriendly()){
                        g.getNavigator().clearPath();
                        g.setAttackTarget(null);
                    }
                }
            }
            if(!(l instanceof EntityMob) && l instanceof EntityLiving){
                EntityLiving m = (EntityLiving) l;
                if(m.getAttackTarget() != null && m.getAttackTarget() instanceof EntitySucc){
                    EntitySucc succ = (EntitySucc) m.getAttackTarget();
                    if(succ == null) return;
                    if(succ.isFriendly() && m.getRevengeTarget() != succ){
                        m.getNavigator().clearPath();
                        m.setAttackTarget(null);
                    }
                }
            }
        }
    }


    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntity().getEntityWorld();
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
        Item item = getActiveItem(entityLiving);
        if(entityLiving.isInWater() && item == ModItems.FISH_TAIL){
            if(entityLiving.motionX < 0.4D){
                entityLiving.motionX *= 1.25D;
            }
            if(entityLiving.motionY < 0.4D){
                entityLiving.motionY *= 1.25D;
            }
            if(entityLiving.motionZ < 0.4D){
                entityLiving.motionZ *= 1.25D;
            }
        }

            if(item == ModItems.FALL_FEATHER){
                if(entityLiving.fallDistance < 5F && entityLiving.fallDistance > 0F) {
                    entityLiving.motionY *= 0.6D;
                }
            }
        if(entityLiving.isPotionActive(ModPotions.CHARMED)){
            if(entityLiving.collidedHorizontally){
                if(entityLiving instanceof EntityLiving)
                {
                   ((EntityLiving) entityLiving).getJumpHelper().setJumping();
                } else if(entityLiving.onGround){
                    entityLiving.motionY = 0.5F;
                }
            }
        }
        if(!world.isRemote){
            if(entityLiving instanceof EntityAnimal){
                EntityAnimal a = (EntityAnimal) entityLiving;
                EntityLivingBase p = a.getAttackTarget();
                if(a.getAttackTarget() != null && a.getAttackTarget() == p) {
                    if((a.getRevengeTarget() != null && a.getRevengeTarget() == p) || a.getRevengeTarget() == null){
                    if(p.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.CHARRED_CLOAK){
                        a.getNavigator().clearPath();
                        a.setAttackTarget(null);
                         }
                    }
                }
            }

            /*if(item == ModItems.AGILITY_TALON){
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.SPEED, 40, 0, true, false));
            }*/

            if(item == ModItems.WARRIORS_LOCK ){
                if(entityLiving.getHealth() < entityLiving.getMaxHealth() / 2F) {
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 1, true, false));
                } else if (entityLiving.getHealth() < entityLiving.getMaxHealth() / 3F){
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 2, true, false));
                } else {
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 0, true, false));
                }
            }
            if(item == ModItems.PICKAXE_AMULET){
                if(world.canSeeSky(entityLiving.getPosition()) && world.isDaytime()) {
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 0, true, false));
                }
            }
            if(item == ModItems.HEART){
                if(entityLiving instanceof EntityPlayer){
                    EntityPlayer p = (EntityPlayer) entityLiving;
                    if(p.getFoodStats().getFoodLevel() > 15  ) {
                        p.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80,1, true, false));
                        if (!p.isPotionActive(MobEffects.HEALTH_BOOST)) {
                            p.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 80, 4, true, false));
                        }

                        else{
                            PotionEffect effect = p.getActivePotionEffect(MobEffects.HEALTH_BOOST);
                            if (effect.getDuration() < 80) {
                                effect.combine(new PotionEffect(MobEffects.HEALTH_BOOST, 80, 4, true, false));
                            }
                        }
                    }
                }
            }
            if(item == ModItems.PROTECTION_TALISMAN){
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 0, true, false));
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 1, true, false));
            }
            if(item == ModItems.HUNGRY_TOOTH){
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 800, 8, true, false));
            }
            if(item == ModItems.PAW ){
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 40, 3, true, false));
            }
            if( entityLiving instanceof EntityPlayer){ //DO NOT ADD A CONDITIONAL FOR THE ARTIFACT HERE, IT'll PREVENT THE MOBS STOP GLOWING AFTER USE
                EntityPlayer player = (EntityPlayer) entityLiving;
                List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, entityLiving.getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D));
                if(!list.isEmpty()) {
                    for (EntityLivingBase targets : list) {
                        //if (!player.canEntityBeSeen(targets)){
                        //  targets.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 40, 0, true, false));//packet then?
                        if (targets != player && getActiveItem(player) == ModItems.HUNTERS_EYE) {
                           targets.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 40, 0, true, false));
                        }

                        //  }
                        if (targets instanceof EntityTameable && ((EntityTameable) targets).getOwner() == player && getActiveItem(player) == ModItems.TAMERS_CHARM) {
                            targets.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 2, true, false));
                            targets.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 1, true, false));
                        }
                    }
                }
            }
        }
        if (entityLiving.isPotionActive(ModPotions.CHARMED)) {
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, entityLiving.getEntityBoundingBox().grow(16.0D));
            for (EntityLivingBase targets : list) {
                if(targets instanceof EntitySucc){
                    EntitySucc succ = (EntitySucc) targets;
                    if(entityLiving.canEntityBeSeen(succ)) {
                        double d0 = succ.posX - entityLiving.posX;
                        double d2 = succ.posZ - entityLiving.posZ;
                        double d1 = succ.posY - entityLiving.posY;
                        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                        float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                        float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));

                        entityLiving.rotationPitch = updateRotation(entityLiving.rotationPitch, f1, 3F);
                        entityLiving.rotationYaw = updateRotation(entityLiving.rotationYaw, f, 3F);
                    }
                }
            }
        }
    }

    public static float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float f = MathHelper.wrapDegrees(targetAngle - angle); // prevents getting negative angles and going beyond 360°
        if (f > maxIncrease) {
            f = maxIncrease;
        }
        if (f < -maxIncrease) {
            f = -maxIncrease;
        }
        return angle + f;
    }

    @SubscribeEvent
    public void Knockback(LivingKnockBackEvent e){
        EntityLivingBase livingBase = e.getEntityLiving();
        Item item = getActiveItem(livingBase);
        if(item == ModItems.ROCK){
            float strenght = e.getStrength();
            e.setStrength(strenght * 0.25F);
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
                List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                if(list.iterator().hasNext()){
                    EntityLivingBase entity = list.get(0);
                    if(entity != null && entity != player){
                        BeastSlayerPacketHandler.sendToServer(new MessageAttackER(player, entity));

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(BeastSlayer.MODID)) {
            ConfigManager.sync(BeastSlayer.MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public void BlockRightClick(PlayerInteractEvent.RightClickBlock e){
        World world = e.getWorld();
        EntityPlayer player = e.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        BlockPos pos = e.getPos();
        IBlockState state = world.getBlockState(pos);
        if(!world.isRemote && world.getBlockState(pos).getBlock() == Blocks.CAULDRON && BeastSlayerConfig.EnableExperimentalFeatures ){
            BlockCauldron cauldron = (BlockCauldron) world.getBlockState(pos).getBlock();
            if(cauldron.getMetaFromState(state) == 3 && stack.getItem() == ModItems.DARK_GOOP){
                world.setBlockState(pos, ModBlocks.CAULDRON.getDefaultState());
                player.swingArm(EnumHand.MAIN_HAND);
                world.playSound(null, pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if(!player.capabilities.isCreativeMode) {
                    player.getHeldItemMainhand().shrink(1);
                }
            }
        }
    }
    @SubscribeEvent
    public void SleepEvents(PlayerSleepInBedEvent event){
        EntityPlayer player = event.getEntityPlayer();
        EntityPlayer.SleepResult result = event.getResultStatus();
        BlockPos location = event.getPos();
        World world = player.getEntityWorld();
        Item item = getActiveItem(player);

        if(item == ModItems.DREAM_CATCHER ){

            List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class,new AxisAlignedBB((double)location.getX() - 8.0D, (double)location.getY() - 5.0D, (double)location.getZ() - 8.0D, (double)location.getX() + 8.0D, (double)location.getY() + 5.0D, (double)location.getZ() + 8.0D));
            if (event.getResultStatus() != null) {
                return;
            }

            if (!list.isEmpty())
            {
                event.setResult(EntityPlayer.SleepResult.NOT_SAFE);
                for(EntityMob mob : list){
                    mob.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 300));
                }
            }

        }
    }

    @SubscribeEvent
    public void LivingDeathEvents(LivingDeathEvent e){
        World world = e.getEntity().getEntityWorld();
        EntityLivingBase entityLivingBase = e.getEntityLiving();
        if(entityLivingBase.isPotionActive(ModPotions.FRENZY) && !world.isRemote){
            entityLivingBase.setHealth(1.0F);
            e.setCanceled(true);
        }
        if(getActiveItem(entityLivingBase)!= null ){

                ItemStack stack = getActiveStack(entityLivingBase);
                if (stack.getItem() == getActiveItem(entityLivingBase) && getActiveItem(entityLivingBase) != null && getActiveItem(entityLivingBase) instanceof ItemArtifact) {
                    //ItemArtifact artifact = (ItemArtifact) getActiveItem(entity);
                    if(stack.getItem() instanceof ItemArtifact  && ((ItemArtifact)stack.getItem()).canBreakOnDeath() && !e.isCanceled()) {

                        //   ItemStack me = new ItemStack(ModItems.BROKEN_ARTIFACT, 1);
                        // BeastSlayer.logger.debug(stack.getItem().toString());
                        //  entity.dropItem(ModItems.BROKEN_ARTIFACT, 1);
                        stack.damageItem(1, entityLivingBase);
                        world.playSound(null, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }

        }
        if(!world.isRemote && e.getSource().getTrueSource() != null){
            Entity source = e.getSource().getTrueSource();
            if(source instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)source;

                if(player.isPotionActive(ModPotions.FRENZY) && player.getActivePotionEffect(ModPotions.FRENZY).getDuration() > 0){
                    int duration = player.getActivePotionEffect(ModPotions.FRENZY).getDuration();
                    player.removePotionEffect(ModPotions.FRENZY);
                    player.addPotionEffect(new PotionEffect(ModPotions.FRENZY,  duration + 40));
                }
                Item item = getActiveItem(player);

                if(item instanceof ItemArtifact ){
                    if(item == ModItems.SOUL_LOCKET ){
                        if(entityLivingBase.isEntityUndead() || entityLivingBase.getRNG().nextInt(3) == 0){
                            EntityWisp wisp = new EntityWisp(world, true, player);
                            wisp.setVariant(entityLivingBase.getRNG().nextInt(3));
                            wisp.moveToBlockPosAndAngles(entityLivingBase.getPosition(), 0.0F, 0.0F);
                            world.spawnEntity(wisp);
                            if(!player.capabilities.isCreativeMode) {
                                player.getHeldItemOffhand().damageItem(1, player);
                            }
                        }
                    }
                    else if(item == ModItems.BLAST_SKULL ){
                        if(entityLivingBase.getRNG().nextInt(3) == 0){

                            player.world.newExplosion(player, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, 1.0F, true, false);
                            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,  entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, 1.0D, 0.0D, 0.0D);

                            Collection<PotionEffect> collection = entityLivingBase.getActivePotionEffects();
                            if (!collection.isEmpty())
                            {
                                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world,  entityLivingBase.posX,  entityLivingBase.posY,  entityLivingBase.posZ);
                                entityareaeffectcloud.setRadius(2.5F);
                                entityareaeffectcloud.setRadiusOnUse(-0.5F);
                                entityareaeffectcloud.setWaitTime(10);
                                entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
                                entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
                                for (PotionEffect potioneffect : collection)
                                {
                                    entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
                                }
                                world.spawnEntity(entityareaeffectcloud);
                            }

                            if(!player.capabilities.isCreativeMode) {
                                player.getHeldItemOffhand().damageItem(1, player);
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void DropEvents(LivingDropsEvent e){
        World world = e.getEntity().getEntityWorld();
        EntityLivingBase entityLivingBase = e.getEntityLiving();
        if(!world.isRemote && e.getSource().getTrueSource() != null) {
            Entity source = e.getSource().getTrueSource();
            if (source instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) source;
                Item item = player.getHeldItemOffhand().getItem();
                Item item2 = null;
                if(!IntegrationBaubles.getEquippedArtifacts(player, ItemArtifact.baubleSlot.CHARM).isEmpty()) {
                    item2 = IntegrationBaubles.getEquippedArtifacts(player, ItemArtifact.baubleSlot.CHARM).get(0);
                }
                if (item instanceof ItemArtifact || item2 != null) {
                    if(item == ModItems.BOUNTIFUL_SACK || item2 == ModItems.BOUNTIFUL_SACK){
                        if(!e.getDrops().isEmpty() && player.getRNG().nextInt(2) == 0) {
                            EntityItem drop = new EntityItem(world);
                            drop.setItem(new ItemStack(e.getDrops().get(entityLivingBase.getRNG().nextInt(e.getDrops().size())).getItem().getItem(), 1));
                            drop.setPosition(entityLivingBase.getPosition().getX(), entityLivingBase.getPosition().getY(), entityLivingBase.getPosition().getZ());
                            world.spawnEntity(drop);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void XPEvents(LivingExperienceDropEvent e){
        World world = e.getEntity().getEntityWorld();
        if(!world.isRemote && e.getAttackingPlayer() != null) {
            EntityPlayer player = e.getAttackingPlayer();
             int xp = e.getOriginalExperience();


                List<EntitySucc> list = player.world.getEntitiesWithinAABB(EntitySucc.class, player.getEntityBoundingBox().grow(40D));
                if(!list.isEmpty())
                {
                    for (EntitySucc entitySucc : list) {
                        if(entitySucc.isFriend(player)) {

                            e.setDroppedExperience(xp / 2);

                            EntitySuccXp xp1 = new EntitySuccXp(world, e.getEntity().getPosition().getX(), e.getEntity().getPosition().getY() + e.getEntity().height, e.getEntity().getPosition().getZ(), e.getDroppedExperience(), entitySucc);
                            world.spawnEntity(xp1);
                        }
                    }
                }


                if (getActiveItem(player) == ModItems.BOUNTIFUL_SACK) {
                    if(!(e.getEntity() instanceof EntityMosquito)) {
                        e.setDroppedExperience(xp * 2);
                    } else {
                        e.setDroppedExperience(xp + 5);
                    }
                }
                if(player.getHeldItemOffhand().getItem() == ModItems.HEART_AMULET && player.getHeldItemOffhand().isItemDamaged()){
                    e.setDroppedExperience(xp /4 * 3);
                    ItemStack itemstack = player.getHeldItemOffhand();
                    if(itemstack.getItemDamage() - xp / 4 > 0) {
                        itemstack.setItemDamage(itemstack.getItemDamage() - xp / 4);
                    } else {
                        itemstack.setItemDamage(0);
                    }
                }
            if(player.getHeldItemMainhand().getItem() == ModItems.HEART_AMULET && player.getHeldItemMainhand().isItemDamaged()){
                e.setDroppedExperience(xp /4 * 3);
                ItemStack itemstack = player.getHeldItemMainhand();
                int i = xp / 4;
                if(itemstack.getItemDamage() - xp / 4 > 0) {
                    if(xp / 4 < 1){
                        i = 1;
                    }
                    itemstack.setItemDamage(itemstack.getItemDamage() - i);
                } else {
                    itemstack.setItemDamage(0);
                }
            }
        }

    }

    @SubscribeEvent
    public void healEvents (LivingHealEvent e){
        EntityLivingBase entityLivingBase = e.getEntityLiving();
        World world = e.getEntityLiving().getEntityWorld();
        if(entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            if(!world.isRemote) {
                List<EntitySucc> list = player.world.getEntitiesWithinAABB(EntitySucc.class, player.getEntityBoundingBox().grow(40D));
                if(!list.isEmpty()){
                    for (EntitySucc entitySucc : list) {
                        if(entitySucc.isFriend(player) && entitySucc.getEat() <= -1200 && entitySucc.getEat() < entitySucc.getGive() && entitySucc.getEat() < entitySucc.getSell()) {
                            e.setAmount(e.getAmount() * 0.7F);
                        }
                    }
                }
            }
        }
    }

    public Item getActiveItem(EntityLivingBase player){
        Item item = player.getHeldItemOffhand().getItem();
        Item item2 = null;
        if(player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            if (!IntegrationBaubles.getEquippedArtifacts(p, ItemArtifact.baubleSlot.CHARM).isEmpty()) {
                item2 = IntegrationBaubles.getEquippedArtifacts(p, ItemArtifact.baubleSlot.CHARM).get(0);
            }
        }
        return item2 != null ? item2 : item;
    }

    public ItemStack getActiveStack(EntityLivingBase player){
        ItemStack item = player.getHeldItemOffhand();
        ItemStack item2 = null;
        if(player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            if (!IntegrationBaubles.getEquippedArtifacts(p, ItemArtifact.baubleSlot.CHARM).isEmpty()) {
                item2 = IntegrationBaubles.getArtifactItemstack(p, ItemArtifact.baubleSlot.CHARM);
            }
        }
        return item2 != null ? item2 : item;
    }

}
