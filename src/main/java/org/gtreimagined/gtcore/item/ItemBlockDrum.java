package org.gtreimagined.gtcore.item;

import muramasa.antimatter.block.AntimatterItemBlock;
import muramasa.antimatter.data.AntimatterTags;
import muramasa.antimatter.item.IFluidItem;
import muramasa.antimatter.machine.BlockMachine;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtcore.machine.DrumMachine;

import java.util.function.Predicate;

public class ItemBlockDrum extends AntimatterItemBlock implements IFluidItem {
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
            if (machine != null && !machine.isAcidProof() && f.getFluid().is(AntimatterTags.ACID)) return false;
            return true;
        };
    }
}
