package com.unoriginal.beastslayer.integration;
import net.minecraftforge.fml.common.Loader;

public class IntegrationBaubles
{

    private static boolean isBaublesLoaded;

    public static void init(){
        isBaublesLoaded = Loader.isModLoaded("baubles"); //I don't think baubles will change its mod ID, so Im not declaring it as a String
    }
    public static boolean isEnabled(){
        return isBaublesLoaded;
    }

}
