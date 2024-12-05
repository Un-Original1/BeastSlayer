package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.AIPlay;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityTribeChild extends AbstractTribesmen{
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTribeChild.class, DataSerializers.VARINT);
    private boolean isPlaying;
    protected int growingAge;

    public EntityTribeChild(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 1.2F);
        this.experienceValue = 0;

    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(6, new AIPlay(this, 0.32D));

    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    public void setPlaying(boolean playing)
    {
        this.isPlaying = playing;
    }

    public boolean isPlaying()
    {
        return this.isPlaying;
    }

    public int getGrowingAge()
    {
        return this.growingAge;
    }

    public void ageUp(int growthSeconds, boolean updateForcedAge)
    {
        int i = this.getGrowingAge();
        int j = i;
        i = i + growthSeconds * 20;

        if (i > 0)
        {
            i = 0;

            if (j < 0)
            {
                this.onGrowingAdult();
            }
        }

        int k = i - j;
        this.setGrowingAge(i);
    }

    public void setGrowingAge(int age)
    {
        this.growingAge = age;
    }

    protected void onGrowingAdult()
    {
        if(!this.world.isRemote) {
            switch (this.getVariant()) {
                case 0:
                default:
                    EntityTribeWarrior tribeWarrior = new EntityTribeWarrior(world);
                    tribeWarrior.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                    this.world.spawnEntity(tribeWarrior);
                    break;
                case 1:
                    EntityHunter hunter = new EntityHunter(world);
                    hunter.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                    this.world.spawnEntity(hunter);
                    break;
                case 2:
                    EntityTank tank = new EntityTank(world);
                    tank.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                    this.world.spawnEntity(tank);
                    break;
                case 3:
                    EntityPriest priest = new EntityPriest(world);
                    priest.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                    this.world.spawnEntity(priest);
                    break;
            }
            this.setDead();
        }
    }


    public static int getRandVariant(Random rand)
    {
        int i = rand.nextInt(4);

        return i;

    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 3);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
        compound.setInteger("Age", this.getGrowingAge());

    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
        this.setGrowingAge(compound.getInteger("Age"));
    }
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            int i = this.getGrowingAge();

            if (i < 0) {
                ++i;
                this.setGrowingAge(i);

                if (i == 0) {
                    this.onGrowingAdult();
                }
            } else if (i > 0) {
                --i;
                this.setGrowingAge(i);
            }

            if(this.isFiery()){
                this.setHealth(0.0F);
            }
        }
    }

    public boolean shouldTradeWithplayer(EntityPlayer player){
        return false;
    }


    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setGrowingAge(-24000);
        this.setVariant(getRandVariant(this.world.rand));
        return livingdata;
    }
}
