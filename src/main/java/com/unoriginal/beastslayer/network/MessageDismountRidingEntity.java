package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.entity.Entities.EntityOwlstack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDismountRidingEntity implements IMessage {
    private int entityId;

    private int playerId;

    public MessageDismountRidingEntity(EntityPlayer player, EntityOwlstack owlstack)
    {
        if (player != null)
        {
            this.playerId = player.getEntityId();
        }
        else
        {
            this.playerId = -1;
        }

        this.entityId = owlstack.getEntityId();
    }

   public MessageDismountRidingEntity()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.playerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeInt(this.playerId);
    }
    public static class MessageHandler implements IMessageHandler<MessageDismountRidingEntity, IMessage> {
// Do note that the default constructor is required, but implicitly defined in this case
     //   @Override
        public IMessage onMessage(MessageDismountRidingEntity message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            Entity owlstack = player.world.getEntityByID(message.entityId);
            owlstack.dismountRidingEntity();

            return null;
        }
    }
}
