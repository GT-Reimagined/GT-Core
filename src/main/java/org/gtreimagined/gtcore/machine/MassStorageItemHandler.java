package org.gtreimagined.gtcore.machine;


import muramasa.antimatter.capability.item.FakeTrackedItemHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.MachineState;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;

public class MassStorageItemHandler extends MachineItemHandler<BlockEntityMassStorage> {

    public MassStorageItemHandler(BlockEntityMassStorage tile) {
        super(tile);
        inventories.put(SlotType.DISPLAY, new FakeTrackedItemHandler<>(tile, SlotType.DISPLAY, 1, SlotType.DISPLAY.output, SlotType.DISPLAY.input, SlotType.DISPLAY.tester));
        inventories.put(SlotTypes.UNLIMITED, new InfiniteSlotTrackedHandler<>(tile, SlotTypes.UNLIMITED, 1, SlotTypes.UNLIMITED.output, SlotTypes.UNLIMITED.input, SlotTypes.UNLIMITED.tester, ((MassStorageMachine)tile.getMachineType()).getCapacity()));
    }

    /*public void drawInfo(MatrixStack stack, FontRenderer renderer, int left, int top) {
        // TODO: Replace by new TranslationTextComponent()
        renderer.draw(stack,"Item amount: " + digitalCount, left + 10, top + 19, 16448255);
    }*/

    @Override
    public boolean allowsInput(Direction side) {
        return super.allowsInput(side) && tile.getMachineState() != MachineState.ACTIVE && (side != Direction.DOWN || !tile.isOutput());
    }

    @Override
    public boolean allowsOutput(Direction side) {
        return super.allowsOutput(side) && tile.getMachineState() != MachineState.ACTIVE;
    }

    @Override
    public LazyOptional<? extends IItemHandler> forNullSide() {
        return LazyOptional.empty();
    }
}
