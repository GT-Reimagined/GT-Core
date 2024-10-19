package org.gtreimagined.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.builder.AntimatterCookingRecipeBuilder;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.SAP_BAG;
import static org.gtreimagined.gtcore.loader.crafting.VanillaRecipes.addWoodRecipe;
import static muramasa.antimatter.data.AntimatterDefaultTools.SAW;
import static muramasa.antimatter.data.AntimatterMaterialTypes.DUST;

public class RubberRecipes {

    public static void addRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider){
        if (!AntimatterAPI.isModLoaded("gti") && !AntimatterAPI.isModLoaded("gt4r")) {
            AntimatterCookingRecipeBuilder.smeltingRecipe(Ingredient.of(GTCoreItems.StickyResin), DUST.get(GTCoreMaterials.Rubber, 1), 0.1f, 200).addCriterion("has_resin", provider.hasSafeItem(GTCoreItems.StickyResin)).build(consumer, "resin_to_rubber");
        }
        Item lumber = AntimatterAPI.isModLoaded("tfc") ? AntimatterAPI.get(Item.class, "rubber_lumber", GTCore.ID) : GTCoreBlocks.RUBBER_PLANKS.asItem();
        if (!AntimatterAPI.isModLoaded("tfc")){
            addWoodRecipe(consumer, provider, GTCore.ID, GTCoreTags.RUBBER_LOGS, GTCoreBlocks.RUBBER_PLANKS.asItem());
        } else if (AntimatterAPI.isModLoaded("tfc")){
            provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(lumber, 8), GTCoreTags.RUBBER_LOGS, TagUtils.getItemTag(new ResourceLocation("tfc", "saws")));
            provider.addItemRecipe(consumer, "rubber_wood", GTCoreBlocks.RUBBER_PLANKS, of('R', lumber), "RR", "RR");
            provider.shapeless(consumer, GTCore.ID, "rubber_lumber_from_planks", "rubber_wood", new ItemStack(lumber, 4), GTCoreBlocks.RUBBER_PLANKS, TagUtils.getItemTag(new ResourceLocation("tfc", "saws")));
            provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(AntimatterAPI.get(Item.class, "rubber_log_fence", GTCore.ID), 8), of('R', GTCoreBlocks.RUBBER_LOG, 'L', lumber), "RLR", "RLR");
        }
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_WOOD, 3), ImmutableMap.of('R', GTCoreBlocks.RUBBER_LOG), "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.STRIPPED_RUBBER_WOOD, 3), ImmutableMap.of('R', GTCoreBlocks.STRIPPED_RUBBER_LOG), "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_SIGN, 3), ImmutableMap.of('R', lumber, 'S', TagUtils.getForgelikeItemTag("rods/wooden")), "RRR", "RRR", " S ");
        int fenceAmount = AntimatterAPI.isModLoaded("tfc") ? 8 : 3, fenceGateAmount = AntimatterAPI.isModLoaded("tfc") ? 2 : 1;
        Object stick = AntimatterAPI.isModLoaded("tfc") ? lumber : TagUtils.getForgelikeItemTag("rods/wooden");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_FENCE, fenceAmount), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "RSR", "RSR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_FENCE_GATE, fenceGateAmount), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "SRS", "SRS");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_PRESSURE_PLATE, 1), ImmutableMap.of('R', lumber), "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_SLAB, 6), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "RRR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_STAIRS, 4), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R  ", "RR ", "RRR");
        int doorAmount = AntimatterAPI.isModLoaded("tfc") ? 2 : 3, trapdoorAmount = AntimatterAPI.isModLoaded("tfc") ? 3 : 2;
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_DOOR, doorAmount), ImmutableMap.of('R', lumber), "RR", "RR", "RR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_TRAPDOOR, trapdoorAmount), ImmutableMap.of('R', lumber), "RRR", "RRR");
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreItems.RubberBoat), ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R R", "RRR");
        provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", new ItemStack(GTCoreBlocks.RUBBER_BUTTON), GTCoreBlocks.RUBBER_PLANKS);
        provider.addStackRecipe(consumer, GTCore.ID, "sapbag", "blocks",
                new ItemStack(SAP_BAG), of('L', ForgeCTags.LEATHER, 'S', SAW.getTag(), 's', Items.STICK), "sss", "LSL", "LLL");
        provider.addStackRecipe(consumer, GTCore.ID, "torch", "torches", new ItemStack(Items.TORCH, 4), of('S', GTCoreItems.StickyResin, 'R', Items.STICK), "S", "R");
    }

}
