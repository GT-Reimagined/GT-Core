package org.gtreimagined.gtcore;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import muramasa.antimatter.event.forge.AntimatterCraftingEvent;
import muramasa.antimatter.event.forge.AntimatterLoaderEvent;
import muramasa.antimatter.event.forge.AntimatterProvidersEvent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gtcore.client.BakedModels;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.gtreimagined.gtcore.data.GTCoreData;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreTools;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.data.client.ScreenFactories;
import org.gtreimagined.gtcore.datagen.GTCoreBlockLootProvider;
import org.gtreimagined.gtcore.datagen.GTCoreBlockTagProvider;
import org.gtreimagined.gtcore.datagen.GTCoreItemTagProvider;
import org.gtreimagined.gtcore.datagen.GTCoreLang;
import org.gtreimagined.gtcore.events.GTCommonEvents;
import org.gtreimagined.gtcore.integration.curio.CurioPlugin;
import org.gtreimagined.gtcore.integration.top.MassStorageProvider;
import org.gtreimagined.gtcore.integration.top.RedstoneWireProvider;
import org.gtreimagined.gtcore.loader.crafting.MachineRecipes;
import org.gtreimagined.gtcore.loader.crafting.MaterialRecipes;
import org.gtreimagined.gtcore.loader.crafting.MiscRecipes;
import org.gtreimagined.gtcore.loader.crafting.Pipes;
import org.gtreimagined.gtcore.loader.crafting.RubberRecipes;
import org.gtreimagined.gtcore.loader.crafting.Tools;
import org.gtreimagined.gtcore.loader.crafting.VanillaRecipes;
import org.gtreimagined.gtcore.loader.machines.AssemblyLoader;
import org.gtreimagined.gtcore.network.MessageCraftingSync;
import org.gtreimagined.gtcore.network.MessageInventorySync;
import org.gtreimagined.gtcore.network.MessageTriggerInventorySync;
import org.gtreimagined.gtcore.proxy.ClientHandler;
import org.gtreimagined.gtcore.proxy.CommonHandler;
import org.gtreimagined.gtcore.tree.RubberFoliagePlacer;
import org.gtreimagined.gtcore.tree.RubberTree;
import org.gtreimagined.gtcore.tree.RubberTreeWorldGen;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterMod;
import muramasa.antimatter.Ref;
import muramasa.antimatter.common.event.CommonEvents;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.datagen.AntimatterDynamics;
import muramasa.antimatter.datagen.builder.AntimatterTagBuilder;
import muramasa.antimatter.datagen.providers.*;
import muramasa.antimatter.event.MaterialEvent;
import muramasa.antimatter.integration.jeirei.AntimatterJEIREIPlugin;
import muramasa.antimatter.network.AntimatterNetwork;
import muramasa.antimatter.recipe.loader.IRecipeRegistrate;
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
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreCovers;
import org.gtreimagined.gtcore.data.GTCoreFluids;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtcore.data.Guis;
import org.gtreimagined.gtcore.data.MenuHandlers;
import org.gtreimagined.gtcore.data.RecipeMaps;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.material.MaterialTags.RUBBERTOOLS;
import static muramasa.antimatter.material.MaterialTags.WOOD;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;

@Mod(GTCore.ID)
public class GTCore extends AntimatterMod {

    public static final Logger LOGGER = LogManager.getLogger(); // Directly reference a log4j logger.
    public static final String ID = "gtcore", NAME = "GT Core";
    public static final ResourceLocation SYNC_ID = new ResourceLocation(GTCore.ID, "crafting_sync");
    public static final ResourceLocation INV_SYNC_ID = new ResourceLocation(GTCore.ID, "inventory_sync");
    public static final ResourceLocation TRIGGER_SYNC_ID = new ResourceLocation(GTCore.ID, "trigger_sync");

