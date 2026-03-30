package org.gtreimagined.gtcore.gui.slots;

import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;

public class SlotCrafting extends AbstractSlot<SlotCrafting> {
    public SlotCrafting(SlotType<SlotCrafting> type, IGuiHandler handler, IItemHandler itemHandler, int index) {
        super(type, handler, itemHandler, index);
    }
}
