package com.unoriginal.beastslayer.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BeastSlayerPacketHandler {
    public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("ancientbeasts");

    public static void initMessages() {
        int id = 0;
        WRAPPER.registerMessage(MessageDismountRidingEntity.MessageHandler.class, MessageDismountRidingEntity.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(MessageAttackER.Handler.class, MessageAttackER.class, id++, Side.SERVER);
        WRAPPER.registerMessage(MessageSpawnParticle.Handler.class, MessageSpawnParticle.class, id++ ,Side.CLIENT);
        WRAPPER.registerMessage(MessageSpawnParticle.Handler.class, MessageSpawnParticle.class, id++ ,Side.SERVER);
      //  WRAPPER.registerMessage(MessageDismountRidingEntity.MessageHandler.class, MessageDismountRidingEntity.class, id++, Side.SERVER);
    }

    public static void sendPacketToAllPlayers(final IMessage message)
    {
        WRAPPER.sendToAll(message);
    }

    public static void sendToServer(final IMessage message){WRAPPER.sendToServer(message);}

    public static void sendToAllAround(final IMessage message, NetworkRegistry.TargetPoint point){
        WRAPPER.sendToAllAround(message, point);
    }
}
