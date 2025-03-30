package org.gtreimagined.gtcore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntitySapBag;
import org.gtreimagined.gtcore.client.BakedModels;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.datagen.builder.DynamicConfigBuilder;
import org.gtreimagined.gtlib.datagen.builder.GTBlockModelBuilder;
import org.gtreimagined.gtlib.datagen.builder.VariantBlockStateBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.dynamic.BlockDynamic;
import org.gtreimagined.gtlib.dynamic.ModelConfig;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.google.common.collect.ImmutableMap.of;
import static net.minecraft.core.Direction.getNearest;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class BlockSapBag extends BlockDynamic implements SimpleWaterloggedBlock, EntityBlock {
    protected ModelConfig config = new ModelConfig();
    final VoxelShape[] SHAPES;
    final Texture[] TEXTURES;
    public BlockSapBag() {
        super(GTCore.ID, "sap_bag", Properties.of(Material.GRASS, MaterialColor.TERRACOTTA_RED).noOcclusion().strength(0.5F).sound(SoundType.WOOL));
        SHAPES = setBlockBounds2();
        TEXTURES = new Texture[]{new Texture(GTCore.ID, "block/sapbag/bottom"), new Texture(GTCore.ID, "block/sapbag/top"), new Texture(GTCore.ID, "block/sapbag/side"), new Texture(GTCore.ID, "block/sapbag/top_filled")};
    }

    private VoxelShape[] setBlockBounds2() {
        AABB north = new AABB(0.3125F, 0, 0, 0.6875, 0.4375, 0.375);
        AABB south = new AABB(0.3125F, 0, 0.625F, 0.6875, 0.4375, 1);
        AABB west = new AABB(0, 0, 0.3125F, 0.375, 0.4375, 0.6875);
        AABB east = new AABB(0.625F, 0, 0.3125F, 1, 0.4375, 0.6875);
        return new VoxelShape[]{Shapes.create(south), Shapes.create(west), Shapes.create(north), Shapes.create(east)};
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(HORIZONTAL_FACING).get2DDataValue()];
    }

    @Override
    public ModelConfig getConfig(BlockState state, BlockGetter world, BlockPos.MutableBlockPos mut, BlockPos pos) {
        BlockEntitySapBag tile = (BlockEntitySapBag) world.getBlockEntity(pos);
        int filled = tile != null ? (tile.getSap().isEmpty() || tile.getSap().getCount() == 0 ? 0 : tile.getSap().getCount() < 11 ? 1 : tile.getSap().getCount() < 21 ? 2 : tile.getSap().getCount() < 31 ? 3 : tile.getSap().getCount() < 41 ? 4 : tile.getSap().getCount() < 51 ? 5 : 6 ) : 0;
        return config.set(pos, new int[]{getModelId(state.getValue(HORIZONTAL_FACING), filled)});
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null) { //Y = 0 , reduce to xz plane
            Direction dir = getNearest((float) placer.getLookAngle().x, (float) 0, (float) placer.getLookAngle().z);
            world.setBlockAndUpdate(pos, state.setValue(HORIZONTAL_FACING, dir));
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BlockEntitySapBag sapBag){
                sapBag.setFacing(dir);
                sapBag.checkRubber();
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof BlockEntitySapBag){
            ((BlockEntitySapBag)tile).onBlockUpdate();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof BlockEntitySapBag){
            BlockEntitySapBag sapBag = (BlockEntitySapBag) tile;
            if (!sapBag.getSap().isEmpty()){
                if (!player.addItem(sapBag.getSap().copy())){
                    player.drop(sapBag.getSap().copy(), false);
                }
                sapBag.setSap(ItemStack.EMPTY);
                sapBag.checkRubber();
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = super.getDrops(state, builder);
        BlockEntity tileentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tileentity instanceof BlockEntitySapBag){
            ItemStack stack = ((BlockEntitySapBag)tileentity).getSap();
            if (!stack.isEmpty()){
                list.add(stack);
            }
        }
        return list;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return GTCoreBlocks.SAP_BAG_BLOCK_ENTITY.create(pPos, pState);
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(prov.existing(GTCore.ID, "block/sapbag/north")).texture("side", TEXTURES[2]).texture("bottom", TEXTURES[0]).texture("top", TEXTURES[1]);
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.getVariantBuilder(block).forAllStates(s -> new VariantBlockStateBuilder.VariantBuilder()
                .modelFile(buildModelsForState(prov.getBuilder(block), s))
                .uvLock()
        );
    }

    private int getModelId(Direction facing, int filled) {
        return facing.get2DDataValue() +  (filled * 10);
    }

    GTBlockModelBuilder buildModelsForState(GTBlockModelBuilder builder, BlockState state) {
        builder.staticConfigId("sap_bag");
        Direction f = state.getValue(HORIZONTAL_FACING);
        boolean waterlogged = state.getValue(WATERLOGGED);
        if (!waterlogged){
            for (int i = 0; i < 7; i++){
                String addition = i == 0 ? "" : "_filled_" + i;
                int finalI = i;
                builder.config(getModelId(f, i), (b, l) -> {
                    DynamicConfigBuilder build = b.of(new ResourceLocation(domain, "block/sapbag/" + f.getSerializedName() + addition)).tex(of("side", TEXTURES[2], "bottom", TEXTURES[0], "top", TEXTURES[1]));
                    if (finalI != 0){
                        build.tex(of("side", TEXTURES[2], "bottom", TEXTURES[0], "top", TEXTURES[1],"topfilled", TEXTURES[3]));
                    }
                    return l.add(build);
                });
            }
        }
        builder.property("particle", TEXTURES[2].toString());
        return builder.loader(BakedModels.LOADER_SAP_BAG);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
