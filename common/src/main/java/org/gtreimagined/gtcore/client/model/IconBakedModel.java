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
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class IconBakedModel extends AntimatterBakedModel<IconBakedModel> {
    BakedModel baseModel;
    List<BlockElement> numberElements;
    public static final FaceBakery FACE_BAKERY = new FaceBakery();
    static Cache<String, List<BakedQuad>> MASS_STORAGE_CACHE = CacheBuilder.newBuilder().maximumSize(10000).build();
    static Cache<String, List<BakedQuad>> ITEM_STORAGE_CACHE = CacheBuilder.newBuilder().maximumSize(1000).build();

    public IconBakedModel(BakedModel baseModel, List<BlockElement> numberElements) {
        super(baseModel.getParticleIcon());
        this.baseModel = baseModel;
        this.numberElements = numberElements;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction direction, @NotNull Random rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> quads = new ObjectArrayList<>();
        quads.addAll(ModelUtils.INSTANCE.getQuadsFromBaked(baseModel, state, direction, rand, level, pos));
        if (numberElements.isEmpty() || direction == null) return quads;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityMassStorage massStorage){
            int offset;
            if (massStorage.getMaxLimit() <= 10000) offset = 1;
            else {
                offset = 0;
            }
            Cache<String, List<BakedQuad>> cache = offset == 0 ? MASS_STORAGE_CACHE : ITEM_STORAGE_CACHE;
            int amount = massStorage.getItemAmount();
            if (amount > 0){
                String number = amount == massStorage.getMaxLimit() ? "100%" : Integer.toString(amount);
                try {
                    quads.addAll(cache.get(number, () -> {
                        List<BakedQuad> bakedQuads = new ObjectArrayList<>();
                        for (int i = 0; i < number.length(); i++) {
                            char c = number.charAt(number.length() - (i + 1));
                            BlockElement element = numberElements.get(i + offset);
                            for (var entry : element.faces.entrySet()){
                                Direction dir = entry.getKey();
                                if (dir != direction) continue;
                                BlockElementFace face = entry.getValue();
                                TextureAtlasSprite sprite = ModelUtils.getDefaultTextureGetter().apply(IconModel.TEXTURE_MAP.get(c == '%' ? "percent" : Character.toString(c)));
                                BakedQuad quad = FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(direction)), element.rotation, element.shade, massStorage.getMassStorageMachine().getLoc());
                                bakedQuads.add(quad);
                            }
                        }
                        return bakedQuads;
                    }));
                } catch (ExecutionException e) {
                    GTCore.LOGGER.error(e);
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
