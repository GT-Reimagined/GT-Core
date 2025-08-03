package org.gtreimagined.gtcore.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

import static org.gtreimagined.gtlib.material.Material.NULL;


public class ItemMixedMetal extends ItemBasic<ItemMixedMetal> implements IColorHandler {
    public ItemMixedMetal() {
        super(GTCore.ID, "mixed_metal_ingot");
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        if (i > 2) return -1;
        CompoundTag stackNbt = stack.getTag();
        if (stackNbt == null) return -1;
        CompoundTag nbt = stackNbt.getCompound(Ref.TAG_TOOL_DATA);
        String tagId = i == 0 ? "tm" : i == 1 ? "mm" : "bm";
        Material mat = Material.get(nbt.getString(tagId));
        if (mat == NULL) return -1;
        return mat.getRGB();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag stackNbt = stack.getTag();
        if (stackNbt == null){
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            return;
        }
        CompoundTag nbt = stackNbt.getCompound(Ref.TAG_TOOL_DATA);
        Material t = Material.get(nbt.getString("tm"));
        Material m = Material.get(nbt.getString("mm"));
        Material b = Material.get(nbt.getString("bm"));
        tooltip.add(Utils.literal("Top Material: " + t.getDisplayName().getString()));
        tooltip.add(Utils.literal("Middle Material: " + m.getDisplayName().getString()));
        tooltip.add(Utils.literal("Bottom Material: " + b.getDisplayName().getString()));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowedIn(group)) {
            ItemStack itemStack = getDefaultInstance();
            items.add(itemStack);
        }
    }

    @NotNull
    @Override
    public ItemStack getDefaultInstance() {
        return getMixedMetalIngot(GTCoreMaterials.WroughtIron, GTCoreMaterials.Brass, GTCoreMaterials.Tin);
    }

    public ItemStack getMixedMetalIngot(Material top, Material middle, Material bottom){
        ItemStack itemStack = new ItemStack(this);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("tm", top.getId());
        nbt.putString("mm", middle.getId());
        nbt.putString("bm", bottom.getId());
        itemStack.getOrCreateTag().put(Ref.TAG_TOOL_DATA, nbt);
        return itemStack;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "item/basic/" + getId() + "_top"), new Texture(getDomain(), "item/basic/" + getId() + "_middle"), new Texture(getDomain(), "item/basic/" + getId() + "_bottom")};
    }
}
