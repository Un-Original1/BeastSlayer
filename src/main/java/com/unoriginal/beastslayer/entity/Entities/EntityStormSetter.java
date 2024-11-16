package com.unoriginal.beastslayer.entity.Entities;

import com.google.common.base.Predicate;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EntityStormSetter extends EntityThrowable
{
    private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityStormSetter.class, DataSerializers.ITEM_STACK);
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Predicate<EntityLivingBase> WATER_SENSITIVE = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return EntityStormSetter.isWaterSensitiveEntity(p_apply_1_);
        }
    };

    public EntityStormSetter(World worldIn)
    {
        super(worldIn);
    }

    public EntityStormSetter(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn)
    {
        super(worldIn, throwerIn);
        this.setItem(potionDamageIn);
    }

    public EntityStormSetter(World worldIn, double x, double y, double z, ItemStack potionDamageIn)
    {
        super(worldIn, x, y, z);

        if (!potionDamageIn.isEmpty())
        {
            this.setItem(potionDamageIn);
        }
    }

    protected void entityInit()
    {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
    }

    public ItemStack getPotion()
    {
        ItemStack itemstack = this.getDataManager().get(ITEM);

        if (itemstack.getItem() != ModItems.STORM_BOTTLE)
        {
            if (this.world != null)
            {
                LOGGER.error("ThrownPotion entity {} has no item?!", this.getEntityId());
            }

            return new ItemStack(ModItems.STORM_BOTTLE);
        }
        else
        {
            return itemstack;
        }
    }

    public void setItem(ItemStack stack)
    {
        this.getDataManager().set(ITEM, stack);
        this.getDataManager().setDirty(ITEM);
    }

    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.getPotion();
            int i = itemstack.getMetadata();
            if (result.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = result.getBlockPos().offset(result.sideHit);
                this.extinguishFires(blockpos, result.sideHit);

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    this.extinguishFires(blockpos.offset(enumfacing), enumfacing);
                }
            }
            this.applyWater(i);
            this.setDead();
            this.world.playEvent(2002, new BlockPos(this), 3694022);
        }
    }

    private void applyWater(int typein)
    {
        int i = (300 + (new Random()).nextInt(600)) * 10;
        WorldInfo worldinfo = world.getWorldInfo();
        if(!this.world.isRemote) {
            switch (typein) {

                case 0:

                    worldinfo.setCleanWeatherTime(0);
                    worldinfo.setRainTime(i);
                    worldinfo.setThunderTime(i);
                    worldinfo.setRaining(true);
                    worldinfo.setThundering(true);
                    EntityLightningBolt bolt = new EntityLightningBolt(world, this.posX, this.posY, this.posZ, false);
                    world.spawnEntity(bolt);
                    world.addWeatherEffect(bolt);

                    break;
                case 1:
                    worldinfo.setCleanWeatherTime(0);
                    worldinfo.setRainTime(i);
                    worldinfo.setThunderTime(i);
                    worldinfo.setRaining(true);
                    worldinfo.setThundering(false);
                    break;
                case 2:
                    worldinfo.setCleanWeatherTime(i);
                    worldinfo.setRainTime(0);
                    worldinfo.setThunderTime(0);
                    worldinfo.setRaining(false);
                    worldinfo.setThundering(false);
                    break;
            }
        }
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, WATER_SENSITIVE);

        if (!list.isEmpty())
        {
            for (EntityLivingBase entitylivingbase : list)
            {
                double d0 = this.getDistanceSq(entitylivingbase);

                if (d0 < 16.0D && isWaterSensitiveEntity(entitylivingbase))
                {
                    entitylivingbase.attackEntityFrom(DamageSource.DROWN, 1.0F);
                }
            }
        }
    }


    private void extinguishFires(BlockPos pos, EnumFacing p_184542_2_)
    {
        if (this.world.getBlockState(pos).getBlock() == Blocks.FIRE)
        {
            this.world.extinguishFire(null, pos.offset(p_184542_2_), p_184542_2_.getOpposite());
        }
    }


    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        ItemStack itemstack = new ItemStack(compound.getCompoundTag("Potion"));

        if (itemstack.isEmpty())
        {
            this.setDead();
        }
        else
        {
            this.setItem(itemstack);
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        ItemStack itemstack = this.getPotion();

        if (!itemstack.isEmpty())
        {
            compound.setTag("Potion", itemstack.writeToNBT(new NBTTagCompound()));
        }
    }

    private static boolean isWaterSensitiveEntity(EntityLivingBase p_190544_0_)
    {
        return p_190544_0_ instanceof EntityEnderman || p_190544_0_ instanceof EntityBlaze;
    }
}
