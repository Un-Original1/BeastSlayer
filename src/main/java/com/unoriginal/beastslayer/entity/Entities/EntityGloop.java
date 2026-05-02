package com.unoriginal.beastslayer.entity.Entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGloop extends EntityAnimal {
    private static final DataParameter<Integer> BALLOON = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BUBBLE = EntityDataManager.createKey(EntityGloop.class, DataSerializers.VARINT);


    public EntityGloop(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1D));
        this.tasks.addTask(4, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BALLOON, 0);
        this.dataManager.register(BUBBLE, 0);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityGloop(this.world);
    }

    @Override
    public float getEyeHeight() {
        return this.isChild()? 0.15F : 0.3F;
    }
}
