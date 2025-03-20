package org.gtreimagined.gtcore.block;

import muramasa.antimatter.block.BlockBasic;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;

public class BlockDimensionMarker extends BlockBasic {
    final String dimension;
    public BlockDimensionMarker(String dimension) {
        super(GTCore.ID, dimension + "_marker", Properties.of(Material.STONE).sound(SoundType.STONE).instabreak());
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/bottom"),
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/top"),
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/front"),
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/back"),
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/left"),
                new Texture(GTCore.ID, "block/dimension_marker/" + dimension + "/right")
        };
    }
}
