package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.texture.Texture;

;

public class BlockRubberFence extends FenceBlock implements IGTObject, IModelProvider {
    public BlockRubberFence() {
        super(Properties.copy(GTCoreBlocks.RUBBER_SLAB));
        GTAPI.register(BlockRubberFence.class, this);
    }

    @Override
    public String getId() {
        return "rubber_fence";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("minecraft", "block/fence_inventory")).texture("texture", new Texture(GTCore.ID, "block/tree/rubber_planks"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.fenceBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
