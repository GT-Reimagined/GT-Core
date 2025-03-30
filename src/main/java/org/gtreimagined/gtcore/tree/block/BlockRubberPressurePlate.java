package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberPressurePlate extends PressurePlateBlock implements IGTObject, IModelProvider {
    public BlockRubberPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD).noCollission().strength(0.5f).sound(SoundType.WOOD));
        GTAPI.register(BlockRubberPressurePlate.class, this);
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
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.pressurePlateBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_planks"));
    }
}
