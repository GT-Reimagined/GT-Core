package org.gtreimagined.gtcore.tree.block;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.tree.RubberTree;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class BlockRubberSapling extends SaplingBlock implements IAntimatterObject, IModelProvider, ITextureProvider {

    public BlockRubberSapling() {
        super(new RubberTree(), Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).instabreak());
        AntimatterAPI.register(BlockRubberSapling.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_sapling";
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[] { new Texture(getDomain(), "block/tree/" + getId()) };
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.state(block, prov.getBuilder(block).parent(prov.existing("minecraft", "block/cross")).texture("cross", getTextures()[0]));
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation("item/generated")).texture("layer0", getTextures()[0]);
    }

    @Override
    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, Random random) {
        if (Biome.getBiomeCategory(world.getBiome(pos)) != Biome.BiomeCategory.NETHER && Biome.getBiomeCategory(world.getBiome(pos)) != Biome.BiomeCategory.THEEND)
            super.advanceTree(world, pos, state, random);
    }
}
