package org.gtreimagined.gtcore.tree.block

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtlib.GTAPI.register
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider
import org.gtreimagined.gtlib.registration.IGTObject
import org.gtreimagined.gtlib.registration.IModelProvider

class BlockRubberSlab : SlabBlock(
    Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)),
    IGTObject, IModelProvider {
    init {
        register<BlockRubberSlab>( this)
    }

    override fun getDomain(): String {
        return GTCore.ID
    }

    override fun getId(): String {
        return "rubber_slab"
    }

    override fun onBlockModelBuild(block: Block?, prov: GTBlockStateProvider) {
        prov.slabBlock(
            this,
            ResourceLocation(GTCore.ID, "block/rubber_planks"),
            ResourceLocation(GTCore.ID, "block/tree/rubber_planks")
        )
    }
}
