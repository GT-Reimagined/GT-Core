package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.item.FakeTrackedItemHandler;
import org.gtreimagined.gtlib.capability.item.SidedCombinedInvWrapper;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.jetbrains.annotations.NotNull;

public class BlockEntityTrashCan extends BlockEntityMachine<BlockEntityTrashCan> {
    public BlockEntityTrashCan(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            public LazyOptional<IItemHandler> forSide(Direction side) {
                return LazyOptional.of(() -> new TrashCanCombinedHandler(side, tile.coverHandler.map(c -> c).orElse(null), this.inventories.values().stream().filter(t -> !(t instanceof FakeTrackedItemHandler)).toArray(IItemHandlerModifiable[]::new)));
            }
        });
        this.fluidHandler.set(() -> new MachineFluidHandler<>(this, 1000, 1, 0){
            @Override
            public int fill(FluidStack fluid, FluidAction action) {
                return fluid.getAmount();
            }

            @Override
            public boolean canInput() {
                return true;
            }
        });
    }

    @Override
    public void onContainerClose(ContainerMachine<BlockEntityTrashCan> c, Player player) {
        super.onContainerClose(c, player);
        if (this.openContainers.isEmpty()){
            this.itemHandler.ifPresent(i -> {
                i.getHandler(SlotType.STORAGE).clearContent();
            });
        }
    }

    public static class TrashCanCombinedHandler extends SidedCombinedInvWrapper {
        public TrashCanCombinedHandler(Direction side, CoverHandler<?> coverHandler, IItemHandlerModifiable... itemHandler) {
            super(side, coverHandler, d -> true, d-> true, itemHandler);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return ItemStack.EMPTY;
        }
    }
}
