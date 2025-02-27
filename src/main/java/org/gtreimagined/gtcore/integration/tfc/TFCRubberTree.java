package org.gtreimagined.gtcore.integration.tfc;


import net.dries007.tfc.world.feature.tree.TFCTreeGrower;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.gtreimagined.gtcore.tree.RubberTreeWorldGen;

public class TFCRubberTree extends TFCTreeGrower {
    public TFCRubberTree() {
        super(null, null);
    }

    @Override
    public ConfiguredFeature<?, ?> getNormalFeature(Registry<ConfiguredFeature<?, ?>> registry) {
        return RubberTreeWorldGen.TREE_FEATURE_CONFIG.value();
    }
}
