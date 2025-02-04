package org.gtreimagined.gtcore.gui.slots;

import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.slot.AbstractSlot;
import muramasa.antimatter.gui.slot.IClickableSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import tesseract.api.item.ExtendedItemContainer;

public class SlotBlueprint extends AbstractSlot<SlotBlueprint> implements IClickableSlot {
    public SlotBlueprint(SlotType<SlotBlueprint> type, IGuiHandler tile, ExtendedItemContainer stackHandler, int index, int x, int y) {
        super(type, tile, stackHandler, index, x, y);
    }


    @Override
    public ItemStack clickSlot(int i, ClickType clickType, Player player, AbstractContainerMenu abstractContainerMenu) {
        if (player.isShiftKeyDown()) {

        }
        abstractContainerMenu.doClick(this.index, i, clickType, player);
        return this.getItem();
    }
}
