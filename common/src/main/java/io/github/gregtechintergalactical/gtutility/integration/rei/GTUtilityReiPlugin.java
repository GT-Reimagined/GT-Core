package io.github.gregtechintergalactical.gtutility.integration.rei;

import io.github.gregtechintergalactical.gtutility.gui.ContainerWorkbench;
import io.github.gregtechintergalactical.gtutility.machine.WorkbenchMachine;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import muramasa.antimatter.AntimatterAPI;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GTUtilityReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        List<EntryStack<?>> list = new ArrayList<>();
        AntimatterAPI.all(WorkbenchMachine.class).forEach(w -> {
            w.getTiers().forEach(t -> {
                list.add(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(w.getItem(t))));
            });
        });
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryIngredient.of(list));

    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        //registry.register(new GTUtilityWorkbenchHandler());
    }

    public static class GTUtilityWorkbenchHandler implements TransferHandler {

        @Override
        public Result handle(Context context) {
            if(!(context.getDisplay() instanceof DefaultCraftingDisplay<?> display)) return Result.createNotApplicable();
            if (!(context.getMenu() instanceof ContainerWorkbench<?> workbench)) return Result.createNotApplicable();
            return Result.createSuccessful().blocksFurtherHandling();
        }
    }
}
