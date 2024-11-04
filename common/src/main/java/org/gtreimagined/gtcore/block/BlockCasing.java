package org.gtreimagined.gtcore.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.builder.AntimatterBlockModelBuilder;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.dynamic.BlockDynamic;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.data.GTCoreBlocks;

public class BlockCasing extends BlockDynamic {

    public BlockCasing(String domain, String id, Properties properties) {
        super(domain, id, properties);
        if (this.getClass() != BlockCasing.class) AntimatterAPI.register(BlockCasing.class, this);
    }

    public BlockCasing(String domain, String id) {
        this(domain, id, Properties.of(Material.METAL).strength(1.0f, 10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "block/casing/" + getId().replaceAll("_casing", ""))};
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        AntimatterBlockModelBuilder builder = buildBlock(block,prov);
        if (builder != null) {
            prov.state(block, builder);
        } else {
            super.onBlockModelBuild(block, prov);
        }
    }
    //Hierarchial block builder.
    protected AntimatterBlockModelBuilder buildBlock(Block block, AntimatterBlockStateProvider prov) {
        return null;
    }

    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return this == GTCoreBlocks.REINFORCED_GLASS ? 1.0F : super.getShadeBrightness(state, worldIn, pos);
    }

   /* public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }*/

    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return (this == GTCoreBlocks.REINFORCED_GLASS && adjacentBlockState.is(this)) || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.modelAndTexture(item, AntimatterBlockModelBuilder.getSimple()).tex(t -> t.putAll(AntimatterBlockModelBuilder.buildTextures(((ITextureProvider) item).getTextures())));
    }
}