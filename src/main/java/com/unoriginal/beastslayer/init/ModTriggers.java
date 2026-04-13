package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.achievements.CustomTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final CustomTrigger OWLSTACK_INTERACT = new CustomTrigger("owlstack_interact");
    public static final CustomTrigger SUCCUBUS_FRIEND = new CustomTrigger("succubus_friend");
    public static final CustomTrigger SUCCUBUS_BLOOD = new CustomTrigger("succubus_blood");
    public static final CustomTrigger SUCCUBUS_BED = new CustomTrigger("succubus_bed");

    public static void init(){
        CriteriaTriggers.register(OWLSTACK_INTERACT);
        CriteriaTriggers.register(SUCCUBUS_FRIEND);
        CriteriaTriggers.register(SUCCUBUS_BLOOD);
        CriteriaTriggers.register(SUCCUBUS_BED);
    }
}
