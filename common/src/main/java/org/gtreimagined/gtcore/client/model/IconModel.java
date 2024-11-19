package org.gtreimagined.gtcore.client.model;

import com.mojang.datafixers.util.Pair;
import muramasa.antimatter.client.IAntimatterModel;
import muramasa.antimatter.client.model.IModelConfiguration;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class IconModel implements IAntimatterModel {
    private final UnbakedModel baseModel;
    private final List<BlockElement> blockElements;

    public IconModel(UnbakedModel baseModel, List<BlockElement> blockElements){
        this.baseModel = baseModel;
        this.blockElements = blockElements;
    }

    @Override
    public BakedModel bakeModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        BakedModel base = baseModel.bake(modelBakery, function, modelState, resourceLocation);
        return new IconBakedModel(Objects.requireNonNull(base), blockElements);
    }

    @Override
    public Collection<Material> getMaterials(IModelConfiguration iModelConfiguration, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        return baseModel.getMaterials(function, set);
    }
}
