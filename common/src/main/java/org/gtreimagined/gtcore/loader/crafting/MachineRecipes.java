package org.gtreimagined.gtcore.loader.crafting;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import net.minecraft.data.recipes.FinishedRecipe;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.DrumMachine;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;

public class MachineRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        AntimatterAPI.all(DrumMachine.class).forEach(d -> {
            Material m = d.getMaterial();
            if (m.has(AntimatterMaterialTypes.PLATE) && m.has(AntimatterMaterialTypes.ROD_LONG)){
                provider.addItemRecipe(output, GTCore.ID, "", "machines", d.getItem(Tier.NONE), of('H', AntimatterDefaultTools.HAMMER.getTag(), 'R', AntimatterMaterialTypes.ROD_LONG.getMaterialTag(m), 'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), " H ", "PRP", "PRP");
            }
        });
    }
}
