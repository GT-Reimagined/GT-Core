package org.gtreimagined.gtcore.client.model;

import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.Pair;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityBookShelf;
import org.gtreimagined.gtcore.client.BookSpriteMaps;
import org.gtreimagined.gtcore.client.ReTexturedQuad;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.RenderHelper;
import org.gtreimagined.gtlib.client.SimpleModelState;
import org.gtreimagined.gtlib.client.baked.GTBakedModel;
import org.gtreimagined.gtlib.gui.SlotType;
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
                for (int j = 0; j < 7; j++) {
                    List<BakedQuad> bakedQuads = new ArrayList<>();
                    Vector3f from = new Vector3f(element.from.x() + (j * 2), element.from.y(), element.from.z());
                    Vector3f to  = new Vector3f(element.to.x() + (j * 2), element.to.y(), element.to.z());
                    for (var entry : element.faces.entrySet()) {
                        Direction dir = entry.getKey();
                        BlockElementFace face = entry.getValue();
                        TextureAtlasSprite sprite = dir == Direction.SOUTH ? BookSpriteMaps.getSpriteMap().get(Items.BOOK).first() : BookSpriteMaps.getSpriteMap().get(Items.BOOK).second();
                        BakedQuad quad = FACE_BAKERY.bakeQuad(from, to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(Direction.SOUTH)), element.rotation, element.shade, new ResourceLocation(GTCore.ID, "bookshelf"));
                        bakedQuads.add(quad);
                    }
                    BOOK_MODELS.add(bakedQuads);
                }
            }
            for (int i = 0; i < BookShelfModelLoader.BOOK_REFERENCE_NORTH.size(); i++) {
                BlockElement element = BookShelfModelLoader.BOOK_REFERENCE_NORTH.get(i);
                for (int j = 0; j < 7; j++) {
                    List<BakedQuad> bakedQuads = new ArrayList<>();
                    Vector3f from = new Vector3f(element.from.x() - (j * 2), element.from.y(), element.from.z());
                    Vector3f to  = new Vector3f(element.to.x() - (j * 2), element.to.y(), element.to.z());
                    for (var entry : element.faces.entrySet()) {
                        Direction dir = entry.getKey();
                        BlockElementFace face = entry.getValue();
                        TextureAtlasSprite sprite = dir == Direction.NORTH ? BookSpriteMaps.getSpriteMap().get(Items.ENCHANTED_BOOK).first() : BookSpriteMaps.getSpriteMap().get(Items.ENCHANTED_BOOK).second();
                        BakedQuad quad = FACE_BAKERY.bakeQuad(from, to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(Direction.SOUTH)), element.rotation, element.shade, new ResourceLocation(GTCore.ID, "bookshelf"));
                        bakedQuads.add(quad);
                    }
                    BOOK_MODELS.add(bakedQuads);
                }
            }
        }
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction direction, @NotNull Random rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> quads = new ObjectArrayList<>();
        quads.addAll(ModelUtils.getQuadsFromBaked(baseModel, state, direction, rand, level, pos));
        if (direction != Direction.SOUTH && direction != Direction.NORTH) return quads;
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof BlockEntityBookShelf bookShelf)) return quads;
        int start = direction == Direction.SOUTH ? 0 : 14;
        int end = direction == Direction.SOUTH ? 14 : 28;
        for (int i = start; i < end; i++) {
            int finalI = i;
            ItemStack book = bookShelf.itemHandler.map(itemHandler -> itemHandler.getHandler(SlotType.STORAGE).getStackInSlot(finalI)).orElse(ItemStack.EMPTY);
            if (book.isEmpty() || !BookSpriteMaps.getSpriteMap().containsKey(book.getItem())) continue;
            Pair<TextureAtlasSprite, TextureAtlasSprite> spritePair = BookSpriteMaps.getSpriteMap().get(book.getItem());
            if (spritePair == null) continue;
            List<BakedQuad> list = BOOK_MODELS.get(i);
            for (BakedQuad quad : list) {
                TextureAtlasSprite sprite = quad.getDirection() == Direction.SOUTH || quad.getDirection() == Direction.NORTH ? spritePair.first() : spritePair.second();
                quads.add(new ReTexturedQuad(sprite, quad));
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
