package org.gtreimagined.gtcore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Debug(export = true)
@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {

    @Inject(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 1), cancellable = true)
    private void gtcore$injectAssemble(CraftingContainer inv, CallbackInfoReturnable<ItemStack> cir, @Local List<ItemStack> list){
        if (list.size() == 2){
            ItemStack first = list.get(0);
            ItemStack second = list.get(1);
            if (first.getItem() == second.getItem() && (first.getItem() == GTCoreItems.Tape || first.getItem() == GTCoreItems.DuctTape || first.getItem() == GTCoreItems.FALDuctTape)) {
                int firstCurrentDamage = first.getMaxDamage() - first.getDamageValue();
                int secondCurrentDamage = second.getMaxDamage() - second.getDamageValue();
                int newDamage = firstCurrentDamage + secondCurrentDamage;
                int actualNewDamage = first.getMaxDamage() - newDamage;
                if (actualNewDamage < 0) actualNewDamage = 0;
                ItemStack newStack = new ItemStack(first.getItem());
                newStack.setDamageValue(actualNewDamage);
                cir.setReturnValue(newStack);
            }
        }

    }

}
