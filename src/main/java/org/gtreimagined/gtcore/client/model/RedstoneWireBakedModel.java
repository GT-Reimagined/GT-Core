package org.gtreimagined.gtcore.client.model;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.QuadTransformers;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe;
import org.gtreimagined.gtlib.capability.pipe.PipeCoverHandler;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.baked.GTBakedModel;
import org.gtreimagined.gtlib.client.baked.PipeBakedModel;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.pipe.BlockPipe;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RedstoneWireBakedModel extends GTBakedModel<PipeBakedModel> {
    final BakedModel base;
    final BakedModel baseEnd;
    final BakedModel[] connections;
    final BakedModel[] connectionsEnd;
    List<BakedQuad> baseLit = null;
    List<BakedQuad> baseEndLit = null;
    Map<Direction, List<BakedQuad>> connectionsLit = null;
    Map<Direction, List<BakedQuad>> connectionsEndLit = null;

    public RedstoneWireBakedModel(TextureAtlasSprite p, BakedModel base, BakedModel baseEnd, BakedModel[] connections, BakedModel[] connectionsEnd) {
        super(p);
        this.base = base;
        this.baseEnd = baseEnd;
        this.connections = connections;
        this.connectionsEnd = connectionsEnd;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BlockEntityRedstoneWire<?> pipe)) return Collections.emptyList();
        List<BakedQuad> quads = new ArrayList<>();
        PipeCoverHandler<?> covers = pipe.coverHandler.orElse(null);
        boolean lit = pipe.getPipeSize() == PipeSize.VTINY && pipe.mRedstone > 0;
        if (side == null){
            var baseQuads = getQuadsFromModel(base, state, rand, level, pos);
            if (lit){
                if (baseLit == null) baseLit = copyLitQuads(baseQuads);
                quads.addAll(baseLit);
            } else {
                quads.addAll(baseQuads);
            }
            List<BakedQuad> coverQuads = new LinkedList<>();
            boolean connected = false;
            for (Direction d : Direction.values()) {
                if (pipe.connects(d)){
                    connected = true;
                    var connectionQuads = getQuadsFromModel(connections[d.get3DDataValue()], state, rand, level, pos);
                    if (lit){
                        if (connectionsLit == null) connectionsLit = new Object2ObjectOpenHashMap<>();
                        if (!connectionsLit.containsKey(d)) connectionsLit.put(d, copyLitQuads(connectionQuads));
                        quads.addAll(connectionsLit.get(d));
                    } else {
                        quads.addAll(connectionQuads);
                    }

                }
                if (covers != null && !covers.get(d).isEmpty()){
                    Texture tex = ((BlockPipe<?>) state.getBlock()).getFace();
                    ICover c = covers.get(d);
                    coverQuads = covers.getTexturer(d).getQuads("pipe", coverQuads, state, c,
                            new BaseCover.DynamicKey(d, tex, c.getId()), d.get3DDataValue(), level, pos);
                }
            }
            quads.addAll(coverQuads);
            if (!connected) {
                var baseEndQuads = getQuadsFromModel(baseEnd, state, rand, level, pos);
                if (lit){
                    if (baseEndLit == null) baseEndLit = copyLitQuads(baseEndQuads);
                    quads.addAll(baseEndLit);
                } else {
                    quads.addAll(baseEndQuads);
                }
            }
        } else {
            if (pipe.connects(side) && covers != null && covers.get(side).isEmpty()){
                var connectionsEndQuads = getQuadsFromModel(connectionsEnd[side.get3DDataValue()], state, rand, level, pos);
                if (lit){
                    if (connectionsEndLit == null) connectionsEndLit = new Object2ObjectOpenHashMap<>();
                    if (!connectionsEndLit.containsKey(side)) connectionsEndLit.put(side, copyLitQuads(connectionsEndQuads));
                    quads.addAll(connectionsEndLit.get(side));
                } else {
                    quads.addAll(connectionsEndQuads);
                }

            }
            if (lit){
                for (BakedQuad quad : quads){
                    QuadTransformers.applyingLightmap(15, 15).processInPlace(quad);
                }
            }
        }
        return quads;
    }

    private List<BakedQuad> copyLitQuads(List<BakedQuad> baseQuads){
        List<BakedQuad> list = new ArrayList<>();
        for (BakedQuad q : baseQuads){
            int[] oldVertices = q.getVertices();
            int[] newVertices = new int[oldVertices.length];
            System.arraycopy(oldVertices, 0, newVertices, 0, oldVertices.length);
            BakedQuad newQuad = new BakedQuad(newVertices, q.getTintIndex(), q.getDirection(), q.getSprite(), q.isShade(), q.hasAmbientOcclusion());
            QuadTransformers.applyingLightmap(15, 15).processInPlace(newQuad);
            list.add(newQuad);
        }
        return list;
    }

    private List<BakedQuad> getQuadsFromModel(BakedModel model, BlockState state, RandomSource rand, BlockAndTintGetter level, BlockPos pos) {
        List<BakedQuad> quads = new ArrayList<>();
        for (Direction side : Direction.values()) {
            quads.addAll(ModelUtils.getQuadsFromBaked(model, state, side, rand, level, pos));
        }
        quads.addAll(ModelUtils.getQuadsFromBaked(model, state, null, rand, level, pos));
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
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
        return false;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
