package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.machine.MassStorageMachine;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockEntityPlasticBin extends BlockEntityMassStorage {
    int maxLimit = 128;
    public BlockEntityPlasticBin(MassStorageMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }

    @Override
    public int getMaxLimit() {
        return maxLimit;
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        if (type == GTTools.WIRE_CUTTER){
            maxLimit = player.isCrouching() ? maxLimit / 2 : maxLimit * 2;
            if (maxLimit == 32) {
                maxLimit = 1024;
            }
            if (maxLimit > 1024) {
                maxLimit = 64;
            }
            //TODO: translation component
            player.sendMessage(Utils.literal("Max capacity set to:  " + (maxLimit / 64) + " stacks"), player.getUUID());
            this.sidedSync(true);
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            var handler = itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
            int amountToExtract = handler.getStackInSlot(0).getCount() - maxLimit;
            if (amountToExtract > 0) {
                ItemStack stored = handler.getStackInSlot(0);
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

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> list = super.getInfo(simple);
        list.add("Max Capacity: " + (maxLimit / 64) + " stacks");
        return list;
    }
}
