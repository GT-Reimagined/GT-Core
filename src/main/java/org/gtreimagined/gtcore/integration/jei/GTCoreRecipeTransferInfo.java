package org.gtreimagined.gtcore.integration.jei;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;

import java.util.ArrayList;
import java.util.List;

public class GTCoreRecipeTransferInfo implements IRecipeTransferInfo<ContainerWorkbench, CraftingRecipe> {
    @Override
    public Class<ContainerWorkbench> getContainerClass() {
        return ContainerWorkbench.class;
    }

    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    public boolean canHandle(ContainerWorkbench containerWorkbench, CraftingRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ContainerWorkbench containerWorkbench, CraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 17; i < 26; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ContainerWorkbench containerWorkbench, CraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        for (int i = 26; i < 70; i++) {
            slots.add(containerWorkbench.getSlot(i));
        }
        return slots;
    }

    @Override
    public Class<CraftingRecipe> getRecipeClass() {
        return CraftingRecipe.class;
    }

    @Override
    public ResourceLocation getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }
}
