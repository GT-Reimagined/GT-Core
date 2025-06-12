package org.gtreimagined.gtcore.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

public class ReTexturedQuad extends BakedQuad {

    public ReTexturedQuad(TextureAtlasSprite newTexture, BakedQuad originalQuad) {
        super(originalQuad.getVertices(), originalQuad.getTintIndex(), originalQuad.getDirection(), newTexture, originalQuad.isShade());
    }
}
