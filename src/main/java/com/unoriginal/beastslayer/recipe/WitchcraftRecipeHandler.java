package com.unoriginal.beastslayer.recipe;

import com.google.common.collect.Sets;
import com.unoriginal.beastslayer.BeastSlayer;
import com.unoriginal.beastslayer.config.BeastSlayerConfig;
import com.unoriginal.beastslayer.init.ModItems;
import com.unoriginal.beastslayer.items.ItemArtifact;
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
            return fixStack(parItemStack);  //if this fails try next operation

        }
        else {
            return pickLootTable((int)quality, seed, null);
        }

    }

    public ItemStack fixStack(List<ItemStack> parItemStack){
        if(parItemStack.size() != 5){
            return ItemStack.EMPTY;
        }
        int artifactsIn = 0;
        int fixIn = 0;
        Item item = null;
        for(ItemStack stack : parItemStack){
            if(stack.getItem() instanceof ItemArtifact && stack.isItemDamaged()){
                artifactsIn = artifactsIn + 1;

                    item = stack.getItem();
                    //BeastSlayer.logger.debug("artifact found");
               //     BeastSlayer.logger.debug(artifactsIn + "artifact");
            }
        }
        if(item != null && item instanceof ItemArtifact && artifactsIn == 1){
            for(ItemStack stack : parItemStack){
                ItemArtifact artifact = (ItemArtifact) item;
                if(getSetforRarity(artifact).contains(stack.getItem())){
                    fixIn = fixIn + 1;
                }
            }
           // BeastSlayer.logger.debug(fixIn);
        }
        if (fixIn == 4 && artifactsIn == 1 && item instanceof ItemArtifact){
            return new ItemStack(item, 1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public Set<Item> getSetforRarity (ItemArtifact artifact)
    {
        Set<Item> artifacts = null;
        switch (artifact.getRarity()){
            case 0:
                artifacts = Q1;
                break;
            case 1:
                artifacts = Q2;
                break;
            case 2:
                artifacts = Q3;
                break;
            case 3:
                artifacts = Q4;
                break;
        }
        return artifacts;
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
