@file:JvmName("VanillaRecipes")
@file:Suppress("DEPRECATION", "removal")

package org.gtreimagined.gtcore.loader.crafting

import com.google.common.collect.ImmutableMap
import com.ibm.icu.text.PluralRules
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.WeatheringCopper
import net.minecraftforge.common.Tags
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtcore.GTCoreConfig
import org.gtreimagined.gtcore.data.GTCoreBlocks
import org.gtreimagined.gtcore.data.GTCoreItems
import org.gtreimagined.gtcore.data.GTCoreMaterials
import org.gtreimagined.gtlib.GTAPI
import org.gtreimagined.gtlib.data.GTMaterialTypes.DUST
import org.gtreimagined.gtlib.data.GTMaterialTypes.PLATE
import org.gtreimagined.gtlib.data.GTMaterialTypes.ROD
import org.gtreimagined.gtlib.data.GTTools
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider
import org.gtreimagined.gtlib.util.RegistryUtils
import org.gtreimagined.gtlib.util.TagUtils
import java.util.function.Consumer

fun loadVanillaRecipes(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider) {
    provider.addStackRecipe(consumer, GTCore.ID, "lead_from_resin", "", ItemStack(Items.LEAD, 2),
        ImmutableMap.of('S', Items.STRING, 'R', GTCoreItems.StickyResin), "SS ", "SR ", "  S")

    provider.addItemRecipe(consumer, GTCore.ID, "piston_sticky", "gears", Blocks.STICKY_PISTON,
        ImmutableMap.of('S', GTCoreItems.StickyResin, 'P', Blocks.PISTON), "S", "P")
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(GTCoreBlocks.BASALT.state.block), RecipeCategory.DECORATIONS, Items.BASALT)
        .unlockedBy("has_basalt", provider.hasSafeItem(GTCoreBlocks.BASALT.state.block))
        .save(consumer, ResourceLocation(GTCore.ID, "basalt_to_vanilla_basalt"))
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(GTCoreBlocks.BASALT.state.block), RecipeCategory.DECORATIONS, Items.POLISHED_BASALT)
        .unlockedBy("has_basalt", provider.hasSafeItem(GTCoreBlocks.BASALT.state.block))
        .save(consumer, ResourceLocation(GTCore.ID, "basalt_to_vanilla_polished_basalt"))
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(GTCoreBlocks.BASALT.state.block), RecipeCategory.DECORATIONS, Items.SMOOTH_BASALT)
        .unlockedBy("has_basalt", provider.hasSafeItem(GTCoreBlocks.BASALT.state.block))
        .save(consumer, ResourceLocation(GTCore.ID, "basalt_to_vanilla_smooth_basalt"))
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.BASALT), RecipeCategory.DECORATIONS, GTCoreBlocks.BASALT.state.block.asItem())
        .unlockedBy("has_basalt", provider.hasSafeItem(Items.BASALT))
        .save(consumer, ResourceLocation(GTCore.ID, "vanilla_basalt_to_basalt"))
    loadOverrides(consumer, provider)
    loadWood(consumer, provider)
}

