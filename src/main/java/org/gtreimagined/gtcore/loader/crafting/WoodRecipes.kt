@file:JvmName("WoodRecipes")
package org.gtreimagined.gtcore.loader.crafting

import com.google.common.collect.ImmutableMap
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.Tags
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtcore.data.GTCoreBlocks
import org.gtreimagined.gtcore.data.GTCoreItems
import org.gtreimagined.gtcore.data.GTCoreMaterials
import org.gtreimagined.gtcore.data.GTCoreTags
import org.gtreimagined.gtlib.GTAPI.get
import org.gtreimagined.gtlib.GTAPI.isModLoaded
import org.gtreimagined.gtlib.data.GTMaterialTypes.DUST
import org.gtreimagined.gtlib.data.GTTools
import org.gtreimagined.gtlib.datagen.builder.GTCookingRecipeBuilder
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider
import org.gtreimagined.gtlib.util.RegistryUtils
import org.gtreimagined.gtlib.util.TagUtils
import java.util.function.Consumer

fun addWoodRecipes(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider) {
    if (!isModLoaded("gt5r") && !isModLoaded("gt4r")) {
        GTCookingRecipeBuilder.smeltingRecipe(Ingredient.of(GTCoreItems.StickyResin), DUST.get(GTCoreMaterials.Rubber, 1), 0.1f, 200).addCriterion("has_resin", provider.hasSafeItem(GTCoreItems.StickyResin))
            .build(consumer, "resin_to_rubber")
    }
    val lumber = if (isModLoaded("tfc")) get<Item>("rubber_lumber", GTCore.ID) ?: return else GTCoreBlocks.RUBBER_PLANKS.asItem()
    if (!isModLoaded("tfc")) {
        addWoodRecipe(consumer, provider, GTCore.ID, "rubber_planks", GTCoreTags.RUBBER_LOGS, GTCoreBlocks.RUBBER_PLANKS.asItem())
        provider.addStackRecipe(consumer, "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_HANGING_SIGN, 6),
            ImmutableMap.of('W', lumber, 'C', Items.CHAIN), "C C", "WWW", "WWW")
    } else if (isModLoaded("tfc")) {
        provider.addStackRecipe(consumer, "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_HANGING_SIGN, 3),
            ImmutableMap.of('W', lumber, 'C', RegistryUtils.getItemFromID("tfc", "metal/chain/wrought_iron")), "C C", "WWW", "WWW")
        provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", ItemStack(lumber, 8),
            GTCoreTags.RUBBER_LOGS, TagUtils.getItemTag(ResourceLocation("tfc", "saws")))
        provider.addItemRecipe(consumer, "rubber_wood", GTCoreBlocks.RUBBER_PLANKS,
            ImmutableMap.of('R', lumber), "RR", "RR")
        provider.shapeless(consumer, GTCore.ID, "rubber_lumber_from_planks", "rubber_wood", ItemStack(lumber, 4),
            GTCoreBlocks.RUBBER_PLANKS, TagUtils.getItemTag(ResourceLocation("tfc", "saws")))
        provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(get<Item>("rubber_log_fence", GTCore.ID)!!, 8),
            ImmutableMap.of('R', GTCoreBlocks.RUBBER_LOG, 'L', lumber), "RLR", "RLR")
    }
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_WOOD, 3),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_LOG), "RR", "RR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.STRIPPED_RUBBER_WOOD, 3),
        ImmutableMap.of('R', GTCoreBlocks.STRIPPED_RUBBER_LOG), "RR", "RR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_SIGN, 3),
        ImmutableMap.of('R', lumber, 'S', TagUtils.getForgelikeItemTag("rods/wooden")), "RRR", "RRR", " S ")
    val fenceAmount = if (isModLoaded("tfc")) 8 else 3
    val fenceGateAmount = if (isModLoaded("tfc")) 2 else 1
    val stick: Any =
        (if (isModLoaded("tfc")) lumber else TagUtils.getForgelikeItemTag("rods/wooden"))!!
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_FENCE, fenceAmount),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "RSR", "RSR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_FENCE_GATE, fenceGateAmount),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS, 'S', stick), "SRS", "SRS")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_PRESSURE_PLATE, 1),
        ImmutableMap.of('R', lumber), "RR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_SLAB, 6),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "RRR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_STAIRS, 4),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R  ", "RR ", "RRR")
    val doorAmount = if (isModLoaded("tfc")) 2 else 3
    val trapdoorAmount = if (isModLoaded("tfc")) 3 else 2
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_DOOR, doorAmount),
        ImmutableMap.of('R', lumber), "RR", "RR", "RR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_TRAPDOOR, trapdoorAmount),
        ImmutableMap.of('R', lumber), "RRR", "RRR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreItems.RubberBoat),
        ImmutableMap.of('R', GTCoreBlocks.RUBBER_PLANKS), "R R", "RRR")
    provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreItems.RubberChestBoat), Tags.Items.CHESTS_WOODEN, GTCoreItems.RubberBoat)
    provider.shapeless(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.RUBBER_BUTTON), GTCoreBlocks.RUBBER_PLANKS)
    provider.addStackRecipe(consumer, GTCore.ID, "sapbag", "blocks", ItemStack(GTCoreBlocks.SAP_BAG),
        ImmutableMap.of('L', Tags.Items.LEATHER, 'S', GTTools.SAW.getTag(), 's', Items.STICK), "sss", "LSL", "LLL")
    provider.addStackRecipe(consumer, GTCore.ID, "torch", "torches", ItemStack(Items.TORCH, 4),
        ImmutableMap.of('S', GTCoreItems.StickyResin, 'R', Items.STICK), "S", "R")

    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.TREATED_SLAB, 6),
        ImmutableMap.of('R', GTCoreBlocks.TREATED_PLANKS), "RRR")
    provider.addStackRecipe(consumer, GTCore.ID, "", "rubber_wood", ItemStack(GTCoreBlocks.TREATED_STAIRS, 4),
        ImmutableMap.of('R', GTCoreBlocks.TREATED_PLANKS), "R  ", "RR ", "RRR")
}
