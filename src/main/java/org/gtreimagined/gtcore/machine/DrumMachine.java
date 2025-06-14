package org.gtreimagined.gtcore.machine;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityDrum;
import org.gtreimagined.gtcore.item.ItemBlockDrum;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;

import static org.gtreimagined.gtlib.Data.WRENCH_MATERIAL;


public class DrumMachine extends MaterialMachine{
    public final int maxCapacity;
    private boolean acidProof = false;
    public DrumMachine(String domain, Material material, int maxCapacity) {
        super(domain, material.getId() + "_drum", material);
        GTAPI.register(DrumMachine.class, this);
        this.maxCapacity = maxCapacity;
        setTiers(Tier.NONE);
        this.setTile(((materialMachine, blockPos, blockState) -> new BlockEntityDrum(this, blockPos, blockState)));
        setBlock((type, tier) -> new BlockMachineMaterial(type, tier, BlockBehaviour.Properties.of(WRENCH_MATERIAL).strength(1.0f, 10.0f)));
        setItemBlock(ItemBlockDrum::new);
        addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("machine.drum.capacity", maxCapacity).withStyle(ChatFormatting.AQUA));
            if (acidProof){
                tooltip.add(Utils.translatable("gtlib.tooltip.acid_proof"));
            }
            CompoundTag nbt = stack.getTag();
            FluidStack fluid = nbt != null && nbt.contains("Fluid") ? FluidUtils.fromTag(nbt.getCompound("Fluid")) : stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(fi -> fi.getFluidInTank(0)).orElse(FluidStack.EMPTY);
            if (!fluid.isEmpty()){
                tooltip.add(Utils.translatable("machine.drum.fluid", fluid.getAmount(), FluidUtils.getFluidDisplayName(fluid)).withStyle(ChatFormatting.AQUA));
            }
            if (nbt != null && nbt.contains("Outputs")){
                tooltip.add(Utils.translatable("machine.drum.output"));
            }
        });
        setBaseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/drum/bottom"),
                new Texture(GTCore.ID, "block/machine/base/drum/top"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
                new Texture(GTCore.ID, "block/machine/base/drum/side"),
        });
        setOverlayTextures((type, state, tier, i) -> new Texture[] {
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
