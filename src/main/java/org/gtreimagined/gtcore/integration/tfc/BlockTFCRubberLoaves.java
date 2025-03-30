package org.gtreimagined.gtcore.integration.tfc;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;

public class BlockTFCRubberLoaves extends TFCLeavesBlock implements IAntimatterObject, ITextureProvider, IModelProvider {
    protected final String domain, id;
    public BlockTFCRubberLoaves() {
        super(ExtendedProperties.of(Material.LEAVES).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().isViewBlocking(TFCBlocks::never).flammableLikeLeaves(), 7, () -> TFCRubberData.RUBBER_FALLEN_LEAVES, () -> TFCRubberData.RUBBER_TWIG);
        this.domain = GTCore.ID;
        this.id = "rubber_leaves";
        GTAPI.register(BlockTFCRubberLoaves.class, this);
    }

    @Override
    protected IntegerProperty getDistanceProperty() {
        return TFCBlockStateProperties.DISTANCES[0];
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[] { new Texture(domain, "block/tree/" + id) };
    }
}
