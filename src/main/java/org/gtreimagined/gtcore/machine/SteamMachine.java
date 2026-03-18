package org.gtreimagined.gtcore.machine;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.blockentity.BlockEntitySteamMachine;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtcore.mui.GTCoreThemes;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.screen.GTContainerScreen;
import org.gtreimagined.gtlib.gui.slot.ISlotProvider;
import org.gtreimagined.gtlib.gui.widget.MachineStateWidget;
import org.gtreimagined.gtlib.gui.widget.ProgressWidget;
import org.gtreimagined.gtlib.gui.widget.TextWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.machine.Tier;
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
        getGuiProperties().setTheme(BRONZE, GTCoreThemes.BRONZE_THEME_ID).setTheme(STEEL, GTCoreThemes.STEEL_THEME_ID);
        getGuiProperties().getMachineData().setMachineStateLocation(BRONZE, GTCoreGuiTextures.BRONZE_MACHINE_STATE).setMachineStateLocation(STEEL, GTCoreGuiTextures.STEEL_MACHINE_STATE);
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
}