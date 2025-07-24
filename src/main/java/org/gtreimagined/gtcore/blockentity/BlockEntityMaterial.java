package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.material.Material;

public class BlockEntityMaterial<T extends BlockEntityMaterial<T>> extends BlockEntityMachine<T> {
    protected Material material;
    public BlockEntityMaterial(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        material = type.getMaterial();
    }

    @Override
    protected void disableMachine() {
       //NOOP
    }

    public Material getMaterial() {
        return material;
    }
}
