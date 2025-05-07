package org.gtreimagined.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.builder.GTCookingRecipeBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialType;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.material.MaterialTags.*;

public class MaterialRecipes {

    public static void loadMaterialRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        int craftingMultiplier = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? 1 : 2;
        if (!GTAPI.isModLoaded("tfc")) {
            DUST.all().forEach(m -> {
                if (m.has(GTMaterialTypes.INGOT)) {
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_grind_ingot", "gt_material",
                            DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', GTTools.MORTAR.getTag())
                                    .put('I', GTMaterialTypes.INGOT.getMaterialTag(m))
                                    .build(),
                            "MI");
                }
                if (m.has(ROCK)) {
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_grind_rock", "gt_material",
                            DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', GTTools.MORTAR.getTag())
                                    .put('I', ROCK.getMaterialTag(m))
                                    .build(),
                            "II ", "IIM");
                    provider.shapeless(consumer, GTCore.ID, m.getId() + "_grind_rock_2", "gt_material", GTMaterialTypes.DUST_SMALL.get(m, 1),
                            GTTools.MORTAR.getTag(), ROCK.getMaterialTag(m));
                }
                if (m.has(GTMaterialTypes.BEARING_ROCK)) {
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_grind_bearing_rock", "gt_material",
                            DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', GTTools.MORTAR.getTag())
                                    .put('I', GTMaterialTypes.BEARING_ROCK.getMaterialTag(m))
                                    .build(),
                            "II ", "IIM");
                    provider.shapeless(consumer, GTCore.ID, m.getId() + "_grind_bearing_rock_2", "gt_material", GTMaterialTypes.DUST_SMALL.get(m, 1),
                            GTTools.MORTAR.getTag(), GTMaterialTypes.BEARING_ROCK.getMaterialTag(m));
                }
                if (m.has(CRUSHED)){
                    provider.shapeless(consumer, GTCore.ID, m.getId() + "_grind_crushed", "gt_material", GTMaterialTypes.DUST_IMPURE.get(m, 1),
                            GTTools.MORTAR.getTag(), CRUSHED.getMaterialTag(m));
                }
            });
        }
        BLOCK.all().forEach(m -> {
            boolean blockReplacement = BLOCK.getReplacements().containsKey(m);
            int output = m.has(QUARTZ_LIKE_BLOCKS) ? 4 : 9;
            String[] strings = m.has(QUARTZ_LIKE_BLOCKS) ? new String[]{"II", "II"} : new String[]{"III", "III", "III"};
            if (m.has(INGOT)){
                if (GTCoreConfig.DISABLE_BLOCK_CRAFTING.get()) return;
                if (INGOT.getReplacements().containsKey(m) && blockReplacement) return;
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_block", "blocks", BLOCK.get().get(m).asStack(), ImmutableMap.of('I', INGOT.getMaterialTag(m)), strings);
                provider.shapeless(consumer, GTCore.ID,"ingot_" + m.getId() + "_from_block", "blocks", INGOT.get(m, output), BLOCK.getMaterialTag(m));
            } else if (m.has(GEM)){
                if (GEM.getReplacements().containsKey(m) && blockReplacement) return;
                provider.shapeless(consumer, GTCore.ID,"gem_" + m.getId() + "_from_block", "blocks", GEM.get(m, output), BLOCK.getMaterialTag(m));
                if (GTCoreConfig.DISABLE_BLOCK_CRAFTING.get()) return;
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_block", "blocks", BLOCK.get().get(m).asStack(), ImmutableMap.of('I', GEM.getMaterialTag(m)), strings);
            }
        });
        if (GTCoreConfig.DISABLE_BLOCK_CRAFTING.get()) {
            provider.removeRecipe(new ResourceLocation("iron_block"));
            provider.removeRecipe(new ResourceLocation("copper_block"));
            provider.removeRecipe(new ResourceLocation("gold_block"));
            provider.removeRecipe(new ResourceLocation("diamond_block"));
            provider.removeRecipe(new ResourceLocation("emerald_block"));
            provider.removeRecipe(new ResourceLocation("netherite_block"));
            provider.removeRecipe(new ResourceLocation("lapis_block"));
            provider.removeRecipe(new ResourceLocation("redstone_block"));
        }
        ROD.all().forEach(m -> {
            if (m.has(INGOT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod", "gt_material", ROD.get(m, craftingMultiplier), of('F', FILE.getTag(), 'I', INGOT.getMaterialTag(m)), "F ", " I");
            }
            if (m.has(BOLT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_bolt", "gt_material", BOLT.get(m, 2 * craftingMultiplier), of('F', SAW.getTag(), 'I', ROD.getMaterialTag(m)), "F ", " I");
                if (m.has(SCREW)) {
                    String[] pattern = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new String[]{"FI", "I "} : new String[]{"F", "I"};
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_screw", "gt_material", SCREW.get(m, 1), of('F', FILE.getTag(), 'I', BOLT.getMaterialTag(m)), pattern);
                }
            }
            if (m.has(RING)) {
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "gt_material",
                            RING.get(m, craftingMultiplier), ImmutableMap.of('H', HAMMER.getTag(), 'W', ROD.getMaterialTag(m)), "H ", " W");
                }
            }
            if (m.has(ROD_LONG)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod_from_long_rod", "rods", ROD.get(m, 2),
                        ImmutableMap.of('S', SAW.getTag(), 'R', ROD_LONG.getMaterialTag(m)), "SR");
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_long_rod", "rods", ROD_LONG.get(m, 1),
                            ImmutableMap.of('S', HAMMER.getTag(), 'R', ROD.getMaterialTag(m)), "RSR");
                }
            }
        });
        ROTOR.all().forEach(m -> {
            provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rotor", "gt_material",
                    ROTOR.get(m, 1), ImmutableMap.<Character, Object>builder()
                            .put('S', SCREWDRIVER.getTag())
                            .put('F', FILE.getTag())
                            .put('H', HAMMER.getTag())
                            .put('P', PLATE.getMaterialTag(m))
                            .put('W', SCREW.getMaterialTag(m))
                            .put('R', RING.getMaterialTag(m))
                            .build(),
                    "PHP", "WRF", "PSP");
        });
        PLATE.all().forEach(m -> {
            if (!m.has(NOSMASH)){
                if (m.has(INGOT)){
                    String[] array = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new String[]{"H", "I", "I"} : new String[]{"H", "I"};
                    provider.addItemRecipe(consumer, GTCore.ID, m.getId() + "_plate", "gt_material", PLATE.get(m), of('H', HAMMER.getTag(), 'I', INGOT.getMaterialTag(m)), array);
                }
                if (m.has(GEAR_SMALL)) {
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_small_gear", "gt_material",
                            GEAR_SMALL.get(m, 1), ImmutableMap.of('H', HAMMER.getTag(),'P', PLATE.getMaterialTag(m)), "P ", " H");
                }
                if (m.has(ITEM_CASING)) {
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_item_casing", "gt_material",
                            ITEM_CASING.get(m, 1), ImmutableMap.of('H', HAMMER.getTag(),'P', PLATE.getMaterialTag(m)), "H P");
                }
                if (m.has(FOIL)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_foil", "gt_materials",
                            FOIL.get(m, 2), of('H', HAMMER.getTag(), 'P', PLATE.getMaterialTag(m)), "HP");
                    if (m.has(WIRE_FINE)){
                        provider.addItemRecipe(consumer, GTCore.ID, m.getId() + "_fine_wire", "gt_materials",
                                WIRE_FINE.get(m), of('F', FOIL.getMaterialTag(m), 'W', WIRE_CUTTER.getTag()), "FW");
                    }
                }
            }
            if (m.has(GEAR)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_gear", "gt_material",
                        GEAR.get(m, 1), ImmutableMap.<Character, Object>builder()
                                .put('W', GTTools.WRENCH.getTag())
                                .put('P', PLATE.getMaterialTag(m))
                                .put('R', ROD.getMaterialTag(m))
                                .build(),
                        "RPR", "PWP", "RPR");
            }
            if (m.has(RING)) {
                if (m.has(RUBBERTOOLS)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "gt_material",
                            RING.get(m, craftingMultiplier), ImmutableMap.of('H', GTTools.WIRE_CUTTER.getTag(), 'W', PLATE.getMaterialTag(m)), "H ", " W");
                }
            }
        });
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in){
        addSmeltingRecipe(consumer, provider, input, output, amount, in, in);
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in, Material out){
        GTCookingRecipeBuilder.blastingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 100)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId())));
        GTCookingRecipeBuilder.smeltingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 200)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId() + "_smelting")));
    }
}
