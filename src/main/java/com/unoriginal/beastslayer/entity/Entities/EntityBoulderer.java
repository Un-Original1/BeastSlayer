package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIBurrow;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAILeapLadder;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityBoulderer extends EntityZombie {

    private int buriedTicks;
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityBoulderer.class, DataSerializers.BYTE);

    private EntityAIBurrow entityAIBurrow;

    public EntityBoulderer(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1.95F);
    }
    protected ItemStack getSkullDrop()
    {
        return ItemStack.EMPTY;
    }

    protected void applyEntityAI()
    {
        super.applyEntityAI();
        this.entityAIBurrow = new EntityAIBurrow(this, 200, true);
        this.tasks.addTask(5, this.entityAIBurrow);
        this.tasks.addTask(3, new EntityAILeapLadder(this, 0.4F));
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
        this.buriedTicks = this.entityAIBurrow.getBuriedTimer();
    }

    public boolean isChild(){return false;}

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isBuried() && world.isRemote) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
            if (iblockstate.getMaterial() != Material.AIR) {
                for(int x = 0; x < 4; x++) {
                    this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.02D, Block.getStateId(iblockstate));
                }
                if (!world.isRemote && this.ticksExisted % 150 == 0 ) {
                    this.playSound(iblockstate.getBlock().getSoundType().getBreakSound(), 1.0F, 1.0F);
                }
            }
        }
        if (this.world.isRemote)
        {
            this.buriedTicks = Math.max(0, this.buriedTicks - 1);
        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        float f = difficulty.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

        this.setBreakDoorsAItask(this.rand.nextFloat() < f * 0.1F);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);

        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
        double d0 = this.rand.nextDouble() * 1.5D * (double)f;

        if (d0 > 1.0D)
        {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        if (this.rand.nextFloat() < f * 0.05F)
        {
            this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
            this.setBreakDoorsAItask(true);
        }
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Health bonus", BeastSlayerConfig.GlobalDamageMultiplier, 2));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier("Armor bonus", BeastSlayerConfig.GlobalArmor, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("Damage bonus", BeastSlayerConfig.GlobalDamageMultiplier, 2));
        return livingdata;
    }


    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || this.isBuried())
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Buried", this.isBuried());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }


    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.buriedTicks = 200;
        }
        if (id == 12) {
            this.buriedTicks = 0;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    public boolean isBuried(){ return this.buriedTicks > 0; }

    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock()
    {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty())
        {
            float f = this.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;

            boolean flag = true;

            for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
            {
                if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
                {
                    ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                    if (!flag && this.rand.nextFloat() < f)
                    {
                        break;
                    }

                    flag = false;

                    if (itemstack.isEmpty())
                    {
                        Item item = ModItems.MINER_HELMET;

                        if (item != null)
                        {
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.posY > 10.0D && this.posY < 40.0D && this.dimension == 0;
    }
    protected float getSoundPitch()
    {
        return 0.8F;
    }

    public void onDeath(DamageSource cause)
    {
        if(!world.isRemote){
            this.dropEquipment(true, 60);
        }
        super.onDeath(cause);
    }
}
