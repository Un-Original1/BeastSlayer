package com.unoriginal.beastslayer.gui;

import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.blocks.tile.TileEntityWitchcraftTable;
import com.unoriginal.beastslayer.init.ModBlocks;
import com.unoriginal.beastslayer.network.BeastSlayerPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class ABGuiHandler implements IGuiHandler {
    public static final ABGuiHandler INSTANCE = new ABGuiHandler();

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == BeastSlayer.GUIs.WITCHCRAFT_TABLE.ordinal()) {
            BlockPos pos = new BlockPos(x, y, z);
            Block block = world.getBlockState(pos).getBlock();
            if (block == ModBlocks.WITCHCRAFT_TABLE) {
                return new ContainerWitchcraft(player.inventory, (TileEntityWitchcraftTable) world.getTileEntity(pos));
            }
        }
        return null;
    }

    /**
     * client side
     **/
    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == BeastSlayer.GUIs.WITCHCRAFT_TABLE.ordinal()) {
            BlockPos pos = new BlockPos(x, y, z);
            Block block = world.getBlockState(pos).getBlock();
            if (block == ModBlocks.WITCHCRAFT_TABLE) {
                return new GuiWitchcraft(new ContainerWitchcraft(player.inventory, (TileEntityWitchcraftTable) world.getTileEntity(pos)), player.inventory, (TileEntityWitchcraftTable) world.getTileEntity(pos));
            }
        }
        return null;
    }

    public static void registerGuiHandler(){
        NetworkRegistry.INSTANCE.registerGuiHandler(BeastSlayer.instance, ABGuiHandler.INSTANCE);
    }

}


