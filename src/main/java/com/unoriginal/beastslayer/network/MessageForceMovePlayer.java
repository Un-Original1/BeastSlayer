package com.unoriginal.beastslayer.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageForceMovePlayer implements IMessage {
    private int entityId ;
    private double d0;
    private double d1;
    private double d2;

    public MessageForceMovePlayer (double d0, double d1, double d2, EntityLivingBase e)
    {
        this.entityId = e.getEntityId();
        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    public MessageForceMovePlayer ()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.d0 = buf.readDouble();
        this.d1 = buf.readDouble();
        this.d2 = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeDouble(this.d0);
        buf.writeDouble(this.d1);
        buf.writeDouble(this.d2);
    }

    public static class Handler implements IMessageHandler<MessageForceMovePlayer, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageForceMovePlayer message, MessageContext ctx)
        {

            EntityLivingBase entity = (EntityLivingBase) ctx.getServerHandler().player.world.getEntityByID(message.entityId);
            if(entity != null) {
                entity.addVelocity(message.d0, message.d1, message.d2);
            }
            return null;
        }
    }
}
