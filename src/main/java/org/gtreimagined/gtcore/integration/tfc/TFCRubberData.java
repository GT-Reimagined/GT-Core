package org.gtreimagined.gtcore.integration.tfc;

import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.item.ItemBasic;

public class TFCRubberData {

    public static final Block RUBBER_TWIG = new BlockRubberTwig();
    public static final Block RUBBER_FALLEN_LEAVES = new BlockFallenRubberLeaves();
    public static final Block RUBBER_LOG_FENCE = new BlockRubberLogFence();
    public static final ItemBasic<?> RUBBER_LUMBER = new ItemBasic<>(GTCore.ID, "rubber_lumber");

    public static void init(){
        GTCoreBlocks.RUBBER_LEAVES = new BlockTFCRubberLoaves();
        GTCoreBlocks.RUBBER_SAPLING = new BlockTFCRubberSapling();
    }
}
