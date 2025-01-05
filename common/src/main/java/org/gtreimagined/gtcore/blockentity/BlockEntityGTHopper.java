package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.gtreimagined.gtcore.machine.MaterialMachine;

public class BlockEntityGTHopper extends BlockEntityMaterial<BlockEntityGTHopper>{
    public BlockEntityGTHopper(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Direction getFacing(BlockState state) {
        return state.getValue(BlockStateProperties.FACING_HOPPER);
    }

    public boolean setFacing(Direction side) {
        if (side == Direction.UP) return false;
        boolean isEmpty = this.coverHandler.map((ch) -> ch.get(side).isEmpty()).orElse(true);
        if (!isEmpty) return false;
        BlockState state = this.getBlockState().setValue(BlockStateProperties.FACING_HOPPER, side);
        this.getLevel().setBlockAndUpdate(this.getBlockPos(), state);
        this.invalidateCaps();
        return true;
    }
}
