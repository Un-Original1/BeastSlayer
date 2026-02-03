package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.entity.Entities.boss.fire_elemental.EntityFireElemental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModClientEvents {
    private ModClientEvents() {}
    private static final ResourceLocation CHARM = new ResourceLocation(BeastSlayer.MODID, "shaders/post/charm.json");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void ClientStuffLiving(LivingEvent.LivingUpdateEvent event){
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase entityLiving = event.getEntityLiving();
        EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;

        if (entityLiving.isPotionActive(ModPotions.CHARMED) && entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if(player.getActivePotionEffect(ModPotions.CHARMED) != null && player.world.isRemote) {
                if(!renderer.isShaderActive()){
                    renderer.loadShader(CHARM);
                }

            }


        }
        if(entityLiving instanceof EntityPlayer) { //note to future self, these kinds of shaders DO NOT work on non player entities, it will lead to a crash given they do not have a "camera" of their own
            if (renderer != null && !entityLiving.isPotionActive(ModPotions.CHARMED) && renderer.getShaderGroup() != null && renderer.getShaderGroup().getShaderGroupName() != null && CHARM.toString().equals(renderer.getShaderGroup().getShaderGroupName()) && entityLiving.world.isRemote) {
                renderer.stopUseShader();
            }
        }
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogColour(EntityViewRenderEvent.FogColors event) {
        //todo add config to tint
        Minecraft mc = Minecraft.getMinecraft();
        WorldClient world = mc.world;
        boolean b = false;
        List<Entity> entities = world.getLoadedEntityList();
        if(!entities.isEmpty()) {
            for (Entity entity : entities){
                if(entity instanceof EntityFireElemental){
                    b = true;
                    break;
                }
            }
        }
        if (b) {
            float d = getSunBrightness((float) event.getRenderPartialTicks()) * 1.4f;

            event.setRed((1f - d) * 180F / 255f + (d * event.getRed()));
            event.setGreen((1f - d) * 60F / 255f + d * event.getGreen());
            event.setBlue((1f - d) * 0 / 255f + d * event.getBlue());

        }
    }
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float partialTicks)
    {
        int i = (int)(20000 % 24000L);
        float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

        if (f < 0.0F)
        {
            ++f;
        }

        if (f > 1.0F)
        {
            --f;
        }

        float f3 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f3 - f) / 3.0F;;
        float f1 = 1.0F - (MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.2F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        f1 = 1.0F - f1;
        return f1 * 0.8F + 0.2F;
    }
}
