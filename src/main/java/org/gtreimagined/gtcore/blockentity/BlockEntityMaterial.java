package org.gtreimagined.gtcore.blockentity;

import org.gtreimagined.gtcore.machine.MaterialMachine;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityMaterial<T extends BlockEntityMaterial<T>> extends BlockEntityMachine<T> {
    protected Material material;
    public BlockEntityMaterial(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        material = type.getMaterial();
    }

    public Material getMaterial() {
        return material;
    }
}
