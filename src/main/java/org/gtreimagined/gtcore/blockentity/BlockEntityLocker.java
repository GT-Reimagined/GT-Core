package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.capability.machine.MachineCoverHandler;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class BlockEntityLocker extends BlockEntityMaterial<BlockEntityLocker> {
    public BlockEntityLocker(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        coverHandler.set(() -> new MachineCoverHandler<>(this) {
            @Override
            public boolean placeCover(Player player, Direction side, ItemStack stack, ICover cover) {
                return false;
            }
        });
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        if (player.getItemInHand(hand).isEmpty() && hit.getDirection() == this.getFacing()){
            this.itemHandler.ifPresent(h -> {
                for (int i = 0; i < 4; i++){
                    ItemStack armorStack = player.getItemBySlot(getSlot(i));
                    SlotType<?> slotType = this.type.getId().contains("charging") ? SlotType.ENERGY : SlotType.STORAGE;
                    ItemStack inventoryStack = h.getHandler(slotType).getStackInSlot(i);
                    if (!armorStack.isEmpty() && !inventoryStack.isEmpty()){
                        ItemStack copy = armorStack.copy();
                        ItemStack copy1 = inventoryStack.copy();
                        armorStack.shrink(armorStack.getCount());
                        inventoryStack.shrink(inventoryStack.getCount());
                        player.setItemSlot(getSlot(i), copy1);
                        h.getHandler(slotType).setStackInSlot(i, copy);
                    } else if (!armorStack.isEmpty()){
                        h.getHandler(slotType).setStackInSlot(i, armorStack.copy());
                        armorStack.shrink(armorStack.getCount());
                    } else if (!inventoryStack.isEmpty()){
                        player.setItemSlot(getSlot(i), inventoryStack.copy());
                        inventoryStack.shrink(inventoryStack.getCount());
                    }
                }
            });
            world.playSound(null, this.getBlockPos(), SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public boolean canPlayerOpenGui(Player player) {
        return player.isCreative();
    }

    @Override
    public <V> boolean blocksCapability(@NotNull Class<V> cap, Direction side) {
        return super.blocksCapability(cap, side) || cap == IItemHandler.class;
    }

    private EquipmentSlot getSlot(int slot){
        if (slot == 0){
            return EquipmentSlot.HEAD;
        } else if (slot == 1){
            return EquipmentSlot.CHEST;
        } else if (slot == 2){
            return EquipmentSlot.LEGS;
        } else {
            return EquipmentSlot.FEET;
        }
    }
}
