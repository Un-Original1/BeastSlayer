package com.unoriginal.beastslayer.integration;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import com.unoriginal.beastslayer.items.ItemArtifact;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class IntegrationBaubles
{

    private static boolean isBaublesLoaded;
    private static final Map<ItemArtifact.baubleSlot, BaubleType> ARTEFACT_TYPE_MAP = new EnumMap<>(ItemArtifact.baubleSlot.class);

    public static void init(){
        isBaublesLoaded = Loader.isModLoaded("baubles"); //I don't think baubles will change its mod ID, so Im not declaring it as a String
        if(!isEnabled()) return;
        ARTEFACT_TYPE_MAP.put(ItemArtifact.baubleSlot.CHARM, BaubleType.CHARM);

    }

    public static boolean isBaubleEquipped(EntityPlayer player, Item item){
        return BaublesApi.isBaubleEquipped(player, item) >= 0;
    }

    public static List<ItemArtifact> getEquippedArtifacts(EntityPlayer player, ItemArtifact.baubleSlot... types){

        List<ItemArtifact> artefacts = new ArrayList<>();

        for(ItemArtifact.baubleSlot type : types) {
            for (int slot : ARTEFACT_TYPE_MAP.get(type).getValidSlots()) {
                ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
                if (stack.getItem() instanceof ItemArtifact) artefacts.add((ItemArtifact) stack.getItem());
            }
        }


        return artefacts;
    }

    public static boolean isEnabled(){
        return isBaublesLoaded;
    }

    @SuppressWarnings("unchecked")
    public static final class BaubleProvider implements ICapabilityProvider {

        private BaubleType type;

        public BaubleProvider(ItemArtifact.baubleSlot type){
            this.type = ARTEFACT_TYPE_MAP.get(type);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
            return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing){
            // This lambda expression is an implementation of the entire IBauble interface
            return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE ? (T)(IBauble) itemStack -> type : null;
        }

    }
}
