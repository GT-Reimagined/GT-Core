package io.github.gregtechintergalactical.gtcore.loader.crafting;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.GTCoreConfig;
import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import io.github.gregtechintergalactical.gtcore.data.GTCoreTags;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.Beeswax;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;

public class VanillaRecipes {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider) {
        provider.addStackRecipe(consumer, GTCore.ID, "lead_from_resin", "", new ItemStack(Items.LEAD, 2), of('S', Items.STRING, 'R', GTCoreItems.StickyResin), "SS ", "SR ", "  S");
        if (!AntimatterAPI.isModLoaded(Ref.MOD_TFC)) {
            provider.shapeless(consumer, "gravel_to_flint", "mortar_recipes", new ItemStack(Items.FLINT), AntimatterDefaultTools.MORTAR.getTag(), Items.GRAVEL);
        }

        provider.addItemRecipe(consumer, GTCore.ID, "piston_sticky","gears", Blocks.STICKY_PISTON, of('S', GTCoreItems.StickyResin, 'P', Blocks.PISTON), "S", "P");
        loadOverrides(consumer, provider);
        loadWood(consumer, provider);
    }

    private static void loadOverrides(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider) {
        if (GTCoreConfig.DISABLE_WOOD_TOOLS.get()){
            provider.removeRecipe(new ResourceLocation("wooden_axe"));
            provider.removeRecipe(new ResourceLocation("wooden_pickaxe"));
            provider.removeRecipe(new ResourceLocation("wooden_hoe"));
            provider.removeRecipe(new ResourceLocation("wooden_sword"));
        }
        if (GTCoreConfig.DISABLE_CHARCOAL_SMELTING.get()){
            provider.removeRecipe(new ResourceLocation("charcoal"));
            provider.removeRecipe(new ResourceLocation("energizedpower", "smelting/charcoal_from_smelting_sawdust_block"));
        }
        if (GTCoreConfig.HONEYCOMB_REPLACEMENT.get()){
            for (WeatheringCopper.WeatherState weatherState : WeatheringCopper.WeatherState.values()) {
                String prefix = weatherState == WeatheringCopper.WeatherState.UNAFFECTED ? "" : weatherState.name().toLowerCase() + "_";
                if (prefix.isEmpty()){
                    addBeeswaxRecipe(consumer, provider, "copper_block");
                } else {
                    addBeeswaxRecipe(consumer, provider, prefix + "copper");
                }
                addBeeswaxRecipe(consumer, provider, prefix + "cut_copper");
                addBeeswaxRecipe(consumer, provider, prefix + "cut_copper_stairs");
                addBeeswaxRecipe(consumer, provider, prefix + "cut_copper_slab");
            }
        }
        if (!GTCoreConfig.VANILLA_OVERRIDES.get()) return;
        provider.addStackRecipe(consumer, "minecraft", "", "misc", new ItemStack(Items.IRON_BARS, 8), of('R', ROD.getMaterialTag(Iron)), "RRR", "RRR");
        provider.addItemRecipe(consumer, "minecraft", "", "misc",
                Items.BUCKET, of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "IHI", " I ");
        provider.addItemRecipe(consumer, "minecraft", "", "misc",
                Items.HOPPER, of('I', PLATE.getMaterialTag(Iron), 'W', WRENCH.getTag(), 'C', ForgeCTags.CHESTS), "IWI", "ICI", " I ");
        provider.addStackRecipe(consumer, "minecraft", "", "cauldrons", new ItemStack(Items.CAULDRON),
                of('P', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "P P", "PHP", "PPP");
        provider.addStackRecipe(consumer, "minecraft", "", "misc", new ItemStack(Items.IRON_DOOR, 3),
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "II ", "IIH", "II ");
        provider.addStackRecipe(consumer, "minecraft", "", "misc", new ItemStack(Items.IRON_TRAPDOOR),
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "II ", "IIH");
        provider.addStackRecipe(consumer, "minecraft", "", "misc", new ItemStack(Items.LIGHT_WEIGHTED_PRESSURE_PLATE),
                of('I', PLATE.getMaterialTag(Gold), 'H', HAMMER.getTag()), "IIH");
        provider.addStackRecipe(consumer, "minecraft", "", "misc", new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE),
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "IIH");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.IRON_AXE,
                of('P', PLATE.getMaterialTag(Iron), 'I', INGOT.getMaterialTag(Iron), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PIH", "PR ", "FR ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.IRON_PICKAXE,
                of('P', PLATE.getMaterialTag(Iron), 'I', INGOT.getMaterialTag(Iron), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PII", "FRH", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.IRON_SWORD,
                of('P', PLATE.getMaterialTag(Iron), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "FPH", " P ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.IRON_SHOVEL,
                of('P', PLATE.getMaterialTag(Iron), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "FPH", " R ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.IRON_HOE,
                of('P', PLATE.getMaterialTag(Iron), 'I', INGOT.getMaterialTag(Iron), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PIH", "FR ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.GOLDEN_AXE,
                of('P', PLATE.getMaterialTag(Gold), 'I', INGOT.getMaterialTag(Gold), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PIH", "PR ", "FR ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.GOLDEN_PICKAXE,
                of('P', PLATE.getMaterialTag(Gold), 'I', INGOT.getMaterialTag(Gold), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PII", "FRH", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.GOLDEN_SWORD,
                of('P', PLATE.getMaterialTag(Gold), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "FPH", " P ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.GOLDEN_SHOVEL,
                of('P', PLATE.getMaterialTag(Gold), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "FPH", " R ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.GOLDEN_HOE,
                of('P', PLATE.getMaterialTag(Gold), 'I', INGOT.getMaterialTag(Gold), 'F', FILE.getTag(), 'H', HAMMER.getTag(), 'R', ROD.getMaterialTag(Wood)), "PIH", "FR ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.DIAMOND_AXE,
                of('G', GEM.getMaterialTag(Diamond), 'F', FILE.getTag(), 'R', ROD.getMaterialTag(Wood)), "GG", "GR", "FR");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.DIAMOND_PICKAXE,
                of('G', GEM.getMaterialTag(Diamond), 'F', FILE.getTag(), 'R', ROD.getMaterialTag(Wood)), "GGG", "FR ", " R ");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.DIAMOND_SWORD,
                of('G', GEM.getMaterialTag(Diamond), 'F', FILE.getTag(), 'R', ROD.getMaterialTag(Wood)), "FG", " G", " R");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.DIAMOND_SHOVEL,
                of('G', GEM.getMaterialTag(Diamond), 'F', FILE.getTag(), 'R', ROD.getMaterialTag(Wood)), "FG", " R", " R");
        provider.addItemRecipe(consumer, "vanilla_tools", Items.DIAMOND_HOE,
                of('G', GEM.getMaterialTag(Diamond), 'F', FILE.getTag(), 'R', ROD.getMaterialTag(Wood)), "GG", "FR", " R");

        provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_HELMET,
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "III", "IHI");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_CHESTPLATE,
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "IHI", "III", "III");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_LEGGINGS,
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "III", "IHI", "I I");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.IRON_BOOTS,
                of('I', PLATE.getMaterialTag(Iron), 'H', HAMMER.getTag()), "I I", "IHI");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_HELMET,
                of('I', PLATE.getMaterialTag(Gold), 'H', HAMMER.getTag()), "III", "IHI");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_CHESTPLATE,
                of('I', PLATE.getMaterialTag(Gold), 'H', HAMMER.getTag()), "IHI", "III", "III");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_LEGGINGS,
                of('I', PLATE.getMaterialTag(Gold), 'H', HAMMER.getTag()), "III", "IHI", "I I");
        provider.addItemRecipe(consumer, "vanilla_armor", Items.GOLDEN_BOOTS,
                of('I', PLATE.getMaterialTag(Gold), 'H', HAMMER.getTag()), "I I", "IHI");
    }

    static void addWoodRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, String domain, TagKey<Item> log, Item plank){
        int amount1 = GTCoreConfig.HARDER_WOOD.get() ? 2 : 4;
        int amount2 = GTCoreConfig.HARDER_WOOD.get() ? 4 : 6;
        provider.shapeless(consumer, domain, "", "planks", new ItemStack(plank, amount1), log);
        provider.addStackRecipe(consumer, domain, AntimatterPlatformUtils.getIdFromItem(plank).getPath() + "_" + amount2, "planks", new ItemStack(plank, amount2), of('S', SAW.getTag(), 'P', log), "S", "P");
    }

    static void addBeeswaxRecipe(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider, String id){
        provider.shapeless(consumer, "minecraft", "waxed_" + id + "_from_honeycomb", "waxed_blocks", new ItemStack(AntimatterPlatformUtils.getItemFromID(new ResourceLocation("waxed_" + id))),
                AntimatterPlatformUtils.getItemFromID(new ResourceLocation(id)), DUST.getMaterialTag(Beeswax));
    }

    private static void loadWood(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider) {
        if (GTCoreConfig.HARDER_WOOD.get()){
            provider.addStackRecipe(consumer, "minecraft", "", "wood_stuff", new ItemStack(Items.STICK, 2), of('P', ItemTags.PLANKS), "P", "P");
            provider.addStackRecipe(consumer, GTCore.ID, "sticks_4", "wood_stuff", new ItemStack(Items.STICK, 4), of('P', ItemTags.PLANKS, 'S', SAW.getTag()), "S", "P", "P");

        }
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.OAK_LOGS, Items.OAK_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.BIRCH_LOGS, Items.BIRCH_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.SPRUCE_LOGS, Items.SPRUCE_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.JUNGLE_LOGS, Items.JUNGLE_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.ACACIA_LOGS, Items.ACACIA_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.DARK_OAK_LOGS, Items.DARK_OAK_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.CRIMSON_STEMS, Items.CRIMSON_PLANKS);
        addWoodRecipe(consumer, provider, "minecraft", ItemTags.WARPED_STEMS, Items.WARPED_PLANKS);

        String[] stones = {"stone", "smooth_stone", "sandstone", "cut_sandstone", "cobblestone", "red_sandstone", "cut_red_sandstone", "prismarine", "dark_prismarine", "polished_granite", "smooth_red_sandstone", "polished_diorite", "mossy_cobblestone", "smooth_sandstone", "smooth_quartz", "granite", "andesite", "polished_andesite", "diorite", "blackstone", "polished_blackstone", "purpur", "quartz", "brick", "stone_brick", "nether_brick", "prismarine_brick", "mossy_stone_brick", "end_stone_brick", "red_nether_brick", "polished_blackstone_brick"};
        for (String stone : stones) {
            Item full = AntimatterPlatformUtils.getItemFromID(new ResourceLocation(stone + (stone.equals("purpur") || stone.equals("quartz") ? "_block" : stone.contains("brick") ? "s" : "")));
            Item slab = AntimatterPlatformUtils.getItemFromID(new ResourceLocation(stone + "_slab"));
            String[] pattern = stone.equals("purpur") || stone.equals("quartz") || stone.equals("sandstone") || stone.equals("red_sandstone") || stone.equals("stone_brick") || stone.equals("nether_brick") || stone.equals("polished_blackstone") ? new String[]{"SS"} : new String[]{"S", "S"};
            provider.addItemRecipe(consumer, GTCore.ID, stone + "_slab_to_" + stone, "slabs", full, of('S', slab), pattern);
        }
        String[] wood = {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "crimson", "warped"};
        for (String s : wood) {
            ResourceLocation name = new ResourceLocation(s + "_planks");
            ResourceLocation slab = new ResourceLocation(s + "_slab");
            provider.addItemRecipe(consumer, GTCore.ID, slab.getPath() + "_to_" + name.getPath(), "slabs", AntimatterPlatformUtils.getItemFromID(name), of('S', AntimatterPlatformUtils.getItemFromID(slab)), "S", "S");
        }
    }
}
