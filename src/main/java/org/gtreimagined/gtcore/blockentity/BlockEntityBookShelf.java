package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.jetbrains.annotations.NotNull;

public class BlockEntityBookShelf extends BlockEntityMachine<BlockEntityBookShelf> {
    public BlockEntityBookShelf(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        itemHandler.ifPresent(e -> {
            CompoundTag in = new CompoundTag();
            e.getAll().forEach((f,i) -> {
                in.put(f.getId(), serializeWithEmpty(i, new CompoundTag()));
            });
            nbt.put(Ref.KEY_MACHINE_ITEMS, in);
        });
        return nbt;
    }

    public CompoundTag serializeWithEmpty(IItemHandler container, CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();


        for(int i = 0; i < container.getSlots(); ++i) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("Slot", i);
            container.getStackInSlot(i).save(itemTag);
            itemTag.putInt("count", container.getStackInSlot(i).getCount());
            nbtTagList.add(itemTag);
        }

        nbt.put("Items", nbtTagList);
        return nbt;
    }
}
