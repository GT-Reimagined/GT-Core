package org.gtreimagined.gtcore.gui.slots;

import brachy.modularui.widgets.slot.ModularCraftingSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.jetbrains.annotations.Nullable;

public class SlotWorkTableResult extends ModularCraftingSlot {
    private @Nullable CraftingContainer craftMatrix;
    private final IItemHandlerModifiable craftingInventory;
    private final MachineItemHandler<?> projectTable;
    public SlotWorkTableResult(MachineItemHandler<?> table, IItemHandler handler, IItemHandlerModifiable craftingInventory, int slotIndex) {
        super(handler, slotIndex);
        this.craftingInventory = craftingInventory;
        projectTable = table;
    }

    @Override
    public void onQuickCraft(ItemStack p_75220_1_, ItemStack p_75220_2_) {

    }

    //requires unfinished branch of mui
    /*@Override
    public CraftingContainerWrapper getCraftSlots() {
        if (craftMatrix == null) {
            craftMatrix = new CraftingContainerWrapper(this, 3,3, craftingInventory, 0);
            ((CraftingContainerWrapper)craftMatrix).notifyContainer();
        }
        return (CraftingContainerWrapper) craftMatrix;
    }*/

    @Override
    public void onTake(Player thePlayer, ItemStack stack) {
        extractedFromTable();
        super.onTake(thePlayer, stack);
    }

    private boolean extractedFromTable(){
        boolean remaining = true;
        for (int i = 0; i < 10; i++) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.getCount() == 1 && itemStack.getMaxStackSize() > 1) {
                extractFromTable(itemStack);
                craftingInventory.setStackInSlot(i, itemStack);
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
}
