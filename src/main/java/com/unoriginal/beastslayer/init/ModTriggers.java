package com.unoriginal.beastslayer.init;

import com.unoriginal.beastslayer.achievements.CustomTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final CustomTrigger OWLSTACK_INTERACT = CriteriaTriggers.register(new CustomTrigger("owlstack_interact"));
    public static final CustomTrigger SUCCUBUS_FRIEND = CriteriaTriggers.register(new CustomTrigger("succubus_friend"));
    public static final CustomTrigger SUCCUBUS_BLOOD = CriteriaTriggers.register(new CustomTrigger("succubus_blood"));

    public static void init(){
    }
}
