package org.gtreimagined.gtcore.integration.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtlib.util.CodeUtils;
import org.gtreimagined.gtlib.util.Utils;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class RedstoneWireProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    private static final ResourceLocation ID = new ResourceLocation(GTCore.ID, "redstone_wire");
    public static final RedstoneWireProvider INSTANCE = new RedstoneWireProvider();
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getBlockEntity() instanceof BlockEntityRedstoneWire<?> wire){
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

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
