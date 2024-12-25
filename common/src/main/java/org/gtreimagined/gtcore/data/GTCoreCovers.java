package org.gtreimagined.gtcore.data;

import muramasa.antimatter.cover.CoverReplacements;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.cover.CoverRedstoneTorch;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.cover.CoverRepeater;

public class GTCoreCovers {

    public static CoverFactory REDSTONE_TORCH = CoverFactory.builder(CoverRedstoneTorch::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/redstone_torch_off"), new Texture(GTCore.ID, "block/cover/redstone_torch_on"))
            .item((f, t) -> Items.REDSTONE_TORCH).build(GTCore.ID, "redstone_torch");
    public static CoverFactory REPEATER = CoverFactory.builder(CoverRepeater::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/redstone_torch_off"), new Texture(GTCore.ID, "block/cover/redstone_torch_on"))
            .item((f, t) -> Items.REPEATER).build(GTCore.ID, "repeater");

    public static void init(){
        CoverReplacements.addReplacement(Items.REDSTONE_TORCH, REDSTONE_TORCH);
        CoverReplacements.addReplacement(Items.REPEATER, REPEATER);
    }
}
