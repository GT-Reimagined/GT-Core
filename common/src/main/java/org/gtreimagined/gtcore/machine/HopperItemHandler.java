package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.capability.item.TrackedItemHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.gui.SlotType;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;
import org.jetbrains.annotations.NotNull;

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
