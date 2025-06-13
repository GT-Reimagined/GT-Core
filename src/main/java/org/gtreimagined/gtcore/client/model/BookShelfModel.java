package org.gtreimagined.gtcore.client.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.client.BookSpriteMaps;
import org.gtreimagined.gtlib.client.IGTModel;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.texture.Texture;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookShelfModel implements IGTModel<BookShelfModel> {
    private final UnbakedModel baseModel;

    public BookShelfModel(UnbakedModel baseModel){
        this.baseModel = baseModel;
        BookSpriteMaps.initMaterialMap();
    }

    @Override
    public BakedModel bakeModel(IModelConfiguration configuration, ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides overrides, ResourceLocation resourceLocation) {
        BakedModel base = baseModel.bake(modelBakery, function, modelState, resourceLocation);
        return new BookShelfBakedModel(Objects.requireNonNull(base));
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration iModelConfiguration, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        Set<Material> materials = new HashSet<>();
        materials.addAll(baseModel.getMaterials(function, set));
        materials.addAll(BookSpriteMaps.getMaterialMap().values().stream().flatMap(p -> Stream.of(p.first(), p.second())).collect(Collectors.toSet()));
        return materials;
    }
}
