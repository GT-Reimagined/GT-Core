package org.gtreimagined.gtcore.behaviour;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.gtreimagined.gtlib.behaviour.IAddInformation;
import org.gtreimagined.gtlib.tool.IBasicGTTool;
import org.gtreimagined.gtlib.util.Utils;

import java.util.List;

public class BehaviourKnifeTooltip implements IAddInformation<IBasicGTTool> {
    public static final BehaviourKnifeTooltip INSTANCE = new BehaviourKnifeTooltip();
    @Override
    public String getId() {
        return "knife_tooltip";
    }

    @Override
    public void onAddInformation(IBasicGTTool instance, ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Utils.translatable("tooltip.gtcore.knife"));
    }
}
