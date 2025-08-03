package org.gtreimagined.gtcore.blockentity;

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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtcore.machine.MultiblockTankMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.fluid.FluidHandlerSidedWrapper;
import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.capability.fluid.IFluidNode;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.data.GTLibTags;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.core.Direction.UP;

public class BlockEntityLargeTank extends BlockEntityMaterialBasicMultiMachine<BlockEntityLargeTank> {
    MultiblockTankMachine tankMachine;
    LazyOptional<IFluidHandler> fakeFacingWrapper;
    public BlockEntityLargeTank(MultiblockTankMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new LargeTankFluidHandler(this, type.getCapacity(), 1, 0));
        this.tankMachine = type;
        this.fakeFacingWrapper = LazyOptional.of(() -> new FakeSidedFluidHandler(fluidHandler.get(), coverHandler.get(), this.getFacing(state)));
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

    @Override
    public <U> LazyOptional<U> getCapabilityFromFake(@NotNull Capability<U> cap, @Nullable Direction side, ICover cover) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && side == this.getFacing()) {
            return fakeFacingWrapper.cast();
        }
        return super.getCapabilityFromFake(cap, side, cover);
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
            if (fluid.getFluid().is(GTCoreTags.STEAM)) {
                if (fluid.getAmount() >= 160) {
                    int toFill = fluid.getAmount() / 160;
                    return fill(GTLibMaterials.Water.getLiquid(toFill), action);
                }
                return 0;
            }
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
            if (!tile.tankMachine.isAcidProof() && fluid.getFluid().is(GTLibTags.ACID)){
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

    public static class FakeSidedFluidHandler extends FluidHandlerSidedWrapper {
        private CoverHandler<?> coverHandler;

        public FakeSidedFluidHandler(IFluidNode fluidHandler, CoverHandler<?> coverHandler, Direction side) {
            super(fluidHandler, coverHandler, side);
            this.coverHandler = coverHandler;
        }
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            if (this.coverHandler != null) {
                if (this.coverHandler.get(this.side).blocksInput(IFluidHandler.class, this.side)) {
                    return 0;
                }

                int oldAmount = resource.getAmount();
                if (this.coverHandler.onTransfer(resource, this.side, true, action.simulate())) {
                    return oldAmount - resource.getAmount();
                }
            }

            return this.fluidHandler.canInput(resource, this.side) ? this.fluidHandler.fill(resource, action) : 0;
        }
    }
}
