package org.gtreimagined.gtcore.machine;

import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.blockentity.ILimitedOutputTile;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class LimitedOutputTrackedHandler<T extends IGuiHandler & ILimitedOutputTile> extends TrackedItemHandler<T> {
    public LimitedOutputTrackedHandler(T tile, SlotType<?> type, int size, boolean output, boolean input, BiPredicate<IGuiHandler, ItemStack> validator) {
        super(tile, type, size, output, input, validator);
    }

    public LimitedOutputTrackedHandler(T tile, SlotType<?> type, int size, boolean output, boolean input, BiPredicate<IGuiHandler, ItemStack> validator, int limit) {
        super(tile, type, size, output, input, validator, limit);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        int stackLimit = getTile().getStackLimit();
        if (!getTile().hasStackLimit()) return super.extractItem(slot, amount, simulate);
        if (amount < stackLimit) return ItemStack.EMPTY;
        amount = stackLimit;
        return super.extractItem(slot, amount, simulate);
    }
}
