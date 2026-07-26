package org.gtreimagined.gtcore.data;

import net.minecraftforge.fml.loading.FMLEnvironment;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.integration.recipeviewer.renderer.InfoRenderers;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;

public class GTCoreRecipeMaps {
    public static final RecipeMap<RecipeBuilder> MORTAR = new RecipeMap<>(GTCore.ID, "mortar", new RecipeBuilder());
    public static void init() {
        MORTAR.setIcon(() -> GTCoreBlocks.IRON_MORTAR).setGuiData(Guis.SIMPLE_DISPLAY);
        if (FMLEnvironment.dist.isClient()){
            MORTAR.setInfoRenderer(InfoRenderers.EMPTY_RENDERER);
        }
    }
}
