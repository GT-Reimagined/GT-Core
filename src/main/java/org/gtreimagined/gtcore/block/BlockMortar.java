package org.gtreimagined.gtcore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.block.BlockBasic;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.jetbrains.annotations.Nullable;

public class BlockMortar extends BlockBasic implements IColorHandler {
    final Material material;
    static final int CERAMIC_COLOR = 0xDC8246;
    public BlockMortar(Material material) {
        super(GTCore.ID, material.getId() + "_mortar", Properties.of().noOcclusion().strength(1.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_BROWN));
        this.material = material;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        return i == 0 ? CERAMIC_COLOR : i == 1 ? material.getRGB() : i == 2 ? 0xffff00 : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? CERAMIC_COLOR : i == 1 ? material.getRGB() : i == 2 ? 0xffff00 : -1;
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.simpleBlock(block, () -> prov.existing(GTCore.ID, "block/mortar"));
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(prov.existing(GTCore.ID, "block/mortar")).build();
    }
}
