package org.gtreimagined.gtcore.mixin;

import net.minecraft.world.item.Tiers;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tiers.class)
public class TiersMixin {
    @Inject(method = "getUses", at = @At("HEAD"), cancellable = true)
    private void gtcore$modifyDurability(CallbackInfoReturnable<Integer> cir){
        switch ((Tiers)(Object)this){
            case WOOD -> cir.setReturnValue(8);
            case STONE -> cir.setReturnValue(16);
            case IRON -> cir.setReturnValue(128);
            case DIAMOND -> cir.setReturnValue(512);
            case NETHERITE -> cir.setReturnValue(1024);
        }

    }
}
