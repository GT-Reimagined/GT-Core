package org.gtreimagined.gtcore.network;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.gui.SlotType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.gtcore.GTCore;

public class MessageInventorySync implements Packet<MessageInventorySync> {
    public static final PacketHandler<MessageInventorySync> HANDLER = new Handler();

    SlotType<?> type;
    int slot;
    ItemStack stack;
    BlockPos pos;
    public MessageInventorySync(BlockPos pos, SlotType<?> type, int slot, ItemStack stack){
        this.type = type;
        this.slot = slot;
        this.stack = stack;
        this.pos = pos;
    }

    @Override
    public ResourceLocation getID() {
        return GTCore.INV_SYNC_ID;
    }

    @Override
    public PacketHandler<MessageInventorySync> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<MessageInventorySync>{
        @Override
        public void encode(MessageInventorySync inventorySync, FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeBlockPos(inventorySync.pos);
            friendlyByteBuf.writeUtf(inventorySync.type.getId());
            friendlyByteBuf.writeVarInt(inventorySync.slot);
            writeItemNoLimit(friendlyByteBuf, inventorySync.stack);

        }

        public void writeItemNoLimit(FriendlyByteBuf buf, ItemStack stack) {
            if (stack.isEmpty()) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                Item item = stack.getItem();
                buf.writeVarInt(Item.getId(item));
                buf.writeInt(stack.getCount());
                CompoundTag compoundTag = null;
                if (item.canBeDepleted() || item.shouldOverrideMultiplayerNbt()) {
                    compoundTag = stack.getTag();
                }

                buf.writeNbt(compoundTag);
            }
        }

        public ItemStack readItemNoLimit(FriendlyByteBuf buf) {
            if (!buf.readBoolean()) {
                return ItemStack.EMPTY;
            } else {
                int i = buf.readVarInt();
                int j = buf.readInt();
                ItemStack itemStack = new ItemStack(Item.byId(i), j);
                itemStack.setTag(buf.readNbt());
                return itemStack;
            }
        }

        @Override
        public MessageInventorySync decode(FriendlyByteBuf friendlyByteBuf) {
            return new MessageInventorySync(friendlyByteBuf.readBlockPos(), AntimatterAPI.get(SlotType.class, friendlyByteBuf.readUtf(), Ref.ID), friendlyByteBuf.readVarInt(), readItemNoLimit(friendlyByteBuf));
        }

        @Override
        public PacketContext handle(MessageInventorySync inventorySync) {
            return (player, level) -> {
                if (player != null) {
                    BlockEntity blockEntity = level.getBlockEntity(inventorySync.pos);
                    if (blockEntity instanceof BlockEntityMachine<?> machine){
                        machine.itemHandler.ifPresent(i -> {
                            var handler = i.getHandler(inventorySync.type);
                            if (handler.getSlots() > 0 && inventorySync.slot < handler.getSlots()){
                                handler.setStackInSlot(inventorySync.slot, inventorySync.stack);
                            }
                        });
                    }
                }
            };
        }
    }
}
