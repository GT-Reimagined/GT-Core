package io.github.gregtechintergalactical.gtcore.cover;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityRedstoneWire;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class CoverSelectorTag extends BaseCover {
    public CoverSelectorTag(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void onPlace() {
        if (source().getTile() instanceof BlockEntityRedstoneWire<?> wire) {

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
        return new ResourceLocation(GTCore.ID + ":block/cover/" + this.getRenderId());
    }
}
