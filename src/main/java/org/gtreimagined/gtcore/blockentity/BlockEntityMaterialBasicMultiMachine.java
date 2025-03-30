package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.machine.MaterialBasicMultiMachine;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityBasicMultiMachine;
import org.gtreimagined.gtlib.material.Material;

public class BlockEntityMaterialBasicMultiMachine<T extends BlockEntityMaterialBasicMultiMachine<T>> extends BlockEntityBasicMultiMachine<T> {
    protected Material material;
    public BlockEntityMaterialBasicMultiMachine(MaterialBasicMultiMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        material = type.getMaterial();
    }

    public Material getMaterial() {
        return material;
    }
}
