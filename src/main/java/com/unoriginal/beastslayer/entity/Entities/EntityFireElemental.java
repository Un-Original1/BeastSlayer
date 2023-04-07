package com.unoriginal.beastslayer.entity.Entities;

import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFireElemental extends EntityMob {
    public EntityFireElemental(World worldIn) {
        super(worldIn);
        this.setSize(2.0F, 5.0F);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((600.0D)  * BeastSlayerConfig.GlobalHealthMultiplier );


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
    //attacks: punch, smash, push, get over here!, summons, flame shot, life steal, meteor rain
}
