package org.gtreimagined.gtcore.integration.tfc;


import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.tree.block.BlockRubberFence;

public class BlockRubberLogFence extends BlockRubberFence {

    @Override
    public String getId() {
        return "rubber_log_fence";
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("tfc", "block/log_fence_inventory")).texture("planks", new Texture(GTCore.ID, "block/tree/rubber_planks")).texture("log", new Texture(GTCore.ID, "block/tree/rubber_log"));
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.fourWayBlock(this, prov.models().fencePost(getId() + "_post", new ResourceLocation(GTCore.ID, "block/tree/rubber_log")), prov.models().fenceSide(getId() + "_side", new ResourceLocation(GTCore.ID, "block/tree/rubber_planks")));
    }
}
