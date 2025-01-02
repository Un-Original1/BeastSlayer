package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.entity.Entities.ai.AIRestrictLeaves;
import com.unoriginal.beastslayer.entity.Entities.ai.navigation.PathNavigateAvoidLeaves;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.items.ItemMask;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class AbstractTribesmen extends EntityMob {
    private ResourceLocation sellingTable;
    private ResourceLocation treasureTable;
    private long sellingTableSeed;
    private long treasureTableSeed;
    public int fieryTicks;
    protected static final DataParameter<Boolean> FIERY = EntityDataManager.createKey(AbstractTribesmen.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> TRADING = EntityDataManager.createKey(AbstractTribesmen.class, DataSerializers.BOOLEAN);
    private static final Set<Block> SPAWNBLOCKS = Sets.newHashSet(ModBlocks.CURSED_PLANK, Blocks.GRASS, ModBlocks.CURSED_SLAB_HALF, ModBlocks.STICK, Blocks.STONE, Blocks.FARMLAND, Blocks.MELON_BLOCK, ModBlocks.CURSED_STAIR);
    private EntityAITempt aiTempt;
    public int tradeTicks;
    private EntityPlayer player;
    private UUID tradingPlayer;

    public AbstractTribesmen(World worldIn) {
        super(worldIn);
        this.setFiery(false);
        this.isImmuneToFire = this.isFiery();
        this.player = null;
        this.tradingPlayer = null;
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(FIERY, false);
        this.dataManager.register(TRADING, false);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        aiTempt = new EntityAITempt(this, 0.6D, ModItems.CURSED_WOOD, true);
        this.tasks.addTask(3, this.aiTempt);

        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(0, new AIRestrictLeaves(this));

        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, AbstractTribesmen.class, p_apply_1_ -> p_apply_1_.isFiery() && p_apply_1_ != this && !this.isFiery(),this.avoidDistance(20F), 1.0F, 1.5F));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, Predicate -> !this.isFiery() && this.getHealth() < this.getMaxHealth() / 4F,8.0F, 1.0, 1.2));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, AbstractTribesmen.class, 10, true, false, p_apply_1_ -> !p_apply_1_.isFiery() && this.isFiery()));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityPlayer.class, 10, true, false, p_apply_1_ -> this.isFiery()));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, 10, true, false, p_apply_1_ -> this.isFiery()));
    }

    public void onUpdate() {
        super.onUpdate();
        if(!this.isFiery() && !this.world.isRemote && this.ticksExisted % 10 == 0 ){
            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(8D));
            if(!players.isEmpty()){
                for (EntityPlayer player : players){
                    if(player.inventory != null && player.inventory.hasItemStack(new ItemStack(ModBlocks.FIRE_IDOL))){
                        this.setAttackTarget(player);
                        break;
                    }
                }
            }
        }
        if(this.isBurning() && !this.isFiery())
        {
            this.setFiery(true);
            this.fieryTicks = 20;
            this.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 10, false, false));
            this.world.setEntityState(this, (byte)5);
            if (this.world.isRemote)
            {
                for (int i = 0; i < 30; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
                }
            }
            this.setRevengeTarget(null);
            this.getHeldItemMainhand().setCount(0);
            this.getHeldItemOffhand().setCount(0);
            this.extinguish();
        }
        if(this.fieryTicks > 0){
            --this.fieryTicks;
        }
        if(this.isFiery()){
            EntityLivingBase base = this.getAttackTarget();
            if(base instanceof EntityFireElemental){
                if(this.isFiery()) {
                    this.setAttackTarget(null);
                }

            }
            if(this.isWet()) {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }
            if(this.getAttackTarget() instanceof AbstractTribesmen && !this.world.isRemote)
            {
                AbstractTribesmen tribesmen = (AbstractTribesmen) this.getAttackTarget();
                if(tribesmen.isFiery()){
                    this.setAttackTarget(null);
                    this.setRevengeTarget(null);
                }
            }
            if(this.isBurning()){
                this.heal(1.0F);
            }
            if(this.world.isRemote){
                for (int i = 0; i < 1; ++i)
                {
                    double d0 = this.posX + rand.nextDouble() - 0.5D;
                    double d1 = this.posY + rand.nextDouble() * 0.5D + 0.5D;
                    double d2 = this.posZ + rand.nextDouble() - 0.5D;
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    this.world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0D, 0D, 0D);
                }
            }
        }
        if(this.isTrading() && !this.isFiery()){
            if(--this.tradeTicks <= 0 && !this.world.isRemote){
                this.dropBartering(this.getBuyer());
                this.swingArm(EnumHand.MAIN_HAND);
                this.setTrading(false);
                this.getHeldItemMainhand().setCount(0);
                Item item = this.getHeldItemOffhand().getItem();
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(item));
                this.getHeldItemOffhand().setCount(0);
            }
        }
        if(this.getAttackTarget() instanceof AbstractTribesmen && !this.isFiery())
        {
            AbstractTribesmen tribesmen = (AbstractTribesmen)this.getAttackTarget();
            if(!tribesmen.isFiery() && !this.world.isRemote){
                this.setAttackTarget(null);
                this.setRevengeTarget(null);
            }
        }
    }
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        super.onCollideWithPlayer(entityIn);
        if(!this.world.isRemote && shouldTradeWithplayer(entityIn)){
            this.getHeldItemMainhand();
            Item item = this.getHeldItemMainhand().getItem();

            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(item));
            entityIn.getHeldItemMainhand().shrink(1);
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.CURSED_WOOD));
            this.tradeTicks = 80;
            this.player = entityIn;
            this.tradingPlayer = entityIn.getUniqueID();
            this.setTrading(true);
        }
    }

    public boolean shouldTradeWithplayer(EntityPlayer player){
        return player.getHeldItemMainhand().getItem() == ModItems.CURSED_WOOD && !this.isFiery() && !this.isTrading();
    }

    public boolean isFiery()
    {

        return this.dataManager.get(FIERY);
    }

    protected void setFiery(boolean f)
    {
        this.dataManager.set(FIERY, f);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag && this.getHeldItemMainhand().isEmpty() && entityIn instanceof EntityLivingBase)
        {
            if(this.isFiery()){
                entityIn.setFire(2);
            }
        }
        if(entityIn instanceof EntityFireElemental && this.isFiery()){
            return false;
        }
        return flag;
    }

    public boolean isTrading()
    {

        return this.dataManager.get(TRADING);
    }

    protected void setTrading(boolean t)
    {
        this.dataManager.set(TRADING, t);
    }


    @Nullable
    protected ResourceLocation getBarteringTable()
    {
        return null;
    }

    protected void dropBartering( EntityPlayer player)
    {
        if(player == null){
            return; //this shouldn't happen but to prevent crashes
        }

        Item item = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();

        int i = 6;
        boolean b = false;
        if(item instanceof ItemMask){
            i = 6 - ((ItemMask) item).getTier() + this.getTreasureTier();
            b = ((ItemMask) item).getTier() >= this.getTreasureTier();
        }

        ResourceLocation resourcelocation = this.sellingTable;

        if (resourcelocation == null)
        {
            resourcelocation = this.getBarteringTable();
        }

        ResourceLocation resourceLocation2 = this.treasureTable;

        if(resourceLocation2 == null){
            resourceLocation2 = this.getTreasureTable();
        }

        if (resourcelocation != null && resourceLocation2 != null) {
            LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
            this.sellingTable = null;

            LootTable loottable2 = this.world.getLootTableManager().getLootTableFromLocation(resourceLocation2);
            this.treasureTable= null;

            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.world);

            List<ItemStack> itemlist = loottable.generateLootForPools(this.sellingTableSeed == 0L ? this.rand : new Random(this.sellingTableSeed), lootcontext$builder.build());

            List<ItemStack> treasure = loottable2.generateLootForPools(this.treasureTableSeed == 0L ? this.rand : new Random(this.treasureTableSeed), lootcontext$builder.build());
            if(rand.nextInt(i) == 0 && b) {
                if (!treasure.isEmpty()) {
                    ItemStack itemstack = treasure.get(0);
                    this.entityDropItem(itemstack, 0.0F);
                }
            } else {

                if (!itemlist.isEmpty()) {
                    ItemStack itemstack = itemlist.get(0);
                    this.entityDropItem(itemstack, 0.0F);
                }
            }

        }
    }

    @Nullable
    protected ResourceLocation getTreasureTable()
    {
        return null;
    }

    @Override
    public boolean canDespawn(){
        return false;
    }

    public int getTreasureTier(){
        return 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.sellingTable != null)
        {
            compound.setString("SellingTable", this.sellingTable.toString());

            if (this.sellingTableSeed != 0L)
            {
                compound.setLong("SellingTableSeed", this.sellingTableSeed);
            }
        }
        if (this.treasureTable != null)
        {
            compound.setString("TTable", this.treasureTable.toString());

            if (this.treasureTableSeed != 0L)
            {
                compound.setLong("TTableSeed", this.treasureTableSeed);
            }
        }
        compound.setBoolean("Fiery", this.isFiery());
        compound.setBoolean("Trading", this.isTrading());
        if(this.tradingPlayer != null){
            compound.setUniqueId("Buyer", this.tradingPlayer);
        }

        compound.setInteger("TradeTicks", this.tradeTicks);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("SellingTable", 8))
        {
            this.sellingTable = new ResourceLocation(compound.getString("SellingTable"));
            this.sellingTableSeed = compound.getLong("SellingTableSeed");
        }
        if (compound.hasKey("TTable", 8))
        {
            this.sellingTable = new ResourceLocation(compound.getString("TTable"));
            this.treasureTableSeed = compound.getLong("TTableSeed");
        }
        if(compound.hasKey("Fiery")){
            this.dataManager.set(FIERY, compound.getBoolean("Fiery"));
        }
        if(compound.hasKey("Trading")){
            this.dataManager.set(TRADING, compound.getBoolean("Trading"));
        }
        if(compound.hasKey("TradeTicks")){
            this.tradeTicks = compound.getInteger("TradeTicks");
        }
        if(compound.hasUniqueId("Buyer")){
            this.tradingPlayer = compound.getUniqueId("Buyer");
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return this.isFiery() ? 15728880 : super.getBrightnessForRender();
    }

    public float getBrightness()
    {
        return this.isFiery() ? 1.0F : super.getBrightness();
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 5) {
            this.fieryTicks = 30;

            for (int i = 0; i < 50; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5D);
            }

        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || source == DamageSource.FALL)
        {
            return false;
        }
        else
        {
            if(!this.isFiery()) {
                List<AbstractTribesmen> tribesmen = this.world.getEntitiesWithinAABB(AbstractTribesmen.class, this.getEntityBoundingBox().grow(10D));
                for(AbstractTribesmen t : tribesmen){
                    if(!t.isFiery() && !(source.getTrueSource() instanceof AbstractTribesmen)){
                        if (source.getTrueSource() instanceof EntityLivingBase) {
                            EntityLivingBase l = (EntityLivingBase) source.getTrueSource();
                            if (l instanceof EntityPlayer){
                                EntityPlayer p = (EntityPlayer)l;
                                if (!p.isCreative()){
                                    t.setAttackTarget(p);
                                }
                            } else {
                                t.setAttackTarget(l);
                            }
                        }
                    }
                }
            }
            if(this.isFiery()) {
                List<AbstractTribesmen> tribesmen = this.world.getEntitiesWithinAABB(AbstractTribesmen.class, this.getEntityBoundingBox().grow(10D));
                for(AbstractTribesmen t : tribesmen){
                    if(t.isFiery() && source.getTrueSource() instanceof EntityLivingBase){
                        EntityLivingBase l = (EntityLivingBase)source.getTrueSource();
                        t.setAttackTarget(l);
                    }
                }
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    public float avoidDistance(float distance){
        return distance;
    }

    @SideOnly(Side.CLIENT)
    public int getFieryTicks(){
        return this.fieryTicks;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if(this.isFiery()){
            return entityIn instanceof EntityFireElemental;
        } else {
            if(entityIn instanceof AbstractTribesmen){
                if(!((AbstractTribesmen) entityIn).isFiery()){
                    return this.getTeam() == null && entityIn.getTeam() == null;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public EntityPlayer getBuyer(){
        if (this.player == null && this.tradingPlayer != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.tradingPlayer);
            if (entity instanceof EntityPlayer) {
                this.player = (EntityPlayer) entity;
            }
        }

        return this.player;
    }

    @Override
    protected boolean isValidLightLevel()
    {
       return true;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return 0.5F - (this.world.getBlockState(pos).getBlock() instanceof BlockLeaves  ? 50F : 0F);
    }

    @Override
    public boolean getCanSpawnHere() {
        boolean b = false;

        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                Chunk chunk = world.getChunkFromBlockCoords(this.getPosition());
                boolean validspawn = this.IsVillageAtPos(this.world, chunk.x + i, chunk.z + j);
                if(validspawn){
                    b = true;
                    break;
                }
            }
        }
      //  boolean b = chunk.equals(  TribeSavedData.loadData(this.world).getLocation());
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        //BeastSlayer.logger.debug(this.world.getBlockState(blockpos).getBlock());

        return blockpos.getY() >= 85 && b;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 5;
    }

    protected boolean IsVillageAtPos(World world, int chunkX, int chunkZ) {
        int spacing = 30;
        int separation = 16;
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= spacing - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random =  world.setRandomSeed(k, l, 10387312);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
        l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;

        if (i == k && j == l)
        {

            return world.getBiomeProvider().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, Lists.newArrayList(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE))); /*&& random.nextInt(20) == 0*/
        } else {

            return false;
        }
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        return false;
    }
    @Override
    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateAvoidLeaves(this, worldIn);
    }

}
