package io.github.gregtechintergalactical.gtcore.mixin;

import io.github.gregtechintergalactical.gtcore.GTCoreConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class MixinHoneycombItem {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void injectUseOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir){
        if (GTCoreConfig.HONEYCOMB_REPLACEMENT.get()){
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
