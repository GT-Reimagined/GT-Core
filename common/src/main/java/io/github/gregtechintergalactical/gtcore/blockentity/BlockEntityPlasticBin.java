package io.github.gregtechintergalactical.gtcore.blockentity;

import io.github.gregtechintergalactical.gtcore.data.SlotTypes;
import io.github.gregtechintergalactical.gtcore.machine.MassStorageMachine;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityPlasticBin extends BlockEntityMassStorage {
    int maxLimit = 128;
    public BlockEntityPlasticBin(MassStorageMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }

    public int getMaxLimit() {
        return maxLimit;
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable AntimatterToolType type) {
        if (type == AntimatterDefaultTools.WIRE_CUTTER){
            int addition = player.isCrouching() ? -64 : 64;
            maxLimit += addition;
            if (maxLimit == 0) {
                maxLimit = 1024;
            }
            if (maxLimit > 1024) {
                maxLimit = 64;
            }
            //TODO: translation component
            player.sendMessage(Utils.literal("Max capacity set to:  " + maxLimit), player.getUUID());
            this.sidedSync(true);
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            var handler = itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
            int amountToExtract = handler.getItem(0).getCount() - maxLimit;
            if (amountToExtract > 0) {
                ItemStack stored = handler.getItem(0);
                if (!stored.isEmpty()) {
                    if (amountToExtract > stored.getMaxStackSize()){
                        int toExtract = amountToExtract;
                        while (toExtract > 0){
                            ItemStack toAdd = Utils.ca(Math.min(stored.getMaxStackSize(), toExtract), stored);
                            toExtract -= toAdd.getCount();
                            if (!player.addItem(toAdd)){
                                player.drop(toAdd, true);
                            }
                        }
                    } else {
                        ItemStack toAdd = Utils.ca(amountToExtract, stored);
                        if (!player.addItem(toAdd)){
                            player.drop(toAdd, true);
                        }
                    }
                    handler.extractItem(0, amountToExtract, false);
                }
            }

            return InteractionResult.SUCCESS;
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        updateTag.putInt("maxLimit", maxLimit);
        return updateTag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        maxLimit = tag.getInt("maxLimit");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("maxLimit", maxLimit);
    }
}
