package org.gtreimagined.gtcore.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityChest;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.types.Machine;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;


public class BlockMaterialChest extends BlockMachineMaterial implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    public BlockMaterialChest(Machine<?> type, Tier tier) {
        super(type, tier);
        type.setClientTicking();
    }


    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        BlockEntity tile = pLevel.getBlockEntity(pPos);
        if (tile instanceof BlockEntityChest chest){
            IItemHandler container = chest.itemHandler.map(i -> i.getHandler(SlotType.STORAGE)).orElse(null);
            if (container != null){
                return getRedstoneSignalFromContainer(container);
            }

        }
        return 0;
    }

    public static int getRedstoneSignalFromContainer(@Nullable IItemHandler inv) {
        if (inv == null) {
            return 0;
        } else {
            int i = 0;
            float f = 0.0F;

            for(int j = 0; j < inv.getSlots(); ++j) {
                ItemStack itemstack = inv.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)Math.min(inv.getSlotLimit(j), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f /= (float)inv.getSlots();
            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(prov.existing(GTCore.ID, "block/material_chest"));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level worldIn, BlockPos pos, int id, int param) {
        super.triggerEvent(state, worldIn, pos, id, param);
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    public static DoubleBlockCombiner.Combiner<BlockEntityChest, Float2FloatFunction> getLid(final LidBlockEntity p_226917_0_) {
        return new DoubleBlockCombiner.Combiner<BlockEntityChest, Float2FloatFunction>() {
            @Override
            public Float2FloatFunction acceptDouble(BlockEntityChest p_225539_1_, BlockEntityChest p_225539_2_) {
                return (p_226921_2_) -> {
                    return Math.max(p_225539_1_.getOpenNess(p_226921_2_), p_225539_2_.getOpenNess(p_226921_2_));
                };
            }

            @Override
            public Float2FloatFunction acceptSingle(BlockEntityChest p_225538_1_) {
                return p_225538_1_::getOpenNess;
            }

            @Override
            public Float2FloatFunction acceptNone() {
                return p_226917_0_::getOpenNess;
            }
        };
    }

    public DoubleBlockCombiner.NeighborCombineResult<? extends BlockEntityChest> getWrapper(BlockState blockState, Level world, BlockPos blockPos, boolean p_225536_4_) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate;
        if (p_225536_4_) {
            biPredicate = (p_226918_0_, p_226918_1_) -> false;
        }
        else {
            biPredicate = BlockEntityChest::isChestBlockedAt;
        }

        return DoubleBlockCombiner.combineWithNeigbour(((BlockEntityType<BlockEntityChest>)this.getType().getTileType()), BlockMaterialChest::getMergerType, BlockMaterialChest::getDirectionToAttached, BlockStateProperties.HORIZONTAL_FACING, blockState, world, blockPos, biPredicate);
    }

    public static DoubleBlockCombiner.BlockType getMergerType(BlockState blockState) {
        return DoubleBlockCombiner.BlockType.SINGLE;
    }

    public static Direction getDirectionToAttached(BlockState state) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return direction.getCounterClockWise();
    }
}
