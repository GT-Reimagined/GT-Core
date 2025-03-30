package org.gtreimagined.gtcore.data.client;

import net.minecraft.client.gui.screens.MenuScreens;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import org.gtreimagined.gtcore.gui.screen.ScreenChargingMaterialBlock;
import org.gtreimagined.gtlib.GTAPI;

public class ScreenFactories {
    public final static MenuScreens.ScreenConstructor SCREEN_WORKBENCH = GTAPI.register(MenuScreens.ScreenConstructor.class, "workbench", GTCore.ID,(MenuScreens.ScreenConstructor)(a, b, c) -> new ScreenChargingMaterialBlock<>((ContainerWorkbench) a,b,c, "workbench"));

    public static void init(){}
}