private fun loadOverrides(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider) {
    if (GTCoreConfig.DISABLE_WOOD_TOOLS.get()) {
        provider.removeRecipe(ResourceLocation("wooden_axe"))
        provider.removeRecipe(ResourceLocation("wooden_pickaxe"))
        provider.removeRecipe(ResourceLocation("wooden_hoe"))
        provider.removeRecipe(ResourceLocation("wooden_sword"))
    }
    if (GTCoreConfig.DISABLE_CHARCOAL_SMELTING.get()) {
        provider.removeRecipe(ResourceLocation("charcoal"))
        provider.removeRecipe(ResourceLocation("energizedpower", "smelting/charcoal_from_smelting_sawdust_block"))
    }
    if (GTCoreConfig.HONEYCOMB_REPLACEMENT.get()) {
        for (weatherState in WeatheringCopper.WeatherState.entries.toTypedArray()) {
            val prefix = if (weatherState == WeatheringCopper.WeatherState.UNAFFECTED) "" else
                "${weatherState.name.lowercase()}_"
            if (prefix.isEmpty()) {
                addBeeswaxRecipe(consumer, provider, "copper_block")
            } else {
                addBeeswaxRecipe(consumer, provider, prefix + "copper")
            }
            addBeeswaxRecipe(consumer, provider, prefix + "cut_copper")
            addBeeswaxRecipe(consumer, provider, prefix + "cut_copper_stairs")
            addBeeswaxRecipe(consumer, provider, prefix + "cut_copper_slab")
        }
        provider.addItemRecipe(
            consumer, "misc", Items.CANDLE, ImmutableMap.of(
                'S', Items.STRING, 'W', DUST.getMaterialTag(
                    GTCoreMaterials.Beeswax
                )
            ), "S", "W"
        )
    }
    if (!GTCoreConfig.VANILLA_OVERRIDES.get() || GTAPI.isModLoaded("tfc")) return
    provider.addStackRecipe(consumer, "minecraft", "", "misc", ItemStack(Items.IRON_BARS, 8),
        ImmutableMap.of('R', ROD.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), " H ", "RRR", "RRR")
    provider.addItemRecipe(consumer, "minecraft", "", "misc", Items.BUCKET,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "IHI", " I ")
    provider.addItemRecipe(consumer, "minecraft", "", "misc", Items.HOPPER,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'W', GTTools.WRENCH.getTag(), 'C', Tags.Items.CHESTS_WOODEN), "IWI", "ICI", " I ")
    provider.addStackRecipe(consumer, "minecraft", "", "cauldrons", ItemStack(Items.CAULDRON),
        ImmutableMap.of('P', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "P P", "PHP", "PPP")
    provider.addStackRecipe(consumer, "minecraft", "", "misc", ItemStack(Items.IRON_DOOR, 3),
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "II ", "IIH", "II ")
    provider.addStackRecipe(consumer, "minecraft", "", "misc", ItemStack(Items.IRON_TRAPDOOR),
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "II ", "IIH")
    provider.addStackRecipe(consumer, "minecraft", "", "misc", ItemStack(Items.LIGHT_WEIGHTED_PRESSURE_PLATE),
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Gold), 'H', GTTools.HAMMER.getTag()), "IIH")
    provider.addStackRecipe(consumer, "minecraft", "", "misc", ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE), ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "IIH")
    provider.addItemRecipe(consumer, "misc", Items.SHEARS,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag(), 'F', GTTools.FILE.getTag()), "HI", "IF")

    provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_HELMET,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "III", "IHI")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_CHESTPLATE,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "IHI", "III", "III")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_LEGGINGS,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "III", "IHI", "I I")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_BOOTS,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Iron), 'H', GTTools.HAMMER.getTag()), "I I", "IHI")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_HELMET,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Gold), 'H', GTTools.HAMMER.getTag()), "III", "IHI")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_CHESTPLATE,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Gold), 'H', GTTools.HAMMER.getTag()), "IHI", "III", "III")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_LEGGINGS,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Gold), 'H', GTTools.HAMMER.getTag()), "III", "IHI", "I I")
    provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_BOOTS,
        ImmutableMap.of('I', PLATE.getMaterialTag(GTCoreMaterials.Gold), 'H', GTTools.HAMMER.getTag()), "I I", "IHI")
}

fun addWoodRecipe(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider, domain: String, id: String, log: TagKey<Item>, plank: Item) {
    val amount1 = if (GTCoreConfig.HARDER_WOOD.get()) 2 else 4
    val amount2 = if (GTCoreConfig.HARDER_WOOD.get()) 4 else 6
    provider.shapeless(consumer, domain, id, "planks", ItemStack(plank, amount1), log)
    provider.addStackRecipe(consumer, domain, "${id}_$amount2", "planks", ItemStack(plank, amount2),
        ImmutableMap.of('S', GTTools.SAW.getTag(), 'P', log), "S", "P")
}

fun addBeeswaxRecipe(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider, id: String) {
    provider.shapeless(
        consumer, "minecraft", "waxed_${id}_from_honeycomb", "waxed_blocks", ItemStack(RegistryUtils.getItemFromID(ResourceLocation("waxed_$id"))),
        RegistryUtils.getItemFromID(ResourceLocation(id)), DUST.getMaterialTag(GTCoreMaterials.Beeswax)
    )
}

