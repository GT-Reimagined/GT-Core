package org.gtreimagined.gtcore.cover;

import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class CoverRepeater extends BaseCover {
    public CoverRepeater(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
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
        return wire.getRedstoneValue() > 0 ? 15 : 0;
    }

    @Override
    public int getWeakPower() {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        return wire.getRedstoneValue() > 0 ? 15 : 0;
    }

    @Override
    public void setTextures(BiConsumer<String, Texture> texer) {
        BlockEntityRedstoneWire<?> wire = (BlockEntityRedstoneWire<?>) source().getTile();
        texer.accept("overlay", factory.getTextures().get(wire.getRedstoneValue() > 0 ? 0 : 1));
        texer.accept("overlay2", factory.getTextures().get(wire.getRedstoneValue() > 0 ? 1 : 0));
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        return new ResourceLocation(GTCore.ID + ":block/cover/" + this.getRenderId());
    }
}
