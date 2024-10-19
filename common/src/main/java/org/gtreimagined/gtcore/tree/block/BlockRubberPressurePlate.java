package org.gtreimagined.gtcore.tree.block;

import org.gtreimagined.gtcore.GTCore;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockRubberPressurePlate extends PressurePlateBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD).noCollission().strength(0.5f).sound(SoundType.WOOD));
        AntimatterAPI.register(BlockRubberPressurePlate.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_pressure_plate";
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.pressurePlateBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
