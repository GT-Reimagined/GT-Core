package io.github.gregtechintergalactical.gtcore.network;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMassStorage;
import io.github.gregtechintergalactical.gtcore.blockentity.IInventorySyncTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class MessageTriggerInventorySync implements Packet<MessageTriggerInventorySync> {
    public static final PacketHandler<MessageTriggerInventorySync> HANDLER = new Handler();
    private BlockPos pos;
    public MessageTriggerInventorySync(BlockPos pos){
        this.pos = pos;
    }

    @Override
    public ResourceLocation getID() {
        return GTCore.TRIGGER_SYNC_ID;
    }

    @Override
    public PacketHandler<MessageTriggerInventorySync> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<MessageTriggerInventorySync> {

        @Override
        public void encode(MessageTriggerInventorySync messageTriggerInventorySync, FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeBlockPos(messageTriggerInventorySync.pos);
        }

        @Override
        public MessageTriggerInventorySync decode(FriendlyByteBuf friendlyByteBuf) {
            return new MessageTriggerInventorySync(friendlyByteBuf.readBlockPos());
        }

        @Override
        public PacketContext handle(MessageTriggerInventorySync messageTriggerInventorySync) {
            return (player, level) -> {
                if (level.getBlockEntity(messageTriggerInventorySync.pos) instanceof IInventorySyncTile massStorage){
                    massStorage.setSyncSlots(true);
                }
            };
        }
    }
}
