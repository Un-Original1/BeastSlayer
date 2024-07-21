package com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental;

import com.unoriginal.beastslayer.animation.EZAnimation;
import com.unoriginal.beastslayer.animation.EZAnimationHandler;
import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.AbstractTribesmen;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.IAttack;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.fire_elemental.FireElementalAI;
import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.action.ActionSummonMinions;
import com.unoriginal.beastslayer.entity.Entities.boss.util.BossUtil;
import com.unoriginal.beastslayer.util.ModRand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityFireElemental extends EntityAbstractBoss implements IAttack, IAnimatedEntity {
    public static final EZAnimation ANIMATION_PUNCH = EZAnimation.create(55);
    public static final EZAnimation ANIMATION_SMASH_GROUND = EZAnimation.create(50);
    public static final EZAnimation ANIMATION_SUMMONS = EZAnimation.create(70);
    public static final EZAnimation ANIMATION_GET_OVER_HERE = EZAnimation.create(60);
    public static final EZAnimation ANIMATION_PUSH = EZAnimation.create(60);

    protected static final DataParameter<Boolean> PUNCH_ATTACK = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SMASH_GROUND_ATTACK = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SUMMON_MINIONS_ATTACK = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> GET_OVER_HERE_ATTACK = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> PUSH_ATTACK = EntityDataManager.createKey(EntityAbstractBoss.class, DataSerializers.BOOLEAN);
    public boolean isPunchAttack() {return this.dataManager.get(PUNCH_ATTACK);}
    public void setPunchAttack(boolean value) {this.dataManager.set(PUNCH_ATTACK, Boolean.valueOf(value));}
    public boolean isSmashGroundAttack() {return this.dataManager.get(SMASH_GROUND_ATTACK);}
    public void setSmashGroundAttack(boolean value) {this.dataManager.set(SMASH_GROUND_ATTACK, Boolean.valueOf(value));}
    public boolean isSummonMinionsAttack() {return this.dataManager.get(SUMMON_MINIONS_ATTACK);}
    public void setSummonMinionsAttack(boolean value) {this.dataManager.set(SUMMON_MINIONS_ATTACK, Boolean.valueOf(value));}
    public boolean isGetOverHereAttack() {return this.dataManager.get(GET_OVER_HERE_ATTACK);}
    public void setGetOverHereAttack(boolean value) {this.dataManager.set(GET_OVER_HERE_ATTACK, Boolean.valueOf(value));}
    public boolean isPushAttack() {return this.dataManager.get(PUSH_ATTACK);}
    public void setPushAttack(boolean value) {this.dataManager.set(PUSH_ATTACK, Boolean.valueOf(value));}

    //Used in the attack brains
    private Consumer<EntityLivingBase> previousAttack;

    //used for animation system
    private int animationTick;
    //just a variable that holds what the current animation is
    private EZAnimation currentAnimation;

    private boolean hasMinionsNearby = false;

    public EntityFireElemental(World worldIn) {
        super(worldIn);
        this.setSize(2.0F, 5.0F);
        this.isImmuneToFire = true;
    }


    @Override
    public void entityInit() {
        this.dataManager.register(PUNCH_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SMASH_GROUND_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(SUMMON_MINIONS_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(GET_OVER_HERE_ATTACK, Boolean.valueOf(false));
        this.dataManager.register(PUSH_ATTACK, Boolean.valueOf(false));
        super.entityInit();
    }


    @Override
    public void initEntityAI() {
        super.initEntityAI();
            // Expect changes to added Paramaters in the future, still working on some more complex stuff in Unseens Box of Curiosities
        this.tasks.addTask(4, new FireElementalAI<>(this, 1.0, 40, 10F, 0.3f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 1, true, false, null));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    public double temporaryDamageModifier = 8.0D;

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((400.0D)  * BeastSlayerConfig.GlobalHealthMultiplier );


        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D * BeastSlayerConfig.GlobalDamageMultiplier);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D + BeastSlayerConfig.GlobalArmor);
    }

    int testAnimationTick = 400;

    public void onUpdate() {
        super.onUpdate();

        if(this.world.isRemote){
            for (int i = 0; i < 1; ++i)
            {
                double d0 = this.posX + rand.nextDouble() - 0.5D;
                double d1 = this.posY + rand.nextDouble() * 0.5D + (this.height / 2);
                double d2 = this.posZ + rand.nextDouble() - 0.5D;
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                this.world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0D, 0D, 0D);
            }
        }

        //used for testing purposes of animations
       // if(testAnimationTick < 0) {
        //    this.setAnimation(ANIMATION_SMASH_GROUND);
      //      testAnimationTick = 500;
      //  } else {
       //     testAnimationTick--;
     //   }

        //handles animations
        if(this.isFightMode() && this.getAnimation() == NO_ANIMATION) {
            //Punch Attack
            if(this.isPunchAttack()) {
                this.setAnimation(ANIMATION_PUNCH);
            }
            //Smash Ground Attack
            if(this.isSmashGroundAttack()) {
                this.setAnimation(ANIMATION_SMASH_GROUND);
            }
            //Summon Minions Attack
            if(this.isSummonMinionsAttack()) {
                this.setAnimation(ANIMATION_SUMMONS);
            }
            //Push Attack
            if(this.isPushAttack()) {
                this.setAnimation(ANIMATION_PUSH);
            }
            //Get Over here attack
            if(this.isGetOverHereAttack()) {
                this.setAnimation(ANIMATION_GET_OVER_HERE);
            }
        }

        //minion Sensing
        if(hasMinionsNearby) {
            List<AbstractTribesmen> nearbyMinions = this.world.getEntitiesWithinAABB(AbstractTribesmen.class, this.getEntityBoundingBox().grow(30D), e -> !e.getIsInvulnerable());
            //just a quick sense to see if minions are nearby, once there is not the boolean sets to false
            if(nearbyMinions.isEmpty()) {
                hasMinionsNearby = false;
            }
        }
        //sends the Animation Handler constant updates on the animations
        EZAnimationHandler.INSTANCE.updateAnimations(this);
    }
    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        if(this.getAttackTarget() != null){
            EntityLivingBase target = this.getAttackTarget();
            if(target instanceof AbstractTribesmen){
                AbstractTribesmen tribesmen = (AbstractTribesmen) target;
                if(tribesmen.isFiery()) {
                    this.setAttackTarget(null);
                }

            }
        }
        super.setAttackTarget(entitylivingbaseIn);
    }


    public float getBrightness()
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        return entityIn instanceof AbstractTribesmen && ((AbstractTribesmen) entityIn).isFiery();
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        //This is where the brains of the AI takes places
        if(!this.isFightMode() && this.getAnimation() == NO_ANIMATION) {
            //Gathers all attacks in a list
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(punch, smash, summonMinions, getOverHERE, pushAttack));
            double[] weights = {
                    //this is where you add weights to the attacks and add a lot of parameters if you want
                    //these first two are just saying if the distance is less than 4 and the attack is not a repeat then do this attack
                    (distance < 4 && previousAttack != punch) ? 1/distance : 0, // Punch attack
                    (distance <= 10) ? 1/distance : 1, // Smash Attack, will edit later, just now to prevent crashing
                    (distance <= 10 && previousAttack != summonMinions && !hasMinionsNearby) ? 1/distance : 0, // Summon minions
                    (distance <= 16 && distance >= 12) ? 1/distance : 0, //Might have to have this operate outside of the system as well, GET OVER HERE
                    (distance < 4 && previousAttack != pushAttack) ? 1/distance : 0 // Push Attack

            };

            previousAttack = ModRand.choice(attacks, rand, weights).next();

            previousAttack.accept(target);
        }


        //the cooldown in between each attack, you can add certain parameters like if it does a certain attack it has a longer cooldown
        return (previousAttack == smash) ? 80 : 60;
    }

    private final Consumer<EntityLivingBase> punch = (target) -> {
        //begins the attack and makes sure that no other attacks play
    this.setFightMode(true);
    this.setPunchAttack(true);

    addEvent(()-> this.lockLook = true, 12);

    //the timed event system, basically allow certain things to activate in ticks from now
    addEvent(()-> {
        Vec3d offset = this.getPositionVector().add(BossUtil.getRelativeOffset(this, new Vec3d(2,1.5,0)));
        DamageSource source = DamageSource.causeMobDamage(this);
        float damage = (float) (temporaryDamageModifier + BeastSlayerConfig.GlobalDamageMultiplier);
        BossUtil.handleAreaImpact(1.5F, (e)-> damage, this, offset, source, 0.5F, 1, false);
        //Do Punch damage
    }, 23);



        //at 2.55 seconds this attack will be over
     addEvent(()-> {
         this.setFightMode(false);
         this.setPunchAttack(false);
         this.setAnimation(NO_ANIMATION);
         this.lockLook = false;
     }, 55);
    };

    private final Consumer<EntityLivingBase> smash = (target) -> {
        this.setFightMode(true);
        this.setSmashGroundAttack(true);
        addEvent(()-> {
            this.lockLook = true;
            this.setImmovable(true);
        }, 20);

        addEvent(()-> {
        //Do Smash Attack stuff
            //Maybe some Vfx and an entity where the ground pops out in a circle
            Vec3d offset = this.getPositionVector().add(BossUtil.getRelativeOffset(this, new Vec3d(0,0.3,0)));
            DamageSource source = DamageSource.causeMobDamage(this);
            float damage = (float) ((temporaryDamageModifier * 0.5) + BeastSlayerConfig.GlobalDamageMultiplier);
            BossUtil.handleAreaImpact(4.5F, (e)-> damage, this, offset, source, 0.9F, 1, false);
        }, 35);


        addEvent(()-> {
            this.setFightMode(false);
            this.setSmashGroundAttack(false);
            this.setImmovable(false);
            this.lockLook = false;
            this.setAnimation(NO_ANIMATION);
        }, 50);
    };


    private final Consumer<EntityLivingBase> summonMinions = (target) -> {
      this.setFightMode(true);
      this.setSummonMinionsAttack(true);
      this.setImmovable(true);
      this.lockLook = true;
      addEvent(()-> {
          new ActionSummonMinions().performAction(this, target);
        hasMinionsNearby = true;
      }, 20);
      addEvent(()-> {
          this.setFightMode(false);
          this.setSummonMinionsAttack(false);
          this.setAnimation(NO_ANIMATION);
          this.setImmovable(false);
          this.lockLook = false;
      }, 70);
    };

    private final Consumer<EntityLivingBase> getOverHERE = (target) -> {
      this.setFightMode(true);
      this.setGetOverHereAttack(true);


      addEvent(()-> {
          this.setGetOverHereAttack(false);
          this.setFightMode(false);
          this.setAnimation(NO_ANIMATION);
      }, 60);
    };

    private final Consumer<EntityLivingBase> pushAttack = (target) -> {
      this.setFightMode(true);
      this.setPushAttack(true);

      addEvent(()-> {
          this.setPushAttack(false);
          this.setFightMode(false);
          this.setAnimation(NO_ANIMATION);
      }, 60);
    };

    @Override
    public int getAnimationTick() {
        return animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        animationTick = tick;
    }

    @Override
    public EZAnimation getAnimation() {
        return currentAnimation;
    }

    @Override
    public void setAnimation(EZAnimation animation) {
        currentAnimation = animation;
    }

    //This is where you store a collective list of all the animations this entity is capable of ONLY USING THIS SYSTEM, there is no walk or idle animations
    @Override
    public EZAnimation[] getAnimations() {
        return new EZAnimation[]{ANIMATION_PUNCH, ANIMATION_SMASH_GROUND, ANIMATION_SUMMONS};
    }
    //  punch, smash, push, get over here!, summons, flame shot, life steal, meteor rain


    public int getSurfaceHeight(World world, BlockPos pos, int min, int max)
    {
        int maxY = max;
        int minY = min;
        int currentY = maxY;

        while(currentY >= minY)
        {
            if(!world.isAirBlock(pos.add(0, currentY, 0))) {
                //checks for air above the ground block
                if(world.isAirBlock(pos.add(0, currentY + 1, 0)) && world.isAirBlock(pos.add(0, currentY + 2, 0))) {
                    return currentY;
                }
            }

            currentY--;
        }
        return 0;
    }
}
