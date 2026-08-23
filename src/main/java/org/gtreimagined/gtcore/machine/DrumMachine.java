package org.gtreimagined.gtcore.machine;

import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityDrum;
import org.gtreimagined.gtcore.item.ItemBlockDrum;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;



public class DrumMachine extends MaterialMachine{
    @Getter public final int maxCapacity;
    @Getter private boolean acidProof = false;
    @Getter private boolean magicProof = false;
    @Getter private boolean gasProof = true;
    public DrumMachine(String domain, Material material, int maxCapacity) {
        super(domain, material.getId() + "_drum", material);
        GTAPI.register(DrumMachine.class, this);
        this.maxCapacity = maxCapacity;
        setTiers(Tier.NONE);
        this.setTile(((materialMachine, blockPos, blockState) -> new BlockEntityDrum(this, blockPos, blockState)));
        setBlock((type, tier) -> new BlockMachineMaterial(type, tier, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.0f, 10.0f)));
        setItemBlock(ItemBlockDrum::new);
        addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("machine.drum.capacity", maxCapacity).withStyle(ChatFormatting.AQUA));
            if (gasProof){
                tooltip.add(Utils.translatable("gtlib.tooltip.gas_proof"));
            }
            if (acidProof){
                tooltip.add(Utils.translatable("gtlib.tooltip.acid_proof"));
            }
            if (magicProof){
                tooltip.add(Utils.translatable("gtlib.tooltip.magic_proof"));
            }
            CompoundTag nbt = stack.getTag();
            FluidStack fluid = nbt != null && nbt.contains("Fluid") ? FluidUtils.fromTag(nbt.getCompound("Fluid")) : stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fi -> fi.getFluidInTank(0)).orElse(FluidStack.EMPTY);
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

    public DrumMachine magicProof(){
        this.magicProof = true;
        return this;
    }

    public DrumMachine nonGasProof(){
        this.gasProof = false;
        return this;
    }
}
