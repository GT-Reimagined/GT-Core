package org.gtreimagined.gtcore.behaviour;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.behaviour.IItemRightClick;
import muramasa.antimatter.tool.IBasicAntimatterTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static org.gtreimagined.gtcore.data.GTCoreTools.ELECTRIC_WRENCH_ALT;

public class BehaviourElectricWrenchSwitching implements IItemRightClick<IBasicAntimatterTool> {

    public static BehaviourElectricWrenchSwitching INSTANCE = new BehaviourElectricWrenchSwitching();
    @Override
    public InteractionResultHolder<ItemStack> onRightClick(IBasicAntimatterTool instance, Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown() && !level.isClientSide){
            String id = instance.getAntimatterToolType() == ELECTRIC_WRENCH_ALT ? instance.getId().replace("wrench_alt", "wrench") : instance.getId().replace("wrench", "wrench_alt");
            Item newWrench = AntimatterAPI.get(Item.class, id, instance.getDomain());
            ItemStack newStack = new ItemStack(newWrench);
            newStack.setTag(stack.getTag());
            player.setItemSlot(usedHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, newStack);
            return InteractionResultHolder.success(newStack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
