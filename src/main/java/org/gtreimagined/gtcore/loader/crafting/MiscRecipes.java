package org.gtreimagined.gtcore.loader.crafting;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.REINFORCED_GLASS;
import static org.gtreimagined.gtcore.data.GTCoreItems.AdvancedAlloy;

public class MiscRecipes {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        provider.addStackRecipe(consumer, GTCore.ID, "reinforce_glass_v", "blocks",
                new ItemStack(REINFORCED_GLASS, 7), of('G', Tags.Items.GLASS, 'A', AdvancedAlloy), "GAG", "GGG", "GAG");
        provider.addStackRecipe(consumer, GTCore.ID, "reinforce_glass_h", "blocks",
                new ItemStack(REINFORCED_GLASS, 7), of('G', Tags.Items.GLASS, 'A', AdvancedAlloy), "GGG", "AGA", "GGG");
    }
}
