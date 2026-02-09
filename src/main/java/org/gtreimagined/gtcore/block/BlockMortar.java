package org.gtreimagined.gtcore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreRecipeMaps;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.block.BlockBasic;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.jetbrains.annotations.Nullable;

public class BlockMortar extends BlockBasic implements IColorHandler {
    final Material material;
    static final int CERAMIC_COLOR = 0xDC8246;
    static final VoxelShape BOTTOM_SHAPE = Shapes.box(0.125, 0.0, 0.125, 0.875, 0.0625, 0.875);
    static final VoxelShape NORTH_SHAPE = Shapes.box(0.25, 0.0, 0.125, 0.75, 0.375, 0.25);
    static final VoxelShape SOUTH_SHAPE = Shapes.box(0.25, 0.0, 0.75, 0.75, 0.375, 0.875);
    static final VoxelShape EAST_SHAPE = Shapes.box(0.75, 0.0, 0.125, 0.875, 0.375, 0.875);
    static final VoxelShape WEST_SHAPE = Shapes.box(0.125, 0.0, 0.125, 0.25, 0.375, 0.875);
    static final VoxelShape BASE_SHAPE = Shapes.or(BOTTOM_SHAPE, NORTH_SHAPE, SOUTH_SHAPE, EAST_SHAPE, WEST_SHAPE);
    static final VoxelShape CENTER_SHAPE = Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.5625, 0.625);
    static final VoxelShape FULL_SHAPE = Shapes.or(BASE_SHAPE, CENTER_SHAPE);
    public BlockMortar(Material material) {
        super(GTCore.ID, material.getId() + "_mortar", Properties.of().noOcclusion().strength(1.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_BROWN));
        this.material = material;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        return i == 0 ? CERAMIC_COLOR : i == 1 ? material.getRGB() : i == 2 ? 0xffff00 : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? CERAMIC_COLOR : i == 1 ? material.getRGB() : i == 2 ? 0xffff00 : -1;
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.simpleBlock(block, () -> prov.existing(GTCore.ID, "block/mortar"));
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(prov.existing(GTCore.ID, "block/mortar")).build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FULL_SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Vec3 location = hit.getLocation();
        double x = location.x() - pos.getX();
        double z = location.z() - pos.getZ();
        if (hit.getDirection() == Direction.UP){
            if (x > 0.125 && x < 0.25 && z > 0.125 && z < 0.25){
                if (level.isClientSide()) {
                    GTLibXEIPlugin.showCategories(GTCoreRecipeMaps.MORTAR.getLoc());
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        ItemStack stack = player.getItemInHand(hand);
        IRecipe recipe = GTCoreRecipeMaps.MORTAR.find(new ItemStack[]{stack}, new FluidStack[0], Tier.NONE, r -> true);
        if (recipe != null){
            if (!level.isClientSide()){
                for (ItemStack outputItem : recipe.getOutputItems()) {
                    if (!player.addItem(outputItem.copy())){
                        player.drop(outputItem.copy(), false);
                    }
                }
                stack.shrink(recipe.getInputItems().get(0).getItems()[0].getCount());
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
