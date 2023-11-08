package io.github.gregtechintergalactical.gtcore.item;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.machine.MassStorageMachine;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.block.AntimatterItemBlock;
import muramasa.antimatter.client.RenderHelper;
import muramasa.antimatter.machine.BlockMachine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ItemBlockMassStorage extends AntimatterItemBlock {
    public ItemBlockMassStorage(BlockMachine block) {
        super(block);
        if (block.getType() instanceof MassStorageMachine && AntimatterAPI.getSIDE().isClient()){
            RenderHelper.registerProperty(this, new ResourceLocation(GTCore.ID, "taped"), (stack, world, living, some_int) -> {
                CompoundTag nbt = stack.getTag();
                return nbt != null && nbt.contains("taped") && nbt.getBoolean("taped") ? 1.0F : 0.0F;
            });
        }
    }
}
