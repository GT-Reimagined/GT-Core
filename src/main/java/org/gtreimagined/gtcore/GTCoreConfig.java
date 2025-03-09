package org.gtreimagined.gtcore;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry.BoolValue;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;

public class GTCoreConfig {
    public static ConfigHandler CONFIG;
    public static BoolValue LOSSY_PART_CRAFTING;
    public static BoolValue HARDER_WOOD;
    public static BoolValue HARDER_LAPOTRON_CRYSTALS;
    public static BoolValue VANILLA_OVERRIDES;
    public static BoolValue DISABLE_WOOD_TOOLS;
    public static BoolValue DISABLE_CHARCOAL_SMELTING;
    public static BoolValue VILLAGER_TRADE_REPLACEMENTS;
    public static BoolValue COMPOSTER_OUTPUT_REPLACEMENT;
    public static BoolValue HONEYCOMB_REPLACEMENT;
    public static BoolValue DISABLE_BLOCK_CRAFTING;

    public static void createConfig(){
        Config config = new Config(GTCore.ID);
        ConfigSection section = config.add("general");
        LOSSY_PART_CRAFTING = section.addBool("lossy_part_crafting", true, "Enable crating recipes for things like rods and plates being lossy - Default: true",
                "Note: make sure to run /reload after changing this.");
        HARDER_WOOD = section.addBool("harder_wood", true,"If true logs to planks and planks to sticks give half of vanilla amounts - Default: true");
        HARDER_LAPOTRON_CRYSTALS = section.addBool("harder_lapotron_crystals", false, "If true lapotron crystals will require assembling with raw lapotron crystals, which are made with lapotronium dust - Default: false");
        VANILLA_OVERRIDES = section.addBool("vanilla_overrides", true, "If true enables gregified recipes of some vanilla blocks and items - Default: true");
        DISABLE_WOOD_TOOLS = section.addBool("disable_wood_tools", true, "If true disables crafting recipes for wood tools(except wood shovels) and hides them in jei/rei - Default: true");
        DISABLE_CHARCOAL_SMELTING = section.addBool("disable_charcoal_smelting", true, "If true disables log to charcoal recipes in the vanilla furnace - Default: true");
        VILLAGER_TRADE_REPLACEMENTS = section.addBool("villager_trade_replacements", true, "If true replaces emeralds with gt credits in all villager trades - Default: true");
        COMPOSTER_OUTPUT_REPLACEMENT = section.addBool("composter_output_replacement", true, "If true makes the composter make fertilizer instead of bonemeal - Default: true");
        HONEYCOMB_REPLACEMENT = section.addBool("honeycomb_replacement", true, "If true honeycomb usage for making waxed copper is replaced by beeswax - Default: true");
        DISABLE_BLOCK_CRAFTING = section.addBool("disable_block_crafting", true, "Disables crafting of material blocks in the crafting table, eg. Block of Iron");
        CONFIG = CarbonConfig.CONFIGS.createConfig(config);
        CONFIG.register();
    }

}
