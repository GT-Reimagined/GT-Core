package org.gtreimagined.gtcore.gui.slots;

import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.slot.AbstractSlot;
import muramasa.antimatter.gui.slot.IClickableSlot;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import tesseract.api.item.ExtendedItemContainer;

import java.util.Optional;

public class SlotBlueprint extends AbstractSlot<SlotBlueprint> implements IClickableSlot {
    public SlotBlueprint(SlotType<SlotBlueprint> type, IGuiHandler tile, ExtendedItemContainer stackHandler, int index, int x, int y) {
        super(type, tile, stackHandler, index, x, y);
    }


    @Override
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
                    ItemStack result = recipe.assemble(craftingGrid);
                    if (!result.isEmpty()) {
                        ItemStack blueprint = new ItemStack(GTCoreItems.Blueprint, item.getCount());
                        StringTag name = StringTag.valueOf(Component.Serializer.toJson(result.getHoverName()));
                        CompoundTag display = new CompoundTag();
                        display.put("Name", name);
                        blueprint.getOrCreateTag().put("display", display);
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
    }
}
