package org.gtreimagined.gtcore.gui.slots;

import brachy.modularui.widgets.slot.ModularCraftingSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.capability.item.ITrackedHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlotCraftingOutput extends ModularCraftingSlot {
    private @Nullable CraftingContainer craftMatrix;
    private final MachineItemHandler<?> projectTable;
    private final IItemHandlerModifiable inputGrid;
    public SlotCraftingOutput(MachineItemHandler<?> table, IItemHandler handler, IItemHandlerModifiable craftingInventory, int slotIndex) {
        super(new WrappedCraftingInventory(handler), slotIndex);
        this.inputInventory(new WrappedCraftingInventory(craftingInventory));
        this.inputGrid = craftingInventory;
        projectTable = table;
    }

    @Override
    public void onQuickCraft(ItemStack p_75220_1_, ItemStack p_75220_2_) {

    }

    @Override
    public void updateCraftResult(Slot slot) {
        super.updateCraftResult(slot);
    }

    @Override
    public void onTake(Player thePlayer, ItemStack stack) {
        extractedFromTable();
        super.onTake(thePlayer, stack);
    }

    private boolean extractedFromTable(){
        boolean remaining = true;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inputGrid.getStackInSlot(i);
            if (itemStack.getCount() == 1 && itemStack.getMaxStackSize() > 1) {
                extractFromTable(itemStack);
                inputGrid.setStackInSlot(i, itemStack);
            }
            if (itemStack.getCount() == 1) {
                remaining  =  false;
            }
        }
        return remaining;
    }

    private ItemStack extractFromTable(ItemStack itemStack){
        for (int j = 0; j < projectTable.getHandler(SlotType.STORAGE).getSlots(); j++) {
            if (ItemStack.isSameItemSameTags(projectTable.getHandler(SlotType.STORAGE).getStackInSlot(j), itemStack)) {
                projectTable.getHandler(SlotType.STORAGE).extractFromInput(j, 1, false);
                itemStack.setCount(itemStack.getCount() + 1);
                break;
            }
        }
        return itemStack;
    }

    public record WrappedCraftingInventory(IItemHandler modifiable) implements IItemHandlerModifiable{

        @Override
        public void setStackInSlot(int i, @NotNull ItemStack itemStack) {
            if (modifiable instanceof IItemHandlerModifiable mod) mod.setStackInSlot(i, itemStack);
        }

        @Override
        public int getSlots() {
            return modifiable.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int i) {
            return modifiable.getStackInSlot(i);
        }

        @Override
        public @NotNull ItemStack insertItem(int i, @NotNull ItemStack itemStack, boolean b) {
            return modifiable.insertItem(i, itemStack, b);
        }

        @Override
        public @NotNull ItemStack extractItem(int i, int i1, boolean b) {
            return modifiable instanceof ITrackedHandler trackedHandler ? trackedHandler.extractFromInput(i, i1, b) : modifiable.extractItem(i, i1, b);
        }

        @Override
        public int getSlotLimit(int i) {
            return modifiable.getSlotLimit(i);
        }

        @Override
        public boolean isItemValid(int i, @NotNull ItemStack itemStack) {
            return modifiable.isItemValid(i, itemStack);
        }
    }
}
