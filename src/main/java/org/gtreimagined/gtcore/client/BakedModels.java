package org.gtreimagined.gtcore.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import muramasa.antimatter.client.AntimatterModelManager;
import muramasa.antimatter.client.model.loader.DynamicModelLoader;
import muramasa.antimatter.dynamic.DynamicModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.IModelConfiguration;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.client.model.IconModelLoader;
import org.gtreimagined.gtcore.client.model.SapBagBakedModel;

import java.util.function.Function;

public class BakedModels {
    public static final ResourceLocation LOADER_SAP_BAG = new ResourceLocation(GTCore.ID, "sap_bag");
    public static final ResourceLocation LOADER_ICON = new ResourceLocation(GTCore.ID, "icon");

    public static void init() {
        AntimatterModelManager.registerStaticConfigMap("sap_bag", () -> SapBagBakedModel.CONFIGS);
        new IconModelLoader(LOADER_ICON);
        new DynamicModelLoader(LOADER_SAP_BAG) {
            @Override
            public DynamicModel read(JsonDeserializationContext context, JsonObject json) {
                return new DynamicModel(super.read(context, json)) {
                    @Override
                    public BakedModel bakeModel(IModelConfiguration configuration, ModelBakery bakery, Function<Material, TextureAtlasSprite> getter, ModelState transform, ItemOverrides overrides, ResourceLocation loc) {
                        return new SapBagBakedModel(getter.apply(new Material(InventoryMenu.BLOCK_ATLAS, particle)), getBakedConfigs(configuration, bakery, getter, transform, overrides, loc));
                    }
                };
            }
        };
    }
}