    public GTCore(){
        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onProvidersEvent);
        eventBus.addListener(this::onCraftingEvent);
        MinecraftForge.EVENT_BUS.addListener(GTCore::registerRecipeLoaders);
        MinecraftForge.EVENT_BUS.addListener(this::onChunkWatch);
        MinecraftForge.EVENT_BUS.addListener(this::onItemUse);
        eventBus.addGenericListener(FoliagePlacerType.class, this::onRegistration);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(this::clientSetup);
            eventBus.addListener(this::onStitch);
            TerraformBoatClientHelper.registerModelLayer(new ResourceLocation(GTCore.ID, "rubber"));
        });
        if (AntimatterAPI.isModLoaded("curios")) eventBus.addListener(CurioPlugin::loadIMC);
        AntimatterDynamics.clientProvider(ID, () -> new AntimatterBlockStateProvider(ID, NAME + " BlockStates"));
        AntimatterDynamics.clientProvider(ID, () -> new AntimatterItemModelProvider(ID, NAME + " Item Models"));
        AntimatterDynamics.clientProvider(ID, GTCoreLang.en_US::new);
        GTCoreConfig.createConfig();
    }

    private void onChunkWatch(ChunkWatchEvent.Watch event){
        /*event.getWorld().getChunk(event.getPos().x, event.getPos().z).getBlockEntities().values().forEach(b -> {
            if (b instanceof BlockEntityMassStorage storage && storage.isServerSide() && !storage.isRemoved()){
                storage.setSyncSlots(true);
            }
        });*/
    }

    private void onItemUse(PlayerInteractEvent.RightClickBlock event){
        if (event.getPlayer().getItemInHand(event.getHand()).is(DUST.getMaterialTag(Beeswax))){
            event.setCancellationResult(Items.HONEYCOMB.useOn(new UseOnContext(event.getPlayer(), event.getHand(), event.getHitVec())));
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event){
        ClientHandler.init();
    }

    @OnlyIn(Dist.CLIENT)
    private void onStitch(TextureStitchEvent.Pre event) {
        ClientHandler.onStitch(event.getAtlas(), event::addSprite);
    }

    private void onProvidersEvent(AntimatterProvidersEvent event){
        final AntimatterBlockTagProvider[] p = new AntimatterBlockTagProvider[1];
        event.addProvider(ID, () -> {
            p[0] = new GTCoreBlockTagProvider(ID, NAME.concat(" Block Tags"), false);
            return p[0];
        });
        event.addProvider(ID, () -> new GTCoreItemTagProvider(ID, NAME.concat(" Item Tags"), false, p[0]));

        event.addProvider(ID, () -> new GTCoreBlockLootProvider(ID, NAME.concat(" Loot generator")));
        event.addProvider(ID, () -> new AntimatterTagProvider<>(BuiltinRegistries.BIOME, ID, NAME.concat(" Biome Tags"), "worldgen/biome") {
            @Override
            protected void processTags(String domain) {
                AntimatterTagBuilder<Biome> tags = this.tag(TagUtils.getBiomeTag(new ResourceLocation(ID, "is_invalid_rubber"))).addTag(BiomeTags.IS_TAIGA).addTag(BiomeTags.IS_MOUNTAIN).addTag(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_DEEP_OCEAN).addTag(BiomeTags.IS_NETHER).addTag(TagUtils.getBiomeTag(new ResourceLocation("is_desert"))).addTag(TagUtils.getBiomeTag(new ResourceLocation("is_plains")));
                boolean forge = AntimatterPlatformUtils.INSTANCE.isForge();
                String d = forge ? "forge" : "c";
                String end = forge ? "is_end" : "in_the_end";
                tags.addTag(TagUtils.getBiomeTag(new ResourceLocation(d, end)));
                tags.addTag(TagUtils.getBiomeTag(new ResourceLocation(d, forge ? "is_snowy" : "snowy")));
            }
        });
        event.addProvider(ID, () -> new AntimatterTagProvider<>(BuiltinRegistries.CONFIGURED_FEATURE, ID, NAME.concat(" Configured Feature Tags"), "worldgen/configured_feature") {
            @Override
            protected void processTags(String domain) {
                if (AntimatterAPI.isModLoaded("tfc")){
                    this.tag(TagKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation("tfc", "forest_trees"))).add(ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(GTCore.ID, "tree/rubber_entry")));
                }
            }
        });
        event.addProvider(ID, () -> new AntimatterWorldgenProvider(ID, NAME.concat(" Configured Features"), "configured_feature"){
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

    private void onCraftingEvent(AntimatterCraftingEvent event){
        event.addLoader(MachineRecipes::initRecipes);
        event.addLoader(RubberRecipes::addRecipes);
        event.addLoader(MaterialRecipes::loadMaterialRecipes);
        event.addLoader(Pipes::loadRecipes);
        event.addLoader(Tools::init);
        event.addLoader(VanillaRecipes::loadRecipes);
        event.addLoader(MiscRecipes::loadRecipes);
    }

    public static void registerRecipeLoaders(AntimatterLoaderEvent event) {
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> event.registrat.add(GTCore.ID, a, b);
        loader.accept("assembling", AssemblyLoader::init);
    }

    private void onRegistration(final RegistryEvent.Register<FoliagePlacerType<?>> e){
        e.getRegistry().register(RubberFoliagePlacer.RUBBER.setRegistryName(GTCore.ID, "rubber_foilage_placer"));
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
                    RedstoneWireProvider.createTopProvider();
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
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_lv", GTCore.ID).getItem());
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_mv", GTCore.ID).getItem());
                    l.add(AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_hv", GTCore.ID).getItem());
                    if (GTCoreConfig.DISABLE_WOOD_TOOLS.get()){
                        l.addAll(Arrays.asList(Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_PICKAXE, Items.WOODEN_SWORD));
                    }
                    if (!GTCoreConfig.VILLAGER_TRADE_REPLACEMENTS.get()){
                        l.add(GTCoreItems.GTCredit);
                    }
                    for (int i = 1; i < 25; i++) {
                        l.add(GTCoreItems.SELECTOR_TAG_ITEMS.get(i));
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
        event.setMaterial(GTCoreMaterials.Plastic).flags(RUBBERTOOLS);
        event.setMaterial(GTCoreMaterials.Beeswax).asDust();
        event.setMaterial(GTCoreMaterials.FierySteel).asMetal().tool().toolDamage(4).toolSpeed(9).toolDurability(1024).toolQuality(4)
                .toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 2)).handleMaterial(AntimatterMaterials.Blaze)
                .blacklistToolTypes(PICKAXE, SWORD).build();
        event.setMaterial(GTCoreMaterials.Knightmetal).asMetal().tool().toolDamage(3).toolSpeed(8).toolDurability(512).toolQuality(3)
                .blacklistToolTypes(AXE, PICKAXE, SWORD).build();
        event.setMaterial(GTCoreMaterials.Ironwood).asMetal(WOOD).tool().toolDamage(2).toolSpeed(6.5f).toolDurability(512).toolQuality(2)
                .toolEnchantments(ImmutableMap.of(Enchantments.KNOCKBACK, 1))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        event.setMaterial(GTCoreMaterials.Steeleaf).asMetal().tool().toolDamage(4).toolSpeed(8).toolDurability(131).toolQuality(3)
                .toolEnchantments(ImmutableMap.of(Enchantments.MOB_LOOTING, 2, Enchantments.BLOCK_FORTUNE, 2))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        if (AntimatterAPI.isModLoaded("twilightforest")){
            INGOT.replacement(GTCoreMaterials.Ironwood, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "ironwood_ingot"));
            BLOCK.replacement(GTCoreMaterials.Ironwood, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "ironwood_block"));
            INGOT.replacement(GTCoreMaterials.Knightmetal, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "knightmetal_ingot"));
            BLOCK.replacement(GTCoreMaterials.Knightmetal, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "knightmetal_block"));
            INGOT.replacement(GTCoreMaterials.Steeleaf, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "steeleaf_ingot"));
            BLOCK.replacement(GTCoreMaterials.Steeleaf, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "steeleaf_block"));
            INGOT.replacement(GTCoreMaterials.FierySteel, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "fiery_ingot"));
            BLOCK.replacement(GTCoreMaterials.FierySteel, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("twilightforest", "fiery_block"));
        }
        event.setMaterial(GTCoreMaterials.Signalum).asMetal(1353).mats(of(Copper, 1, Silver, 2, RedAlloy, 5));
        event.setMaterial(GTCoreMaterials.Lumium).asMetal(593).mats(of(Tin, 3, Silver, 1, Glowstone, 4));
        event.setMaterial(GTCoreMaterials.Enderium).asMetal(1071).mats(of(Tin, 2, Silver, 1, Platinum, 1, EnderPearl, 4));
        if (AntimatterAPI.isModLoaded("thermal")){
            INGOT.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_ingot"));
            DUST.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_dust"));
            NUGGET.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_nugget"));
            BLOCK.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_block"));
            GEAR.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_gear"));
            PLATE.replacement(GTCoreMaterials.Signalum, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "signalum_plate"));
            INGOT.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_ingot"));
            DUST.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_dust"));
            NUGGET.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_nugget"));
            BLOCK.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_block"));
            GEAR.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_gear"));
            PLATE.replacement(Lumium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "lumium_plate"));
            INGOT.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_ingot"));
            DUST.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_dust"));
            NUGGET.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_nugget"));
            BLOCK.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_block"));
            GEAR.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_gear"));
            PLATE.replacement(GTCoreMaterials.Enderium, () -> AntimatterPlatformUtils.INSTANCE.getItemFromID("thermal", "enderium_plate"));
        }
        GTCoreBlocks.initItemBarrels();
        Guis.init();
    }
}
