package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityBookShelf extends BlockEntityMachine<BlockEntityBookShelf> {
    public BlockEntityBookShelf(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
