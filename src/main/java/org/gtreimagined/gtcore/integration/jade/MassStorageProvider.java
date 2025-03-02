package org.gtreimagined.gtcore.integration.jade;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import mcp.mobius.waila.api.ui.IElement;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import snownee.jade.Jade;
import snownee.jade.VanillaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MassStorageProvider implements IComponentProvider {
    public static final MassStorageProvider INSTANCE = new MassStorageProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.get(JadePlugin.MASS_STORAGE) && blockAccessor.getBlockEntity() instanceof BlockEntityMassStorage massStorage){
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
                    elements.add(Jade.smallItem(helper, copy).tag(JadePlugin.MASS_STORAGE));
                    String text = displayItem ? "" : (stack.getCount() < 10000 ? String.valueOf(stack.getCount()) : VanillaPlugin.getDisplayHelper().humanReadableNumber(stack.getCount(), "", false)) + "× ";
                    elements.add(helper.text(Utils.literal(text).append(stack.getHoverName())).tag(JadePlugin.MASS_STORAGE).message(null));
                    iTooltip.add(elements);
                }
            }
        }
    }
}
