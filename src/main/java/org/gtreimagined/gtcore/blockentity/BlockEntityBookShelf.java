package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.BookRegistration;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.item.ITrackedHandler;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineCoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.gtreimagined.gtcore.GTCoreCodeUtils.*;

public class BlockEntityBookShelf extends BlockEntityMachine<BlockEntityBookShelf> implements IFilterableHandler {
    public BlockEntityBookShelf(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            protected TrackedItemHandler<BlockEntityBookShelf> createTrackedHandler(SlotType<?> type, BlockEntityBookShelf tile) {
                if (type == SlotType.STORAGE){
                    int count = tile.getMachineType().getCount(tile.getMachineTier(), type);
                    return new TrackedItemHandler<>(tile, type, count, type.output, type.input, type.tester, 1);
                }
                return super.createTrackedHandler(type, tile);
            }
        });
        this.coverHandler.set(() -> new MachineCoverHandler<>(this){
            @Override
            public boolean placeCover(Player player, Direction side, ItemStack stack, ICover cover) {
                if (side == this.getTileFacing() || side == this.getTileFacing().getOpposite()) return false;
                return super.placeCover(player, side, stack, cover);
            }
        });
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        Direction face = hit.getDirection();
        Vec3 vec = hit.getLocation();
        var handler = itemHandler.map(i -> i.getHandler(SlotType.STORAGE)).orElse(null);
        if ((face == this.getFacing() || face == this.getFacing().getOpposite()) && handler != null) {
            double[] coords = getFacingCoordsClicked(face, vec.x()-hit.getBlockPos().getX(), vec.y()-hit.getBlockPos().getY(), vec.z() - hit.getBlockPos().getZ());
            if (coords[0] >= PX_P[1] && coords[0] <= PX_N[1] && coords[1] >= PX_P[1] && coords[1] <= PX_N[1]) {
                if (face == this.getFacing()) {
                    int slot = (coords[1] < PX_P[8]? 0:7)+(int) bind_(0, 6, (long)(8 * (coords[0] - PX_P[1])));
                    if (switchBooks(player, slot, handler, hand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
                if (face == this.getFacing().getOpposite()) {
                    int slot = (coords[1] < PX_P[8]?14:21)+(int)bind_(0, 6, (long)(8*(coords[0]-PX_P[1])));
                    if (switchBooks(player, slot, handler, hand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    private boolean switchBooks(Player player, int slot, ITrackedHandler handler, InteractionHand hand) {
        ItemStack current = handler.getStackInSlot(slot);
        if (!current.isEmpty()){
            if (!player.isCrouching()){
                /*if (OD.button.is(slot(aSlot))) {
                    mRedstoneDelay = 120;
                    causeBlockUpdate();
                    playClick();
                    return T;
                }
                if (OD.lever.is(slot(aSlot)) || ST.equal(slot(aSlot), Blocks.redstone_torch)) {
                    if (mRedstoneDelay == 0) mRedstoneDelay = -1; else mRedstoneDelay = 0;
                    causeBlockUpdate();
                    playClick();
                    return T;
                }*/
            }
            if (player.addItem(current.copy())){
                handler.setStackInSlot(slot, ItemStack.EMPTY);
                sidedSync(true);
                return true;
            }
        } else {
            ItemStack held = player.getItemInHand(hand);
            if (!held.isEmpty() && BookRegistration.getTextureMap().containsKey(held.getItem())){
                handler.setStackInSlot(slot, Utils.ca(1, held));
                held.shrink(1);
                sidedSync(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setFacing(Direction side) {
        if (coverHandler.map(c -> !c.get(side).isEmpty()).orElse(false)) return false;
        return super.setFacing(side);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        itemHandler.ifPresent(e -> {
            CompoundTag in = new CompoundTag();
            e.getAll().forEach((f,i) -> {
                in.put(f.getId(), serializeWithEmpty(i, new CompoundTag()));
            });
            nbt.put(Ref.KEY_MACHINE_ITEMS, in);
        });
        return nbt;
    }

    public CompoundTag serializeWithEmpty(IItemHandler container, CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();


        for(int i = 0; i < container.getSlots(); ++i) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("Slot", i);
            container.getStackInSlot(i).save(itemTag);
            itemTag.putInt("count", container.getStackInSlot(i).getCount());
            nbtTagList.add(itemTag);
        }

        nbt.put("Items", nbtTagList);
        return nbt;
    }

    @Override
    public boolean canPlayerOpenGui(Player playerEntity) {
        return playerEntity.isCreative();
    }

    @Override
    public boolean test(SlotType<?> slotType, int i, ItemStack itemStack) {
        return BookRegistration.getTextureMap().containsKey(itemStack.getItem());
    }

    public int getEnchantmentPowerBonus(){
        return itemHandler.map(i -> {
            int total = 0;
            IItemHandler handler = i.getHandler(SlotType.STORAGE);
            for (int j = 0; j < handler.getSlots(); j++) {
                if (handler.getStackInSlot(j).is(GTCoreTags.BOOKS_ENCHANTED)){
                    total +=2;
                }
                if (handler.getStackInSlot(j).is(GTCoreTags.BOOKS_NORMAL)){
                    total++;
                }
            }
            return total;
        }).orElse(0);
    }
}
