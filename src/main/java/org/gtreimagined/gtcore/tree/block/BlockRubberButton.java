package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberButton extends WoodButtonBlock implements IGTObject, IModelProvider {
    public BlockRubberButton() {
        super(Properties.of(Material.DECORATION).noCollission().strength(0.5f).sound(SoundType.WOOD));
        GTAPI.register(BlockRubberButton.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_button";
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("block/button_inventory")).texture("texture", new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.buttonBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
