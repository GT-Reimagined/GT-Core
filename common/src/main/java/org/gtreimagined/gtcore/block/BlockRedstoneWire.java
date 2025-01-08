package org.gtreimagined.gtcore.block;

import muramasa.antimatter.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
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
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockRedstoneWire<T extends RedstoneWire<T>> extends BlockPipe<T> {
    public static final int INSULATION_COLOR = 0x604040;
    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);
    protected final StateDefinition<Block, BlockState> stateContainer;
    public BlockRedstoneWire(T type, PipeSize size) {
        super(type.getId(), type, size, 2, Properties.of(Data.WRENCH_MATERIAL).strength(1.0f, 3.0f).requiresCorrectToolForDrops().emissiveRendering(((blockState, blockGetter, blockPos) -> isEmissive(size, blockState, blockGetter, blockPos))).lightLevel(BlockRedstoneWire::getLightEmission));
        String prefix = size == PipeSize.TINY ? "cable" : "wire";
        this.side = new Texture(Ref.ID, "block/pipe/" + prefix + "_side");
        this.faces = new Texture[]{
                new Texture(Ref.ID, "block/pipe/" + "wire_vtiny"),
                new Texture(Ref.ID, "block/pipe/" + "wire_tiny"),
                new Texture(Ref.ID, "block/pipe/" + "wire_small"),
                new Texture(Ref.ID, "block/pipe/" + "wire_normal"),
                new Texture(Ref.ID, "block/pipe/" + "wire_large"),
                new Texture(Ref.ID, "block/pipe/" + "wire_huge")
        };
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBlockStateDefinition(builder);
        this.stateContainer = builder.create(Block::defaultBlockState, BlockState::new);
        if (type.emitsLight && size == PipeSize.VTINY){
            this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(TICKING, false).setValue(LIGHT, 0));
        } else {
            this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(TICKING, false));
        }
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        if (this.stateContainer != null) return stateContainer;
        return super.getStateDefinition();
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        if (this.type == null) return;
        if (type.emitsLight && size == PipeSize.VTINY) {
            builder.add(LIGHT);
        }
    }

    private static boolean isEmissive(PipeSize size, BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return size == PipeSize.VTINY && blockGetter.getBlockEntity(blockPos) instanceof BlockEntityRedstoneWire<?> wire && wire.getRedstoneValue() > 0;
    }

    public static int getLightEmission(BlockState state) {
        if (state.hasProperty(LIGHT)) return state.getValue(LIGHT);
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Utils.translatable("tooltip.gtcore.redstone_wire_range", type.getRange()));
        if (type.isEmitsLight() && size == PipeSize.VTINY) {
            tooltip.add(Utils.translatable("tooltip.gtcore.redstone_wire_light"));
        }
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
        if (world == null || pos == null) return -1;
        BlockEntityPipe<?> pipe = getTilePipe(world, pos);
        if (!(pipe instanceof BlockEntityRedstoneWire<?> redstoneWire)) return -1;
        if (size == PipeSize.TINY && pipe.getPipeColor() != -1 && i == 0) return pipe.getPipeColor();
        if (i == 1 && redstoneWire.getRedstoneValue() > 0){
            return getType().getOnColor();
        }
        if (size == PipeSize.TINY) return i == 1 ? getRGB() : i == 0 ? INSULATION_COLOR : -1;
        return i == 0 || i == 1 ? getRGB() : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        if (size == PipeSize.TINY && stack.getTag() != null && stack.getTag().contains(Ref.KEY_PIPE_TILE_COLOR) && i == 0){
            return stack.getTag().getInt(Ref.KEY_PIPE_TILE_COLOR);
        }
        return size == PipeSize.TINY ? i == 1 ? getRGB() : i == 0 ? INSULATION_COLOR : -1 : getRGB();
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityRedstoneWire<?> wire){
            return wire.getWeakPower(direction == null ? null : direction.getOpposite());
        }
        return super.getSignal(state, level, pos, direction);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityRedstoneWire<?> wire){
            return wire.getStrongPower(direction == null ? null : direction.getOpposite());
        }
        return super.getDirectSignal(state, level, pos, direction);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return super.getAnalogOutputSignal(state, level, pos);
    }
}
