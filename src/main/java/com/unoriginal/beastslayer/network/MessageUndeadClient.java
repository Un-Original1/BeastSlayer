package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUndeadClient  implements IMessage {
    private int playerID;
    public MessageUndeadClient(EntityLivingBase player) {
        playerID = player.getEntityId();
    }
    public MessageUndeadClient() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.playerID);
    }
    public static class Handler implements IMessageHandler<MessageUndeadClient, IMessage> {
        public IMessage onMessage(MessageUndeadClient message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            Entity daplayer = player.world.getEntityByID(message.playerID);
            Minecraft.getMinecraft().effectRenderer.emitParticleAtEntity(daplayer, EnumParticleTypes.TOTEM, 30);
            player.world.playSound(daplayer.posX, daplayer.posY, daplayer.posZ, SoundEvents.ITEM_TOTEM_USE, daplayer.getSoundCategory(), 1.0F, 1.0F, false);
            if(player == daplayer) {
                Minecraft.getMinecraft().entityRenderer.displayItemActivation(new ItemStack(ModItems.TOTEM_OF_DYING));
            }

            return null;
        }
    }
}
