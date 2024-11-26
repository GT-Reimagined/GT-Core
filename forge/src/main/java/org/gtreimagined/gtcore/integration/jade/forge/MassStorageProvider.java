package org.gtreimagined.gtcore.integration.jade.forge;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import snownee.jade.Jade;
import snownee.jade.VanillaPlugin;

public class MassStorageProvider implements IComponentProvider {
    public static final MassStorageProvider INSTANCE = new MassStorageProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.get(JadePlugin.MASS_STORAGE) && blockAccessor.getBlockEntity() instanceof BlockEntityMassStorage massStorage){
            var handler = massStorage.itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
            if (handler != null) {
                ItemStack stack = handler.getItem(0);
                if (!stack.isEmpty()){
                    iTooltip.add(iTooltip.getElementHelper().item(stack, 1.0f, (stack.getCount() < 10000 ? String.valueOf(stack.getCount()) : VanillaPlugin.getDisplayHelper().humanReadableNumber(stack.getCount(), "", false))).tag(JadePlugin.MASS_STORAGE));
                    iTooltip.add(iTooltip.getElementHelper().text(stack.getHoverName()).tag(VanillaPlugin.INVENTORY).message(null));
                }
            }
        }
    }
}
