package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberSlab extends SlabBlock implements IGTObject, IModelProvider {
    public BlockRubberSlab() {
        super(Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        GTAPI.register(BlockRubberSlab.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_slab";
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.slabBlock(this, new ResourceLocation(GTCore.ID, "block/rubber_planks"), new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
