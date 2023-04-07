package com.unoriginal.beastslayer.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSpawnParticle implements IMessage {
    private boolean messageValid;

    private double x, y, z, xspeed, yspeed, zspeed;
    private EnumParticleTypes particleIn;

    public MessageSpawnParticle ()
    {
        this.messageValid = false;
    }

    public MessageSpawnParticle (EnumParticleTypes types, double x, double y, double z, double xspeed, double yspeed, double zspeed)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.zspeed = zspeed;
        this.particleIn = types;

        this.messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
            this.xspeed = buf.readDouble();
            this.yspeed = buf.readDouble();
            this.zspeed = buf.readDouble();
        }
        catch(IndexOutOfBoundsException ioe)
        {
            return;
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        if(!this.messageValid)
            return;

        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(xspeed);
        buf.writeDouble(yspeed);
        buf.writeDouble(zspeed);
    }


    public static class Handler implements IMessageHandler<MessageSpawnParticle, IMessage>
    {

        @Override
        public IMessage onMessage(MessageSpawnParticle message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT)
            {
                return null;
            }


            Minecraft minecraft = Minecraft.getMinecraft();
            final WorldClient worldClient = minecraft.world;

            minecraft.addScheduledTask(() -> processMessage(message, worldClient));

            return null;
        }

        void processMessage(MessageSpawnParticle message, WorldClient worldClient)
        {
            worldClient.spawnParticle(message.particleIn, message.x, message.y, message.z, message.xspeed, message.yspeed, message.zspeed);
        }

    }
}
