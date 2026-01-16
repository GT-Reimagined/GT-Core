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
import java.util.Optional;

public class IconModelLoader extends GTModelLoader<IconModel> {
    static List<BlockElement> BLOCK_ELEMENTS = null;
    public IconModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public IconModel read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject copy = jsonObject.deepCopy();
        copy.remove("loader");
        UnbakedModel model = jsonDeserializationContext.deserialize(copy, BlockModel.class);
        if (BLOCK_ELEMENTS == null) {
            BLOCK_ELEMENTS = new ObjectArrayList<>();
            try {
                UnbakedModel numberModel = ModelUtils.getModelBakery().loadBlockModel(new ResourceLocation(GTCore.ID, "block/machine/overlay/mass_storage/icons"));
                if (numberModel instanceof BlockModel blockModel){
                    BLOCK_ELEMENTS.addAll(blockModel.getElements());
                }
            } catch (Exception e) {
                GTCore.LOGGER.error(e);
            }
        }

        return new IconModel(model, BLOCK_ELEMENTS);
    }
}
