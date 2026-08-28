package org.gtreimagined.gtcore.data;

import brachy.modularui.widgets.slot.ModularSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.gtreimagined.gtcore.gui.slots.SlotBlueprint;
import org.gtreimagined.gtcore.gui.slots.SlotCrafting;
import org.gtreimagined.gtcore.gui.slots.SlotUnlimited;
import org.gtreimagined.gtcore.gui.slots.SlotCraftingOutput;
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.tesseract.TesseractCapUtils;

public class GTCoreSlotTypes {
    public static SlotType<AbstractSlot<?>> TOOLS = SlotType.create(b -> {
        b.setId("tools");
        b.slotSupplier((type, gui, item, i, d) -> {
            return new AbstractSlot<>(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
        });
        b.setTester((t, i) -> i.getItem() instanceof IGTTool || i.getItem().canBeDepleted());
    });
    public static SlotType<AbstractSlot<?>> TOOL_CHARGE = SlotType.create(b -> {
        b.setId("tool_charge");
        b.slotSupplier((type, gui, item, i, d) -> {
            return new AbstractSlot<>(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
        });
        b.setTester((t, i) -> {
            boolean electric = t instanceof BlockEntity tile && TesseractCapUtils.INSTANCE.getEnergyHandler(tile, null).map(eh -> {
                return TesseractCapUtils.INSTANCE.getEnergyHandlerItem(i).map(inner -> {
                    return (inner.getInputVoltage() | inner.getOutputVoltage()) <= (eh.getInputVoltage() | eh.getOutputVoltage());
                }).orElse(false);
            }).orElse(false);
            return electric || i.getItem() instanceof IGTTool || i.getItem().canBeDepleted();
        });
        b.setOverlay(GTCoreGuiTextures.ELECTRIC_TOOL_SLOT_OVERLAY);
    });

    public static SlotType<SlotCrafting> CRAFTING = SlotType.create(b -> {
        b.setId("crafting");
        b.setSlotSupplier((type, gui, item, i, d) -> {
            return new SlotCrafting(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
        });
        b.setAllowExternalOutput(false);
    });
    public static SlotType<AbstractSlot<?>> PARK = SlotType.create(b -> {
        b.setId("park");
        b.setSlotSupplier((type, gui, item, i, d) -> {
            return new AbstractSlot<>(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
        });
        b.setAllowExternalInput(false);
        b.allowExternalOutput(false);
        b.slotGroup(false);
        b.overlay(GTCoreGuiTextures.PARK_SLOT_OVERLAY);
    });
    public static SlotType<SlotBlueprint> BLUEPRINT = SlotType.create(r -> {
        r.setId("blueprint");
        r.setSlotSupplier((type, gui, item, i, d) -> {
                    return new SlotBlueprint(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
                });
        r.setTester((t, i) -> i.getItem() == GTCoreItems.Blueprint || i.getItem() == GTCoreItems.EmptyBlueprint);
        r.setAllowExternalOutput(false);
        r.setOverlay(GTCoreGuiTextures.BLUEPRINT_SLOT_OVERLAY);
    });
    public static SlotType<AbstractSlot<?>> EXPORT = SlotType.create(b -> {
        b.setId("export");
        b.setSlotSupplier((type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i));
        b.setSlotGroup(false);
        b.setOverlay(GTCoreGuiTextures.EXPORT_SLOT_OVERLAY);
    });
    public static SlotType<ModularSlot> CRAFTING_RESULT = SlotType.create(b -> {
        b.setId("crafting_result");
        b.setSlotSupplier((type, tile, slots, index, data) -> {
            if (tile instanceof BlockEntityMachine<?> machine) {
                return new SlotCraftingOutput(machine.itemHandler.map(m -> m).orElse(null), slots.getOrDefault(type, EmptyHandler.INSTANCE), (IItemHandlerModifiable) slots.getOrDefault(CRAFTING, new EmptyHandler()), index);
            }
            return new ModularSlot(slots.getOrDefault(type, EmptyHandler.INSTANCE), index);
        });
        b.setAllowExternalInput(false);
        b.setOverlay(GTCoreGuiTextures.CRAFTING_OUTPUT_SLOT_OVERLAY);
    });

    public static SlotType<SlotUnlimited> UNLIMITED = SlotType.create(b -> {
        b.setId("unlimited");
        b.setSlotSupplier((type, gui, item, i, d) -> {
                    return new SlotUnlimited(type, gui, item.getOrDefault(type, EmptyHandler.INSTANCE), i);
                });
    });
    public static void init(){

    }
}
