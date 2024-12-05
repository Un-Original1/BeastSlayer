package com.unoriginal.beastslayer.proxy;

import com.unoriginal.beastslayer.animation.IAnimatedEntity;
import com.unoriginal.beastslayer.gui.GuiWiki;
import com.unoriginal.beastslayer.init.ModEntities;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.items.models.*;
import com.unoriginal.beastslayer.particles.ParticleColoredDrip;
import com.unoriginal.beastslayer.particles.ParticleRift;
import com.unoriginal.beastslayer.particles.ParticleSandSpit;
import com.unoriginal.beastslayer.particles.ParticleWispFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
    private final ModelBiped MODEL_SCALE_ARMOR = new ModelScaleArmor(0.5F);
    private final ModelBiped MODEL_CHARRED_ARMOR = new ModelCharredCloak(1.0F);
    private final ModelBiped MODEL_MINER_HELMET = new ModelMinerHelmet(0.75F);
    private final ModelBiped MODEL_MASK_W = new MaskMarauder(0.0F);
    private final ModelBiped MODEL_MASK_H = new MaskHunter(0.0F);
    private final ModelBiped MODEL_MASK_S = new MaskScorcher(0.0F);
    private final ModelBiped MODEL_MASK_P = new MaskSpiritweaver(0.0F);
    private final ModelBiped MODEL_MASK_BOSS = new ModelFireMask(0.0F);

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModEntities.initModels();
    }
    @Override
    public void registerParticles() {
        Minecraft.getMinecraft().effectRenderer.registerParticle(ModParticles.SAND_SPIT.getParticleID(), new ParticleSandSpit.Factory());
        Minecraft.getMinecraft().effectRenderer.registerParticle(ModParticles.RIFT.getParticleID(), new ParticleRift.Factory());
        Minecraft.getMinecraft().effectRenderer.registerParticle(ModParticles.WISPFLAME.getParticleID(), new ParticleWispFire.Factory());
        Minecraft.getMinecraft().effectRenderer.registerParticle(ModParticles.DRIP.getParticleID(), new ParticleColoredDrip.Factory());
    }
    @Override
    public Object getArmorModel(Item item, EntityLivingBase entity) {
        if(item == ModItems.SCALE_ARMOR || item == ModItems.SCALE_HOOD){
            return MODEL_SCALE_ARMOR;
        }
        if(item == ModItems.CHARRED_CLOAK){
            return MODEL_CHARRED_ARMOR;
        }
        if(item == ModItems.MINER_HELMET){
            return MODEL_MINER_HELMET;
        }
        if(item == ModItems.MASK_W){
            return MODEL_MASK_W;
        }
        if(item == ModItems.MASK_H){
            return MODEL_MASK_H;
        }
        if(item == ModItems.MASK_S){
            return MODEL_MASK_S;
        }
        if(item == ModItems.MASK_P){
            return MODEL_MASK_P;
        }
        if(item == ModItems.LOGO){
            return MODEL_MASK_BOSS;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void openGui(ItemStack bestiary) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiWiki(bestiary));
    }

    @Override
    public void handleAnimationPacket(int entityId, int index) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null) {
            IAnimatedEntity entity = (IAnimatedEntity) player.world.getEntityByID(entityId);
            if (entity != null) {
                if (index == -1) {
                    entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
                } else {
                    entity.setAnimation(entity.getAnimations()[index]);
                }
                entity.setAnimationTick(0);
            }
        }
    }
}
