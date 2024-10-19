package org.gtreimagined.gtcore.item;

import org.gtreimagined.gtcore.data.GTCoreItems;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.cover.ICover;
import muramasa.antimatter.cover.IHaveCover;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemSelectorTag extends ItemBasic<ItemSelectorTag> implements IHaveCover {

    public final int circuitId;

    public ItemSelectorTag(String domain, String id, int circuitId, Properties properties) {
        super(domain, id, "selector_tags/", properties);
        this.circuitId = circuitId;
    }

    public ItemSelectorTag(String domain, String id, int circuitId) {
        super(domain, id, "selector_tags/");
        this.circuitId = circuitId;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(this.domain, "item/basic/" + this.subDir + circuitId)};
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        int newId = playerIn.isCrouching() ? this.getNewCircuitIdBackward() : this.getNewCircuitIdForward();
        ItemStack stack = playerIn.getItemInHand(handIn);
        ItemStack newStack = new ItemStack(GTCoreItems.SELECTOR_TAG_ITEMS.get(newId), stack.getCount());
        playerIn.setItemInHand(handIn, newStack);
        return InteractionResultHolder.consume(stack);
    }

    private int getNewCircuitIdForward(){
        if (this.circuitId == 24){
            return 0;
        }
        return this.circuitId + 1;
    }
    private int getNewCircuitIdBackward(){
        if (this.circuitId == 0){
            return 24;
        }
        return this.circuitId - 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Utils.translatable("tooltip.gtcore.selector_tag.0"));
        tooltipComponents.add(Utils.translatable("tooltip.gtcore.selector_tag.1"));
    }

    @Override
    public CoverFactory getCover() {
        if (circuitId > 15) return ICover.emptyFactory;
        return GTCoreItems.SELECTOR_TAG_COVERS.get(circuitId);
    }
}
