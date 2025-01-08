package org.gtreimagined.gtcore.data.client;

import muramasa.antimatter.AntimatterAPI;
import net.minecraft.client.gui.screens.MenuScreens;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import org.gtreimagined.gtcore.gui.screen.ScreenChargingMaterialBlock;

public class ScreenFactories {
    public final static MenuScreens.ScreenConstructor SCREEN_WORKBENCH = AntimatterAPI.register(MenuScreens.ScreenConstructor.class, "workbench", GTCore.ID,(MenuScreens.ScreenConstructor)(a, b, c) -> new ScreenChargingMaterialBlock<>((ContainerWorkbench) a,b,c, "workbench"));

    public static void init(){}
}
