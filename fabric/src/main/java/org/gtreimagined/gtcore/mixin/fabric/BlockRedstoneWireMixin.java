package org.gtreimagined.gtcore.mixin.fabric;

import io.github.fabricators_of_create.porting_lib.block.LightEmissiveBlock;
import org.gtreimagined.gtcore.block.BlockRedstoneWire;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockRedstoneWire.class)
public class BlockRedstoneWireMixin implements LightEmissiveBlock {
}
