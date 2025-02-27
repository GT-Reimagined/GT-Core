package org.gtreimagined.gtcore.tree.block;

import muramasa.antimatter.datagen.builder.VariantBlockStateBuilder;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.tree.ResinState;

import java.util.Random;

import static muramasa.antimatter.data.AntimatterDefaultTools.KNIFE;

public class BlockRubberLog extends BlockRubberWood {

    public static final DirectionProperty RESIN_FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final double CHANCE_FILL = 0.3;

    public BlockRubberLog(String domain, String id) {
        super(domain, id, Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD).randomTicks());
        registerDefaultState(defaultBlockState().setValue(ResinState.INSTANCE, ResinState.NONE).setValue(RESIN_FACING, Direction.NORTH).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ResinState.INSTANCE, RESIN_FACING, AXIS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (state.getValue(ResinState.INSTANCE) != ResinState.EMPTY) {
            return;
        }
        if (random.nextDouble() < CHANCE_FILL) {
            worldIn.setBlock(pos, state.setValue(ResinState.INSTANCE, ResinState.FILLED), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (Utils.isPlayerHolding(player, handIn, KNIFE) && state.getValue(ResinState.INSTANCE) == ResinState.FILLED && !worldIn.isClientSide) {
            worldIn.setBlock(pos, state.setValue(ResinState.INSTANCE, ResinState.EMPTY), 3);
            Direction dir = state.getValue(RESIN_FACING);
            BlockPos spawnPos = pos.offset(dir.getStepX(), dir.getStepY(), dir.getStepZ());
            Containers.dropItemStack(worldIn, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), GTCoreItems.StickyResin.get(1));
            if (worldIn.random.nextDouble() > 0.5) {
                Containers.dropItemStack(worldIn, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), GTCoreItems.StickyResin.get(1));
            }
            player.getItemInHand(handIn).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
            return InteractionResult.SUCCESS;
        }
        if (this == GTCoreBlocks.RUBBER_LOG){
            ItemStack stack = player.getItemInHand(handIn);
            if (stack.getItem() instanceof DiggerItem diggerItem && diggerItem.getDestroySpeed(stack, state) > 1.0f){
                worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!worldIn.isClientSide){
                    BlockState target = GTCoreBlocks.STRIPPED_RUBBER_LOG.defaultBlockState().setValue(AXIS, state.getValue(AXIS)).setValue(ResinState.INSTANCE, state.getValue(ResinState.INSTANCE)).setValue(RESIN_FACING, state.getValue(RESIN_FACING));
                    worldIn.setBlockAndUpdate(pos, target);
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(RESIN_FACING, context.getHorizontalDirection().getOpposite()).setValue(ResinState.INSTANCE, ResinState.NONE);
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        ResourceLocation rubberLog = prov.existing(GTCore.ID, "block/" + id);
        ResourceLocation rubberLogEmpty = prov.existing(GTCore.ID, "block/" + id + "_empty");
        ResourceLocation rubberLogFilled = prov.existing(GTCore.ID, "block/" + id + "_filled");
        prov.getVariantBuilder(block).forAllStates(s ->
                new VariantBlockStateBuilder.VariantBuilder().modelFile(s.getValue(ResinState.INSTANCE) == ResinState.NONE ? rubberLog : s.getValue(ResinState.INSTANCE) == ResinState.EMPTY ? rubberLogEmpty : rubberLogFilled)
                        .rotationY((int) s.getValue(RESIN_FACING).getOpposite().toYRot())
                        .rotationX(s.getValue(AXIS) == Direction.Axis.Y ? 0 : 90)
        );
    }
}
