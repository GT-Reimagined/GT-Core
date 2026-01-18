package org.gtreimagined.gtcore.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.client.BookSpriteMaps;
import org.gtreimagined.gtlib.client.IGTModel;
import org.gtreimagined.gtlib.client.ModelUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class BookShelfModel implements IGTModel<BookShelfModel> {
    static List<BlockElement> BOOK_REFERENCE_SOUTH = null;
    static List<BlockElement> BOOK_REFERENCE_NORTH = null;
    private final UnbakedModel baseModel;
    static List<List<BakedQuad>> BOOK_MODELS = null;

    public BookShelfModel(UnbakedModel baseModel){
        this.baseModel = baseModel;
    }

    @Override
    public BakedModel bakeModel(IGeometryBakingContext configuration, ModelBaker modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides overrides, ResourceLocation resourceLocation) {
        if (BOOK_REFERENCE_SOUTH == null) {
            try {
                BOOK_REFERENCE_SOUTH = new ArrayList<>();
                BlockModel numberModel = ModelUtils.getModelBakery().loadBlockModel(new ResourceLocation(GTCore.ID, "block/machine/overlay/bookshelf/book_south"));
                BOOK_REFERENCE_SOUTH.addAll(numberModel.getElements());
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }
        if (BOOK_REFERENCE_NORTH == null) {
            try {
                BOOK_REFERENCE_NORTH = new ArrayList<>();
                BlockModel numberModel = ModelUtils.getModelBakery().loadBlockModel(new ResourceLocation(GTCore.ID, "block/machine/overlay/bookshelf/book_north"));
                BOOK_REFERENCE_NORTH.addAll(numberModel.getElements());
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }
        BakedModel base = baseModel.bake(modelBakery, function, modelState, resourceLocation);
        return new BookShelfBakedModel(Objects.requireNonNull(base));
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
        baseModel.resolveParents(modelGetter);
    }
}
