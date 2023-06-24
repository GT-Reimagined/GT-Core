package io.github.gregtechintergalactical.gtutility.network;

import muramasa.antimatter.network.packets.IAntimatterPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class MessageCraftingSync implements IAntimatterPacket {

    public static MessageCraftingSync decodeStatic(FriendlyByteBuf buffer){
        return new MessageCraftingSync();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        encodeStatic(this, buf);
    }

    @Override
    public void handleClient(ServerPlayer player) {
        if (player != null) {
            AbstractContainerMenu container = player.containerMenu;
            if (container != null) {
                container.slotsChanged(null);
            }
        }
    }

    public static void encodeStatic(MessageCraftingSync message, FriendlyByteBuf buffer) {
    }
}
