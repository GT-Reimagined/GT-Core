package org.gtreimagined.gtcore.client;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;

public class ClientHandler {
    public static void init() {
        Sheets.SIGN_MATERIALS.put(GTCoreBlocks.RUBBER_WOOD_TYPE, createSignMaterial(GTCoreBlocks.RUBBER_WOOD_TYPE));
        Sheets.HANGING_SIGN_MATERIALS.put(GTCoreBlocks.RUBBER_WOOD_TYPE, createHangingSignMaterial(GTCoreBlocks.RUBBER_WOOD_TYPE));
    }

    private static Material createSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, new ResourceLocation(GTCore.ID, "entity/signs/" + woodType.name()));
    }

    private static Material createHangingSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, new ResourceLocation(GTCore.ID, "entity/signs/hanging/" + woodType.name()));
    }
}
