package org.gtreimagined.gtcore.tree.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;

public class BlockRubberStairs extends StairBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberStairs() {
        super(GTCoreBlocks.RUBBER_PLANKS.defaultBlockState(), Properties.copy(GTCoreBlocks.RUBBER_PLANKS));
        AntimatterAPI.register(BlockRubberStairs.class, this);
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
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.stairsBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
