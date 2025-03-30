package org.gtreimagined.gtcore.loader.crafting;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtcore.machine.HopperMachine;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.HAMMER;
import static org.gtreimagined.gtlib.data.GTTools.WRENCH;
import static org.gtreimagined.gtlib.machine.Tier.NONE;

public class MachineRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        GTAPI.all(DrumMachine.class).forEach(d -> {
            Material m = d.getMaterial();
            if (m.has(PLATE) && (m.has(ROD_LONG) || m.has(ROD))){
                TagKey<Item> rod = m.has(ROD_LONG) ? ROD_LONG.getMaterialTag(m) : ROD.getMaterialTag(m);
                provider.addItemRecipe(output, GTCore.ID, "", "machines", d.getItem(NONE), of('H', HAMMER.getTag(), 'R', rod, 'P', PLATE.getMaterialTag(m)), " H ", "PRP", "PRP");
            }
        });
        GTAPI.all(HopperMachine.class).forEach(h -> {
            Material m = h.getMaterial();
            if (m.has(PLATE)){
                provider.addItemRecipe(output, GTCore.ID, "", "machines", h.getItem(NONE), of('W', WRENCH.getTag(), 'C', Tags.Items.CHESTS_WOODEN, 'P', PLATE.getMaterialTag(m)), "PWP", "PCP", " P ");
            }
        });
    }
}
