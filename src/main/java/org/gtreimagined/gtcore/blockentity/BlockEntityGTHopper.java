package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import org.gtreimagined.gtcore.machine.HopperItemHandler;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

public class BlockEntityGTHopper extends BlockEntityMaterial<BlockEntityGTHopper> implements ILimitedOutputTile{
    protected int stackLimit = 0;
    boolean observeStackLimit = false;
    boolean disabled = false;
    public BlockEntityGTHopper(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new HopperItemHandler(this));
    }

    @Override
    public Direction getFacing(BlockState state) {
        return state.getValue(BlockStateProperties.FACING_HOPPER);
    }

    public boolean setFacing(Direction side) {
        if (side == Direction.UP) return false;
        boolean isEmpty = this.coverHandler.map((ch) -> ch.get(side).isEmpty()).orElse(true);
        if (!isEmpty) return false;
        BlockState state = this.getBlockState().setValue(BlockStateProperties.FACING_HOPPER, side);
        this.getLevel().setBlockAndUpdate(this.getBlockPos(), state);
        this.invalidateCaps();
        return true;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (disabled || level.getGameTime() % 2 != 0) return;
        if (this.itemHandler.map(i -> !i.getHandler(SlotType.STORAGE).isEmpty()).orElse(false)) {
            BlockEntity neighbor = getCachedBlockEntity(this.getFacing());
            if (neighbor != null) {
                neighbor.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.getFacing().getOpposite()).ifPresent(adjHandler -> {
                    this.itemHandler.ifPresent(h -> Utils.transferItems(h.getHandler(SlotType.STORAGE), adjHandler, true));
                });
            }
        }
        BlockEntity above = getCachedBlockEntity(Direction.UP);
        if (above != null) {
            this.itemHandler.ifPresent(to -> {
                above.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(from -> {
                    Utils.transferItems(from, to.getHandler(SlotType.STORAGE), true);
                });
            });
        }
    }

    @Override
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        for (Direction direction : Direction.values()) {
            if (level.getSignal(this.getBlockPos().relative(direction), direction) > 0){
                this.disabled = true;
            }
        }
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
        Direction side = Utils.getOffsetFacing(this.getBlockPos(), neighbor);
        if (side != null){
            this.disabled = level.getSignal(neighbor, side) > 0;
        }
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable AntimatterToolType type) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(GTTools.SCREWDRIVER.getTag())){
            if (!observeStackLimit){
                if (stackLimit > 0 && stackLimit < 65){
                    observeStackLimit = true;
                } else {
                    stackLimit = player.isCrouching() ? 65 : 0;
                }
            }
            if (player.isCrouching()){
                stackLimit--;
            } else {
                stackLimit++;
            }
            if (stackLimit == 65 || stackLimit == 0){
                observeStackLimit = false;
                player.sendMessage(Utils.translatable("machine.gtcore.no_stack_limit"), player.getUUID());
            } else {
                player.sendMessage(Utils.translatable("machine.gtcore.stack_limit", stackLimit), player.getUUID());
            }
            stack.hurt(1, world.random, (ServerPlayer) player);
            return InteractionResult.SUCCESS;
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public int getStackLimit() {
        return stackLimit;
    }

    @Override
    public boolean hasStackLimit() {
        return observeStackLimit;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("stackLimit", stackLimit);
        tag.putBoolean("observeStackLimit", observeStackLimit);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        stackLimit = tag.getInt("stackLimit");
        observeStackLimit = tag.getBoolean("observeStackLimit");
    }
}
