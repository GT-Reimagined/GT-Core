package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.data.GTLibTags;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static muramasa.antimatter.data.AntimatterDefaultTools.WRENCH;
import static net.minecraft.core.Direction.DOWN;
import static net.minecraft.core.Direction.UP;

public class BlockEntityDrum extends BlockEntityMaterial<BlockEntityDrum> {
    FluidStack drop = FluidStack.EMPTY;
    boolean output = false;
    public BlockEntityDrum(DrumMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new DrumFluidHandler(this));
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable AntimatterToolType type) {
        boolean[] success = new boolean[1];
        this.fluidHandler.ifPresent(f -> {
            DrumFluidHandler dF = (DrumFluidHandler) f;
            if ((type == WRENCH) && !player.isShiftKeyDown()){
                dF.setOutput(!dF.isOutput());
                success[0] = true;
                player.playNotifySound(Ref.WRENCH, SoundSource.BLOCKS, 1.0f, 1.0f);
                // TODO: Replace by new TranslationTextComponent()
                player.sendMessage(Utils.literal((dF.isOutput() ? "Will" : "Won't") + " fill adjacent Tanks"), player.getUUID());
            }
        });
        if (success[0]){
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove() {
        this.fluidHandler.ifPresent(f -> {
            this.drop = f.getFluidInTank(0);
            this.output = ((DrumFluidHandler)f).isOutput();
        });
       super.onRemove();
    }

    @Override
    public void onDrop(BlockState state, LootContext.Builder builder, List<ItemStack> drops) {
        if (!drops.isEmpty()){
            ItemStack stack = drops.get(0);
            if (!getDrop().isEmpty()){
                stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(f -> f.fill(drop, FluidAction.EXECUTE));
            }
            if (isOutput()){
                CompoundTag nbt = stack.getOrCreateTag();
                nbt.putBoolean("Outputs", isOutput());
            }
        }
    }

    @Override
    public void onPlacedBy(Level world, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        super.onPlacedBy(world, pos, state, placer, stack);
        CompoundTag nbt = stack.getTag();
        this.fluidHandler.ifPresent(f -> {
            FluidStack fluid = nbt != null && nbt.contains("Fluid") ? FluidUtils.fromTag(nbt.getCompound("Fluid")) : stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(fi -> fi.getFluidInTank(0)).orElse(FluidStack.EMPTY);
            if (!fluid.isEmpty()){
                f.fill(fluid, FluidAction.EXECUTE);
            }
            if (nbt != null && nbt.contains("Outputs")){
                ((DrumFluidHandler)f).setOutput(nbt.getBoolean("Outputs"));
            }
        });
    }

    public FluidStack getDrop() {
        return drop;
    }

    public boolean isOutput() {
        return output;
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> list = super.getInfo(simple);
        fluidHandler.ifPresent(f -> {
            FluidStack stack = f.getInputTanks().getFluidInTank(0);
            list.add("Fluid: " + (stack.isEmpty() ? "Empty" : stack.getAmount() + "mb" + " of " + FluidUtils.getFluidDisplayName(stack).getString()));
        });
        list.add("Auto Output: " + isOutput());
        return list;
    }

    public static class DrumFluidHandler extends MachineFluidHandler<BlockEntityDrum> {
        boolean output = false;
        public DrumFluidHandler(BlockEntityDrum tile) {
            super(tile);
            tanks.put(FluidDirection.INPUT, FluidTanks.create(tile, SlotType.FL_IN, b -> {
                b.tank(((DrumMachine)tile.getMachineType()).maxCapacity);
                return b;
            }));
        }

        public void setOutput(boolean output) {
            this.output = output;
        }

        public boolean isOutput() {
            return output;
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
            if (output){
                Direction dir = FluidUtils.getFluidDensity(getTank(0).getFluid().getFluid()) < 0 ? UP : DOWN;
                if (getTank(0).getFluid().getAmount() > 0){
                    FluidUtils.getFluidHandler(tile.getLevel(), tile.getBlockPos().relative(dir), tile.getCachedBlockEntity(dir), dir.getOpposite()).ifPresent(other -> Utils.transferFluids(this, other, 1000));
                }
            }
        }

        @Override
        public CompoundTag serialize(CompoundTag nbt) {
            super.serialize(nbt);
            nbt.putBoolean("Output", output);
            return nbt;
        }

        @Override
        public void deserialize(CompoundTag nbt) {
            super.deserialize(nbt);
            this.output = nbt.getBoolean("Output");
        }

        @Override
        public boolean canInput(FluidStack fluid, Direction direction) {
            boolean gaseous = FluidUtils.getFluidDensity(fluid.getFluid()) < 0;
            if (output && ((direction == UP && gaseous) || (direction == DOWN && !gaseous))) return false;
            return super.canInput(fluid, direction);
        }

        @Override
        public int fill(FluidStack fluid, FluidAction action) {
            if (tile.getMachineType() instanceof DrumMachine drumMachine && !drumMachine.isAcidProof() && fluid.getFluid().is(GTLibTags.ACID)){
                int insert = super.fill(fluid, FluidAction.SIMULATE);
                if (insert > 0){
                    if (action.execute()) {
                        tile.getLevel().setBlock(tile.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
                    }
                    return Math.min(16, fluid.getAmount());
                }
            }
            return super.fill(fluid, action);
        }
    }
}
