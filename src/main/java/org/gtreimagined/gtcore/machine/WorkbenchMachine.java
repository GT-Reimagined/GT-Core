package org.gtreimagined.gtcore.machine;

import brachy.modularui.widgets.ButtonWidget;
import brachy.modularui.widgets.slot.ItemSlot;
import net.minecraft.core.Direction;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.gtreimagined.gtcore.blockentity.BlockEntityWorkbench;
import org.gtreimagined.gtcore.data.MenuHandlers;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.machine.IPanelFunction;
import org.gtreimagined.gtlib.material.Material;

import static org.gtreimagined.gtlib.gui.SlotType.STORAGE;
import static org.gtreimagined.gtlib.machine.MachineFlag.*;


public class WorkbenchMachine extends ChargingMachine{
    public WorkbenchMachine(String domain, Material material, boolean charge) {
        super(domain, material, "workbench", charge);
        setGUI(MenuHandlers.WORKBENCH_HANDLER);
        this.addFlags(ITEM, GUI);
        this.setTile(BlockEntityWorkbench::new);
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                this.add(STORAGE, 8 + (x * 18), 8 + (y * 18));
            }
        }
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 3; x++){
                this.add(SlotTypes.CRAFTING, 82 + (x * 18), 28 + (y * 18));
            }
        }
        for (int x = 0; x < 5; x++){
            this.add(charge ? SlotTypes.TOOL_CHARGE : SlotTypes.TOOLS, 82 + (x * 18), 8);
        }
        this.add(SlotTypes.CRAFTING_RESULT, 136, 64);
        this.add(SlotTypes.BLUEPRINT, 136, 28);
        this.add(SlotTypes.EXPORT, 154, 28);
        this.add(SlotTypes.PARK, 154, 64);
        this.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (guiData.getSide() != Direction.UP) return;
            modularPanel.child(new ButtonWidget<>()
                    .onMousePressed((x, y, mouseButton) -> {
                        //TODO
                        return true;
                    })
                    .overlay(GTCoreGuiTextures.TO_INV_BUTTON)
                    .pos(136, 46));
            modularPanel.child(new ButtonWidget<>()
                    .onMousePressed((x, y, mouseButton) -> {
                        //TODO
                        return true;
                    })
                    .overlay(GTCoreGuiTextures.TO_PLAYER_BUTTON)
                    .pos(154, 46));
        });
        IPanelFunction slotFunction = this.getSlotFunction();
        this.setSlotFunction(((modularPanel, machine, guiData, syncManager, settings) -> {
            if (guiData.getSide() == Direction.UP) {
                slotFunction.modifyPanel(modularPanel, machine, guiData, syncManager, settings);
                return;
            }
            int i = 16;
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 9; x++) {
                    int xPos = 8 + (x * 18);
                    int yPos = 8 + (y * 18);
                    modularPanel.child(new ItemSlot()
                            .slot(new AbstractSlot<>(STORAGE, machine, machine.itemHandler.map(item ->
                                    item.getAll().get(STORAGE)).orElse(new EmptyHandler()), i))
                            .pos(xPos - 1, yPos - 1));
                    i++;
                }
            }
        }));
        this.getGuiProperties().setHasGTIcon(false);
        this.removeFlags(COVERABLE);
        GTAPI.register(WorkbenchMachine.class, this);
    }
}
