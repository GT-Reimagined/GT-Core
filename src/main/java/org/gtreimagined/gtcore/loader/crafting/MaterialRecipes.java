package org.gtreimagined.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.datagen.builder.AntimatterCookingRecipeBuilder;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.material.MaterialType;
import muramasa.antimatter.material.MaterialTypeItem;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.GTCoreConfig;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.material.MaterialTags.*;

public class MaterialRecipes {

    public static void loadMaterialRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider){
        int craftingMultiplier = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? 1 : 2;
        if (!GTAPI.isModLoaded("tfc")) {
            AntimatterMaterialTypes.DUST.all().forEach(m -> {
                if (m.has(AntimatterMaterialTypes.INGOT)) {
                    provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_grind_ingot", "antimatter_material",
                            AntimatterMaterialTypes.DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', AntimatterDefaultTools.MORTAR.getTag())
                                    .put('I', AntimatterMaterialTypes.INGOT.getMaterialTag(m))
                                    .build(),
                            "MI");
                }
                if (m.has(AntimatterMaterialTypes.ROCK)) {
                    provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_grind_rock", "antimatter_material",
                            AntimatterMaterialTypes.DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', AntimatterDefaultTools.MORTAR.getTag())
                                    .put('I', AntimatterMaterialTypes.ROCK.getMaterialTag(m))
                                    .build(),
                            "II ", "IIM");
                    provider.shapeless(consumer, m.getId() + "_grind_rock_2", "antimatter_material", AntimatterMaterialTypes.DUST_SMALL.get(m, 1),
                            AntimatterDefaultTools.MORTAR.getTag(), AntimatterMaterialTypes.ROCK.getMaterialTag(m));
                }
                if (m.has(AntimatterMaterialTypes.BEARING_ROCK)) {
                    provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_grind_bearing_rock", "antimatter_material",
                            AntimatterMaterialTypes.DUST.get(m, 1), ImmutableMap.<Character, Object>builder()
                                    .put('M', AntimatterDefaultTools.MORTAR.getTag())
                                    .put('I', AntimatterMaterialTypes.BEARING_ROCK.getMaterialTag(m))
                                    .build(),
                            "II ", "IIM");
                    provider.shapeless(consumer, m.getId() + "_grind_bearing_rock_2", "antimatter_material", AntimatterMaterialTypes.DUST_SMALL.get(m, 1),
                            AntimatterDefaultTools.MORTAR.getTag(), AntimatterMaterialTypes.BEARING_ROCK.getMaterialTag(m));
                }
                if (m.has(AntimatterMaterialTypes.CRUSHED)){
                    provider.shapeless(consumer, m.getId() + "_grind_crushed", "antimatter_material", AntimatterMaterialTypes.DUST_IMPURE.get(m, 1),
                            AntimatterDefaultTools.MORTAR.getTag(), AntimatterMaterialTypes.CRUSHED.getMaterialTag(m));
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
        AntimatterMaterialTypes.ROD.all().forEach(m -> {
            if (m.has(AntimatterMaterialTypes.INGOT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod", "antimatter_material", AntimatterMaterialTypes.ROD.get(m, craftingMultiplier), of('F', AntimatterDefaultTools.FILE.getTag(), 'I', AntimatterMaterialTypes.INGOT.getMaterialTag(m)), "F ", " I");
            }
            if (m.has(AntimatterMaterialTypes.BOLT)) {
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_bolt", "antimatter_material", AntimatterMaterialTypes.BOLT.get(m, 2 * craftingMultiplier), of('F', SAW.getTag(), 'I', AntimatterMaterialTypes.ROD.getMaterialTag(m)), "F ", " I");
                if (m.has(AntimatterMaterialTypes.SCREW)) {
                    String[] pattern = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new String[]{"FI", "I "} : new String[]{"F", "I"};
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_screw", "antimatter_material", AntimatterMaterialTypes.SCREW.get(m, 1), of('F', AntimatterDefaultTools.FILE.getTag(), 'I', AntimatterMaterialTypes.BOLT.getMaterialTag(m)), pattern);
                }
            }
            if (m.has(AntimatterMaterialTypes.RING)) {
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "antimatter_material",
                            AntimatterMaterialTypes.RING.get(m, craftingMultiplier), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(), 'W', AntimatterMaterialTypes.ROD.getMaterialTag(m)), "H ", " W");
                }
            }
            if (m.has(ROD_LONG)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rod_from_long_rod", "rods", ROD.get(m, 2),
                        ImmutableMap.of('S', SAW.getTag(), 'R', ROD_LONG.getMaterialTag(m)), "SR");
                if (!m.has(NOSMASH)){
                    provider.addStackRecipe(consumer, GTCore.ID, "", "rods", ROD_LONG.get(m, 1),
                            ImmutableMap.of('S', HAMMER.getTag(), 'R', ROD.getMaterialTag(m)), "RSR");
                }
            }
        });
        AntimatterMaterialTypes.ROTOR.all().forEach(m -> {
            provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_rotors", "antimatter_material",
                    AntimatterMaterialTypes.ROTOR.get(m, 1), ImmutableMap.<Character, Object>builder()
                            .put('S', SCREWDRIVER.getTag())
                            .put('F', AntimatterDefaultTools.FILE.getTag())
                            .put('H', AntimatterDefaultTools.HAMMER.getTag())
                            .put('P', AntimatterMaterialTypes.PLATE.getMaterialTag(m))
                            .put('W', AntimatterMaterialTypes.SCREW.getMaterialTag(m))
                            .put('R', AntimatterMaterialTypes.RING.getMaterialTag(m))
                            .build(),
                    "PHP", "WRF", "PSP");
        });
        AntimatterMaterialTypes.PLATE.all().forEach(m -> {
            if (!m.has(NOSMASH)){
                if (m.has(AntimatterMaterialTypes.INGOT)){
                    Object[] array = GTCoreConfig.LOSSY_PART_CRAFTING.get() ? new Object[]{AntimatterDefaultTools.HAMMER.getTag(), AntimatterMaterialTypes.INGOT.getMaterialTag(m), AntimatterMaterialTypes.INGOT.getMaterialTag(m)} : new Object[]{AntimatterDefaultTools.HAMMER.getTag(), AntimatterMaterialTypes.INGOT.getMaterialTag(m)};
                    provider.shapeless(consumer, GTCore.ID, "", "antimatter_material", AntimatterMaterialTypes.PLATE.get(m, 1), array);
                }
                if (m.has(AntimatterMaterialTypes.GEAR_SMALL)) {
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_material",
                            AntimatterMaterialTypes.GEAR_SMALL.get(m, 1), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(),'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "P ", " H");
                }
                if (m.has(AntimatterMaterialTypes.ITEM_CASING)) {
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_material",
                            AntimatterMaterialTypes.ITEM_CASING.get(m, 1), ImmutableMap.of('H', AntimatterDefaultTools.HAMMER.getTag(),'P', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "H P");
                }
                if (m.has(FOIL)){
                    provider.addStackRecipe(consumer, GTCore.ID, "", "antimatter_materials",
                            FOIL.get(m, 2), of('H', HAMMER.getTag(), 'P', PLATE.getMaterialTag(m)), "HP");
                    if (m.has(WIRE_FINE)){
                        provider.addItemRecipe(consumer, GTCore.ID, "", "antimatter_materials",
                                WIRE_FINE.get(m), of('F', FOIL.getMaterialTag(m), 'W', WIRE_CUTTER.getTag()), "FW");
                    }
                }
            }
            if (m.has(AntimatterMaterialTypes.GEAR)){
                provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_gear", "antimatter_material",
                        AntimatterMaterialTypes.GEAR.get(m, 1), ImmutableMap.<Character, Object>builder()
                                .put('W', AntimatterDefaultTools.WRENCH.getTag())
                                .put('P', AntimatterMaterialTypes.PLATE.getMaterialTag(m))
                                .put('R', AntimatterMaterialTypes.ROD.getMaterialTag(m))
                                .build(),
                        "RPR", "PWP", "RPR");
            }
            if (m.has(AntimatterMaterialTypes.RING)) {
                if (m.has(RUBBERTOOLS)){
                    provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_ring", "antimatter_material",
                            AntimatterMaterialTypes.RING.get(m, craftingMultiplier), ImmutableMap.of('H', AntimatterDefaultTools.WIRE_CUTTER.getTag(), 'W', AntimatterMaterialTypes.PLATE.getMaterialTag(m)), "H ", " W");
                }
            }
        });
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in){
        addSmeltingRecipe(consumer, provider, input, output, amount, in, in);
    }

    public static void addSmeltingRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, MaterialType<?> input, MaterialTypeItem<?> output, int amount, Material in, Material out){
        AntimatterCookingRecipeBuilder.blastingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 100)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId())));
        AntimatterCookingRecipeBuilder.smeltingRecipe(RecipeIngredient.of(input.getMaterialTag(in), 1), new ItemStack(output.get(out), MaterialTags.SMELTING_MULTI.getInt(in) * amount), 2.0F, 200)
                .addCriterion("has_material_" + in.getId(), provider.hasSafeItem(output.getMaterialTag(out)))
                .build(consumer, provider.fixLoc(Ref.ID, in.getId().concat("_" + input.getId() + "_to_" + output.getId() + "_smelting")));
    }
}
