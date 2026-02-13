package org.gtreimagined.gtcore.loader;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreRecipeMaps;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.util.RegistryUtils;

public class MortarLoader {
    public static void init(){
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.BLAZE_ROD).io(new ItemStack(Items.BLAZE_POWDER, 3)).add("blaze_powder");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.BONE).io(new ItemStack(Items.BONE_MEAL, 2)).add("bone_meal");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.WITHER_SKELETON_SKULL).io(new ItemStack(Items.BONE_MEAL, 9)).add("wither_skeleton_skull");
        GTCoreRecipeMaps.MORTAR.RB().ii(Items.SKELETON_SKULL).io(new ItemStack(Items.BONE_MEAL, 9)).add("skeleton_skull");
        if (GTAPI.isModLoaded("thermal_foundation")){
            GTCoreRecipeMaps.MORTAR.RB().ii(RegistryUtils.getItemFromID("thermal", "basalz_rod")).io(new ItemStack(RegistryUtils.getItemFromID("thermal", "basalz_powder"), 3)).add("basalz_powder");
            GTCoreRecipeMaps.MORTAR.RB().ii(RegistryUtils.getItemFromID("thermal", "blitz_rod")).io(new ItemStack(RegistryUtils.getItemFromID("thermal", "blitz_powder"), 3)).add("blitz_powder");
            GTCoreRecipeMaps.MORTAR.RB().ii(RegistryUtils.getItemFromID("thermal", "blizz_rod")).io(new ItemStack(RegistryUtils.getItemFromID("thermal", "blizz_powder"), 3)).add("blizz_powder");
        }
    }
}
