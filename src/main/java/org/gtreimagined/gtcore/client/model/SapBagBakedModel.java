package org.gtreimagined.gtcore.client.model;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import org.gtreimagined.gtlib.dynamic.DynamicBakedModel;

public class SapBagBakedModel extends DynamicBakedModel {
    public static final Int2ObjectOpenHashMap<BakedModel[]> CONFIGS = new Int2ObjectOpenHashMap<>();

    public SapBagBakedModel(TextureAtlasSprite particle, Int2ObjectOpenHashMap<BakedModel[]> map) {
        super(particle, map);
        onlyGeneralQuads();
    }
}
