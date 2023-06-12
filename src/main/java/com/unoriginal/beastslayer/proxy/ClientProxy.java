package com.unoriginal.beastslayer.proxy;

import com.unoriginal.beastslayer.gui.GuiWiki;
import com.unoriginal.beastslayer.init.ModEntities;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.init.ModParticles;
import com.unoriginal.beastslayer.items.models.ModelCharredCloak;
import com.unoriginal.beastslayer.items.models.ModelMinerHelmet;
import com.unoriginal.beastslayer.items.models.ModelScaleArmor;
import com.unoriginal.beastslayer.particles.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
    private final ModelBiped MODEL_SCALE_ARMOR = new ModelScaleArmor(0.5F);
    private final ModelBiped MODEL_CHARRED_ARMOR = new ModelCharredCloak(1.0F);
    private final ModelBiped MODEL_MINER_HELMET = new ModelMinerHelmet(0.75F);

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
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void openGui(ItemStack bestiary) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiWiki(bestiary));
    }


}
