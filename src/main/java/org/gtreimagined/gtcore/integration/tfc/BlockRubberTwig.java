package org.gtreimagined.gtcore.integration.tfc;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.texture.Texture;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import org.gtreimagined.gtcore.GTCore;

public class BlockRubberTwig extends GroundcoverBlock implements IModelProvider, IAntimatterObject {
    public BlockRubberTwig() {
        super(ExtendedProperties.of(TFCMaterials.GROUNDCOVER).strength(0.05F, 0.0F).sound(SoundType.WOOD).noCollission().flammableLikeWool(), TWIG, null);
        AntimatterAPI.register(BlockRubberTwig.class, this);
    }

    @Override
    public String getId() {
        return "rubber_twig";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.state(block, prov.getBuilder(block).model("tfc:block/groundcover/twig", ImmutableMap.of("side", new Texture(GTCore.ID,"block/tree/rubber_log"), "top", new Texture(GTCore.ID,"block/tree/rubber_log_end"))));
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.tex(item, new Texture(GTCore.ID, "item/basic/rubber_twig"));
    }
}
