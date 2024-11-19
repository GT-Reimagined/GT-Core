package org.gtreimagined.gtcore.client.model;

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

import java.util.List;
import java.util.Random;

public class IconBakedModel extends AntimatterBakedModel<IconBakedModel> {
    BakedModel baseModel;
    List<BlockElement> numberElements;
    public static final FaceBakery FACE_BAKERY = new FaceBakery();

    public IconBakedModel(BakedModel baseModel, List<BlockElement> numberElements) {
        super(baseModel.getParticleIcon());
        this.baseModel = baseModel;
        this.numberElements = numberElements;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction direction, @NotNull Random rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> quads = new ObjectArrayList<>();
        for (Direction dir : Ref.DIRS) {
            quads.addAll(ModelUtils.INSTANCE.getQuadsFromBaked(baseModel, state, dir, rand, level, pos));
        }
        quads.addAll(ModelUtils.INSTANCE.getQuadsFromBaked(baseModel, state, null, rand, level, pos));
        if (numberElements.isEmpty()) return quads;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityMassStorage massStorage){
            int offset = 0;
            if (massStorage.getMaxLimit() <= 10000) offset = 1;
            int amount = massStorage.getItemAmount();
            if (amount > 0){
                String number = amount == massStorage.getMaxLimit() ? "100%" : Integer.toString(amount);
                for (int i = 0; i < number.length(); i++) {
                    char c = number.charAt(number.length() - (i + 1));
                    BlockElement element = numberElements.get(i + offset);
                    for (var entry : element.faces.entrySet()){
                        Direction dir = entry.getKey();
                        BlockElementFace face = entry.getValue();
                        TextureAtlasSprite sprite = ModelUtils.getDefaultTextureGetter().apply(ModelUtils.getBlockMaterial(new Texture(GTCore.ID, "block/characters/" + (c == '%' ? "percent" : c))));
                        BakedQuad quad = FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(state)), element.rotation, element.shade, massStorage.getMassStorageMachine().getLoc());
                        quads.add(quad);
                    }
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
