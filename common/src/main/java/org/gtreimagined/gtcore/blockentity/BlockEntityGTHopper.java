package org.gtreimagined.gtcore.blockentity;

import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.machine.HopperItemHandler;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import tesseract.TesseractCapUtils;

public class BlockEntityGTHopper extends BlockEntityMaterial<BlockEntityGTHopper>{
    boolean disabled = false;
    public BlockEntityGTHopper(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new HopperItemHandler(this));
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

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (disabled || level.getGameTime() % 2 != 0) return;
        if (this.itemHandler.map(i -> !i.getHandler(SlotType.STORAGE).isEmpty()).orElse(false)) {
            BlockEntity neighbor = getCachedBlockEntity(this.getFacing());
            if (neighbor != null) {
                TesseractCapUtils.INSTANCE.getItemHandler(neighbor, this.getFacing().getOpposite()).ifPresent(adjHandler -> {
                    this.itemHandler.ifPresent(h -> Utils.transferItems(h.getHandler(SlotType.STORAGE), adjHandler, true));
                });
            }
        }
        BlockEntity above = getCachedBlockEntity(Direction.UP);
        if (above != null) {
            this.itemHandler.ifPresent(to -> {
                TesseractCapUtils.INSTANCE.getItemHandler(above, Direction.DOWN).ifPresent(from -> {
                    Utils.transferItems(from, to.getHandler(SlotType.STORAGE), true);
                });
            });
        }
    }

    @Override
    public void onFirstTick() {
        super.onFirstTick();
        if (isClientSide()) return;
        for (Direction direction : Direction.values()) {
            if (level.getSignal(this.getBlockPos().relative(direction), direction) > 0){
                this.disabled = true;
            }
        }
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
        Direction side = Utils.getOffsetFacing(this.getBlockPos(), neighbor);
        if (side != null){
            this.disabled = level.getSignal(neighbor, side) > 0;
        }
    }
}
