package org.gtreimagined.gtcore.mixin;

import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtcore.data.GTCoreItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantOffer.class)
public class MixinMerchantOffer {
    @Mutable
    @Shadow @Final private ItemStack baseCostA;

    @Mutable
    @Shadow @Final private ItemStack costB;

    @Mutable
    @Shadow @Final private ItemStack result;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void injectInit(CallbackInfo info){
        if (GTCoreConfig.VILLAGER_TRADE_REPLACEMENTS.get()){
            if (this.baseCostA.getItem() == Items.EMERALD){
                this.baseCostA = new ItemStack(GTCoreItems.GTCredit, baseCostA.getCount());
            }
            if (this.costB.getItem() == Items.EMERALD){
                this.costB = new ItemStack(GTCoreItems.GTCredit, costB.getCount());
            }
            if (this.result.getItem() == Items.EMERALD){
                this.result = new ItemStack(GTCoreItems.GTCredit, result.getCount());
            }
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;IIIFI)V", at = @At("TAIL"))
    private void injectInit2(CallbackInfo info){
        if (GTCoreConfig.VILLAGER_TRADE_REPLACEMENTS.get()){
            if (this.baseCostA.getItem() == Items.EMERALD){
                this.baseCostA = new ItemStack(GTCoreItems.GTCredit, baseCostA.getCount());
            }
            if (this.costB.getItem() == Items.EMERALD){
                this.costB = new ItemStack(GTCoreItems.GTCredit, costB.getCount());
            }
            if (this.result.getItem() == Items.EMERALD){
                this.result = new ItemStack(GTCoreItems.GTCredit, result.getCount());
            }
        }
    }

}
