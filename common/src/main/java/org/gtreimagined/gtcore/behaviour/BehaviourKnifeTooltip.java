package org.gtreimagined.gtcore.behaviour;

import muramasa.antimatter.behaviour.IAddInformation;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.antimatter.tool.IBasicAntimatterTool;
import muramasa.antimatter.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BehaviourKnifeTooltip implements IAddInformation<IBasicAntimatterTool> {
    public static final BehaviourKnifeTooltip INSTANCE = new BehaviourKnifeTooltip();
    @Override
    public String getId() {
        return "knife_tooltip";
    }

    @Override
    public void onAddInformation(IBasicAntimatterTool instance, ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Utils.translatable("tooltip.gtcore.knife"));
    }
}
