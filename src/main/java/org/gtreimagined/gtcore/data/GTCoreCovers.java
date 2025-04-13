package org.gtreimagined.gtcore.data;

import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.cover.CoverRedstoneTorch;
import org.gtreimagined.gtcore.cover.CoverRepeater;
import org.gtreimagined.gtcore.cover.CoverSteamVent;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.cover.CoverReplacements;
import org.gtreimagined.gtlib.texture.Texture;

public class GTCoreCovers {

    public static CoverFactory REDSTONE_TORCH = CoverFactory.builder(CoverRedstoneTorch::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/redstone_torch_off"), new Texture(GTCore.ID, "block/cover/redstone_torch_on"))
            .item((f, t) -> Items.REDSTONE_TORCH).build(GTCore.ID, "redstone_torch");
    public static CoverFactory REPEATER = CoverFactory.builder(CoverRepeater::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/redstone_torch_off"), new Texture(GTCore.ID, "block/cover/redstone_torch_on"))
            .item((f, t) -> Items.REPEATER).build(GTCore.ID, "repeater");
    public static final CoverFactory COVER_STEAM_VENT = CoverFactory.builder(CoverSteamVent::new)
            .addTextures(new Texture(GTCore.ID, "block/cover/output")).build(GTCore.ID, "steam_vent");

    public static void init(){
        CoverReplacements.addReplacement(Items.REDSTONE_TORCH, REDSTONE_TORCH);
        CoverReplacements.addReplacement(Items.REPEATER, REPEATER);
    }
}
