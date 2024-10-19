package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.machine.BlockMultiMachine;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.registration.IColorHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


import static muramasa.antimatter.material.Material.NULL;

public class BlockMultiMachineMaterial extends BlockMultiMachine implements IColorHandler {
    Material material = NULL;

    public BlockMultiMachineMaterial(Machine<?> type, Tier tier) {
        super(type, tier);
        if (type instanceof MaterialBasicMultiMachine machine){
            this.material = machine.getMaterial();
        }
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        return i == 0 ? material.getRGB() : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? material.getRGB() : -1;
    }
}
