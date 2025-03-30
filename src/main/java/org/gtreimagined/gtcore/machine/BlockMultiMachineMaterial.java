package org.gtreimagined.gtcore.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtlib.machine.BlockMultiMachine;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.jetbrains.annotations.Nullable;

import static org.gtreimagined.gtlib.material.Material.NULL;

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
