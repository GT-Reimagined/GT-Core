package org.gtreimagined.gtcore.integration.jei;

import brachy.modularui.ModularUIMenuTypes;
import brachy.modularui.screen.ModularContainerMenu;
import brachy.modularui.widgets.slot.ModularSlot;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreSlotTypes;
import org.gtreimagined.gtcore.gui.slots.SlotCrafting;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GTCoreRecipeTransferInfo implements IRecipeTransferInfo<ModularContainerMenu, CraftingRecipe> {
    @Override
    public Class<ModularContainerMenu> getContainerClass() {
        return ModularContainerMenu.class;
    }

    @Override
    public Optional<MenuType<ModularContainerMenu>> getMenuType() {
        return Optional.of(ModularUIMenuTypes.MODULAR_CONTAINER.get());
    }

    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    public boolean canHandle(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        return containerWorkbench.getScreen().getName().contains("workbench") && containerWorkbench.getScreen().getOwner().equals(GTCore.ID);
    }

    @Override
    public List<Slot> getRecipeSlots(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null, null));
        for (Slot slot : containerWorkbench.slots){
            if (slot instanceof SlotCrafting abstractSlot){
                slots.set(abstractSlot.getSuperIndex(), slot);
            }
        }
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        List<Slot> toolSlots = new ArrayList<>();
        List<Slot> storageSlots = new ArrayList<>();
        List<Slot> playerSlots = new ArrayList<>();
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : containerWorkbench.slots){
            if (slot instanceof AbstractSlot<?> abstractSlot) {
                if (abstractSlot.getType() == GTCoreSlotTypes.TOOLS || abstractSlot.getType() == GTCoreSlotTypes.TOOL_CHARGE) {
                    toolSlots.add(slot);
                }
                if (abstractSlot.getType() == SlotTypes.STORAGE){
                    storageSlots.add(slot);
                }
            } else if (slot instanceof ModularSlot modularSlot && modularSlot.getSlotGroupName() != null && modularSlot.getSlotGroupName().equals("player_inventory")){
                playerSlots.add(slot);
            }
        }
        slots.addAll(toolSlots);
        slots.addAll(storageSlots);
        slots.addAll(playerSlots);
        return slots;
    }
}
