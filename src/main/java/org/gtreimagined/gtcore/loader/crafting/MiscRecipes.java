package org.gtreimagined.gtcore.loader.crafting;

import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.REINFORCED_GLASS;
import static org.gtreimagined.gtcore.data.GTCoreItems.AdvancedAlloy;

public class MiscRecipes {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider){
        provider.addStackRecipe(consumer, GTCore.ID, "reinforce_glass_v", "blocks",
                new ItemStack(REINFORCED_GLASS, 7), of('G', ForgeCTags.GLASS, 'A', AdvancedAlloy), "GAG", "GGG", "GAG");
        provider.addStackRecipe(consumer, GTCore.ID, "reinforce_glass_h", "blocks",
                new ItemStack(REINFORCED_GLASS, 7), of('G', ForgeCTags.GLASS, 'A', AdvancedAlloy), "GGG", "AGA", "GGG");
    }
}
