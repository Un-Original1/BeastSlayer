package com.unoriginal.beastslayer.network;

import com.unoriginal.beastslayer.entity.Entities.EntityOwlstack;
import com.unoriginal.beastslayer.gui.ContainerWitchcraft;
import com.unoriginal.beastslayer.gui.SlotWitchResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class MessageItemWitchcraft implements IMessage{
    private ItemStack stack;
    private int playerId;


    public MessageItemWitchcraft(EntityPlayer player, ItemStack stack)
    {
        if (player != null)
        {
            this.playerId = player.getEntityId();
        }
        else
        {
            this.playerId = -1;
        }

        this.stack = stack.copy();

    }
    public MessageItemWitchcraft()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.playerId = buf.readInt();


        int i = buf.readShort();

        if (i < 0)
        {
            this.stack = ItemStack.EMPTY;
        }
        else
        {
            int j = buf.readByte();
            int k = buf.readShort();
            ItemStack itemstack = new ItemStack(Item.getItemById(i), j, k);
            itemstack.getItem().readNBTShareTag(itemstack, this.readCompoundTag(buf));
            this.stack = itemstack;
        }
    }

    public NBTTagCompound readCompoundTag(ByteBuf buf)
    {

        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0)
        {
            return null;
        }
        else
        {
            buf.readerIndex(i);

            try
            {
                return CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(2097152L));
            }
            catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.playerId);

        if (stack.isEmpty())
        {
            buf.writeShort(-1);
        }
        else
        {
            buf.writeShort(Item.getIdFromItem(stack.getItem()));
            buf.writeByte(stack.getCount());
            buf.writeShort(stack.getMetadata());
            NBTTagCompound nbttagcompound = null;

            if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
            {
                nbttagcompound = stack.getItem().getNBTShareTag(stack);
            }

            if (nbttagcompound == null)
            {
                buf.writeByte(0);
            }
            else
            {
                try
                {
                    CompressedStreamTools.write(nbttagcompound, new ByteBufOutputStream(buf));
                }
                catch (IOException ioexception)
                {
                    throw new EncoderException(ioexception);
                }
            }
        }
    }
    public static class MessageHandler implements IMessageHandler<MessageItemWitchcraft, IMessage> {
        // Do note that the default constructor is required, but implicitly defined in this case
        //   @Override
        public IMessage onMessage(MessageItemWitchcraft message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if(player.openContainer != null) {
                if (player.openContainer instanceof ContainerWitchcraft) {
                    ContainerWitchcraft witchcraft = (ContainerWitchcraft) player.openContainer;
                    witchcraft.putStackInResultSlot(message.stack);

                }
            }

            return null;
        }
    }
}
