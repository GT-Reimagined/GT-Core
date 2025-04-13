package org.gtreimagined.gtcore.behaviour;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.behaviour.IItemRightClick;
import org.gtreimagined.gtlib.tool.IBasicGTTool;
import org.gtreimagined.gtlib.tool.IGTTool;

import static org.gtreimagined.gtcore.data.GTCoreTools.*;

public class BehaviourMultitoolSwitching implements IItemRightClick<IBasicGTTool> {

    public static BehaviourMultitoolSwitching INSTANCE = new BehaviourMultitoolSwitching();
    @Override
    public InteractionResultHolder<ItemStack> onRightClick(IBasicGTTool instance, Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown() && !level.isClientSide){
            var toolType = instance.getGTToolType();
            String id = instance.getId();
            if (toolType == POCKET_MULTITOOL) id = id + "_knife";
            if (toolType == POCKET_MULTITOOL_KNIFE) id = id.replace("knife", "saw");
            if (toolType == POCKET_MULTITOOL_SAW) id = id.replace("saw", "file");
            if (toolType == POCKET_MULTITOOL_FILE) id = id.replace("file", "screwdriver");
            if (toolType == POCKET_MULTITOOL_SCREWDRIVER) id = id.replace("screwdriver", "wire_cutter");
            if (toolType == POCKET_MULTITOOL_WIRE_CUTTER) id = id.replace("wire_cutter", "scissors");
            if (toolType == POCKET_MULTITOOL_SCISSORS) id = id.replace("_scissors", "");
            Item newWrench = GTAPI.get(IGTTool.class, id, Ref.SHARED_ID).getItem();
            ItemStack newStack = new ItemStack(newWrench);
            newStack.setTag(stack.getTag());
            player.setItemSlot(usedHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, newStack);
            return InteractionResultHolder.success(newStack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
