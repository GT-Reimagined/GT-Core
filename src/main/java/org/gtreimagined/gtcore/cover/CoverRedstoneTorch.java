package org.gtreimagined.gtcore.cover;

import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.pipe.BlockPipe;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class CoverRedstoneTorch extends BaseCover {
    public CoverRedstoneTorch(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return source().getTile() instanceof BlockEntityRedstoneWire<?>;
    }

    @Override
    public void onPlace() {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        wire.clearConnection(side);
    }

    @Override
    public boolean blockConnection(Direction side) {
        return true;
    }

    @Override
    public int getStrongPower() {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        return wire.getRedstoneValue() > 0 ? 0 : 15;
    }

    @Override
    public int getWeakPower() {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        return wire.getRedstoneValue() > 0 ? 0 : 15;
    }

    @Override
    public void setTextures(BiConsumer<String, Texture> texer) {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        texer.accept("overlay", factory.getTextures().get(wire.getRedstoneValue() > 0 ? 0 : 1));
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        return new ResourceLocation(GTCore.ID + ":block/cover/" + this.getRenderId());
    }

    @Override
    public VoxelShape getShape(Direction side) throws ExecutionException {
        int config = 1 << side.get3DDataValue();
        BlockPipe<?> pipe = (BlockPipe<?>) GTCoreCables.WIRE_RED_ALLOY.getBlock(PipeSize.VTINY);
        return pipe.getPipeShapes().get(config, () -> pipe.makeShapes((short) config));
    }

    @Override
    public ResourceLocation getIdForCache() {
        return new ResourceLocation(GTCore.ID, "torches");
    }
}
