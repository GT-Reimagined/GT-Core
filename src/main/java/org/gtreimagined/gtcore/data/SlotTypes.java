package org.gtreimagined.gtcore.data;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.gtreimagined.gtcore.gui.slots.SlotBlueprint;
import org.gtreimagined.gtcore.gui.slots.SlotCrafting;
import org.gtreimagined.gtcore.gui.slots.SlotUnlimited;
import org.gtreimagined.gtcore.gui.slots.SlotWorkTableResult;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.tesseract.TesseractCapUtils;

public class SlotTypes {
    public static SlotType<AbstractSlot<?>> TOOLS = SlotType.<AbstractSlot<?>>builder().id("tools").slotSupplier((type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .tester((t, i) -> i.getItem() instanceof IGTTool || i.getItem().canBeDepleted()).build();
    public static SlotType<AbstractSlot<?>> TOOL_CHARGE = SlotType.<AbstractSlot<?>>builder().id("tool_charge").slotSupplier((type, gui, item, i, d) -> new AbstractSlot<>(type,gui, item.getOrDefault(type, new EmptyHandler()), i)).tester((t, i) -> {
        if (t instanceof BlockEntity tile) {
            return TesseractCapUtils.INSTANCE.getEnergyHandler(tile, null).map(eh -> TesseractCapUtils.INSTANCE.getEnergyHandlerItem(i).map(inner -> ((inner.getInputVoltage() | inner.getOutputVoltage()) <= (eh.getInputVoltage() | eh.getOutputVoltage()) )).orElse(false)).orElse(false) || i.getItem() instanceof IGTTool || i.getItem().canBeDepleted();
        }
        return true;
    }).overlay(GTCoreGuiTextures.ELECTRIC_TOOL_SLOT_OVERLAY).build();
    public static SlotType<SlotCrafting> CRAFTING = SlotType.<SlotCrafting>builder().id("crafting").slotSupplier((type, gui, item, i, d) -> new SlotCrafting(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .output(false).build();
    public static SlotType<AbstractSlot<?>> PARK = SlotType.<AbstractSlot<?>>builder().id("park")
            .slotSupplier((type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .output(false).slotGroup(false).overlay(GTCoreGuiTextures.PARK_SLOT_OVERLAY).build();
    public static SlotType<SlotBlueprint> BLUEPRINT = SlotType.<SlotBlueprint>builder().id("blueprint")
            .slotSupplier((type, gui, item, i, d) -> new SlotBlueprint(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .tester((t, i) -> i.getItem() == GTCoreItems.Blueprint || i.getItem() == GTCoreItems.EmptyBlueprint)
            .output(false).overlay(GTCoreGuiTextures.BLUEPRINT_SLOT_OVERLAY).build();
    public static SlotType<AbstractSlot<?>> EXPORT = SlotType.<AbstractSlot<?>>builder().id("export")
            .slotSupplier((type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .slotGroup(false).overlay(GTCoreGuiTextures.EXPORT_SLOT_OVERLAY).build();
    public static SlotType<SlotWorkTableResult> CRAFTING_RESULT = SlotType.<SlotWorkTableResult>builder().id("crafting_result").slotSupplier((type, tile, slots, index, data) -> {
        if (tile instanceof BlockEntityMachine<?> machine) {
            return new SlotWorkTableResult(machine.itemHandler.map(m -> m).orElse(null), slots.getOrDefault(type, new EmptyHandler()), (IItemHandlerModifiable) slots.getOrDefault(CRAFTING, new EmptyHandler()), index);
        }
        return null;
    }).input(false).overlay(GTCoreGuiTextures.CRAFTING_OUTPUT_SLOT_OVERLAY).build();

    public static SlotType<SlotUnlimited> UNLIMITED = SlotType.<SlotUnlimited>builder().id("unlimited")
            .slotSupplier((type, gui, item, i, d) -> new SlotUnlimited(type, gui, item.getOrDefault(type, new EmptyHandler()), i))
            .build();
    public static void init(){

    }
}
