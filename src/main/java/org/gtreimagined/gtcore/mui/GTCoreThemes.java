package org.gtreimagined.gtcore.mui;

import brachy.modularui.ModularUI;
import brachy.modularui.api.IThemeApi;
import brachy.modularui.theme.ReloadThemeEvent;
import brachy.modularui.theme.ThemeBuilder;
import brachy.modularui.theme.WidgetThemeKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.gtreimagined.gtcore.GTCore;

@EventBusSubscriber(modid = GTCore.ID, bus = Bus.FORGE)
public class GTCoreThemes {
    public static final String BRONZE_THEME_ID = GTCore.ID + ":bronze";
    public static final String STEEL_THEME_ID = GTCore.ID + ":steel";
    public static final String PRIMITIVE_THEME_ID = GTCore.ID + ":primitive";
    public static final ThemeBuilder<?> BRONZE_THEME = new ThemeBuilder<>(BRONZE_THEME_ID)
            .background(IThemeApi.PANEL, GTCoreGuiTextures.BRONZE_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, GTCoreGuiTextures.BRONZE_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, GTCoreGuiTextures.BRONZE_FLUID_SLOT);
    public static final ThemeBuilder<?> STEEL_THEME = new ThemeBuilder<>(STEEL_THEME_ID)
            .background(IThemeApi.PANEL, GTCoreGuiTextures.STEEL_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, GTCoreGuiTextures.STEEL_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, GTCoreGuiTextures.STEEL_FLUID_SLOT);
    public static final ThemeBuilder<?> PRIMITIVE_THEME = new ThemeBuilder<>(PRIMITIVE_THEME_ID)
            .background(IThemeApi.PANEL, GTCoreGuiTextures.PRIMITIVE_BACKGROUND)
            .background(IThemeApi.ITEM_SLOT, GTCoreGuiTextures.PRIMITIVE_ITEM_SLOT)
            .background(IThemeApi.FLUID_SLOT, GTCoreGuiTextures.PRIMITIVE_FLUID_SLOT);

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onThemeReload(ReloadThemeEvent.Pre event) {
        IThemeApi.get().registerTheme(BRONZE_THEME);
        IThemeApi.get().registerTheme(STEEL_THEME);
        IThemeApi.get().registerTheme(PRIMITIVE_THEME);
    }
}
