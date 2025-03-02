package org.gtreimagined.gtcore.gui.slots;

import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.slot.AbstractSlot;
import net.minecraftforge.items.IItemHandler;

public class SlotCrafting extends AbstractSlot<SlotCrafting> {
    public SlotCrafting(SlotType<SlotCrafting> type, IGuiHandler handler, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(type, handler, itemHandler, index, xPosition, yPosition);
    }
}
