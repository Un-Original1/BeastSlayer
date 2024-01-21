package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.entity.Entities.EntityOwlstack;
import com.unoriginal.beastslayer.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGlowing implements IMessage {
    private int entityId;

    private int playerId;
    public MessageGlowing(){
    }

    public MessageGlowing(EntityPlayer player, EntityLivingBase entityLivingBase)

    {
        if (player != null)
        {
            this.playerId = player.getEntityId();
        }
        else
        {
            this.playerId = -1;
        }

        this.entityId = entityLivingBase.getEntityId();
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

    public static class MessageHandler implements IMessageHandler<MessageGlowing, IMessage> {
        // Do note that the default constructor is required, but implicitly defined in this case
        //   @Override
        public IMessage onMessage(MessageGlowing message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            Entity livingBase = player.world.getEntityByID(message.entityId);
            if(livingBase instanceof EntityLivingBase){
             //   ((EntityLivingBase) livingBase).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 40, 0, true, false));
                livingBase.setGlowing(player.getHeldItemOffhand().getItem() == ModItems.HUNTERS_EYE && !player.canEntityBeSeen(livingBase));
            }
            return null;
        }
    }
}
