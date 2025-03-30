package org.gtreimagined.gtcore.integration.tfc;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.tree.block.BlockRubberFence;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockRubberLogFence extends BlockRubberFence {

    @Override
    public String getId() {
        return "rubber_log_fence";
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("tfc", "block/log_fence_inventory")).texture("planks", new Texture(GTCore.ID, "block/tree/rubber_planks")).texture("log", new Texture(GTCore.ID, "block/tree/rubber_log"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.fourWayBlock(this, prov.models().fencePost(getId() + "_post", new ResourceLocation(GTCore.ID, "block/tree/rubber_log")), prov.models().fenceSide(getId() + "_side", new ResourceLocation(GTCore.ID, "block/tree/rubber_planks")));
    }
}
