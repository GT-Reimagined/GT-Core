package org.gtreimagined.gtcore.tree.block;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;

;

public class BlockRubberFence extends FenceBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberFence() {
        super(Properties.copy(GTCoreBlocks.RUBBER_SLAB));
        AntimatterAPI.register(BlockRubberFence.class, this);
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
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("minecraft", "block/fence_inventory")).texture("texture", new Texture(GTCore.ID, "block/tree/rubber_planks"));
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.fenceBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
