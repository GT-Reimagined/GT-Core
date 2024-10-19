package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.blockentity.BlockEntityPlasticBin;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.capability.item.TrackedItemHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.blockentity.BlockEntityPlasticBin;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class InfiniteSlotTrackedHandler<T extends IGuiHandler> extends TrackedItemHandler<T> {

    public InfiniteSlotTrackedHandler(T tile, SlotType<?> type, int size, boolean output, boolean input, BiPredicate<IGuiHandler, ItemStack> validator, int limit) {
        super(tile, type, size, output, input, validator, limit);
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        return getSlotLimit(slot);
    }

    @Override
    public int getSlotLimit(int slot) {
        if (getTile() instanceof BlockEntityPlasticBin bin){
            return bin.getMaxLimit();
        }
        return super.getSlotLimit(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (getTile() instanceof BlockEntityMassStorage barrel && barrel.itemHandler.isPresent()) {
            if (barrel.getMachineState() == MachineState.ACTIVE) return stack;
            var handler = barrel.itemHandler.get().getHandler(SlotType.DISPLAY);
            if (barrel.keepFilter && !handler.getItem(0).isEmpty() && !Utils.equals(stack, handler.getItem(0))) {
                return stack;
            } else if (barrel.keepFilter && handler.getItem(0).isEmpty() && !simulate) {
                barrel.itemHandler.ifPresent(i -> i.getHandler(SlotType.DISPLAY).setItem(0, Utils.ca(1, stack)));
            }
            if (barrel.isOutputOverflow()){
                ItemStack leftover = super.insertItem(slot, stack.copy(), simulate);
                if (leftover.getCount() > 0){
                    barrel.processItemOutput(leftover, simulate);
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
                        if (getTile() instanceof BlockEntityMassStorage barrel && barrel.itemHandler.isPresent() && !barrel.keepFilter){
                            ItemStack display = barrel.itemHandler.get().getHandler(SlotType.DISPLAY).getItem(0);
                            if (!display.isEmpty()){
                                barrel.itemHandler.get().getHandler(SlotType.DISPLAY).setItem(0, ItemStack.EMPTY);
                            }
                        }
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
                itemTag.putByte("Count", (byte) 1);
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
