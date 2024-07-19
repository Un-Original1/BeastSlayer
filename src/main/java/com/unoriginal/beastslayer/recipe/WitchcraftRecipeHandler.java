package com.unoriginal.beastslayer.recipe;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WitchcraftRecipeHandler {

    private ItemStack stack;
    private int expectedQuality;
    private long seed;
    public World world;
    private NBTTagCompound customData;

    private static final Set<Item> Q1 = Sets.newHashSet(
            ModItems.CLOTH);
    private static final Set<Item> Q2 = Sets.newHashSet(
            ModItems.BROKEN_TALISMAN
    );
    private static final Set<Item> Q3 = Sets.newHashSet(
            ModItems.DUST
    );
    private static final Set<Item> Q4 = Sets.newHashSet(
            ModItems.TABLET
    );

    public WitchcraftRecipeHandler(World world){
        this.expectedQuality = 0;
        this.world = world;
    }

    public ItemStack getResults(List<ItemStack> parItemStack)
    {
        double quality = 0D;
        int count = 0;
        if(parItemStack.size() != 5){
            return ItemStack.EMPTY;
        }

        for(ItemStack stack : parItemStack){
            if(Q1.contains(stack.getItem())){
                quality = quality + 1D;
                count = count + 1;
            }
            if (Q2.contains(stack.getItem())) {
                quality = quality + 2D;
                count = count + 1;
            }
            if (Q3.contains(stack.getItem())) {
                quality = quality + 3D;
                count = count + 1;
            }
            if (Q4.contains(stack.getItem())) {
                quality = quality + 4D;
                count = count + 1;
            }

        }
        BigDecimal decimal = new BigDecimal(String.valueOf(quality / 5D));
        int randChance = decimal.subtract(new BigDecimal(decimal.intValue())).multiply(new BigDecimal(10)).intValue();

        //BeastSlayer.logger.debug(randChance + "chance");
       // BeastSlayer.logger.debug(quality/5D + "quality");
        quality = Math.round(quality / 5D);
        //TODO add config for rng
        if(quality > 0.5 && BeastSlayerConfig.WitchcraftTableQualityDecimalRand && randChance > 0) {
            quality = new Random().nextInt(randChance) == 0 ? quality - 1 : quality;
        }
        if(this.seed == 0L) {
            this.seed = new Random().nextLong();
        }


        if(quality <= 0.5 || count != 5){
            return ItemStack.EMPTY;
        }
        else {
            return pickLootTable((int)quality, seed, null);
        }

    }

    public ItemStack pickLootTable(int quality, long seed, @Nullable String tag)
    {
        ItemStack result = ItemStack.EMPTY;
        ResourceLocation location = new ResourceLocation(BeastSlayer.MODID, "magic/witchcraft_" + quality + (tag == null ? "" : tag));
        if(!this.world.isRemote) {
            LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(location);
            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.world);
            List<ItemStack> itemlist = loottable.generateLootForPools(new Random(seed), lootcontext$builder.build());
            if (!itemlist.isEmpty()) {
                result = itemlist.get(new Random().nextInt(itemlist.size()));
                //this.setLastCraftablebyQuality(quality, result);
            }
       }
        return result; //source of desync
    }

}
