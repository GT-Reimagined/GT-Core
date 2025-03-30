package org.gtreimagined.gtcore.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.WorkbenchMachine;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTLib;
import org.gtreimagined.gtlib.Ref;

import java.util.Optional;

@JeiPlugin
public class GTCoreJeiPlugin implements IModPlugin {
    public GTCoreJeiPlugin(){
        GTCore.LOGGER.debug("GTCoreJEIPlugin created");
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(GTCore.ID, "jei_plugin");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        if (GTAPI.isModLoaded(Ref.MOD_REI)) return;
        registration.addRecipeTransferHandler(new GTCoreRecipeTransferInfo());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (GTAPI.isModLoaded(Ref.MOD_REI)) return;
        /*AntimatterAPI.all(WorkbenchMachine.class).forEach(m -> {
            m.getTiers().forEach(t -> {
                registration.addRecipeCatalyst(new ItemStack(m.getItem(t)), RecipeTypes.CRAFTING);
            });
        });*/
        Optional<WorkbenchMachine> machine = GTAPI.all(WorkbenchMachine.class).stream().findFirst();
        if (machine.isPresent()){
            registration.addRecipeCatalyst(new ItemStack(machine.get().getItem(machine.get().getFirstTier())), RecipeTypes.CRAFTING);
        }
    }

}
