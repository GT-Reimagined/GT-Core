package org.gtreimagined.gtcore.behaviour;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.behaviour.IItemRightClick;
import org.gtreimagined.gtlib.tool.IBasicGTTool;

import static org.gtreimagined.gtcore.data.GTCoreTools.ELECTRIC_WRENCH_ALT;

public class BehaviourElectricWrenchSwitching implements IItemRightClick<IBasicGTTool> {

    public static BehaviourElectricWrenchSwitching INSTANCE = new BehaviourElectricWrenchSwitching();
    @Override
    public InteractionResultHolder<ItemStack> onRightClick(IBasicGTTool instance, Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown() && !level.isClientSide){
            String id = instance.getGTToolType() == ELECTRIC_WRENCH_ALT ? instance.getId().replace("wrench_alt", "wrench") : instance.getId().replace("wrench", "wrench_alt");
            Item newWrench = GTAPI.get(Item.class, id, instance.getDomain());
            ItemStack newStack = new ItemStack(newWrench);
            newStack.setTag(stack.getTag());
            player.setItemSlot(usedHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, newStack);
            return InteractionResultHolder.success(newStack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
