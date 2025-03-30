package org.gtreimagined.gtcore.integration.tfc;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.gtreimagined.gtcore.GTCore;

public class BlockTFCRubberSapling extends TFCSaplingBlock implements IAntimatterObject, IModelProvider, ITextureProvider {
    public BlockTFCRubberSapling() {
        super(new TFCRubberTree(), ExtendedProperties.of(Material.PLANT, MaterialColor.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).flammableLikeLeaves().blockEntity(TFCBlockEntities.TICK_COUNTER), 7);
        GTAPI.register(BlockTFCRubberSapling.class, this);
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
}
