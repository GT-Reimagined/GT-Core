package org.gtreimagined.gtcore.mixin;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ComposterBlock.class)
public class MixinComposterBlock {

    @ModifyArg(method = "extractProduce", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"))
    private static ItemLike modifyResult(ItemLike item){
        if (GTCoreConfig.COMPOSTER_OUTPUT_REPLACEMENT.get()){
            return GTCoreItems.Fertilizer;
        }
        return item;
    }
}
