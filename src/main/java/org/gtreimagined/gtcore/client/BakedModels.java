package org.gtreimagined.gtcore.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.client.model.BookShelfModelLoader;
import org.gtreimagined.gtcore.client.model.IconModelLoader;
import org.gtreimagined.gtcore.client.model.SapBagBakedModel;
import org.gtreimagined.gtlib.client.GTLibModelManager;
import org.gtreimagined.gtlib.client.model.loader.DynamicModelLoader;
import org.gtreimagined.gtlib.dynamic.DynamicModel;

import java.util.function.Function;

public class BakedModels {
    public static final ResourceLocation LOADER_SAP_BAG = new ResourceLocation(GTCore.ID, "sap_bag");
    public static final ResourceLocation LOADER_ICON = new ResourceLocation(GTCore.ID, "icon");
    public static final ResourceLocation LOADER_BOOKSHELF = new ResourceLocation(GTCore.ID, "bookshelf");

    public static void init() {
        GTLibModelManager.registerStaticConfigMap("sap_bag", () -> SapBagBakedModel.CONFIGS);
        new IconModelLoader(LOADER_ICON);
        new BookShelfModelLoader(LOADER_BOOKSHELF);
        new DynamicModelLoader(LOADER_SAP_BAG) {
            @Override
            public DynamicModel read(JsonObject json, JsonDeserializationContext context) {
                return new DynamicModel(super.read(json, context)) {
                    @Override
                    public BakedModel bakeModel(IGeometryBakingContext configuration, ModelBakery bakery, Function<Material, TextureAtlasSprite> getter, ModelState transform, ItemOverrides overrides, ResourceLocation loc) {
                        return new SapBagBakedModel(getter.apply(new Material(InventoryMenu.BLOCK_ATLAS, particle)), getBakedConfigs(configuration, bakery, getter, transform, overrides, loc));
                    }
                };
            }
        };
    }
}
