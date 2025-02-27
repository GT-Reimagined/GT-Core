package org.gtreimagined.gtcore.block;

import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.datagen.builder.VariantBlockStateBuilder.VariantBuilder;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING_HOPPER;

public class BlockGTHopper extends BlockMachineMaterial {
    public static final VoxelShape TOP = Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape FUNNEL = Block.box(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
    public static final VoxelShape BASE = Shapes.or(TOP, FUNNEL);
    public static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
    public static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
    public static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
    public static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));

    public BlockGTHopper(Machine<?> type, Tier tier) {
        super(type, tier);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING_HOPPER, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
       builder.add(FACING_HOPPER);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext collisionContext && collisionContext.getEntity() instanceof Player player){
            if (Utils.isPlayerHolding(player, InteractionHand.MAIN_HAND, AntimatterDefaultTools.WRENCH)){
                return Shapes.block();
            }
        }
        return switch (state.getValue(FACING_HOPPER)) {
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> BASE;
        };
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACING_HOPPER, direction.getAxis() == Axis.Y ? Direction.DOWN : direction);
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.getBuilder(item).parent(prov.existing(GTCore.ID, "block/hopper")).texture("side", new Texture(GTCore.ID, "block/machine/base/hopper/side")).texture("bottom", new Texture(GTCore.ID, "block/machine/base/hopper/bottom")).texture("top", new Texture(GTCore.ID, "block/machine/base/hopper/top"));
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.getVariantBuilder(block).forAllStates(b -> {
            VariantBuilder builder = new VariantBuilder();
            Direction facing = b.getValue(FACING_HOPPER);
            String hopper = facing == Direction.DOWN ? "" : "_side";
            builder.modelFile(new ResourceLocation(GTCore.ID, "block/hopper" + hopper));
            if (facing == Direction.EAST || facing == Direction.WEST || facing == Direction.SOUTH) {
                builder.rotationY(facing == Direction.EAST ? 90 : facing == Direction.SOUTH ? 180 : 270);
            }
            return builder;
        });
    }
}
