package org.gtreimagined.gtcore.mui;

import brachy.modularui.ModularUI;
import brachy.modularui.api.IThemeApi;
import brachy.modularui.theme.ReloadThemeEvent;
import brachy.modularui.theme.SelectableTheme;
import brachy.modularui.theme.ThemeBuilder;
import brachy.modularui.theme.WidgetThemeKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures.IDs;

@EventBusSubscriber(modid = GTCore.ID, bus = Bus.FORGE)
public class GTCoreThemes {
    public static final String BRONZE_THEME_ID = GTCore.ID + ":bronze";
    public static final String STEEL_THEME_ID = GTCore.ID + ":steel";
    public static final String PRIMITIVE_THEME_ID = GTCore.ID + ":primitive";
    public static final ThemeBuilder<?> BRONZE_THEME = new ThemeBuilder<>(BRONZE_THEME_ID)
            .background(IThemeApi.PANEL, IDs.BRONZE_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, IDs.BRONZE_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, IDs.BRONZE_FLUID_SLOT)
            .background(IThemeApi.BUTTON, IDs.BRONZE_BUTTON)
            .background(IThemeApi.CLOSE_BUTTON, IDs.BRONZE_BUTTON)
            .widgetTheme(IThemeApi.TOGGLE_BUTTON, new SelectableTheme.Builder<>().background(IDs.BRONZE_BUTTON).selectedBackground(IDs.BRONZE_BUTTON_PRESSED));
    public static final ThemeBuilder<?> STEEL_THEME = new ThemeBuilder<>(STEEL_THEME_ID)
            .background(IThemeApi.PANEL, IDs.STEEL_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, IDs.STEEL_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, IDs.STEEL_FLUID_SLOT)
            .background(IThemeApi.BUTTON, IDs.STEEL_BUTTON)
            .background(IThemeApi.CLOSE_BUTTON, IDs.STEEL_BUTTON)
            .widgetTheme(IThemeApi.TOGGLE_BUTTON, new SelectableTheme.Builder<>().background(IDs.STEEL_BUTTON).selectedBackground(IDs.STEEL_BUTTON_PRESSED));
    public static final ThemeBuilder<?> PRIMITIVE_THEME = new ThemeBuilder<>(PRIMITIVE_THEME_ID)
            .background(IThemeApi.PANEL, IDs.PRIMITIVE_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, IDs.PRIMITIVE_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, IDs.PRIMITIVE_FLUID_SLOT)
            .background(IThemeApi.BUTTON, IDs.PRIMITIVE_BUTTON)
            .background(IThemeApi.CLOSE_BUTTON, IDs.PRIMITIVE_BUTTON)
            .widgetTheme(IThemeApi.TOGGLE_BUTTON, new SelectableTheme.Builder<>().background(IDs.PRIMITIVE_BUTTON).selectedBackground(IDs.PRIMITIVE_BUTTON_PRESSED));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onThemeReload(ReloadThemeEvent.Pre event) {
        registerThemes();
    }

    public static void registerThemes(){
        IThemeApi.get().registerTheme(BRONZE_THEME);
        IThemeApi.get().registerTheme(STEEL_THEME);
        IThemeApi.get().registerTheme(PRIMITIVE_THEME);
    }
}
