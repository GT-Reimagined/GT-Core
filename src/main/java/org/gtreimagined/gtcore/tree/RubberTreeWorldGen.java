package org.gtreimagined.gtcore.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
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
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.mixin.BiomeAccessor;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RubberTreeWorldGen {

    public static ResourceKey<PlacedFeature> TREE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree"));
    public static ResourceKey<PlacedFeature> TREE_SWAMP = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree_swamp"));
    public static ResourceKey<PlacedFeature> TREE_JUNGLE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree_jungle"));
    public static ResourceKey<ConfiguredFeature<?, ?>> TREE_FEATURE_CONFIG = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree_normal"));
    public static ResourceKey<ConfiguredFeature<?, ?>> TREE_FEATURE_SWAMP_CONFIG = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree_swamp"));
    public static ResourceKey<ConfiguredFeature<?, ?>> TREE_FEATURE_JUNGLE_CONFIG = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(GTCore.ID, "rubber_tree_jungle"));

    public static final PlacementModifierType<RubberTreePlacementModifier> RUBBER_TREE_PLACEMENT_MODIFIER  = () -> RubberTreePlacementModifier.CODEC;

    public static void init(){
        Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, new ResourceLocation(GTCore.ID, "rubber_trunk_placer"), RubberTrunkPlacer.RUBBER);
        Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, new ResourceLocation(GTCore.ID, "rubber_tree_placement_modifier"), RUBBER_TREE_PLACEMENT_MODIFIER);
    }

    public static class RubberTreePlacementModifier extends PlacementModifier {
        public static final Codec<RubberTreePlacementModifier> CODEC = Codec.unit(RubberTreePlacementModifier::new);


        @Override
        public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
            BiomeAccessor biome = ((BiomeAccessor)(Object)context.getLevel().getBiome(pos).value());
            if (context.getLevel().getBiome(pos).is(TagUtils.getBiomeTag(new ResourceLocation(GTCore.ID, "is_invalid_rubber")))) return Stream.empty();
            float p = 0.15F;
            if (biome.getClimateSettings().temperature() > 0.8f) {
                p = 0.04F;
                if (biome.getClimateSettings().hasPrecipitation())
                    p += 0.04F;
            }
            float finalp = p;
            int i = 0;
            if (random.nextDouble() < finalp) {
                i = random.nextInt(1) + 1;
            }
            return IntStream.range(0, i).mapToObj((ix) -> {
                int j = random.nextInt(16) + pos.getX();
                int k = random.nextInt(16) + pos.getZ();
                return new BlockPos(j, context.getHeight(Heightmap.Types.MOTION_BLOCKING, j, k), k);
            });
        }

        @Override
        public PlacementModifierType<?> type() {
            return RUBBER_TREE_PLACEMENT_MODIFIER;
        }
    }
}
