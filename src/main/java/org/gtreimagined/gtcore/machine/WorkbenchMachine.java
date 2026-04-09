package org.gtreimagined.gtcore.machine;

import brachy.modularui.widgets.ButtonWidget;
import brachy.modularui.widgets.SlotGroupWidget;
import brachy.modularui.widgets.slot.ItemSlot;
import brachy.modularui.widgets.slot.ModularSlot;
import brachy.modularui.widgets.slot.SlotGroup;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.gtreimagined.gtcore.blockentity.BlockEntityWorkbench;
import org.gtreimagined.gtcore.data.MenuHandlers;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.gui.slots.SlotBlueprint;
import org.gtreimagined.gtcore.gui.slots.SlotCrafting;
import org.gtreimagined.gtcore.gui.slots.SlotCraftingOutput;
import org.gtreimagined.gtcore.gui.slots.SlotCraftingOutput.WrappedCraftingInventory;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotType.ISlotSupplier;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.machine.IPanelFunction;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.drawable.GTDrawableStack;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.gtreimagined.gtlib.gui.SlotType.STORAGE;
import static org.gtreimagined.gtlib.machine.MachineFlag.*;


public class WorkbenchMachine extends ChargingMachine{
    public WorkbenchMachine(String domain, Material material, boolean charge) {
        super(domain, material, "workbench", charge);
        setGUI(MenuHandlers.WORKBENCH_HANDLER);
        this.addFlags(ITEM, GUI);
        this.setTile(BlockEntityWorkbench::new);
        this.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (guiData.getSide() != Direction.UP) return;
        });
        this.setSlotFunction(((modularPanel, machine, guiData, syncManager, settings) -> {
            if (guiData.getSide() == Direction.UP) {
                syncManager.registerSlotGroup("storage", 4)
                        .registerSlotGroup("tools", 5, 99);
                SlotGroup craftingSlotGroup = new SlotGroup("crafting", 3);
                syncManager.registerSlotGroup(craftingSlotGroup);
                modularPanel.child(SlotGroupWidget
                        .builder()
                        .matrix("IIII", "IIII", "IIII", "IIII")
                        .key('I', i -> new ItemSlot()
                                .slot(new AbstractSlot<>(STORAGE, machine, machine.itemHandler.map(item ->
                                        item.getAll().get(STORAGE)).orElse(new EmptyHandler()), i)))
                        .slotGroup("storage")
                        .build().pos(7, 7));
                modularPanel.child(SlotGroupWidget
                        .builder()
                        .matrix("III", "III", "III")
                        .key('I', i -> new ItemSlot()
                                .slot(new SlotCrafting(SlotTypes.CRAFTING, machine, machine.itemHandler.map(item ->
                                        item.getAll().get(SlotTypes.CRAFTING)).orElse(new EmptyHandler()), i)))
                        .slotGroup("crafting")
                        .build().pos(81, 27));
                SlotType<AbstractSlot<?>> toolSlot = charge ? SlotTypes.TOOL_CHARGE : SlotTypes.TOOLS;
                Function<Integer, ModularSlot> slotSupplier = i -> toolSlot.getSlotSupplier().get(toolSlot, machine, machine.itemHandler.map(MachineItemHandler::getAll).orElse(Map.of()), i, new SlotData<>(toolSlot, 0, 0));
                modularPanel.child(SlotGroupWidget
                        .builder()
                        .matrix("IIIII")
                        .key('I', i -> new ItemSlot()
                                .slot(slotSupplier.apply(i)).background(new GTDrawableStack(toolSlot.getTexture() == GTGuiTextures.ITEM_SLOT ? null :  toolSlot.getTexture(), toolSlot.getOverlay())))
                        .slotGroup("tools")
                        .build().pos(81, 7));
                SlotCraftingOutput output = new SlotCraftingOutput(machine.itemHandler.map(m -> m).orElse(null),
                        machine.itemHandler.map(item -> item.getAll().get(SlotTypes.CRAFTING_RESULT)).orElse(new EmptyHandler()),
                        machine.itemHandler.map(item -> (IItemHandlerModifiable)item.getAll().get(SlotTypes.CRAFTING)).orElse(new EmptyHandler()), 0);
                craftingSlotGroup.addSlotChangeListener(output::updateCraftResult);
                modularPanel.child(new ItemSlot().slot(output).pos(135, 63).background(new GTDrawableStack(null, SlotTypes.CRAFTING_RESULT.getOverlay())));
                modularPanel.child(new ItemSlot()
                        .pos(135, 27)
                        .slot(new SlotBlueprint(SlotTypes.BLUEPRINT, machine, machine.itemHandler.map(item -> item.getAll().get(SlotTypes.BLUEPRINT)).orElse(new EmptyHandler()), 0)
                                .slotGroup(SlotGroup.singleton("blueprint", 99)))
                        .background(new GTDrawableStack(null, SlotTypes.BLUEPRINT.getOverlay())));
                modularPanel.child(new ItemSlot()
                        .pos(153, 27)
                        .slot(new AbstractSlot<>(SlotTypes.EXPORT, machine, machine.itemHandler.map(item -> item.getAll().get(SlotTypes.EXPORT)).orElse(new EmptyHandler()), 0))
                        .background(new GTDrawableStack(null, SlotTypes.EXPORT.getOverlay())));
                modularPanel.child(new ItemSlot()
                        .pos(153, 63)
                        .slot(new AbstractSlot<>(SlotTypes.PARK, machine, machine.itemHandler.map(item -> item.getAll().get(SlotTypes.PARK)).orElse(new EmptyHandler()), 0))
                        .background(new GTDrawableStack(null, SlotTypes.PARK.getOverlay())));
                syncManager.registerSyncedAction("inventorySend", packet -> {
                    for (int i = 0; i < 9; i++) {
                        int finalI = i;
                        boolean toPlayer = packet.readBoolean();
                        IItemHandler inventory = toPlayer ? new PlayerMainInvWrapper(syncManager.getPlayer().getInventory()) : machine.itemHandler.map(item -> item.getAll().get(STORAGE)).orElse(new EmptyHandler());
                        ItemStack leftover = ItemHandlerHelper.insertItem(inventory, machine.itemHandler.map(item -> item.getHandler(SlotTypes.CRAFTING).getStackInSlot(finalI)).orElse(ItemStack.EMPTY), false);
                        machine.itemHandler.ifPresent(item -> item.getHandler(SlotTypes.CRAFTING).setStackInSlot(finalI, leftover.copy()));
                        output.updateCraftResult(null);
                    }
                });
                modularPanel.child(new ButtonWidget<>()
                        .onMousePressed((x, y, mouseButton) -> {
                            syncManager.callSyncedAction("inventorySend", b -> b.writeBoolean(false));
                            return true;
                        })
                        .overlay(GTCoreGuiTextures.TO_INV_BUTTON)
                        .pos(136, 46));
                modularPanel.child(new ButtonWidget<>()
                        .onMousePressed((x, y, mouseButton) -> {
                            syncManager.callSyncedAction("inventorySend", b -> b.writeBoolean(true));
                            return true;
                        })
                        .overlay(GTCoreGuiTextures.TO_PLAYER_BUTTON)
                        .pos(154, 46));
                return;
            }
            syncManager.registerSlotGroup("storage", 9);
            modularPanel.child(SlotGroupWidget
                    .builder()
                    .matrix("IIIIIIIII", "IIIIIIIII", "IIIIIIIII", "IIIIIIIII")
                    .key('I', i -> new ItemSlot()
                            .slot(new AbstractSlot<>(STORAGE, machine, machine.itemHandler.map(item ->
                                    item.getAll().get(STORAGE)).orElse(new EmptyHandler()), i + 16)))
                    .slotGroup("storage")
                    .build().pos(7, 7));
        }));
        this.getGuiProperties().setHasGTIcon(false);
        this.removeFlags(COVERABLE);
        GTAPI.register(WorkbenchMachine.class, this);
    }
}
