package org.gtreimagined.gtcore.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import muramasa.antimatter.client.ModelUtils;
import muramasa.antimatter.client.model.loader.AntimatterModelLoader;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.gtreimagined.gtcore.mixin.ModelBakeryAccessor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IconModelLoader extends AntimatterModelLoader<IconModel> {
    public IconModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public IconModel readModel(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
        JsonObject copy = jsonObject.deepCopy();
        copy.remove("loader");
        UnbakedModel model = jsonDeserializationContext.deserialize(copy, BlockModel.class);
        ResourceLocation parent = new ResourceLocation(copy.get("parent").getAsString());
        List<BlockElement> blockElements = new ObjectArrayList<>();
        try {
            Resource resource = ((ModelBakeryAccessor)ModelUtils.INSTANCE.getModelBakery()).getResourceManager().getResource(new ResourceLocation(parent.getNamespace(), "models/" + parent.getPath() + ".json"));
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = Streams.parse(jsonReader);
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                JsonArray numberElementJsons = obj.get("numberElements").getAsJsonArray();
                JsonObject newObj = new JsonObject();
                newObj.add("elements", numberElementJsons);
                UnbakedModel numberModel = jsonDeserializationContext.deserialize(newObj, BlockModel.class);
                if (numberModel instanceof BlockModel blockModel){
                    blockElements.addAll(blockModel.getElements());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IconModel(model, blockElements);
    }
}
