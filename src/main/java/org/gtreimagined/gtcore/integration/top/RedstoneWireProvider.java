package org.gtreimagined.gtcore.integration.top;

import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtlib.integration.top.TheOneProbePlugin;

public class RedstoneWireProvider implements IProbeInfoProvider {
    public static void createTopProvider(){
        TheOneProbePlugin.addProbeInfoProvider(RedstoneWireProvider::new);
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(GTCore.ID, "redstone_wire");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockEntity blockEntity = level.getBlockEntity(iProbeHitData.getPos());
        if (blockEntity instanceof BlockEntityRedstoneWire<?> wire){
            int redstone = wire.getVanillaRedstonePower();
            if (redstone > 0 && Tools.show(probeMode, Config.getRealConfig().getShowRedstone())) {
                probeInfo.horizontal().item(new ItemStack(Items.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14)).text(CompoundText.createLabelInfo("Power: ", redstone));
            }
        }
    }
}
