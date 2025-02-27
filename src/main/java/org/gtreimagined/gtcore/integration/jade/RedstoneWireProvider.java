package org.gtreimagined.gtcore.integration.jade;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import muramasa.antimatter.util.CodeUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;

public class RedstoneWireProvider implements IComponentProvider, IServerDataProvider<BlockEntity> {
    public static final RedstoneWireProvider INSTANCE = new RedstoneWireProvider();
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.get(JadePlugin.REDSTONE_WIRE) && accessor.getBlockEntity() instanceof BlockEntityRedstoneWire<?> wire){
            if (!accessor.isServerConnected() || accessor.getServerData().contains("mRedstone")){
                long mRedstone = accessor.isServerConnected() ? accessor.getServerData().getLong("mRedstone") : wire.mRedstone;
                int redstone = CodeUtils.bind4(CodeUtils.divup(mRedstone, BlockEntityRedstoneWire.MAX_RANGE));
                if (redstone > 0){
                    iTooltip.add(Utils.translatable("tooltip.waila.power", redstone));
                }
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity tile, boolean b) {
        if (tile instanceof BlockEntityRedstoneWire<?> wire){
            compoundTag.putLong("mRedstone", wire.mRedstone);
        }
    }
}
