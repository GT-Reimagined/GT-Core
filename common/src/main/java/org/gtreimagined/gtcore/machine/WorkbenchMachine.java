package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.gui.ButtonOverlay;
import muramasa.antimatter.gui.SlotData;
import muramasa.antimatter.gui.widget.SlotWidget;
import muramasa.antimatter.material.Material;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityWorkbench;
import org.gtreimagined.gtcore.data.MenuHandlers;
import org.gtreimagined.gtcore.data.SlotTypes;

import static muramasa.antimatter.gui.SlotType.STORAGE;
import static muramasa.antimatter.machine.MachineFlag.*;

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
        this.add(SlotTypes.PARK, 154, 46);
        this.addGuiCallback(t -> {
            t.addButton(136, 28, new ButtonOverlay(GTCore.ID, "to_inv", 18, 18));
            t.addButton(154, 28, new ButtonOverlay(GTCore.ID, "to_player", 18, 18));
            t.addWidget(SlotWidget.build(new SlotData<>(STORAGE, 136, 46, new ResourceLocation(GTCore.ID, "textures/gui/slots/crafting_output.png"))));
        });
        this.removeFlags(COVERABLE);
        AntimatterAPI.register(WorkbenchMachine.class, this);
    }
}
