package org.gtreimagined.gtcore.mixin;

import org.gtreimagined.gtcore.data.GTCoreTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Block.getDrops;

@Debug(export = true)
@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)V", at = @At("HEAD"), cancellable = true)
    private static void injectDropResources(BlockState state, LootContext.Builder lootContextBuilder, CallbackInfo ci) {
        ItemStack tool = lootContextBuilder.getParameter(LootContextParams.TOOL);
        Entity entity = lootContextBuilder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (tool.is(GTCoreTags.MAGNETIC_TOOL) && entity instanceof Player player){
            ServerLevel serverlevel = lootContextBuilder.getLevel();
            BlockPos blockpos = new BlockPos(lootContextBuilder.getParameter(LootContextParams.ORIGIN));
            state.getDrops(lootContextBuilder).forEach((stack) -> {
                gtcore$popResource(serverlevel, blockpos, stack, player);
            });
            state.spawnAfterBreak(serverlevel, blockpos, ItemStack.EMPTY);
            ci.cancel();
        }

    }

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void injectDropResources(BlockState state, Level level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && entity instanceof Player player && tool.is(GTCoreTags.MAGNETIC_TOOL)) {
            getDrops(state, serverLevel, pos, blockEntity, entity, tool).forEach((itemStack) -> {
                gtcore$popResource(level, pos, itemStack, player);
            });
            state.spawnAfterBreak((ServerLevel)level, pos, tool);
            ci.cancel();
        }

    }

    @Unique
    private static void gtcore$popResource(Level pLevel, BlockPos pPos, ItemStack pStack, Player player) {
        float f = EntityType.ITEM.getHeight() / 2.0F;
        double d0 = (double)((float)pPos.getX() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25);
        double d1 = (double)((float)pPos.getY() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25) - (double)f;
        double d2 = (double)((float)pPos.getZ() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25, 0.25);
        gtcore$popResource(pLevel, () -> {
            return new ItemEntity(pLevel, d0, d1, d2, pStack);
        }, pStack, player);
    }

    @Unique
    private static void gtcore$popResource(Level pLevel, Supplier<ItemEntity> pItemEntitySupplier, ItemStack pStack, Player player) {
        if (!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            ItemEntity itementity = pItemEntitySupplier.get();
            itementity.setNoPickUpDelay();
            itementity.playerTouch(player);
            if (!itementity.isRemoved()){
                pLevel.addFreshEntity(itementity);
            }
        }
    }
}
