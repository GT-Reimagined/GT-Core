package org.gtreimagined.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.builder.GTCookingRecipeBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.SAP_BAG;
import static org.gtreimagined.gtcore.loader.crafting.VanillaRecipes.addWoodRecipe;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.DUST;
import static org.gtreimagined.gtlib.data.GTTools.SAW;

public class RubberRecipes {

    public static void addRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        if (!GTAPI.isModLoaded("gt5r") && !GTAPI.isModLoaded("gt4r")) {
            GTCookingRecipeBuilder.smeltingRecipe(Ingredient.of(GTCoreItems.StickyResin), DUST.get(GTCoreMaterials.Rubber, 1), 0.1f, 200).addCriterion("has_resin", provider.hasSafeItem(GTCoreItems.StickyResin)).build(consumer, "resin_to_rubber");
        }
        Item lumber = GTAPI.isModLoaded("tfc") ? GTAPI.get(Item.class, "rubber_lumber", GTCore.ID) : GTCoreBlocks.RUBBER_PLANKS.asItem();
        if (!GTAPI.isModLoaded("tfc")){
            addWoodRecipe(consumer, provider, GTCore.ID, GTCoreTags.RUBBER_LOGS, GTCoreBlocks.RUBBER_PLANKS.asItem());
        } else if (GTAPI.isModLoaded("tfc")){
            provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(lumber, 8), GTCoreTags.RUBBER_LOGS, TagUtils.getItemTag(new ResourceLocation("tfc", "saws")));
            provider.addItemRecipe(consumer, "rubber_wood", GTCoreBlocks.RUBBER_PLANKS, of('R', lumber), "RR", "RR");
            provider.shapeless(consumer, GTCore.ID, "rubber_lumber_from_planks", "rubber_wood", new ItemStack(lumber, 4), GTCoreBlocks.RUBBER_PLANKS, TagUtils.getItemTag(new ResourceLocation("tfc", "saws")));
            provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTAPI.get(Item.class, "rubber_log_fence", GTCore.ID), 8), of('R', GTCoreBlocks.RUBBER_LOG, 'L', lumber), "RLR", "RLR");
        }
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_WOOD, 3), ImmutableMap.of('R', GTCoreBlocks.RUBBER_LOG), "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.STRIPPED_RUBBER_WOOD, 3), ImmutableMap.of('R', GTCoreBlocks.STRIPPED_RUBBER_LOG), "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_SIGN, 3), ImmutableMap.of('R', lumber, 'S', TagUtils.getForgelikeItemTag("rods/wooden")), "RRR", "RRR", " S ");
        int fenceAmount = GTAPI.isModLoaded("tfc") ? 8 : 3, fenceGateAmount = GTAPI.isModLoaded("tfc") ? 2 : 1;
        Object stick = GTAPI.isModLoaded("tfc") ? lumber : TagUtils.getForgelikeItemTag("rods/wooden");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_FENCE, fenceAmount), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "RSR", "RSR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_FENCE_GATE, fenceGateAmount), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "SRS", "SRS");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_PRESSURE_PLATE, 1), ImmutableMap.of('R', lumber), "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_SLAB, 6), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "RRR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_STAIRS, 4), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R  ", "RR ", "RRR");
        int doorAmount = GTAPI.isModLoaded("tfc") ? 2 : 3, trapdoorAmount = GTAPI.isModLoaded("tfc") ? 3 : 2;
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_DOOR, doorAmount), ImmutableMap.of('R', lumber), "RR", "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_TRAPDOOR, trapdoorAmount), ImmutableMap.of('R', lumber), "RRR", "RRR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreItems.RubberBoat), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R R", "RRR");
        provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreItems.RubberChestBoat), Tags.Items.CHESTS_WOODEN, GTCoreItems.RubberBoat);
        provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_BUTTON), GTCoreBlocks.RUBBER_PLANKS);
        provider.addStackRecipe(consumer, GTCore.ID, "sapbag", "blocks",
                new ItemStack(SAP_BAG), of('L', Tags.Items.LEATHER, 'S', SAW.getTag(), 's', Items.STICK), "sss", "LSL", "LLL");
        provider.addStackRecipe(consumer, GTCore.ID, "torch", "torches", new ItemStack(Items.TORCH, 4), of('S', GTCoreItems.StickyResin, 'R', Items.STICK), "S", "R");
    }

}
