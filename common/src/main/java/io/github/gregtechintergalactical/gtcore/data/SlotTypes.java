package io.github.gregtechintergalactical.gtcore.data;

import io.github.gregtechintergalactical.gtcore.gui.slots.SlotCrafting;
import io.github.gregtechintergalactical.gtcore.GTCore;
import muramasa.antimatter.capability.item.EmptyContainer;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.slot.AbstractSlot;
import muramasa.antimatter.tool.IAntimatterTool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.TesseractCapUtils;

public class SlotTypes {
    public static SlotType<AbstractSlot<?>> TOOLS = new SlotType<>("tools", (type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyContainer()), i, d.getX(), d.getY()), (t, i) -> i.getItem() instanceof IAntimatterTool || i.getItem().canBeDepleted());
    public static SlotType<AbstractSlot<?>> TOOL_CHARGE = new SlotType<>("tool_charge", (type, gui, item, i, d) -> new AbstractSlot<>(type,gui, item.getOrDefault(type, new EmptyContainer()), i, d.getX(), d.getY()), (t, i) -> {
        if (t instanceof BlockEntity tile) {
            return TesseractCapUtils.getEnergyHandler(tile, null).map(eh -> TesseractCapUtils.getEnergyHandlerItem(i).map(inner -> ((inner.getInputVoltage() | inner.getOutputVoltage()) <= (eh.getInputVoltage() | eh.getOutputVoltage()) )).orElse(false)).orElse(false) || i.getItem() instanceof IAntimatterTool || i.getItem().canBeDepleted();
        }
        return true;
    }, true, true, new ResourceLocation(GTCore.ID, "electric_tool"));
    public static SlotType<SlotCrafting> CRAFTING = new SlotType<>("crafting", (type, gui, item, i, d) -> new SlotCrafting(type, gui, item.getOrDefault(type, new EmptyContainer()), i, d.getX(), d.getY()), (t, i) -> true, false, true);
    public static SlotType<AbstractSlot<?>> PARK = new SlotType<>("park", (type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyContainer()), i, d.getX(), d.getY()), (t, i) -> true, true, true, new ResourceLocation(GTCore.ID, "park"));

    public static void init(){

    }
}
