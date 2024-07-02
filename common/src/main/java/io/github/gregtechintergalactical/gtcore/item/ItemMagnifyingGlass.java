package io.github.gregtechintergalactical.gtcore.item;

import io.github.gregtechintergalactical.gtcore.GTCore;
import muramasa.antimatter.Ref;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ScannerItem;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.registration.IColorHandler;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemMagnifyingGlass extends ScannerItem implements IColorHandler {
    public final Material material;
    public ItemMagnifyingGlass(Material material, int durability) {
        super(GTCore.ID, material.getId() + "_magnifying_glass", true, "magnifying_glass/", new Properties().defaultDurability(durability).tab(Ref.TAB_TOOLS));
        this.material = material;
    }

    @Override
    public @NotNull InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        InteractionResult result = super.onItemUseFirst(stack, context);
        if (result == InteractionResult.SUCCESS && context.getPlayer() != null){
            Utils.damageStack(context.getItemInHand(), context.getPlayer());
        }
        return result;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(this.domain, "item/basic/" + subDir + "base"), new Texture(this.domain, "item/basic/" + subDir + "overlay")};
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? material.getRGB() : -1;
    }
}
