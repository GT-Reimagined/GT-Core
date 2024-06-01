package io.github.gregtechintergalactical.gtcore.block;

import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityRedstoneWire;
import muramasa.antimatter.Data;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.pipe.BlockEntityPipe;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.pipe.BlockPipe;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.CodeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockRedstoneWire<T extends RedstoneWire<T>> extends BlockPipe<T> {
    public BlockRedstoneWire(T type, PipeSize size) {
        super(type.getId(), type, size, 2, Properties.of(Data.WRENCH_MATERIAL).strength(1.0f, 3.0f).requiresCorrectToolForDrops().emissiveRendering(((blockState, blockGetter, pos) -> isEmissive(size, blockState, blockGetter, pos))));
        String prefix = size == PipeSize.TINY ? "cable" : "wire";
        this.side = new Texture(Ref.ID, "block/pipe/" + prefix + "_side");
        this.faces = new Texture[]{
                new Texture(Ref.ID, "block/pipe/" + prefix + "_vtiny"),
                new Texture(Ref.ID, "block/pipe/" + prefix + "_tiny"),
                new Texture(Ref.ID, "block/pipe/" + prefix + "_small"),
                new Texture(Ref.ID, "block/pipe/" + prefix + "_normal"),
                new Texture(Ref.ID, "block/pipe/" + prefix + "_large"),
                new Texture(Ref.ID, "block/pipe/" + prefix + "_huge")
        };
    }

    private static boolean isEmissive(PipeSize size, BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return size == PipeSize.VTINY && blockGetter.getBlockEntity(blockPos) instanceof BlockEntityRedstoneWire<?> wire && wire.getRedstoneValue() > 0;
    }

    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (getType().emitsLight && getSize() == PipeSize.VTINY && level.getBlockEntity(pos) instanceof BlockEntityRedstoneWire<?> wire && wire.getState() > 0){
            return CodeUtils.bind4(CodeUtils.divup(wire.getRedstoneValue(), BlockEntityRedstoneWire.MAX_RANGE));
        }
        return 0;
    }

    @Override
    public AntimatterToolType getToolType() {
        return AntimatterDefaultTools.WIRE_CUTTER;
    }

    @Override
    public boolean onBlockPlacedTo(Level world, BlockPos pos, Direction face) {
        BlockEntityPipe<?> tile = getTilePipeRedstone(world, pos);
        if (tile != null && !world.isClientSide()) {
            BlockEntityPipe<?> side = tile.getPipe(face.getOpposite());
            if (side != null && side.blocksSide(face)) return false;
            tile.setConnection(face.getOpposite());
            return true;
        }
        return false;
    }

    protected static BlockEntityRedstoneWire<?> getTilePipeRedstone(BlockGetter world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        return tile instanceof BlockEntityRedstoneWire<?> wire ? wire : null;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        if (world != null && pos != null){
            if (world.getBlockEntity(pos) instanceof BlockEntityRedstoneWire<?> redstoneWire){
                if (redstoneWire.getRedstoneValue() > 0){
                    return getType().getOnColor();
                }
            }
        }
        return super.getBlockColor(state, world, pos, i);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityRedstoneWire<?> wire){
            return wire.getWeakPower(direction);
        }
        return super.getSignal(state, level, pos, direction);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityRedstoneWire<?> wire){
            return wire.getStrongPower(direction);
        }
        return super.getDirectSignal(state, level, pos, direction);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return super.getAnalogOutputSignal(state, level, pos);
    }
}
