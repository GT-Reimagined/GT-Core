package io.github.gregtechintergalactical.gtcore;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import io.github.gregtechintergalactical.gtcore.client.BakedModels;
import io.github.gregtechintergalactical.gtcore.data.*;
import io.github.gregtechintergalactical.gtcore.data.client.ScreenFactories;
import io.github.gregtechintergalactical.gtcore.datagen.GTCoreBlockLootProvider;
import io.github.gregtechintergalactical.gtcore.datagen.GTCoreBlockTagProvider;
import io.github.gregtechintergalactical.gtcore.datagen.GTCoreItemTagProvider;
import io.github.gregtechintergalactical.gtcore.datagen.GTCoreLang;
import io.github.gregtechintergalactical.gtcore.events.GTCommonEvents;
import io.github.gregtechintergalactical.gtcore.integration.top.MassStorageProvider;
import io.github.gregtechintergalactical.gtcore.loader.crafting.MachineRecipes;
import io.github.gregtechintergalactical.gtcore.loader.crafting.MaterialRecipes;
import io.github.gregtechintergalactical.gtcore.loader.crafting.Pipes;
import io.github.gregtechintergalactical.gtcore.loader.crafting.RubberRecipes;
import io.github.gregtechintergalactical.gtcore.loader.crafting.Tools;
import io.github.gregtechintergalactical.gtcore.loader.crafting.VanillaRecipes;
import io.github.gregtechintergalactical.gtcore.loader.machines.AssemblyLoader;
import io.github.gregtechintergalactical.gtcore.network.MessageCraftingSync;
import io.github.gregtechintergalactical.gtcore.network.MessageInventorySync;
import io.github.gregtechintergalactical.gtcore.network.MessageTriggerInventorySync;
import io.github.gregtechintergalactical.gtcore.proxy.CommonHandler;
import io.github.gregtechintergalactical.gtcore.tree.RubberTree;
import io.github.gregtechintergalactical.gtcore.tree.RubberTreeWorldGen;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterMod;
import muramasa.antimatter.Ref;
import muramasa.antimatter.common.event.CommonEvents;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.datagen.AntimatterDynamics;
import muramasa.antimatter.datagen.builder.AntimatterTagBuilder;
import muramasa.antimatter.datagen.providers.*;
import muramasa.antimatter.event.CraftingEvent;
import muramasa.antimatter.event.MaterialEvent;
import muramasa.antimatter.event.ProvidersEvent;
import muramasa.antimatter.integration.jeirei.AntimatterJEIREIPlugin;
import muramasa.antimatter.network.AntimatterNetwork;
import muramasa.antimatter.pipe.BlockFluidPipe;
import muramasa.antimatter.recipe.loader.IRecipeRegistrate;
import muramasa.antimatter.registration.IAntimatterRegistrar;
import muramasa.antimatter.registration.RegistrationEvent;
import muramasa.antimatter.registration.Side;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.*;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.Steeleaf;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.material.MaterialTags.RUBBERTOOLS;
import static muramasa.antimatter.material.MaterialTags.WOOD;

public class GTCore extends AntimatterMod {

    public static final Logger LOGGER = LogManager.getLogger(); // Directly reference a log4j logger.
    public static final String ID = "gtcore", NAME = "GT Core";
    public static final ResourceLocation SYNC_ID = new ResourceLocation(GTCore.ID, "crafting_sync");
    public static final ResourceLocation INV_SYNC_ID = new ResourceLocation(GTCore.ID, "inventory_sync");
    public static final ResourceLocation TRIGGER_SYNC_ID = new ResourceLocation(GTCore.ID, "trigger_sync");

