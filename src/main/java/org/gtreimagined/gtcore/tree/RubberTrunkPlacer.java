package org.gtreimagined.gtcore.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.gtreimagined.gtcore.data.GTCoreBlocks;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RubberTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<RubberTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return trunkPlacerParts(instance).apply(instance, RubberTrunkPlacer::new);
    });
    public static final TrunkPlacerType<RubberTrunkPlacer> RUBBER = new TrunkPlacerType<>(CODEC);
    public RubberTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return RUBBER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        if (!AntimatterAPI.isModLoaded("tfc")) setDirtAt(level, blockSetter, random, pos.below(), config);

        for(int i = 0; i < freeTreeHeight; ++i) {
            placeLog(level, blockSetter, random, pos.above(i), config, Function.identity(), i);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private static boolean isDirt(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (blockState) -> {
            return Feature.isDirt(blockState) && !blockState.is(Blocks.GRASS_BLOCK) && !blockState.is(Blocks.MYCELIUM) && (!AntimatterAPI.isModLoaded("tfc") ||
            !blockState.is(TagUtils.getBlockTag(new ResourceLocation("tfc", "tree_grows_on"))));
        });
    }

    protected static void setDirtAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, BlockPos pos, TreeConfiguration config) {
        if (config.forceDirt || !isDirt(level, pos)) {

            blockSetter.accept(pos, config.dirtProvider.getState(random, pos));
        }

    }

    protected static void placeLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> propertySetter, int i) {
        if (TreeFeature.validTreePos(level, pos)) {
            BlockState state = null;
            if (i > 0){
                state = GTCoreBlocks.RUBBER_LOG.defaultBlockState();
            } else {
                state = config.trunkProvider.getState(random, pos);
            }
            blockSetter.accept(pos, (BlockState)propertySetter.apply(state));
        }
    }
}
