package com.unoriginal.beastslayer.command;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class CommandLocateAB implements ICommand {
    private final List<String> aliases;
    public CommandLocateAB(){
        aliases = new ArrayList<>();
        aliases.add("locateAB");
    }
    @Override
    public String getName() {
        return "locateAB";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "locateAB <mod location>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
        {
            throw new WrongUsageException("locateAB <mod location>");
        }
        else
        {
            String s = args[0];
            BlockPos blockpos = findNearestPos(sender);

            if (blockpos != null)
            {
                sender.sendMessage(new TextComponentTranslation("commands.locate.success", new Object[] {s, blockpos.getX(), blockpos.getZ()}));
            }
            else
            {
                throw new CommandException("commands.locate.failure", s);
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "TribeVillage") : Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }



    public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities)
    {
        return getListOfStringsMatchingLastWord(args, Arrays.asList(possibilities));
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] inputArgs, Collection<?> possibleCompletions)
    {
        String s = inputArgs[inputArgs.length - 1];
        List<String> list = Lists.newArrayList();

        if (!possibleCompletions.isEmpty())
        {
            for (String s1 : Iterables.transform(possibleCompletions, Functions.toStringFunction()))
            {
                if (doesStringStartWith(s, s1))
                {
                    list.add(s1);
                }
            }

            if (list.isEmpty())
            {
                for (Object object : possibleCompletions)
                {
                    if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath()))
                    {
                        list.add(String.valueOf(object));
                    }
                }
            }
        }

        return list;
    }

    public static boolean doesStringStartWith(String original, String region)
    {
        return region.regionMatches(true, 0, original, 0, original.length());
    }

    public static BlockPos findNearestPos (ICommandSender sender){
        BlockPos resultpos = null;
        BlockPos pos = sender.getPosition();
        World world = sender.getEntityWorld();
        Chunk chunk =  world.getChunkFromBlockCoords(pos);
        //probably laggy as hell but hey it works
        for (int i = -100; i < 101; i++) {
            for (int j = -100; j < 101; j++) {
                // boolean validspawn = this.IsVillageAtPos(world, (pos.getX() - 8)  >> 4 + i, (pos.getZ() - 8)  >> 4 + j);
                //if(validspawn){
                //Chunk chunk = world.getChunkFromBlockCoords(pos);
                boolean c = IsVillageAtPos(world, chunk.x + i, chunk.z + j);
                if (c) {
                    resultpos = new BlockPos((chunk.x+i) << 4, 100, (chunk.z +j) << 4 );
                    break;
                }
                // }
            }
        }
        return resultpos;
    }

    protected static boolean IsVillageAtPos(World world, int chunkX, int chunkZ) {
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
