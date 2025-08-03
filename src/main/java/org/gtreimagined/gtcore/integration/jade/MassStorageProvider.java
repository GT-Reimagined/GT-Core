package org.gtreimagined.gtcore.integration.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.util.Utils;
import snownee.jade.Jade;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.IElement;

import java.util.ArrayList;
import java.util.List;

public class MassStorageProvider implements IBlockComponentProvider {
    private static final ResourceLocation ID = new ResourceLocation(GTCore.ID, "mass_storage");
    public static final MassStorageProvider INSTANCE = new MassStorageProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof BlockEntityMassStorage massStorage){
            var handler = massStorage.itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
            var displayHandler = massStorage.itemHandler.map(i -> i.getHandler(SlotType.DISPLAY)).orElse(null);
            if (handler != null && displayHandler != null) {
                ItemStack stack = handler.getStackInSlot(0);
                boolean displayItem = false;
                if (stack.isEmpty()) {
                    stack = displayHandler.getStackInSlot(0);
                    displayItem = true;
                }
                if (!stack.isEmpty()){
                    ItemStack copy = stack.copy();
                    copy.setCount(1);
                    var helper = iTooltip.getElementHelper();
                    List<IElement> elements = new ArrayList<>();
                    elements.add(helper.smallItem(copy));
                    String text = displayItem ? "" : (stack.getCount() < 10000 ? String.valueOf(stack.getCount()) : humanReadableNumber(stack.getCount(), "", false)) + "× ";
                    elements.add(helper.text(Utils.literal(text).append(stack.getHoverName())).message(null));
                    iTooltip.add(elements);
                }
            }
        }
    }

    private String humanReadableNumber(double number, String unit, boolean shift) {
        return shift ? number + unit : IDisplayHelper.get().humanReadableNumber(number, unit, false);
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
