package io.github.gregtechintergalactical.gtcore.loader.crafting;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.machine.DrumMachine;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;

public class MachineRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        AntimatterAPI.all(DrumMachine.class).forEach(d -> {
            Material m = d.getMaterial();
            if (m.has(AntimatterMaterialTypes.PLATE) && m.has(AntimatterMaterialTypes.ROD)){
                provider.addItemRecipe(output, GTCore.ID, "", "machines", "has_hammer", provider.hasSafeItem(AntimatterDefaultTools.HAMMER.getTag()), d.getItem(Tier.NONE), of('H', AntimatterDefaultTools.HAMMER.getTag(), 'R', AntimatterMaterialTypes.ROD.getMaterialTag(m), 'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), " H ", "PRP", "PRP");
            }
        });
    }
}
