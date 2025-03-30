package org.gtreimagined.gtcore.integration.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import muramasa.antimatter.AntimatterAPI;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import org.gtreimagined.gtcore.machine.WorkbenchMachine;
import org.gtreimagined.gtlib.GTAPI;

import java.util.ArrayList;
import java.util.List;

@REIPluginClient
public class GTCoreReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        List<EntryStack<?>> list = new ArrayList<>();
        GTAPI.all(WorkbenchMachine.class).forEach(w -> {
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
