package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.item.FakeTrackedItemHandler;
import org.gtreimagined.gtlib.capability.item.SidedCombinedInvWrapper;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;

import static org.gtreimagined.gtlib.machine.MachineFlag.EU;

public class BlockEntityWorkbench extends BlockEntityMaterial<BlockEntityWorkbench>{
    public BlockEntityWorkbench(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new WorkbenchItemHandler(this));
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return super.getGuiTexture();
    }

    @Override
    public boolean canPlayerOpenGui(Player playerEntity, Direction side) {
        return side == Direction.UP || side == this.getFacing() || side == this.getFacing().getOpposite();
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON && !openContainers.isEmpty()){
            final int[] data = ((GuiEvents.GuiEvent)event).data;
            if (data[1] == 0){
                openContainers.forEach(o -> {
                    if (playerEntity.getUUID().compareTo(o.getPlayerInv().player.getUUID()) == 0){
                        ((ContainerWorkbench<?>)o).clearCraftingGrid();
                    }
                });
            } else if (data[1] == 1){
                openContainers.forEach(o -> {
                    if (playerEntity.getUUID().compareTo(o.getPlayerInv().player.getUUID()) == 0){
                        ((ContainerWorkbench<?>)o).clearCraftingGridToPlayer();
                    }
                });
            }
        }
    }

    public static class WorkbenchItemHandler extends MachineItemHandler<BlockEntityWorkbench> {
        public WorkbenchItemHandler(BlockEntityWorkbench tile) {
            super(tile);
            inventories.put(SlotType.STORAGE, new TrackedItemHandler<>(tile, SlotType.STORAGE, 52, true, true, SlotType.STORAGE.getTester()));
            inventories.put(SlotTypes.CRAFTING, new TrackedItemHandler<>(tile, SlotTypes.CRAFTING, 9, true, true, SlotTypes.CRAFTING.getTester()));
            SlotType<?> type1 = tile.getMachineType().has(EU) ? SlotTypes.TOOL_CHARGE : SlotTypes.TOOLS;
            inventories.put(type1, new TrackedItemHandler<>(tile, type1, 5, type1.isOutput(), type1.isInput(), type1.getTester()));
            SlotType<?>[] types = new SlotType<?>[]{SlotTypes.CRAFTING_RESULT, SlotTypes.BLUEPRINT, SlotTypes.EXPORT, SlotTypes.PARK};
            for (SlotType<?> type : types) {
                inventories.put(type, new TrackedItemHandler<>(tile, type, 1, type.isOutput(), type.isInput(), type.getTester()));
            }
        }

        @Override
        public LazyOptional<IItemHandler> forSide(Direction side) {
            return LazyOptional.of(() -> new SidedCombinedInvWrapper(side, tile.coverHandler.map(c -> c).orElse(null), this::allowsInput, this::allowsOutput, this.inventories.get(SlotTypes.EXPORT)));
        }
    }

    public static class WorkbenchSidedCombinedInvWrapper extends SidedCombinedInvWrapper {

        public WorkbenchSidedCombinedInvWrapper(Direction side, CoverHandler<?> coverHandler, IItemHandlerModifiable... itemHandler) {
            super(side, coverHandler, itemHandler);
        }


    }
}
