package io.github.gregtechintergalactical.gtcore.fabric;

import io.github.gregtechintergalactical.gtcore.GTCore;
import muramasa.antimatter.event.fabric.CraftingEvents;
import muramasa.antimatter.event.fabric.LoaderEvents;
import muramasa.antimatter.event.fabric.ProviderEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.Beeswax;
import static muramasa.antimatter.data.AntimatterMaterialTypes.DUST;

//initializer class for non antimatter related stuff
public class GTCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ProviderEvents.PROVIDERS.register(GTCore::onProviders);
        CraftingEvents.CRAFTING.register(GTCore::onCrafting);
        LoaderEvents.LOADER.register(GTCore::registerRecipeLoaders);
        UseBlockCallback.EVENT.register((player, level, interactionHand, hitResult) -> {
            if (player.getItemInHand(interactionHand).is(DUST.getMaterialTag(Beeswax))){
                return Items.HONEYCOMB.useOn(new UseOnContext(player, interactionHand, hitResult));
            }
            return InteractionResult.PASS;
        });
    }
}
