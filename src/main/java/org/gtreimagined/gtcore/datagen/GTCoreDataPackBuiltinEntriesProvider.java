package org.gtreimagined.gtcore.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.tree.RubberFoliagePlacer;
import org.gtreimagined.gtcore.tree.RubberTree;
import org.gtreimagined.gtcore.tree.RubberTreeWorldGen;
import org.gtreimagined.gtcore.tree.RubberTreeWorldGen.RubberTreePlacementModifier;
import org.gtreimagined.gtcore.tree.RubberTrunkPlacer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GTCoreDataPackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    static RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, GTCoreDataPackBuiltinEntriesProvider::populateConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, GTCoreDataPackBuiltinEntriesProvider::populatePlacedFeatures);
    public GTCoreDataPackBuiltinEntriesProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Set.of(GTCore.ID, "minecraft"));
    }

    public static void populateConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context){
        final TreeConfiguration RUBBER_TREE_CONFIG_SWAMP =
                (new TreeConfiguration.TreeConfigurationBuilder(RubberTree.TRUNK_BLOCKS, new RubberTrunkPlacer(5, 2, 2), BlockStateProvider.simple(GTCoreBlocks.RUBBER_LEAVES.defaultBlockState()),
                        new RubberFoliagePlacer(),  new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(0.25f))).build();

        final TreeConfiguration RUBBER_TREE_CONFIG_JUNGLE =
                (new TreeConfiguration.TreeConfigurationBuilder(RubberTree.TRUNK_BLOCKS, new RubberTrunkPlacer(7, 2, 2), BlockStateProvider.simple(GTCoreBlocks.RUBBER_LEAVES.defaultBlockState()),
                        new RubberFoliagePlacer(),  new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(0.25f))).build();

        final TreeConfiguration RUBBER_TREE_CONFIG_NORMAL =
                (new TreeConfiguration.TreeConfigurationBuilder(RubberTree.TRUNK_BLOCKS, new RubberTrunkPlacer(5, 2, 2),BlockStateProvider.simple(GTCoreBlocks.RUBBER_LEAVES.defaultBlockState()),
                        new RubberFoliagePlacer(),  new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build();
        context.register(RubberTreeWorldGen.TREE_FEATURE_CONFIG, new ConfiguredFeature<>(RubberTree.TREE_FEATURE, RUBBER_TREE_CONFIG_NORMAL));
        context.register(RubberTreeWorldGen.TREE_FEATURE_JUNGLE_CONFIG, new ConfiguredFeature<>(RubberTree.TREE_FEATURE, RUBBER_TREE_CONFIG_JUNGLE));
        context.register(RubberTreeWorldGen.TREE_FEATURE_SWAMP_CONFIG, new ConfiguredFeature<>(RubberTree.TREE_FEATURE, RUBBER_TREE_CONFIG_SWAMP));

    }

    public static void populatePlacedFeatures(BootstapContext<PlacedFeature> context){
        context.register(RubberTreeWorldGen.TREE, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(RubberTreeWorldGen.TREE_FEATURE_CONFIG), List.of(new RubberTreePlacementModifier(), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(GTCoreBlocks.RUBBER_SAPLING.defaultBlockState(), BlockPos.ZERO)))));
        context.register(RubberTreeWorldGen.TREE_JUNGLE, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(RubberTreeWorldGen.TREE_FEATURE_JUNGLE_CONFIG), List.of(new RubberTreePlacementModifier(), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(GTCoreBlocks.RUBBER_SAPLING.defaultBlockState(), BlockPos.ZERO)))));
        context.register(RubberTreeWorldGen.TREE_SWAMP, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(RubberTreeWorldGen.TREE_FEATURE_SWAMP_CONFIG), List.of(new RubberTreePlacementModifier(), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(GTCoreBlocks.RUBBER_SAPLING.defaultBlockState(), BlockPos.ZERO)))));
    }
}
