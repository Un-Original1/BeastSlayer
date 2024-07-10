package com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental;

import com.unoriginal.beastslayer.animation.EZAnimation;
import com.unoriginal.beastslayer.animation.EZAnimationHandler;
import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.IAttack;
import com.unoriginal.beastslayer.entity.Entities.ai.attack_manager.fire_elemental.FireElementalAI;
import com.unoriginal.beastslayer.entity.Entities.boss.EntityAbstractBoss;
import com.unoriginal.beastslayer.util.ModRand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityFireElemental extends EntityAbstractBoss implements IAttack, IAnimatedEntity {
    public static final EZAnimation ANIMATION_EXAMPLE = EZAnimation.create(20);
    //Used in the attack brains
    private Consumer<EntityLivingBase> previousAttack;

    //used for animation system
    private int animationTick;
    //just a variable that holds what the current animation is
    private EZAnimation currentAnimation;

    public EntityFireElemental(World worldIn) {
        super(worldIn);
        this.setSize(2.0F, 5.0F);
    }


    @Override
    public void initEntityAI() {
        super.initEntityAI();
            // Expect changes to added Paramaters in the future, still working on some more complex stuff in Unseens Box of Curiosities
        this.tasks.addTask(4, new FireElementalAI<>(this, 1.0, 40, 10F, 0.3f));
    }

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

        //sends the Animation Handler constant updates on the animations
        EZAnimationHandler.INSTANCE.updateAnimations(this);
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
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        //This is where the brains of the AI takes places
        if(!this.isFightMode() && this.getAnimation() != NO_ANIMATION) {
            //Gathers all attacks in a list
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(punch, smash));
            double[] weights = {
                    //this is where you add weights to the attacks and add a lot of parameters if you want
                    //these first two are just saying if the distance is less than 4 and the attack is not a repeat then do this attack
                    (distance < 4 && previousAttack != punch) ? 1/distance : 0,
                    (distance < 4 && previousAttack != smash) ? 1/distance : 0
            };

            previousAttack = ModRand.choice(attacks, rand, weights).next();

            previousAttack.accept(target);
        }


        //the cooldown in between each attack, you can add certain parameters like if it does a certain attack it has a longer cooldown
        return (previousAttack == smash) ? 80 : 40;
    }

    private final Consumer<EntityLivingBase> punch = (target) -> {
        //begins the attack and makes sure that no other attacks play
    this.setFightMode(true);

    //the timed event system, basically allow certain things to activate in ticks from now
    addEvent(()-> {
        //Do action with whatever code you want
    }, 40);



        //at 4 seconds this attack will be over
     addEvent(()-> {
         this.setFightMode(false);
     }, 80);
    };

    private final Consumer<EntityLivingBase> smash = (target) -> {

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
        return new EZAnimation[]{ANIMATION_EXAMPLE};
    }
    //  punch, smash, push, get over here!, summons, flame shot, life steal, meteor rain
}
