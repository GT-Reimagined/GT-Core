package org.gtreimagined.gtcore.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.gui.slots.SlotBlueprint;
import org.gtreimagined.gtcore.gui.slots.SlotCrafting;
import org.gtreimagined.gtcore.gui.slots.SlotUnlimited;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.tesseract.TesseractCapUtils;

public class SlotTypes {
    public static SlotType<AbstractSlot<?>> TOOLS = new SlotType<>("tools", (type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> i.getItem() instanceof IGTTool || i.getItem().canBeDepleted());
    public static SlotType<AbstractSlot<?>> TOOL_CHARGE = new SlotType<>("tool_charge", (type, gui, item, i, d) -> new AbstractSlot<>(type,gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> {
        if (t instanceof BlockEntity tile) {
            return TesseractCapUtils.INSTANCE.getEnergyHandler(tile, null).map(eh -> TesseractCapUtils.INSTANCE.getEnergyHandlerItem(i).map(inner -> ((inner.getInputVoltage() | inner.getOutputVoltage()) <= (eh.getInputVoltage() | eh.getOutputVoltage()) )).orElse(false)).orElse(false) || i.getItem() instanceof IGTTool || i.getItem().canBeDepleted();
        }
        return true;
    }, true, true, new ResourceLocation(GTCore.ID, "electric_tool"));
    public static SlotType<SlotCrafting> CRAFTING = new SlotType<>("crafting", (type, gui, item, i, d) -> new SlotCrafting(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> true, false, true);
    public static SlotType<AbstractSlot<?>> PARK = new SlotType<>("park", (type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> true, true, false, new ResourceLocation(GTCore.ID, "park"));
    public static SlotType<SlotBlueprint> BLUEPRINT = new SlotType<>("blueprint", (type, gui, item, i, d) -> new SlotBlueprint(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> i.getItem() == GTCoreItems.Blueprint || i.getItem() == GTCoreItems.EmptyBlueprint, true, false, new ResourceLocation(GTCore.ID, "blueprint"));
    public static SlotType<AbstractSlot<?>> EXPORT = new SlotType<>("export", (type, gui, item, i, d) -> new AbstractSlot<>(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> true, true, true, new ResourceLocation(GTCore.ID, "export"));

    public static SlotType<SlotUnlimited> UNLIMITED = new SlotType<>("unlimited", (type, gui, item, i, d) -> new SlotUnlimited(type, gui, item.getOrDefault(type, new EmptyHandler()), i, d.getX(), d.getY()), (t, i) -> true);
    public static void init(){

    }
}
