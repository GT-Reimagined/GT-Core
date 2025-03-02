package org.gtreimagined.gtcore.loader.crafting;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.Material;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtcore.machine.HopperMachine;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.HAMMER;
import static muramasa.antimatter.data.AntimatterDefaultTools.WRENCH;
import static muramasa.antimatter.data.AntimatterMaterialTypes.PLATE;
import static muramasa.antimatter.data.AntimatterMaterialTypes.ROD_LONG;
import static muramasa.antimatter.machine.Tier.NONE;

public class MachineRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        AntimatterAPI.all(DrumMachine.class).forEach(d -> {
            Material m = d.getMaterial();
            if (m.has(PLATE) && m.has(ROD_LONG)){
                provider.addItemRecipe(output, GTCore.ID, "", "machines", d.getItem(NONE), of('H', HAMMER.getTag(), 'R', ROD_LONG.getMaterialTag(m), 'P', PLATE.getMaterialTag(m)), " H ", "PRP", "PRP");
            }
        });
        AntimatterAPI.all(HopperMachine.class).forEach(h -> {
            Material m = h.getMaterial();
            if (m.has(PLATE)){
                provider.addItemRecipe(output, GTCore.ID, "", "machines", h.getItem(NONE), of('W', WRENCH.getTag(), 'C', Tags.Items.CHESTS_WOODEN, 'P', PLATE.getMaterialTag(m)), "PWP", "PCP", " P ");
            }
        });
    }
}
