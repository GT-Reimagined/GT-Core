package org.gtreimagined.gtcore.data;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtlib.gui.GuiProperties;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.slot.ISlotProvider;
import org.gtreimagined.gtlib.machine.IPanelFunction;

import static org.gtreimagined.gtlib.gui.SlotType.*;


public class Guis {
    public static GuiProperties SIMPLE_DISPLAY = new GuiProperties(GTCore.ID, "simple_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 53, 25).add(IT_OUT, 107, 25));
    public static GuiProperties MULTI_DISPLAY = new GuiProperties(GTCore.ID, "multi_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 17, 34).add(IT_IN, 35, 34)
            .add(IT_IN, 53, 34).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16).add(IT_OUT, 107, 34)
            .add(IT_OUT, 125, 34).add(IT_OUT, 143, 34).add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 63).add(FL_OUT, 125, 63).add(FL_OUT, 143, 63));

    public static GuiProperties MULTI_DISPLAY_FLUID = new GuiProperties(GTCore.ID, "multi_display_fluid").setSlots(ISlotProvider.DEFAULT()
            .add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 7).add(FL_OUT, 125, 7).add(FL_OUT, 143, 7)
            .add(FL_OUT, 107, 25).add(FL_OUT, 125, 25).add(FL_OUT, 143, 25)
            .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
            .add(FL_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61));

    public static void init(){
        GTCoreBlocks.WOOD_ITEM_BARREL.add(DISPLAY, 53, 34).add(SlotTypes.UNLIMITED, 71, 34);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                GTCoreBlocks.ENDER_GARBAGE_BIN.add("", new SlotData<>(STORAGE, 62 + (x * 18), 17 + (y * 18), GTCoreGuiTextures.BLANK_SLOT, null));
            }
        }
        IPanelFunction oldFunction = GTCoreBlocks.ENDER_GARBAGE_BIN.getBackgroundFunction();;
        GTCoreBlocks.ENDER_GARBAGE_BIN.setBackgroundFunction((modularPanel, machine, guiData, syncManager, settings) -> {
            oldFunction.modifyPanel(modularPanel, machine, guiData, syncManager, settings);
            modularPanel.child(GTCoreGuiTextures.TRASH_CAN.asWidget().pos(56, 4).size(64, 68));
        });
        GTCoreBlocks.ENDER_GARBAGE_BIN.getGuiProperties().setTheme("mui:test_theme");
    }
}