    @Override
    public void onRegistrarInit() {
        super.onRegistrarInit();
        AntimatterDynamics.clientProvider(ID, () -> new AntimatterBlockStateProvider(ID, NAME + " BlockStates"));
        AntimatterDynamics.clientProvider(ID, () -> new AntimatterItemModelProvider(ID, NAME + " Item Models"));
        AntimatterDynamics.clientProvider(ID, GTCoreLang.en_US::new);
        GTCoreConfig.createConfig();
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Side side) {
        switch (event) {
            case DATA_INIT -> {
                SlotTypes.init();
                MenuHandlers.init();
                GTCoreData.init();
                GTCoreCovers.init();
                GTCoreBlocks.init();
                GTCoreItems.init();
                GTCoreFluids.init();
                GTCoreMaterials.init();
                GTCoreCables.init();
                GTCoreTools.init(side);
                RecipeMaps.init();
                if (side.isClient()) RecipeMaps.clientMaps();
                RubberTree.init();
                RubberTreeWorldGen.init();
                if (AntimatterAPI.isModLoaded(Ref.MOD_TOP)){
                    MassStorageProvider.createTopProvider();
                }
                CommonEvents.addPlayerTickCallback(GTCommonEvents::onPlayerTick);
                AntimatterNetwork.NETWORK.registerPacket(NetworkDirection.CLIENT_TO_SERVER, SYNC_ID, MessageCraftingSync.HANDLER, MessageCraftingSync.class);
                AntimatterNetwork.NETWORK.registerPacket(NetworkDirection.SERVER_TO_CLIENT, INV_SYNC_ID, MessageInventorySync.HANDLER, MessageInventorySync.class);
                AntimatterNetwork.NETWORK.registerPacket(NetworkDirection.CLIENT_TO_SERVER, TRIGGER_SYNC_ID, MessageTriggerInventorySync.HANDLER, MessageTriggerInventorySync.class);
            }
            case DATA_READY -> {
                WoodType.register(GTCoreBlocks.RUBBER_WOOD_TYPE);
                GTCoreRemapping.init();
                CommonHandler.setup();
                AntimatterJEIREIPlugin.addItemsToHide(l -> {
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_lv").getItem());
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_mv").getItem());
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_hv").getItem());
                    if (GTCoreConfig.DISABLE_WOOD_TOOLS.get()){
                        l.addAll(Arrays.asList(Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_PICKAXE, Items.WOODEN_SWORD));
                    }
                    if (!GTCoreConfig.VILLAGER_TRADE_REPLACEMENTS.get()){
                        l.add(GTCoreItems.GTCredit);
                    }
                    AntimatterAPI.all(IAntimatterTool.class).stream().filter(t -> {
                        var toolType = t.getAntimatterToolType();
                        return toolType == GTCoreTools.POCKET_MULTITOOL_SCISSORS
                                || toolType == GTCoreTools.POCKET_MULTITOOL_FILE
                                || toolType == GTCoreTools.POCKET_MULTITOOL_KNIFE
                                || toolType == GTCoreTools.POCKET_MULTITOOL_SAW
                                || toolType == GTCoreTools.POCKET_MULTITOOL_SCREWDRIVER
                                || toolType == GTCoreTools.POCKET_MULTITOOL_WIRE_CUTTER;
                    }).forEach(tool -> l.add(tool.getItem()));
                });
            }
            case CLIENT_DATA_INIT -> {
                BakedModels.init();
                ScreenFactories.init();
            }
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        event.setMaterial(GTCoreMaterials.Rubber).asSolid(295, PLATE, RING);
        event.setMaterial(Plastic).flags(RUBBERTOOLS);
        event.setMaterial(Beeswax).asDust();
        event.setMaterial(GTCoreMaterials.FierySteel).asMetal().tool().toolDamage(4).toolSpeed(9).toolDurability(1024).toolQuality(4)
                .toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 2)).handleMaterial(AntimatterMaterials.Blaze)
                .blacklistToolTypes(PICKAXE, SWORD).build();
        event.setMaterial(GTCoreMaterials.Knightmetal).asMetal(GEAR).tool().toolDamage(3).toolSpeed(8).toolDurability(512).toolQuality(3)
                .blacklistToolTypes(AXE, PICKAXE, SWORD).build();
        event.setMaterial(Ironwood).asMetal(WOOD).tool().toolDamage(2).toolSpeed(6.5f).toolDurability(512).toolQuality(2)
                .toolEnchantments(ImmutableMap.of(Enchantments.KNOCKBACK, 1))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        event.setMaterial(GTCoreMaterials.Steeleaf).asMetal().tool().toolDamage(4).toolSpeed(8).toolDurability(131).toolQuality(3)
                .toolEnchantments(ImmutableMap.of(Enchantments.MOB_LOOTING, 2, Enchantments.BLOCK_FORTUNE, 2))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        if (AntimatterAPI.isModLoaded("twilightforest")){
            INGOT.replacement(Ironwood, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "ironwood_ingot"));
            BLOCK.replacement(Ironwood, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "ironwood_block"));
            INGOT.replacement(Knightmetal, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "knightmetal_ingot"));
            BLOCK.replacement(Knightmetal, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "knightmetal_block"));
            INGOT.replacement(Steeleaf, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "steeleaf_ingot"));
            BLOCK.replacement(Steeleaf, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "steeleaf_block"));
            INGOT.replacement(FierySteel, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "fiery_ingot"));
            BLOCK.replacement(FierySteel, () -> AntimatterPlatformUtils.getItemFromID("twilightforest", "fiery_block"));
        }
        GTCoreBlocks.initItemBarrels();
        Guis.init();
    }

    public static void onCrafting(CraftingEvent event){
        //event.addLoader(CircuitRecipes::initRecipes);
        event.addLoader(MachineRecipes::initRecipes);
        event.addLoader(RubberRecipes::addRecipes);
        event.addLoader(MaterialRecipes::loadMaterialRecipes);
        event.addLoader(Pipes::loadRecipes);
        event.addLoader(Tools::init);
        event.addLoader(VanillaRecipes::loadRecipes);
    }

    public static void registerRecipeLoaders(IAntimatterRegistrar registrar, IRecipeRegistrate reg) {
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> reg.add(GTCore.ID, a, b);
        loader.accept("assembling", AssemblyLoader::init);
    }

    public static void onProviders(ProvidersEvent ev) {
        final AntimatterBlockTagProvider[] p = new AntimatterBlockTagProvider[1];
        ev.addProvider(ID, () -> {
            p[0] = new GTCoreBlockTagProvider(ID, NAME.concat(" Block Tags"), false);
            return p[0];
        });
        ev.addProvider(ID, () -> new GTCoreItemTagProvider(ID, NAME.concat(" Item Tags"), false, p[0]));

        ev.addProvider(ID, () -> new GTCoreBlockLootProvider(ID, NAME.concat(" Loot generator")));
        ev.addProvider(ID, () -> new AntimatterTagProvider<>(BuiltinRegistries.BIOME, ID, NAME.concat(" Biome Tags"), "worldgen/biome") {
            @Override
            protected void processTags(String domain) {
                AntimatterTagBuilder<Biome> tags = this.tag(TagUtils.getBiomeTag(new ResourceLocation(ID, "is_invalid_rubber"))).addTag(BiomeTags.IS_TAIGA).addTag(BiomeTags.IS_MOUNTAIN).addTag(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_DEEP_OCEAN).addTag(BiomeTags.IS_NETHER).addTag(TagUtils.getBiomeTag(new ResourceLocation("is_desert"))).addTag(TagUtils.getBiomeTag(new ResourceLocation("is_plains")));
                boolean forge = AntimatterPlatformUtils.isForge();
                String d = forge ? "forge" : "c";
                String end = forge ? "is_end" : "in_the_end";
                tags.addTag(TagUtils.getBiomeTag(new ResourceLocation(d, end)));
                tags.addTag(TagUtils.getBiomeTag(new ResourceLocation(d, forge ? "is_snowy" : "snowy")));
            }
        });
        ev.addProvider(ID, () -> new AntimatterTagProvider<>(BuiltinRegistries.CONFIGURED_FEATURE, ID, NAME.concat(" Configured Feature Tags"), "worldgen/configured_feature") {
            @Override
            protected void processTags(String domain) {
                if (AntimatterAPI.isModLoaded("tfc")){
                    this.tag(TagKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation("tfc", "forest_trees"))).add(ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(GTCore.ID, "tree/rubber_entry")));
                }
            }
        });
        ev.addProvider(ID, () -> new AntimatterWorldgenProvider(ID, NAME.concat(" Configured Features"), "configured_feature"){
            @Override
            public void run() {
                if (!AntimatterAPI.isModLoaded("tfc")) return;
                JsonObject object = new JsonObject();
                object.addProperty("type", "tfc:random_tree");
                JsonObject config = new JsonObject();
                JsonArray structures = new JsonArray();
                structures.add("gtcore:rubber_dead/1");
                structures.add("gtcore:rubber_dead/2");
                structures.add("gtcore:rubber_dead/3");
                structures.add("gtcore:rubber_dead/4");
                config.add("structures", structures);
                config.addProperty("radius", 1);
                JsonObject placement = new JsonObject();
                placement.addProperty("width", 1);
                placement.addProperty("height", 9);
                placement.addProperty("allow_submerged", true);
                placement.addProperty("allow_deeply_submerged", false);
                config.add("placement", placement);
                object.add("config", config);
                addJsonObject(new ResourceLocation(ID, "tree/rubber_dead"), object);

                object = new JsonObject();
                object.addProperty("type", "tfc:forest_entry");
                config = new JsonObject();
                config.addProperty("min_rain", 250);
                config.addProperty("max_rain", 400);
                config.addProperty("min_temp", 15.0);
                config.addProperty("max_temp", 40.0);
                JsonArray groundCover = new JsonArray();
                JsonObject block = new JsonObject();
                block.addProperty("block", "gtcore:rubber_twig");
                groundCover.add(block);
                block = new JsonObject();
                block.addProperty("block", "gtcore:rubber_fallen_leaves");
                groundCover.add(block);
                config.add("groundcover", groundCover);
                config.addProperty("normal_tree", "gtcore:rubber_tree_normal");
                config.addProperty("dead_tree", "gtcore:tree/rubber_dead");
                config.addProperty("fallen_log", "gtcore:rubber_log");
                object.add("config", config);
                addJsonObject(new ResourceLocation(ID, "tree/rubber_entry"), object);

            }
        });
    }
}
