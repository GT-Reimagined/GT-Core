package org.gtreimagined.gtcore.integration.jei;

import brachy.modularui.screen.ModularContainerMenu;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GTCoreRecipeTransferInfo implements IRecipeTransferInfo<ModularContainerMenu, CraftingRecipe> {
    @Override
    public Class<ModularContainerMenu> getContainerClass() {
        return ModularContainerMenu.class;
    }

    @Override
    public Optional<MenuType<ModularContainerMenu>> getMenuType() {
        return Optional.empty();
    }

    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    public boolean canHandle(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 17; i < 26; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ModularContainerMenu containerWorkbench, CraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        for (int i = 26; i < 70; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        return slots;
    }
}
