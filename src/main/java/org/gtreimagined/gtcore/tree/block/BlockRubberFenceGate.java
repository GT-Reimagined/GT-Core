package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberFenceGate extends FenceGateBlock implements IGTObject, IModelProvider {
    public BlockRubberFenceGate() {
        super(Properties.copy(GTCoreBlocks.RUBBER_SLAB));
        GTAPI.register(BlockRubberFenceGate.class, this);
    }

    @Override
    public String getId() {
        return "rubber_fence_gate";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.fenceGateBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
