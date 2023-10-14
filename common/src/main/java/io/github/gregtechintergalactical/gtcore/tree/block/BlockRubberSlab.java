package io.github.gregtechintergalactical.gtcore.tree.block;

import io.github.gregtechintergalactical.gtcore.GTCore;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockRubberSlab extends SlabBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberSlab() {
        super(Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        AntimatterAPI.register(BlockRubberSlab.class, this);
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
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.slabBlock(this, new ResourceLocation(GTCore.ID, "block/rubber_planks"), new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
