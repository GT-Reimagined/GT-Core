package org.gtreimagined.gtcore.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.MassStorageMachine;
import org.gtreimagined.gtlib.block.GTItemBlock;
import org.gtreimagined.gtlib.client.RenderHelper;
import org.gtreimagined.gtlib.machine.BlockMachine;

public class ItemBlockMassStorage extends GTItemBlock {
    public ItemBlockMassStorage(BlockMachine block) {
        super(block);
        if (block.getType() instanceof MassStorageMachine && FMLEnvironment.dist.isClient()){
            RenderHelper.registerProperty(this, new ResourceLocation(GTCore.ID, "taped"), (stack, world, living, some_int) -> {
                CompoundTag nbt = stack.getTag();
                return nbt != null && nbt.contains("taped") && nbt.getBoolean("taped") ? 1.0F : 0.0F;
            });
        }
    }
}
