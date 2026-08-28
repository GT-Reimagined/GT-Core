package org.gtreimagined.gtcore.machine;


import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.GTCoreSlotTypes;
import org.gtreimagined.gtlib.capability.item.FakeTrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.machine.MachineState;

public class MassStorageItemHandler extends MachineItemHandler<BlockEntityMassStorage> {

    public MassStorageItemHandler(BlockEntityMassStorage tile) {
        super(tile);
        inventories.put(SlotTypes.DISPLAY, new FakeTrackedItemHandler<>(tile, SlotTypes.DISPLAY, 1, false, false, SlotTypes.DISPLAY.tester()));
        inventories.put(GTCoreSlotTypes.UNLIMITED, new InfiniteSlotTrackedHandler<>(tile, GTCoreSlotTypes.UNLIMITED, 1, GTCoreSlotTypes.UNLIMITED.allowExternalOutput(), GTCoreSlotTypes.UNLIMITED.allowExternalInput(), GTCoreSlotTypes.UNLIMITED.tester(), ((MassStorageMachine)tile.getMachineType()).getCapacity()));
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
