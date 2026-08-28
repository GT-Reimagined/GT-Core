package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.data.GTCoreSlotTypes;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.capability.item.SidedCombinedInvWrapper;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;

import static org.gtreimagined.gtlib.machine.MachineFlag.EU;

public class BlockEntityWorkbench extends BlockEntityMaterial<BlockEntityWorkbench>{
    public BlockEntityWorkbench(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new WorkbenchItemHandler(this));
    }

    @Override
    public boolean canPlayerOpenGui(Player playerEntity, Direction side) {
        return side == Direction.UP || side == this.getFacing() || side == this.getFacing().getOpposite();
    }

    public static class WorkbenchItemHandler extends MachineItemHandler<BlockEntityWorkbench> {
        public WorkbenchItemHandler(BlockEntityWorkbench tile) {
            super(tile);
            inventories.put(SlotTypes.STORAGE, new TrackedItemHandler<>(tile, SlotTypes.STORAGE, 52, false, false, SlotTypes.STORAGE.tester()));
            inventories.put(GTCoreSlotTypes.CRAFTING, new TrackedItemHandler<>(tile, GTCoreSlotTypes.CRAFTING, 9, false, false, GTCoreSlotTypes.CRAFTING.tester()));
            SlotType<?> type1 = tile.getMachineType().has(EU) ? GTCoreSlotTypes.TOOL_CHARGE : GTCoreSlotTypes.TOOLS;
            inventories.put(type1, new TrackedItemHandler<>(tile, type1, 5, false, false, type1.tester()));
            SlotType<?>[] types = new SlotType<?>[]{GTCoreSlotTypes.CRAFTING_RESULT, GTCoreSlotTypes.BLUEPRINT, GTCoreSlotTypes.EXPORT, GTCoreSlotTypes.PARK};
            for (SlotType<?> type : types) {
                inventories.put(type, new TrackedItemHandler<>(tile, type, 1, type == GTCoreSlotTypes.EXPORT, false, type.tester()));
            }
        }

        @Override
        public LazyOptional<IItemHandler> forSide(Direction side) {
            return LazyOptional.of(() -> new SidedCombinedInvWrapper(side, tile.coverHandler.map(c -> c).orElse(null), this::allowsInput, this::allowsOutput, this.inventories.get(GTCoreSlotTypes.EXPORT)));
        }
    }
}
