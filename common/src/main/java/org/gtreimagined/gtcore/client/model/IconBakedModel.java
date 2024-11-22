package org.gtreimagined.gtcore.client.model;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import muramasa.antimatter.Ref;
import muramasa.antimatter.client.ModelUtils;
import muramasa.antimatter.client.RenderHelper;
import muramasa.antimatter.client.SimpleModelState;
import muramasa.antimatter.client.baked.AntimatterBakedModel;
import muramasa.antimatter.texture.Texture;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class IconBakedModel extends AntimatterBakedModel<IconBakedModel> {
    BakedModel baseModel;

    public IconBakedModel(BakedModel baseModel) {
        super(baseModel.getParticleIcon());
        this.baseModel = baseModel;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction direction, @NotNull Random rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> quads = new ObjectArrayList<>();
        quads.addAll(ModelUtils.INSTANCE.getQuadsFromBaked(baseModel, state, direction, rand, level, pos));
        if (direction != Direction.SOUTH) return quads;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityMassStorage massStorage){
            int offset;
            if (massStorage.getMaxLimit() <= 10000) offset = 1;
            else {
                offset = 0;
            }
            int amount = massStorage.getItemAmount();
            if (amount > 0){
                String number = amount == massStorage.getMaxLimit() ? "100%" : Integer.toString(amount);
                for (int i = 0; i < number.length(); i++) {
                    char c = number.charAt(number.length() - (i + 1));
                    Map<String, List<BakedQuad>> map = IconModel.ICON_MODELS.get(i + offset);
                    quads.addAll(map.get(c == '%' ? "percent" : Character.toString(c)));
                }
            }
        }
        return quads;
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
