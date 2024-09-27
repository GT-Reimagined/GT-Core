package io.github.gregtechintergalactical.gtcore.mixin.jei;

import io.github.gregtechintergalactical.gtcore.GTCore;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.common.input.IClickedIngredient;
import mezz.jei.common.input.handlers.FocusInputHandler;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(FocusInputHandler.class)
public class FocusInputHandlerMixin {
    @Redirect(method = "lambda$handleShow$0", at = @At(value = "INVOKE", target = "Lmezz/jei/common/input/IClickedIngredient;getTypedIngredient()Lmezz/jei/api/ingredients/ITypedIngredient;"), remap = false)
    private static ITypedIngredient<?> gtcore$redirectSelectorTag(IClickedIngredient<?> instance){
        if (instance.getTypedIngredient().getIngredient() instanceof ItemStack stack){
            GTCore.LOGGER.info(instance.getTypedIngredient());
        }
        return instance.getTypedIngredient();
    }
}
