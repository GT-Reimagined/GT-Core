package org.gtreimagined.gtcore.cover;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class CoverSelectorTag extends BaseCover {
    final int mode;
    public CoverSelectorTag(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory, int mode) {
        super(source, tier, side, factory);
        this.mode = mode;
    }

    @Override
    public void onFirstTick() {
        if (source().getTile() instanceof BlockEntityRedstoneWire<?> wire) {
            wire.setMode(mode);
        }
    }

    @Override
    public void onPlace() {
        if (source().getTile() instanceof BlockEntityRedstoneWire<?> wire) {
            wire.setMode(mode);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (source().getTile() instanceof BlockEntityRedstoneWire<?> wire && !wire.isRemoved()) {
            wire.setMode(0);
        }
    }

    @Override
    protected String getRenderId() {
        return "selector_tag";
    }

    @Override
    public void setTextures(BiConsumer<String, Texture> texer) {
        texer.accept("overlay", factory.getTextures().get(0));
        texer.accept("underlay", factory.getTextures().get(1));
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        String renderId = type.equals("pipe")? this.getRenderId() + "_pipe" : this.getRenderId();
        return new ResourceLocation(GTCore.ID + ":block/cover/" + renderId);
    }
}