private fun loadWood(consumer: Consumer<FinishedRecipe>, provider: GTRecipeProvider) {
    if (GTCoreConfig.HARDER_WOOD.get()) {
        provider.addStackRecipe(consumer, "minecraft", "", "wood_stuff", ItemStack(Items.STICK, 2),
            ImmutableMap.of('P', ItemTags.PLANKS), "P", "P")
        provider.addStackRecipe(consumer, GTCore.ID, "sticks_4", "wood_stuff", ItemStack(Items.STICK, 4), ImmutableMap.of('P', ItemTags.PLANKS, 'S', GTTools.SAW.getTag()), "S", "P", "P")
    }
    val customSuffixes: MutableMap<String, String> = HashMap()
    val modWoods: MutableMap<String, List<String>> = Object2ObjectOpenHashMap()
    customSuffixes["crimson"] = "stems"
    customSuffixes["warped"] = "stems"
    modWoods["minecraft"] = listOf(
        "oak", "birch", "spruce", "jungle", "acacia", "dark_oak",
        "mangrove", "cherry", "crimson", "warped")
    if (GTAPI.isModLoaded("northstar")) {
        modWoods["northstar"] = listOf("wilter", "argyre", "coiler", "calorian")
    }
    if (GTAPI.isModLoaded("ad_astra")) {
        modWoods["ad_astra"] = listOf("aeronos", "strophar", "glacian")
        customSuffixes["aeronos"] = "caps"
        customSuffixes["strophar"] = "caps"
    }
    if (GTAPI.isModLoaded("terrestria")) {
        modWoods["terrestria"] = listOf("cypress", "hemlock", "japanese_maple", "rainbow_eucalyptus", "redwood",
            "rubber", "sakura", "willow", "yucca_palm")
    }
    if (GTAPI.isModLoaded("twilightforest")) modWoods["twilightforest"] = listOf("twilight_oak", "canopy", "mangrove", "mining")
    if (GTAPI.isModLoaded("undergarden")) modWoods["undergarden"] = listOf("smogstem", "wigglewood", "grongle")
    if (GTAPI.isModLoaded("botania")) modWoods["botania"] = listOf("livingwood", "dreamwood")
    if (GTAPI.isModLoaded("traverse")) modWoods["traverse"] = listOf("fir")
    if (GTAPI.isModLoaded("forestry")) {
        val domain = "forestry"
        modWoods[domain] = mutableListOf("larch", "teak", "acacia_desert", "lime", "chestnut", "wenge", "baobab", "sequoia",
            "kapok", "ebony", "mahogany", "balsa", "willow", "walnut", "greenheart", "hill_cherry", "mahoe", "poplar", "palm", "papaya", "pine", "plum", "maple", "citrus", "giganteum", "ipe", "padauk", "cocobolo", "zebrawood")
        val fireProofPlanks: MutableList<String> = ArrayList(modWoods[domain]!!)
        fireProofPlanks.addAll(modWoods["minecraft"]!!)
        fireProofPlanks.removeAll(mutableListOf("mangrove", "crimson", "warped").toSet())
        for (wood in fireProofPlanks) {
            val suffix = customSuffixes.getOrDefault(wood, "logs")
            val planks = ResourceLocation(domain, wood + "_fireproof_planks")
            addWoodRecipe(consumer, provider, domain, planks.path, TagUtils.getItemTag(ResourceLocation(domain, "fireproof_" + wood + "_" + suffix)), RegistryUtils.getItemFromID(planks))
            val slab = ResourceLocation(domain, wood + "_fireproof_slab")
            provider.addItemRecipe(consumer, domain, slab.path + "_to_" + planks.path, "slabs", RegistryUtils.getItemFromID(planks), ImmutableMap.of('S', RegistryUtils.getItemFromID(slab)), "S", "S")
        }
    }

    modWoods.forEach { (domain, w) ->
        for (wood in w) {
            val suffix = customSuffixes.getOrDefault(wood, "logs")
            val planks = ResourceLocation(domain, wood + "_planks")
            val id = "${if (domain == "twilightforest") "wood/" else ""}${planks.path}"
            addWoodRecipe(consumer, provider, domain, id, TagUtils.getItemTag(ResourceLocation(domain, wood + "_" + suffix)), RegistryUtils.getItemFromID(planks))
            val slab = ResourceLocation(domain, wood + "_slab")
            provider.addItemRecipe(consumer, domain, slab.path + "_to_" + planks.path, "slabs", RegistryUtils.getItemFromID(planks), ImmutableMap.of('S', RegistryUtils.getItemFromID(slab)), "S", "S")
        }
    }
    if (GTAPI.isModLoaded("twilightforest")) {
        val tf = "twilightforest"
        val logs = arrayOf("timewood", "darkwood", "sortwood", "transwood")
        val planks = arrayOf("time", "dark", "sorting", "transformation")
        for ((i, log) in logs.withIndex()) {
            val plank = ResourceLocation(tf, "${planks[i]}_planks")
            addWoodRecipe(consumer, provider, tf, "wood/${plank.path}", TagUtils.getItemTag(ResourceLocation(tf, "${log}_logs")), RegistryUtils.getItemFromID(plank))
            val slab = ResourceLocation(tf, "${planks[i]}_slab")
            provider.addItemRecipe(consumer, tf, slab.path + "_to_" + plank.path, "slabs", RegistryUtils.getItemFromID(plank), ImmutableMap.of('S', RegistryUtils.getItemFromID(slab)), "S", "S")
        }
    }

    val stones = arrayOf("stone", "smooth_stone", "sandstone", "cut_sandstone", "cobblestone", "red_sandstone", "cut_red_sandstone", "prismarine",
        "dark_prismarine", "polished_granite", "smooth_red_sandstone", "polished_diorite", "mossy_cobblestone", "smooth_sandstone", "smooth_quartz",
        "granite", "andesite", "polished_andesite", "diorite", "blackstone", "polished_blackstone", "purpur", "quartz", "brick",
        "stone_brick", "nether_brick", "prismarine_brick", "mossy_stone_brick", "end_stone_brick", "red_nether_brick", "polished_blackstone_brick")
    for (stone in stones) {
        val suffix = if (stone == "purpur" || stone == "quartz") "_block" else if (stone.contains("brick")) "s" else ""
        val full = RegistryUtils.getItemFromID(ResourceLocation("$stone$suffix"))
        val slab = RegistryUtils.getItemFromID(ResourceLocation("${stone}_slab"))
        val pattern: Array<String> =
            if (stone == "purpur" || stone == "quartz" || stone == "sandstone" || stone == "red_sandstone" || stone == "stone_brick"
                || stone == "nether_brick" || stone == "polished_blackstone")
                arrayOf("SS")
            else arrayOf("S", "S")
        provider.addItemRecipe(consumer, GTCore.ID, stone + "_slab_to_" + stone, "slabs", full,
            ImmutableMap.of('S', slab), *pattern)
    }
}
