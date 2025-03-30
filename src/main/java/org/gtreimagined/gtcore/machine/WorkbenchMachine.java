package org.gtreimagined.gtcore.machine;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityWorkbench;
import org.gtreimagined.gtcore.data.MenuHandlers;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.widget.SlotWidget;
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
        this.add(SlotTypes.BLUEPRINT, 136, 28);
        this.add(SlotTypes.EXPORT, 154, 28);
        this.add(SlotTypes.PARK, 154, 64);
        this.addGuiCallback(t -> {
            t.addButton(136, 46, new ButtonOverlay(GTCore.ID, "to_inv", 18, 18));
            t.addButton(154, 46, new ButtonOverlay(GTCore.ID, "to_player", 18, 18));
            t.addWidget(SlotWidget.build(new SlotData<>(STORAGE, 136, 64, new ResourceLocation(GTCore.ID, "textures/gui/slots/crafting_output.png"))));
        });
        this.removeFlags(COVERABLE);
        GTAPI.register(WorkbenchMachine.class, this);
    }
}
