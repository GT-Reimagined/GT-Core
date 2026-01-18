package org.gtreimagined.gtcore.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.client.model.loader.GTModelLoader;

public class BookShelfModelLoader extends GTModelLoader<BookShelfModel> {
    public BookShelfModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public BookShelfModel read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject copy = jsonObject.deepCopy();
        copy.remove("loader");
        UnbakedModel model = jsonDeserializationContext.deserialize(copy, BlockModel.class);
        return new BookShelfModel(model);
    }
}
