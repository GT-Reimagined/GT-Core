package org.gtreimagined.gtcore.data;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockMortar;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;

public class GTCoreRecipeMaps {
    public static final RecipeMap<RecipeBuilder> MORTAR = new RecipeMap<>(GTCore.ID, "mortar", new RecipeBuilder());
    public static void init() {
        MORTAR.setIcon(() -> GTCoreBlocks.IRON_MORTAR).setGuiData(Guis.SIMPLE_DISPLAY);
        GTLibXEIPlugin.addWorkstations(MORTAR.getLoc(), l -> {
            GTAPI.all(BlockMortar.class).forEach(b -> l.add(b.asItem()));
        });
        GTAPI.register(RecipeMap.class, MORTAR);
    }
}
