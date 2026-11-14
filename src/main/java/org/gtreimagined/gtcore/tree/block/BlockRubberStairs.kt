package org.gtreimagined.gtcore.tree.block

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.StairBlock
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtcore.data.GTCoreBlocks
import org.gtreimagined.gtlib.GTAPI.register
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider
import org.gtreimagined.gtlib.registration.IGTObject
import org.gtreimagined.gtlib.registration.IModelProvider

class BlockRubberStairs :
    StairBlock(GTCoreBlocks.RUBBER_PLANKS.defaultBlockState(), Properties.copy(GTCoreBlocks.RUBBER_PLANKS)), IGTObject,
    IModelProvider {
    init {
        register<BlockRubberStairs>( this)
    }

    override fun getDomain(): String {
        return GTCore.ID
    }

    override fun getId(): String {
        return "rubber_stairs"
    }

    override fun onBlockModelBuild(block: Block?, prov: GTBlockStateProvider) {
        prov.stairsBlock(this, ResourceLocation(GTCore.ID, "block/tree/rubber_planks"))
    }
}
