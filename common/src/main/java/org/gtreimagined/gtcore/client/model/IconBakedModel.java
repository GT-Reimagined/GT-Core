package org.gtreimagined.gtcore.client.model;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
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
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
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
import java.util.function.Function;

import static org.gtreimagined.gtcore.client.model.IconModel.FACE_BAKERY;
import static org.gtreimagined.gtcore.client.model.IconModel.ICON_MODELS;

public class IconBakedModel extends AntimatterBakedModel<IconBakedModel> {
    BakedModel baseModel;

    public IconBakedModel(BakedModel baseModel, Function<Material, TextureAtlasSprite> function) {
        super(baseModel.getParticleIcon());
        this.baseModel = baseModel;
        if (ICON_MODELS == null){
            ICON_MODELS = new ObjectArrayList<>();
            String[] icons = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "percent"};
            for (int i = 0; i < IconModelLoader.BLOCK_ELEMENTS.size(); i++) {
                BlockElement element = IconModelLoader.BLOCK_ELEMENTS.get(i);
                Map<String, List<BakedQuad>> map = new Object2ObjectOpenHashMap<>();
                for (String icon : icons) {
                    if (i > 1 && icon.equals("percent")) continue;
                    List<BakedQuad> bakedQuads = new ArrayList<>();
                    for (var entry : element.faces.entrySet()){
                        Direction dir = entry.getKey();
                        BlockElementFace face = entry.getValue();
                        TextureAtlasSprite sprite = function.apply(IconModel.TEXTURE_MAP.get(icon));
                        BakedQuad quad = FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(Direction.SOUTH)), element.rotation, element.shade, new ResourceLocation(GTCore.ID, "item_storage"));
                        bakedQuads.add(quad);
                    }
                    map.put(icon, bakedQuads);
                }
                ICON_MODELS.add(map);
            }
        }
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
                    Map<String, List<BakedQuad>> map = ICON_MODELS.get(i + offset);
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
