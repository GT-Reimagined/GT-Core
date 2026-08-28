package org.gtreimagined.gtcore.machine;

import net.minecraft.core.Direction;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;

public class HopperItemHandler extends MachineItemHandler<BlockEntityGTHopper> {
    public HopperItemHandler(BlockEntityGTHopper tile) {
        super(tile);
    }

    @Override
    protected TrackedItemHandler<BlockEntityGTHopper> createTrackedHandler(SlotType<?> type, BlockEntityGTHopper tile) {
        if (type == SlotTypes.STORAGE) {
            int count = tile.getMachineType().getCount(tile.getMachineTier(), SlotTypes.STORAGE);
            return new LimitedOutputTrackedHandler<>(tile, SlotTypes.STORAGE, count, type.allowExternalOutput(), type.allowExternalInput(), type.tester());
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
