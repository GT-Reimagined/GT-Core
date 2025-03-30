package org.gtreimagined.gtcore.loader.crafting;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.FluidPipe;
import org.gtreimagined.gtlib.pipe.types.ItemPipe;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.PLATE;
import static org.gtreimagined.gtlib.data.GTTools.*;

public class Pipes {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        final CriterionTriggerInstance in = provider.hasSafeItem(WRENCH.getTag());
        GTAPI.all(ItemPipe.class, i -> {
            Material m = i.getMaterial();
            if (!m.has(PLATE) || m == GTLibMaterials.Wood) return;
            if (i.getSizes().contains(PipeSize.TINY)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_tiny", "antimatter_pipes", new ItemStack(i.getBlock(PipeSize.TINY), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m), 'S', SAW.getTag()), "SP ", "W H");
            }
            if (i.getSizes().contains(PipeSize.SMALL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_small", "antimatter_pipes", new ItemStack(i.getBlock(PipeSize.SMALL), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), " P ", "W H");
            }
            if (i.getSizes().contains(PipeSize.NORMAL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_normal", "antimatter_pipes", new ItemStack(i.getBlock(PipeSize.NORMAL), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H");
            }
            if (i.getSizes().contains(PipeSize.LARGE)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_large", "antimatter_pipes", new ItemStack(i.getBlock(PipeSize.LARGE), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H", "PPP");
            }
        });
        GTAPI.all(FluidPipe.class, f -> {
            Material m = f.getMaterial();
            if (f.getSizes().contains(PipeSize.QUADRUPLE) && f.getSizes().contains(PipeSize.NORMAL)){
                provider.addItemRecipe(consumer, Ref.ID, "", "antimatter_pipes", f.getBlock(PipeSize.QUADRUPLE), of('P', f.getBlock(PipeSize.NORMAL)), "PP", "PP");
                provider.shapeless(consumer, Ref.ID, "", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.NORMAL), 4), f.getBlock(PipeSize.QUADRUPLE));
            }
            if (f.getSizes().contains(PipeSize.NONUPLE) && f.getSizes().contains(PipeSize.SMALL)){
                provider.addItemRecipe(consumer, Ref.ID, "", "antimatter_pipes", f.getBlock(PipeSize.NONUPLE), of('P', f.getBlock(PipeSize.SMALL)), "PPP", "PPP", "PPP");
                provider.shapeless(consumer, Ref.ID, "", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.SMALL), 9), f.getBlock(PipeSize.NONUPLE));
            }
            if (!m.has(PLATE) || m == GTLibMaterials.Wood) return;
            if (f.getSizes().contains(PipeSize.TINY)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_tiny", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.TINY), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m), 'S', SAW.getTag()), "SP ", "W H");
            }
            if (f.getSizes().contains(PipeSize.SMALL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_small", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.SMALL), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), " P ", "W H");
            }
            if (f.getSizes().contains(PipeSize.NORMAL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_normal", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.NORMAL), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H");
            }
            if (f.getSizes().contains(PipeSize.LARGE)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_large", "antimatter_pipes", new ItemStack(f.getBlock(PipeSize.LARGE), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H", "PPP");
            }
        });
    }
}
