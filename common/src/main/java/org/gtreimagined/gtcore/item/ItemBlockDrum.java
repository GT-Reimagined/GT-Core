package org.gtreimagined.gtcore.item;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import muramasa.antimatter.block.AntimatterItemBlock;
import muramasa.antimatter.data.AntimatterTags;
import muramasa.antimatter.item.IFluidItem;
import muramasa.antimatter.machine.BlockMachine;
import org.gtreimagined.gtcore.machine.DrumMachine;
import tesseract.TesseractGraphWrappers;

import java.util.function.BiPredicate;

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
    public long getTankSize() {
        if (machine != null) return machine.maxCapacity * TesseractGraphWrappers.dropletMultiplier;
        return 0;
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> {
            if (machine != null && !machine.isAcidProof() && f.getFluid().is(AntimatterTags.ACID)) return false;
            return true;
        };
    }
}
