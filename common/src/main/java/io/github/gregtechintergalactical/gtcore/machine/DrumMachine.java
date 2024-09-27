package io.github.gregtechintergalactical.gtcore.machine;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityDrum;
import io.github.gregtechintergalactical.gtcore.item.ItemBlockDrum;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tesseract.FluidPlatformUtils;
import tesseract.TesseractGraphWrappers;

import static muramasa.antimatter.Data.WRENCH_MATERIAL;

public class DrumMachine extends MaterialMachine{
    public final int maxCapacity;
    private boolean acidProof = false;
    public DrumMachine(String domain, Material material, int maxCapacity) {
        super(domain, material.getId() + "_drum", material);
        AntimatterAPI.register(DrumMachine.class, this);
        this.maxCapacity = maxCapacity;
        setTiers(Tier.NONE);
        this.setTile(((materialMachine, blockPos, blockState) -> new BlockEntityDrum(this, blockPos, blockState)));
        setBlock((type, tier) -> new BlockMachineMaterial(type, tier, BlockBehaviour.Properties.of(WRENCH_MATERIAL).strength(1.0f, 10.0f)));
        setItemBlock(ItemBlockDrum::new);
        setTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("machine.drum.capacity", maxCapacity).withStyle(ChatFormatting.AQUA));
            if (acidProof){
                tooltip.add(Utils.translatable("antimatter.tooltip.acid_proof"));
            }
            CompoundTag nbt = stack.getTag();
            FluidHolder fluid = nbt != null && nbt.contains("Fluid") ? FluidHooks.fluidFromCompound(nbt.getCompound("Fluid")) : FluidHooks.safeGetItemFluidManager(stack).map(fi -> fi.getFluidInTank(0)).orElse(FluidHooks.emptyFluid());
            if (!fluid.isEmpty()){
                tooltip.add(Utils.translatable("machine.drum.fluid", fluid.getFluidAmount() / TesseractGraphWrappers.dropletMultiplier, FluidPlatformUtils.INSTANCE.getFluidDisplayName(fluid)).withStyle(ChatFormatting.AQUA));
            }
            if (nbt != null && nbt.contains("Outputs")){
                tooltip.add(Utils.translatable("machine.drum.output"));
            }
        });
        baseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/drum/bottom"),
                new Texture(GTCore.ID, "block/machine/base/drum/top"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
        });
        overlayTexture((type, state, tier, i) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/overlay/drum/bottom"),
                new Texture(GTCore.ID, "block/machine/overlay/drum/top"),
                new Texture(GTCore.ID, "block/machine/overlay/drum/side"),
                new Texture(GTCore.ID, "block/machine/overlay/drum/side"),
                new Texture(GTCore.ID, "block/machine/overlay/drum/side"),
                new Texture(GTCore.ID, "block/machine/overlay/drum/side"),
        });
    }

    public DrumMachine acidProof(){
        this.acidProof = true;
        return this;
    }

    public boolean isAcidProof() {
        return acidProof;
    }
}
