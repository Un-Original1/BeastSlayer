package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.EntityAIMeleeConditional;
import com.unoriginal.beastslayer.entity.Entities.magic.CastingMagic;
import com.unoriginal.beastslayer.entity.Entities.magic.MagicType;
import com.unoriginal.beastslayer.entity.Entities.magic.UseMagic;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.util.IMagicUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityPriest extends AbstractTribesmen implements IMagicUser {
    private static final DataParameter<Boolean> MAGIC_CAST = EntityDataManager.createKey(EntityPriest.class, DataSerializers.BOOLEAN);
    private int magicUseTicks;
    private int CDTicks;
    public EntityPriest(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 1.8F);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D * BeastSlayerConfig.GlobalHealthMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D + BeastSlayerConfig.GlobalArmor);
    }

    @Override
    protected void updateAITasks(){
        super.updateAITasks();
        if (this.magicUseTicks > 0) {
            --this.magicUseTicks;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(MAGIC_CAST, Boolean.FALSE);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new CastingMagic<>(this));
        this.tasks.addTask(3, new AITarget(this));
        this.tasks.addTask(3, new EntityAIMeleeConditional(this, 1.2F, false, Predicate -> this.CDTicks <= 0));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, Predicate -> !this.isFiery() && this.CDTicks > 0 ,8.0F, 1.0, 1.2));
        this.tasks.addTask(4, new EntityPriest.AIWisp(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, AbstractTribesmen.class, 10, true, false, p_apply_1_ -> p_apply_1_.isFiery() && !this.isFiery()));
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.STAFF));

    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return livingdata;
    }
    public void setMagicCast(boolean magicCast)
    {
        this.dataManager.set(MAGIC_CAST, magicCast);
    }

    @SideOnly(Side.CLIENT)
    public boolean isCastingMagic()
    {
        return this.dataManager.get(MAGIC_CAST);
    }

    @Override
    public boolean isUsingMagic() {
        return this.dataManager.get(MAGIC_CAST);
    }

    @Override
    public int getMagicUseTicks() {
        return this.magicUseTicks;
    }

    @Override
    public void setMagicUseTicks(int magicUseTicks) {
        this.magicUseTicks = magicUseTicks;
    }

    @Override
    public MagicType getMagicType() {
        return null;
    }

    @Override
    public void setMagicType(MagicType spellTypeIn) {

    }

    class AIWisp extends UseMagic<EntityPriest> {
        protected  AIWisp(EntityPriest magicUserMob) {
            super(magicUserMob);
        }

        public void updateTask() {
            super.updateTask();
            if(this.magicWarmup < 100) {
                EntityPriest.this.setMagicCast(true);
            }
        }

        @Override
        public boolean shouldExecute() {
            List<EntityWisp> list = EntityPriest.this.world.getEntitiesWithinAABB(EntityWisp.class, EntityPriest.this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));

            return list.size() < 5 && super.shouldExecute();
        }

        @Override
        protected void useMagic() {
            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos = (new BlockPos(EntityPriest.this)).add(-4 + EntityPriest.this.rand.nextInt(8), 1, -4 + EntityPriest.this.rand.nextInt(8));

                EntityWisp wisp = new EntityWisp(EntityPriest.this.world);
                wisp.moveToBlockPosAndAngles(blockpos, 0, 0);
                if(EntityPriest.this.isFiery()){
                    wisp.setVariant(3);
                } else if(EntityPriest.this.getAttackTarget() instanceof AbstractTribesmen && ((AbstractTribesmen) EntityPriest.this.getAttackTarget()).isFiery()) {
                    wisp.setVariant(2);
                } else {
                    wisp.setVariant(rand.nextInt(3));
                }
                EntityPriest.this.world.spawnEntity(wisp);
            }
        }
        @Override
        public void resetTask() {
            super.resetTask();
            EntityPriest.this.setMagicCast(false);
        }

        @Override
        protected int getMagicUseTime() {
            return 200;
        }

        @Override
        protected int getMagicUseInterval() {
            return 400;
        }

        @Nullable
        @Override
        protected SoundEvent getMagicPrepareSound() {
            return null;
        }

        @Override
        protected MagicType getMagicType() {
            return null;
        }
    }

    class AITarget extends UseMagic<EntityPriest> {
        protected  AITarget(EntityPriest magicUserMob) {
            super(magicUserMob);
        }

        public void updateTask() {
            super.updateTask();
            if(this.magicWarmup < 100) {
                EntityPriest.this.setMagicCast(true);
            }
        }

        @Override
        public boolean shouldExecute() {
            List<EntityMob> list = EntityPriest.this.world.getEntitiesWithinAABB(EntityMob.class, EntityPriest.this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));

            return list.size() > 2 && super.shouldExecute();
        }

        @Override
        protected void useMagic() {
            List<EntityMob> list = EntityPriest.this.world.getEntitiesWithinAABB(EntityMob.class, EntityPriest.this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));

            if(!list.isEmpty() && EntityPriest.this.getAttackTarget() != null){
                for (EntityMob mob : list){
                    mob.setAttackTarget(EntityPriest.this.getAttackTarget());
                }
            }
        }
        @Override
        public void resetTask() {
            super.resetTask();
            EntityPriest.this.setMagicCast(false);
        }

        @Override
        protected int getMagicUseTime() {
            return 100;
        }

        @Override
        protected int getMagicUseInterval() {
            return 300;
        }

        @Nullable
        @Override
        protected SoundEvent getMagicPrepareSound() {
            return null;
        }

        @Override
        protected MagicType getMagicType() {
            return null;
        }
    }
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("MagicUseTicks", this.magicUseTicks);
        compound.setInteger("CD", this.CDTicks);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if(!this.isFiery()){
            this.CDTicks = 200 + rand.nextInt(200);
        } else {
            this.CDTicks = rand.nextInt(200);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.CDTicks >= 0){
            --this.CDTicks;
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.magicUseTicks = compound.getInteger("MagicUseTicks");
        this.CDTicks = compound.getInteger("CD");
    }
}
