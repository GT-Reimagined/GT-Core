package org.gtreimagined.gtcore.blockentity;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.capability.fluid.FluidTanks;
import muramasa.antimatter.capability.machine.MachineFluidHandler;
import muramasa.antimatter.data.AntimatterTags;
import muramasa.antimatter.util.FluidUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gtcore.machine.MultiblockTankMachine;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractGraphWrappers;

import java.util.List;
import java.util.Optional;

import static muramasa.antimatter.data.AntimatterMaterials.Wood;
import static net.minecraft.core.Direction.UP;

public class BlockEntityLargeTank extends BlockEntityMaterialBasicMultiMachine<BlockEntityLargeTank> {
    MultiblockTankMachine tankMachine;
    public BlockEntityLargeTank(MultiblockTankMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new LargeTankFluidHandler(this, type.getCapacity(), 1, 0));
        this.tankMachine = type;
    }

    @Override
    public boolean allowsFakeTiles() {
        return true;
    }

    public Block getCasing(){
        Block block = tankMachine.getCasing().get();
        if (block != null) return block;
        return Blocks.AIR;
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> list = super.getInfo(simple);
        fluidHandler.ifPresent(f -> {
            FluidStack stack = f.getInputTanks().getFluidInTank(0);
            list.add("Fluid: " + (stack.isEmpty() ? "Empty" : stack.getAmount() + "mb" + " of " + FluidUtils.getFluidDisplayName(stack).getString()));
        });
        return list;
    }

    public static class LargeTankFluidHandler extends MachineFluidHandler<BlockEntityLargeTank> {

        public LargeTankFluidHandler(BlockEntityLargeTank tile, int capacity, int inputCount, int outputCount) {
            super(tile, capacity, inputCount, outputCount);
        }

        @Nullable
        @Override
        public FluidTanks getOutputTanks() {
            return super.getInputTanks();
        }

        @Override
        protected FluidTank getTank(int tank) {
            return getInputTanks().getTank(tank);
        }

        @Override
        public FluidTanks getTanks(int tank) {
            return getInputTanks();
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            Direction dir = tile.getFacing();
            if (getTank(0).getFluid().getAmount() > 0 && (dir != UP || FluidUtils.getFluidDensity(getTank(0).getFluid().getFluid()) < 0)){
                FluidUtils.getFluidHandler(tile.getLevel(), tile.getBlockPos().relative(dir), tile.getCachedBlockEntity(dir), dir.getOpposite()).ifPresent(other -> Utils.transferFluids(this.getOutputTanks(), other, 1000));
            }
        }

        @Override
        public boolean canInput(Direction direction) {
            return direction != tile.getFacing();
        }

        @Override
        public int fill(FluidStack fluid, FluidAction action) {
            if (!tile.tankMachine.isGasProof()){
                if (FluidUtils.isFluidGaseous(fluid.getFluid())) {
                    int inserted = super.fill(fluid, FluidAction.SIMULATE);
                    if (inserted > 0) {
                        if (action.execute()) tile.getLevel().playSound(null, tile.getBlockPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return inserted;
                    }
                    return 0;
                }
            }
            if (FluidUtils.getFluidTemperature(fluid.getFluid()) > tile.tankMachine.getMaxHeat()){
                int inserted = super.fill(fluid, action);
                if (inserted > 0 && action.execute()){
                    meltdown();
                }
                return inserted;
            }
            if (!tile.tankMachine.isAcidProof() && fluid.getFluid().is(AntimatterTags.ACID)){
                if (action.execute()) {
                    tile.getLevel().setBlock(tile.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
                }
                return Math.min(16, fluid.getAmount());
            }
            return super.fill(fluid, action);
        }

        public boolean meltdown() {
            BlockPos offset = tile.getBlockPos().relative(tile.getFacing().getOpposite());
            int tX = offset.getX(), tY = offset.getY(), tZ = offset.getZ();
            for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) for (int k = -1; k <= 1; k++) {
                burn(tile.level, tX+i, tY+j, tZ+k);
                if (tile.getLevel().random.nextInt(4) == 0) tile.getLevel().setBlock(new BlockPos(tX+i, tY+j, tZ+k), Blocks.FIRE.defaultBlockState(), 3);
            }
            FluidStack fluidHolder = getInputTanks().getTank(0).getFluid();
            if (fluidHolder.getAmount() >= 1000 && fluidHolder.getFluid() == Fluids.LAVA){
                tile.getLevel().setBlock(offset, Blocks.LAVA.defaultBlockState(), 3);
            }
            tile.getLevel().setBlock(tile.getBlockPos(), Blocks.FIRE.defaultBlockState(), 3);
            return true;
        }

        public static void burn(Level aWorld, int aX, int aY, int aZ) {
            BlockPos pos = new BlockPos(aX, aY, aZ);
            for (Direction tSide : Direction.values()) {
                fire(aWorld, pos.relative(tSide), false);
            }
        }

        public static boolean fire(Level aWorld, BlockPos pos, boolean aCheckFlammability) {
            BlockState tBlock = aWorld.getBlockState(pos);
            if (tBlock.getMaterial() == Material.LAVA || tBlock.getMaterial() == Material.FIRE) return false;
            if (tBlock.getMaterial() == Material.CLOTH_DECORATION || tBlock.getCollisionShape(aWorld, pos).isEmpty()) {
                if (tBlock.getFlammability(aWorld, pos, Direction.NORTH) > 0) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                if (aCheckFlammability) {
                    for (Direction tSide : Direction.values()) {
                        BlockState tAdjacent = aWorld.getBlockState(pos.relative(tSide));
                        if (tAdjacent.getBlock() == Blocks.CHEST || tAdjacent.getBlock() == Blocks.TRAPPED_CHEST) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                        if (tAdjacent.getFlammability(aWorld, pos.relative(tSide), tSide.getOpposite()) > 0) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                    }
                } else {
                    return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                }
            }
            return false;
        }
    }
}
