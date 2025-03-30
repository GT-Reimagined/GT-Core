package org.gtreimagined.gtcore.integration.tfc;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IColorHandler;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.texture.Texture;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCMaterials;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import org.gtreimagined.gtcore.GTCore;
import org.jetbrains.annotations.Nullable;

public class BlockFallenRubberLeaves extends FallenLeavesBlock implements IAntimatterObject, IModelProvider, IColorHandler {
    public BlockFallenRubberLeaves() {
        super(ExtendedProperties.of(TFCMaterials.GROUNDCOVER).noCollission().strength(0.05F, 0.0F).noOcclusion().sound(SoundType.CROP).flammableLikeWool());
        GTAPI.register(BlockFallenRubberLeaves.class, this);
    }

    @Override
    public String getId() {
        return "rubber_fallen_leaves";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.state(block, prov.getBuilder(block).model("tfc:block/groundcover/fallen_leaves", ImmutableMap.of("all", new Texture(GTCore.ID,"block/tree/rubber_leaves"))));
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.tex(item, new Texture("tfc", "item/groundcover/fallen_leaves"));
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return TFCColors.getFoliageColor(null, 0);
    }
}
