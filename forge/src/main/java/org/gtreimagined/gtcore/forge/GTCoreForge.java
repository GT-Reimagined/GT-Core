package org.gtreimagined.gtcore.forge;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import muramasa.antimatter.event.forge.AntimatterCraftingEvent;
import muramasa.antimatter.event.forge.AntimatterLoaderEvent;
import muramasa.antimatter.event.forge.AntimatterProvidersEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.proxy.ClientHandler;
import org.gtreimagined.gtcore.tree.RubberFoliagePlacer;

import static muramasa.antimatter.data.AntimatterMaterialTypes.DUST;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.Beeswax;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GTCore.ID)
public class GTCoreForge extends GTCore {
    public GTCoreForge(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onProvidersEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCraftingEvent);
        MinecraftForge.EVENT_BUS.<AntimatterLoaderEvent>addListener(GTCoreForge::registerRecipeLoaders);
        MinecraftForge.EVENT_BUS.addListener(this::onChunkWatch);
        MinecraftForge.EVENT_BUS.addListener(this::onItemUse);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(FoliagePlacerType.class, this::onRegistration);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onStitch);
            TerraformBoatClientHelper.registerModelLayer(new ResourceLocation(GTCore.ID, "rubber"));
        });
    }

    private void onChunkWatch(ChunkWatchEvent.Watch event){
        /*event.getWorld().getChunk(event.getPos().x, event.getPos().z).getBlockEntities().values().forEach(b -> {
            if (b instanceof BlockEntityMassStorage storage && storage.isServerSide() && !storage.isRemoved()){
                storage.setSyncSlots(true);
            }
        });*/
    }

    private void onItemUse(PlayerInteractEvent.RightClickBlock event){
        if (event.getPlayer().getItemInHand(event.getHand()).is(DUST.getMaterialTag(Beeswax))){
            event.setCancellationResult(Items.HONEYCOMB.useOn(new UseOnContext(event.getPlayer(), event.getHand(), event.getHitVec())));
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event){
        ClientHandler.init();
    }

    @OnlyIn(Dist.CLIENT)
    private void onStitch(TextureStitchEvent.Pre event) {
        ClientHandler.onStitch(event.getAtlas(), event::addSprite);
    }

    private void onProvidersEvent(AntimatterProvidersEvent event){
        onProviders(event.event);
    }

    private void onCraftingEvent(AntimatterCraftingEvent event){
        onCrafting(event.getEvent());
    }

    public static void registerRecipeLoaders(AntimatterLoaderEvent event) {
        GTCore.registerRecipeLoaders(event.sender, event.registrat);
    }

    private void onRegistration(final RegistryEvent.Register<FoliagePlacerType<?>> e){
        e.getRegistry().register(RubberFoliagePlacer.RUBBER.setRegistryName(GTCore.ID, "rubber_foilage_placer"));
    }
}
