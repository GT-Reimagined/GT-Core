package org.gtreimagined.gtcore;

import carbonconfiglib.config.*;
import carbonconfiglib.config.ConfigEntry.BoolValue;
import carbonconfiglib.config.ConfigEntry.EnumValue;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import net.minecraft.world.item.Items;

public class GTCoreConfig {
    public static ConfigHandler CONFIG;
    public static BoolValue GOOD_CIRCUITS;
    public static BoolValue COMPLEX_CIRCUITS;
    public static BoolValue USE_SOLDERING_ALLOY;
    public static BoolValue ADVANCED_CIRCUIT_CRAFTING;
    public static BoolValue LOSSY_PART_CRAFTING;
    public static BoolValue HARDER_WOOD;
    public static BoolValue HARDER_LAPOTRON_CRYSTALS;
    public static BoolValue VANILLA_OVERRIDES;
    public static BoolValue DISABLE_WOOD_TOOLS;
    public static BoolValue DISABLE_CHARCOAL_SMELTING;
    public static BoolValue VILLAGER_TRADE_REPLACEMENTS;
    public static BoolValue COMPOSTER_OUTPUT_REPLACEMENT;
    public static BoolValue HONEYCOMB_REPLACEMENT;
    public static EnumValue<CircuitRecipeMode> CIRCUIT_RECIPE_MODE;

    public static void createConfig(){
        Config config = new Config(GTCore.ID);
        ConfigSection section = config.add("general");
        GOOD_CIRCUITS = section.addBool("good_circuits", AntimatterAPI.isModLoaded("gti"), "Whether good circuits are enabled. Note defaults to true when gti is loaded, and result is completely ignored when CIRCUIT_RECIPE_MODE isn't GT4 or GT5.");
        COMPLEX_CIRCUITS = section.addBool("complex_circuits", false, "Whether complex circuits are enabled. Note: Ignored when CIRCUIT_RECIPE_MODE is not GT4 or GT5");
        USE_SOLDERING_ALLOY = section.addBool("use_soldering_alloy", AntimatterAPI.isModLoaded("gti"), "Whether circuit recipes require soldering alloy. Note: defaults to true when gti is loaded");
        ADVANCED_CIRCUIT_CRAFTING = section.addBool("advanced_circuit_crafting", AntimatterAPI.isModLoaded("gt4r"), "Whether crafting table recipes for advanced circuits exist. Note: defaults to true when gt4r is loaded.");
        CircuitRecipeMode recipeMode = AntimatterAPI.isModLoaded("gti") ? CircuitRecipeMode.GT5 : AntimatterAPI.isModLoaded("gt4r") ? CircuitRecipeMode.GT4 : CircuitRecipeMode.PUP;
        CIRCUIT_RECIPE_MODE = section.addEnum("circuit_recipe_mode", recipeMode, CircuitRecipeMode.class, "Determines the various recipes for circuits.", "GT4 is circuits recipes based off gt4, GT5 is circuits recipes based off mostly GT5 but with slight modifications,", "PUP is harder circuit recipes but not crazy like gt5u, and GT%U is exactly what it sounds like: the absurdly grindy circuit recipes from GT5U 09.31 but with slight modifications.");
        LOSSY_PART_CRAFTING = section.addBool("lossy_part_crafting", true, "Enable crating recipes for things like rods and plates being lossy - Default: true",
                "Note: make sure to run /reload after changing this.");
        HARDER_WOOD = section.addBool("harder_wood", AntimatterAPI.isModLoaded("gti"),"If true logs to planks and planks to sticks give half of vanilla amounts - Default: true with gti, false otherwise");
        HARDER_LAPOTRON_CRYSTALS = section.addBool("harder_lapotron_crystals", false, "If true lapotron crystals will require assembling with raw lapotron crystals, which are made with lapotronium dust - Default: false");
        VANILLA_OVERRIDES = section.addBool("vanilla_overrides", true, "If true enables gregified recipes of some vanilla blocks and items - Default: true");
        DISABLE_WOOD_TOOLS = section.addBool("disable_wood_tools", true, "If true disables crafting recipes for wood tools(except wood shovels) and hides them in jei/rei - Default: true");
        DISABLE_CHARCOAL_SMELTING = section.addBool("disable_charcoal_smelting", true, "If true disables log to charcoal recipes in the vanilla furnace - Default: true");
        VILLAGER_TRADE_REPLACEMENTS = section.addBool("villager_trade_replacements", true, "If true replaces emeralds with gt credits in all villager trades - Default: true");
        COMPOSTER_OUTPUT_REPLACEMENT = section.addBool("composter_output_replacement", true, "If true makes the composter make fertilizer instead of bonemeal - Default: true");
        HONEYCOMB_REPLACEMENT = section.addBool("honeycomb_replacement", true, "If true honeycomb usage for making waxed copper is replaced by beeswax - Default: true");
        CONFIG = AntimatterPlatformUtils.INSTANCE.createConfig(GTCore.ID, config);
        CONFIG.register();
    }

    public enum CircuitRecipeMode{
        GT4,
        GT5,
        PUP,
        GT5U
    }
}
