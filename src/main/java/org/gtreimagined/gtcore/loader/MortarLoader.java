package org.gtreimagined.gtcore.loader;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreRecipeMaps;

public class MortarLoader {
    public static void init(){
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.BLAZE_ROD).io(new ItemStack(Items.BLAZE_POWDER, 3)).add("blaze_powder");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.BONE).io(new ItemStack(Items.BONE_MEAL, 2)).add("bone_meal");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.WITHER_SKELETON_SKULL).io(new ItemStack(Items.BONE_MEAL, 9)).add("wither_skeleton_skull");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.SKELETON_SKULL).io(new ItemStack(Items.BONE_MEAL, 9)).add("skeleton_skull");
    }
}
