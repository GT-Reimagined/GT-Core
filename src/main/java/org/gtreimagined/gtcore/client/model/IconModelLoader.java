package org.gtreimagined.gtcore.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import java.util.List;

public class IconModelLoader extends GTModelLoader<IconModel> {
    static List<BlockElement> BLOCK_ELEMENTS = null;
    public IconModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public IconModel read(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
        JsonObject copy = jsonObject.deepCopy();
        copy.remove("loader");
        UnbakedModel model = jsonDeserializationContext.deserialize(copy, BlockModel.class);
        if (BLOCK_ELEMENTS == null) {
            BLOCK_ELEMENTS = new ObjectArrayList<>();
            try {
                Resource resource = ModelUtils.getModelBakery().resourceManager.getResource(new ResourceLocation(GTCore.ID, "models/block/machine/overlay/mass_storage/icons.json"));
                InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                JsonReader jsonReader = new JsonReader(reader);
                JsonElement element = Streams.parse(jsonReader);
                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    UnbakedModel numberModel = jsonDeserializationContext.deserialize(obj, BlockModel.class);
                    if (numberModel instanceof BlockModel blockModel){
                        BLOCK_ELEMENTS.addAll(blockModel.getElements());
                    }
                }
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }

        return new IconModel(model, BLOCK_ELEMENTS);
    }
}
