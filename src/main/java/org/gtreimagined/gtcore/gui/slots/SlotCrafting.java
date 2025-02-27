package org.gtreimagined.gtcore.gui.slots;

import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.slot.AbstractSlot;
import tesseract.api.item.ExtendedItemContainer;

public class SlotCrafting extends AbstractSlot<SlotCrafting> {
    public SlotCrafting(SlotType<SlotCrafting> type, IGuiHandler handler, ExtendedItemContainer itemHandler, int index, int xPosition, int yPosition) {
        super(type, handler, itemHandler, index, xPosition, yPosition);
    }
}
