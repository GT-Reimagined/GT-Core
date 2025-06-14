package org.gtreimagined.gtcore.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ReTexturedQuad extends BakedQuad {

    TextureAtlasSprite newSprite;

    public ReTexturedQuad(TextureAtlasSprite newTexture, BakedQuad originalQuad) {
        super(Arrays.copyOf(originalQuad.getVertices(), originalQuad.getVertices().length), originalQuad.getTintIndex(), originalQuad.getDirection(), originalQuad.getSprite(), originalQuad.isShade());
        this.newSprite = newTexture;
        remapQuad();
    }

    @Override
    public @NotNull TextureAtlasSprite getSprite() {
        return newSprite;
    }

    private void remapQuad() {
        for (int i = 0; i < 4; ++i) {
            int j = DefaultVertexFormat.BLOCK.getIntegerSize() * i;
            int uvIndex = 4;
            this.vertices[j + uvIndex] = Float.floatToRawIntBits(this.newSprite.getU((double) getUnInterpolatedU(this.sprite, Float.intBitsToFloat(this.vertices[j + uvIndex]))));
            this.vertices[j + uvIndex + 1] = Float.floatToRawIntBits(this.newSprite.getV((double) getUnInterpolatedV(this.sprite, Float.intBitsToFloat(this.vertices[j + uvIndex + 1]))));
        }
    }

    private static float getUnInterpolatedU(TextureAtlasSprite sprite, float u) {
        float f = sprite.getU1() - sprite.getU0();
        return (u - sprite.getU0()) / f * 16.0F;
    }

    private static float getUnInterpolatedV(TextureAtlasSprite sprite, float v) {
        float f = sprite.getV1() - sprite.getV0();
        return (v - sprite.getV0()) / f * 16.0F;

    }
}
