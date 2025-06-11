package org.gtreimagined.gtcore.machine;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntitySteamMachine;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.screen.GTContainerScreen;
import org.gtreimagined.gtlib.gui.slot.ISlotProvider;
import org.gtreimagined.gtlib.gui.widget.MachineStateWidget;
import org.gtreimagined.gtlib.gui.widget.ProgressWidget;
import org.gtreimagined.gtlib.gui.widget.TextWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.machine.types.Machine;

import java.util.List;

import static org.gtreimagined.gtlib.machine.MachineFlag.*;
import static org.gtreimagined.gtlib.machine.Tier.BRONZE;
import static org.gtreimagined.gtlib.machine.Tier.STEEL;

public class SteamMachine extends Machine<SteamMachine> {

    int durationMultiplier = 1;
    int euMultiplier = 2;

    public SteamMachine(String domain, String id) {
        super(domain, id);
        setTile(BlockEntitySteamMachine::new);
        addFlags(BASIC, STEAM, COVERABLE);
        setGUI(Data.BASIC_MENU_HANDLER);
        setClientTicking();
        getGuiData().getMachineData().setMachineStateLocation(BRONZE, "bronze_machine_state").setMachineStateLocation(STEEL, "steel_machine_state");
        String suffix = id.contains("furnace") || id.contains("smelter") || id.contains("boiler") ? "default" : id.replace("steam_", "");
        getGuiData().getMachineData().setProgressLocation(BRONZE, "bronze_" + suffix).setProgressLocation(STEEL, "steel_" + suffix);
        String bSuffix = id.contains("macerator") || id.contains("forge_hammer") ? id.replace("steam", "") : "";
        if (id.contains("boiler")){
            getGuiData().setBackgroundTexture(BRONZE, id + "_bronze").setBackgroundTexture(STEEL, id + "_steel");
        } else {
            getGuiData().setBackgroundTexture(BRONZE, "machine_bronze" + bSuffix).setBackgroundTexture(STEEL, "machine_steel" + bSuffix);
        }
    }

    public int getDurationMultiplier() {
        return durationMultiplier;
    }

    public int getEuMultiplier() {
        return euMultiplier;
    }

    public SteamMachine setEuMultiplier(int euMultiplier) {
        this.euMultiplier = euMultiplier;
        return this;
    }

    public SteamMachine setDurationMultiplier(int durationMultiplier) {
        this.durationMultiplier = durationMultiplier;
        return this;
    }

    protected void setupGui() {
        super.setupGui();
        addGuiCallback(t -> {
            t.addWidget(WidgetSupplier.build((a, b) -> TextWidget.build(((GTContainerScreen<?>) b).getTitle().getString(), 4210752, false).build(a, b)).setPos(9, 5).clientSide());
            if (has(RECIPE) && !getId().contains("boiler")) {
                t.addWidget(ProgressWidget.build())
                        .addWidget(MachineStateWidget.build());
            }
        });
    }

    @Override
    public Machine<SteamMachine> add(ISlotProvider<?> provider) {
        List<SlotData<?>> list = provider.getAnySlots();
        for (SlotData<?> slot : list) {
            String suffix = slot.getType() == SlotType.FL_IN ? "fluid" : "item";
            add(BRONZE, slot.getType(), slot.getX(), slot.getY(), new ResourceLocation(domain, "bronze_"+suffix));
            add(STEEL, slot.getType(), slot.getX(), slot.getY(), new ResourceLocation(domain, "steel_"+suffix));
        }
        return this;
    }

    @Override
    public Machine<SteamMachine> add(SlotType<?> type, int x, int y) {
        String suffix = type == SlotType.FL_IN ? "fluid" : "item";
        add(BRONZE, type, x, y, new ResourceLocation(domain, "bronze_"+suffix));
        add(STEEL, type, x, y, new ResourceLocation(domain, "steel_"+suffix));
        return this;
    }
}