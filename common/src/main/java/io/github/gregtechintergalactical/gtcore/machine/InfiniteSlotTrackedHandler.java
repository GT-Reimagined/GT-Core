package io.github.gregtechintergalactical.gtcore.machine;

import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMassStorage;
import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.capability.item.TrackedItemHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public class InfiniteSlotTrackedHandler extends TrackedItemHandler<BlockEntityMassStorage> {
    BlockEntity barrel;

    public InfiniteSlotTrackedHandler(BlockEntityMassStorage tile, SlotType<?> type, int size, boolean output, boolean input, BiPredicate<IGuiHandler, ItemStack> validator, int limit) {
        super(tile, type, size, output, input, validator, limit);
        this.barrel = tile;
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return getSlotLimit(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (barrel instanceof BlockEntityMassStorage barrel1 && barrel1.itemHandler.isPresent()) {
            var handler = barrel1.itemHandler.get().getHandler(SlotType.DISPLAY);
            if (!handler.getItem(0).isEmpty() && !Utils.equals(stack, handler.getItem(0))) {
                return stack;
            } else if (handler.getItem(0).isEmpty() && !simulate) {
                barrel1.itemHandler.ifPresent(i -> i.getHandler(SlotType.DISPLAY).setItem(0, Utils.ca(1, stack)));
            }
            if (barrel1.isOutputOverflow()){
                ItemStack leftover = super.insertItem(slot, stack, simulate);
                if (leftover.getCount() > 0 && !simulate){
                    barrel1.processItemOutput(leftover);
                }
                return leftover;
            }
        }
        return super.insertItem(slot, stack, simulate);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        } else {
            this.validateSlotIndex(slot);
            ItemStack existing = (ItemStack) this.stacks.get(slot);
            if (existing.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                if (existing.getCount() <= amount) {
                    if (!simulate) {
                        this.stacks.set(slot, ItemStack.EMPTY);
                        this.onContentsChanged(slot);
                        return existing;
                    } else {
                        return existing.copy();
                    }
                } else {
                    if (!simulate) {
                        this.stacks.set(slot, Utils.ca(existing.getCount() - amount, existing));
                        this.onContentsChanged(slot);
                    }

                    return Utils.ca(amount, existing);
                }
            }
        }
    }

    @Override
    public CompoundTag serialize(CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stacks.get(i).save(itemTag);
                itemTag.putInt("count", stacks.get(i).getCount());
                nbtTagList.add(itemTag);
            }
        }
        nbt.put("Items", nbtTagList);
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStack.of(itemTags));
                stacks.get(slot).setCount(itemTags.getInt("count"));
            }
        }
        onLoad();
    }


}