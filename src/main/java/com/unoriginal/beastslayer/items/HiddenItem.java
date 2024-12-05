package com.unoriginal.beastslayer.items;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public class HiddenItem extends Item {
    public HiddenItem(String name) {
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setMaxStackSize(1);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        ItemStack itemstack = player.getHeldItem(hand);
        pos = pos.offset(facing);

        if (world.isAirBlock(pos))
        {
            if (!world.isRemote) {

                RayTraceResult raytraceresult = this.rayTrace(world, player, true);

                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    boolean b = false;
                    for (int i = -3; i < 4; i++) {
                        for (int j = -3; j < 4; j++) {
                           // boolean validspawn = this.IsVillageAtPos(world, (pos.getX() - 8)  >> 4 + i, (pos.getZ() - 8)  >> 4 + j);
                            //if(validspawn){
                                Chunk chunk = world.getChunkFromBlockCoords(pos);
                                boolean c = this.IsVillageAtPos(world, chunk.x + i, chunk.z + j);
                                if (c) {
                                    b = true;
                                    break;
                                }
                           // }
                        }
                    }


                   // String chunk =  "[" + this.chunkCoordX + "," + this.chunkCoordZ + "]";
                    //  boolean b = chunk.equals(  TribeSavedData.loadData(this.world).getLocation());
                    //  BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

                  //  BeastSlayer.logger.debug(chunk + "chunkinfo");
                //    BeastSlayer.logger.debug(  TribeSavedData.loadData(world).getLocation() + "locationinfo");
                    if (b) {
                        if (player != null)
                        {
                            player.sendStatusMessage(new TextComponentTranslation("Tribesmen can spawn here", new Object[0]), true);
                            return EnumActionResult.SUCCESS;
                        }
                    } else {
                        if (player != null)
                        {
                            player.sendStatusMessage(new TextComponentTranslation("Tribesmen cant spawn here", new Object[0]), true);
                            return EnumActionResult.SUCCESS;
                        }
                    }
                    player.swingArm(hand);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }
    protected boolean IsVillageAtPos(World world, int chunkX, int chunkZ) {
        int spacing = 30;
        int separation = 16;
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= spacing - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random =  world.setRandomSeed(k, l, 10387312);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
        l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;

        if (i == k && j == l)
        {

            return world.getBiomeProvider().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, Lists.newArrayList(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE))); /*&& random.nextInt(20) == 0*/
        } else {

            return false;
        }
    }

}
