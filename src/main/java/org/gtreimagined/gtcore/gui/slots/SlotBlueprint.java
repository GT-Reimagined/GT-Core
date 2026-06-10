package org.gtreimagined.gtcore.gui.slots;

import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;

public class SlotBlueprint extends AbstractSlot<SlotBlueprint> /*implements IClickableSlot*/ {
    public SlotBlueprint(SlotType<SlotBlueprint> type, IGuiHandler tile, IItemHandler stackHandler, int index) {
        super(type, tile, stackHandler, index);
    }


    /*@Override
    public ItemStack clickSlot(int i, ClickType clickType, Player player, AbstractContainerMenu abstractContainerMenu) {
        if (!(abstractContainerMenu instanceof ContainerWorkbench<?> workbench)) return this.getItem();
        ItemStack item = this.getItem();
        if (clickType == ClickType.QUICK_MOVE && i == 0 && item.getItem() == GTCoreItems.EmptyBlueprint) {
            Level level = player.getCommandSenderWorld();
            if (!level.isClientSide()){
                ServerPlayer serverPlayer = (ServerPlayer) player;
                CraftingContainer craftingGrid = workbench.getCraftingGrid();
                Optional<CraftingRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingGrid, level);
                if (optional.isPresent()) {
                    CraftingRecipe recipe = optional.get();
                    ItemStack result = recipe.assemble(craftingGrid, player.level().registryAccess());
                    if (!result.isEmpty()) {
                        ItemStack blueprint = new ItemStack(GTCoreItems.Blueprint, item.getCount()).setHoverName(result.getHoverName());
                        blueprint.getOrCreateTag().putString("recipeId", recipe.getId().toString());
                        this.set(blueprint);
                        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(abstractContainerMenu.containerId, abstractContainerMenu.getStateId(), 31, blueprint));
                    }
                }
            }
            return this.getItem();
        }
        abstractContainerMenu.doClick(31, i, clickType, player);
        return this.getItem();
    }*/
}
