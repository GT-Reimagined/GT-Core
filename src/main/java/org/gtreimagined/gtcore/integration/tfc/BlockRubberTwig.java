package org.gtreimagined.gtcore.integration.tfc;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockRubberTwig extends GroundcoverBlock implements IModelProvider, IGTObject {
    public BlockRubberTwig() {
        super(ExtendedProperties.of(TFCMaterials.GROUNDCOVER).strength(0.05F, 0.0F).sound(SoundType.WOOD).noCollission().flammableLikeWool(), TWIG, null);
        GTAPI.register(BlockRubberTwig.class, this);
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
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.state(block, prov.getBuilder(block).model("tfc:block/groundcover/twig", ImmutableMap.of("side", new Texture(GTCore.ID,"block/tree/rubber_log"), "top", new Texture(GTCore.ID,"block/tree/rubber_log_end"))));
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.tex(item, new Texture(GTCore.ID, "item/basic/rubber_twig"));
    }
}
