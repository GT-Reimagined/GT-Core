package org.gtreimagined.gtcore.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTape extends ItemBasic<ItemTape> {
    public ItemTape(String domain, String id, int maxDurability) {
        super(domain, id, new Item.Properties().tab(Ref.TAB_ITEMS).defaultDurability(maxDurability));
    }

    public ItemTape(String domain, String id) {
        super(domain, id);
    }

    public Item getEmpty(){
        return GTAPI.get(Item.class, id + "_empty", domain);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        String tape = getId().contains("empty") ? "used" : "full";
        tooltipComponents.add(Utils.translatable("tooltip.gtcore.tape." + tape + "_roll"));
        tooltipComponents.add(Utils.translatable("tooltip.gtcore.tape.can_fix_anything"));
        tooltipComponents.add(Utils.translatable("tooltip.gtcore.tape.remaining_uses", stack.getMaxDamage() - stack.getDamageValue()));
    }
}
