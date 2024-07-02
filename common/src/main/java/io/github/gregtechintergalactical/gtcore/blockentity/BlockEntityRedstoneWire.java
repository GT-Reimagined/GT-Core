package io.github.gregtechintergalactical.gtcore.blockentity;

import io.github.gregtechintergalactical.gtcore.block.BlockRedstoneWire;
import io.github.gregtechintergalactical.gtcore.block.RedstoneWire;
import io.github.gregtechintergalactical.gtcore.cover.CoverRedstoneTorch;
import muramasa.antimatter.Antimatter;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.blockentity.pipe.BlockEntityPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.cover.ICover;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.CodeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlockEntityRedstoneWire<T extends RedstoneWire<T>> extends BlockEntityPipe<T> {
    int state = 0;
    byte mReceived = 6, mMode = 0;
    public long mRedstone = 0;
    public boolean mConnectedToNonWire = true;
    public static final long MAX_RANGE = Integer.MAX_VALUE;
    public final long mLoss;
    public BlockEntityRedstoneWire(T type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        mLoss = MAX_RANGE / type.getRange();
    }

    @Override
    public boolean validate(Direction dir) {
        return true;
    }

    @Override
    public CoverFactory[] getValidCovers() {
        return AntimatterAPI.all(CoverFactory.class).stream().filter(t -> {
            try {
                ICover cover = t.get().get(ICoverHandler.empty(this), t.getValidTier(), Direction.SOUTH, t);
                return !cover.isNode() || cover instanceof CoverRedstoneTorch;
            } catch (Exception ex) {
                return false;
            }
        }).toArray(CoverFactory[]::new);
    }

    @Override
    public void onFirstTick() {
        super.onFirstTick();
        updateConnectionStatus();
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
    }

    @Override
    public void toggleConnection(Direction side) {
        super.toggleConnection(side);
        boolean oldConnectedToNonWire = mConnectedToNonWire;
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
        if (mConnectedToNonWire || oldConnectedToNonWire) updateBlock(side);
    }

    @Override
    public Class<?> getCapClass() {
        return Redstone.class;
    }

    @Override
    protected void register() {
    }

    @Override
    protected boolean deregister() {
        return false;
    }

    public int getState() {
        return state;
    }

    public int getWeakPower(Direction side){
        ICover cover = coverHandler.map(c -> c.get(side)).orElse(ICover.empty);
        if (cover.isNode()){
            return cover.getWeakPower();
        }
        Block block = level.getBlockState(this.getBlockPos().relative(side)).getBlock();
        if (block instanceof BlockRedstoneWire<?>) return 0;
        boolean connects = connects(side);
        if (mRedstone <= 0 || !connects) return 0;
        return CodeUtils.bind4(CodeUtils.divup(mRedstone, MAX_RANGE)) - 1;
    }

    public int getStrongPower(Direction side){
        ICover cover = coverHandler.map(c -> c.get(side)).orElse(ICover.empty);
        if (cover.isNode()){
            return cover.getStrongPower();
        }
        Block block = level.getBlockState(this.getBlockPos().relative(side)).getBlock();
        if (block instanceof BlockRedstoneWire<?>) return 0;
        boolean connects = connects(side);
        if (mRedstone <= 0 || !connects) return 0;
        return CodeUtils.bind4(CodeUtils.divup(mRedstone, MAX_RANGE)) - 1;
    }

    public int getComparatorInputOverride(byte aSide) {
        return CodeUtils.bind4(mRedstone / MAX_RANGE);
    }

    public long getRedstoneLoss() {
        return mLoss;
    }
    public long getRedstoneValue() {return mRedstone;}
    public long getRedstoneMinusLoss() {return mRedstone - mLoss;}

    public void updateConnectionStatus() {
        mConnectedToNonWire = false;
        for (Direction tSide : Direction.values()) {
            if (connects(tSide) && !(getCachedBlockEntity(tSide) instanceof BlockEntityRedstoneWire<?>)) mConnectedToNonWire = true;
        }
    }

    public long getRedstoneAtSide(int aSide) {
        if (aSide < 0 || aSide > 5) return 0;
        Direction side = Direction.from3DDataValue(aSide);
        if (!connects(side)) return 0;
        BlockEntity tDelegator = getCachedBlockEntity(side);
        if (tDelegator instanceof BlockEntityRedstoneWire<?> wire) return connects(side) && wire.connects(side.getOpposite()) ? wire.getRedstoneMinusLoss() : 0;
        // Do not accept Redstone coming from any Redstone Sink! (Such as Droppers or Dispensers)
        //if (REDSTONE_SINKS.contains(tDelegator.getBlock())) return 0;
        int redstoneLevel = level.getSignal(this.getBlockPos().relative(side), side);
        return (long) (MAX_RANGE * redstoneLevel) - mLoss;
    }

    private void updateBlock(Direction side){
        AntimatterPlatformUtils.markAndNotifyBlock(getLevel(), getBlockPos(), getLevel().getChunkAt(getBlockPos()), getBlockState(), getBlockState(), 1, 512);
        BlockPos neighbor = getBlockPos().relative(side);
        BlockState neighborState = getLevel().getBlockState(neighbor);
        getLevel().updateNeighborsAtExceptFromFacing(neighbor, neighborState.getBlock(), side.getOpposite());
    }

    public boolean updateRedstone() {

        long oRedstone = mRedstone, tRedstone = mMode * MAX_RANGE - mLoss;
        byte oReceived = mReceived;
        if ((mRedstone = getRedstoneAtSide(oReceived)) <= tRedstone) {
            mRedstone = tRedstone;
            mReceived = 6;
        }
        List<Direction> sidesToUpdate = new ArrayList<>();
        for (Direction tSide : Direction.values()) {
            if (!(getLevel().getBlockState(getBlockPos().relative(tSide)).getBlock() instanceof BlockRedstoneWire)) {
                sidesToUpdate.add(tSide);
            }
            if (tSide.get3DDataValue() == oReceived) continue;
            if ((tRedstone = getRedstoneAtSide(tSide.get3DDataValue())) > mRedstone) {
                mRedstone = tRedstone; mReceived = (byte) tSide.get3DDataValue();
            }
        }
        if (mRedstone != oRedstone) {
            sidedSync(true);
            if (mConnectedToNonWire) {
                for (Direction tSide : sidesToUpdate) {
                    updateBlock(tSide);
                }
            }
            return true;
        }
        return false;
    }


    public static void doRedstoneUpdate(BlockEntityRedstoneWire<?> aTileEntity) {
        HashSet<BlockEntityRedstoneWire<?>> tSetUpdating = new HashSet<>(List.of(aTileEntity)), tSetNext = new HashSet<>();
        while (!tSetUpdating.isEmpty()) {
            for (BlockEntityRedstoneWire<?> tTileEntity : tSetUpdating) {
                for (Direction tSide : Direction.values()) {
                    if (tTileEntity.connects(tSide)) {
                        BlockEntity tDelegator = tTileEntity.getCachedBlockEntity(tSide);
                        if (tDelegator instanceof BlockEntityRedstoneWire<?> wire && wire.connects(tSide.getOpposite()) && wire.updateRedstone()) {
                            tSetNext.add(wire);
                        }
                    }
                }
            }
            tSetUpdating.clear();
            tSetUpdating.addAll(tSetNext);
            tSetNext.clear();
        }
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> info = super.getInfo(simple);
        if (!simple) {
            info.add("Redstone level " + mRedstone);
        }
        info.add("Vanilla redstone power: " + CodeUtils.bind4(CodeUtils.divup(mRedstone, MAX_RANGE)));
        return info;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("mRedstone", mRedstone);
        tag.putByte("mMode", mMode);
        tag.putByte("mReceived", mReceived);
        tag.putBoolean("mConnectedToNonWire", mConnectedToNonWire);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        mRedstone = tag.getLong("mRedstone");
        mMode = tag.getByte("mMode");
        mReceived = tag.getByte("mReceived");
        mConnectedToNonWire = tag.getBoolean("mConnectedToNonWire");
        if (this.getLevel() != null && this.isClientSide()){
            sidedSync(true);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        updateTag.putLong("mRedstone", mRedstone);
        updateTag.putBoolean("mConnectedToNonWire", mConnectedToNonWire);
        return updateTag;
    }
}
