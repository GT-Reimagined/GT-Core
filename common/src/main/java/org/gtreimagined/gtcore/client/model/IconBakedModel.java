package org.gtreimagined.gtcore.client.model;

import muramasa.antimatter.client.baked.AntimatterBakedModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class IconBakedModel extends AntimatterBakedModel<IconBakedModel> {
    public IconBakedModel(TextureAtlasSprite p) {
        super(p);
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState blockState, @Nullable Direction direction, @NotNull Random random, @NotNull BlockAndTintGetter blockAndTintGetter, @NotNull BlockPos blockPos) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @NotNull
    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
