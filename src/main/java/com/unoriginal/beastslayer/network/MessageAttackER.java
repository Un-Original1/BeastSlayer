package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.items.ItemSpear;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageAttackER implements IMessage
{
    private int entityId ;
    private int playerId;

    public MessageAttackER(EntityPlayer p, EntityLivingBase e)
    {
        if (p != null)
        {
            this.playerId = p.getEntityId();
        }
        else
        {
            this.playerId = -1;
        }

        this.entityId = e.getEntityId();
    }

    public MessageAttackER()
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

    public static class Handler implements IMessageHandler<MessageAttackER, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageAttackER message, MessageContext ctx)
        {

            EntityPlayerMP player = ctx.getServerHandler().player ;
            EntityLivingBase entity = (EntityLivingBase) player.world.getEntityByID(message.entityId);

            player.getHeldItemMainhand();
            if (player.getHeldItemMainhand().getItem() instanceof ItemSpear)
            {
                double distanceSq = player.getDistanceSq(entity);
             ///  if (10D >= distanceSq)
              //  {
                    player.attackTargetEntityWithCurrentItem(entity);
               // }
            }
            return null;
        }
    }
}

