package io.github.gregtechintergalactical.gtcore.cover;

import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityRedstoneWire;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverRedstoneTorch extends BaseCover {
    public CoverRedstoneTorch(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return source().getTile() instanceof BlockEntityRedstoneWire<?>;
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
}
