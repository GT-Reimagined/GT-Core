package org.gtreimagined.gtcore.blockentity;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import org.gtreimagined.gtcore.data.GTCoreTools;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtcore.item.ItemTape;
import org.gtreimagined.gtcore.machine.MassStorageMachine;
import org.gtreimagined.gtcore.machine.MassStorageItemHandler;
import org.gtreimagined.gtcore.network.MessageInventorySync;
import org.gtreimagined.gtcore.network.MessageTriggerInventorySync;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.network.AntimatterNetwork;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tesseract.FluidPlatformUtils;
import tesseract.TesseractCapUtils;
import tesseract.TesseractGraphWrappers;
import tesseract.api.item.ExtendedItemContainer;

import java.util.List;

public class BlockEntityMassStorage extends BlockEntityMaterial<BlockEntityMassStorage> implements IInventorySyncTile {
    boolean output = false;
    boolean outputOverflow = false;
    boolean syncSlots;
    public boolean keepFilter = true;
    public BlockEntityMassStorage(MassStorageMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new MassStorageItemHandler(this));
    }

    public MassStorageMachine getMassStorageMachine() {
        return (MassStorageMachine) type;
    }

    public int getMaxLimit(){
        return getMassStorageMachine().getCapacity();
    }

    public int getItemAmount(){
        return itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED).getItem(0).getCount()).orElse(0);
    }

    @Override
    public void onDrop(BlockState state, LootContext.Builder builder, List<ItemStack> drops) {
        if (!drops.isEmpty() && getMachineState() == MachineState.ACTIVE){
            ItemStack massStorage = drops.get(0);
            CompoundTag nbt = new CompoundTag();
            this.itemHandler.ifPresent(handler -> {
                handler.getAll().forEach((f, i) -> {
                    if (i.isEmpty()) return;
                    nbt.put(f.getId(), i.serialize(new CompoundTag()));
                });
            });
            if (!nbt.isEmpty()) {
                massStorage.getOrCreateTag().put("inventories", nbt);
            }
            massStorage.getOrCreateTag().putBoolean("taped", true);
            if (output){
                massStorage.getOrCreateTag().putBoolean("output", output);
            }
            if (outputOverflow){
                massStorage.getOrCreateTag().putBoolean("outputOverflow", outputOverflow);
            }
        }
    }

    @Override
    public void dropInventory(BlockState state, LootContext.Builder builder, List<ItemStack> drops) {
        if (getMachineState() != MachineState.ACTIVE) {
            itemHandler.ifPresent(t -> {
                ItemStack held = t.getHandler(SlotTypes.UNLIMITED).getItem(0);
                int amountToExtract = held.getCount();
                if (amountToExtract > 0){
                    if (amountToExtract > held.getMaxStackSize()){
                        int toExtract = amountToExtract;
                        while (toExtract > 0){
                            ItemStack toAdd = Utils.ca(Math.min(held.getMaxStackSize(), toExtract), held);
                            toExtract -= toAdd.getCount();
                            drops.add(toAdd);
                        }
                    } else {
                        ItemStack toAdd = Utils.ca(amountToExtract, held);
                        drops.add(toAdd);
                    }
                }
            });
        }
    }

    @Override
    public void onPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onPlacedBy(world, pos, state, placer, stack);
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("taped")) {
            if (nbt.contains("inventories")) {
                CompoundTag inventories = nbt.getCompound("inventories");
                this.itemHandler.ifPresent(handler -> {
                    handler.getAll().forEach((f, i) -> {
                        if (!inventories.contains(f.getId())) return;
                        i.deserialize(inventories.getCompound(f.getId()));
                    });
                });
                this.setMachineState(MachineState.ACTIVE);
            }
            if (nbt.contains("output") && nbt.getBoolean("output")) output = true;
            if (nbt.contains("outputOverflow") && nbt.getBoolean("outputOverflow")) outputOverflow = true;
        }
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable AntimatterToolType type) {
        if ((type == AntimatterDefaultTools.KNIFE || type == AntimatterDefaultTools.SCISSORS) && this.getMachineState() == MachineState.ACTIVE){
            setMachineState(MachineState.IDLE);
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            return InteractionResult.SUCCESS;
        }
        if (this.getMachineState() == MachineState.ACTIVE) return super.onInteractServer(state, world, pos, player, hand, hit, type);
        Vec3 vec = hit.getLocation();
        var handler = itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ItemTape tape && stack.isDamageableItem()) {
            int count = handler.getItem(0).getCount();
            if (count == 0 || count <= stack.getMaxDamage() - stack.getDamageValue()){
                int damage = count == 0 ? 1 : count;
                this.setMachineState(MachineState.ACTIVE);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(damage, player, (player2) -> {
                        player2.broadcastBreakEvent(hand);
                        if (!player2.addItem(new ItemStack(tape.getEmpty())))
                            player2.drop(new ItemStack(tape.getEmpty()), true);
                    });
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (type == AntimatterDefaultTools.WIRE_CUTTER){
            outputOverflow = !outputOverflow;
            //TODO: translation component
            player.sendMessage(Utils.literal(outputOverflow ? "Outputs overflow" : "Doesn't output overflow"), player.getUUID());
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            return InteractionResult.SUCCESS;
        }
        if (type == AntimatterDefaultTools.WRENCH_ALT){
            output = !output;
            //TODO: translation component
            player.sendMessage(Utils.literal(output ? "Auto output on" : "Auto output off"), player.getUUID());
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            return InteractionResult.SUCCESS;
        }
        if (type == AntimatterDefaultTools.SCREWDRIVER && coverHandler.map(c -> c.get(Utils.getInteractSide(hit)).isEmpty()).orElse(true)){
            keepFilter = !keepFilter;
            //TODO: translation component
            player.sendMessage(Utils.literal("Filter " + (keepFilter ? "Stays" : "Resets") + " when empty"), player.getUUID());
            Utils.damageStack(player.getItemInHand(hand), hand, player);
            return InteractionResult.SUCCESS;
        }
        if (hit.getDirection().getAxis().isHorizontal() && hit.getDirection() == this.getFacing() && handler != null){
            double x = hit.getDirection().getAxis() == Direction.Axis.Z ?  vec.x() - hit.getBlockPos().getX() : vec.z() - hit.getBlockPos().getZ(), y = vec.y() - hit.getBlockPos().getY();
            int amountToExtract = 0;


            if (x > 0.0625 && x < 0.1875) {
                if (y > 0.125 && y < 0.25){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 16 : 1;
                }
                if (y > 0.3125 && y < 0.4375){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 32 : 4;
                }
                if (y > 0.5 && y < 0.625){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 64 : 8;
                }

            } else if (x > 0.8125 && x < 0.9375) {
                if (y > 0.125 && y < 0.25){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 1 : 16;
                }
                if (y > 0.3125 && y < 0.4375){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 4 : 32;
                }
                if (y > 0.5 && y < 0.625){
                    amountToExtract = hit.getDirection().get2DDataValue() > 1 ? 8 : 64;
                }

            } else if (x > 0.25 && x < 0.75){
                if (y > 0.125 && y < 0.625){
                    ItemStack stored = handler.getItem(0);
                    ItemStack displayed = itemHandler.map(i -> i.getHandler(SlotType.DISPLAY).getItem(0)).orElse(ItemStack.EMPTY);
                    if (type == AntimatterDefaultTools.SOFT_HAMMER){
                        amountToExtract = stored.getCount();
                        Utils.damageStack(stack, hand, player);
                        itemHandler.get().getHandler(SlotType.DISPLAY).setItem(0, ItemStack.EMPTY);
                    } else {
                        if (!stack.isEmpty()){
                            ItemStack leftover = handler.insertItem(0, stack.copy(), true);
                            if (leftover.getCount() < stack.getCount()) {
                                handler.insertItem(0, stack.copy(), false);
                                stack.shrink(stack.getCount() - leftover.getCount());
                                return InteractionResult.SUCCESS;
                            }
                        } else {
                            if (!stored.isEmpty() || !displayed.isEmpty()) {
                                boolean sucess = false;
                                for (int i = 0; i < player.getInventory().items.size(); i++){
                                    ItemStack stack1 = player.getInventory().items.get(i);
                                    if (Utils.equals(stack1, displayed) || Utils.equals(stack1, stored)){
                                        ItemStack inserted = handler.insertItem(0, stack1.copy(), false);
                                        stack1.shrink(stack1.getCount() - inserted.getCount());
                                        sucess = true;
                                        if (inserted.getCount() > 0) break;
                                    }
                                }
                                if (sucess) {
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }

                }
            }
            ItemStack held = handler.getItem(0);
            if (amountToExtract > 0 && !held.isEmpty()){
                int extract = Math.min(amountToExtract, held.getCount());

                if (extract > held.getMaxStackSize()){
                    int toExtract = extract;
                    while (toExtract > 0){
                        ItemStack toAdd = Utils.ca(Math.min(held.getMaxStackSize(), toExtract), held);
                        toExtract -= toAdd.getCount();
                        if (!player.addItem(toAdd)){
                            player.drop(toAdd, true);
                        }
                    }
                } else {
                    ItemStack toAdd = Utils.ca(extract, held);
                    if (!player.addItem(toAdd)){
                        player.drop(toAdd, true);
                    }
                }

                handler.extractItem(0, extract, false);
                world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public void onFirstTick() {
        super.onFirstTick();
        if (level != null && isClientSide()){
            AntimatterNetwork.NETWORK.sendToServer(new MessageTriggerInventorySync(this.getBlockPos()));
        }
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (getMachineState() != MachineState.ACTIVE && output && level.getGameTime() % 10 == 0){
            processItemOutput(ItemStack.EMPTY, false);
        }
        if (syncSlots){
            syncSlots();
            syncSlots = false;
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        itemHandler.ifPresent(e -> {
            CompoundTag in = new CompoundTag();
            e.getAll().forEach((f,i) -> {
                in.put(f.getId(), serializeWithEmpty(i, new CompoundTag()));
            });
            nbt.put(Ref.KEY_MACHINE_ITEMS, in);
        });
        return nbt;
    }

    public CompoundTag serializeWithEmpty(Container container, CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();


        for(int i = 0; i < container.getContainerSize(); ++i) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("Slot", i);
            container.getItem(i).save(itemTag);
            itemTag.putInt("count", container.getItem(i).getCount());
            nbtTagList.add(itemTag);
        }

        nbt.put("Items", nbtTagList);
        return nbt;
    }

    public void setSyncSlots(boolean syncSlots) {
        this.syncSlots = syncSlots;
    }

    public void syncSlots(){
        if (getLevel() != null && isServerSide()){
            this.sidedSync(true);
        }
    }


    public void processItemOutput(ItemStack itemStack, boolean simulate) {
        Direction outputDir = Direction.DOWN;
        BlockEntity adjTile = Utils.getTile(this.getLevel(), this.getBlockPos().relative(outputDir));
        if (adjTile == null) return;
        if (!itemStack.isEmpty()) {
            TesseractCapUtils.INSTANCE.getItemHandler(adjTile, outputDir.getOpposite()).ifPresent(adjHandler -> {
                ItemStack transferred = Utils.insertItem(adjHandler, itemStack.copy(), simulate);
                itemStack.shrink(itemStack.getCount() - transferred.getCount());
            });
        } else if (!simulate){
            TesseractCapUtils.INSTANCE.getItemHandler(adjTile, outputDir.getOpposite()).ifPresent(adjHandler -> {
                this.itemHandler.ifPresent(h -> Utils.transferItems(h.getHandler(SlotTypes.UNLIMITED), adjHandler,true));
            });
        }
    }

    @Override
    public boolean canPlayerOpenGui(Player playerEntity) {
        return playerEntity.isCreative();
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event instanceof SlotType<?> && data.length > 0 && data[0] instanceof Integer){
            if (isServerSide() && getLevel() != null){
                sidedSync(true);
            }
        }
        super.onMachineEvent(event, data);
    }

    public boolean isOutputOverflow() {
        return outputOverflow;
    }

    public boolean isOutput() {
        return output;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("outputOverflow", outputOverflow);
        tag.putBoolean("output", output);
        tag.putBoolean("keepFilter", keepFilter);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        outputOverflow = tag.getBoolean("outputOverflow");
        output = tag.getBoolean("output");
        keepFilter = !tag.contains("keepFilter") || tag.getBoolean("keepFilter");
        if (level != null && isClientSide()){
            sidedSync(true);
        }
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> list = super.getInfo(simple);
        list.add("Auto Outputs: " + output);
        list.add("Keeps Filter: " + keepFilter);
        list.add("Outputs Overflow: " + outputOverflow);
        return list;
    }
}
