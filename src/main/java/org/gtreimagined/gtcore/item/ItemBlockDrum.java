package org.gtreimagined.gtcore.item;

import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtlib.block.GTItemBlock;
import org.gtreimagined.gtlib.data.GTLibTags;
import org.gtreimagined.gtlib.item.IFluidItem;
import org.gtreimagined.gtlib.machine.BlockMachine;

import java.util.function.Predicate;

public class ItemBlockDrum extends GTItemBlock implements IFluidItem {
    final DrumMachine machine;
    public ItemBlockDrum(BlockMachine block) {
        super(block);
        if (block.getType() instanceof DrumMachine machine){
            this.machine = machine;
        } else {
            machine = null;
        }
    }

    @Override
    public int getCapacity() {
        if (machine != null) return machine.maxCapacity;
        return 0;
    }

    @Override
    public Predicate<FluidStack> getFilter() {
        return (f) -> {
            if (machine != null && !machine.isAcidProof() && f.getFluid().is(GTLibTags.ACID)) return false;
            return true;
        };
    }
}
