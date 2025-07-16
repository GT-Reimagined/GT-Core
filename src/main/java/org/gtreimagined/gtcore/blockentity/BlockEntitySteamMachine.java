package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtcore.machine.SteamMachine;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.util.TagUtils;

import static org.gtreimagined.gtlib.machine.Tier.BRONZE;

public class BlockEntitySteamMachine extends BlockEntityMachine<BlockEntitySteamMachine> {

    public static final TagKey<Fluid> STEAM = TagUtils.getForgelikeFluidTag("steam");

    SteamMachine machine;

    public BlockEntitySteamMachine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidHandler.set(() -> new MachineFluidHandler<>(this, 64000));
        recipeHandler.set(() -> new SteamMachineRecipeHandler(this));
        if (type instanceof SteamMachine machine) {
            this.machine = machine;
        }
    }

    @Override
    public Tier getPowerLevel() {
        return Tier.LV;
    }

    public static class SteamMachineRecipeHandler extends MachineRecipeHandler<BlockEntitySteamMachine>{
        protected boolean isSteamClear = false;
        protected boolean firstBlockedRun = false;

        public SteamMachineRecipeHandler(BlockEntitySteamMachine tile) {
            super(tile);
        }

        @Override
        public boolean consumePower(boolean simulate) {
            return tile.fluidHandler.map(t -> t.consumeTaggedInput(STEAM, (int) getPower(), simulate).getAmount() > 0)
                    .orElse(false);
        }
        //Allow up to 16 .
        @Override
        protected boolean validateRecipe(IRecipe r) {
            return r.getPower() * tile.machine.getEuMultiplier() <= Tier.LV.getVoltage();
        }

        public void setSteamClear(boolean steamClear) {
            isSteamClear = steamClear;
            if (steamClear){
                firstBlockedRun = false;
                checkRecipe();
            }
        }

        @Override
        protected boolean canRecipeContinue() {
            isSteamClear = tile.level.isEmptyBlock(tile.worldPosition.relative(tile.getOutputFacing()));
            return super.canRecipeContinue() && (isSteamClear || !firstBlockedRun);
        }

        @Override
        protected MachineState recipeFinish() {
            if (!firstBlockedRun) firstBlockedRun = true;
            return super.recipeFinish();
        }

        @Override
        public long getPower() {
            if (activeRecipe == null) return 0;
            return (activeRecipe.getPower() * (tile.getMachineTier() == BRONZE ? 1 : 2) * (tile.machine.getEuMultiplier()));
        }

        @Override
        protected void calculateDurations() {
            super.calculateDurations();
            maxProgress = activeRecipe.getDuration() * (tile.getMachineTier() == BRONZE ? 2 : 1) * tile.machine.getDurationMultiplier();
        }

        @Override
        public float getClientProgress() {
            if (tile.getMachineType().getId().contains("forge_hammer")){
                float percent = (float) currentProgress / ((float) maxProgress / 3);
                if (percent > 2){
                    percent -= 2;
                } else if (percent > 1){
                    percent -=1;
                }
                return percent;
            }
            return super.getClientProgress();
        }

        @Override
        public boolean accepts(FluidStack stack) {
            return super.accepts(stack) || stack.getFluid().builtInRegistryHolder().is(STEAM);
        }

        @Override
        public CompoundTag serialize() {
            CompoundTag tag = super.serialize();
            tag.putBoolean("firstBlockedRun", firstBlockedRun);
            return tag;
        }

        @Override
        public void deserialize(CompoundTag nbt) {
            super.deserialize(nbt);
            firstBlockedRun = nbt.getBoolean("firstBlockedRun");
        }
    }
}
