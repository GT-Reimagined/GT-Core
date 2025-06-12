package org.gtreimagined.gtcore.client.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import org.gtreimagined.gtlib.client.IGTModel;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class BookShelfModel implements IGTModel<BookShelfModel> {
    @Override
    public BakedModel bakeModel(IModelConfiguration iModelConfiguration, ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides itemOverrides, ResourceLocation resourceLocation) {
        return null;
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration iModelConfiguration, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        return List.of();
    }
}
