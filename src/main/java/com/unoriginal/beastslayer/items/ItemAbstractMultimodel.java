package com.unoriginal.beastslayer.items;

public abstract class ItemAbstractMultimodel extends ItemBase{

    public ItemAbstractMultimodel(String name) {
        super(name);
    }

    public abstract void registerModels();
}
