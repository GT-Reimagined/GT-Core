package org.gtreimagined.gtcore.tree.block

import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtlib.block.BlockBasic
import org.gtreimagined.gtlib.texture.Texture

class BlockGTPlanks(val type: String): BlockBasic(GTCore.ID, "${type}_planks",
    Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0f).sound(SoundType.WOOD)) {
    override fun getTextures(): Array<Texture> {
        return arrayOf(Texture(domain, "block/wood/${type}_planks"))
    }
}