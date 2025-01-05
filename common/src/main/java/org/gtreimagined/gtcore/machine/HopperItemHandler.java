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
        int count = tile.getMachineType().getCount(tile.getMachineTier(), SlotType.STORAGE);
        this.inventories.put(SlotType.STORAGE, new TrackedItemHandler<>(tile, SlotType.STORAGE, count, true, true, (t, s) -> true){
            @NotNull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                int stackLimit = 1;
                //if (!tile.observeStackLimit) return super.extractItem(slot, amount, simulate);
                if (amount < stackLimit) return ItemStack.EMPTY;
                amount = stackLimit;
                return super.extractItem(slot, amount, simulate);
            }
        });
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
