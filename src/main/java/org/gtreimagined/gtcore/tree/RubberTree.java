package org.gtreimagined.gtcore.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.tree.block.BlockRubberLog;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.worldgen.feature.IGTFeature;

import java.util.Random;

public class RubberTree extends AbstractTreeGrower {

    public static final TreeFeature TREE_FEATURE = (TreeFeature) GTAPI.register(IGTFeature.class, new RubberTreeFeature()).asFeature();
    public static final WeightedStateProvider TRUNK_BLOCKS;

    static {
        SimpleWeightedRandomList.Builder<BlockState> st = SimpleWeightedRandomList.<BlockState>builder();
        BlockStateProperties.HORIZONTAL_FACING.getPossibleValues().forEach(d -> {
            st.add(GTCoreBlocks.RUBBER_LOG.defaultBlockState()
                    .setValue(ResinState.INSTANCE, ResinState.FILLED)
                    .setValue(BlockRubberLog.RESIN_FACING, d), 1);
            st.add(GTCoreBlocks.RUBBER_LOG.defaultBlockState()
                    .setValue(ResinState.INSTANCE, ResinState.EMPTY)
                    .setValue(BlockRubberLog.RESIN_FACING, d), 1);
        });
        TRUNK_BLOCKS = new WeightedStateProvider(st.build());
}

    public static void init() {
    }


    public RubberTree() {

    }


    @Override
    public boolean growTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource rand) {
        Holder<Biome> biome = world.getBiome(pos);
        ConfiguredFeature<TreeConfiguration, ?> configuredFeature;
        if (biome.is(Biomes.JUNGLE)) {
            configuredFeature = RubberTreeWorldGen.TREE_FEATURE_JUNGLE_CONFIG.value();
        } else if (biome.is(Biomes.SWAMP)) {
            configuredFeature = RubberTreeWorldGen.TREE_FEATURE_SWAMP_CONFIG.value();
        } else {
            configuredFeature = RubberTreeWorldGen.TREE_FEATURE_CONFIG.value();
        }
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
        if (!configuredFeature.place(world, chunkGenerator, rand, pos)) {
            world.setBlock(pos, state, 4);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource rand,
            boolean b) {
        return RubberTreeWorldGen.TREE_FEATURE_CONFIG;
    }
}
