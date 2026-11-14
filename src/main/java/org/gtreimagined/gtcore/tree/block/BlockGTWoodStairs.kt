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

class BlockGTWoodStairs(planks: Block, val type: String) :
    StairBlock(planks.defaultBlockState(), Properties.copy(planks)), IGTObject,
    IModelProvider {
    init {
        register<BlockGTWoodStairs>( this)
    }

    override fun getDomain(): String {
        return GTCore.ID
    }

    override fun getId(): String {
        return "${type}_stairs"
    }

    override fun onBlockModelBuild(block: Block, prov: GTBlockStateProvider) {
        prov.stairsBlock(this, ResourceLocation(GTCore.ID, "block/wood/${type}_planks"))
    }
}
