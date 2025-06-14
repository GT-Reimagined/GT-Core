package org.gtreimagined.gtcore.client.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.client.BookSpriteMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.RenderHelper;
import org.gtreimagined.gtlib.client.SimpleModelState;
import org.gtreimagined.gtlib.client.baked.GTBakedModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.gtreimagined.gtcore.client.model.BookShelfModel.BOOK_MODELS;
import static org.gtreimagined.gtcore.client.model.IconModel.FACE_BAKERY;

public class BookShelfBakedModel extends GTBakedModel<BookShelfBakedModel> {
    BakedModel baseModel;

    public BookShelfBakedModel(BakedModel baseModel) {
        super(baseModel.getParticleIcon());
        this.baseModel = baseModel;
        if (BOOK_MODELS == null){
            BOOK_MODELS = new ObjectArrayList<>();
            for (int i = 0; i < BookShelfModelLoader.BOOK_REFERENCE_SOUTH.size(); i++) {
                BlockElement element = BookShelfModelLoader.BOOK_REFERENCE_SOUTH.get(i);
                List<BakedQuad> bakedQuads = new ArrayList<>();
                for (var entry : element.faces.entrySet()) {
                    Direction dir = entry.getKey();
                    BlockElementFace face = entry.getValue();
                    TextureAtlasSprite sprite = dir == Direction.NORTH || dir == Direction.SOUTH ? BookSpriteMaps.getSpriteMap().get(GTCoreItems.SELECTOR_TAG_ITEMS.get(0)).first() : BookSpriteMaps.getSpriteMap().get(GTCoreItems.SELECTOR_TAG_ITEMS.get(0)).second();
                    BakedQuad quad = FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(Direction.SOUTH)), element.rotation, element.shade, new ResourceLocation(GTCore.ID, "bookshelf"));
                    bakedQuads.add(quad);
                }
                BOOK_MODELS.add(bakedQuads);
            }
        }
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction direction, @NotNull Random rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> quads = new ObjectArrayList<>();
        quads.addAll(ModelUtils.getQuadsFromBaked(baseModel, state, direction, rand, level, pos));
        if (direction != Direction.SOUTH && direction != Direction.NORTH) return quads;
        BlockEntity be = level.getBlockEntity(pos);
        for (var list : BOOK_MODELS){
            quads.addAll(list);
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
