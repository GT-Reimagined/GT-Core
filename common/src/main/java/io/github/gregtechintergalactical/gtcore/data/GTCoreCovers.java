package io.github.gregtechintergalactical.gtcore.data;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.cover.CoverRedstoneTorch;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.item.ItemCover;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.item.Items;

public class GTCoreCovers {

    public static CoverFactory REDSTONE_TORCH = CoverFactory.builder(CoverRedstoneTorch::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/redstone_torch_off"), new Texture(GTCore.ID, "block/cover/redstone_torch_on"))
            .item((f, t) -> new ItemCover(GTCore.ID, "redstone_torch")).build(GTCore.ID, "redstone_torch");

    public static void init(){}
}
