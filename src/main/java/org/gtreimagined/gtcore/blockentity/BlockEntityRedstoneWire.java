package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;
import org.gtreimagined.gtcore.block.BlockRedstoneWire;
import org.gtreimagined.gtcore.block.RedstoneWire;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.util.CodeUtils;

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
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
        if (mConnectedToNonWire) {
            for (Direction tSide : Direction.values()) {
                if (connects(tSide) && getPipe(tSide) == null) {
                    updateBlock(tSide);
                }
            }
        }
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
        if (level.getBlockState(neighbor).getBlock() instanceof BlockRedstoneWire<?>) return;
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
    }

    @Override
    public void toggleConnection(Direction side) {
        boolean oldConnectedToNonWire = mConnectedToNonWire;
        super.toggleConnection(side);
        if (mConnectedToNonWire || oldConnectedToNonWire) updateBlock(side);
    }

    @Override
    public void setConnection(Direction side) {
        super.setConnection(side);
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
    }

    @Override
    public void clearConnection(Direction side) {
        super.clearConnection(side);
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
    }

    @Override
    public boolean onCoverUpdate(boolean remove, boolean hasNonEmpty, Direction side, ICover old, ICover stack) {
        boolean oldConnectedToNonWire = mConnectedToNonWire;
        boolean update = super.onCoverUpdate(remove, hasNonEmpty, side, old, stack);
        updateConnectionStatus();
        if (updateRedstone()) doRedstoneUpdate(this);
        if (mConnectedToNonWire || oldConnectedToNonWire) updateBlock(side);
        return update;
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
        return getPower(side);
    }

    public int getStrongPower(Direction side){
        ICover cover = coverHandler.map(c -> c.get(side)).orElse(ICover.empty);
        if (cover.isNode()){
            return cover.getStrongPower();
        }
        return getPower(side);
    }

    private int getPower(Direction side) {
        if (side.get3DDataValue() == mReceived) return 0;
        BlockState blockState = level.getBlockState(this.getBlockPos().relative(side));
        Block block = blockState.getBlock();
        if (block instanceof BlockRedstoneWire<?>) return 0;
        boolean connects = connects(side);
        if (mRedstone <= 0 || !connects) return 0;
        return getVanillaRedstonePower() - (blockState.is(Blocks.REDSTONE_WIRE) || blockState.isCollisionShapeFullBlock(level, this.getBlockPos().relative(side)) ? 1 : 0);
    }

    public int getComparatorInputOverride(byte aSide) {
        return CodeUtils.bind4(mRedstone / MAX_RANGE);
    }

    public long getRedstoneLoss() {
        return mLoss;
    }
    public long getRedstoneValue() {return mRedstone;}
    public long getRedstoneMinusLoss() {return mRedstone - mLoss;}

    public int getVanillaRedstonePower(){
        return CodeUtils.bind4(CodeUtils.divup(mRedstone, MAX_RANGE));
    }

    public void updateConnectionStatus() {
        mConnectedToNonWire = false;
        for (Direction tSide : Direction.values()) {
            if ((connects(tSide) || coverHandler.map(c -> c.get(tSide).getWeakPower() >= 0).orElse(false)) && !(getCachedBlockEntity(tSide) instanceof BlockEntityRedstoneWire<?>)) mConnectedToNonWire = true;
        }
    }

    public long getRedstoneAtSide(int aSide) {
        if (aSide < 0 || aSide > 5) return 0;
        Direction side = Direction.from3DDataValue(aSide);
        if (!connects(side)) return 0;
        BlockEntity tDelegator = getCachedBlockEntity(side);
        if (tDelegator instanceof BlockEntityRedstoneWire<?> wire) return connects(side) && wire.connects(side.getOpposite()) ? wire.getRedstoneMinusLoss() : 0;
        BlockState state = level.getBlockState(getBlockPos().relative(side));
        // Do not accept Redstone coming from any Redstone Sink! (Such as Droppers or Dispensers)
        //if (REDSTONE_SINKS.contains(tDelegator.getBlock())) return 0;
        int redstoneLevel = level.getSignal(this.getBlockPos().relative(side), side);
        return (MAX_RANGE * redstoneLevel) - (getPipeType().getRange() == 1 && state.getBlock() != Blocks.REDSTONE_WIRE ? 0 : mLoss);
    }

    private void updateBlock(Direction side){
        getLevel().markAndNotifyBlock(getBlockPos(), getLevel().getChunkAt(getBlockPos()), getBlockState(), getBlockState(), 1, 512);
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
            if (type.isEmitsLight() && level != null && size == PipeSize.VTINY){
                int lightLevel = getBlockState().getValue(BlockRedstoneWire.LIGHT);
                if (getVanillaRedstonePower() != lightLevel){
                    level.setBlock(this.getBlockPos(), this.getBlockState().setValue(BlockRedstoneWire.LIGHT, getVanillaRedstonePower()), 0);
                }
            }
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

    public void setMode(int mode){
        int oldMode = mMode;
        mMode = CodeUtils.bind4(mode);
        if (oldMode != mMode) {
            updateConnectionStatus();
            if (updateRedstone()) doRedstoneUpdate(this);
        }
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> info = super.getInfo(simple);
        if (!simple) {
            info.add("Internal redstone value " + mRedstone);
        }
        info.add("Redstone power: " + getVanillaRedstonePower());
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
            coverHandler.ifPresent(c -> c.coverTexturer.forEach((d, t) -> t.invalidate()));
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
