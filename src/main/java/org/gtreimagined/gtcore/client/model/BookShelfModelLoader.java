package org.gtreimagined.gtcore.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.model.loader.GTModelLoader;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookShelfModelLoader extends GTModelLoader<BookShelfModel> {
    static List<BlockElement> BOOK_REFERENCE_SOUTH = null;
    static List<BlockElement> BOOK_REFERENCE_NORTH = null;
    public BookShelfModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public BookShelfModel read(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
        JsonObject copy = jsonObject.deepCopy();
        copy.remove("loader");
        UnbakedModel model = jsonDeserializationContext.deserialize(copy, BlockModel.class);
        if (BOOK_REFERENCE_SOUTH == null) {
            try {
                BOOK_REFERENCE_SOUTH = new ArrayList<>();
                Resource resource = ModelUtils.getModelBakery().resourceManager.getResource(new ResourceLocation(GTCore.ID, "models/block/machine/overlay/bookshelf/book_south.json"));
                InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                JsonReader jsonReader = new JsonReader(reader);
                JsonElement element = Streams.parse(jsonReader);
                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    UnbakedModel numberModel = jsonDeserializationContext.deserialize(obj, BlockModel.class);
                    if (numberModel instanceof BlockModel blockModel){
                        BOOK_REFERENCE_SOUTH.addAll(blockModel.getElements());
                    }
                }
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }
        if (BOOK_REFERENCE_NORTH == null) {
            try {
                BOOK_REFERENCE_NORTH = new ArrayList<>();
                Resource resource = ModelUtils.getModelBakery().resourceManager.getResource(new ResourceLocation(GTCore.ID, "models/block/machine/overlay/bookshelf/book_north.json"));
                InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                JsonReader jsonReader = new JsonReader(reader);
                JsonElement element = Streams.parse(jsonReader);
                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    UnbakedModel numberModel = jsonDeserializationContext.deserialize(obj, BlockModel.class);
                    if (numberModel instanceof BlockModel blockModel){
                        BOOK_REFERENCE_NORTH.addAll(blockModel.getElements());
                    }
                }
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }
        return new BookShelfModel(model);
    }
}
