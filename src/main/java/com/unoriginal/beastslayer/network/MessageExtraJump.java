package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.entity.Entities.EntityGloop;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageExtraJump implements IMessage {
    private int playerId;

    public MessageExtraJump(EntityPlayer player) {
        this.playerId = player.getEntityId();
    }

    public MessageExtraJump() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerId);
    }

    public static class MessageHandler implements IMessageHandler<MessageExtraJump, IMessage> {
        public IMessage onMessage(MessageExtraJump message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            if(player.isBeingRidden()) {
                Entity entity = player.getPassengers().get(0);
                if (entity != null) {
                    if (entity instanceof EntityGloop) {
                        EntityGloop entityGloop = (EntityGloop) entity;
                        if (entityGloop.getRidingEntity() == player && !player.isInWater()) {
                            entityGloop.setMaxJump(entityGloop.getMaxJump() + 1);
                        }
                    }
                }
            }
            return null;
        }
    }
}

