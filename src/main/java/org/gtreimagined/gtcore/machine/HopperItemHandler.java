package org.gtreimagined.gtcore.machine;

import net.minecraft.core.Direction;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;

public class HopperItemHandler extends MachineItemHandler<BlockEntityGTHopper> {
    public HopperItemHandler(BlockEntityGTHopper tile) {
        super(tile);
    }

    @Override
    protected TrackedItemHandler<BlockEntityGTHopper> createTrackedHandler(SlotType<?> type, BlockEntityGTHopper tile) {
        if (type == SlotType.STORAGE) {
            int count = tile.getMachineType().getCount(tile.getMachineTier(), SlotType.STORAGE);
            return new LimitedOutputTrackedHandler<>(tile, SlotType.STORAGE, count, type.output, type.input, type.tester);
        }
        return super.createTrackedHandler(type, tile);
    }

    @Override
    public boolean allowsInput(Direction side) {
        return side != tile.getFacing();
    }

    @Override
    public boolean allowsOutput(Direction side) {
        return side == tile.getFacing();
    }
}
