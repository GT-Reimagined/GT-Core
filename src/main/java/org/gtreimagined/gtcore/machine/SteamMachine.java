package org.gtreimagined.gtcore.machine;

import brachy.modularui.drawable.UITexture;
import brachy.modularui.drawable.progress.CompositeProgress;
import brachy.modularui.value.sync.DoubleSyncValue;
import brachy.modularui.widgets.ProgressWidget;
import org.gtreimagined.gtcore.blockentity.BlockEntitySteamMachine;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtcore.mui.GTCoreThemes;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.BarDir;
import org.gtreimagined.gtlib.mui.widgets.GTProgressWidget;
import org.gtreimagined.gtlib.util.int2;

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
        getGuiFunctions().add(((modularPanel, machine, guiData, syncManager, settings) -> {
            if (has(RECIPE)) {
                int2 size = guiProperties.getMachineData().getMachineStateSize();
                modularPanel.child(new org.gtreimagined.gtlib.mui.widgets.MachineStateWidget(machine.getMachineTier(), this.has(RECIPE), machine::getMachineState,
                        guiProperties.getMachineData().getMachineStateTexture(machine.getMachineTier()))
                        .pos(guiProperties.getMachineData().getMachineStatePos().x, guiProperties.getMachineData().getMachineStatePos().y)
                        .size(size.x, size.y));

                syncManager.syncValue("progress", new DoubleSyncValue(() -> machine.recipeHandler.map(r -> guiProperties.getMachineData().getProgressPercentFunction().apply(r.getCurrentProgress(), r.getMaxProgress())).orElse(0f)));
                syncManager.syncValue("progress", new DoubleSyncValue(() -> machine.recipeHandler.map(r -> guiProperties.getMachineData().getProgressPercentFunction().apply(r.getCurrentProgress(), r.getMaxProgress())).orElse(0f)));
                BarDir direction = guiProperties.getMachineData().getDir();
                UITexture texture = guiProperties.getMachineData().getProgressTexture(machine.getMachineTier());
                ProgressWidget progressWidget = new GTProgressWidget(machine.getMachineType(), machine.getMachineTier())
                        .syncHandler("progress")
                        .pos(guiProperties.getMachineData().getProgressPos().x + 6, guiProperties.getMachineData().getProgressPos().y + 6);
                modularPanel.child(progressWidget);
                if (!direction.isCircular()) {
                    progressWidget.texture(texture, direction.toRegularDirection());
                } else {
                    progressWidget.progress(CompositeProgress.circularLike4Slice(
                            texture.getSubArea(0.0f, 0.0f, 1f, 0.5f),
                            texture.getSubArea(0f, 0.5f,1f, 1f),
                            direction.toCircularDirection()
                    ));
                }
            }
        }));
    }
}