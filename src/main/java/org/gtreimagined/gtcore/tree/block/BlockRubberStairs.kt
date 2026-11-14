package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberStairs extends StairBlock implements IGTObject, IModelProvider {
    public BlockRubberStairs() {
        super(GTCoreBlocks.RUBBER_PLANKS.defaultBlockState(), Properties.copy(GTCoreBlocks.RUBBER_PLANKS));
        GTAPI.register(BlockRubberStairs.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_stairs";
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.stairsBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
